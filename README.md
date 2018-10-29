########################
模拟阿里的dubbo框架，自己简单实现dubbo的功能

基于spring-boot进行开发：主要是也能学习一下spring-boot

功能实现dubbo的基本的rpc使用

与ali的dubbo一致，采用xml配置的方式进行dubbo服务的发布


dubbo-master模块：dubbo的核心包
进行客户端和服务端注册的管理（这里就不用ZK了，就是单机）
实现基于TCP的RPC
TCP通讯采用mina


dubbo-client模块：客户端，应用引入client包进行客户端开发
实现dubbo.xml的配置解析 配置bean


dubbo-server模块：服务端，应用引入服务端包进行服务端开发
实现dubbo.xml的配置解析，配置bean

dubbo-common模块：用于spring注册

config



功能实现：
设计一个dubbo-master作为dubbo的服务器，提供注册功能（这里不采用ZK）
服务端开发引入dubbo-server，通过配置文件注册一个dubbo服务：
这里简单实现，注册成一个dubbo服务之后，会开通一个指定端口的TCP连接，等待dubbo-client的连接

客户端开发引入dubbo-client，通过配置文件调用一个dubbo服务：即连接服务端注册的TCP连接




