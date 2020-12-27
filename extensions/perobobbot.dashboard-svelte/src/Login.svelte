<script lang="ts">
    import * as Authenticator from "./authenticator";
    import {push, pop, replace} from 'svelte-spa-router';


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


<div class="container">
    <label for="uname"><b>Username</b></label>
    <input type="text" bind:value={login} placeholder="Enter Username" name="uname" required>

    <label for="psw"><b>Password</b></label>
    <input type="password" bind:value={password} placeholder="Enter Password" name="psw" required>

    <button type="submit" on:click|preventDefault={submitForm}>Login</button>
    <label>
        <input type="checkbox" checked="checked" name="remember"> Remember me
    </label>
</div>