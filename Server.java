
/**
 * 121 Comp.Prob.Solv.II
 * Prof. Ante Poljicak
 *
 * Group 03 - Final  Project
 * @author Jorge Olavarria, Nikica Kovacevic, Marko Golemovic, Ivan Jerkovic
 * 
 */

import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.text.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Server class that is continuously open to receive any objects
 */

public class Server extends JFrame implements ActionListener {

	private Vector<ThreadServer> vector = new Vector<ThreadServer>();
	private JPanel panelArea, panelNotMain;
	private SimpleDateFormat timer;
	private JTextArea textArea;
	private JButton bDisc;
	private ArrayList<Client> alClient;
	private ClientChatGUI client;
	private String address;
	private int port;

	public Server() {
		ServerSocket serverSocket = null;
		Socket socket = null;

		//----------------------------
		//---------Panel1-------------
		panelArea = new JPanel();
		//panelArea.setPreferredSize(new Dimension(300, 400));
		timer = new SimpleDateFormat("HH:mm:ss");
		String time = timer.format(new Date());


		textArea = new JTextArea(time + " Welcome Server Room \n \n"  ,20,25);
		try{
			textArea.append("getLocalHost: " + InetAddress.getLocalHost() + "\n" );
			textArea.append("getByName:    " + InetAddress.getByName("localhost"));
		}catch(UnknownHostException e){

		}
		//textArea.append();
		textArea.setEditable(false);

		//button for exiting 
		bDisc = new JButton("Exit Server");
		bDisc.addActionListener(this);

		//adding components to PanelArea
		panelArea.add(new JScrollPane(textArea));
		panelArea.add(bDisc);
		add(panelArea, BorderLayout.CENTER);

		//set JFrame properties
		this.setSize(350, 450);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("Server Side");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try{
			System.out.println("getLocalHost: " + InetAddress.getLocalHost());
			System.out.println("getByName:    " + InetAddress.getByName("localhost"));

			serverSocket = new ServerSocket(16789);

			while(true) {
				socket = serverSocket.accept();
				ThreadServer threadServer = new ThreadServer(socket);
				threadServer.start();
				vector.add(threadServer);
				threadServer.write(new Move(vector.size()+""));
				System.out.println("something");
			}//end of while loop
		}catch(BindException be) {
			System.out.println("Server already running");
			be.printStackTrace();
		}catch(IOException ioe) {
			System.out.println("IO Error");
			ioe.printStackTrace();
		}

	}//end of constructor
	
	//action performed for button exit clicked
    public void actionPerformed(ActionEvent ae) {
      
      //if button login is clicked 
      if(ae.getSource() == bDisc) {
         System.exit(0);
      }
      
    }//end actionPerformed


	class ThreadServer extends Thread {

		ObjectInputStream ois;
		ObjectOutputStream oos;
		Socket socket;
		Info info;

		public ThreadServer(Socket socket) {
			this.socket = socket;
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
				oos.flush();
			}catch(IOException ioe) {
				System.out.println("Thread IO Exception");
				ioe.printStackTrace();
			}//end of try/catch
			write(new ChatMessage("Welcome to the Chatroom."));
		}//end of ThreadServer method

		public void write(Object object) {
			try {
				oos.writeObject(object);
				oos.flush();
			}catch(IOException ioe) {

			}//end of try/catch
		}//end of write method

		/**
		 * notifyAll sends any object to the clients either for chat or move
		 */
		public void notifyAll(Object msg) {
			for(ThreadServer e : vector) {
				e.write(msg);
			}//end of for loop
		}//end of notifyAll method

		/**
		 * Keeps the server running continuously and ready to send any object
		 */
		public void run() {
			boolean running = true;
			while (running) {
				try {
					info = (Info) ois.readObject();
					notifyAll(info);
				}catch(IOException ioe) {

				}catch(ClassNotFoundException cnf) {
					System.out.println("Class Not Found Error");
					cnf.printStackTrace();
				}
			}//end while loop
		}//end of run method
	}//end of Thread class

	public static void main(String[] args) {
		Server server = new Server();
	}//end of main

} //end of Server class