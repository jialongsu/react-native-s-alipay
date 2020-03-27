
# [react-native-s-alipay](https://github.com/1035901787/react-native-s-alipay)
React Native 支付宝模块，同时支持ios和android，react native 0.60.0+  autolink

## 安装

```js
npm install react-native-s-alipay --save
```
或者

```js
yarn add react-native-s-alipay
```

## 配置
如果你的react native >= 0.60.0，那么你不需要做太多的配置。

## Android:
android需要在android -> app -> build.gradle中配置lib包的引用：

```js
...
repositories {
    flatDir {
        dirs project(':react-native-s-alipay').file('libs')
    }
}
...
```

## ios:

## 第一步：

ios需要在ios项目的Podfile中加入支付宝依赖包的引用：
```js
pod 'AlipaySDK-iOS'
```
然后运行命令：

```js
cd ios && pod install
```
## 第二步：
等待安装成功后，进入ios工程文件夹，会看到一个.xcworkspace 结尾的文件 ，双击打开
![在这里插入图片描述](https://user-gold-cdn.xitu.io/2020/3/22/1710041119cafc32?w=922&h=1028&f=jpeg&s=118493)

## 第三步：
选中项目，右键添加文件
![在这里插入图片描述](https://user-gold-cdn.xitu.io/2020/3/22/17100411193f21c3?w=1000&h=971&f=jpeg&s=156841)
点击找到本项目node_modules下的react-native-s-alipay -> iosLib -> RNSAlipay, 将整个RNSAlipay文件夹导入。

## 第四步：
在AppDelegate.m文件中添加以下两个方法，来处理跳转的url：

头部引用：

```js
#import "RNSAlipayManager.h"
```

```js
- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation {
    
    if ([url.host isEqualToString:@"safepay"]) {
        //跳转支付宝钱包进行支付，处理支付结果
        [RNSAlipayManager handleCallback:url];
    }
    return YES;
}

// NOTE: 9.0以后使用新API接口
- (BOOL)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<NSString*, id> *)options
{
    if ([url.host isEqualToString:@"safepay"]) {
        //跳转支付宝钱包进行支付，处理支付结果
        [RNSAlipayManager handleCallback:url];
    }
    return YES;
}
```

## 第五步：
添加url type：
![在这里插入图片描述](https://user-gold-cdn.xitu.io/2020/3/22/171004111a738471?w=2322&h=858&f=jpeg&s=219888)
到此支付宝配置结束。

## 使用
```javascript
import Alipay from 'react-native-s-alipay';

//orderInfo由后端返回
Alipay.pay(orderInfo).then(
  res => {
    console.log('success:', res);
  },
  res => {
    console.log('fail:', res);
  },
);
```
