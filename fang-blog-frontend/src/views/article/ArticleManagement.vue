<script setup>
    import { Plus, Edit, Delete, Position, MessageBox } from "@element-plus/icons-vue";
    import { ElMessage, ElMessageBox } from "element-plus";
    import { ref } from "vue";
    import { getAllCategoryService, getArticleListService, addArticleService, updateArticleService, deleteArticleService } from "@/api/article.js";
    import { useTokenStore } from "@/store/token";
    

    /********************* searching bar *********************/
    const searchCondition = ref({
        categoryId: null,
        state: null
    });

    const categoryList = ref([]);
    async function getCategoryList() {
        let result = await getAllCategoryService();
        categoryList.value = result.data;
    }
    getCategoryList();

    function resetSearchCondition() {
        searchCondition.value.categoryId = null;
        searchCondition.value.state = null;
    }


    /********************* pagination *********************/
    const currentPage = ref(1);
    const pageSize = ref(20);
    const total = ref(-1);      // set total after calling getArticleList()

    // article table
    const articleList = ref([]);
    async function getArticleList() {
        // get article list
        let result = await getArticleListService(currentPage.value, 
                                                 pageSize.value, 
                                                 searchCondition.value.categoryId, 
                                                 searchCondition.value.state);
        articleList.value = result.data.items;
        total.value = result.data.total;

        // find the category name of each article
        for (let i = 0; i < articleList.value.length; i++) {
            let categoryId = articleList.value[i].categoryId;
            for (let j = 0; j < categoryList.value.length; j++) {
                if (categoryId == categoryList.value[j].id) {
                    articleList.value[i].categoryName = categoryList.value[j].categoryName;
                }
            }
        }
    }
    getArticleList();

    function handleCurrentChange() {
        // handleCurrentChange() would change currentPage automatically, no need to write by ourselves
        getArticleList();
    }

    function handleSizeChange() {
        // handleSizeChange() would change pageSize automatically, no need to write by ourselves
        getArticleList();
    }


    /********************* add article *********************/
    const drawerVisible = ref(false);

    const articleForm = ref();
    function clearArticleForm() {
        articleForm.value = {
            id: null,
            title: "",
            categoryId: null,
            coverImg: "",
            content: "",
            state: ""
        };
    }
    clearArticleForm();

    const labelWidth = ref("70px");

    const tokenStore = useTokenStore();
    const headers = ref({
        Authorization: tokenStore.token
    });

    const articleRule = {
        title: [
            { required: true, message: "please enter article title", trigger: "blur" },
            { min: 1, max: 50, message: "length of title should be 1 to 50"}
        ],
        content: [
            { required: true, message: "please enter article content", trigger: "blur" },
            { min: 1, max: 50000, message: "length of title should be 1 to 50000"}
        ]
    }

    function handleAvatarSuccess(result) {
        articleForm.value.coverImg = result.data;
        ElMessage.success("Cover Upload Success");
        // alert("articleForm.value.coverImg = " + articleForm.value.coverImg);
    }

    function beforeAvatarUpload(rawFile) {
        let allowedType = ["image/jpeg", "image/jpg", "image/png"];
        if (!allowedType.includes(rawFile.type)) {
            ElMessage.error('Cover image must be png, jpg or jpeg format.')
            return false
        } else if (rawFile.size / 1024 / 1024 > 10) {
            ElMessage.error('Cover image size can not exceed 10MB!')
            return false
        }
        return true
    }

    function closeDrawer() {
        clearArticleForm();
        drawerVisible.value = false;
    }

    async function handleAddArticle(state) {
        console.log(articleForm.value);

        // add article
        articleForm.value.state = state;
        await addArticleService(articleForm.value);

        // clear article form and close drawer
        closeDrawer();

        // refresh article list
        getArticleList();

        // show tips
        ElMessage.success("Add Successfully");
    }


    /*********************  edit article *********************/
    const editDialogVisible = ref(false);

    function closeEditDialog() {
        clearArticleForm();
        editDialogVisible.value = false;
    }

    function showEditDialog(row) {
        // set form data to current row's data
        articleForm.value = {
            id: row.id,
            title: row.title,
            categoryId: row.categoryId,
            coverImg: row.coverImg,
            content: row.content,
            state: row.state
        }
        
        // show edit dialog
        editDialogVisible.value = true;
    }

    async function handleEditArticle() {
        await updateArticleService(articleForm.value);
        closeEditDialog();
        getArticleList();
        ElMessage.success("Edit Successfully");
    }


    /*********************  delete article *********************/
    async function handleDeleteArticle(row) {
        ElMessageBox.confirm(
            "Are you sure you want to delete this article?",
            "Warning",
            {
                confirmButtonText: 'Confirm',
                cancelButtonText: 'Cancel',
                type: 'warning',
            }
        ).then(async () => {
            await deleteArticleService(row.id);
            getArticleList();
            ElMessage.success("Delete Successfully");
        }).catch((err) => {
            ElMessage.info("Delete Canceled");
            console.log(err);
        });
    }
</script>

<template>
    <el-card>
        <el-container>
            <!-- Title & Add Article -->
            <el-header>
                <span>Article Management</span>
                <el-button type="primary" :icon="Plus" @click="drawerVisible = true">
                    Add Article
                </el-button>
            </el-header>

            <!-- Searching Bar, Article Table, Pagination -->
            <el-main>
                <!-- Searching Bar -->
                <el-form inline :model="searchCondition">
                    <el-form-item label="Article Category">
                        <el-select v-model="searchCondition.categoryId" clearable style="width: 150px;">
                            <el-option v-for="c in categoryList" 
                                       :key="c.id" 
                                       :label="c.categoryName" 
                                       :value="c.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="Article State">
                        <el-select v-model="searchCondition.state" clearable style="width: 150px;">
                            <el-option label="draft" value="draft"></el-option>
                            <el-option label="released" value="released"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="getArticleList">Search</el-button>
                        <el-button type="default" @click="resetSearchCondition">Reset</el-button>
                    </el-form-item>
                </el-form>

                <!-- Article Table -->
                <el-table :data="articleList">
                    <el-table-column label="Title" prop="title"></el-table-column>
                    <el-table-column label="Category" prop="categoryName"></el-table-column>
                    <el-table-column label="Create Time" prop="createTime"></el-table-column>
                    <el-table-column label="State" prop="state"></el-table-column>
                    <el-table-column label="Operation" width="100px">
                        <template #default="{ row }">
                            <el-button type="primary" :icon="Edit" circle @click="showEditDialog(row)"></el-button>
                            <el-button type="danger" :icon="Delete" circle @click="handleDeleteArticle(row)"></el-button>
                        </template>
                    </el-table-column>
                </el-table>

                <!-- Pagination -->
                <el-pagination
                    v-model:current-page="currentPage"
                    v-model:page-size="pageSize"
                    :page-sizes="[3, 5, 10, 20]"
                    background
                    layout="jumper, total, sizes, prev, pager, next"
                    :total="total"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                />

                <!-- Add Article Drawer -->
                <el-drawer v-model="drawerVisible" title="Add Article" :before-close="closeDrawer">
                    <el-form :model="articleForm" :rules="articleRule">
                        <!-- Title -->
                        <el-form-item label="Title" :label-width="labelWidth" prop="title">
                            <el-input v-model="articleForm.title"></el-input>
                        </el-form-item>
                        <!-- Category -->
                        <el-form-item label="Category" :label-width="labelWidth">
                            <el-select v-model="articleForm.categoryId">
                                <el-option 
                                    v-for="c in categoryList"
                                    :key="c.id"
                                    :label="c.categoryName"
                                    :value="c.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <!-- Cover Upload -->
                        <el-form-item label="Cover" :label-width="labelWidth">
                            <el-upload class="avatar-uploader" :show-file-list="false"
                                :auto-upload="true"
                                action="/api/upload"
                                :headers="headers"
                                name="file"
                                :on-success="handleAvatarSuccess"
                                :before-upload="beforeAvatarUpload"
                            >
                                <img v-if="articleForm.coverImg" :src="articleForm.coverImg" class="avatar" />
                                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                            </el-upload>
                        </el-form-item>
                        <!-- Content -->
                        <el-form-item label="Content" :label-width="labelWidth">
                            <el-input
                                v-model="articleForm.content"
                                :autosize="{ minRows: 4, maxRows: 10 }"
                                type="textarea"
                            />
                        </el-form-item>
                        <!-- Release or Save as Draft -->
                        <el-form-item class="scattered">
                            <el-button type="primary" :icon="Position" @click="handleAddArticle('released')">Release</el-button>
                            <el-button type="info" :icon="MessageBox" @click="handleAddArticle('draft')">Draft</el-button>
                        </el-form-item>
                    </el-form>
                </el-drawer>

                <!-- Edit Article Dialog -->
                <el-dialog v-model="editDialogVisible" title="Edit Article" :before-close="closeEditDialog" top="10vh">
                    <el-form :model="articleForm">
                        <el-form-item label="Title" :label-width="labelWidth">
                            <el-input v-model="articleForm.title" />
                        </el-form-item>
                        <el-form-item label="Category" :label-width="labelWidth">
                            <el-select v-model="articleForm.categoryId">
                                <el-option 
                                    v-for="c in categoryList"
                                    :key="c.id"
                                    :label="c.categoryName"
                                    :value="c.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="State" :label-width="labelWidth">
                            <el-select v-model="articleForm.state">
                                <el-option label="released" value="released"></el-option>
                                <el-option label="draft" value="draft"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="Cover" :label-width="labelWidth">
                            <el-upload 
                                class="avatar-uploader"
                                :auto-upload="true"
                                action="/api/upload"
                                name="file"
                                :headers="headers"
                                :on-success="handleAvatarSuccess"
                                :before-upload="beforeAvatarUpload"
                                :show-file-list="false"
                            >
                                <img v-if="articleForm.coverImg" :src="articleForm.coverImg" class="avatar" />
                                <el-icon v-else class="avatar-uploader-icon"><Plus/></el-icon>
                            </el-upload>
                        </el-form-item>
                        <el-form-item label="Content" :label-width="labelWidth">
                            <el-input
                                v-model="articleForm.content" 
                                type="textarea"
                                :autosize="{ minRows: 4, maxRows: 10 }">
                            </el-input>
                        </el-form-item>
                        <el-form-item class="scattered">
                            <el-button type="primary" @click="handleEditArticle">Confirm</el-button>
                            <el-button type="default" @click="closeEditDialog">Cancel</el-button>
                        </el-form-item>
                    </el-form>
                </el-dialog>
            </el-main>
        </el-container>
    </el-card>
</template>

<style scoped>
    .el-card {
        min-height: 100%;
        box-sizing: border-box;
    }

    .el-header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .el-pagination {
        margin-top: 15px;
        display: flex;
        justify-content: flex-end;
    }

    /* cover upload (copy from official website) */
    .avatar-uploader .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }

    .scattered ::v-deep .el-form-item__content {
		display: flex;
        justify-content: space-around;
	}
</style>

<style>
    /* cover upload (copy from official website) */
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