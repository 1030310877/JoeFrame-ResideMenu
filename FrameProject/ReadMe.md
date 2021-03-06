# 框架使用说明：
	该框架集成使用了如下第三方库：
	1.SweetDialog（对话框）、Progresswheel（圆形进度条）、nineoldandroids.jar（动画兼容包）
	2.ResideMenu（侧滑菜单）
	3.androidEventBus（事件订阅处理）
	4.@Trinea大神些的工具类

## 注意
### 该框架要求API>=11。
#### 如果使用Toolbar作为Actionbar，则项目Theme需要为AppCompat.NoActionbar，或直接使用框架内的AppTheme。
#### 该框架已整合成Library形式，添加项目依赖即可使用。
	
# 更新：
	1.添加流式线性布局和竖型Viewpager。
	2.添加最基础的注解绑定View功能。
	3.封装了Webview，可使用WebViewType进行快速配置。
	4.添加通用型ListView和GridView的Adapter——CommonAdapter，内部已集成ViewHolder重用。
	5.添加button的background设置API兼容。
	6.将框架部分更改成为Library，与demo分离。
	7.再封装asynchttp库。
	8.添加一些自定义View和布局，略微扩展SweetAlertDialog。
	9.添加版本更新Task类和Socket连接Task类。
	10.添加崩溃捕捉crashHandler类,集成BaseApplication即可使用。
	11.完善ServiceUtils类，可以简单几步实现服务监听防杀。
	12.升级任务添加强制升级操作。
	13.优化CountDownView。
	14.优化AppupdateTask类，添加忽略该版本功能，以及根据MD5码判断是否已有安装包功能。
	15.添加同步网络请求工具类。
	16.去除了封装的网络框架。AppupdateTask采用DownloadManager下载
	
## Activity：FrameBaseActivity
	使用时继承FrameBaseActivity。入口方法为onBaseActivityCreated()。
	使用replaceFragment()或者setMyContentView()来设置显示内容。
	请在根布局中设置背景色，不然就是透明的。
	可使用注解形式替代findViewById：
			@ViewInject(R.id.xxx)
			private Button btn;
					||
			btn=(Button)findViewById(R.id.xxx);
	
### Toolbar：
设置标题，直接在Activity中使用setToolbarTitle（title,isCenter）来设置，<br>
根据inCenter的值，将会显示在不同位置。<br>
设置标题颜色和字体大小，getToolbar.setTitleColor。<br>
使用Toolbar代替Actionbar。通过hideToolbar和showToolbar控制Toolbar的显示<br>隐藏（默认显示），通过getToolbar获取Toolbar对象。Toolbar用法，请查询资料。<br>
设置Toobar的背景颜色使用getToolbar().setbackground取代style中的colorPrimary属性。<br>
要改变弹出菜单，可使用getToobar().setPopupTheme(styleId)来设置：<br>
```
<style name="PopupMenuTheme" parent="Theme.AppCompat.Light.NoActionBar">
	<item name="android:textColorPrimary">@color/grey</item>
	<item name="android:textColorSecondary">@color/grey</item>
	<item name="android:background">@color/white</item>
</style>
```
#### Toolbar菜单使用：<br>
如果setToolbarAsActionbar设置返回为true，则使用需要重写原生的onCreateOptionsMenu()和onOptionsItemSelected()进行菜单设置。
如果setToolbarAsActionbar设置返回为false，使用toolbar自带的addMenu()和setOnMenuItemClickListener进行菜单设置。<br>
		
### 侧滑菜单ResideMenu：
使用initResideMenu()进行初始化设置并返回ResideMenu对象。<br>也可使用getResideMenu()获得ResideMenu对象<br>（如果之前未调用initResideMenu，那么将会进行默认初始化）。<br>
侧滑菜单分为两部分：菜单头和菜单项。添加菜单头可以添加任意View，<br>使用residemenu.addMenuHeader(View)方法添加；菜单项为ResideMenuItem对象，<br>直接使用addMenuItemToMenu()或addMenuItemsToMenu()添加。<br>
使用侧滑菜单后，默认界面的所有左右滑动会触发菜单开关，<br>其他组件不能接受滑动事件。如果有滑动列表或其他需要接收滑动事件，<br>则使用addIgnoredView()，将其添加。添加后，在该View上的滑动事件不会打开菜单。<br>
![](https://github.com/1030310877/JoeFrame-ResideMenu/blob/master/pic/residemenu.png)
## Fragment: FrameBaseFragment
使用时继承FragmentBaseFragment。入口方法为onBaseFragmentCreate()。<br>
使用setMyContentView()设置显示内容。replaceFragment()将会replace新的Fragment取代自己。<br>
请在根布局中设置背景色，不然就是透明的。<br>
使用findViewById或注解@ViewInject可以获取到内容中的组件。<br>
<br>

### Toolbar：
要使用Toolbar，可使用context.getToolbar获取到对象。<br>
需要在Fragment中改写Toolbar的菜单，要设置setHasOptionMenu(true)，然后和Activity一样建立菜单。<br>
提示：在onCreateOptionsMenu中根据需要进行menu.clear()，不然会和activity的菜单进行叠加，一起显示。
		
## AndroidEventBus：
在Activity或Fragment中直接使用registerEventBus或regiseterEventBusForSticky注册即可,<br>注册后会自动在onDestroy中注销。如需自行管理注销，仍旧按照原有方法使用。

## 常用工具类：
### HttpUtils： 使用原生API进行http异步请求。

### LogUtils：	日志记录类
使用方法：LogUtils.d	LogUtils.i	LogUtils.e	LogUtils.v
使用时，更改其中isDebugModel为true，TAG为所需标识。如果需要保存到SD卡，<br>更改LogUtils下的isSaveDebugInfo和isSaveCrashInfo，以及更改CACHE_DIR_NAME。同时添加对应权限：
```
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
```
### ToastUtils:	吐司类
对Toast类进行简单封装，实现同一个context下，toast不会进行叠加延迟显示。

### ServiceUtils：  服务工具类
作为工具类提供isServiceRunning()方法查询服务是否在运行。<br>
此类还可以用来对服务进行监听，服务停止后将会进行回调，并根据配置可再启动服务。<br>
	使用getInstance方法获取单例，建议参数传入ApplicationContext；<br>
	通过addNeedMonitorredService()方法传入需要监听状态的服务名；在通过startMonitorService(String,boolean)方法（需要服务停止后能够再启动，第二个参数传true）可启动框架中的MonitorService对其他服务进行监听。<br>
	无需再进行监听， 使用removeMonitorredService，将服务移除。<br>
	
### LocationUtils：	定位工具类
静态方法isLocateEnable()用于判定是否可以使用定位功能。<br>
静态方法isGPSopen()判断GPS是否打开。<br>
静态方法openGPSset()打开系统的GPS设置界面，需要在跳转的Activity中进行结果接收。<br>
简单定位的实现，需要获取该LocationUtils的单例，调用locate()方法进行获取。<br>

### ThreadPoolUtils:	线程池封装类
通过参数Type进行不同类型的实例化：
```
 public enum Type {
        SingleThread,
        FixedThread,
        CachedThread,
        ScheduledSingleThread
    }
```
使用execute执行任务，shutdown和shutdownNow停止和关闭线程。

## Task类
### AppUpdateTask：
请继承该类，重写parseUpdateInfo方法和ignoreThisVersion方法。进行从服务器获取的版本信息的解析，<br>并再封装成AppUpdateInfo类回传。AppUpdateInfo中的appname，downloadUrl，<br>versionname，suffixname（.apk），updateinfo数据必须进行设值。<br>并且调用info.setIsNeedToUpdate（true）后。在checkVersion时会进行升级提示。<br>调用checkVersion传入保存APK的路径请保证具有读写权限。

### SocketAsyncTask
使用比较简单，内含心跳机制，有三种状态回调：连接成功，连接失败，连接断开。<br>接收和发送数据均已进行封装，可进行byte[]和String类型的发送和接收。<br>·注意点：内部使用了AsyncTask，因此使用时，和AsyncTask一样，<br>不能重复执行connect操作，每次均需要重新实例化。·

## CrashHandler
CrashHandler取代默认崩溃异常捕捉线程。使用时只要使项目Application集成框架中的BaseApplication即可，重写BaseApplication中的抽象方法，可以获取到保存的崩溃日志的地址。<br>
崩溃产生的日志，在内部存储中Android/data/package-name/files/Crash文件夹内，以XML形式记录，包括手机信息，系统信息，崩溃信息等。<br>
崩溃产生时，使用Toast进行提示，3S后自动关闭程序，不再弹出“程序停止运行”的对话框。

## Dialog类
集成了SweetAlert—dialog，并加以修改，添加第三个button。
### DirChooserDialog
目录选择或文件选择对话框，可以直接使用，在回调中接收选择结果。
![](https://github.com/1030310877/JoeFrame-ResideMenu/blob/master/pic/dirchooserdialog.png)

## 自定义View
### CountDownView——倒计时View
### RoundImageView——圆（角）的ImageView
### SwitchButton——和IOS的switchbutton类似
![](https://github.com/1030310877/JoeFrame-ResideMenu/blob/master/pic/countdownroundiamge.png)
