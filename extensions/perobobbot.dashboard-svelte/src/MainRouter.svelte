<script lang="typescript">
    import type {ConditionsFailedEvent, WrappedComponent} from "svelte-spa-router";
    import Router, {replace,location} from "svelte-spa-router";
    import {Optional} from "./types/types";
    import type {RouteUserData} from "./types/routeUserData";
    import {authentication} from "./stores/stores";
    import {routing, withoutRequestedRoute, withRequestedRoute} from "./stores/routing";
    import * as Utils from "./route_utils";
    import * as Routes from "./route_list";
    import wrap from "svelte-spa-router/wrap";

    let authenticated;
    $: authenticated = $authentication.user != undefined;

    $: {
        console.group("Authenticated")
        console.log("authenticated : "+authenticated)
        console.groupEnd();
    }

    const routes = createRoutes();

    function createRoutes(): Map<string | RegExp, WrappedComponent> {
        const routes = new Map<string | RegExp, WrappedComponent>();
        routes.set(Routes.WELCOME, wrap({asyncComponent:() => import("./routes/Welcome.svelte")}));
        routes.set(Routes.LOGIN, wrap({asyncComponent:() => import("./routes/Login.svelte")}));

        routes.set("/", wrap(
            {asyncComponent:() => import("./routes/Home.svelte"),
                conditions:[() => authenticated], userData:{onDeniedRoute:"/welcome"}}
                ));
        routes.set(/^\/home(\/(.*))?/, wrap(
            {asyncComponent:() => import("./routes/Home.svelte"),
                conditions:[() => authenticated], userData:{onDeniedRoute:"/login"}}
                ));
        return routes;
    }

    function onRouteDenied(event: ConditionsFailedEvent): void {
        $routing = withRequestedRoute(event.detail);
        Optional.ofNullable(event.detail)
            .map(d => d.userData as RouteUserData)
            .map(d => d.onDeniedRoute)
            .ifPresent(r => replace(r))
    }

</script>

<style>
</style>

<Router {routes} on:conditionsFailed={onRouteDenied}/>
