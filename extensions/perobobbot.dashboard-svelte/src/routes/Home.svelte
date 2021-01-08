<script lang="ts">
    import {onDestroy, onMount} from "svelte";
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
    <div class="relative w-full ">
        <div class="absolute top-0 bg-black bg-opacity-30 h-full w-full"></div>
        <div class="relative top 0 flex justify-between p-3">
            <div class="text-white text-2xl">{login}</div>
            <button class="inline-block px-6 py-2 text-xs font-medium leading-6 text-center text-white uppercase transition bg-blue-700 rounded shadow ripple hover:shadow-lg hover:bg-blue-800 focus:outline-none">logout</button>
        </div>
    </div>
    <p>Secured Component</p>
</div>
