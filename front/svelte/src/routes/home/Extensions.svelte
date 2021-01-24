<script>

    import {ExtensionController} from "@backend/rest-controller";
    import {onMount} from "svelte";
    import {_} from "svelte-i18n";
    import {extensions, FrontExtension} from "@stores/extensions";
    import type {Table} from "@types/table";
    import type {Extension} from "@backend/data-com";

    const extensionController = new ExtensionController();

    let _extensions:FrontExtension[] = [];


    onMount(() => {
        extensions.initialize();
        return extensions.subscribe(e => _extensions = e);
    })

    const table: Table<Extension> = {
        columns: [
            {i18nKey: 'Name', getter: u => u.name},
            {i18nKey: 'Disabled', getter: u => !u.activated},
        ]
    };

</script>

<style>
    .table {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        border-top: 1px solid black;
        border-right: 1px solid black;
    }

    .table > span {
        padding: 8px 4px;
        border-left: 1px solid black;
        border-bottom: 1px solid black;
    }

    .header {
        font-weight: bold;
    }
</style>

<div class="p-2">
    <div class="table">

        {#each table.columns as column}
            <span class="header uppercase text-center">{$_(column.i18nKey)}</span>
        {/each}

        {#each _extensions as extension}
            {#each table.columns as column}
                <span class="value">{column.getter(extension)}</span>
            {/each}
            <!--            <span class="value">{user.login}</span>-->
            <!--            <span class="value">{user.roles}</span>-->
            <!--            <span class="value">{user.language}</span>-->
            <!--            <span class="value">{user.deactivated}</span>-->
        {/each}


    </div>
</div>
