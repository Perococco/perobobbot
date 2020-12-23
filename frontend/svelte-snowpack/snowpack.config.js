/** @type {import("snowpack").SnowpackUserConfig } */
module.exports = {
  mount: {
    "public": {url: '/', static: true},
    src: {url: '/dist'},
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
    /* ... */
  },
  buildOptions: {
  },
  proxy: {
    "/api":"https://localhost:8443/api",
    /* ... */
  },
  alias: {
    /* ... */
  },
};
