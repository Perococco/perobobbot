<script lang="ts">
    import * as Authenticator from "../server/authenticator";
    import {replace} from 'svelte-spa-router';
    import {onMount} from "svelte";
    import {background} from "../stores/background";
    import {debug} from "svelte/internal";

    let error:string = "";
    let login: string = "";
    let password: string = "";
    let rememberMe: boolean = false;

    function submitForm(): void {
        Authenticator.authenticate(login, password, rememberMe)
            .then(() => replace("/home"))
            .catch(err => {
                error = formErrorMessage(err);
            });
    }

    function formErrorMessage(err:object):string {
        const response:any = err.response;
        const status:number = response.status as number;
        if (status == 403) {
            return "Invalid credentials";
        }
        if (Math.floor(status/100) == 5) {
            return "server error";
        }
        return "error";
    }

    onMount(() => background.update(g => g.withBackgroundImage("welcome_bkg.png")));

</script>

<style>

</style>


<div>
    <div class="container flex justify-center mt-40 mx-auto">
        <form class="bg-blue-300 p-4 shadow-2xl">
            <div class="p-3">
                <input type="text" bind:value={login} placeholder="Enter Username" name="uname" required>
            </div>
            <div class="p-3">
                <input type="password" bind:value={password} placeholder="Enter Password" name="psw" required>
            </div>
            <div class="p-3">
                <button type="submit" on:click|preventDefault={submitForm}>Login</button>
                <label>
                    <input type="checkbox" bind:checked={rememberMe} name="remember"> Remember me
                </label>
            </div>
            {#if error != ""}
                <p>An error occurred : {error}</p>
            {/if}
        </form>

    </div>
</div>
