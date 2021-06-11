package com.lizhi.netty.example.netty.dubborpc.provider;

import com.lizhi.netty.example.netty.dubborpc.publicinterface.HelloService;

/**
 * @author lizhi
 * @date: 2021/6/1 16:48
 */
public class HelloServiceImpl implements HelloService {
    private static int count = 0;
    @Override
    public String hello(String mes) {
        System.out.println("收到客户端消息=" + mes);
        //根据mes 返回不同的结果
        if(mes != null) {
            return "你好客户端, 我已经收到你的消息。消息为：[" + mes + "] ，第" + (++count) + " 次 \n";
        } else {
            return "你好客户端, 我已经收到你的消息 ";
        }
    }
}
