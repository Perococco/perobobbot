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
    {#each users as {locale, login, roles}, i}
        <li>{login} : {roles}  {locale}</li>
    {/each}
</ul>
