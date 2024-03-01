<script setup>
    import { ref } from "vue";
    import { useUserInfoStore } from "@/store/user.js";
    import { updatePasswordService } from "@/api/user.js";
    import { ElMessage } from "element-plus";
    import { useTokenStore } from "@/store/token";
    import { useRouter } from "vue-router";

    const resetPasswordForm = ref();

    function clearResetPasswordForm() {
        resetPasswordForm.value = {
            oldPassword: "",
            newPassword: "",
            rePassword: "",
        };
    }

    clearResetPasswordForm();

    const tokenStore = useTokenStore();
    const router = useRouter();
    async function resetPassword() {
        let params = {
            old_pwd: resetPasswordForm.value.oldPassword,
            new_pwd: resetPasswordForm.value.newPassword,
            re_pwd: resetPasswordForm.value.rePassword
        };
        await updatePasswordService(params, tokenStore.token);

        router.push("/login");
        ElMessage.success("Password reset successfully. Please login again.");
    }

    const labelWidth = ref("140px");
</script>

<template>
    <el-card>
        <el-container>
            <el-header>
                Reset Password
            </el-header>
            <el-main>
                <el-form :model="resetPasswordForm">
                    <el-form-item label="Original Password" :label-width="labelWidth">
                        <el-input v-model="resetPasswordForm.oldPassword"></el-input>
                    </el-form-item>
                    <el-form-item label="New Password" :label-width="labelWidth">
                        <el-input v-model="resetPasswordForm.newPassword"></el-input>
                    </el-form-item>
                    <el-form-item label="New Password Check" :label-width="labelWidth">
                        <el-input v-model="resetPasswordForm.rePassword"></el-input>
                    </el-form-item>
                    <el-form-item :label-width="labelWidth">
                        <el-button type="primary" @click="resetPassword">Reset Password</el-button>
                        <el-button type="default">Cancel</el-button>
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