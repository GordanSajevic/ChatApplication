package ba.bitcamp.graphic;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUIChat implements Runnable{
	
	private JTextArea area;
	private JTextField field;
	private Socket connection;
	private InputStream in;
	private OutputStream out;
	
	public GUIChat(Socket connection) 
	{
		this.connection = connection;
		try {
			in = connection.getInputStream();
			out = connection.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame window = new JFrame();
		window.setSize(300, 400);
		area = new JTextArea();
		area.setEditable(false);
		field = new JTextField();
		field.setPreferredSize(new Dimension(210, 50));
		JButton button = new JButton("Send");
		JScrollPane scrollPane = new JScrollPane(area);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(290, 310));
		sendMessage(button);
		JPanel panel = new JPanel();
		panel.add(scrollPane);
		panel.add(field);
		panel.add(button);
		window.add(panel);
		window.setVisible(true);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void sendMessage(JButton button)
	{
		button.addActionListener(new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				message();
			}
		});
		
		field.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyChar() == KeyEvent.VK_ENTER)
				{
					message();
				}
			}
		});
	}
	
	public void listenForNetwork() throws IOException
	{
		BufferedReader input = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while((line = input.readLine()) != null)
		{
			if (!line.equals(""))
			{
				area.append("Client: " + line + "\n");
				line = null;
			}
		}
	}
	
	private void message()
	{
		String str = field.getText();
		if (!str.equals(""))
		{	
			str += "\n";
			area.setText(area.getText() + "ME: " + str);
			area.setLineWrap(true);
			try {
				out.write(str.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			field.setText(null);
			
		}
	}

	@Override
	public void run() 
	{
		try {
			listenForNetwork();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
