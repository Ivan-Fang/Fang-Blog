# Fang Blog
* Fang Blog is a "blog website project". Users can create their own account, publish articles, and read others released articles.
* This project is developed in client-side-render (CSR) mode. The details are as follows.
  * Backend
    * We use `MySQL` to create and manage database.
    * We use `MyBatis` to connect and operate database.
    * We use `SpringBoot 3` to implement backend business, which includes (1) CRUD of users' information and avatar uploading; (2) CRUD of articles; (3) CRUD of article categories.
    * We use `JWT` (Json Web Token) to store the information of current logged-in user.
    * We use `md5` to encrypt users' password.
    * We use `Redis` to store valid JWT. If current user's JWT has expired, the system would ask him to login again.
    * We connect to the bucket in `AWS S3` to store avatars and article covers.
  * Frontend
    * We use `Vue 3` as frontend development framework.
    * We use `Element Plus` as frontend UI framework.
    * We use `Axios` to send ajax request.


# Screenshot
<img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/d6178633-8a31-44c5-b2c9-8f927589ff7d" width="500" height="250" title="Login page">
<img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/566069ef-375d-4642-8658-1d578acad322" width="500" height="250" title="Browse all released articles">
<img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/c3c622e3-81e7-417b-8cf5-65211c4d2de7" width="500" height="280" title="Read a specified article">
<img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/fde72a01-5308-47fa-9eb9-733308cd12f6" width="500" height="280" title="Article management (CRUD, condition search, pagination)">
<img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/d050c188-4c1d-4350-9f1c-a831df983007" width="500" height="250" title="Article modificaation">
<img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/df3a42c4-ecce-4da3-9c64-7d29db8d4bf7" width="500" height="250" title="User infomation modification">


# How to use?
1. Download this project.
2. Open redis-server, and set the port as 6379。
3. Modify BUCKET, REGION, ACCESS_KEY_ID, and SECRET_ACCESS_KEY in fang-blog-backend/src/main/java/com/ivanfang/fangblog/utils/AwsS3Util.java.<br/>
   <img src="https://github.com/Ivan-Fang/Fang-Blog/assets/40261483/c1a76854-5554-402f-94b1-706a06737c2b" width="500" height="250" title="Due to information security issues, I don't give mine here. Please use your own.">
4. Launch backend.
   ```
   cd ./fang-blog-backend
   mvn spring-boot:run
   ```
5. Launch frontend.
   ```
   cd ./fang-blog-frontend
   npm install
   npm run dev
   ```
6. Open the following url.
   ```
   http://localhost:5173/login
   ```
