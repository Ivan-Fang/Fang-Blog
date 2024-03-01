import { useTokenStore } from "@/store/token";
import axios from "axios";
import { ElMessage } from "element-plus";

/* if we don't specify the protocal and host, axios will 
   set it as current frontend's ex. http://localhost:5173 */
const baseURL = "/api";   
const instance = axios.create({baseURL});

// import { useRouter } from "vue-router";
// const router = useRouter();
import router from "@/router";

// automatically (1) check the status code (2) get data (3) deal with error
instance.interceptors.response.use(
    result => {
        if (result.data.code === 0) {
            return result.data;
        } else {
            ElMessage.error(result.data.message ? result.data.message : "Server Error");
            return Promise.reject(result.data);
        }
    },
    err => {
        if (err.response.status === 401) {  // unauthorized error
            ElMessage.error("Please login first.");
            router.push("/login");
        } else {                            // others error
            ElMessage.error(err);
        }
        // ElMessage.error(err);
        return Promise.reject(err);
    }
);

// automatically save jwt token in every request
instance.interceptors.request.use(
    config => {
        const tokenStore = useTokenStore();
        const jwtToken = tokenStore.token;
        if (jwtToken) {
            config.headers.Authorization = jwtToken;
        }
        return config;
    },
    err => {
        ElMessage.error(err);
        return Promise.reject(err);
    }
);

export default instance;