import http from "@/utils/http"

const admin = {

	getAll: async () => {
		return await http.get("/admin/getAll")
	},

	getRegister: async () => {
		return await http.get("/admin/getRegister")
	},

	approve: async (uid: any) => {
		return await http.post(`/admin/approve?uid=${uid}`)
	},

}

export default admin