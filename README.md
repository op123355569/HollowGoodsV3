# HollowGoodsV3
## 1.导入
### LastVersion：
[![](https://jitpack.io/v/op123355569/HollowGoodsV3.svg)](https://jitpack.io/#op123355569/HollowGoodsV3)

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
  
```
dependencies {
	implementation 'com.github.op123355569:HollowGoodsV3:LastVersion'
}
```

## 2.拷贝Gradle
```
android {
    buildToolsVersion '29.0.0'
    defaultConfig {
        ...
        /**** 拷贝S 1/5 ****/
        // Enabling MultiDex Support.
        multiDexEnabled true
        vectorDrawables.useSupportLibrary = true
        /**** 拷贝E 1/5 ****/
    }

    /**** 拷贝S 2/5 ****/
    sourceSets {
        main() {
            jniLibs.srcDirs = ['jniLibs']
        }
    }
    dataBinding {
        enabled = true
    }
    compileOptions {
        targetCompatibility JavaVersion.VERSION_1_8
        sourceCompatibility JavaVersion.VERSION_1_8
    }
    
    // 建议 recommend Tinker相关配置
    dexOptions {
        // 启动矩形模式
        jumboMode = true
    }
    
    // 打包APK重命名
    // 如果使用热插件模块，此处请删除，否则无法正常打包
    applicationVariants.all { variant ->
        variant.outputs.all {
            def version = "V${versionName}"
            def apkName = project.name
            def fileName = "${apkName}-${version}-${variant.buildType.name}-${releaseTime()}.apk"
            // 修改打包路径
            // variant.packageApplicationProvider.get().outputDirectory = new File("D:/APK/${project.name}/${variant.buildType.name}/")
            outputFileName = fileName
        }
    }
    /**** 拷贝E 2/5 ****/
}

/**** 拷贝S 3/5 ****/
static def releaseTime() {
    return new Date().format("yyyy.MM.dd HH.mm.ss", TimeZone.getDefault())
}
/**** 拷贝E 3/5 ****/

/**** 拷贝S 4/5 ****/
// 这个目录是基于项目的目录：Tinker/app/build/bakApk目录下存放oldApk
// def bakPath = file("${buildDir}/bakApk/")//指定基准文件(oldApk)存放位置
def bakPath = new File("D:/APK/HollowGoods/release/")//指定基准文件(oldApk)存放位置

ext {
    commonPath = "Sample-V1.0-release-2019.07.26 13.50.08"
    tinkerEnabled = true  // 是否启用Tinker的标志位
    tinkerOldApkPath = "${bakPath}/${commonPath}.apk"// oldApk 文件路径
    tinkerID = "1.0"// 与版本号一致
    tinkerApplyMappingPath = "${bakPath}/" // 混淆文件路径
    tinkerApplyResourcePath = "${bakPath}/${commonPath}-R.txt" // 资源路径
    tinkDexLoader = "com.hg.hollowgoods.sample.MyApplication" // 制定patch文件用到类
}

/*================================方法实现模块====================================*/

def getOldApkPath() {
    return ext.tinkerOldApkPath
}

def getApplyMappingPath() {
    return ext.tinkerApplyMappingPath
}

def getApplyResourceMappingPath() {
    return ext.tinkerApplyResourcePath
}

def getTinkerIdValue() {
    return ext.tinkerID
}

def buildWithTinker() {
    return ext.tinkerEnabled
}

if (buildWithTinker()) {
    // 启用Tinker  引入相关Gradle方法
    apply plugin: 'com.tencent.tinker.patch'

    // 所有Tinker相关参数的配置
    tinkerPatch {

        /*================================基本配置====================================*/

        // 指定old apk(即上一个版本的Apk) 的文件路径
        oldApk = getOldApkPath()

        // 是否忽略Tinker在产生patch文件时的错误警告并中断编译 false 不忽略 这样可以在生成patch文件时查看错误 具体哪些错误类型查考文档
        ignoreWarning = false

        // patch是否需要签名 true为需要 防止恶意串改
        useSign = true

        // 是否启用tinker
        tinkerEnable = buildWithTinker()

        /*================================build配置====================================*/

        buildConfig {

            // 是否支持新增非export的Activity
            supportHotplugComponent = true

            // 指定old apk打包时所使用的混淆文件 (因为patch文件也是需要混淆的 所以必须要与Apk的打包混淆文件一致)
            applyMapping = getApplyMappingPath()

            // 指定old apk的资源文件 希望new apk与其保持一致(R.txt 文件保持ResId的分配)
            applyResourceMapping = getApplyResourceMappingPath()

            // 指定TinkerID patch文件的唯一标识符 要与新旧Apk一致
            tinkerId = getTinkerIdValue()

            // 通常为false true会根据dex分包动态编译patch文件
            keepDexApply = false
        }

        /*================================dex相关配置====================================*/
        dex {

            // Tinker提供两种模式jar、raw
            // jar 适配到了api=14以下 而raw只能再14以上
            // jar模式下 Tinker会对dex文件压缩成jar文件 在对jar进行处理
            // raw模式下 Tinker直接对dex进行处理
            // 使用jar文件体积相对会小一些 在实际开发中用jar模式较多
            dexMode = "jar"

            // 指定dex目录  "assets/secondary-dex-?.jar"为Tinker官方Demo中建议参数
            // 在没有分包的情况下 "classes*.dex" 会匹配到应用中的所有dex文件 分包会是classes1,classes2....
            pattern = ["classes*.dex", "assets/secondary-dex-?.jar"]

            // 制定patch文件用到类
            loader = ["${tinkDexLoader}"]
        }

        /*================================Tinker关于jar与.so文件的替换相关配置====================================*/

        lib {
            pattern = ["libs/*/*.so"]
        }

        /*================================Tinker关于资源文件替换相关配置====================================*/

        res {
            // 指定Tinker可以修改的资源文件路径
            // resources.arcs ：AndroidReSourCe也就是与Android资源相关的一种文件格式。
            // 具体角色是提供资源ID到资源文件路径的映射关系，
            // 具体来说就是R.layout.activity_main（0x7f030000）到res/layout/activity_main.xml的映射关系
            // 其中R.layout.activity_main就是应用开发过程中所使用的资源ID
            pattern = ["res/*", "assets/*", "resources.arsc", "AndroidManifest.xml"]
            // 在编译时会忽略该文件的新增、删除与修改 即使修改了文件 也不会patch文件生效
            ignoreChange = ["assets/sample_meta.txt"]

            // 对于修改的资源，如果大于largeModSize，我们将使用bsdiff算法。
            // 默认大小为100kb
            largeModSize = 100
        }

        /*=============附加说明字段 配置 说明本次Patch文件的相关信息 非必须 packageConfig(官方：用于生成补丁包中的'package_meta.txt'文件)=================*/

        packageConfig {
            /*configField("key","value") 键值对 用于说明 当客户端使用patch文件修复成功 可以通过代码获取下面patch相关信息*/
            configField("patchMessage", "fix the version's bugs")
            configField("patchVersion", "${tinkerID}")
        }

        // sevenZip ......
        sevenZip {
            /**
             * 例如"com.tencent.mm:SevenZip:1.1.10"，将自动根据机器属性获得对应的7za运行文件，推荐使用
             */
            zipArtifact = "com.tencent.mm:SevenZip:1.1.10"
        }
    }

    /*================================备份脚本 用来将生成的APK的制定文件备份到制定目录====================================*/

    android.applicationVariants.all { variant ->
        variant.outputs.all {
            if (variant.buildType.name == "release") {
                /**
                 * task type, you want to bak 备份你想备份的数据 可以是任意类型
                 */
                def taskName = variant.name

                tasks.all {
                    if ("assemble${taskName.capitalize()}".equalsIgnoreCase(it.name)) {
                        it.doLast {
                            copy {
                                def fileNamePrefix = "${project.name}-${variant.baseName}"

                                def version = "V${versionName}"
                                def apkName = project.name
                                def newFileNamePrefix = "${apkName}-${version}-${variant.buildType.name}-${releaseTime()}"

                                // destPath为备份的目录 没有没有多渠道打包那么hasFlavors为false destPath=bakPath bakPath即最上面定义的基础目录
                                def destPath = bakPath

                                from outputFile
                                into destPath
                                // 备份.apk文件
                                rename { String fileName ->
                                    fileName.replace("${fileNamePrefix}.apk", "${newFileNamePrefix}.apk")
                                }

                                from "${buildDir}/intermediates/symbols/${variant.dirName}/R.txt"
                                into destPath
                                // 备份R.txt文件 即用于映射的资源ID
                                rename { String fileName ->
                                    fileName.replace("R.txt", "${newFileNamePrefix}-R.txt")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
/**** 拷贝E 4/5 ****/

/**** 拷贝S 5/5 ****/
// 对应拷入根目录的Gradle文件中
buildscript {
    ext.kotlin_version = '1.3.21'
    ext.TINKERPATCH_VERSION = '1.9.13.2'

    repositories {
        ...
        mavenCentral()
    }
    dependencies {
        ...
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
	// TinkerPatch 插件
        classpath("com.tencent.tinker:tinker-patch-gradle-plugin:${TINKERPATCH_VERSION}")
    }
}

allprojects {
    repositories {
        ...
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}

/**** 拷贝E 5/5 ****/
```

## 3.修改Style文件
```
<style name="AppTheme" parent="HGBaseAppTheme">
	<!-- Customize your theme here. -->
	<item name="colorPrimary">@color/colorPrimary</item>
	<item name="colorPrimaryDark">@color/colorPrimaryDark</item>
	<item name="colorAccent">@color/colorAccent</item>
</style>
```

## 4.重写Application
继承BaseApplication，参照[ExampleApplication](https://github.com/op123355569/HollowGoodsV3/blob/master/HollowGoodsLibrary/src/main/java/com/hg/hollowgoods/Application/ExampleApplication.java)。

## 5.添加权限
```
    xmlns:tools="http://schemas.android.com/tools"
    tools:ignore="AllowBackup,GoogleAppIndexingWarning,InnerclassSeparator,ProtectedPermissions"

    <!-- **** 本APP相关权限 **** -->
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS" /><!-- 在SDCard中创建与删除文件权限 -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /><!-- 在SDCard中写入文件权限 -->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /><!-- 在SDCard中读取文件权限 -->
    <uses-permission android:name="android.permission.INTERNET" /><!-- 上网权限 -->
   
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /><!-- 查看网络状态权限 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /><!-- 查看网络状态权限 -->
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" /><!-- 查看网络状态权限 -->
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" /><!-- 查看网络状态权限 -->
   
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" /><!-- BugView所需权限 -->
    <uses-permission android:name="android.permission.GET_TASKS" /><!-- 获取最近任务权限 -->
    <uses-permission android:name="android.permission.NFC" /><!-- NFC -->
    <uses-permission android:name="android.permission.VIBRATE" /><!-- 震动 -->
    <uses-permission android:name="android.permission.CAMERA" /><!-- 相机 -->
    <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" /> <!-- 安装APK许可 -->

    <!-- **** 二维码扫描 **** -->
    <uses-permission android:name="android.permission.VIBRATE" /><!-- 许可振动 -->
    <uses-permission android:name="android.permission.FLASHLIGHT" /><!-- 打开闪光灯 -->
    <uses-permission android:name="android.permission.CAMERA" /><!-- 许可使用照相机 -->
    <uses-feature android:name="android.hardware.camera" /><!-- 许可调用相机硬件 -->
    <uses-feature android:name="android.hardware.camera.autofocus" /><!-- 许可调用相机自动对焦 -->

    <supports-screens
        android:anyDensity="true"
        android:largeScreens="true"
        android:normalScreens="true"
        android:smallScreens="true" /><!-- 适应所有屏幕 -->
```

## PS
### 录音模块权限

```
<!-- **** 录音模块 **** -->
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

### 高德地图模块权限

```
<!-- **** 高德地图模块 **** -->
<!-- 允许程序打开网络套接字 -->
<uses-permission android:name="android.permission.INTERNET" />
<!-- 允许程序设置内置sd卡的写权限 -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<!-- 允许程序获取网络状态 -->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<!-- 允许程序访问WiFi网络信息 -->
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<!-- 允许程序读写手机状态和身份 -->
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<!-- 允许程序访问CellID或WiFi热点来获取粗略的位置 -->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
```

## SO兼容性问题
### 在defaultConfig里面加入以下代码

```
ndk {
    // 设置支持的SO库架构（开发者可以根据需要，选择一个或多个平台的so）
    abiFilters "armeabi"/*, "armeabi-v7a", "arm64-v8a", "x86","x86_64"*/
}
```

## Application无法继承框架中的BaseApplication的情况
复制[BaseApplication](https://github.com/op123355569/HollowGoodsV3/blob/master/HollowGoodsLibrary/src/main/java/com/hg/hollowgoods/Application/BaseApplication.java)到项目中，修改继承类即可，但Tinker插件将无法使用  
本方式从V3.5.3开始有效
