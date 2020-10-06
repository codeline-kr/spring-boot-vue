export default {
    namespaced: true,
    state: () => ({
        cur_id: 4,
        test: [
            { id: 1, title: 'a1' },
            { id: 2, title: 'a2' },
            { id: 3, title: 'a3' },
            { id: 4, title: 'a4' },
        ],
    }),
    mutations: {
        addTest(state, payload) {
            state.test.push({
                id: state.cur_id + 1,
                title: payload,
            });

            state.cur_id = state.cur_id + 1;
        },
    },
    actions: {
        addTest({ commit }, payload) {
            commit('addTest', payload);
        },
    },
    getters: {},
};
