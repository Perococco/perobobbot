import axios, {AxiosRequestConfig} from "axios";
import User from "./types/user";
import Optional from "./optional";
import {authentication} from "./stores/stores";


export const jwt_token_key = "jwt_token";

function request_interceptor(config: AxiosRequestConfig): AxiosRequestConfig {
    const jwt_token = sessionStorage.getItem(jwt_token_key);
    if (jwt_token != undefined) {
        Object.defineProperty(config.headers, "Authorization","bearer "+jwt_token);
    }
    return config;
}

export function initialize() {
    axios.interceptors.request.use(request_interceptor);
}

export function authenticate(login: string, password: string): Promise<User> {
    return retrieve_jwt_token(login, password)
        .then(jwtToken => sessionStorage.setItem(jwt_token_key, jwtToken))
        .then(() => retrieve_authenticated_user())
        .then(user => {
            authentication.set(Optional.of(user));
            return user;
        }).catch(err => {
            sessionStorage.removeItem(jwt_token_key);
            authentication.set(Optional.empty());
            throw err;
        })
}

async function retrieve_jwt_token(login: string, password: string): Promise<string> {
    if (login == "admin" && password == "admin") {
        return "adminJWToken";
    } else {
        throw new Error("Authentication failed");
    }
    //todo perform real request to backend
    // const result = await axios.get<string,AxiosResponse<string>>("");
    // return result.data;
}

export async function retrieve_authenticated_user():Promise<User> {
    if (sessionStorage.getItem(jwt_token_key) == "adminJWToken") {
        return new User("admin");
    }
    throw new Error("No user log in");
}


