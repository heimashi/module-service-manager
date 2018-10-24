# module-service-manager

Android模块化/组件化后组件间通信框架，支持模块间功能服务/View/Fragment的通信调用等，通过注解标示模块内需要暴露出来的服务和View，应用gradle插件会通过transform来hook编译过程，扫描出注解信息后再利用asm生成代码来向框架中注册对应的服务和View，之后模块间就可以利用框架这个桥梁来调用和通信了。

## Getting started

在跟目录的build.gradle中添加gradle插件:
```Groovy
dependencies {
    //...
    classpath 'com.rong360.msm:msm-gradle-plugin:1.0.0'
}
```
在Application项目的build.gradle中应用gradle插件
```Groovy
apply plugin: 'com.rong360.msm.plugin'
```
添加api的依赖
```Groovy
api 'com.rong360.msm:msm-api:1.0.0'
```
开始使用吧，案例参考[sample2](https://github.com/heimashi/module-service-manager/tree/master/sample2)

- 1、提供方：通过注解注册服务/View/Fragment
```kotlin
@ModuleService(register = "AModuleCalculateService")
class AModuleCalculateService2 : IModuleService {...}

@ModuleView(register = "AModuleView", desc = "Module A View")
class AModuleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {...}

@ModuleFragment(register = "AModuleFragment")
class AModuleFragment : Fragment() {...}
```
- 2、使用方：通过api调用
```kotlin
val service = ModuleServiceManager.instance.loadService("AModuleCalculateService") as IModuleService?
val aView = ModuleServiceManager.instance.loadView(this, "AModuleView")
val fragment = ModuleServiceManager.instance.loadFragment("AModuleFragment")
```
