import {writable} from "svelte/store";
import {User,Optional} from "../types/types";

let authentication = writable<Optional<User>>(Optional.empty());

export {authentication};