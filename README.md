## GuestLiang’s coding time: [![wakatime](https://wakatime.com/badge/user/0985cb7f-21b8-4ea5-86a4-5e6ba93cb575/project/018ece31-86b2-4427-922b-0c02f2d86060.svg)](https://wakatime.com/badge/user/0985cb7f-21b8-4ea5-86a4-5e6ba93cb575/project/018ece31-86b2-4427-922b-0c02f2d86060)

## 使用方法
git clone 整个项目到本地，然后在ChildBank文件夹下执行   
```java
java -cp childBank.jar Main
```
我使用的java版本是   
```
Picked up JAVA_TOOL_OPTIONS: -Dfile.encoding=UTF-8
java 21 2023-09-19 LTS
Java(TM) SE Runtime Environment (build 21+35-LTS-2513)
Java HotSpot(TM) 64-Bit Server VM (build 21+35-LTS-2513, mixed mode, sharing)
```

## 数据
数据存储在data文件夹下，后缀copy是备份，建议不动   
task和user以json格式存储   

## 进度
基本完成child页面，使用user里的child账户(isParent=false)登录可以进行操作   
parent页面还未完成，使用user里的parent账户(isParent=true)登录是只有背景图   
