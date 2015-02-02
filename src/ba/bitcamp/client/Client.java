package ba.bitcamp.client;

import java.io.IOException;
import java.net.Socket;

import ba.bitcamp.graphic.GUIChat;

public class Client{
	
	private static final int port = 1717;
	private static final String host = "localhost";
	
	public static void main(String[] args) 
	{
		try {
			Socket client = new Socket(host, port);
			GUIChat chat = new GUIChat(client);
			Thread th = new Thread(chat);
			th.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
