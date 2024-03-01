<script setup>
    import { onMounted, ref } from "vue";
    import { Plus, Edit, Delete, Warning } from "@element-plus/icons-vue";
    import { getAllCategoryService, addCategoryService, updateCategoryService, deleteCategoryService } from "@/api/article.js";
    import { ElMessage, ElMessageBox } from "element-plus";

    // show category list
    const articleCategoryList = ref();

    async function getAllCategory() {
        const result = await getAllCategoryService();
        articleCategoryList.value = result.data;
    };

    getAllCategory();

    // add new category
    const categoryFormTitle = ref("");
    const categoryForm = ref({
        id: -1,
        categoryName: "",
        categoryAlias: ""
    });
    const dialogVisable = ref(false);

    function showAddDialog() {
        categoryFormTitle.value = "Add Category";
        dialogVisable.value = true;
    }

    function showUpdateDialog(row) {
        categoryFormTitle.value = "Update Category";
        categoryForm.value.id = row.id;
        categoryForm.value.categoryName = row.categoryName;
        categoryForm.value.categoryAlias = row.categoryAlias;
        dialogVisable.value = true;
    }

    function closeDialog() {
        categoryForm.value = {
            id: -1,
            categoryName: "",
            categoryAlias: ""
        };
        dialogVisable.value = false;
    }

    async function addOrUpdateCategory() {
        if (categoryForm.value.id === -1) {    // add a category
            await addCategoryService(categoryForm.value.categoryName, categoryForm.value.categoryAlias);
            ElMessage.success("Category Added Successfully");
        } else {                               // update a category
            await updateCategoryService(categoryForm.value.id,
                                        categoryForm.value.categoryName, 
                                        categoryForm.value.categoryAlias);
            ElMessage.success("Category Updated Successfully");
        }
        
        // refresh category list
        getAllCategory();

        // clear and close dialog
        closeDialog();
    }

    const categoryRule = {
        categoryName: [
            { required: true, message: 'please enter category name', trigger: 'blur' }
        ],
        categoryAlias: [
            { required: true, message: 'please enter category alias', trigger: 'blur' }
        ]
    };

    // delete category
    async function deleteCategory(row) {
        ElMessageBox.confirm(
            "Are you sure you want to delete category \"" + row.categoryName + "\"?",    // content
            "Warning",                                                                   // title
            {
                confirmButtonText: 'OK',
                cancelButtonText: 'Cancel',
                type: 'warning',
            }
        ).then(async () => {
            await deleteCategoryService(row.id);
            ElMessage.success("Delete Successfully");
            getAllCategory();
        }).catch(() => {
            ElMessage.info("Delete Canceled");
        });
    }
</script>

<template>
    <el-card>
        <el-container>
            <!-- Title & Add Category -->
            <el-header>
                <span>Article Category</span>
                <el-button type="primary" :icon="Plus" @click="showAddDialog">
                    Add Category
                </el-button>
            </el-header>

            <!-- Article Category Table -->
            <el-footer>
                <el-table :data="articleCategoryList">
                    <el-table-column label="Index" type="index" width="100px"></el-table-column>
                    <el-table-column label="Category Name" prop="categoryName"></el-table-column>
                    <el-table-column label="Category Alias" prop="categoryAlias"></el-table-column>
                    <el-table-column label="Operation" width="100px">
                        <template #default="{ row }">
                            <el-button type="primary" :icon="Edit" circle @click="showUpdateDialog(row)"></el-button>
                            <el-button type="danger" :icon="Delete" circle @click="deleteCategory(row)"></el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-footer>
        </el-container>

        <!-- Add/Update Category Dialog -->
        <el-dialog v-model="dialogVisable" :title="categoryFormTitle" width="30%">
            <el-form :model="categoryForm" :rules="categoryRule">
              <el-form-item label="Category Name" prop="categoryName">
                <el-input v-model="categoryForm.categoryName"></el-input>
              </el-form-item>
              <el-form-item label="Category Alias" prop="categoryAlias">
                <el-input v-model="categoryForm.categoryAlias"></el-input>
              </el-form-item>
              <el-form-item>
                <span>
                    <el-button type="primary" @click="addOrUpdateCategory">Confirm</el-button>
                    <el-button type="default" @click="closeDialog">Cancel</el-button>
                </span>
              </el-form-item>
            </el-form>
        </el-dialog>
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

    /* .element-right {
        display: flex;
        justify-content: flex-end;
    } */
</style>