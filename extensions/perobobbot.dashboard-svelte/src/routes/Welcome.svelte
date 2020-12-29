<script lang="ts">
    import {onMount} from "svelte";
    import {push} from "svelte-spa-router";
    import {background} from "../stores/background";

    let style = "border-2 border-black text-center rounded-xl md:text-4xl lg:text-5xl align-middle p-1 shadow-2xl";

    $: streamer =  "bg-white "+style;
    $: viewer =  "bg-white "+style;

    function onViewer(event:object):void {
        console.log("on viewer");
    }
    function onStreamer(event:object):void {
        push("/home");
    }

    onMount(() => background.update(g => g.withBackgroundImage("welcome_bkg.png")));
</script>

<style>
</style>

<div class="full-screen">
    <!--    <div id="header" class="relative h-20 shadow-2xl ">-->
    <!--        <div class="absolute opacity-50 bg-black w-full h-full"></div>-->
    <!--        <div class="relative flex -top-0 justify-end items-center w-auto h-full mx-5">-->
    <!--            <div class="bg-red-50 h-10 w-10"></div>-->
    <!--        </div>-->
    <!--    </div>-->
    <div class="mycenter flex flex-col place-content-evenly">
            <button class={streamer} on:click={onStreamer}>Streamer</button>
            <button class={viewer} on:click={onViewer}>Viewer</button>
    </div>
</div>


