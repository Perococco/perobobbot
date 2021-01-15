<script lang="ts">
    import type {RouteDetail} from 'svelte-spa-router';
    import {replace} from 'svelte-spa-router';
    import {onMount} from "svelte";
    import {styles} from "../stores/styles";
    import {routing, withoutRequestedRoute} from "../stores/routing";
    import * as Str from "../tools";
    import {Optional} from "../types/optional";
    import {authentication} from "../stores/authentication";
    import {_} from "svelte-i18n"


    let error: string = "";
    let login: string = "";
    let password: string = "";
    let rememberMe: boolean = false;

    $: request = $routing;
    $: passwordInvalid = Str.isBlank(password);
    $: loginInvalid = Str.isBlank(login);
    $: invalid = passwordInvalid || loginInvalid;

    function formRouteFromRouteDetail(routeDetail:RouteDetail):string {
        if (routeDetail.querystring.length == 0) {
            return routeDetail.location;
        }
        return routeDetail.location+"?"+routeDetail.querystring;
    }

    function submitForm(): void {
        const nextRoute = Optional.ofNullable($routing)
            .map(u => u.requestedRoute)
            .map(r => formRouteFromRouteDetail(r))
            .orElse("/home");
        authentication.signIn(login, password, rememberMe)
            .then(() => {
                $routing = withoutRequestedRoute();
                console.log("Next route "+nextRoute)
                replace(nextRoute);
            })
            .catch(err => {
                error = formErrorMessage(err);
            });
    }

    function formErrorMessage(err: object): string {
        const response: any = err.response;
        const status: number = response.status as number;
        if (status == 403) {
            return "Invalid credentials";
        } else if (Math.floor(status / 100) == 4) {
            return "Invalid login/password";
        } else if (Math.floor(status / 100) == 5) {
            return "server error";
        }
        return "error";
    }

    onMount(() => styles.update(g => g.withBackgroundForRoute("/login")));

    function inputChanged() {
        error = "";
    }

</script>

<style>
    @keyframes disableAnime {
        from {opacity: 1;}
        to {opacity: 0.4;}
    }
    @keyframes enableAnime {
        from {opacity: 0.4;}
        to {opacity: 1;}
    }

    .disabled {
        animation: disableAnime 0.5s;
        opacity: 0.4;
    }
    .enabled {
        animation: enableAnime 0.5s;
        opacity: 1;
    }

</style>

<div class="full-screen bg-neutral-200">
    <div class="mycenter flex justify-center">
        <form class="bg-white p-4 shadow-2xl rounded">
            <div class="relative">
                <input class="relative p-1 m-2 bg-neutral-100 shadow-inner" type="text" bind:value={login}
                       placeholder="{$_('login.username.placeholder')}" name="uname" required on:change="{inputChanged}">
                {#if loginInvalid}
                    <div class="absolute top-0 right-2 p-1 2xl:font-bold  text-error-500   rounded-3xl">!</div>
                {/if}
            </div>
            <div class="relative">
                <input class="relative p-1 m-2 bg-neutral-100 shadow-inner" type="password" bind:value={password}
                       placeholder="{$_('login.password.placeholder')}" name="psw" required on:change="{inputChanged}">
                {#if passwordInvalid}
                    <div class="absolute top-0 right-2 2xl:font-bold  text-error-500 p-1  rounded-3xl">!</div>
                {/if}
            </div>
            <div>
                <label>
                    <input class="m-2" type="checkbox" bind:checked={rememberMe} name="remember">{$_('login.Remember_me')}
                </label>
            </div>
            <div class="pt-1 pr-1 flex flex-col items-end">
                <button class="bg-primary-500 text-primary-50 rounded pb-1 pt-1 pl-2 pr-2 shadow-xl" disabled={invalid}
                        class:disabled="{invalid}"
                        class:enabled="{!invalid}"
                        on:click|preventDefault={submitForm} type="submit">{$_('SignIn')}</button>
            </div>
            {#if error !== ""}
            <div class="text-error-600">
                <p>{error}</p>
            </div>
            {/if}
        </form>

    </div>
</div>
