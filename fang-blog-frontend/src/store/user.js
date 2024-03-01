import { defineStore } from "pinia";
import { ref } from "vue";

export const useUserInfoStore = defineStore(
    "userInfo",
    () => {
        const userInfo = ref({});

        function setUserInfo(newUserInfo) {
            userInfo.value = newUserInfo;
        }

        function removeUserInfo() {
            userInfo.value = {}
        }

        return { userInfo, setUserInfo, removeUserInfo };
    },
    {
        persist: true
    }
);