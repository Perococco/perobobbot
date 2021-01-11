import type {SimpleUser} from "../server/security-com";

export declare interface Authentication {
    user?:SimpleUser;
}

export function noAuthentication():Authentication {
    return {};
}

export function userAuthentication(user:SimpleUser):Authentication {
    return {user};
}



