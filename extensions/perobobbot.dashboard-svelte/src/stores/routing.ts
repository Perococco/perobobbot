import {writable} from "svelte/store";
import type {RouteDetail} from "svelte-spa-router";


export interface Routing {
    requestedRoute?:RouteDetail;
}

export function withoutRequestedRoute():Routing {
    return {};
}

export function withRequestedRoute(requestedRoute:RouteDetail):Routing {
    return {requestedRoute};
}

export const routing = writable<Routing>(withoutRequestedRoute());

