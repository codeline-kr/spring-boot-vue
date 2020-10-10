import 'core-js/stable';
import 'regenerator-runtime/runtime';

import Vue from 'vue';
import App from './App.vue';
import store from './store';
import router from './router';
import vuetify from './plugins/vuetify';

import axios from 'axios';

Vue.config.productionTip = false;

axios.defaults.baseURL = 'http://127.0.0.1:8080';
axios.defaults.headers.common['Authorization'] = localStorage.getItem('token');

new Vue({
    store,
    router,
    vuetify,
    render: h => h(App),
}).$mount('#app');
