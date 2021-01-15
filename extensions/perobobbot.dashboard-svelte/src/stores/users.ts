
import type {SimpleUser} from "../server/security-com";
import {UserController} from "../server/rest-controller";
import type {Readable} from "svelte/types/runtime/store";


const controller = new UserController();

declare interface UsersStore extends Readable<SimpleUser[]> {

}



