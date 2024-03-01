import { createRouter, createWebHistory } from "vue-router";

const routes = [
    { path: "/login", component: () => import("@/views/Login.vue") },
    { 
        path: "/", 
        component: () => import("@/views/Layout.vue"),
        redirect: "/article/allReleased", // if a browser access "/", vue will redirect it to "/article/allReleased"
        children: [
            { path: "/article/allReleased", component: () => import("@/views/article/AllReleasedArticle.vue") },
            { path: "/article/category", component: () => import("@/views/article/ArticleCategory.vue") }, 
            { path: "/article/management", component: () => import("@/views/article/ArticleManagement.vue") }, 
            { path: "/user/info", component: () => import("@/views/user/UserInfo.vue") }, 
            { path: "/user/avatar", component: () => import("@/views/user/UserAvatar.vue") }, 
            { path: "/user/resetPassword", component: () => import("@/views/user/UserResetPassword.vue") }
        ]
    }
];

const router = createRouter({
    routes: routes,
    history: createWebHistory()
});

export default router;