import request from "@/util/request.js";

export async function registerService(username, password) {
    let urlSearchParams = new URLSearchParams();
    urlSearchParams.append("username", username);
    urlSearchParams.append("password", password);
    return await request.post("/user/register", urlSearchParams);
}

export async function loginService(username, password) {
    let urlSearchParams = new URLSearchParams();
    urlSearchParams.append("username", username);
    urlSearchParams.append("password", password);
    return await request.post("/user/login", urlSearchParams);
}

export async function getUserInfoService() {
    return await request.get("/user/userInfo");
}

export async function updateUserInfoService(user) {
    return await request.put("/user/update", user)
}

export async function updateUserAvatarService(avatarUrl) {
    return await request.patch("/user/updateAvatar?avatarUrl=" + avatarUrl);
}

export async function updatePasswordService(params, jwtToken) {
    return await request.patch("/user/updatePwd", params, { headers: { Authorization: jwtToken } });
}

// get all user's id-username map
export async function getIdUsernameMapService() {
    return await request.get("/user/id/username/map");
}