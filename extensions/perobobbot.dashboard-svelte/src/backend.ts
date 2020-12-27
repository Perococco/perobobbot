import axios, { AxiosResponse} from "axios";
import type {User} from "./types/types";

const SIGN_IN_ENDPOINT = "/api/signin";
const CURRENT_USER_ENDPOINT = "/api/user";

export const JWT_TOKEN_KEY = "jwt_token";


export function postSignIn(login: string, password: string): Promise<string> {
    const data = {"login": login, "password": password};
    return axios
        .post<string, AxiosResponse<string>>(SIGN_IN_ENDPOINT, data)
        .then(r => r.data)
}

export function getCurrentUser(): Promise<User> {
    return axios.get<User,AxiosResponse<User>>(CURRENT_USER_ENDPOINT)
        .then(r => r.data);
}





