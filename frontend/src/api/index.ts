/**
 * @desc API注册文件
 */
const api: any = {}
const files = import.meta.glob('./route/*.ts', { eager: true, import: 'default' })
Object.keys(files).forEach((fileName) => {
	const name = fileName.replace(/\.\/|\.ts/g, '')
	api[name] = files[fileName] || {}
})
export default api
