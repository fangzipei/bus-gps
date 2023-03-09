package com.zonefun.backend.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @ClassName SocketTest
 * @Description TODO
 * @Date 2022/9/23 15:50
 * @Author ZoneFang
 */
@Slf4j
public class SocketTest {

    @Test
    public void testSocket() throws IOException, InterruptedException {
        //1. 连接服务端 (ip , 端口）
        //解读:连接本机的 9999端口, 如果连接成功，返回Socket对象
        Socket socket = new Socket(InetAddress.getLocalHost(), 8282);
        System.out.println("客户端 socket = " + socket.getClass());
        //2. 连接上后，生成Socket, 通过socket.getOutputStream()
        //得到和 socket对象关联的输出流对象
        OutputStream outputStream = socket.getOutputStream();
        //3. 通过输出流，写入数据到 数据通道
        outputStream.write("/combAbstract/getMerchantAsset".getBytes());
        //4. 关闭流对象和socket, 必须关闭
        outputStream.close();
        socket.close();
        System.out.println("客户端退出...");
    }
}
