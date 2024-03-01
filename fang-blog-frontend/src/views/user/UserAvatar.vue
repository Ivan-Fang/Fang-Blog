<script setup>
    import { ref } from "vue";
    import { useUserInfoStore } from "@/store/user.js";
    import { useTokenStore } from "@/store/token.js";
    import defaultAvatar from "@/assets/avatar.png";
    import { updateUserAvatarService } from "@/api/user.js";
    import { ElMessage } from "element-plus";

    const userInfoStore = useUserInfoStore();
    const tokenStore = useTokenStore();

    const newUserPic = ref(userInfoStore.userInfo.userPic);

    function handleAvatarSuccess(result) {
        newUserPic.value = result.data;
        ElMessage.success("Picture Upload Successfully");
    }

    async function changeAvatar() {
        await updateUserAvatarService(newUserPic.value);
        userInfoStore.userInfo.userPic = newUserPic.value;
        ElMessage.success("Avatar Changed Successfully");
    } 

</script>

<template>
    <el-card>
        <el-container>
            <el-header>
                Change {{ userInfoStore.userInfo.username }}'s Avatar
            </el-header>
            <el-main>
                <el-upload
                    class="avatar-uploader"
                    :auto-upload="true"
                    action="/api/upload"
                    name="file"
                    :headers="{Authorization: tokenStore.token}"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                >
                    <img :src="newUserPic ? newUserPic : defaultAvatar" class="avatar" />
                </el-upload>
                <el-button 
                    type="primary" 
                    :disabled="newUserPic === userInfoStore.userInfo.userPic" 
                    @click="changeAvatar"
                    style="margin-top: 15px;"
                >
                    Change
                </el-button>
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

    .avatar-uploader .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>

<style>
    .avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
    }

    .avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    text-align: center;
    }
</style>