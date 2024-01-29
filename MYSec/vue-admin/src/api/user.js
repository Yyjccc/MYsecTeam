import axios from "../axios.js";



export default {
    login(username, password) {
        return axios.get("/api/data/user/login/" + username + "/" + password, { withCredentials: true })
    },
    getAvastor(id) {
        return axios.get("/upload/user/" + id + "/" + id + ".png")
    }


}