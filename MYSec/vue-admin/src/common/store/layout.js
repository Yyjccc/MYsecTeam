export default {
    state: {
        isExpand: true
    },
    mutations: {
        setIsExpand(state) {
            state.isExpand = !state.isExpand;
        }
    },
}