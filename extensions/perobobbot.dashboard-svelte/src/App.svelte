<script lang='typescript'>

    import "./GlobalCSS.svelte";

    import {onDestroy, onMount} from 'svelte';
    import {link} from 'svelte-spa-router'
    import * as Authenticator from "./server/authenticator";
    import {background,authentication} from "./stores/stores";
    import MainRouter from "./MainRouter.svelte";
    import {Styles} from "./types/styles";

    const unsubscriber1 = background.subscribe(bkg => updateBackground(bkg))
    const unsubscriber2 = authentication.subscribe(a => {
        console.log(a.getUser().map(u => u.login).orElse("no user"));
    })


    onMount(() => Authenticator.initialize())
    onDestroy(() => {
        unsubscriber1();
        unsubscriber2();
    });


    function updateBackground(globalStyle: Styles) {
        document.body.style.background = globalStyle.getBackground();
    }


</script>

<style global>

    html {
        height: 100vh;
        width: 100vw;
    }

    body {
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
        height: 100vh;
        width: 100vw;
        margin: 0;
    }
</style>

<div>
    <MainRouter/>
</div>