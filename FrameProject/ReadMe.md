#框架使用说明：
	该框架集成使用了如下第三方库：
	1.SweetDialog（对话框）、Progresswheel（圆形进度条）、nineoldandroids.jar（动画兼容包）
	2.ResideMenu（侧滑菜单）
	3.android-async-http-1.4.8.jar（异步网络请求）
	4.androidEventBus（事件订阅处理）
	5.一些工具类
	
该框架已整合成Library形式，添加项目依赖即可使用，Library之外无需再导入eventbus和asynchttp的jar包，更易进行集成。
	
#更新：
	1.添加流式线性布局和竖型Viewpager。
	2.添加最基础的注解绑定View功能。
	3.封装了Webview，可使用WebViewType进行快速配置。
	4.添加通用型ListView和GridView的Adapter——CommonAdapter，内部已集成ViewHolder重用。
	5.添加button的background设置API兼容。
	6.将框架部分更改成为Library，与demo分离。
	7.再封装asynchttp库。
	
##Activity：FrameBaseActivity
	使用时继承FrameBaseActivity。入口方法为onMyActivityCreated()。
	使用replaceFragment()或者setMyContentView()来设置显示内容。
	请在根布局中设置背景色，不然就是透明的。
	可使用注解形式替代findViewById：
			@ViewInject(R.id.xxx)
			private Button btn;
					||
			btn=(Button)findViewById(R.id.xxx);
	
###Toolbar：
		设置标题，直接在Activity中使用setToolbarTitle（title,isCenter）来设置，根据inCenter的值，将会显示在不同位置。
		设置标题颜色和字体大小，1、getToolbar.setTitleColor
		使用Toolbar代替Actionbar。通过hideToolbar和showToolbar控制Toolbar的显示隐藏（默认显示），通过getToolbar获取Toolbar对象。Toolbar用法，请查询资料。
		要改变弹出菜单，可使用getToobar().setPopupTheme(styleId)来设置：
		<style name="PopupMenuTheme" parent="Theme.AppCompat.Light.NoActionBar">
			<item name="android:textColorPrimary">@color/grey</item>
			<item name="android:textColorSecondary">@color/grey</item>
			<item name="android:background">@color/white</item>
		</style>
		Toolbar菜单使用：
			如果setToolbarAsActionbar设置返回为true，则使用需要重写onCreateMyToolbarMenu()方法，实现方式同原生的onCreateOptionsMenu()。
			菜单点击事件获取在onMyToolbarMenuItemClicked()中，通过item的id进行判断。
			
			如果setToolbarAsActionbar设置返回为false，onCreateMyToolbarMenu()和onMyToolbarMenuItemClicked()将不再生效。
			使用toolbar自带的addMenu()和setOnMenuItemClickListener进行菜单设置。
		
	
###侧滑菜单ResideMenu：
		使用initResideMenu()进行初始化设置并返回ResideMenu对象。也可使用getResideMenu()获得ResideMenu对象（如果之前未调用initResideMenu，那么将会进行默认初始化）。
		侧滑菜单分为两部分：菜单头和菜单项。添加菜单头可以添加任意View，使用residemenu.addMenuHeader(View)方法添加；菜单项为ResideMenuItem对象，直接使用addMenuItemToMenu()或addMenuItemsToMenu()添加。
		使用侧滑菜单后，默认界面的所有左右滑动会触发菜单开关，其他组件不能接受滑动事件。如果有滑动列表或其他需要接收滑动事件，则使用addIgnoredView()，将其添加。添加后，在该View上的滑动事件不会打开菜单。
		
##Fragment: FrameBaseFragment
	使用时继承FragmentBaseFragment。入口方法为onMyFragmentCreate()。
	使用setMyContentView()设置显示内容。replaceFragment()将会replace新的Fragment取代自己。
	请在根布局中设置背景色，不然就是透明的。
	使用findViewById或注解@ViewInject可以获取到内容中的组件。
	
###Toolbar：
		要使用Toolbar，可使用context.getToolbar获取到对象。
		需要在Fragment中改写Toolbar的菜单，要设置setHasOptionMenu(true)。
		Toolbar的菜单，重写onCreateMyToolbarMenu和onMyToolbarMenuItemClicked。根据需要进行menu.clear()，不然会和activity的菜单进行叠加，一起显示。
		
##AndroidEventBus：
	在Activity或Fragment中直接使用registerEventBus或regiseterEventBusForSticky注册即可,注册后会自动在onDestroy中注销。


##常用工具类：
###HttpUtils： 使用原生API进行http异步请求。
	
###AsyncHttpUtils:	完全再封装android-async-http库
		使用doHttpRequestForXXXX的方法进行不同返回值的请求，具体查看该类源代码。
		参数类FrameRequestParams继承自asynchttp库的RequestParmas类，使用put(key,value)添加参数。也可添加对象类型参数。
		使用FrameHttpRspBytes，FrameHttpRspJson，FrameHttpRspString进行http请求回调。
		回调方法onSuccess和onFailed中会有http状态码及其所表示的含义。
		
###LogUtils：	日志记录类
		使用方法：LogUtils.d	LogUtils.i	LogUtils.e	LogUtils.v
		使用时，更改其中isDebugModel为true，TAG为所需标识。如果需要保存到SD卡，更改LogUtils下的isSaveDebugInfo和isSaveCrashInfo，以及更改CACHE_DIR_NAME。同时添加对应权限：
		<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
		<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
		
###ToastUtils:	吐司类
		对Toast类进行简单封装。