<script lang="typescript">
    import type {ConditionsFailedEvent, WrappedComponent} from "svelte-spa-router";
    import Router, {replace} from "svelte-spa-router";
    import {Optional, UserData} from "./types/types";
    import {authentication} from "./stores/stores";
    import {routing,withoutRequestedRoute,withRequestedRoute} from "./stores/routing";
    import * as Utils from "./route_utils";
    import * as Routes from "./route_list";

    const routes = createRoutes(() => $authentication.user !== undefined)

    function createRoutes(authenticated:()=>boolean):Map<string|RegExp,WrappedComponent> {
        const routes = new Map<string|RegExp,WrappedComponent>();
        routes.set(Routes.WELCOME, Utils.basicAsync(() => import("./routes/Welcome.svelte")));
        routes.set(Routes.LOGIN, Utils.basicAsync(() => import("./routes/Login.svelte")));
        routes.set("/", Utils.securedAsync(() => import("./routes/Home.svelte"),authenticated, "/welcome"))
        routes.set(/^\/home(\/(.*))?/, Utils.securedAsync(() => import("./routes/Home.svelte"),authenticated))
        return routes;
    }

    function onRouteDenied(event: ConditionsFailedEvent): void {
        $routing = withRequestedRoute(event.detail);
        Optional.ofNullable(event.detail)
            .map(d => d.userData as UserData)
            .map(d => d.onDeniedRoute)
            .ifPresent(r => replace(r))
    }

</script>

<style>
</style>

<Router {routes} on:conditionsFailed={onRouteDenied}/>
