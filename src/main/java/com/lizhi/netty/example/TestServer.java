package com.lizhi.netty.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class TestServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();  //相当于两个死循环
        ServerBootstrap serverBootstrap = new ServerBootstrap();  // 相当于一个服务端的一个雷
    }
}
