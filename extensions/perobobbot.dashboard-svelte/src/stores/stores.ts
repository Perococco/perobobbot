import {writable} from "svelte/store";
import {User,Optional} from "../types/types";

let currentUser = writable<Optional<User>>(Optional.empty());

export {currentUser};