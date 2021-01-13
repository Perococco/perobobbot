<script lang='typescript'>

    import "./css/GlobalCSS.svelte";
    import "./css/TailwindCSS.svelte";
    import "./css/VarCSS.svelte";
    import MainRouter from "./MainRouter.svelte";

    import {onMount} from 'svelte';
    import {Styles} from "./types/styles";

    import {authentication, styles} from "./stores/stores";
    import {initializeAxiosSecurity} from "./axios";
    import {botLocale} from "./stores/locale-store";
    import {Optional} from "./types/optional";


    onMount(async () => {
        await botLocale.initialize();
        const userUnSubscriber = authentication.subscribe(auth => {
            Optional.of(auth)
                .map(a => a.user)
                .map(a => a.locale)
                .ifPresent(l => botLocale.set(l))
        })
        const styleUnSubscriber = styles.subscribe(s => updateBackground(s))

        initializeAxiosSecurity();
        await authentication.refresh();

        return () => {
            userUnSubscriber();
            styleUnSubscriber();
        }
    });

    function updateBackground(styles: Styles) {
        document.body.style.background = styles.getBackground();
    }


</script>
<div>
    <MainRouter/>
</div>
