<script lang="typescript">
    import type {ConditionsFailedEvent, WrappedComponent} from "svelte-spa-router";
    import Router, {replace} from "svelte-spa-router";
    import {onDestroy} from "svelte";
    import {Authentication, Optional, UserData} from "./types/types";
    import {authentication} from "./stores/stores";
    import * as Utils from "./route_utils";

    let _authentication = Authentication.none();
    const unsubscriber1 = authentication.subscribe(o => _authentication = o);

    const routes = createRoutes(() => _authentication.isAuthenticated())

    onDestroy(() => {unsubscriber1();});

    function createRoutes(authenticated:()=>boolean):Map<string|RegExp,WrappedComponent> {
        const routes = new Map<string|RegExp,WrappedComponent>();
        routes.set("/welcome", Utils.basicAsync(() => import("./routes/Welcome.svelte")));
        routes.set("/login", Utils.basicAsync(() => import("./routes/Login.svelte")));
        routes.set("/", Utils.securedAsync(() => import("./routes/Home.svelte"),authenticated, "/welcome"))
        routes.set(/^\/home(\/(.*))?/, Utils.securedAsync(() => import("./routes/Home.svelte"),authenticated))
        return routes;
    }

    function onRouteDenied(event: ConditionsFailedEvent): void {
        Optional.ofNullable(event.detail)
            .map(d => d.userData as UserData)
            .map(d => d.onDeniedRoute)
            .ifPresent(r => replace(r))
    }

</script>

<style>
</style>

<Router {routes} on:conditionsFailed={onRouteDenied}/>
