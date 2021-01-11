import {writable} from "svelte/store";
import type {Writable} from "svelte/types/runtime/store";

declare type Subscriber<T> = (value: T) => void;
/** Unsubscribes from value updates. */
declare type Unsubscriber = () => void;
/** Start and stop notification callbacks. */
declare type StartStopNotifier<T> = (set: Subscriber<T>) => Unsubscriber | void;


export function sessionWritable<T>(name: string, defaultValue: T, start?: StartStopNotifier<T>): Writable<T> {
    const storeName = "store###" + name;
    const val: T = retrieveValueInSession<T>(storeName,defaultValue);

    const store = writable(val, set => start && start(set));
    store.subscribe(a => {
        console.log("STORE VALUE "+a);
        sessionStorage.setItem(storeName, JSON.stringify(a))
    })
    return store;
}

function retrieveValueInSession<T>(name: string,defaultValue:T): T {
    const value = sessionStorage.getItem(name);
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
