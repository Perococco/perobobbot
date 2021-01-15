
import type {SimpleUser} from "@backend/security-com";
import {UserController} from "@backend/rest-controller";
import type {Readable} from "svelte/types/runtime/store";


const controller = new UserController();

declare interface UsersStore extends Readable<SimpleUser[]> {

}



