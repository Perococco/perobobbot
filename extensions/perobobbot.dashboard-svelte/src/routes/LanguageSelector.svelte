<script>
    import {authentication} from "../stores/authentication";
    import {onMount} from "svelte";
    import {UserController} from "../server/rest-controller";
    import {botLocale, botLocales} from "../stores/locale-store";

    const userController = new UserController();

    let _locale=$botLocale;

    onMount(() => {
        return botLocale.subscribe(l => _locale = l)
    })

    function onLanguageSelected() {
        const user = $authentication.user;
        if (_locale == null) {
            return;
        }
        if (user != null) {
            userController.updateUser(user.login, {languageTag: _locale.toString()})
                .then(u => botLocale.set(u.locale))
        } else {
            botLocale.set(_locale)
        }
    }

</script>


<label>
    <select bind:value={_locale} on:change={onLanguageSelected}>
        {#each $botLocales as locale}
            <option value={locale}>{locale}</option>
        {/each}
    </select>
</label>
