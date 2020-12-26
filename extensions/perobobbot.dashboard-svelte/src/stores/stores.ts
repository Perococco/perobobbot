import {writable} from "svelte/store";
import Optional from "../optional";
import type User from "../types/user";

let authentication = writable<Optional<User>>(Optional.empty());

export {authentication};