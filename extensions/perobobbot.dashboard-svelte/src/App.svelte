<script lang='typescript'>

    import "./css/GlobalCSS.svelte";
    import "./css/TailwindCSS.svelte";
    import "./css/VarCSS.svelte";
    import MainRouter from "./MainRouter.svelte";

    import {onDestroy, onMount} from 'svelte';
    import * as Authenticator from "./server/authenticator";
    import {Styles} from "./types/styles";

    import {styles} from "./stores/stores";

    const unsubscriber1 = styles.subscribe(s => updateBackground(s))

    onMount(() => {
        Authenticator.initialize();
    });
    onDestroy(() => {
        unsubscriber1();
    });

    function updateBackground(styles: Styles) {
        document.body.style.background = styles.getBackground();
    }


</script>
<div>
    <MainRouter/>
</div>
