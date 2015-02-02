package ba.bitcamp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ba.bitcamp.graphic.GUIChat;

public class Server {
	
	public static final int port = 1717;
	
	public static void startServer()
	{
		try {
			ServerSocket server = new ServerSocket(port);
			while(true)
			{
				String str = "Waiting...";
				System.out.println(str);
				Socket client = server.accept();
				GUIChat chat = new GUIChat(client);
				Thread th = new Thread(chat);
				th.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		startServer();
	}
}
