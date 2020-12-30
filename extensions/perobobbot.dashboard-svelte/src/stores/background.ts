import {writable} from "svelte/store";
import {Styles} from "../types/styles";

export let background = writable<Styles>(new Styles());

