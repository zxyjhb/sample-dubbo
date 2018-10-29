########################
模拟阿里的dubbo框架，自己简单实现dubbo的功能

基于spring-boot进行开发：主要是也能学习一下spring-boot

功能实现dubbo的基本的rpc使用

与ali的dubbo一致，采用xml配置的方式进行dubbo服务的发布

主要知识点：
1。自定义的xml解析
通过编写xsd文件，（这个文件分本地和远程，一般会在本地的class里面存放一份）
实现spring提供的解析入口。实现自定义的xml解析配置
2。springbean的加载顺序：以及spring暴露接口的用法
详细可以参考 serviceBean和referenceBean
3。java动态代理的实现
4。socket/mina/netty通讯实现
5。zk的使用
6。代码设计的实现细节




dubbo-common模块：用于spring注册，实现dubbo.xml的配置解析，配置bean

dubbo-master模块：dubbo界面的核心包
进行客户端和服务端注册的管理（这里就不用ZK了，就是单机）
实现基于TCP的RPC
TCP通讯采用socket/mina/netty

dubbo-demo-client模块：客户端demo的客户端模块，引入dubbo.jar,仿dubbo配置
dubbo-demo-server模块：服务端demo的服务端模块,引入dubbo.jar,仿dubbo配置


功能实现：
设计一个dubbo-master作为dubbo的服务器，提供注册功能（这里不采用ZK）
服务端开发引入dubbo-server，通过配置文件注册一个dubbo服务：
这里简单实现，注册成一个dubbo服务之后，会开通一个指定端口的TCP连接，等待dubbo-client的连接

客户端开发引入dubbo-client，通过配置文件调用一个dubbo服务：即连接服务端注册的TCP连接




