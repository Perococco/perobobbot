import type {User} from "./user";
import {Optional} from "./optional";

export class Authentication {
    private readonly user?:User


    private constructor(user: User|undefined) {
        this.user = user;
    }

    public isAuthenticated():boolean {
        return this.user != undefined;
    }

    public getUser():Optional<User> {
        return Optional.ofNullable(this.user);
    }

    static none():Authentication {
        return new Authentication(undefined);
    }

    static with(user: User) {
        return new Authentication(user);
    }
}