/** @type {import("snowpack").SnowpackUserConfig } */
module.exports = {
    mount: {
        public: {url: '/', static: true},
        assets: {url: '/assets', static: true},
        "src": {url: '/_dist_'},
    },
    plugins: [
        '@snowpack/plugin-svelte',
        '@snowpack/plugin-dotenv',
        '@snowpack/plugin-typescript',
    ],
    install: [
        /* ... */
    ],
    installOptions: {

        /* ... */
    },
    devOptions: {
        port: 8080,
        open: "none", /* can be default, chrome, brave, firefox or none */
    },
    buildOptions: {
        out: "target/classes/dashboard-svelte/public"
    },
    proxy: {
        "/api": "https://localhost:8443/api",
        /* ... */
    },
    alias: {
        "@optional": "./src/types/optional.ts",
        "@backend": "./src/server",
        "@stores": "./src/stores",
        "@types": "./src/types",
        /* ... */
    },
};
