package org.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Client {
    private final Socket clientSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final int messages;

    public Client(String ip, int port, int messages) throws IOException {
        this.messages = messages;
        clientSocket = new Socket(ip, port);
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void sendMessage() throws IOException, ClassNotFoundException {
        String ready = (String) inputStream.readObject();

        if (!Objects.equals(ready, MessagesContent.READY)) {
            System.out.println("Error");
            return;
        }

        System.out.println("Send messages number");
        outputStream.writeObject(messages);
        String waiting = (String) inputStream.readObject();

        if (!Objects.equals(waiting, MessagesContent.READY_FOR_MESSAGES)) {
            System.out.println("Error");
            return;
        }

        for (int i = 1; i <= messages; i++) {
            Message message = new Message(i, "Content of Message" + i);
            System.out.println("Send " + message.getContent());
            outputStream.writeObject(message);
        }

        String finished = (String) inputStream.readObject();

        if (!Objects.equals(finished, MessagesContent.FINISHED)) {
            System.out.println("Error");
        }

        System.out.println("Connection successfully finished");
        inputStream.close();
        outputStream.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int port = 8088;
        int[] messagesNumbers = { 3, 5, 7, 2 };

        try {
            Client client = new Client(ip, port, 5);
            client.sendMessage();

//            List<Thread> clientsThreads new List<Thread>();
//            for (int number : messagesNumbers) {
//
//            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
