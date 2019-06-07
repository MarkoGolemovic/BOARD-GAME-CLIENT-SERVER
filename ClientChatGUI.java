
/**
 * 121 Comp.Prob.Solv.II
 * Prof. Ante Poljicak
 *
 * Group 03 - Final  Project
 * @author Jorge Olavarria, Nikica Kovacevic, Marko Golemovic, Ivan Jerkovic
 * 
 */

import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * ClientChatGUI contains all information including the entire gui of the game, the chat,
 *  moves, decision for winner, score keeping, positioning selection for each piece, change
 *  of background color of the board and color of objects.
 */

public class ClientChatGUI extends JFrame implements ActionListener, KeyListener{

	//global variables
	private JPanel panelArea, panelLogin, board, score;
	private JLabel redScore, orangeScore;
	private JTextArea textArea;
	private SimpleDateFormat timer;
	private JTextField messageField, serverField, portField, userField;
	private JButton[][] buttons = new JButton[6][7];
	private JButton bLogin, bEnter, exit, restart, scoreRestart;
	private ImageIcon redCircle, orangeCircle;

	private int redWinCounter;
	private int orangeWinCounter;

	public static final String HOST = "localhost";
	public static final int PORT = 16789;

	private Client client;
	private String address,username;
	private int port;

	private boolean connection = false;
	private boolean yourTurn = true;

	public boolean isYourTurn() {
		return yourTurn;
	}

	public void setYourTurn(boolean yourTurn) {
		this.yourTurn = yourTurn;
	}

	ImageIcon circleColor = orangeCircle;

	//constructor
	public ClientChatGUI(String _address, int _port){

		address = _address;
		port = _port;

		//----------------------------
		//---------Panel1-------------

		panelLogin = new JPanel();
		add(panelLogin, BorderLayout.NORTH);
		JLabel serverAdd = new JLabel("Server Address: ");
		serverField = new JTextField(address, 10);

		JLabel portAdd = new JLabel("Port Number: ");
		portField = new JTextField((""+port),10);

		JLabel userAdd = new JLabel("Enter Your Username: ");
		userField = new JTextField((""),10);

		bLogin = new JButton("Login");
		bLogin.setBackground(new Color(255, 220, 0));
		bLogin.addActionListener(this);

		//adding components to login panel NORTH
		panelLogin.add(serverAdd);
		panelLogin.add(serverField);
		panelLogin.add(portAdd);
		panelLogin.add(portField);
		panelLogin.add(userAdd);
		panelLogin.add(userField);
		panelLogin.add(bLogin);


		//----------------------------
		//---------Panel2-------------
		panelArea = new JPanel();
		panelArea.setPreferredSize(new Dimension(300, 400));
		add(panelArea, BorderLayout.EAST);
		textArea = new JTextArea(null,26,25);
		textArea.setEditable(false);

		timer = new SimpleDateFormat("HH:mm:ss");

		//adding components to PanelArea
		panelArea.add(new JScrollPane(textArea));

		messageField = new JTextField(15);

		bEnter = new JButton("Enter");
		bEnter.setBackground(new Color(255, 220, 0));
		bEnter.addActionListener(this);

		panelArea.add(messageField);
		panelArea.add(bEnter);

		//----------------------------
		//---------Panel3-------------

		redCircle = new ImageIcon("red.png");
		orangeCircle = new ImageIcon("orange.png");

		score = new JPanel();
		score.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(score, BorderLayout.SOUTH);
		exit = new JButton("Exit");
		exit.setBackground(new Color(255, 220, 0));
		restart = new JButton("Clear the field");
		restart.setBackground(new Color(255, 220, 0));
		scoreRestart = new JButton("Restart the scoreline");
		scoreRestart.setBackground(new Color(255, 220, 0));
		redScore = new JLabel("Your wins: " + redWinCounter);
		redScore.setFont(new Font("Serif", Font.BOLD, 20));
		redScore.setForeground(Color.RED);
		orangeScore = new JLabel("Opponent wins: " + orangeWinCounter);
		orangeScore.setFont(new Font("Serif", Font.BOLD, 20));
		orangeScore.setForeground(Color.ORANGE);
		score.add(exit);
		score.add(restart);
		score.add(scoreRestart);
		exit.addActionListener(this);
		restart.addActionListener(this);
		scoreRestart.addActionListener(this);
		score.add(redScore);
		score.add(orangeScore);

		//----------------------------
		//---------Panel4-------------

		board = new JPanel();
		board.setLayout(new GridLayout(6, 7));
		add(board, BorderLayout.CENTER);

		/**
		 * double for loop that creates a 2D array of 6x7 dimensions that is made entirely of buttons.
		 */
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){

				buttons[i][j] = new JButton();
				buttons[i][j].addActionListener(this);
				buttons[i][j].setBackground(Color.WHITE);
				board.add(buttons[i][j]);

			}
		}

		/**
		 * Chunks of code responsible for the menu bar's functionality.
		 * Features help, different color layouts, etc.
		 * Contains action listeners for certain menu items in order to change the color of board layout.
		 */
		JMenuBar jmb = new JMenuBar();

		JMenu jmAbout = new JMenu("About");
		JMenuItem jmiRules = new JMenuItem("Rules");
		jmiRules.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						JOptionPane.showMessageDialog(null, "**Two-player turn based game with one player as the red chip and other as the orange chip.\n"
								+ "**Objective is to connect four chips in a row either vertically, horizontally, or diagnally.\n"
								+ "**Each player must place their chip at the lowest position in a column of their choosing.");
					}
				});
		jmAbout.add(jmiRules);
		JMenuItem jmiHelp = new JMenuItem("Help");
		jmiHelp.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						JOptionPane.showMessageDialog(null, "Clear the field button clears the entire field\nExit the game by clicking on the exit button or the X button in upper right corner\nRestart the scorline button sets the scoreline for both players to zero");
					}
				});
		jmAbout.add(jmiHelp);
		jmb.add(jmAbout);

		JMenu jmLayout = new JMenu("Layout");
		JMenuItem jmiWhite = new JMenuItem("White/Default");
		jmiWhite.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						for(int i = 0; i < 6; i++){
							for(int j = 0; j < 7; j++){

								buttons[i][j].setBackground(Color.WHITE);

							}
						}

					}
				});
		JMenuItem jmiBlue = new JMenuItem("Blue");
		jmiBlue.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						for(int i = 0; i < 6; i++){
							for(int j = 0; j < 7; j++){

								buttons[i][j].setBackground(Color.BLUE);

							}
						}

					}
				});
		JMenuItem jmiGreen = new JMenuItem("Green");
		jmiGreen.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						for(int i = 0; i < 6; i++){
							for(int j = 0; j < 7; j++){

								buttons[i][j].setBackground(Color.GREEN);

							}
						}

					}
				});
		JMenuItem jmiCyan = new JMenuItem("Cyan");
		jmiCyan.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						for(int i = 0; i < 6; i++){
							for(int j = 0; j < 7; j++){

								buttons[i][j].setBackground(Color.CYAN);

							}
						}

					}
				});
		JMenuItem jmiMagenta = new JMenuItem("Purple");
		jmiMagenta.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						for(int i = 0; i < 6; i++){
							for(int j = 0; j < 7; j++){

								buttons[i][j].setBackground(Color.MAGENTA);

							}
						}

					}
				});
		JMenuItem jmiBlack = new JMenuItem("Black");
		jmiBlack.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						for(int i = 0; i < 6; i++){
							for(int j = 0; j < 7; j++){

								buttons[i][j].setBackground(Color.BLACK);

							}
						}

					}
				});
		JMenuItem jmiPink = new JMenuItem("Pink");
		jmiPink.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent ae){
						for(int i = 0; i < 6; i++){
							for(int j = 0; j < 7; j++){

								buttons[i][j].setBackground(Color.PINK);

							}
						}

					}
				});
		jmLayout.add(jmiWhite);
		jmLayout.add(jmiBlue);
		jmLayout.add(jmiGreen);
		jmLayout.add(jmiCyan);
		jmLayout.add(jmiMagenta);
		jmLayout.add(jmiBlack);
		jmLayout.add(jmiPink);
		jmb.add(jmLayout);
		this.setJMenuBar(jmb);

		//set JFrame properties
		this.setSize(1000, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("Client ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent w) {
						if(client != null){
							JOptionPane.showMessageDialog(null, "Thanks for playing the game! :)");
							Info info = new ChatMessage(username + " disconnected");
							client.sendInfo(info);
							client.disconnect();
							System.exit(0);
							dispose();
						}
						else{
							System.exit(0);
						}
					}
				});
	}

	/**
	 * Method that sets all button fields to null, used to restart the game.
	 */
	public void restart() {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				buttons[i][j].setIcon(null);
			}
		}  
	}


	/**
	 * Add time to the message and append them to the text field method
	 */
	public void addTimeMsg(String msg) {
		String time = timer.format(new Date());
		textArea.append("[" + time + "] " + msg + "\n");
	}
	/**
	 * Method to send position of move
	 */
	public void sendMove(Object source) {
		int[] positions = position(source);
		client.sendInfo(new Move(positions[0] + " " + positions[1]));
	}



	/**
	 * connection failed method to reset
	 */ 
	public void connectionFailure() {
		serverField.setText(HOST);
		portField.setText(""+PORT);
		bLogin.setEnabled(true);  
		messageField.removeKeyListener(this);  
		connection = false;
	}

	//eclipse suggested to add these as void even when not used
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}

	/** 
	 * Event for Enter button to send message into chat
	 */
	public void keyPressed(KeyEvent kp) {
		//store keycode to int and use it later for displaying which key pressed
		int key = kp.getKeyCode();

		//if key enter is pressed sent the message as client
		if(key == KeyEvent.VK_ENTER) { 
			client.sendInfo(new ChatMessage(username + ": " + messageField.getText()));
			//and set text back to empty thus a new message can be entered
			messageField.setText(""); 
		}
	}

	//eclipse suggested 
	public void keyReleased(KeyEvent kr) {} 
	public void keyTyped(KeyEvent kt) {}

	/**Click events
	 * Contains: 
	 * -Login button to connect users to the chat and game.
	 * -Enter button to submit text string into chat
	 * -Exit button to exit the game properly
	 * -Restart button to reset the game board
	 * -Score Restart to reset the wins accumulation
	 * -Sends position selected on game
	 */  
	public void actionPerformed(ActionEvent ae) {

		//if button login is clicked 
		if(ae.getActionCommand().equals("Login")) {
			//get user name and store it in string
			username = userField.getText().trim();
			//cannot be empty
			if(username.length() < 3) {        
				JOptionPane.showMessageDialog(null, "Username has to be at least 3 characters long");
				return;               
			}

			//same for server input
			String server = serverField.getText().trim();

			if(server.length() == 0) {
				JOptionPane.showMessageDialog(null, "Please enter your server address");         
				return;
			}

			String port2 = portField.getText().trim();
			//and port input
			if(port2.length() == 0) {
				JOptionPane.showMessageDialog(null, "Please enter your port number");
				return;
			}

			int port = 0;

			try {
				port = Integer.parseInt(port2);
			}
			catch(Exception ex) {
				System.out.println("Exception - ActionPerformed - port/server");
				return;   
			}

			client = new Client(server,port,username,this);

			connection = client.connect();
			if(connection == true) {

				messageField.setText("");
				serverField.setEditable(false);
				portField.setEditable(false);
				bLogin.setEnabled(false);
				bEnter.setEnabled(true);
				//adding key listener for button enter to send msg
				messageField.addKeyListener(this);
				client.sendInfo(new ChatMessage(username + " has connected"));
			}
			else {
				JOptionPane.showMessageDialog(null, "Cannot login without the server connection");
				client = null;
			}
		}else if(ae.getActionCommand().equals("Enter")) {
			client.sendInfo(new ChatMessage(username + ": " + messageField.getText()));
			messageField.setText(""); 
		}else if (ae.getActionCommand().equals("Exit")){
			if(client == null){
				JOptionPane.showMessageDialog(null, "You have to establish a connection with the server first");
				return;
			}else {
				JOptionPane.showMessageDialog(null, "Thanks for playing the game! :)");
				Info info = new ChatMessage(username + " disconnected");
				client.sendInfo(info);
				System.exit(0);
			}

		}else if (ae.getSource() == restart){

			restart();

		}else if(ae.getSource() == scoreRestart){
			redWinCounter = 0;
			redScore.setText("Your wins: " + redWinCounter);
			orangeWinCounter = 0;
			orangeScore.setText("Opponent wins: " + orangeWinCounter);

		}else if(ae.getSource() instanceof JButton) {
			if(yourTurn) {
				circleColor = redCircle;
				int[] positions = position(ae.getSource());
				sendMove(ae.getSource());
			}
		}
	}
	/**
	 * checkWin() checks the multiple variations of possible wins in the game.
	 * The player using the specific gui will always be red and the opponent will always be orange
	 */
	public void checkWin() {
		/**
		 * Custom made algorithm that covers all win combinations for horizontal type of win.
		 */
		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 4; j++){
				if(buttons[i][j].getIcon() == orangeCircle && buttons[i][j+1].getIcon() == orangeCircle && buttons[i][j+2].getIcon() == orangeCircle && buttons[i][j+3].getIcon() == orangeCircle){

					JOptionPane.showMessageDialog(null, "Opponent wins!");
					orangeWinCounter++;
					orangeScore.setText("Opponent wins: " + orangeWinCounter);
					restart();

				}
				else if(buttons[i][j].getIcon() == redCircle && buttons[i][j+1].getIcon() == redCircle && buttons[i][j+2].getIcon() == redCircle && buttons[i][j+3].getIcon() == redCircle){

					JOptionPane.showMessageDialog(null, "You win!");
					redWinCounter++;
					redScore.setText("Your wins: " + redWinCounter);
					restart();

				}
			}
		}
		/**
		 * Custom made algorithm that covers all win combinations for vertical type of win.
		 */
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 7; j++){
				if(buttons[i][j].getIcon() == orangeCircle && buttons[i+1][j].getIcon() == orangeCircle && buttons[i+2][j].getIcon() == orangeCircle && buttons[i+3][j].getIcon() == orangeCircle){

					JOptionPane.showMessageDialog(null, "Opponent wins!");
					orangeWinCounter++;
					orangeScore.setText("Opponent wins: " + orangeWinCounter);
					restart();

				}
				else if(buttons[i][j].getIcon() == redCircle && buttons[i+1][j].getIcon() == redCircle && buttons[i+2][j].getIcon() == redCircle && buttons[i+3][j].getIcon() == redCircle){

					JOptionPane.showMessageDialog(null, "You win!");
					redWinCounter++;
					redScore.setText("Your wins: " + redWinCounter);
					restart();

				}
			}
		}
		/**
		 * Custom made algorithm that covers all win combinations for diagonal wins in diagonal left(top) to right(bottom) direction.
		 */
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 4; j++){
				if(buttons[i][j].getIcon() == orangeCircle && buttons[i+1][j+1].getIcon() == orangeCircle && buttons[i+2][j+2].getIcon() == orangeCircle && buttons[i+3][j+3].getIcon() == orangeCircle){

					JOptionPane.showMessageDialog(null, "Opponent wins!");
					orangeWinCounter++;
					orangeScore.setText("Opponent wins: " + orangeWinCounter);
					restart();

				}
				else if(buttons[i][j].getIcon() == redCircle && buttons[i+1][j+1].getIcon() == redCircle && buttons[i+2][j+2].getIcon() == redCircle && buttons[i+3][j+3].getIcon() == redCircle){

					JOptionPane.showMessageDialog(null, "You win!");
					redWinCounter++;
					redScore.setText("Your wins: " + redWinCounter);
					restart();

				}
			}
		}
		/**
		 * Custom made algorithm that covers all win combinations for diagonal wins in diagonal right(top) to left(bottom) direction.
		 */
		for(int i = 0; i < 3; i++){
			for(int j = 6; j > 2; j--){
				if(buttons[i][j].getIcon() == orangeCircle && buttons[i+1][j-1].getIcon() == orangeCircle && buttons[i+2][j-2].getIcon() == orangeCircle && buttons[i+3][j-3].getIcon() == orangeCircle){

					JOptionPane.showMessageDialog(null, "Opponent wins!");
					orangeWinCounter++;
					orangeScore.setText("Opponent wins: " + orangeWinCounter);
					restart();

				}
				else if(buttons[i][j].getIcon() == redCircle && buttons[i+1][j-1].getIcon() == redCircle && buttons[i+2][j-2].getIcon() == redCircle && buttons[i+3][j-3].getIcon() == redCircle){

					JOptionPane.showMessageDialog(null, "You win!");
					redWinCounter++;
					redScore.setText("Your wins: " + redWinCounter);
					restart();

				}
			}
		}
	}



	/**
	 * Sets position of selection
	 * returns position list
	 */
	public int[] position(Object source) {
		int[] positions = new int[2];
		int horizontal = -1;
		int vertical = -1;

		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 7; j++){
				if(source == buttons[i][j]) {
					if(buttons[5][j].getIcon() == null){
						horizontal = 5;
						vertical = j;
					}
					else if(buttons[4][j].getIcon() == null){
						horizontal = 4;
						vertical = j;
					}
					else if(buttons[3][j].getIcon() == null){
						horizontal = 3;
						vertical = j;
					}
					else if(buttons[2][j].getIcon() == null){
						horizontal = 2;
						vertical = j;
					}
					else if(buttons[1][j].getIcon() == null){
						horizontal = 1;
						vertical = j;
					}
					else if(buttons[0][j].getIcon() == null){
						horizontal = 0;
						vertical = j;
					}
				}
			}
		}
		positions[0] = horizontal;
		positions[1] = vertical;
		return positions;
	}//end of positions

	/**
	 *If it is not your turn it changes the color and
	 *sets the icon on the board
	 */
	public void addCircle(int horizontal, int vertical) {
		if(!yourTurn) {
			circleColor = orangeCircle;
		}
		buttons[horizontal][vertical].setIcon(circleColor);
	}

}//end class
