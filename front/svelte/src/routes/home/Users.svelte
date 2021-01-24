<script>
    import {onMount} from "svelte";
    import {User, users} from "../../stores/users";
    import {_} from "svelte-i18n"
    import type {Table} from "../../types/table";
    import type {SimpleUser} from "../../server/security-com";

    let _users:User[] = [];

    onMount(() => {
        users.initialize();
        return users.subscribe(u => {
            _users = u;
        })
    });

    const table: Table<SimpleUser> = {
        columns: [
            {i18nKey: 'Login', getter: u => u.login},
            {i18nKey: 'Roles', getter: u => u.roles},
            {i18nKey: 'Locale', getter: u => u.locale},
            {i18nKey: 'Disabled', getter: u => u.deactivated},
        ]
    };

    function onClick(ev) {
        console.log(ev)
    }


</script>

<style>
    .table {
        display: grid;
        grid-template-columns: repeat(4, 1fr);
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

<div class="p-3">
    <button on:click|preventDefault={onClick}>Toggle Perococco</button>
    <div class="table">

        {#each table.columns as column}
            <span class="header uppercase text-center">{$_(column.i18nKey)}</span>
        {/each}

        {#each _users as user (user.login)}
            {#each table.columns as column}
                <span class="value">{column.getter(user)}</span>
            {/each}
            <!--            <span class="value">{user.login}</span>-->
            <!--            <span class="value">{user.roles}</span>-->
            <!--            <span class="value">{user.language}</span>-->
            <!--            <span class="value">{user.deactivated}</span>-->
        {/each}


    </div>
</div>
