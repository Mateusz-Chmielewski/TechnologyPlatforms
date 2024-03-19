package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final ServerSocket serverSocket;
    private List<Thread> threads;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        threads = new ArrayList<>();
    }

    public void listen() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread connection = new Thread(new ServerConnection(clientSocket));
            connection.start();
            threads.add(connection);
            threads = threads.stream().filter(Thread::isAlive).toList();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int port = 8088;

        try {
            Server server = new Server(port);
            server.listen();
            server.stop();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
