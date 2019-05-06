

<p align="center">

<a href="https://bintray.com/leo90/maven/debug">
		<image alt="Version" src="https://img.shields.io/badge/maven%20central-1.0.1-green.svg">
	</a>
</p>


> **[XMDebug](https://guobao90.github.io/2018/11/30/debug/)** is a debug tool to help developer


## 1.ScreenShot
<p align="center">
 <img src="https://github.com/guobao90/XMDebug/blob/master/device-2019-05-06-210419.png?raw=true" width = "300" height = "534" alt="图片名称" align=center />
 <img src="https://github.com/guobao90/XMDebug/blob/master/device-2019-05-06-210443.png?raw=true" width = "300" height = "534" alt="图片名称" align=center />

You can download [apk](https://github.com/guobao90/XMDebug/blob/master/app-release.apk)  sample 

## 2.Download
You can download from GitHub's [releases page].

Or use Gradle:

```gradle
repositories {
  mavenCentral()
  google()
}

dependencies {
  implementation 'com.leo:debug:1.0.1'
}
```


## 3.How do I Use
### 3.1 App Info
You can see Activities, Services , Receivers ,Providers,Permissions colletions . Some Detail of your App like ,verson ,version code .

In Activities your can see All Activity . Press the item your can jump to the corresponding Activity .
#### 3.1.1 Activities
![](https://github.com/guobao90/XMDebug/blob/master/screenshot/device20190506213306_activit.gif?raw=true)

#### 3.1.2 Permissions .See the detail of Permissions
![](https://github.com/guobao90/XMDebug/blob/master/screenshot/device20190506213415_permiss.gif?raw=true)

### 3.2 Exception Reports
You can see the bugs reports with the detail of code and the screenshot of the exception happen
![](http://pktfce9ot.bkt.clouddn.com/19-1-5/46712449.jpg)

### 3.3 Local Data
Detail of SharedPreference ,Sqlite, local Cache. You can also clean the data through this debug tools
![](http://pktfce9ot.bkt.clouddn.com/19-1-5/49438500.jpg)

### 3.4 Toast Activity Name
Switch On Show Activity Name ,You can see the toast of Activity of .

![](https://github.com/guobao90/XMDebug/blob/master/screenshot/device20190506213948_toast.gif?raw=true)


## 4.Start
### 4.1 First ... emm ,you should implementation the library
### 4.2 In your custom application ,put the code below

	 override fun onCreate() {
        super.onCreate()
        DebugCrashHandler.setCrashHandler(this)
    }

    these code use to capture the exceptions

### 4.3 put the DebugHomeActivity entrance in somewhere nobody know

## GitPage

https://guobao90.github.io/2018/11/30/debug/

## Author

leo, guobao9006@gmail.com

## License

XMDebug is available under the MIT license. See the LICENSE file for more info.