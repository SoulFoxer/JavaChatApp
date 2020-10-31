package com.soulfoxer;

import android.widget.TextView;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {

    private InetSocketAddress inetaddress;
    private Socket clientSocket;
    private String username;
    private String ipadress;
    private int PORT;
    private String responseFromServer;
    private String fullChatContent;
    private TextView messageHistory;


    public Client(String username, String ipadress, int PORT, TextView messageHistory) {
        this.username = username;
        this.ipadress = ipadress;
        this.PORT = PORT;
        this.messageHistory = messageHistory;
        connect();
        listenForMessage();
    }

    private void setSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void connect() {
        try {
            Socket client = new Socket();
            setSocket(client);
            this.inetaddress = new InetSocketAddress(ipadress, PORT);
            client.connect(inetaddress, 5000);
        } catch (IOException e) {
            System.out.println("Client couldn´t connect to server");
        }
    }


    public void sendMessage(String message) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
            writer.write(username + ": " + message + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void listenForMessage() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {


                    if (clientSocket != null) {
                        if (clientSocket.isConnected() && !clientSocket.isClosed()) {
                            System.out.println("Ich lese...");
                            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));) {
                                responseFromServer = null;
                                while ((responseFromServer = reader.readLine()) != null) {
                                    messageHistory.setText(messageHistory.getText()+responseFromServer+ "\n");
                                }
                                //messageHistory.setText(fullChatContent);
                                clientSocket.close(); //
                                System.out.println("fertig mit lesen");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }).start();
    }
}
