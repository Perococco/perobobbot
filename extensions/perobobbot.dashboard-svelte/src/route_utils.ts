import type {WrappedComponent,AsyncSvelteComponent} from "svelte-spa-router";
import {wrap} from "svelte-spa-router/wrap";
import type {UserData} from "./types/userData";

function basicAsync(asyncComponent:AsyncSvelteComponent):WrappedComponent {
    return wrap({
        asyncComponent: asyncComponent,
    })
}

function securedAsync(asyncComponent:AsyncSvelteComponent,authenticated:()=>boolean):WrappedComponent {
    const data:UserData = {
        onDeniedRoute: "/login"
    }
    return wrap({
        asyncComponent: asyncComponent,
        userData: data,
        conditions: [
            (detail => authenticated())
        ]
    })
}

export {basicAsync,securedAsync}