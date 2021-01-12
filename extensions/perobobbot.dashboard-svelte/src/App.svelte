<script lang='typescript'>

    import "./css/GlobalCSS.svelte";
    import "./css/TailwindCSS.svelte";
    import "./css/VarCSS.svelte";
    import MainRouter from "./MainRouter.svelte";

    import {onDestroy, onMount} from 'svelte';
    import * as Authenticator from "./server/authenticator";
    import {Styles} from "./types/styles";

    import {styles} from "./stores/stores";
    import {initializeI18n, saveLocale} from "./i18nts";
    import {locale} from "svelte-i18n";


    const localeUnsubscriber = locale.subscribe(l => saveLocale(l))
    const styleUnsubscriber = styles.subscribe(s => updateBackground(s))


    initializeI18n()

    onMount(() => {
        Authenticator.initialize();
    });
    onDestroy(() => {
        localeUnsubscriber()
        styleUnsubscriber();
    });

    function updateBackground(styles: Styles) {
        document.body.style.background = styles.getBackground();
    }


</script>
<div>
    <MainRouter/>
</div>
