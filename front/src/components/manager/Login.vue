<template>
    <v-container>
        <v-form ref="form" v-model="valid" lazy-validation>
            <v-text-field v-model="id" label="ID" required></v-text-field>

            <v-text-field v-model="password" label="Password" type="password" required></v-text-field>

            <v-btn :disabled="!valid" color="success" @click="login({ id, password })">로그인</v-btn>
        </v-form>
    </v-container>
</template>

<script>
import { createNamespacedHelpers } from 'vuex';

const { mapActions } = createNamespacedHelpers('auth');

export default {
    name: 'ManagerLogin',

    data: () => ({
        valid: false,
        id: '',
        password: '',
    }),

    methods: {
        validate() {
            this.$refs.form.validate();
        },
        ...mapActions(['login']),
    },
};
</script>
