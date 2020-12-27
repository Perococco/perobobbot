<script lang='typescript'>

    import {onMount, onDestroy} from 'svelte';
    import Router, {link,replace} from 'svelte-spa-router'
    import {wrap} from 'svelte-spa-router/wrap'
    import * as Store from "./stores/stores";
    import {User, Optional} from './types/types';
    import * as Authenticator from "./authenticator";

    let authenticatedUser: Optional<User> = Optional.empty();

    const unsubscriber = Store.currentUser.subscribe(o => authenticatedUser = o);

    onMount(() => Authenticator.initialize())
    onDestroy(() => unsubscriber())

    const routes = new Map();

    routes.set("/login", wrap({
        asyncComponent: () => import('./Login.svelte'),
    }));
    routes.set(/^\/(home)?/, wrap({
        asyncComponent: () => import('./Home.svelte'),
        userData: {
            onDeniedRoute: "/login"
        },
        conditions: [
            (detail => authenticatedUser.isPresent())
        ]
    }));

    function onRouteDenied(event: object): void {
        Optional.ofNullable(event.detail)
            .map(d => d.userData)
            .map(d => d.onDeniedRoute)
            .ifPresent(r => replace(r))
    }

    function logout():void {
        Authenticator.logout();
    }

</script>

<style>
</style>

<div>
    <div>
        <a href="/login" use:link>Login</a>
        <a href="/home" use:link>Home</a>
        <a href="/logout" on:click|preventDefault={logout}>Logout</a>
    </div>
    <Router {routes} on:conditionsFailed={onRouteDenied}/>
</div>