import {getLocaleFromNavigator} from 'svelte-i18n';
import {localWritable} from "./local-storage-store";

export const localeStore = localWritable<string>("locale",getLocaleFromNavigator());


