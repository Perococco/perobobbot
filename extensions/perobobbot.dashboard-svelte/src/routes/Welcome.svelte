<script lang="ts">
    import {onMount} from "svelte";
    import {push} from "svelte-spa-router";
    import {styles} from "../stores/styles";

    function onViewer(event:object):void {
        console.log("on viewer");
    }
    function onStreamer(event:object):void {
        push("/home");
    }

    onMount(() => styles.update(g => g.withBackgroundForRoute("/welcome")));
</script>

<style>
</style>

<div class="full-screen bg-neutral-200">
    <div class="mycenter flex flex-col place-content-evenly">
            <button class="align-middle text-center m-4 p-2 bg-white  md:text-4xl lg:text-5xl  shadow-2xl border-2 rounded-md border-neutral-900" on:click={onStreamer}>Streamer</button>
            <button class="align-middle text-center m-4 p-2 bg-white md:text-4xl lg:text-5xl  shadow-2xl border-2 rounded-md border-neutral-900" on:click={onViewer}>Viewer</button>
    </div>
</div>


