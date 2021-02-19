import type {AxiosRequestConfig, AxiosResponse} from 'axios';
import type {Extension, UpdateUserParameters} from './data-com';
import type {Bot} from './perobobbot-lang';
import type {CreateBotParameters, RestCredentialInfo} from './rest-com';
import type {Credential, JwtInfo, SimpleUser} from './security-com';
import axios from "axios";

export class BotController {
    baseURL: URL;


    public constructor(baseURL: URL = new URL(window.document.URL)) {
        this.baseURL = baseURL;
    }

    public createBot(parameters: CreateBotParameters): Promise<Bot> {
        const url = new URL('/api/bots', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'post',
            url: url.toString(),
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(parameters)
        };
        return axios(config).then(res => res.data);
    }

    public deleteBot(id: string): Promise<AxiosResponse> {
        const url = new URL('/api/bots/' + id + '', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'delete',
            url: url.toString()
        };
        return axios(config);
    }

    public listBots(): Promise<Bot[]> {
        const url = new URL('/api/bots', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'get',
            url: url.toString()
        };
        return axios(config).then(res => res.data);
    }

}

export class CredentialController {
    baseURL: URL;


    public constructor(baseURL: URL = new URL(window.document.URL)) {
        this.baseURL = baseURL;
    }

    public deleteCredential(id: string): Promise<AxiosResponse> {
        const url = new URL('/api/credentials/' + id + '', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'delete',
            url: url.toString()
        };
        return axios(config);
    }

    public getCredential(id: string): Promise<RestCredentialInfo> {
        const url = new URL('/api/credentials/' + id + '', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'get',
            url: url.toString()
        };
        return axios(config).then(res => res.data);
    }

    public getCredentials(): Promise<RestCredentialInfo[]> {
        const url = new URL('/api/credentials', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'get',
            url: url.toString()
        };
        return axios(config).then(res => res.data);
    }

}

export class ExtensionController {
    baseURL: URL;


    public constructor(baseURL: URL = new URL(window.document.URL)) {
        this.baseURL = baseURL;
    }

    public listExtensions(): Promise<Extension[]> {
        const url = new URL('/api/extensions', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'get',
            url: url.toString()
        };
        return axios(config).then(res => res.data);
    }

}

export class I18nController {
    baseURL: URL;


    public constructor(baseURL: URL = new URL(window.document.URL)) {
        this.baseURL = baseURL;
    }

    public getAvailableLanguageTags(): Promise<string[]> {
        const url = new URL('/api/dictionaries', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'get',
            url: url.toString()
        };
        return axios(config).then(res => res.data);
    }

    public getDictionary(languageTag: string): Promise<{ [key: string]: string }> {
        const url = new URL('/api/dictionaries/' + languageTag + '', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'get',
            url: url.toString()
        };
        return axios(config).then(res => res.data);
    }

}

export class SecurityController {
    baseURL: URL;


    public constructor(baseURL: URL = new URL(window.document.URL)) {
        this.baseURL = baseURL;
    }

    public getCurrentUser(): Promise<SimpleUser> {
        const url = new URL('/api/user', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'get',
            url: url.toString()
        };
        return axios(config).then(res => res.data);
    }

    public signIn(credential: Credential): Promise<JwtInfo> {
        const url = new URL('/api/signin', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'post',
            url: url.toString(),
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(credential)
        };
        return axios(config).then(res => res.data);
    }

    public singup(parameters: any): Promise<SimpleUser> {
        const url = new URL('/api/signup', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'post',
            url: url.toString(),
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(parameters)
        };
        return axios(config).then(res => res.data);
    }

}

export class UserController {
    baseURL: URL;


    public constructor(baseURL: URL = new URL(window.document.URL)) {
        this.baseURL = baseURL;
    }

    public createUser(parameters: any): Promise<SimpleUser> {
        const url = new URL('/api/users', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'post',
            url: url.toString(),
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(parameters)
        };
        return axios(config).then(res => res.data);
    }

    public getUserByLogin(login: string): Promise<SimpleUser> {
        const url = new URL('/api/users/' + login + '', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'get',
            url: url.toString()
        };
        return axios(config).then(res => res.data);
    }

    public getUserCredentials(login: string): Promise<RestCredentialInfo[]> {
        const url = new URL('/api/users/' + login + '/credentials', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'get',
            url: url.toString()
        };
        return axios(config).then(res => res.data);
    }

    public listAllUsers(): Promise<SimpleUser[]> {
        const url = new URL('/api/users', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'get',
            url: url.toString()
        };
        return axios(config).then(res => res.data);
    }

    public updateUser(login: string, parameters: UpdateUserParameters): Promise<SimpleUser> {
        const url = new URL('/api/users/' + login + '', this.baseURL);

        const config: AxiosRequestConfig = {
            method: 'patch',
            url: url.toString(),
            headers: {
                'Content-Type': 'application/json'
            },
            data: JSON.stringify(parameters)
        };
        return axios(config).then(res => res.data);
    }

}

