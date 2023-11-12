<template>
	<el-table :data="userList">
		<el-table-column prop="full_name" label="姓名" width="150" />
		<el-table-column prop="phone" label="手机号" width="150" align="center" />
		<el-table-column prop="isBindTOTP" label="无密码登录" align="center">
			<template #default="scope">
				<el-result :icon="scope.row.isBindTOTP != 1 ? 'error' : 'success'" />
			</template>
		</el-table-column>
	</el-table>
</template>
  
<script lang="ts" setup>
import { ref, onMounted, inject } from 'vue'

const api: any = inject('$api')

var userList = ref([])

onMounted(async () => {
	// @ts-ignore
	await api['route/admin'].getAll().then(({ data: res }) => {
		if (res.code === 200) {
			userList.value = res.data
		}
	})
})

</script>
  
<style lang="less" scoped>
.el-result {
	--el-result-padding: 0px;
	--el-result-icon-font-size: 30px;
	--el-result-title-font-size: 20px;
	--el-result-title-margin-top: 20px;
	--el-result-subtitle-margin-top: 10px;
	--el-result-extra-margin-top: 30px;
}
</style>
  