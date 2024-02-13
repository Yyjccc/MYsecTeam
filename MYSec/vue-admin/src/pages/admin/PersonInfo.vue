<template>
    <div>
        INFO
    </div>
</template>

<script>

export default {
    name: "PersonInfo",
    data() {
        return {
            user: {
                id: 0,
                username: "",
                password: "",
                salt: "",
                inner: false,
                email: "",
                studentInfoId: 0,
                manager: true,
                studentInfo: {
                    id: 0,
                    realName: "",
                    age: 0,
                    department: "",
                    marjor: "",
                    dirction: "",
                    qq: 0
                }

            }
        }
    },
    created() {
        console.log();
    },
    methods: {
        load() {

        },
    },
}
</script>