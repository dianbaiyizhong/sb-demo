<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/logo.png">
    <h1>App2 组件</h1>
    <router-link to="/Hello">hello</router-link>
    <br>
    <router-link to="/Hi">hi</router-link>
    <hr />
    <!-- <router-view></router-view> -->
    <div id="my-test-gallery">
      <a v-for="(image, key) in images" :key="key" :href="image.largeURL" :data-pswp-width="image.width"
        :data-pswp-height="image.height" target="_blank" rel="noreferrer">
        <img :src="image.thumbnailURL" alt="" />
      </a>
    </div>
  </div>
</template>

<script>
import PhotoSwipeLightbox from 'photoswipe/lightbox';
import 'photoswipe/style.css';

export default {
  name: 'App',
  data() {
    return {
      images: [
        {
          largeURL:
            'https://cdn.photoswipe.com/photoswipe-demo-images/photos/1/img-2500.jpg',
          thumbnailURL:
            'https://cdn.photoswipe.com/photoswipe-demo-images/photos/1/img-200.jpg',
          width: 1875,
          height: 2500,
        },
        {
          largeURL:
            'https://cdn.photoswipe.com/photoswipe-demo-images/photos/2/img-2500.jpg',
          thumbnailURL:
            'https://cdn.photoswipe.com/photoswipe-demo-images/photos/2/img-200.jpg',
          width: 1669,
          height: 2500,
        },
        {
          largeURL:
            'https://cdn.photoswipe.com/photoswipe-demo-images/photos/3/img-2500.jpg',
          thumbnailURL:
            'https://cdn.photoswipe.com/photoswipe-demo-images/photos/3/img-200.jpg',
          width: 2500,
          height: 1666,
        },
      ],
    }
  },
  mounted() {
    if (!this.lightbox) {
      this.lightbox = new PhotoSwipeLightbox({
        gallery: '#my-test-gallery',
        children: 'a',
        pswpModule: () => import('photoswipe'),
      });
      this.lightbox.init();
    }
  },
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
