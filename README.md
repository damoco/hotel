# hotel
运行测试: gradle cucumber

JDK版本: 16
## 编程风格说明
- 使用record等不可变对象简化线程安全问题, 不需要锁.
- 没有使用传统的OO或者FP风格, 使用了Data-Oriented programming(DOP)的风格.
- 混合了Kotlin与java用于增强交付能力.
- 测试使用了Kotlin + kotest assert库