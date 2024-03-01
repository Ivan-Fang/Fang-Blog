<script setup>
    import { ref } from "vue";
    import { useUserInfoStore } from "@/store/user.js";
    import { ElMessage, ElMessageBox } from "element-plus";
    import { updateUserInfoService } from "@/api/user.js";

    /****************** get user info ******************/
    const userInfoStore = useUserInfoStore();
    const userInfoForm = ref();

    function resetUserInfoForm() {
        userInfoForm.value = {...userInfoStore.userInfo};
    }
    resetUserInfoForm();

    const labelWidth = ref("80px");

    const userInfoRule = {
        nickname: [
            { required: true, message: "please enter nickname", trigger: "blur" },
            { pattern: /^\S{1,16}$/, message: "nickname must be non-empty character with length 1 to 16" }
        ],
        email: [
            { required: true, message: "please enter email", trigger: "blur" },
            { type: "email", message: "please follow email format", trigger: "blur" }
        ]
    };

    async function modifyUserInfo() {
        ElMessageBox.confirm(
            "Are you sure you want to modify user info?",   // content
            "Warning",                                      // title
            {
                confirmButtonText: 'OK',
                cancelButtonText: 'Cancel',
                type: 'warning',
            }
        ).then(async () => {
            await updateUserInfoService(userInfoForm.value);
            userInfoStore.setUserInfo(userInfoForm.value);
            ElMessage.success("Modify Successfully");
        }).catch(() => {
            ElMessage.info("Modification Canceled");
        });
    }

</script>

<template>
    <el-card>
        <el-container>
            <el-header>
                Modify {{ userInfoStore.userInfo.username }}'s Information
            </el-header>
            <el-main>
                <el-form :model="userInfoForm" :rules="userInfoRule">
                    <el-form-item label="Username" :label-width="labelWidth">
                        <el-input v-model="userInfoForm.username" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="Nickname" prop="nickname" :label-width="labelWidth">
                        <el-input v-model="userInfoForm.nickname"></el-input>
                    </el-form-item>
                    <el-form-item label="Email" prop="email" :label-width="labelWidth">
                        <el-input v-model="userInfoForm.email"></el-input>
                    </el-form-item>
                    <el-form-item :label-width="labelWidth">
                        <el-button type="primary" @click="modifyUserInfo">Submit</el-button>
                        <el-button type="default" @click="resetUserInfoForm">Reset</el-button>
                    </el-form-item>
                </el-form>
            </el-main>
        </el-container>
    </el-card>
</template>

<style scoped>
    .el-header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
    .el-card {
        height: 100%;
        box-sizing: border-box;
    }
</style>