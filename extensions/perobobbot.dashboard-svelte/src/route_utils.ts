import type {AsyncSvelteComponent, WrappedComponent} from "svelte-spa-router";
import {wrap} from "svelte-spa-router/wrap";
import type {UserData} from "./types/userData";

function basicAsync(asyncComponent:AsyncSvelteComponent):WrappedComponent {
    return wrap({
        asyncComponent: asyncComponent,
    })
}

function securedAsync(asyncComponent:AsyncSvelteComponent,
                      authenticated:()=>boolean,
                      fallbackRoute:string = "/login"):WrappedComponent {
    const data:UserData = {
        onDeniedRoute: fallbackRoute
    }
    return wrap({
        asyncComponent: asyncComponent,
        userData: data,
        conditions: [
            (detail => {
                return authenticated()
            })
        ]
    })
}

export {basicAsync,securedAsync}
