<template>
	<div class="container">
		<login-form v-if="isShowLogin" v-model:state="isShowLogin"></login-form>
		<register-form v-else v-model:state="isShowLogin"></register-form>
	</div>
</template>
<script lang="ts" setup>
import { ref, createApp} from 'vue'
import LoginForm from '../../components/LoginForm.vue';
import RegisterForm from '../../components/RegisterForm.vue';

const app = createApp({});
app.component('LoginForm', LoginForm).component('ResgiterForm', RegisterForm)

const isShowLogin = ref(true) //默认:true,显示登录窗口

</script>

<style lang="less" scoped>
.container {
	display: flex;
	justify-content: center;
	background-image: url('../../assets/login-bg-autumn.jpg');
	background-position: center;
	background-size: cover;
	position: fixed;
	left: 0;
	top: 0;
	height: 100%;
	width: 100%;
}
</style>
