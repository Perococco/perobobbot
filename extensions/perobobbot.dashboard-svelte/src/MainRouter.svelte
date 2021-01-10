<script lang="typescript">
    import type {ConditionsFailedEvent, WrappedComponent} from "svelte-spa-router";
    import Router, {replace} from "svelte-spa-router";
    import {onDestroy} from "svelte";
    import {Optional, UserData} from "./types/types";
    import * as Bundle from "./types/authentication";
    import {authentication} from "./stores/stores";
    import * as Utils from "./route_utils";

    let _authentication:Bundle.Authentication = $authentication;
    console.log(_authentication);
    const unsubscriber1 = authentication.subscribe(o => _authentication = o);

    const routes = createRoutes(() => _authentication.user !== undefined)

    onDestroy(() => {unsubscriber1();});

    function createRoutes(authenticated:()=>boolean):Map<string|RegExp,WrappedComponent> {
        const routes = new Map<string|RegExp,WrappedComponent>();
        routes.set("/welcome", Utils.securedAsync(() => import("./routes/Welcome.svelte"), () => !authenticated(), "/home"));
        routes.set("/login", Utils.securedAsync(() => import("./routes/Login.svelte"), () => !authenticated(), "/home"));
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
