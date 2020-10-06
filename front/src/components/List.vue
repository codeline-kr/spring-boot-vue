<template>
    <div class="hello">
        <h1>{{ name }}</h1>

        <ul>
            <li v-for="v in test" :key="v.id">{{ v.title }}<button @click="testMethod(v.title)">버튼</button></li>
        </ul>
    </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex';

const { mapState, mapActions } = createNamespacedHelpers('sample');

export default {
    name: 'List',
    data() {
        return {
            name: 'hi! hd',
        };
    },
    computed: {
        ...mapState(['test']),
    },
    methods: {
        ...mapActions(['addTest']),
        testMethod: function(x) {
            this.addTest(x);
        },
    },
};
</script>

<style scoped></style>
