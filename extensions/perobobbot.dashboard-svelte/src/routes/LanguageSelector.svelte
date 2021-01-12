<script>
    import {locale, locales} from "svelte-i18n";
    import {authentication} from "../stores/authentication";
    import {onDestroy, onMount} from "svelte";
    import {UserController} from "../server/rest-controller";

    let subscription = () =>{}
    let _locale = $locale

    onMount(() => {
        _locale = $locale;
        locale.subscribe(l => _locale = l)
    })
    onDestroy(subscription);

    function onLanguageSelected() {
        const user = $authentication.user;
        if (_locale !== undefined && user !== undefined) {
            const userController = new UserController();
            userController.updateUser(user.login, {languageTag: _locale})
                .then(u => locale.set(u.locale))
        }
    }

</script>


<label>
    <select bind:value={_locale} on:change={onLanguageSelected}>
        {#each $locales as locale}
            <option value={locale}>{locale}</option>
        {/each}
    </select>
</label>
