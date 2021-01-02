import * as Backend from "./backend";
import {Authentication, Optional} from "../types/types";
import {authentication} from "../stores/authentication";
import axios from "axios";

export function logout() {
    clearAuthentication()
}

export const JWT_KEY = "jwt_token";

/**
 * Retrieve the JWT from the web browser storage.
 * Check session first and then local storage
 */
function retrieveStoredJWToken(): Optional<string> {
    const inSession: string | null = sessionStorage.getItem(JWT_KEY);
    const jwToken: string | null = inSession ?? localStorage.getItem(JWT_KEY);
    return Optional.ofNullable(jwToken);
}

/**
 * Remove all authentications from web browser storage
 */
function clearAuthentication(): void {
    localStorage.removeItem(JWT_KEY);
    sessionStorage.removeItem(JWT_KEY);
    authentication.set(Authentication.none());
}

/**
 * @param jwToken the token to store in the session storage
 * @param local if true, the token is store also in the localStorage
 */
function storedJWToken(jwToken: string, local: boolean = false):void {
    if (local) {
        localStorage.setItem(JWT_KEY, jwToken);
    }
    sessionStorage.setItem(JWT_KEY, jwToken);
}


/**
 * Add jwt token in header in each request for Spring authentication by the JwtAuthenticationFilter
 */
export async function initialize():Promise<void> {
    axios.interceptors.request.use(
        config => {
            retrieveStoredJWToken().ifPresent(token => {
                config.headers = Object.assign(config.headers || {}, {"Authorization": "Bearer " + token});
            })
            return config;
        },
        err => Promise.reject(err));

    await updateAuthenticationStore()
}

/**
 * Perform the authentication (retrieve the jwt token, get the user information store the token in web browser storage)
 * @param login the login
 * @param password the password
 * @param rememberMe if true, the JWT will be saved in the localstorage
 */
export function authenticate(login: string, password: string, rememberMe: boolean = false): Promise<void> {
    clearAuthentication();
    return Backend.postSignIn(login, password)
        .then(jwt  => storedJWToken(jwt,rememberMe))
        .then(() => updateAuthenticationStore())

}

/**
 * update the authorisation store by calling the Backend#getCurrentUser method (that uses
 * any JWT save in storage). If the authentication failed, any JWT will be cleared
 */
function updateAuthenticationStore():Promise<void> {
    return Backend.getCurrentUser()
        .then(user => authentication.set(Authentication.with(user)))
        .catch(err => {
            localStorage.removeItem(JWT_KEY);
            sessionStorage.removeItem(JWT_KEY);
            authentication.set(Authentication.none());
        });
}

