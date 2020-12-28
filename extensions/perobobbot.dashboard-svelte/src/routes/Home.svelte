<script lang="ts">
    import {onMount} from "svelte";
    import {background} from "../stores/background";

    onMount(() => background.update(g => g.withoutBackground()));
</script>

<style>

</style>

<div>
    <p>Secured Component</p>
</div>