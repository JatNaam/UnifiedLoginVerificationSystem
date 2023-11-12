<template>
	<el-dialog v-model="dialogFormVisible" title="修改密码" width="30%" center :show-close="firstLogin"
		:close-on-click-modal="firstLogin" :close-on-press-escape="firstLogin">
		<el-form ref="modifyForm" :model="formData" :rules="rules" status-icon>
			<el-form-item label="旧密码" prop="oldPassword">
				<el-input v-model="formData.oldPassword" autocomplete="off" placeholder="请输入旧密码（初始密码为123456）" />
			</el-form-item>
			<el-form-item label="新密码" prop="newPassword">
				<el-input v-model="formData.newPassword" autocomplete="off" placeholder="请输入新的密码" />
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button type="primary" @click="modifyPsw(modifyForm)">
					Confirm
				</el-button>
			</span>
		</template>
	</el-dialog>

	<el-container class="layout-container-demo" style="height: 100vh">
		<el-container>
			<el-header style="text-align: right; font-size: 12px">
				<div class="toolbar">
					<el-dropdown>
						<el-icon style="margin-right: 8px; margin-top: 1px">
							<setting />
						</el-icon>
						<template #dropdown>
							<el-dropdown-menu>
								<el-dropdown-item @click="logout">Log out</el-dropdown-item>
							</el-dropdown-menu>
							<el-dropdown-menu>
								<el-dropdown-item @click="openModifyPsw">修改密码</el-dropdown-item>
							</el-dropdown-menu>
						</template>
					</el-dropdown>
					<span>{{ user.full_name }}</span>
				</div>
			</el-header>

			<el-main style="padding: 2rem;">
				<el-scrollbar>

					<el-descriptions title="" :column="2" border>
						<el-descriptions-item>
							<template #label>
								<div class="cell-item">
									<el-icon>
										<UserFilled />
									</el-icon>
									姓名
								</div>
							</template>
							{{ user.full_name }}
						</el-descriptions-item>
						<el-descriptions-item>
							<template #label>
								<div class="cell-item">
									<el-icon>
										<iphone />
									</el-icon>
									手机号
								</div>
							</template>
							{{ user.phone }}
						</el-descriptions-item>

						<el-descriptions-item width="10rem" align="center" label-align="left">
							<template #label>
								<div class="cell-item">
									<el-icon>
										<tickets />
									</el-icon>
									无密码登录
								</div>
							</template>

							<el-result :icon="bindResult" v-if="!showQrCode">
								<template #extra>
									<el-button type="primary" @click="getQrcode" v-if="isBindTotp !== 1">点击验证</el-button>
								</template>
							</el-result>

							<el-space direction="vertical" v-else>
								<el-skeleton style="width: 240px" :loading="isLoading" animated :throttle="500">
									<template #template>
										<el-skeleton-item variant="image" style="width: 240px; height: 240px" />
										<div style="padding-top: 14px">
											<el-skeleton-item variant="h3" style="width: 50%" />
											<div class="bottom card-header">
												<el-button class="button" @click="cancelBind">取消</el-button>
											</div>
										</div>
									</template>
									<template #default>
										<el-card :body-style="{}">
											<img :src="qrCodeUrl" style="width: 240px; height: 240px" />
											<div>
												<span>请使用Google Authenticator扫码绑定</span>
												<div class="bottom card-header" style="margin-top: 10px;">
													<el-input onkeyup="value=value.replace(/[^\d]/g,'')" minlength="6"
														maxlength="6" v-model="code" placeholder="输入验证码"></el-input>
													<el-button type="primary" class="button"
														@click="bindTOTP">确认</el-button>
													<el-button style="margin-left: 1px;" class="button"
														@click="cancelBind">取消</el-button>
												</div>
											</div>
										</el-card>
									</template>
								</el-skeleton>
							</el-space>

						</el-descriptions-item>
					</el-descriptions>

				</el-scrollbar>
			</el-main>
		</el-container>
	</el-container>
</template>
  
<script lang="ts" setup>
import { ref, reactive, onMounted, computed, inject } from 'vue'
import { Setting } from '@element-plus/icons-vue'
import { FormInstance, FormRules, ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { userStore } from '@/store/user'
const api: any = inject('$api')

const store = userStore()

const firstLogin = ref(false)
const dialogFormVisible = ref(false)

const user = reactive(store.user!)

const code = ref()

const formData = reactive({
	oldPassword: '',
	newPassword: ''
})

const modifyForm = ref<FormInstance>()

const rules = reactive<FormRules<typeof formData>>({
	oldPassword: [
		{
			required: true,
			message: '该项不能为空',
		},
	],
	newPassword: [
		{
			required: true,
			message: '该项不能为空',
		},
	],
})

onMounted(async () => {
	if (user.password == "49,50,51,52,53,54")
		dialogFormVisible.value = true
	// @ts-ignore
	await api['route/user'].getAu(user.phone).then(({ data: res }) => {
		if (res.code === 200) {
			isBindTotp.value = res.data
		}
	})
})

// 未绑定：0，已绑定：1，绑定失败：-1
const isBindTotp = ref(0)
const showQrCode = ref(false)
const bindResult = computed(() => {
	switch (isBindTotp.value) {
		case 0:
			return 'warning'
		case 1:
			return 'success'
		case -1:
			return 'error'
		default:
			return 'warning'
	}
})
const getQrcode = async () => {
	// @ts-ignore
	await api['route/user'].getQrcode(user.phone).then(({ data: res }) => {
		if (res.code === 200) {
			qrCodeUrl.value = res.data
			showQrCode.value = true
		}
	})
}
const cancelBind = () => {
	showQrCode.value = false
}
const bindTOTP = async () => {
	if ((code.value + '').length == 6)
		// @ts-ignore
		await api['route/user'].bindTOTP(user.phone, code.value).then(({ data: res }) => {
			isBindTotp.value = res.data
			showQrCode.value = false
			code.value = ''
		})
	else ElMessage.warning('请输入6位验证码')
}

const qrCodeUrl = ref('')
const isLoading = computed(() => {
	return qrCodeUrl.value === '' ? true : false
})

const modifyPsw = (formEl: FormInstance | undefined) => {
	if (!formEl) return
	formEl.validate(async (valid) => {
		if (valid) {
			if (formData.newPassword == '123456') {
				ElMessage.error('不用使用初始密码作为新密码')
				return
			}
			let asciiStr: string = '';
			for (let i = 0; i < formData.oldPassword.length; i++)
				asciiStr += formData.oldPassword.charCodeAt(i) + ',';
			let entryText = asciiStr.slice(0, -1);
			if (user.password == entryText) {
				user.password = formData.newPassword
				// @ts-ignore
				await api['route/user'].modifyPsw(user).then(({ data: res }) => {
					if (res.code === 200) {
						dialogFormVisible.value = false
						formEl.resetFields()
					}
					else user.password = formData.oldPassword
				})
			} else ElMessage.error('密码错误')
		} else {
			ElMessage.error('表单校验错误')
			return false
		}
	})
}
const openModifyPsw = () => {
	firstLogin.value = true
	dialogFormVisible.value = true
}

const router = useRouter()
const logout = () => {
	router.replace({ name: 'Login' })
}

</script>
  
<style lang="less" scoped>
.layout-container-demo .el-header {
	position: relative;
	background-color: var(--el-color-primary-light-7);
	color: var(--el-text-color-primary);
}

.layout-container-demo .el-main {
	padding: 0;
}

.layout-container-demo .toolbar {
	display: inline-flex;
	align-items: center;
	justify-content: center;
	height: 100%;
	right: 20px;
}

.bottom {
	line-height: 12px;
	display: flex;
	justify-content: center;
}
</style>
  