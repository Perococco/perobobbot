<script>
    import {UserController} from "../../server/rest-controller";
    import {onMount} from "svelte";

    const userController = new UserController();

    let users = [];

    async function loadAllUsers() {
        users = await userController.listAllUsers()
    }
    onMount(async () => {
        loadAllUsers();
    });

</script>

<ul>
    {#each users as {locale, deactivated, login, roles}, i}
        <li>{login} : {deactivated} {roles}  {locale}</li>
    {/each}
</ul>
