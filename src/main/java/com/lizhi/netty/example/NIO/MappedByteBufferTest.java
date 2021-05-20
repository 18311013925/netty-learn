package com.lizhi.netty.example.NIO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer，可以让文件直接在内存（堆外的内存）中进行修改，而如何同步到文件由 NIO 来完成。【举例说明】
 * @author lizhi
 * 说明 1.MappedByteBuffer 可让文件直接在内存（堆外内存）修改,操作系统不需要拷贝一次
 * @date: 2021/5/20 15:20
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws IOException {
        // 可读可写
        RandomAccessFile randomAccessFile = new RandomAccessFile("mappedByteBuffer.txt", "rw");

        FileChannel fileChannel = randomAccessFile.getChannel();
        /**
         * 参数 1:FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数 2：0：可以直接 修改 的起始位置
         * 参数 3:5: 是映射到内存的大小（不是索引位置），即将 1.txt 的多少个字节映射到内存
         * 可以直接修改的范围就是 0-5
         * 实际类型 DirectByteBuffer
         */
        // 将channel 中的0-5范围内的数据映射到内存
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 2, 6);
        final int remaining = mappedByteBuffer.remaining();
        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) 'e');
        mappedByteBuffer.put(5, (byte) 'r');
        mappedByteBuffer.put(8, (byte) 'l'); //IndexOutOfBoundsException
        fileChannel.write(mappedByteBuffer);
        randomAccessFile.close();
        fileChannel.close();
        System.out.println("修改成功~~");
    }
}
