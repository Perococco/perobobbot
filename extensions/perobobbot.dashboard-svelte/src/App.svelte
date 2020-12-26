<script lang='typescript'>
    // import './TailwindStyles.svelte';

    import Router, {link} from 'svelte-spa-router'
    import {wrap} from 'svelte-spa-router/wrap'
    import {authentication} from "./stores/stores";
    import Optional from './optional.ts';
    import type Unsubscriber from 'svelte/store'
    import {onDestroy} from 'svelte';
    import {push, pop, replace} from 'svelte-spa-router'
    import User from "./types/user";

    let authenticated_user: Optional<User> = Optional.empty();

    const unsubscriber: Unsubscriber = authentication.subscribe(o => {
            console.log("New user : " + o.map(u => u.login).orElse("No user"))
            authenticated_user = o
        }
    );

    onDestroy(() => {
        unsubscriber; //TODO does the unsubscription occurs actually
    })


    function check_authentication(details): boolean {
        //si l'utilisateur est authentifié on laisse passer
        //sinon, on verifie l'existence d'un JWT et on l'utilise pour recuperer l'utilisateur
        //sinon, on interdit l'access
        return authenticated_user.isPresent()
    }


    const routes = new Map();

    routes.set("/login", wrap({
        asyncComponent: () => import('./Login.svelte'),
    }));
    routes.set("/warning", wrap({
        asyncComponent: () => import('./Warning.svelte'),
    }));


    routes.set(/^\/(home)?/, wrap({
        asyncComponent: () => import('./Home.svelte'),
        userData: {
            onDenyRoute: "/warning"
        },
        conditions: [
            (detail => check_authentication(detail))
        ]
    }));

    function onRouteDenied(event: object): void {
        Optional.ofNullable(event.detail)
            .map(d => d.userData)
            .map(d => d.onDenyRoute)
            .ifPresent(r => replace(r))
    }

</script>

<style>
</style>

<div>
    <div>
        <a href="/login" use:link>Login</a>
        <a href="/home" use:link>Home</a>
    </div>
    <Router {routes} on:conditionsFailed={onRouteDenied}/>
</div>