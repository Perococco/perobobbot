<script lang="typescript">
    import type {ConditionsFailedEvent, WrappedComponent} from "svelte-spa-router";
    import Router, {replace} from "svelte-spa-router";
    import type {RouteDetail} from "svelte-spa-router";
    import {Optional} from "@types/optional";
    import type {RouteUserData} from "./types/routeUserData";
    import {authentication} from "@stores/authentication";
    import {routing, withRequestedRoute} from "./stores/routing";
    import * as Routes from "./route_list";
    import wrap from "svelte-spa-router/wrap";

    const routes = createRoutes();

    async function routeCondition(routeDetails:RouteDetail):Promise<boolean> {
        try {
            return await authentication.isAuthenticated();
        } finally {
            console.groupEnd();
        }
    }

    function createRoutes(): Map<string | RegExp, WrappedComponent> {
        const routes = new Map<string | RegExp, WrappedComponent>();
        routes.set(Routes.WELCOME, wrap({asyncComponent:() => import("./routes/Welcome.svelte")}));
        routes.set(Routes.LOGIN, wrap({asyncComponent:() => import("./routes/Login.svelte")}));

        routes.set("/", wrap(
            {asyncComponent:() => import("./routes/Home.svelte"),
                conditions:[d => routeCondition(d)], userData:{onDeniedRoute:"/welcome"}}
                ));
        routes.set(/^\/home(\/(.*))?/, wrap(
            {asyncComponent:() => import("./routes/Home.svelte"),
                conditions:[d => routeCondition(d)], userData:{onDeniedRoute:"/login"}}
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
