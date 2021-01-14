<script>
    import {UserController} from "../../server/rest-controller";
    import {onMount} from "svelte";
    import type {Table} from "../../types/table";
    import type {SimpleUser} from "../../server/security-com";
    import {_} from "svelte-i18n"

    const userController = new UserController();

    let users = [];

    async function loadAllUsers():Promise<void> {
        users = await userController.listAllUsers()
    }

    onMount(async () => {
        loadAllUsers();
    });

    const table: Table<SimpleUser> = {
        columns: [
            {i18nKey: 'Login', getter: u => u.login},
            {i18nKey: 'Roles', getter: u => u.roles},
            {i18nKey: 'Locale', getter: u => u.locale},
            {i18nKey: 'Disabled', getter: u => u.deactivated},
        ]
    };

    function onClick(): void {
        const index = users.findIndex(u => u.login === "perococco")
        if (index >= 0) {
            users[index].deactivated = !users[index].deactivated;
            users[index] = users[index]
        }
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

        {#each users as user}
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
