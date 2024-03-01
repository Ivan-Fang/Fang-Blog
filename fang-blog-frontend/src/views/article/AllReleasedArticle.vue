<script setup>
    import { ref } from "vue";
    import { getAllReleasedArticleService, getIdCategoryNameMapService } from "@/api/article.js";
    import { getIdUsernameMapService } from "@/api/user.js";
    import { Reading } from "@element-plus/icons-vue";


    /******************************* common variables *******************************/
    const showArticleList = ref(true);


    /******************************* list all released articles *******************************/
    const total = ref();
    const pageNum = ref(1);
    const pageSize = ref(20);
    const articleList = ref([]);
    const userList = ref([]);

    // get id-categoryName map
    const idCategoryNameMap = ref();

    async function getIdCategoryNameMap() {
        let result = await getIdCategoryNameMapService();
        let idCategoryNameList = result.data;
        idCategoryNameMap.value = new Map(Object.entries(idCategoryNameList));
    } 
    getIdCategoryNameMap();

    // get id-username map
    const idUsernameMap = ref();

    async function getIdUsernameMap() {
        let result = await getIdUsernameMapService();
        let idUsernameList = result.data;
        idUsernameMap.value = new Map(Object.entries(idUsernameList));
    }
    getIdUsernameMap();

    // get article list
    function setCategoryName() {
        for (let i = 0; i < articleList.value.length; i++) {
            articleList.value[i].categoryName = idCategoryNameMap.value.get(articleList.value[i].categoryId.toString());
        }
    }

    function setUsername() {
        for (let i = 0; i < articleList.value.length; i++) {
            articleList.value[i].username = idUsernameMap.value.get(articleList.value[i].createUser.toString());
        }
    }

    async function getAllReleasedArticle(pageNum, pageSize) {
        let result = await getAllReleasedArticleService(pageNum, pageSize);
        total.value = result.data.total;
        articleList.value = result.data.items;
        setCategoryName();
        setUsername();
    }

    getAllReleasedArticle(pageNum.value, pageSize.value);

    // pagination
    function handleSizeChange() {
        getAllReleasedArticle(pageNum.value, pageSize.value);
    }

    function handleCurrentChange() {
        getAllReleasedArticle(pageNum.value, pageSize.value);
    }
    
    /******************************* show specified article *******************************/
    const articleForm = ref({
        coverImg: "",
        username: "",
        title: "",
        content: "",
        updateTime: ""
    });

    function showSpecifiedArticleCard(row) {
        showArticleList.value = false;
        articleForm.value.coverImg = row.coverImg;
        articleForm.value.username = row.username;
        articleForm.value.title = row.title;
        articleForm.value.content = row.content;
        articleForm.value.updateTime = row.updateTime;
    }

    function showAllArticleCard() {
        showArticleList.value = true;
    }

</script>

<template>
    <!-- list all released articles -->
    <el-card v-if="showArticleList">
        <el-container>
            <el-header>
                Come and see what other people are doing.
            </el-header>
            <el-main>
                <el-table :data="articleList">
                    <el-table-column label="Title" prop="title" />
                    <el-table-column label="Category" prop="categoryName" />
                    <el-table-column label="Author" prop="username"/>
                    <el-table-column label="Update Time" prop="updateTime"/>
                    <el-table-column label="Operation">
                        <template #default="{ row }">
                            <el-button type="primary" :icon="Reading" @click="showSpecifiedArticleCard(row)">Read</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination
                    v-model:current-page="pageNum"
                    v-model:page-size="pageSize"
                    :page-sizes="[3, 5, 10, 20]"
                    :background="background"
                    layout="total, jumper, sizes, prev, pager, next"
                    :total="total"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                />
            </el-main>
        </el-container>
    </el-card>
    <!-- show specified article -->
    <el-card v-else>
        <template #header>
            <el-row>
                <div style="width: 150px; height: 150px; margin-right: 20px;">
                    <img :src="articleForm.coverImg" style="width: 100%; height: 100%;"/>
                </div>
                <div>
                    <div style="height: 75%; display: flex; align-items: center;">
                        <span style="font-size: 44px;">{{ articleForm.title }}</span>
                    </div>
                    <div style=" height: 25%;">
                        <span>Created by <strong>{{ articleForm.username }}</strong> at {{ articleForm.updateTime }}</span>
                    </div>
                </div>
            </el-row>
        </template>
        <el-input 
            v-model="articleForm.content" 
            type="textarea" 
            :rows="20"
            resize="none"
            readonly>
        </el-input>
        <template #footer>
            <el-button type="info" @click="showAllArticleCard">Go Back</el-button>
        </template>
    </el-card>
</template>

<style scoped>
    .article-title-bar {
        display: flex;
        justify-content: space-between;
    }

    .el-header {
        display: flex;
        align-items: center;
    }

    .el-card {
        min-height: 100%;
        box-sizing: border-box;
    }

    .el-pagination {
        margin-top: 15px;
        display: flex;
        justify-content: flex-end;
    }

    :deep(.el-textarea__inner) {
        box-shadow: 0 0 0 0px;
    }

    :deep(.el-textarea__inner:hover) {
        box-shadow: 0 0 0 0px;
    }

    :deep(.el-textarea__inner:focus) {
        box-shadow: 0 0 0 0px;
    }
</style>