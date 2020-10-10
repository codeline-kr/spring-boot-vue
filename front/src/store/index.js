import Vue from 'vue';
import Vuex from 'vuex';
import sample from './sample';
import auth from './auth';

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        auth,
        sample,
    },
});
