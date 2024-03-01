# Fang Blog
* Fang Blog 顧名思義是一個「部落格網站」，使用者可以在這裡建立自己的帳號、發表文章，並查閱其他人已發佈的文章。
* 本專案採前後端分離模式開發，詳細說明如下：
  * 後端：
    * 使用 MySQL 創建與管理資料庫。
    * 使用 MyBatis 連結與操作資料庫。
    * 使用 SpringBoot 3 完成後端業務邏輯，包括：（1）使用者基本資料的增刪改查、頭像上傳；（2）文章的增刪改查；（3）文章類別的增刪改查。
    * 使用 JWT（Json Web Token）儲存目前登入的使用者。
    * 使用 md5 對使用者的密碼進行加密。
    * 使用 Redis 存儲有效的 JWT，若 JWT 過期則會要求使用者重新登入。
    * 連結 AWS S3 中的 bucket 以儲存使用者上傳的頭像與文章封面。
  * 前端：
    * 使用 Vue 3 作為前端開發框架。
    * 使用 Element Plus 作為 UI 框架。
    * 使用 Axios 發送 Ajax 請求。


# Screenshot
<img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/d6178633-8a31-44c5-b2c9-8f927589ff7d" width="500" height="250" title="登入頁面">
<img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/566069ef-375d-4642-8658-1d578acad322" width="500" height="250" title="瀏覽所有文章">
<img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/c3c622e3-81e7-417b-8cf5-65211c4d2de7" width="500" height="280" title="查看指定文章">
<img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/fde72a01-5308-47fa-9eb9-733308cd12f6" width="500" height="280" title="文章管理（增刪改查、條件查尋、分頁功能）">
<img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/d050c188-4c1d-4350-9f1c-a831df983007" width="500" height="250" title="文章修改">
<img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/df3a42c4-ecce-4da3-9c64-7d29db8d4bf7" width="500" height="250" title="使用者資料修改">


# 操作說明
1. 下載本專案，執行。
2. 開啟 redis-server，port 設為 6379。
3. 修改 fang-blog-backend/src/main/java/com/ivanfang/fangblog/utils/AwsS3Util.java 檔案，將 aws s3 bucket 的名稱、地區、密鑰修改為自己的。<br/>
   <img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/c1a76854-5554-402f-94b1-706a06737c2b" width="500" height="250" title="因為資安問題，這邊就請你放自己的吧~">
4. 啟動後端
   ```
   cd ./fang-blog-backend
   mvn spring-boot:run
   ```
5. 啟動前端。
   ```
   cd ./fang-blog-frontend
   npm install
   npm run dev
   ```
6. 開啟以下網址。
   ```
   http://localhost:5173/login
   ```
