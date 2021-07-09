package com.bat.jyzh.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * Netty {@link io.netty.buffer.ByteBuf} API
 *
 * @author ZhengYu
 * @version 1.0 2021/7/9 15:15
 **/
@Slf4j
public class ByteBufApi {
    public static void main(String[] args) {
        zeroCopy();
    }

    private static void zeroCopy() {
        byte[] bytes1 = "Hello".getBytes(StandardCharsets.UTF_8);
        byte[] bytes2 = "Netty".getBytes(StandardCharsets.UTF_8);

        // byteBuf1 指向 bytes1, 避免了内存拷贝
        ByteBuf byteBuf1 = Unpooled.wrappedBuffer(bytes1);
        log.info("byteBuf1: {}, readableBytes: {}", byteBuf1, byteBuf1.readableBytes());

        // byteBuf2 是一个 CompositeByteBuf, 避免了内存拷贝
        ByteBuf byteBuf2 = Unpooled.wrappedBuffer(bytes1, bytes2);
        log.info("byteBuf2: {}, readableBytes: {}", byteBuf2, byteBuf2.readableBytes());

        // CompositeByteBuf 动态添加
        CompositeByteBuf byteBuf3 = Unpooled.compositeBuffer();
        byteBuf3.addComponent(true, Unpooled.wrappedBuffer(bytes1));
        byteBuf3.addComponent(true, Unpooled.wrappedBuffer(bytes2));
        log.info("byteBuf3: {}, readableBytes: {}", byteBuf2, byteBuf3.readableBytes());
    }
}
