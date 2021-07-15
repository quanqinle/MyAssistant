目录结构参考： https://juejin.cn/post/6844904054720561165

---
# 编译
* dev

  npm install &&npm run dev

* product

  npm install &&npm run build

# 前端包结构描述

* src 为前端根目录
* src/css .css后缀css文件目录
* src/scss sass文件目录
* src/scss/module.scss 公共模块scss文件
* src/scss/unit.scss 公共元件scss文件

* src/js js 根目录
* src/js/components vue自定义组件库(被其他页面所公共引用的组件)
* src/js/pages/ 页面入口文件存放目录 *.js为该页面唯一入口文件 App.vue为该入口文件的默认入口vue根组件
* src/js/util/ js 工具包
* src/js/baseModule.js  所有页面入口必须继承自该基础类，该基础类 除了引入常规的类库外，还会添加每个页面的公共方法
* src/template 服务端模版文件存放目录
* src/template/pages 每个页面的模版文件存放此目录

# css命名规则 http://nec.netease.com/standard/css-name.html

* 分类的命名方法：使用单个字母+"-"为前缀
* 布局（grid）（.g-）；模块（module）（.m-）；元件（unit）（.u-）；功能（function）（.f-）；皮肤（skin）（.s-）；状态（.z-）。

# css 文件创建编辑 规则
* css 尽量采用 scss css预处理器，src/scss/ 为scss根目录 ;src/scss/pages/ 为页面scss目录
* css 分全局css跟组件css,全局css注意要添加 命名空间防止css污染,组件css 即 .vue组件里的 <code><style lang="scss" scoped>  </style></code>里的样式
* 每个页面独立引入自己的scss 该scss 需要在入口js import 进来,不建议内敛到 顶级root Vue组件里<code><style lang="scss" > </style></code>

# 资料
* vue 插件库 https://github.com/vuejs/awesome-vue
* vue地址 http://cn.vuejs.org/v2/guide/index.html  https://vuefe.cn/v2/guide/ 
* element UI http://element.eleme.io/#/zh-CN 
* loadsh https://wizardforcel.gitbooks.io/lodash-doc-45/content/19.html
* xss 白名单过滤HTML(防止XSS攻击) http://jsxss.com/zh/index.html
