<script lang="ts">
    import {onMount, onDestroy} from "svelte";
    import {styles} from "../stores/styles";
    import {authentication} from "../stores/stores";
    import {Authentication} from "../types/authentication";

    let auth = Authentication.none();
    const unsub = authentication.subscribe(a => auth=a);

    $: login = auth.getUser().map(u => u.login).orElse("?")

    onMount(() => styles.update(g => g.withBackgroundForRoute("/home")));
    onDestroy(unsub);
</script>

<style>

</style>

<div class="full-screen">
    <div class="relative w-full">
        <div class="absolute top-0 bg-black bg-opacity-30 h-full w-full"></div>
        <div class="relative top 0 text-white">{login}</div>
    </div>
    <p>Secured Component</p>
</div>
