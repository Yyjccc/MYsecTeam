import axios from "../../axios.js";
const basePath = "/api/data/targetDrone"
export default {
    getAll() {
        return axios.get(basePath + "/getAll")
    },
    convert(target) {
        let cnt = JSON.parse(target.solvedUser);
        let wp = JSON.parse(target.wp)
        if (wp == null) {
            wp = []
        }
        if (cnt == null) {
            cnt = []
        }
        return {
            index: target.index,
            id: target.id,
            name: target.name,
            class: target.category,
            solved: cnt.length,
            wp: wp.length,
            Date: target.date
        }
    },
    convertArr(arr) {
        let res = []
        for (let i = 0; i < arr.length; i++) {
            let v = this.convert(arr[i])
            res.push(v);
        }
        return res;
    }


}