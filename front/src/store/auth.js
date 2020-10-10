import axios from 'axios';

export default {
    namespaced: true,
    state: () => ({
        token: null,
    }),
    mutations: {
        SET_TOKEN(state, token) {
            state.token = token;
        },
    },
    actions: {
        init({ commit }) {
            commit('SET_TOKEN', localStorage.getItem('token'));
        },
        async login({ commit }, payload) {
            try {
                const result = await axios.post(`/auth/m/login`, payload);
                localStorage.setItem('token', result.data.token);
                commit('SET_TOKEN', result.data.token);
            } catch (err) {
                alert(err);
            }
        },
        logout({ commit }) {
            localStorage.removeItem('token');
            commit('SET_TOKEN', null);
        },
    },
    getters: {},
};
