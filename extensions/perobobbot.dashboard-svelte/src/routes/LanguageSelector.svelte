<script>
    import {locale, locales} from "svelte-i18n";
    import {authentication} from "../stores/authentication";
    import {onMount} from "svelte";
    import {UserController} from "../server/rest-controller";

    let _locale;

    onMount(() => {
        _locale = $locale;
        console.log("Init locale selector to "+_locale)
    })

    function onLanguageSelected() {
        const user = $authentication.user;
        if (_locale !== undefined && user !== undefined) {
            const userController = new UserController();
            userController.updateUser(user.login, {languageTag: _locale})
                .then(u => locale.set(u.locale))

        }
    }

</script>


<select bind:value={_locale} on:change={onLanguageSelected}>
    {#each $locales as locale}
        <option value={locale}>{locale}</option>
    {/each}
</select>
