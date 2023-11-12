<template>
	<el-container class="layout-container-demo" style="height: 100vh">
		<el-aside width="200px">
			<el-scrollbar>
				<el-menu router default-active="1">

					<el-menu-item index="1" route="/admin-home/unapproveduser"><template #title>
							<el-icon>
								<message />
							</el-icon>
							待审核用户
						</template>
					</el-menu-item>

					<el-menu-item index="2" route="/admin-home/approveduser"><template #title>
							<el-icon>
								<message />
							</el-icon>
							已录入用户
						</template>
					</el-menu-item>

				</el-menu>
			</el-scrollbar>
		</el-aside>

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
						</template>
					</el-dropdown>
					<span>{{ user.full_name }}</span>
				</div>
			</el-header>

			<el-main>

				<el-scrollbar>
					<router-view></router-view>
				</el-scrollbar>

			</el-main>
		</el-container>
	</el-container>
</template>
  
<script lang="ts" setup>
import { reactive } from 'vue'
import { Message, Setting } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { userStore } from '../../store/user'

const router = useRouter()
const store = userStore()

const user = reactive(store.user!)

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

.layout-container-demo .el-aside {
	color: var(--el-text-color-primary);
	background: var(--el-color-primary-light-8);
}

.layout-container-demo .el-menu {
	border-right: none;
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
</style>
  