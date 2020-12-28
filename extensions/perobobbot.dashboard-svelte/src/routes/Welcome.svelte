<script lang="ts">
    import {onMount} from "svelte";
    import {background} from "../stores/background";


    onMount(() => background.update(g => g.withBackgroundImage("welcome_bkg.png")));
</script>

<style>
</style>

<div class="main">
</div>