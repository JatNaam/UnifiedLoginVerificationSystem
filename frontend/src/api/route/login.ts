import http from "@/utils/http"

const login = {

	loginByPassword: async (user: any) => {
		const { data: res } = await http.post("/login/psw", user)
		return res
	},

	loginByCode: async (phone: any, code: any) => {
		const { data: res } = await http.post(`/login/code?phone=${phone}&code=${code}`)
		return res
	},
}

export default login