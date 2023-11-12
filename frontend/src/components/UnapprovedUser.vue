<template>
	<el-table :data="applyInfo">
		<el-table-column prop="applyTime" label="申请日期" width="200" />
		<el-table-column prop="full_name" label="姓名" width="150" />
		<el-table-column prop="phone" label="手机号" width="150" align="center" />
		<el-table-column label="操作" align="center">
			<template #default="scope">
				<el-button type="primary" size="small" @click="aprroved(scope.row.uid)">通过</el-button>
			</template>
		</el-table-column>
	</el-table>
</template>
  
<script lang="ts" setup>
import { ref, onMounted, inject } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const api: any = inject('$api')

const applyInfo = ref([])

onMounted(() => {
	getRegister()
})

const getRegister = async () => {
	// @ts-ignore
	await api['route/admin'].getRegister().then(({ data: res }) => {
		if (res.code === 200) {
			applyInfo.value = res.data
		}
	})
}

const aprroved = (uid: any) => {
	ElMessageBox.confirm(
		'是否通过该申请请求?',
		'Warning',
		{
			confirmButtonText: 'OK',
			cancelButtonText: 'Cancel',
			type: 'warning',
		}
	).then(async () => {
		// @ts-ignore
		await api['route/admin'].approve(uid).then(({ data: res }) => {
			if (res.code === 200) {
				getRegister()
				ElMessage({
					type: 'success',
					message: `通过${res.data}的申请`,
				})
			}
		})
	}).catch(() => {
		ElMessage({
			type: 'info',
			message: '取消',
		})
	})
}

</script>
<style lang="less" scoped></style>
  