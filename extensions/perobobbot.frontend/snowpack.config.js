/** @type {import("snowpack").SnowpackUserConfig } */
module.exports = {
  mount: {
    public: {url: '/', static: true},
    "js-src": {url: '/dist'},
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
    open:"none", /* can be default, chrome, brave, firefox or none */
  },
  buildOptions: {
    out:"target/classes/frontend/public"
  },
  proxy: {
    "/api":"https://localhost:8443/api",
    /* ... */
  },
  alias: {
    /* ... */
  },
};
