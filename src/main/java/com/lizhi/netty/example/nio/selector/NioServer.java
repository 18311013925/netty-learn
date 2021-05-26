package com.lizhi.netty.example.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.security.Key;
import java.util.Iterator;
import java.util.Set;

/**
 * 编写一个 NIO 入门案例，实现服务器端和客户端之间的数据简单通讯（非阻塞）
 * 目的：理解 NIO 非阻塞网络编程机制
 * @author lizhi
 * @date: 2021/5/21 9:30
 */
public class NioServer {
    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Selecor对象
        Selector selector = Selector.open();
        //绑定一个端口6666, 在服务器端监听
        serverSocketChannel.bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把 serverSocketChannel 注册到  selector 关心 事件为 OP_ACCEPT       pos_1
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("注册后的selectionkey 数量=" + selector.keys().size()); // 1

        //循环等待客户端连接
        while (true) {
            //这里我们等待1秒，如果没有事件发生, 返回
            if (selector.select(1000) == 0) { //没有事件发生
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }

            //如果返回的>0, 就获取到相关的 selectionKey集合
            //1.如果返回的>0， 表示已经获取到关注的事件
            //2. selector.selectedKeys() 返回关注事件的集合
            //   通过 selectionKeys 反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys 数量 = " + selectionKeys.size());

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                //获取到SelectionKey
                SelectionKey selectionKey = iterator.next();
                //根据key 对应的通道发生的事件做相应处理

                if (selectionKey.isAcceptable()) {//如果是 OP_ACCEPT, 有新的客户端连接
                    //该该客户端生成一个 SocketChannel
//                    accept 是阻塞的，但此时已经有链接请求；
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 生成了一个 socketChannel " + socketChannel.hashCode());
                    //将  SocketChannel 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketChannel 注册到selector, 关注事件为 OP_READ， 同时给socketChannel
                    //关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接后 ，注册的selectionkey 数量=" + selector.keys().size()); //2,3,4..
                }
                if (selectionKey.isReadable()) { //发生 OP_READ
                    //通过key 反向获取到对应channel
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
//                    channel.read(buffer); // 如果客户端突然断开链接导致报错
                    try{
                        channel.read(buffer);
                    }catch(IOException e){
                        selectionKey.cancel();
                        System.out.println(selectionKey.toString() + "断开了链接");
//                        channel.socket().close();
//                        channel.close();
//                        return;
                    }
                    System.out.println("form 客户端 " + new String(buffer.array()));

                }
                //手动从集合中移动当前的selectionKey, 防止重复操作
                iterator.remove();

            }
      /*      serverSocketChannel.close();
            selector.close();*/
        }

    }
}
