import type {AsyncSvelteComponent, WrappedComponent} from "svelte-spa-router";
import {wrap} from "svelte-spa-router/wrap";
import type {RouteUserData} from "./types/routeUserData";

function basicAsync(asyncComponent:AsyncSvelteComponent):WrappedComponent {
    return wrap({
        asyncComponent: asyncComponent,
    })
}

function securedAsync(asyncComponent:AsyncSvelteComponent,
                      authenticated:()=>boolean,
                      fallbackRoute:string = "/login"):WrappedComponent {
    const data:RouteUserData = {
        onDeniedRoute: fallbackRoute
    }
    return wrap({
        asyncComponent: asyncComponent,
        userData: data,
        conditions: [
            (detail => {
                console.group("Route check");
                console.log(detail);
                try {
                    return authenticated()
                } finally {
                    console.groupEnd();
                }
            })
        ]
    })
}

export {basicAsync,securedAsync}
