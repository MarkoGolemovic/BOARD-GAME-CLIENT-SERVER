
/**
 * 121 Comp.Prob.Solv.II
 * Prof. Ante Poljicak
 *
 * Group 03 - Final  Project
 * @author Jorge Olavarria, Nikica Kovacevic, Marko Golemovic, Ivan Jerkovic
 * 
 */

import java.net.*;
import java.io.*;

public class Client {


	private Socket socket;
	private int port;
	private String server, username;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ClientChatGUI ccg;

	public Client(String server, int port, String username, ClientChatGUI ccg) {
		this.server = server;
		this.port = port;
		this.username = username;
		this.ccg = ccg;
	}
	
	/**
	 * method to connect properly to connect to the network 
	 */
	public boolean connect() {
		try {
			socket = new Socket(server, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
		}catch(UnknownHostException uhe) {
			System.out.println("connect() UnknownHostException ");
			uhe.printStackTrace();
			return false;
		}catch(ConnectException ce){
			System.out.println("Connection with the server not established");
			ce.getMessage();
			return false;
		}catch (IOException e) {
			System.out.println("connect() IO exception");
			e.printStackTrace();
			return false;
		}
		new getServerMsg().start();
		return true;
	}
	/**
	 * method to close the network connection properly
	 */
	public void disconnect() {
		try {
			socket.close();
			ois.close();
			oos.close();
		}catch(Exception e) {
			System.out.println("A problem with disconnect()");
			e.printStackTrace();
		}
		ccg.connectionFailure();
	}
	
	/**
	 * method that sends any object needed to the server and/or other clients
	 */
	public void sendInfo(Info info) {
		try {
			oos.writeObject(info);
			oos.flush();
		}catch(IOException io) {
			System.out.println("SendMsg() exception");
			io.printStackTrace();
		}
	}

	class getServerMsg extends Thread {
		/**
		 * Run method reads any objects sent to the client either if a message or a move.
		 * Also decides which player's turn it is, sets the decision made by the player and
		 * continuously checks if the there is a winner with the checkWin method
		 */
		public void run() {
			try {
				while(true) {
					Info info = (Info)ois.readObject();
					if(info instanceof ChatMessage) {
						ccg.addTimeMsg(info.getInfo());
					}else if(info instanceof Move) {
						String[] positions = info.getInfo().split(" ");
						if(positions.length < 2) {
							int numClients = Integer.parseInt(positions[0]);
							if(numClients == 1) {
								ccg.setYourTurn(false);
								System.out.println("Waiting for other player...");
							}
						}else {
							ccg.addCircle(Integer.parseInt(positions[0]), Integer.parseInt(positions[1]));
							ccg.setYourTurn(!ccg.isYourTurn());
							ccg.checkWin();
						}
					}
				}
			}catch (IOException e) {
				
			}catch (ClassNotFoundException cnf) {
				System.out.println("getServerMsg() Class not found exception");
				cnf.printStackTrace();
			}
		}
	}
}