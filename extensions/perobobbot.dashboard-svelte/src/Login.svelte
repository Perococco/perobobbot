<script lang="ts">
    import * as Authenticator from "./authenticator";
    import {replace} from 'svelte-spa-router';

    let login: string = "";
    let password: string = "";

    function submitForm(): void {
        Authenticator.authenticate(login, password)
            .then(u => replace("/home"))
            .catch(error => {
                console.log("LOGIN : Authentication failed : " + error)
            });
    }

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
                    <input type="checkbox" checked="checked" name="remember"> Remember me
                </label>
            </div>
        </form>
    </div>
</div>
