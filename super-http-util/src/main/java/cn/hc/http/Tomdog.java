package cn.hc.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Tomdog {
    private ServerSocket server;

    public static void main(String[] args) throws IOException {
        Tomdog tomdog = new Tomdog(8080);
        tomdog.start();
    }

    public Tomdog(int port) throws IOException {
        try {
            server = new ServerSocket(port);
            System.out.println("tomdog start:" + port);
        } catch (IOException e) {
            server.close();
        }
    }

    public void start() throws IOException {
        this.receive(server);
    }

    public void receive(ServerSocket server) throws IOException {
        while (true) {
            Socket socket = server.accept();
            OutputStream response = socket.getOutputStream();
            InputStream  request  = socket.getInputStream();
            String hello = "GET HTTP/1.1\r\n" +
                    "Content-Type: text/html;charset=utf-8\r\n" +
                    "\n\n" +
                    "<h5>hello tomdog</h5>";

            response.write(hello.getBytes());
            response.flush();
            socket.close();
            System.out.println("over");
        }

    }
}

