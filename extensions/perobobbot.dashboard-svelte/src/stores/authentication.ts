import {sessionWritable} from "./session-storage-store";
import type {Writable} from "svelte/types/runtime/store";
import {SecurityController} from "@backend/rest-controller";
import type {JwtInfo, SimpleUser} from "@backend/security-com";
import {Optional} from "../types/optional";

const JWT_KEY = "jwt_token";
const securityController = new SecurityController();

//Type declarations

declare interface Authentication {
    user?: SimpleUser;
}

declare interface Authenticator extends Writable<Authentication> {
    logout: () => void;
    signIn: (login: string, password: string, rememberMe: boolean) => Promise<void>;
    refresh: () => Promise<SimpleUser | undefined>;
    isAuthenticated: () => boolean;

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

let local: Authentication = {}

const authentication: Authenticator = {
    subscribe: _authentication.subscribe,
    set: auth => {
        local = auth;
        _authentication.set(auth);
    },
    update: u => {
        local = u(local);
        _authentication.set(local);
    },

    logout: async () => {
        clearBrowserStorage();
        authentication.set({});
    },

    signIn: async (login: string, password: string, rememberMe: boolean = false) => {
        const updateBrowserStorage = createBrowserStoreUpdater(rememberMe);
        authentication.logout();
        return await securityController.signIn({login, password})
            .then(updateBrowserStorage)
            .then(user => authentication.set({user}));
    },

    refresh: async () => {
        return securityController.getCurrentUser()
            .then(user => {
                authentication.set({user})
                return user;
            })
            .catch(err => {
                authentication.set({});
                return undefined;
            })
    },
    isAuthenticated: () => {
        return local.user != undefined;
    }

}

export {retrieveStoredJWToken, authentication};
export type {Authentication, Authenticator};
