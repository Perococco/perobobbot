<style global>
    html {
        height: 100vh;
        width: 100vw;
    }

    body {
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
        height: 100vh;
        width: 100vw;
        margin: 0;
    }

    /* Remove outline for non-keyboard :focus */
    .js-focus-visible :focus {
        outline: none;
    }

    /* Optional: Customize .focus-visible */
    .js-focus-visible .focus-visible {
        box-shadow: inset 0 0 3px 2px theme('colors.primary.600');
    }

    .full-screen {
        position: fixed;
        width: 100%;
        height: 100%;
    }
    .mycenter {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%,-50%);
    }

</style>
