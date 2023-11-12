<template>
    <div class="login">

        <span class="title">统一身份认证</span>

        <div class="login-form" v-if="!phoneMode">
            <el-form ref="loginForm" :model="user" :rules="rules" status-icon>
                <el-form-item prop="type">
                    <el-select v-model.number="user.type" placeholder="请选择用户类型">
                        <el-option v-for="item in maps" :key="item.key" :label="item.label" :value="item.key">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="phone">
                    <el-input type="text" autocomplete="off" placeholder="手机号" v-model.number="user.phone">
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" autocomplete="off" placeholder="密码" show-password
                        v-model.trim="user.password">
                    </el-input>
                </el-form-item>
            </el-form>

            <span class="go" style="margin-right: 10%;" @click="login(loginForm)">登录</span>
            <span class="go" @click="gotoRegister">注册</span>
        </div>

        <div class="phone-verificate" v-else>
            <el-form ref="phoneForm" :model="user" :rules="rules" status-icon>
                <el-form-item prop="phone">
                    <el-input type="text" autocomplete="off" placeholder="手机号" v-model.number="user.phone"></el-input>
                </el-form-item>
                <el-form-item prop="code">
                    <el-input type="text" autocomplete="off" placeholder="验证码" v-model.number="user.code">
                    </el-input>
                </el-form-item>
            </el-form>

            <span class="go" @click="login(phoneForm)">登录</span>

            <el-icon color="#3d3d3d" :size="30" style="position: absolute;top:10px;left:20px;cursor: pointer" @click="back">
                <Back />
            </el-icon>
        </div>

    </div>
</template>
<script lang="ts" setup>
import { ref, reactive, inject } from 'vue'
import { FormInstance, FormRules, ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { userStore } from '@/store/user'

const store = userStore()
const router = useRouter()
const api: any = inject('$api')

const props = defineProps({
    state: { type: Boolean, required: true }
})

const maps = reactive([{ key: 1, label: '管理员' }, { key: 2, label: '用户' }])

const user = reactive(localStorage.getItem('loginInfo') ?
    JSON.parse(localStorage.getItem('loginInfo')!) : { type: '', phone: '', password: '', code: '' })

const phoneMode = ref(false)

const loginForm = ref<FormInstance>()

const phoneForm = ref<FormInstance>()

// @ts-ignore
const validatePhone = (rule: any, value: any, callback: any) => {
    if (value.toString().length !== 11)
        callback(new Error('请输入11位手机号'))
    else callback()
}

// @ts-ignore
const validateCode = (rule: any, value: any, callback: any) => {
    if (value.toString().length !== 6)
        callback(new Error('请输入6位验证码'))
    else callback()
}

const rules = reactive<FormRules<typeof user>>({
    type: [
        { required: true, message: '请选择账号类型', },
    ],
    phone: [
        {
            validator: validatePhone,
            trigger: 'blur',
        },
    ],
    password: [
        {
            required: true,
            message: '请输入密码',
        },
    ],
    code: [
        {
            validator: validateCode,
            trigger: 'blur',
        },
    ]
})

const emit = defineEmits(['update:state'])
const gotoRegister = () => {
    emit('update:state', !props.state)
}

const back = () => {
    phoneMode.value = false
}

const login = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            if (phoneMode.value) {
                let res = await api['route/login'].loginByCode(user.phone, user.code)
                if (res.code === 200) {
                    localStorage.setItem('loginInfo', JSON.stringify({ type: 2, phone: user.phone, password: user.password, code: '' }))
                    store.user = res.data
                    localStorage.setItem('token', res.data.token)
                    router.push({ name: 'UserHome' })
                }
            } else {
                let res = await api['route/login'].loginByPassword(user)
                if (res.code === 200) {
                    if (user.type == 1) {
                        localStorage.setItem('loginInfo', JSON.stringify({ type: user.type, phone: user.phone, password: user.password, code: '' }))
                        store.user = res.data
                        localStorage.setItem('token', res.data.token)
                        router.push({ name: 'AdminHome' })
                    }
                    else if (user.type == 2) {
                        if (res.data != null) {
                            localStorage.setItem('loginInfo', JSON.stringify({ type: user.type, phone: user.phone, password: user.password, code: '' }))
                            store.user = res.data
                            localStorage.setItem('token', res.data.token)
                            router.push({ name: 'UserHome' })
                        } else{
                            phoneMode.value = true
                        }
                    }
                }
            }
        } else {
            ElMessage.error('表单校验错误')
            return false
        }
    })
}


</script>

<style lang="less" scoped>
@keyframes login_box {
    0% {
        transform: translate(0, -100%);
    }

    100% {
        transform: translate(0, -6%);
    }
}

.login {
    width: 500px;
    height: 350px;
    display: grid;
    grid-template-rows: 1fr 2fr;
    border-radius: 15px;
    justify-content: center;
    align-self: center;
    align-content: space-evenly;
    animation: login_box 0.5s forwards ease;
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(4px);
    box-shadow: 0 0 20px #16161a;

    .title {
        align-self: center;
        font-weight: 900;
        font-size: 40px;
        font-style: oblique;
        letter-spacing: 5px;
        color: #3d3d3d;
    }

    .login-form,
    .phone-verificate {
        align-self: center;
        width: 18em;
        padding: 0 20px;

        .el-select,
        .el-input {
            width: 100%;
        }

        :deep(.el-input__inner) {
            &::placeholder {
                font-size: 10px;
            }
        }

        .go {
            padding: 5px 10%;
            line-height: 3em;
            font-weight: 900;
            font-size: 20px;
            border-radius: 10px;
            color: rgba(255, 255, 255, 0.839);
            letter-spacing: 5px;
            background-image: linear-gradient(to right, #170d11, #ceced4);
            cursor: pointer;
            user-select: none;
        }
    }


    .switch-login-mode {
        position: absolute;
        right: 20px;
        top: 10px;
        font-size: 15px;
        color: #3d3d3d;

        .el-icon {
            cursor: pointer;
        }
    }
}
</style>
