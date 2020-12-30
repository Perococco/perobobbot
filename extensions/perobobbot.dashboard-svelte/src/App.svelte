<script lang='typescript'>

    import "./GlobalCSS.svelte";
    import MainRouter from "./MainRouter.svelte";

    import {onDestroy, onMount} from 'svelte';
    import * as Authenticator from "./server/authenticator";
    import {Styles} from "./types/styles";

    import {authentication, styles} from "./stores/stores";

    const unsubscriber1 = styles.subscribe(s => updateBackground(s))
    const unsubscriber2 = authentication.subscribe(a => {
        console.log(a.getUser().map(u => u.login).orElse("no user"));
    })


    onMount(() => Authenticator.initialize())
    onDestroy(() => {
        unsubscriber1();
        unsubscriber2();
    });


    function updateBackground(styles: Styles) {
        document.body.style.background = styles.getBackground();
    }


</script>

<div>
    <MainRouter/>
</div>
