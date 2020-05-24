# 计算机体系结构课程实验
使用Intellij IDEA构建SBT项目，将`SSSP.scala`和`PageRank.scala`文件放入`src/main/scala`目录下。在`build.sbt`中导入相关依赖。完成后即可运行两个scala文件。
### SSSP(single source shortest path)
* 运行前将`GraphLoader.edgeListFile`传入的字符串改为需要解析的源数据集所在的绝对路径
* 如果没有源数据集，可以选择使用在注释中给出的`GraphGenerators`生成随机图用于传入SSSP算法
* `SourceId`改为算法的起始节点
### PageRank
* 运行前将`GraphLoader.edgeListFile`传入的字符串改为需要解析的源数据集所在的绝对路径
* 如果没有源数据集，可以选择使用在注释中给出的`GraphGenerators`生成随机图用于传入PageRank算法
