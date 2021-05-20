package com.lizhi.netty.example.NIO;

import io.netty.buffer.ByteBuf;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用一个 Buffer 完成文件读取、写入
 * 使用 FileChannel（通道）和方法 read、write，完成文件的拷贝
 * 拷贝一个文本文件 1.txt，放在项目下即可
 * 代码演示
 * @author lizhi
 * @date: 2021/5/19 17:25
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("d:\\file01.txt");
        FileChannel readChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel writeChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true) { //循环读取
            //这里有一个重要的操作，一定不要忘了
            /*
            public final Buffer clear() {
                position = 0;
                limit = capacity;
                mark = -1;
                return this;
            }
            */
            byteBuffer.clear();//清空 buffer
            int read = readChannel.read(byteBuffer);
            System.out.println("read = " + read);
            if (read == -1) {//表示读完
                break;
            }
            //将 buffer 中的数据写入到 fileChannel02--2.txt
            byteBuffer.flip();
            writeChannel.write(byteBuffer);
        }




        fileInputStream.close();
        fileOutputStream.close();

    }
}
