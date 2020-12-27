import * as Backend from "./backend";
import {User, Optional} from "./types/types";
import {authentication} from "./stores/stores";
import axios, {AxiosRequestConfig} from "axios";

export function logout() {
    clearAuthentication()
}


const JWT_TOKEN_KEY = Backend.JWT_TOKEN_KEY;

/**
 * Retrieve the JWT from the web browser storage.
 * Check session first and then local storage
 */
function retrieveStoredJWToken(): Optional<string> {
    const inSession: string | null = sessionStorage.getItem(JWT_TOKEN_KEY);
    const jwToken: string | null = inSession ?? localStorage.getItem(JWT_TOKEN_KEY);
    return Optional.ofNullable(jwToken);
}

/**
 * Remove all authentications from web browser storage
 */
function clearAuthentication(): void {
    localStorage.removeItem(JWT_TOKEN_KEY);
    sessionStorage.removeItem(JWT_TOKEN_KEY);
    authentication.set(Optional.empty());
}

/**
 * @param jwToken the token to store in the session storage
 * @param local if true, the token is store also in the localStorage
 */
function storedJWToken(jwToken: string, local: boolean = false): string {
    if (local) {
        localStorage.setItem(JWT_TOKEN_KEY, jwToken);
    }
    sessionStorage.setItem(JWT_TOKEN_KEY, jwToken);
    return jwToken;
}

/**
 * update the authorisation store with the provided user
 * @param user the provided user
 */
function updateAuthorisationStore(user: User): User {
    authentication.set(Optional.of(user));
    return user;
}


/**
 * Add jwt token in header in each request for Spring authentication by the JwtAuthenticationFilter
 */
export function initialize() {
    axios.interceptors.request.use(
        config => {
            retrieveStoredJWToken().ifPresent(token => {
                config.headers = Object.assign(config.headers || {}, {"Authorization": "Bearer " + token});
            })
            return config;
        },
        err => Promise.reject(err));
}

/**
 * Perform the authentication (retrieve the jwt token, get the user information store the token in web browser storage)
 * @param login the login
 * @param password the password
 */
export function authenticate(login: string, password: string): Promise<User> {
    clearAuthentication();
    return Backend.postSignIn(login, password)
        .then(storedJWToken)
        .then(Backend.getCurrentUser)
        .then(updateAuthorisationStore)
        .catch(err => {
            clearAuthentication();
            throw err;
        })
}

