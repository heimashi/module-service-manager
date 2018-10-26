## 模块化/组件化

随着客户端项目越来越大，一个项目往往会分为不同的业务线，不同的业务线由不同的开发人员维护开发，模块化/组件化势在必行，一个模块代码一条业务线，模块内职责单一，模块间界限清晰，模块自身的复用更加方便快捷，模块化的好处很多，同时也存在一些需要改进的地方：例如编译速度的瓶颈越来越大、模块间怎么进行高效通信、模块怎么独立运行调试等等。


## 理想的模块化架构
![image](https://github.com/heimashi/module-service-manager/blob/master/imgs/modules.png)
- 可以参考案例项目代码[sample2](https://github.com/heimashi/module-service-manager/tree/master/sample2)
- 如上图所示，模块后的代码从下往上看，可以分为三层：
	- 最下层是common层，为上层业务提供基础支持，该层不含有业务代码，可以再按功能细分为多个module，提供基础统一的服务。
	- 中间层是不同的业务线模块，module-a/module-b/module-c/...等，每个模块代表不同的业务模块，自身职责尽量单一，模块间界限清晰。向下以implementation形式依赖common基础库
	- 最上层是壳工程application，该工程没有业务代码，为所有模块提供一个组装的壳，向下以runtimeOnly的形式依赖所有的业务线，采用runtimeOnly的目的是使得壳工程尽量的职责单一，各模块间在编译期没有代码上的直接交互，需要在运行期才能产生交互，模块间更加独立和复用性更好。

## 模块化后的一些优化

采用像上图这样模块化改造之后，还可以进行更进一步的优化工作，例如支持模块的单独编译运行调试，优化代码编译速度等

#### 模块的单独编译运行

有两种思路可以实现模块的独立运行：
- 思路1: **变量控制**，参考案例1[sample](https://github.com/heimashi/module-service-manager/tree/master/sample)
	- 1、在各模块的build.gradle中，根据控制变量来决定依赖的library插件还是application插件
	```Groovy
	if(getBooleanPropertyIfExist("BIsApplicationMode")){
    	apply plugin: 'com.android.application'
	}else{
    	apply plugin: 'com.android.library'
	}
	```
	- 2、application模式下需要指定单独的manifest和一些初始化代码，例如launcher的Activity等
	```Groovy
	sourceSets {
        main {
            if(getBooleanPropertyIfExist("BIsApplicationMode")){
                manifest.srcFile 'src/main/release/AndroidManifest.xml'
            }else{
                manifest.srcFile 'src/main/AndroidManifest.xml'
            }
        }
    }
	```
	- 3、getBooleanPropertyIfExist工具方法是获取gradle环境中的变量，如果变量不存在有个容错方案是默认都是false，不存在gradle.properties文件项目也能正常编译
	```Groovy
	def getBooleanPropertyIfExist(propertyString) {
        if(hasProperty(propertyString)){
            if(project[propertyString].toBoolean()){
                return true
            }
        }
        return false
    }
	```
	4、这样一来就可以在gradle.properties文件中定义模块的控制变量了，之后可以将gradle.properties文件放在.gitignore中防止本地修改污染别人的代码
	```Groovy
	AIsApplicationMode=false
	BIsApplicationMode=false
	```
- 思路2: **为每个模块增加一个Application的module**，参考案例2[sample2](https://github.com/heimashi/module-service-manager/tree/master/sample2)

	- 每个module都添加一个Application的工程然后再依赖对应的模块，可以将这样的模块聚合到一个目录下，例如modules-wrapper，在settings.gradle中添加：
	```Groovy
	include ':sample2:modules-wrapper:module-a-app',':sample2:modules-wrapper:module-b-app'
	```
	- 然后新建modules-wrapper目录，在该目录下建各模块的工程module-a-app，module-b-app...
	- module-a-app这样的工程都是application工程，提供模块启动的壳，具体参考案例2[sample2](https://github.com/heimashi/module-service-manager/tree/master/sample2)

- **总结**：上面两种思路都实现了模块的独立编译运行，思路1的缺点是需要维护两套manifest等代码资源，每次修改gradle.properties的变量后需要重新sync一下代码可能比较浪费时间，相对而言思路2会更好一些，虽然增加了不少工程项目，但是收缩到一个目录下后也还是较好管理。


#### 项目全量包打包速度优化

经过上面的模块单独编译改造，模块本身的打包速度得到很大提高，因为模块本身可以以Application形式编译，不需要依赖其他无关模块。但是如果要进行壳工程的编译，即全量模块的打包，对于大项目时间还是会很慢。

一种优化的思路是这样的：把模块的项目project形式依赖该为aar形式依赖，因为aar里已经是编译好的class代码了，减少了java编译为class和kotlin编译为class的过程。把不经常改变的模块打成aar，或者如果你在开发A模块，你就可以选择将所有除A模块以外的模块全部以aar形式进行依赖，或者你可以选择依赖你需要关心的模块，你不关心的模块可以不依赖。aar可以发布到公司内部私服里，还有一种办法是直接发布到本地maven库，即在本地建一个目录例如local_maven，将所有aar发布到该目录下，项目中再引入该本地maven即可。下面详细介绍通过脚本改造快捷的实现方案：

- 首先在[utils.gradle](https://github.com/heimashi/module-service-manager/blob/master/utils.gradle)的脚本中添加发布aar的task，可以快捷的在所有的project中注入发布的task避免重复的发布脚本
```Groovy
//add task about publishing aar to local maven
task publishLocalMaven {
    group = 'msm'
    description = 'publish aar to local maven'
    dependsOn project.path + ':clean'
    finalizedBy 'uploadArchives'

    doLast {
        apply plugin: 'maven'
        project.group = 'com.rong360.example.modules'
        if (project.name == "module-a") {//may changer version
            project.version = '1.0.0'
        } else {
            project.version = '1.0.0'
        }
        uploadArchives {
            repositories {
                mavenDeployer {
                    repository(url: uri(project.rootProject.rootDir.path + '/local_maven'))
                }
            }
        }

        uploadArchives.doFirst {
            println "START publish aar:" + project.name + " " + project.version
        }

        uploadArchives.doLast {
            println "End publish aar:" + project.name + " " + project.version
        }
    }
}

ext {
    //...
    compileByPropertyType = this.&compileByPropertyType
}
```

- 然后就可以在各模块中执行发布aar的脚本，就可以在local_maven目录下查看到已发布的aar
```Shell
./gradlew :sample2:module-a:publishLocalMaven

```

- 在项目的build.gradle中加入本地的maven地址
```Groovy
repositories {
        //...
        maven {
            url "$rootDir/local_maven"
        }
    }
```

- 在[utils.gradle](https://github.com/heimashi/module-service-manager/blob/master/utils.gradle)的脚本中添加根据变量控制编译方式的脚本
```Groovy
//返回0，1，2三种数值，默认返回0
def getCompileType(propertyString) {
    if (hasProperty(propertyString)) {
        try {
            def t = Integer.parseInt(project[propertyString])
            if (t == 1 || t == 2) {
                return t
            }
        } catch (Exception ignored) {
            return 0
        }
    }
    return 0
}

//根据property选择依赖方式，0采用project形式编译，1采用aar形式编译，2不编译
def runtimeOnlyByPropertyType(pro, modulePath, version = '1.0.0') {
    def moduleName
    if (modulePath.lastIndexOf(':') >= 0) {
        moduleName = modulePath.substring(modulePath.indexOf(':') + 1, modulePath.length())
    } else {
        moduleName = modulePath
    }
    def type = getCompileType(moduleName+'CompileType')
    if (type == 0) {
        dependencies.runtimeOnly pro.project(":$modulePath")
    } else if (type == 1) {
        dependencies.runtimeOnly "com.rong360.example.modules:$moduleName:$version@aar"
    }
}

ext {
    //...
    runtimeOnlyByPropertyType = this.&runtimeOnlyByPropertyType
}
```
- 在gradle.properties中就可以添加控制变量来控制项目是以aar形式/project形式/不依赖三种情况来编译了
```Groovy
module-aCompileType=0
module-bCompileType=0
```

- 最后在壳工程中就可以调用compileByPropertyType来进行依赖了,根据gradle.property中的变量来选择依赖方式：0采用project形式编译，1采用aar形式编译，2不编译
```Groovy
dependencies {
    runtimeOnlyByPropertyType(this, 'sample2:module-a')
    runtimeOnlyByPropertyType(this, 'sample2:module-b')
    //...
}
```



## 模块化通信方案：module-service-manager

Android模块化/组件化后组件间通信框架，支持模块间功能服务/View/Fragment的通信调用等，通过注解标示模块内需要暴露出来的服务和View，应用gradle插件会通过transform来hook编译过程，扫描出注解信息后再利用asm生成代码来向框架中注册对应的服务和View，之后模块间就可以利用框架这个桥梁来调用和通信了。

## 怎样模块间通信

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
