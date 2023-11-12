import { defineStore } from 'pinia'

interface State {
    user: UserInfo | null
}

export const userStore = defineStore('userId', {
    state: (): State => {
        return {
            user: null,
        }
    },
    persist: {
        key:'userInfo',
    }
})

interface UserInfo {
    first_name: string
    full_name: string
    password: string
    phone: string
    token: string
}