import http from "../../utils/http"

const user = {

    register: async (user: any) => {
        await http.post("/user/register", user)
    },

    modifyPsw: async (user: any) => {
        return await http.post("/user/modifyPsw", user)
    },

    getQrcode: async (phone: any) => {
        return await http.get(`/user/qrCode?phone=${phone}`)
    },

    bindTOTP: async (phone: any, code: any) => {
        return await http.post(`/user/bindTOTP?phone=${phone}&code=${code}`)
    },

    getAu: async (phone: any) => {
        return await http.get(`/user/getAu?phone=${phone}`)
    },

}

export default user