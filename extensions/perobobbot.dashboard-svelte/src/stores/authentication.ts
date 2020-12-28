import {writable} from "svelte/store";
import {Authentication} from "../types/authentication";


export let authentication = writable<Authentication>(Authentication.none());

