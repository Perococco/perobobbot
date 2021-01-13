<script lang="ts">
    import {onMount} from "svelte";
    import {styles} from "../stores/styles";
    import {authentication} from "../stores/stores";
    import type {WrappedComponent} from "svelte-spa-router";
    import Router, {push, replace} from "svelte-spa-router";
    import {Optional} from "../types/optional";
    import {RoleKind} from "../server/security-com";
    import * as Routes from "../route_list";
    import * as Utils from "../route_utils";
    import {_} from 'svelte-i18n'
    import LanguageSelector from "./LanguageSelector.svelte";

    $: admin = Optional.ofNullable($authentication.user).map(u => u.roles.includes(RoleKind.ADMIN)).orElse(false)
    $: login = Optional.ofNullable($authentication.user).map(u => u.login).orElse("?")

    onMount(() => styles.update(g => g.withBackgroundForRoute("/home")));
    export let params = {}

    const prefix = Routes.HOME;
    const routes = createRoutes()

    function onLogout() {
        authentication.logout();
        replace("/welcome");
    }

    function createRoutes(): Map<string | RegExp, WrappedComponent> {
        const routes = new Map<string | RegExp, WrappedComponent>();
        routes.set(Routes.REL_USERS, Utils.basicAsync(() => import("./home/Users.svelte")));
        routes.set(Routes.REL_EXTENSIONS, Utils.basicAsync(() => import("./home/Extensions.svelte")));
        routes.set(Routes.REL_CREDENTIALS, Utils.basicAsync(() => import("./home/Credentials.svelte")));
        routes.set(Routes.REL_BOTS, Utils.basicAsync(() => import("./home/Bots.svelte")));
        return routes;
    }

</script>

<style>
    .top-panel {
        box-shadow: 0 5px 5px 0px rgba(0, 0, 0, 0.25);
    }

    .left-panel {
        box-shadow: 5px 6px 5px 0px rgba(0, 0, 0, 0.25);
    }

    .panel:hover {
        background: theme('colors.primary.200');
    }

    .panel {
        padding: 0.4rem;
        border-radius: theme('borderRadius.2xl');
    }
</style>

<div class="flex flex-col w-full h-screen">
    <div class="flex flex-row h-full">
        <div class="bg-neutral-100 top 0 left-panel flex flex-col justify-between items-start p-2">
            <div class="flex flex-col w-full">
                <div class="text-black text-2xl font-bold">{login}</div>

                <div class="h-auto"></div>

                <div class="flex flex-col items-start">
                    <button class="panel" on:click|preventDefault={()=>push(Routes.HOME)}>{$_('Home')}</button>
                    {#if admin}
                        <button class="panel"
                                on:click|preventDefault={() => push(Routes.USERS)}>{$_('Users')}</button>
                        <button class="panel"
                                on:click|preventDefault={() => push(Routes.EXTENSIONS)}>{$_('Extensions')}</button>
                    {/if}
                    <button class="panel"
                            on:click|preventDefault={() => push(Routes.CREDENTIALS)}>{$_('Credentials')}</button>
                    <button class="panel" on:click|preventDefault={() => push(Routes.BOTS)}>{$_('Bots')}</button>
                </div>
            </div>
            <button
                    class="inline-block px-6 py-2 text-xs font-medium leading-6 text-center text-white uppercase transition bg-blue-700 rounded shadow ripple hover:shadow-lg hover:bg-blue-800 focus:outline-none"
                    on:click|preventDefault={onLogout}>
                {$_('Logout')}
            </button>
        </div>
        <div class="h-full w-full">

            <Router {routes} {prefix}/>
        </div>
    </div>
</div>

