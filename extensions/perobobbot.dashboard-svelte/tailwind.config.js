module.exports = {
  purge: ["./src/**/*.svelte"],
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {
      extend: {
        colors: {
          orange: {
            500: "#ff3e00",
          },
        },
      },
    },
  },
  variants: {
    extend: {},
  },
  plugins: [],
}
