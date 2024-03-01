<script setup>
import { ref } from "vue";
import { User, Lock, Position, Right, QuestionFilled } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import axios from "@/util/request.js";
import { registerService, loginService } from "@/api/user.js";
import { useRouter } from "vue-router";
import { useTokenStore } from "@/store/token.js";
import logo from "@/assets/logo.png";


// common components for register and login
let isRegister = ref(false);
const router = useRouter();

function goToLogin() {
	isRegister.value = false;
}

function goToRegister() {
	isRegister.value = true;
}

function clearForm() {
	registerForm.value = {
		username: "",
		password: "",
		password2: ""
	};

	loginForm.value = {
		username: "",
		password: ""
	}
}

// register
let registerForm = ref({
	username: "",
	password: "",
	password2: ""
});

const password2Validator = (rule, value, callback) => {
	if (value === "") {
		return callback(new Error("Please enter password again."));
	} else if (value !== registerForm.value.password) {
		return callback(new Error("The passwords should be the same."));
	} else {
		return callback();
	}
}

let registerRule = {
	username: [
		{ required: true, message: "please input username", trigger: "blur" },
		{ min: 5, max: 16, message: "length of username must be 5 ~ 16", trigger: "blur" }
	],
	password: [
		{ required: true, message: "please input password.", trigger: "blur" },
		{ min: 5, max: 16, message: "length of password must be 5 ~ 16", trigger: "blur" }
	],
	password2: [
		{ validator: password2Validator, trigger: "blur" }
	]
}

async function handleRegister() {
	let result = await registerService(registerForm.value.username, registerForm.value.password);
	ElMessage.success("Register Success");
	clearForm();
	goToLogin();
}

// login
let loginForm = ref({
	username: "",
	password: ""
});

async function handleLogin() {
	// connect to server and login
	let result = await loginService(loginForm.value.username, loginForm.value.password);

	// set jwt token in store object
	const tokenStore = useTokenStore();
	tokenStore.setToken(result.data);

	// show login success message
	ElMessage.success("Login Success");

	// jump page
	router.push("/");
}

function handleForgetPassword() {
	
}

let loginRule = {
	username: [
		{ required: true, message: "please enter username", trigger: "blur" },
		{ min: 5, max: 16, message: "length of username must be 5 ~ 16", trigger: "blur" }
	],
	password: [
		{ required: true, message: "please enter password", trigger: "blur" },
		{ min: 5, max: 16, message: "length of password must be 5 ~ 16", trigger: "blur" }
	]
}
</script>

<template>
	<div class="login-register-form-div">
		<el-card class="login-register-card">
			<img :src="logo" class="center"/>

			<!-- register form -->
			<el-form :model="registerForm" v-if="isRegister" :rules="registerRule">
				<el-form-item>
					<h1>Register</h1>
				</el-form-item>
				<el-form-item prop="username">
					<el-input :prefix-icon="User" v-model="registerForm.username" placeholder="username"></el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input :prefix-icon="Lock" v-model="registerForm.password" placeholder="password"></el-input>
				</el-form-item>
				<el-form-item prop="password2">
					<el-input :prefix-icon="Lock" v-model="registerForm.password2" placeholder="password again"></el-input>
				</el-form-item>
				<el-form-item class="form-button">
					<el-button :icon="Position" type="primary" @click="handleRegister">register</el-button>
					<el-button :icon="Right" type="info" @click="goToLogin(); clearForm()">go to login</el-button>
				</el-form-item>
			</el-form>

			<!-- login form -->
			<el-form :model="loginForm" v-else :rules="loginRule">
				<el-form-item>
					<h1>Login</h1>
				</el-form-item>
				<el-form-item prop="username">
					<el-input v-model="loginForm.username" placeholder="username" :prefix-icon="User"></el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input v-model="loginForm.password" placeholder="password" :prefix-icon="Lock"></el-input>
				</el-form-item>
				<el-form-item class="form-button">
					<el-button :icon="Position" type="primary" @click="handleLogin">login</el-button>
					<!-- <el-button :icon="QuestionFilled" type="danger" @click="handleForgetPassword">forget password</el-button> -->
					<el-button :icon="Right" type="info" @click="goToRegister">go to register</el-button>
				</el-form-item>
			</el-form>
		</el-card>
	</div>
</template>

<style scoped>
	.login-register-form-div {
		height: 100vh;
		display: flex;
		justify-content: center;
		background-color: gray;
		/* align-items: center; */
	}

	.login-register-card {
		margin-top: 150px;
		width: 50%;
		height: fit-content;
		justify-content: center;
	}

	.center {
		display: block;
		margin-left: auto;
		margin-right: auto;
		width: 120px;
	}

	.login-register-form {
		margin-top: 50px;
		width: 50%;
		/* display: flex;
		align-items: center; */
	}

	.form-button ::v-deep .el-form-item__content {
		float: inline-end;
		display: flex;
        justify-content: space-around;
	}
</style>