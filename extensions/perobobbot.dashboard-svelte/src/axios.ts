import axios from "axios";
import {retrieveStoredJWToken} from "./stores/authentication";

export function initializeAxiosSecurity(): void {
    axios.interceptors.request.use(
        config => {
            retrieveStoredJWToken().ifPresent(token => {
                config.headers = Object.assign(config.headers || {}, {"Authorization": "Bearer " + token});
            })
            return config;
        },
        err => Promise.reject(err));
}
