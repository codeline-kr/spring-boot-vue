<template>
    <div class="manager">
        <Login v-if="token == null" />

        <router-view v-if="token != null"></router-view>
    </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex';
import Login from '@/components/manager/Login.vue';

const { mapState, mapActions } = createNamespacedHelpers('auth');

export default {
    name: 'Manager',
    components: {
        Login,
    },
    computed: {
        ...mapState(['token']),
    },
    methods: {
        ...mapActions(['init']),
    },
    created() {
        this.init();
    },
};
</script>
