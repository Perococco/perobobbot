const colors = require('tailwindcss/colors');

module.exports = {
  purge: ["./src/**/*.svelte"],
  darkMode: false, // or 'media' or 'class'
  variants: {
    extend: {},
  },
  plugins: [],
  theme: {
    extend: {
      colors: {
        "primary": colors.blue,
        "secondary": colors.fuchsia,
        "neutral": colors.trueGray,
        "ok": colors.green,
        "warn": colors.yellow,
        "error": colors.red
      }
    }
  }
}
