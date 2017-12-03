package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void sendBroadcastMessage(Message message) {
        try {
            for (Map.Entry<String, Connection> pair : connectionMap.entrySet()) {
                pair.getValue().send(message);
            }
        } catch (IOException e) {
            ConsoleHelper.writeMessage("SEND PROBLEMS...");
        }
    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            String clientName = "";
            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message answer = connection.receive();
                if (answer.getType().equals(MessageType.USER_NAME)) {
                    clientName = answer.getData();
                    if (!clientName.isEmpty() && !connectionMap.containsKey(clientName)) {
                        connectionMap.put(clientName, connection);
                        connection.send(new Message(MessageType.NAME_ACCEPTED));
                        return clientName;
                    }
                }
            }
        }

        private void sendListOfUsers(Connection connection, String userName) throws IOException {
            for (Map.Entry<String, Connection> pair : connectionMap.entrySet()) {
                String name = pair.getKey();
                if (userName != name) {
                    connection.send(new Message(MessageType.USER_ADDED, name));
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            String stringMessage = "";
            while (true) {
                Message message = connection.receive();
                if (message.getType() == MessageType.TEXT) {
                    stringMessage = userName + ": " + message.getData();
                    sendBroadcastMessage(new Message(MessageType.TEXT, stringMessage));
                } else {
                    ConsoleHelper.writeMessage("TEXT PROBLEMS...");
                }
            }
        }

        @Override
        public void run() {
            String userName = "";
            ConsoleHelper.writeMessage("CONNECT" + socket.getRemoteSocketAddress());
            SocketAddress socketAddress = null;
            try (Connection connection = new Connection(socket)) {
                userName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, userName));
                sendListOfUsers(connection, userName);
                serverMainLoop(connection, userName);
            } catch (Exception e) {
                ConsoleHelper.writeMessage("CONNECT " + socketAddress + " PROBLEM");
            } finally {
                if (userName != null && !userName.isEmpty() ) {
                    connectionMap.remove(userName);
                    sendBroadcastMessage(new Message(MessageType.USER_REMOVED, userName));
                }
            }
            ConsoleHelper.writeMessage("CONNECT " + socketAddress + " FINISHED");
        }
    }

    public static void main(String[] args) throws IOException {
        ConsoleHelper.writeMessage("INPUT PORT");
        int port = ConsoleHelper.readInt();
        ServerSocket serverSocket = new ServerSocket(port);
        ConsoleHelper.writeMessage("SERVER - RUNNING");
        try {
            while (true) {
                Socket socketConnect = serverSocket.accept();
                Handler handler = new Handler(socketConnect);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            serverSocket.close();
        }
    }
}
