import {Optional} from "./optional";
import {SimpleUser} from "../server/security-com";

export class Authentication {
    private readonly user?:SimpleUser


    private constructor(user: SimpleUser|undefined) {
        this.user = user;
    }

    public isAuthenticated():boolean {
        return this.user != undefined;
    }

    public getUser():Optional<SimpleUser> {
        return Optional.ofNullable(this.user);
    }

    static none():Authentication {
        return new Authentication(undefined);
    }

    static with(user: SimpleUser) {
        return new Authentication(user);
    }
}
