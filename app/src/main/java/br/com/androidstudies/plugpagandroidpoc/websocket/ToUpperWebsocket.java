package br.com.androidstudies.plugpagandroidpoc.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import br.com.androidstudies.plugpagandroidpoc.TaskHandler;
import br.com.androidstudies.plugpagandroidpoc.task.TerminalPaymentTask;
import br.com.androidstudies.plugpagandroidpoc.websocket.mapper.PlugPagPaymentDataMapper;
import br.com.androidstudies.plugpagandroidpoc.websocket.mapper.WebsocketInMapper;
import br.com.androidstudies.plugpagandroidpoc.websocket.model.WebsocketIn;
import br.com.uol.pagseguro.plugpag.PlugPagPaymentData;

public class ToUpperWebsocket extends WebSocketServer {

    private TaskHandler taskHandler;


    public ToUpperWebsocket(int port, TaskHandler taskHandler) {
        super(new InetSocketAddress(port));
        this.taskHandler = taskHandler;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conn.send("Welcome to the server!"); //This method sends a message to the new client
        broadcast("new connection: " + handshake.getResourceDescriptor()); //This method sends a message to all clients connected
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        broadcast(conn + " has left the room!");
        System.out.println(conn + " has left the room!");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(conn + ": " + message);
        WebsocketIn websocketIn = WebsocketInMapper
                .mapperToWebsocketIn(message);
        PlugPagPaymentData plugPagPaymentData = PlugPagPaymentDataMapper
                .mapperToPlugPagPaymentData(websocketIn);
        try {
            new TerminalPaymentTask(taskHandler, conn)
                    .execute(plugPagPaymentData);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        broadcast(message.array());
        System.out.println(conn + ": " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
        if (conn != null) {
        }
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
    }
}
