# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\Lenovo\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#####android studio中，由于gradle里进行了配置，混淆文件中可以不用再次声明所引用的包名##################
#-libraryjars libs/org.apache.http.legacy.jar
#-libraryjars libs/SwetakeQRCode.jar
#-libraryjars libs/mqtt-client-1.7-uber.jar
#-libraryjars libs/base64.jar
#-libraryjars libs/gson-2.3.1.jar
#-libraryjars libs/zps_widget_switchbutton.jar
#-libraryjars libs/zps_widget_pulltorefresh.jar
#-libraryjars libs/zps_widget_numprogressbar.jar
#-libraryjars libs/zps_widget_circleprogress.jar
#-libraryjars libs/zps_widget_wheel.jar
#-libraryjars libs/appcompat_v7.jar
#-libraryjars libs/zps_widget_bugtags-android-eclipse-lib.jar
#-libraryjars libs/zps_widget_crosswalk-webview-13.42.319.12-arm.jar
#-libraryjars libs/android.jar

#-libraryjars libs/AMap_Location_V3.3.0_20170118.jar
#-libraryjars libs/AMap_Search_V4.0.0_20170111.jar
#-libraryjars libs/AMap_Location_V3.3.0_20170118.jar
#-libraryjars libs/Android_Map3D_SDK_V4.1.2_20161104.jar

-libraryjars libs/armeabi/libgdinamapv4sdk752.so
-libraryjars libs/armeabi/libgdinamapv4sdk752ex.so
-libraryjars libs/armeabi/libgetuiext2.so
-libraryjars libs/armeabi-v7a/libgdinamapv4sdk752.so
-libraryjars libs/armeabi-v7a/libgdinamapv4sdk752ex.so
-libraryjars libs/armeabi-v7a/libgetuiext2.so
-libraryjars libs/x86_64/libgdinamapv4sdk752.so
-libraryjars libs/x86_64/libgdinamapv4sdk752ex.so
-libraryjars libs/x86_64/libgetuiext2.so

-libraryjars libs/armeabi/libmp3lame.so
-libraryjars libs/armeabi-v7a/libmp3lame.so
-libraryjars libs/x86_64/libmp3lame.so

## ########## 个推混淆 ##########
## ----------------------------------
-dontwarn com.igexin.**
-keep class com.igexin.** {*;}

-keep class javax.security.sasl.** { *; }
-dontwarn javax.security.sasl.**
-keep class javax.security.auth.callback.** { *; }
-dontwarn javax.security.auth.callback.**
-keep class java.beans.** { *; }
-dontwarn java.beans.**
-keep class android.** { *; }
-dontwarn android.**

-keep class org.apache.** { *; }
-dontwarn org.apache.**
-keep class org.eclipse.paho.client.mqttv3.** { *; }
-dontwarn org.eclipse.paho.client.mqttv3.**
-keep class com.swetake.util.** { *; }
-dontwarn com.swetake.util.**
-keep class com.rt.** { *; }
-dontwarn com.rt.**

-dontwarn cn.com.hiss.www.multilib.db.**
-keep class cn.com.hiss.www.multilib.db.**{*;}
-dontwarn cn.com.hiss.www.multilib.common.**
-keep class cn.com.hiss.www.multilib.common.**{*;}
-dontwarn cn.com.hiss.www.multilib.oss.**
-keep class cn.com.hiss.www.multilib.oss.**{*;}

-keep class circledemo.bean.** { *; }
-dontwarn circledemo.bean.**
-keep class com.im.hiss.beansI.** { *; }
-dontwarn com.im.hiss.beansI.**
-keep class com.im.hiss.beansO.** { *; }
-dontwarn com.im.hiss.beansO.**
-keep class com.im.hiss.httpbean.** { *; }
-dontwarn com.im.hiss.httpbean.**
-keep class com.im.hiss.model.** { *; }
-dontwarn com.im.hiss.model.**
-keep class net.openmob.mobileimsdk.server.protocal.** { *; }
-dontwarn net.openmob.mobileimsdk.server.protocal.**
-dontwarn com.maxi.chatdemo.common.**
-keep class com.maxi.chatdemo.common.**{*;}

-keep class com.github.florent37.camerafragment.**{*;}
-keep class com.ririjin.editclear.**{*;}
-keep class io.github.rockerhieu.emojicon.**{*;}
-keep class com.ririjin.loadingstateview.**{*;}
-keep class org.angmarch.views.**{*;}
-keep class com.bigkoo.pickerview.**{*;}
-keep class com.daimajia.swipe.**{*;}
-keep class com.gjiazhe.wavesidebar.**{*;}
-keep class com.handmark.pulltorefresh.library.**{*;}

-dontwarn pl.droidsonroids.gif.**
-keep class pl.droidsonroids.gif.**{*;}
-dontwarn com.github.ybq.android.spinkit.**
-keep class com.github.ybq.android.spinkit.**{*;}
-dontwarn q.rorbin.badgeview.**
-keep class q.rorbin.badgeview.**{*;}
-dontwarn com.zhy.base.adapter.**
-keep class com.zhy.base.adapter.**{*;}
-dontwarn pub.devrel.easypermissions.**
-keep class pub.devrel.easypermissions.**{*;}
-dontwarn android.support.test.espresso.**
-keep class android.support.test.espresso.**{*;}
-dontwarn com.google.**
-keep class com.google.**{*;}
-dontwarn com.flyco.**
-keep class com.flyco.**{*;}
-dontwarn com.github.yuweiguocn.library.greendao.**
-keep class com.github.yuweiguocn.library.greendao.**{*;}
-dontwarn org.hamcrest.**
-keep class org.hamcrest.**{*;}
-dontwarn lecho.lib.hellocharts.**
-keep class lecho.lib.hellocharts.**{*;}
-dontwarn com.squareup.javawriter.**
-keep class com.squareup.javawriter.**{*;}
-dontwarn javax.annotation.**
-keep class javax.annotation.**{*;}
-dontwarn javax.inject.**
-keep class javax.inject.**{*;}
-dontwarn javax.annotation.**
-keep class javax.annotation.**{*;}
-dontwarn javax.inject.**
-keep class javax.inject.**{*;}
-dontwarn com.soundcloud.android.crop.**
-keep class com.soundcloud.android.crop.**{*;}
-dontwarn com.baoyz.widget.**
-keep class com.baoyz.widget.**{*;}
-dontwarn me.shaohui.advancedluban.**
-keep class me.shaohui.advancedluban.**{*;}
-dontwarn com.liulishuo.filedownloader.**
-keep class com.liulishuo.filedownloader.**{*;}
-dontwarn com.kyleduo.switchbutton.**
-keep class com.kyleduo.switchbutton.**{*;}
-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.**{*;}
-dontwarn com.orhanobut.logger.**
-keep class com.orhanobut.logger.**{*;}
-dontwarn com.rey.material.**
-keep class com.rey.material.**{*;}
-dontwarn com.github.mikephil.charting.**
-keep class com.github.mikephil.charting.**{*;}
-dontwarn android.support.multidex.**
-keep class android.support.multidex.**{*;}
-dontwarn com.darsh.multipleimageselect.**
-keep class com.darsh.multipleimageselect.**{*;}
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-dontwarn okio.**
-keep class okio.**{*;}
-dontwarn com.yalantis.phoenix.**
-keep class com.yalantis.phoenix.**{*;}
-dontwarn br.com.goncalves.pugnotification.**
-keep class br.com.goncalves.pugnotification.**{*;}
-dontwarn com.makeramen.roundedimageview.**
-keep class com.makeramen.roundedimageview.**{*;}
-dontwarn com.jakewharton.rxbinding.**
-keep class com.jakewharton.rxbinding.**{*;}
-dontwarn com.trello.rxlifecycle.**
-keep class com.trello.rxlifecycle.**{*;}
-dontwarn com.lapism.searchview.**
-keep class com.lapism.searchview.**{*;}
-dontwarn com.readystatesoftware.systembartint.**
-keep class com.readystatesoftware.systembartint.**{*;}
-dontwarn com.jph.takephoto.**
-keep class com.jph.takephoto.**{*;}
-dontwarn android.support.transition.**
-keep class android.support.transition.**{*;}
-dontwarn com.yalantis.ucrop.**
-keep class com.yalantis.ucrop.**{*;}
-dontwarn com.journeyapps.barcodescanner.**
-keep class com.journeyapps.barcodescanner.**{*;}
######################朋友圈新增
-dontwarn uk.co.senab.photoview.**
-keep class uk.co.senab.photoview.**{*;}
-dontwarn com.malinskiy.superrecyclerview.**
-keep class com.malinskiy.superrecyclerview.**{*;}
-dontwarn com.mikhaellopez.circularprogressbar.**
-keep class com.mikhaellopez.circularprogressbar.**{*;}
-dontwarn com.google.example.easypermissions.**
-keep class com.google.example.easypermissions.**{*;}
-dontwarn pub.devrel.easypermissions.**
-keep class pub.devrel.easypermissions.**{*;}
-dontwarn com.kyleduo.switchbutton.**
-keep class com.kyleduo.switchbutton.**{*;}
-dontwarn net.sqlcipher.**
-keep class net.sqlcipher.**{*;}
-dontwarn android.support.test.internal.runner.hidden.**
-keep class android.support.test.internal.runner.hidden.**{*;}
-dontwarn net.sqlcipher.**
-keep class net.sqlcipher.**{*;}


-dontwarn java.lang.**
-keep class java.lang.**{*;}
-dontwarn com.amap.**
-keep class com.amap.**{*;}
-dontwarn com.a.a.**
-keep class com.a.a.**  {*;}
-dontwarn com.autonavi.**
-keep class com.autonavi.**  {*;}
#-dontwarn com.loc.**
#-keepclass com.loc.**{*;}

-dontwarn sun.misc.**
-keep class sun.misc.**{*;}

-dontwarn retrofit2.**
-keep class retrofit2.**{*;}
-dontwarn rx.**
-keep class rx.**{*;}
-keepattributes Signature
-keepattributes Exceptions
# Fixed: Caused by: java.lang.NoSuchFieldException: No field producerIndex
 -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
     long producerIndex;
     long consumerIndex;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     long producerNode;
     long consumerNode;
}

# picasso
-dontwarn com.squareup.picasso.**
-keep class com.squareup.picasso.**{*;}

#glide
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.**{*;}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#greendao3.2.0,此是针对3.2.0，如果是之前的，可能需要更换下包名
-dontwarn org.greenrobot.greendao.**
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


#mqtt-client,hawt****
-keep class org.fusesource.** { *; }
-dontwarn org.fusesource.**

#okhttputils
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}



#base64
-dontwarn com.rt.**
-keep class com.rt.**{*;}

#org.apache.http.legacy.jar
-dontwarn android.net.**
-keep class android.net.**{*;}
-dontwarn com.android.internal.http.multipart.**
-keep class com.android.internal.http.multipart.**{*;}
-dontwarn org.apache.**
-keep class org.apache.**{*;}

#gson start
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

-keep class com.google.**{*;}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# Gson specific classes
-keep class sun.misc.Unsafe { *; }

#保护注解
-keepattributes *Annotation*

##---------------End: proguard configuration for Gson  ----------

#support.v4/v7/v13包不混淆
-keep class android.support.** { *; }
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v7.** { *; }
-keep public class * extends android.support.v7.**
-keep interface android.support.v7.app.** { *; }
-keep class android.support.v13.** { *; }
-keep public class * extends android.support.v13.**
-keep interface android.support.v13.app.** { *; }
-dontwarn android.support.**    # 忽略警告

#保持注解继承类不混淆
-keep class * extends java.lang.annotation.Annotation {*;}
#保持Serializable实现类不被混淆
-keepnames class * implements java.io.Serializable
#保持Serializable不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#保持枚举enum类不被混淆
-keepclassmembers enum * {
  public static **[] values();
 public static ** valueOf(java.lang.String);
}
#自定义组件不被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class javax.**
-keep public class android.webkit.**
-keep public class com.android.vending.licensing.ILicensingService
-keep class com.android.vending.licensing.ILicensingService
-keep class android.support.v4.** { *; }
-dontwarn android.support.v4.**
-keep class org.apache.commons.net.** { *; }
-dontwarn org.apache.commons.net.**

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}

-keep public class **.R$*{
    public static <fields>;
}