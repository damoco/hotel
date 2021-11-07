# hotel
![example workflow](https://github.com/damoco/hotel/actions/workflows/gradle.yml/badge.svg)

运行验收测试: gradle cucumber

运行单元测试: gradle test

JDK版本: 16
## 代码风格
- 使用不可变对象简化线程安全问题, 不需要悲观锁.
- 为了避免复杂性, 没有使用传统的OO或者FP风格, 使用了Data-Oriented programming(DOP)的风格.
- 用Kotlin增强交付能力.
- 测试使用了Kotlin + kotest assert库

## 分支
web-server分支准备写一个简易的http server, 还未完成.