package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection implements Runnable {
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private Socket clientSocket;

    public ServerConnection(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
        this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            outputStream.writeObject(MessagesContent.READY);
            System.out.println(MessagesContent.READY);

            int n = (int) inputStream.readObject();
            System.out.println("Number of expected messages: " + n);
            outputStream.writeObject(MessagesContent.READY_FOR_MESSAGES);

            for (int i = 1; i <= n; i++) {
                Message message = (Message) inputStream.readObject();

                if (message != null) {
                    System.out.println("Message " + message.getNumber() + ": " + message.getContent());
                }
            }

            outputStream.writeObject(MessagesContent.FINISHED);
            System.out.println("Connection finished");
            inputStream.close();
            outputStream.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
