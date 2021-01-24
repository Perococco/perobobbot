import {sessionWritable} from "./session-storage-store";
import type {Readable, Writable} from "svelte/types/runtime/store";
import {SecurityController} from "@backend/rest-controller";
import type {JwtInfo, SimpleUser} from "@backend/security-com";
import {Optional} from "@optional";
import {PromiseHelper} from "../types/promiseHelper";

const JWT_KEY = "jwt_token";
const securityController = new SecurityController();

//Type declarations

declare interface Authentication {
    user?: SimpleUser;
}

declare interface Authenticator extends Readable<Authentication> {
    logout: () => void;
    signIn: (login: string, password: string, rememberMe: boolean) => Promise<void>;
    isAuthenticated: () => Promise<boolean>;
    currentUser: () => Promise<Optional<SimpleUser>>
}

declare interface InnerAuthenticator extends Authenticator, Writable<Authentication> {

}


//

// Store declarations
const _authentication = sessionWritable<Authentication>("authentication", {});


function clearBrowserStorage(): void {
    localStorage.removeItem(JWT_KEY);
    sessionStorage.removeItem(JWT_KEY);
}

function updateBrowserStorage(jwt: string, rememberMe: boolean): void {
    clearBrowserStorage()
    sessionStorage.setItem(JWT_KEY, jwt);
    if (rememberMe) {
        localStorage.setItem(JWT_KEY, jwt);
    }
}

function createBrowserStoreUpdater(rememberMe: boolean): (jwtInfo: JwtInfo) => SimpleUser {
    return jwtInfo => {
        updateBrowserStorage(jwtInfo.token, rememberMe);
        return jwtInfo.user;
    };
}

function retrieveStoredJWToken(): Optional<string> {
    const inSession: string | null = sessionStorage.getItem(JWT_KEY);
    const jwToken: string | null = inSession ?? localStorage.getItem(JWT_KEY);
    return Optional.ofNullable(jwToken);
}

function loadCurrentUser(p:PromiseHelper<Optional<SimpleUser>>): Promise<void> {
    return securityController.getCurrentUser()
        .then(user => {
            authentication.set({user})
            p.resolve(Optional.of(user))
        })
        .catch(err => {
            authentication.set({});
            p.resolve(Optional.empty())
        })
}


let _promiseHelper: PromiseHelper<Optional<SimpleUser>> | undefined = undefined;


const authentication: InnerAuthenticator = {
    subscribe: _authentication.subscribe,
    set: _authentication.set,
    update: _authentication.update,
    logout: async () => {
        Optional.ofNullable(_promiseHelper).ifPresent(p => p.reject("logout requested"));
        _promiseHelper = undefined;
        clearBrowserStorage();
        authentication.set({});
    },

    signIn: async (login: string, password: string, rememberMe: boolean = false) => {
        authentication.logout();
        const ph: PromiseHelper<Optional<SimpleUser>> = new PromiseHelper();
        _promiseHelper = ph;
        try {
            const result = await securityController.signIn({login, password});
            const token = result.token;
            const user = result.user;
            updateBrowserStorage(token, rememberMe);
            authentication.set({user});
            ph.resolve(Optional.of(user));
        } catch (err) {
            ph.resolve(Optional.empty());
            throw err;
        }
    },
    currentUser: async () => {
        if (_promiseHelper == null) {
            const p = new PromiseHelper<Optional<SimpleUser>>();
            _promiseHelper = p;
            await loadCurrentUser(p);
        }
        return _promiseHelper.promise;
    },
    isAuthenticated: async function() {return await this.currentUser().then(o => o.isPresent());}
}

export {retrieveStoredJWToken, authentication};
export type {Authentication, Authenticator};
