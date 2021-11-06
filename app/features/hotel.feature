Feature: Hotel

  Background:
    Given The hotel has 2 rooms
    And The following rooms are reserved:
      | date       | room |
      | 2021-11-06 | 2    |
      | 2021-11-07 | 1    |
      | 2021-11-07 | 2    |

  Scenario: Booking
  As a guest
  In able to booking a room
  I need find the available rooms on a given date & booking
    When I find available rooms on 2021-11-06
    Then I should be able to see the room list:
      | number |
      | 1      |
    When I book room 1
    Then it is included in my bookings list

  Scenario: Booking conflict
  As hotel system
  In able to ???
  I need notice user that the booking room is not available
    Given another guy booked room 1
    When I book room 1
    Then I should get warn that the room was booked
#Task
#Your task is to implement a simple hotel booking manager in Java, as a microservice API.
# The number of rooms should be configurable, and it should expose the following methods:
#1. A method to store a booking. A booking consists of a guest name, a room number, and a date.
#2. A method to find the available rooms on a given date.
#3. A method to find all the bookings for a given guest.
#Guidance
#1. Use only in-memory data structures; do not use a database.
#2. Do not use any framework or libraries in your solution.
#3. Provide tests with your solution (you may use libraries for the tests).
#4. Your solution should build with Maven or Gradle.
#5. Do not need to take into account the booking cancellation and guest check out.
#6. Test cases as comprehensive as possible.
#7. Please share github link of your solution with us.
#Extra credit
#Make your solution thread-safe.

#Job title:Senior Developer
#
#Location: SCB Tower, Pudong Disctrict, Shanghai
#The Role Responsibilities &Our Ideal Candidate
#The successful candidate will be a senior developer in an Agile team building end-to-end solutions with both in-house and vendor components under API and cloud-based technology.
#Domain:Equity Derivative Products for Banking Customers
#Mission
#Build scalable, reusable, time-critical applications and services with API architecture on Cloud platform under an agile delivery model.
#Contribute to CI/CD automation agenda throughout the whole software development cycle (build, test, package, provision, deploy, and monitor).
#Ensure best practice and quality of coding, via test automation and adoption of design patterns.
#Provide level 2 application support.
#
#Start-up mindset on resolving issues in all aspect of software development (Development, OS, Installation, DevOps Integration).
#Adhere to bank’s policy and control on software development and release.
#Strengths and capabilities
#Hands on experience in core Java
#Proven knowledge on application design/architecture on micro-services/API-first development/high-throughput/event-driven system, with good design patterns
#High code quality delivery which is defect free, maintainable and performance agnostic.
#Strong knowledge on modern DevOps methodologies and tools:
#Collaboration – JIRA, Confluence, BitBucket, Git
#Build - Jenkins, Maven, Docker
#Test – Junit, cucumber, sonarqube
#Package – Artifactory
#Provision – Terraform, Azure CLI
#Deploy – Rundeck, Ansible, RAT
#Some knowledge of development on Cloud platform (Azure or AWS)
#Some knowledge of ReactJS development
#Relevant financial (preferable banking) experience, or interest on financial services
#Soft Skills
#Excellent written and verbal communication skills. Able to communicate with business stakeholders.
#Outstanding problem-solving skills
#Proactive, self-starter, autonomous, self-motivated, multi-tasking, solution oriented with proven results

#职位名称：高级开发人员
#
#地点：渣打银行大厦， 上海浦东新区
#
#角色职责和我们的理想人选
#成功的候选人将是一个敏捷团队中的高级开发人员，在API和基于云的技术下，利用内部和供应商的组件建立端到端的解决方案。
#领域。面向银行客户的股票衍生产品
#任务
#1.在敏捷交付模式下，在云平台上建立可扩展、可重用、时间关键的应用程序和服务，并采用API架构。
#2.在整个软件开发周期（构建、测试、打包、供应、部署和监控）中为CI/CD自动化议程作出贡献。
#3.通过测试自动化和采用设计模式，确保编码的最佳实践和质量。
#4.提供2级应用支持。
#
#1.在软件开发的所有方面（开发、操作系统、安装、DevOps集成），以创业的心态解决问题。
#2.遵守银行关于软件开发和发布的政策和控制。
#
#职位要求
#具有核心Java的实践经验
#在微服务/API优先开发/高吞吐量/事件驱动系统的应用设计/架构方面有成熟的知识，有良好的设计模式。
#具有较高的代码质量，无缺陷，可维护，不受性能影响。
#对现代DevOps方法论和工具有深刻的认识。
#o协作 - JIRA, Confluence, BitBucket, Git
#o构建 - Jenkins, Maven, Docker
#o测试 - Junit, cucumber, sonarqube
#O Package - Artifactory
#o配置 - Terraform, Azure CLI
#o部署 - Rundeck, Ansible, RAT
#对云平台（Azure或AWS）的开发有一定了解
#对ReactJS的开发有一定了解
#有相关金融（最好是银行）经验，或对金融服务感兴趣
#软技能
#优秀的中英书面和口头沟通能力。能够与商业利益相关者沟通。
#出色的解决问题的能力
#积极主动，自我启动，自主，自我激励，多任务，以解决方案为导向，并取得良好效果
