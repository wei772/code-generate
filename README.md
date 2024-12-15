**code-generate是一个通用的代码生成工具**，支持从各种元数据，通过定义模板生成需要的代码，减少低级重复的编码工作。目前支持通过数据库元数据生成业务对象、数据访问对象等。

## 项目地址
gitee:  https://gitee.com/wei772/code-generate

github: https://github.com/wei772/code-generate

## 使用方法

1. 配置模板仓库，在 src/resources/templateRepository 目录下创建模板仓库或使用现有模板仓库，详情参考entityDemo仓库

1. 运行 ` mvn -U clean install` 生成code-generate.jar包

1. 使用Java17+运行jar包  或者单元测试TestCommandLineApplication或者 CommandLineApplication运行

* 使用entity代码生成工作类，并且使用jsonFileReader运行（该方式主要用于测试，不建议实际使用）

编写实体定义Json，参考definition.json

运行jar包

Windows下建议使用Cmd,PowerShell下面脚本运行有问题

```
 java -jar code-generate.jar -Dtemplate.repository=entityDemo -Dgenerate.output=D:\source\code-generate\src\test\resources\generateResult -Dgenerate.basePackage=cn.cli -Dgenerate.author=liwei -Dgenerate.tags=mysql,jdbc -Dentity.reader=jsonFile -Dentity.reader.json.file=D:\source\code-generate\target\test-classes\entityGenerate\userDefinition.json
```

使用配置文件运行jar包

```
 java -jar code-generate.jar "-c=D:\source\code-generate\target\test-classes\config\json.properties"
```

* 使用JdbcFileReader运行

创建数据库和表

运行jar包

```
java  -jar code-generate.jar -Dtemplate.repository=entityDemo  -Dgenerate.output=D:\source\code-generate\src\test\resources\generateResult  -Dgenerate.basePackage=cn.cli.jdbc  -Dentity.reader=jdbc  -Dentity.names=user  -Dentity.reader.jdbc.url=jdbc:mysql://localhost:3306/code_generate  -Dentity.reader.jdbc.user=root  -Dentity.reader.jdbc.password=123456
```

## 代码生成的优缺点

代码生成的最大优点就是减少低级重复的编码工作。
但是也有不少缺点。

* 首先它第一次生成容易，一旦修改过，生成就会困难很多。如果不修改生成代码可以减少影响
* 其次，尤其是比较重量级的生成容易促使开人人员直接使用生成的代码，而不是根据实际需求去调整代码
* 还有一个比较重大弊端，会隐藏架构的垃圾设计，给垃圾架构打补丁。 比如一个实体生成几十个相关类，如果要手写只要是程序员就会拒绝这种垃圾架构，
  但是如果有代码生成可能就勉强能接受。
  
  但是这样的架构是难以修改的，生成代码工具只会是温水煮青蛙，还不如不要。
  很多看起来很有用的技术都存在同样的问题，比如依赖注入框架，很容易写出有复杂的依赖的类而不自知。技术手段无法拯救垃圾的设计，只会把问题隐藏的更深点，变得更严重

**代码生成工具需要慎用，尽可能优化架构与设计，实在没办法避免的重复劳动才考虑使用。**

## 测试驱动开发

本项目使用测试驱动开发的方法开发

### 效果与感受

#### 流畅的组织所有开发活动的技术
测试驱动开发这种先写测试再开发的方式很流畅。

* 不要绞尽脑汁再脑中、用文字还有图去思考设计，这种方法浪费时间，想象力也无法有效发挥。
* 也不会一上来就编码，编写难以执行代码，代码基本没有设计，想到哪写到哪。
* 使用单元测试可以持续重构，一直保持设计和编码变得更好的

#### 自底向上
本项目中体会到测试驱动开发是一种自底向上的设计和开发方式，从明确和细微的方向开始一直到最复杂的问题，直到完成最终目标。

遇到新的并且复杂的事情，确实很难一下自顶向下给出比较完善设计方案，自底向上提供了一种一步一步解决部分问题，不断集成最终解决所有问题的方式。

#### 测试各个层次

测试驱动开发能够对一个概念在各个层级上都很好的测试和设计，例如targetLanguage就是从EntityType到Property以及Entity最后的CodeGenerate

从最高层到最低层设计和测试的。一旦那一层出现问题，只要看哪些测试用例异常都可以分析出哪里出问题，如果高层没有问题，那么问题就是最底层没有传好值，
或者改动高层的值导致的。

然后就有了Entity更新targetLanguage,PropertyType也会更新targetLanguage的实现，这能大大降级了调试时间

#### 单元测试的代码代码量

单元测试的代码与实现的代码量差不多，某次统计代码行，实现 1714、单元测试 1400 （使用 IDEA Statistic插件统计）。

看起来增加了工作量，实际上却不是。减少了调试的时间，而且代码更加简洁，也是重构的基础，极大了减少了bug量。大大减少了开发和维护成本


### 测试驱动开发本质

下面一些经典数据的一些论述，涉及到测试驱动开发本质

《测试驱动开发 (Kent Beck) 》当中有几段话让我印象深刻，体现了这种方法的核心作用

* 而我从书本上学到的却恰好相反:“编码为今天，设计为明天。”而测试驱动开发看起来已经彻底推翻了这一论点: **“为明天编码，为今天设计。”** 。
* 测试(Test)--自动、具体、切实的测试。按一个键就可以让测试运行。具有讽刺意味的是**测试驱动开发不是一种测试技术(Cunningham
  Koan)。 它是一种分析技术、设计技术，更是一种组织所有开发活动的技术**。

《敏捷整洁之道：回归本源》关于**复式记账**的论述

* 会计师们在1000年前发明了一条法则，并将其称为复式记账。每笔交易会写入账本两次:在一组账户中记一笔贷项，然后相应地在另一组账户中记为借项。
  这些账户最终汇总到收支平衡表文件中，用总资产减去总负债和权益。差额必须为零。如果不为零，肯定就出错了
* **复式记账与TDD这两种纪律是等效的。它们都具有相同的功用:在极其重要的文档中避免错误，确保每个符号都正确。**
  尽管编程对社会来说已经必不可少，
  但我们还没有用法律强制实施 TDD。可是，既然编写糟糕的软件已经造成了生命财产损失，立法还会远吗?

### 步骤

首先编写任务清单，一般包含设计想法、要实现用例、重构任务等等，将TODO的事情要一个简单的文档记录，整个过程比较随意，有价值的想法就记录下来，完成之后将对应的任务划上删除线。

然后是具体编写过程

1. 从任务清单中挑选任务,针对任务编写不通过的单元测试，包括无法编译和运行错误的用例
2. 使单元测试运行通过
3. 重构现有代码，使设计更佳。

每次写代码都重复这3个步骤，直到没有需要完成的任务。

## 包结构和主要类介绍

讨论实现细节

### entity包

主要包含实体定义相关类 Entity、Property、EntityType。

#### entity.reader包

实体代码生成读取实体配置
目前支持的配置类型

* jsonFile ,从json文件中读取配置的元数据
* jdbc ,使用jdbc从mysql与oracle等数据库读取元数据

### template包

对应模板引擎和模板实例
目前支持的模板引擎有

* velocity,使用详情请参考 https://velocity.apache.org/engine/2.4.1/user-guide.html

### param包

支持从配置文件中读取参数

* 支持@file参数,在文件里设置可以指定文件生成名
* 支持@folder，在文件里设置可以指定文件生成的目录,一般配合output参数使用。

### generate包

代码生成核心类

* CodeGenerate,代码生成的人口，读取所有代码生成相关参数

* CodeGenerateWork，真正去执行代码生成的工作类
  CodeGenerateWork具体实现类
* EntityGenerateWork,通过实体配置读取模板文件生成相关文件

















