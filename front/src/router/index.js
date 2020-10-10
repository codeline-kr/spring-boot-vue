import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '@/views/Home.vue';
import Manager from '@/views/Manager.vue';

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
    },
    {
        path: '/m',
        name: 'manager',
        component: Manager,
        children: [{ path: '', component: () => import(/* webpackChunkName: "manager" */ '@/views/manager/Dashboard.vue') }],
    },
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
});

export default router;
