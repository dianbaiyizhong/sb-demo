<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <h1>App2 组件</h1>
    <router-link to="/Hello">hello</router-link>
    <br>
    <router-link to="/Hi">hi</router-link>
    <hr />
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
