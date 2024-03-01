<script setup>
    import { Document, Grid, Management, Setting, InfoFilled, Avatar, Lock,
             CaretBottom, SwitchButton } from "@element-plus/icons-vue";
    import { ref } from "vue";
    import { ElMessage } from "element-plus";
    import { useRouter } from "vue-router";
    import { useTokenStore } from "@/store/token.js";
    import avatar from "@/assets/avatar.png";
    import { getUserInfoService } from "@/api/user.js";
    import { useUserInfoStore } from "@/store/user.js";


    /****************** username section ******************/
    const userInfoStore = useUserInfoStore();
    const tokenStore = useTokenStore();
    
    async function getUserInfo() {
        let userInfo = await getUserInfoService();
        userInfoStore.setUserInfo(userInfo.data);
    }

    getUserInfo();


    /****************** dropdown section ******************/
    const router = useRouter();

    function handleDropdown(command) {
        if (command === "logOut") {
            userInfoStore.removeUserInfo();
            tokenStore.removeToken();
            router.push("/login");
            ElMessage.success("Logout Success");
        } else {
            router.push("/user/" + command);
        }
    }
</script>

<template>
    <el-container class="sidebar">
        <!-- side bar menu -->
        <el-aside width="200px">
            <div class="sidebar-title">
                <h1>Fang Blog</h1>
            </div>

            <el-menu router>
                <el-menu-item index="/article/allReleased">
                    <el-icon><Document/></el-icon>
                    <span>All Released Articles</span>
                </el-menu-item>

                <el-menu-item index="/article/category">
                    <el-icon><Grid/></el-icon>
                    <span>Article Category</span>
                </el-menu-item>

                <el-menu-item index="/article/management">
                    <el-icon><Management/></el-icon>
                    <span>Article Management</span>
                </el-menu-item>

              <el-sub-menu>
                <!-- title of the sub menu -->
                <template #title>
                    <el-icon><Setting/></el-icon>
                    <span>User Settings</span>
                </template>

                <el-menu-item index="/user/info">
                    <el-icon><InfoFilled/></el-icon>
                    <span>User Info</span>
                </el-menu-item>

                <el-menu-item index="/user/avatar">
                    <el-icon><Avatar/></el-icon>
                    <span>Reset Avatar</span>
                </el-menu-item>

                <el-menu-item index="/user/resetPassword">
                    <el-icon><Lock/></el-icon>
                    <span>Reset Password</span>
                </el-menu-item>
              </el-sub-menu>
            </el-menu>
        </el-aside>

        <el-container>
            <!-- header : username & user menu -->
            <el-header class="header">
                <div>Welcome back, <strong>{{ userInfoStore.userInfo.username }}</strong>.</div>
                <!-- 
                    el-dropdown :
                        @command : hook the function when an "el-dropdown-item" click
                    el-dropdown-item :
                        command : the parameter that is sent to @command
                 -->
                <el-dropdown @command="handleDropdown">
                    <span>
                        <el-avatar :src="userInfoStore.userInfo.userPic ? userInfoStore.userInfo.userPic : avatar"></el-avatar>
                        <el-icon><CaretBottom/></el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item :icon="InfoFilled" command="info">User Info</el-dropdown-item>
                            <el-dropdown-item :icon="Avatar" command="avatar">Reset Avatar</el-dropdown-item>
                            <el-dropdown-item :icon="Lock" command="resetPassword">Reset Password</el-dropdown-item>
                            <el-dropdown-item :icon="SwitchButton" command="logOut">Log Out</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>

            <!-- main content -->
            <el-main class="main">
                <router-view></router-view>
            </el-main>

            <!-- copyright -->
            <el-footer class="footer">
                Fang Blog &copy;2024 Created by Ivan Fang
            </el-footer>
        </el-container>
    </el-container>
</template>

<style scoped>
    .sidebar {
        height: 100vh;
        box-sizing: border-box;
    }

    .sidebar-title {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .main {
        background-color: rgb(240, 240, 240);
    }

    .footer {
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>