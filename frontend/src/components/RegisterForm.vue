<template>
    <div class="register">

        <span class="title">申请用户</span>

        <el-form ref="registerForm" :model="user" :rules="rules" class="register-form" status-icon>
            <el-form-item prop="phone">
                <el-input type="text" autocomplete="off" placeholder="手机号" v-model.number="user.phone">
                </el-input>
            </el-form-item>
            <el-form-item prop="first_name">
                <el-input type="text" autocomplete="off" placeholder="姓氏" v-model.number="user.first_name">
                </el-input>
            </el-form-item>
            <el-form-item prop="full_name">
                <el-input type="text" autocomplete="off" placeholder="姓名" v-model.number="user.full_name">
                </el-input>
            </el-form-item>
        </el-form>

        <div>
            <span class="go" style="margin-right: 10%;" @click="register(registerForm)">申请</span>
            <span class="go" @click="backLogin">返回</span>
        </div>

    </div>
</template>
<script lang="ts" setup>
import { ref, reactive, inject } from 'vue'
import { FormInstance, FormRules,ElMessage } from 'element-plus'

const user = reactive({ phone: '', first_name: '', full_name: '' })
const api: any = inject('$api')
const registerForm = ref<FormInstance>()

const props = defineProps({
    state: { type: Boolean, required: true }
})
const emit = defineEmits(['update:state'])
const backLogin = () => {
    emit('update:state', !props.state)
}

// @ts-ignore
const validatePhone = (rule: any, value: any, callback: any) => {
    if (value.toString().length !== 11)
        callback(new Error('请输入11位手机号'))
    else callback()
}

const rules = reactive<FormRules<typeof user>>({
    phone: [
        {
            validator: validatePhone,
            trigger: 'blur',
        },
    ],
    first_name: [
        {
            required: true,
            message: '请输入姓氏',
        },
    ],
    full_name: [
        {
            required: true,
            message: '请输入姓名',
        },
    ]
})

const register = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate((valid) => {
        if (valid) {
            api['route/user'].register(user)
        } else {
            ElMessage.error('表单校验错误')
            return false
        }
    })
}

</script>

<style lang="less" scoped>
@keyframes register_box {
    0% {
        transform: translate(0, -100%);
    }

    100% {
        transform: translate(0, -6%);
    }
}

.register {
    width: 500px;
    height: 350px;
    display: grid;
    grid-template-rows: 1fr 2fr 1fr;
    border-radius: 15px;
    justify-content: space-evenly;
    align-self: center;
    align-content: center;
    animation: register_box 0.5s forwards ease;
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

    .register-form {
        align-self: center;
        width: 18em;
        padding: 0 20px;

        .el-input {
            width: 100%;
        }

        :deep(.el-input__inner) {
            &::placeholder {
                font-size: 10px;
            }
        }
    }

    .go {
        justify-self: center;
        padding: 5px 10%;
        font-weight: 900;
        font-size: 20px;
        border-radius: 10px;
        color: rgba(255, 255, 255, 0.839);
        letter-spacing: 5px;
        background-image: linear-gradient(to left, #170d11, #ceced4);
        cursor: pointer;
        user-select: none;
    }
}
</style>
