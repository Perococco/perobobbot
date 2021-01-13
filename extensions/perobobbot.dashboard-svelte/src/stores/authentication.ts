import {sessionWritable} from "./session-storage-store";
import type {Writable} from "svelte/types/runtime/store";
import {SecurityController} from "../server/rest-controller";
import type {JwtInfo, SimpleUser} from "../server/security-com";
import {Optional} from "../types/optional";

const JWT_KEY = "jwt_token";
const securityController = new SecurityController();

//Type declarations

declare interface Authentication {
    user?: SimpleUser;
}

declare interface Authenticator extends Writable<Authentication> {
    logout: () => void;
    signIn: (login: string, password: string, rememberMe: boolean) => Promise<SimpleUser>;
    refresh: () => Promise<SimpleUser|undefined>;
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

function setAuthenticationStore(user: SimpleUser): SimpleUser {
    _authentication.set({user});
    return user;
}

function retrieveStoredJWToken(): Optional<string> {
    const inSession: string | null = sessionStorage.getItem(JWT_KEY);
    const jwToken: string | null = inSession ?? localStorage.getItem(JWT_KEY);
    return Optional.ofNullable(jwToken);
}

const authentication: Authenticator = {
    subscribe: _authentication.subscribe,
    set: _authentication.set,
    update: _authentication.update,

    logout: async () => {
        clearBrowserStorage();
        authentication.set({});
    },

    signIn: async (login: string, password: string, rememberMe: boolean = false) => {
        const updateBrowserStorage = createBrowserStoreUpdater(rememberMe);
        authentication.logout();
        return securityController.signIn({login, password})
            .then(updateBrowserStorage)
            .then(setAuthenticationStore)
    },

    refresh: async () => {
        return securityController.getCurrentUser()
            .then(setAuthenticationStore)
            .catch(err => {
                authentication.set({});
                return undefined;
            })
    }

}

export {retrieveStoredJWToken,authentication};
export type {Authentication, Authenticator};
