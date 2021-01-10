<script lang="ts">
    import * as Authenticator from "../server/authenticator";
    import {replace} from 'svelte-spa-router';
    import {onMount} from "svelte";
    import {styles} from "../stores/styles";
    import {fade} from "svelte/transition";
    import * as Str from "../tools";


    let error: string = "";
    let login: string = "";
    let password: string = "";
    let rememberMe: boolean = false;

    $: passwordInvalid = Str.isBlank(password);
    $: loginInvalid = Str.isBlank(login);
    $: invalid = passwordInvalid || loginInvalid;

    function submitForm(): void {
        Authenticator.authenticate(login, password, rememberMe)
            .then(() => replace("/home"))
            .catch(err => {
                error = formErrorMessage(err);
            });
    }

    function formErrorMessage(err: object): string {
        console.log(err);
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

</script>

<style>

</style>

<div class="full-screen bg-neutral-200">
    <div class="mycenter flex justify-center">
        <form class="bg-white p-4 shadow-2xl rounded">
            <div class="relative">
                <input class="relative p-1 m-2 bg-neutral-100 shadow-inner" type="text" bind:value={login}
                       placeholder="Enter Username" name="uname" required>
                {#if loginInvalid}
                    <div class="absolute top-0 right-2 2xl:font-bold  text-error-500 p-1  rounded-3xl">!</div>
                {/if}
            </div>
            <div class="relative">
                <input class="relative p-1 m-2 bg-neutral-100 shadow-inner" type="password" bind:value={password}
                       placeholder="Enter Password" name="psw" required>
                {#if passwordInvalid}
                    <div class="absolute top-0 right-2 2xl:font-bold  text-error-500 p-1  rounded-3xl">!</div>
                {/if}
            </div>
            <div class="p-3 flex items-center justify-between">
                <button class="bg-primary-500 text-primary-50 rounded pb-1 pt-1 pl-2 pr-2 shadow-xl" disabled={invalid}
                        on:click|preventDefault={submitForm} type="submit">Login
                </button>
                <label>
                    <input type="checkbox" bind:checked={rememberMe} name="remember"> Remember me
                </label>
            </div>
            {#if error !== ""}
                <p>An error occurred : {error}</p>
            {/if}
        </form>

    </div>
</div>
