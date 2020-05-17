module.exports = {

  // Change build paths to make them Maven compatible
  // see https://cli.vuejs.org/config/
  outputDir: 'target/dist',
  assetsDir: 'static',

  devServer: {
    proxy: {
      '/api': {
        target: 'https://localhost:8443',
        ws: true,
        changeOrigin: true
      }
    }
  },
}
