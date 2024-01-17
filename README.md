# AimRobot-Kookbot 使用帮助

## 阅前须知
**使用Kookbot，请确保你有一定的计算机动手能力！**
**使用Kookbot，请确保你有一定的计算机动手能力！**
**使用Kookbot，请确保你有一定的计算机动手能力！**

当前项目不会提供Jar包，你需要自行编译（Java 17）

## 部署说明

### 创建机器人
1. 前往 [Kook开发者中心](https://developer.kookapp.cn/app/index) 创建你的机器人应用，
   ![教程](_staticres/1.png "1")
   ![教程](_staticres/2.png "1")
2. 记录下机器人的 `Token` 和 `ClientId`
   ![教程](_staticres/3.png "1")
   ![教程](_staticres/4.png "1")
3. 设置机器人拥有的权限
   ![教程](_staticres/5.png "1")
4. 邀请机器人至你的Kook
   ![教程](_staticres/6.png "1")
   ![教程](_staticres/7.png "1")

### 部署机器人程序 （以Ubuntu系统演示）
1. 将机器人程序的Jar包放入一个单独的文件夹中，并同级目录中创建三个文件
   1. **application.yml** 参考 [application.yml文件内容](src/main/resources/application.yml)
   2. **application-aimrobot.yml** 参考 [application-aimrobot.yml文件内容](src/main/resources/application-aimrobot.yml)
   3. **aimrobot.bot.json 文件**参考 [aimrobot.bot.json文件内容](src/main/resources/simbot-bots/aimrobot.bot.json), **需要将先前的 `Token` 和 `ClientId` 填写至这个文件中**
   ![教程](_staticres/8.png "1")

2. 启动机器人程序即可 
```shell
# 参考示范
root@example:/home/kookbot# /home/jdk-17.0.2/bin/java -jar kookbot-0.0.1-SNAPSHOT.jar
```

## 如何获取 `Kook服务器ID` 和 `Kook用户ID` ？
![教程](_staticres/9.png "1")
![教程](_staticres/10.png "1")
![教程](_staticres/11.png "1")
![教程](_staticres/12.png "1")
