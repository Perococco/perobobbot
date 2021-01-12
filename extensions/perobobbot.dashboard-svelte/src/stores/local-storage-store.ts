import {writable} from "svelte/store";
import type {Writable} from "svelte/types/runtime/store";

declare type Subscriber<T> = (value: T) => void;
/** Unsubscribes from value updates. */
declare type Unsubscriber = () => void;
/** Start and stop notification callbacks. */
declare type StartStopNotifier<T> = (set: Subscriber<T>) => Unsubscriber | void;


export function localWritable<T>(name: string, defaultValue: T, start?: StartStopNotifier<T>): Writable<T> {
    const storeName = "store###" + name;
    const val: T = retrieveValueInLocal<T>(storeName,defaultValue);

    const store = writable(val, set => start && start(set));
    store.subscribe(a => {
        localStorage.setItem(storeName, JSON.stringify(a))
    })
    return store;
}

function retrieveValueInLocal<T>(name: string,defaultValue:T): T {
    const value = localStorage.getItem(name);
    try {
        if (typeof value === "string") {
            const val: T = JSON.parse(value);
            return val;
        }
    } catch (err) {
        console.error("Could not parse : " + value);
    }
    return defaultValue;
}
