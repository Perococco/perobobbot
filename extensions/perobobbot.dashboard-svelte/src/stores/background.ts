import {writable} from "svelte/store";
import {GlobalStyle} from "../types/globalStyle";

export let background = writable<GlobalStyle>(new GlobalStyle());

