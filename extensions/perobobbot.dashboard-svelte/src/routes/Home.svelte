<script lang="ts">
    import {onMount} from "svelte";
    import {background} from "../stores/background";

    onMount(() => background.update(g => g.withBackgroundForRoute("/home")));
</script>

<style>

</style>

<div class="full-screen">
    <div class="relative w-full">
        <div class="absolute top-0 bg-black bg-opacity-30 h-full w-full"></div>
        <div class="relative top 0">Ah</div>
    </div>
    <p>Secured Component</p>
</div>