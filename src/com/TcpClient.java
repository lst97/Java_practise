package com;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TcpClient {
    private int port;
    // IP address of local client and server
    private InetAddress ipLocal, ipServer;
    // Represents connection to server
    private Socket connection = null;

    // Constructor
    public TcpClient(String serverName, int port) {
        try {
            // Identify server
            ipServer = InetAddress.getByName(serverName);
            this.port = port;
        } catch (UnknownHostException e) {
            System.out.println("Server No Found: " + e);
            ipServer = null;
        }
    }

    // Return a reference to the server connection
    public Socket getConnection() {
        // Could not find server
        if (ipServer == null)
        {
            return null;
        }
        try {
            connection = new Socket(ipServer, port);
            ipLocal = InetAddress.getLocalHost();
        } catch (IOException e) {
            System.out.println("Establish Fail: " + e);
            connection = null;
        }
        return connection;
    }

    public void runClient() throws IOException {
        DataInputStream disFromServer;
        DataOutputStream dosToServer;
        String serverMsg;

        // Establish the input and output streams
        try {
            disFromServer = new DataInputStream(connection.getInputStream());
            dosToServer = new DataOutputStream(connection.getOutputStream());

            // Process messages from the Server.
            while (true) {
                System.out.print("Msg > ");
                Scanner objScanner = new Scanner(System.in);
                String input = objScanner.nextLine();

                dosToServer.writeUTF(input);
                serverMsg = disFromServer.readUTF();
                System.out.println(serverMsg);
            }
        } catch (IOException e) {
            connection.close();
            System.out.println("Connection Closed.");
        }
    }

    public String toString() {
        return "\nClient connected to:" + ipServer +   // The server's address
                "\nClient address is  :" + ipLocal;	// The client's address
    }
}
