import { useCookies } from '@vueuse/integrations/useCookies';
const cookie = useCookies();
export default {
    setJwt(jwt) {
        cookie.set("jwt", jwt)
    },
    set(key, value) {
        cookie.set(key, value);
    },
    get(key) {
        return cookie.get(key)
    },
    remove(key) {
        cookie.remove(key)
    },
    getUserByJwt() {
        const jwtStr = cookie.get("jwt");
        const base64Payload = jwtStr.split('.')[1]
        const jsonPayload = JSON.parse(atob(base64Payload))
        return JSON.parse(jsonPayload.sub);
    }
}