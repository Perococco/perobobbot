<script lang='typescript'>
    // import './TailwindStyles.svelte';

    import {onMount, onDestroy} from 'svelte';
    import Router, {link} from 'svelte-spa-router'
    import {wrap} from 'svelte-spa-router/wrap'
    import {push, pop, replace} from 'svelte-spa-router'
    import * as Store from "./stores/stores";
    import {User,Optional} from './types/types';
    import * as Authenticator from "./authenticator";

    let authenticated_user: Optional<User> = Optional.empty();

    const unsubscriber = Store.authentication.subscribe(o => {
            console.log("New user : " + o.map(u => u.login).orElseGet("No user"))
            authenticated_user = o
        }
    );

    onMount(() => Authenticator.initialize())
    onDestroy(unsubscriber)

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
            (detail => authenticated_user.isPresent())
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
        <a on:click|preventDefault={logout}>Logout</a>
    </div>
    <Router {routes} on:conditionsFailed={onRouteDenied}/>
</div>