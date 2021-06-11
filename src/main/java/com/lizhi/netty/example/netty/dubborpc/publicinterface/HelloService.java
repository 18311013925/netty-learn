package com.lizhi.netty.example.netty.dubborpc.publicinterface;

/**
 * @author lizhi
 * @date: 2021/6/1 16:47
 * 这个是接口，是服务提供方和 服务消费方都需要
 */
public interface HelloService {
    String hello(String mes);
}
