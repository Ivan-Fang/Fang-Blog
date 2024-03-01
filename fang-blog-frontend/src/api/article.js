import request from "@/util/request.js";

/*********************  category *********************/

// get current user's category list
export async function getAllCategoryService() {
    return await request.get("/category");
}

// // get all user's category
// export async function getWholeCategoryService() {
//     return await request.get("/category/all");
// }

// get id-categoryName map
export async function getIdCategoryNameMapService() {
    return await request.get("/category/id/categoryName/map");
}

// add  category
export async function addCategoryService(categoryName, categoryAlias) {
    return await request.post("/category", { categoryName, categoryAlias });
}

// update category
export async function updateCategoryService(id, categoryName, categoryAlias) {
    return await request.put("/category", { id, categoryName, categoryAlias });
}

// delete category
export async function deleteCategoryService(id) {
    return await request.delete("/category?id=" + id);
}

/*********************  article *********************/

// get article list
export async function getArticleListService(pageNum, pageSize, categoryId, state) {
    return await request.get("/article", { params: { pageNum: pageNum, pageSize: pageSize, categoryId: categoryId, state: state } });
}

// get all released article
export async function getAllReleasedArticleService(pageNum, pageSize) {
    return await request.get("/article/allReleased?pageNum=" + pageNum + "&pageSize=" + pageSize);
}

// add article
export async function addArticleService(articleForm) {
    return await request.post("/article", articleForm);
}

// update article
export async function updateArticleService(articleForm) {
    return await request.put("/article", articleForm);
}

// delete article
export async function deleteArticleService(id) {
    return request.delete("/article?id=" + id);
}