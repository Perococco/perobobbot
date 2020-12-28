<script lang='typescript'>

    import {onDestroy, onMount} from 'svelte';
    import {link} from 'svelte-spa-router'
    import * as Authenticator from "./server/authenticator";
    import {background,authentication} from "./stores/stores";
    import MainRouter from "./MainRouter.svelte";
    import {GlobalStyle} from "./types/globalStyle";

    const unsubscriber1 = background.subscribe(bkg => updateBackground(bkg))
    const unsubscriber2 = authentication.subscribe(a => {
        console.log(a.getUser().map(u => u.login).orElse("no user"));
    })


    onMount(() => Authenticator.initialize())
    onDestroy(() => {
        unsubscriber1();
        unsubscriber2();
    });


    function updateBackground(globalStyle: GlobalStyle) {
        document.body.style.background = globalStyle.getBackground();
    }


</script>

<style global>
    @tailwind base;
    @tailwind components;
    @tailwind utilities;

    body {
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
        background-color: red;
    }
    a {
        color: white;
    }
</style>

<div>
    <div>
        <a href="/welcome" use:link>Welcome</a>
        <a href="/login" use:link>Login</a>
        <a href="/home" use:link>Home</a>
        <a href="/logout" on:click|preventDefault={() => Authenticator.logout()}>Logout</a>
    </div>
    <MainRouter/>
</div>