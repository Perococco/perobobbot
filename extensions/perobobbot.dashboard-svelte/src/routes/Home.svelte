<script lang="ts">
    import {onDestroy, onMount} from "svelte";
    import {styles} from "../stores/styles";
    import {authentication} from "../stores/stores";
    import {logout} from "../server/authenticator";
    import {replace} from "svelte-spa-router";
    import {Optional} from "../types/optional";
    import * as Bundle from "../types/authentication";

    let auth:Bundle.Authentication = {};
    const unsub = authentication.subscribe(a => auth = a);

    $: login = Optional.ofNullable(auth.user).map(u => u.login).orElse("?")

    onMount(() => styles.update(g => g.withBackgroundForRoute("/home")));
    onDestroy(unsub);

    function onLogout() {
        logout();
        replace("/welcome");
    }

</script>

<style>

</style>

<div class="full-screen">
    <div class="flex flex-row w-full h-screen">
        <div class="relative bg-neutral-100 top 0 flex flex-col justify-between items-start p-3">
            <div class="text-black text-2xl">{login}</div>
            <button
                    class="inline-block px-6 py-2 text-xs font-medium leading-6 text-center text-white uppercase transition bg-blue-700 rounded shadow ripple hover:shadow-lg hover:bg-blue-800 focus:outline-none"
                    on:click|preventDefault={onLogout}
            >
                logout
            </button>
        </div>
        <div>
            <p>Secured Component</p>
        </div>
    </div>
</div>
