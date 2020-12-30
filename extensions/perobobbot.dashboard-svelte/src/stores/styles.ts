import {writable} from "svelte/store";
import {Styles} from "../types/styles";

export let styles = writable<Styles>(new Styles());

