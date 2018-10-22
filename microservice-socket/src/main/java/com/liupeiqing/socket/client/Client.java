package com.liupeiqing.socket.client;

import java.io.*;
import java.net.Socket;

/**
 * 客户端
 * @author liupeqing
 * @date 2018/10/18 20:12
 */
public class Client {

    public static void main(String[] args) {
        try{
            //创建socket对象，第一个参数服务方的ip，第二个参数服务器的端口
            Socket socket = new Socket("localhost",8090);
            //获取一个输出流，向服务方发送请求信息
            OutputStream dataOutputStream =  socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(dataOutputStream);
            printWriter.print("你好，我是客户端，请给我信息。");
            printWriter.flush();
            socket.shutdownOutput();  //关闭输出流

            //获取一个输入流，接收服务端的信息
            InputStream dataInputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  //缓冲区
            String info = "";
            String temp = null;
            while ((temp = bufferedReader.readLine()) != null){
                info += temp;
                System.out.println("客户端接受的服务端的信息:" + info);
            }
            bufferedReader.close();
            inputStreamReader.close();
            dataInputStream.close();
            printWriter.close();
            dataOutputStream.close();
            socket.close();

        }catch (IOException e){
            e.printStackTrace();
        }finally {

        }
    }
}
