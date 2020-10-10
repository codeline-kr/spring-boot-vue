<template>
    <v-container>
        <v-row class="text-center">
            <v-col cols="12">
                <v-btn color="primary" @click="logout">로그아웃</v-btn>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import { createNamespacedHelpers } from 'vuex';
const { mapActions } = createNamespacedHelpers('auth');

export default {
    name: 'Dashboard',

    data: () => ({}),

    methods: {
        ...mapActions(['logout']),
    },
};
</script>
