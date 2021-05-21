package com.lizhi.netty.example.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 使用 FileChannel（通道）和方法 transferFrom，完成文件的拷贝
 * 拷贝一张图片
 * @author lizhi
 * @date: 2021/5/20 14:59
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("01.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("2.jpg");

        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel descChannel = fileOutputStream.getChannel();
        // 图片01 复制到图片2.jpg
        descChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

        fileInputStream.close();
        fileOutputStream.close();

    }
}

