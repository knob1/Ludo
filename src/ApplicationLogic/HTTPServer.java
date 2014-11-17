/**
 * 
 */
package ApplicationLogic;

/**
 * @author Sebastian Römer
 * @author Christoph Otto
 */
import java.awt.Dimension;
import java.io.*;
import java.net.*;
import java.text.NumberFormat;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class HTTPServer extends Thread {
	Socket connectedClient = null;
	BufferedReader inFromClient = null;
	DataOutputStream outToClient = null;
	private static HTTPServer instance = null;
	static boolean gameStarted = false;
	
	static String playerIp = null;
	static String REDIRECT = ";URL=/refresh";
	static String NOADDON = "";
	static String ipAddress;
	
	static int REFRESH = 1;
	static int PORT = 8000;

	static ArrayList<String>playerIPList= new ArrayList<String>();
	
	



	
	
	public HTTPServer(){
		
	}
	
	public static HTTPServer getInstance()
	{
		if (instance == null) 
		{
			instance = new HTTPServer();
		}
		return instance;
	}
	
	public HTTPServer(Socket client)
	{
		connectedClient = client;
	}

	public void run()
	{
		System.out.println("Thread with ID " + getId());
		
		String beforeQm = null;
		String afterQm = null;
		String beforeEq = null;
		String afterEq1 = null;
		String username = null;
		String eqSub0 = ""; //Chip Color
		String eqSub1 = ""; //Chip Color Value
		String eqSub2 = ""; //ChipId
		String eqSub3 = ""; //ChipId Value
		String eqSub4 = ""; //divID
		String eqSub5 = ""; //divID Value
		
		try
		{
			System.out.println("The Client " + connectedClient.getInetAddress() + ":" + connectedClient.getPort() + " is connected");
			
			inFromClient = new BufferedReader(new InputStreamReader(connectedClient.getInputStream()));
			outToClient = new DataOutputStream(connectedClient.getOutputStream());
			
			String currentIp = connectedClient.getInetAddress().toString();
			currentIp = currentIp.substring(1, currentIp.length());
			
			String requestString = inFromClient.readLine();
			String headerLine = requestString; // example: GET / HTTP/1.1

			StringTokenizer tokenizer = new StringTokenizer(headerLine);
			String httpMethod = tokenizer.nextToken();
			String httpQueryString = tokenizer.nextToken();
			
			StringBuffer responseBuffer = new StringBuffer();
			
			System.out.println("The HTTP request string is ....");
			
			while(inFromClient.ready())
			{
				responseBuffer.append(requestString + "<BR>");
				System.out.println(requestString);
				requestString = inFromClient.readLine();
			}
			
			System.out.println("received query:" + httpQueryString);
			
			
			/*if(httpMethod.equals("POST")){
				if(httpQueryString.toLowerCase().contains("random"))
				{
					// Player has rolled dice already
					if(Manager.getInstance().diceRolled())
					{
						int diceValue = Manager.getInstance().getDiceValue();
						String name = Manager.getInstance().getCurrentPlayer().getPlayerName();
						String playerColor = Manager.getInstance().getPlayerColor();
						boolean isCurrentPlayer = Manager.getInstance().isCurrentPlayer(currentIp);
						
						if(Manager.getInstance().isGameActive()){
							sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, diceValue, playerColor, isCurrentPlayer), false, REFRESH);
						}
						else {
							sendResponse(200, HTMLSites.getInstance().endRanking(), false, 100);
						}
					}
					// Play is allowed to roll dice
					else
					{
						try
						{
							String name = Manager.getInstance().getCurrentPlayer().getPlayerName();
							int diceValue = Manager.getInstance().rollDice(currentIp);
							String playerColor = Manager.getInstance().getPlayerColor();
							boolean isCurrentPlayer = Manager.getInstance().isCurrentPlayer(currentIp);
							
							if(Manager.getInstance().isGameActive()){
								sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, diceValue, playerColor, isCurrentPlayer), false, REFRESH);
							}
							else {
								sendResponse(200, HTMLSites.getInstance().endRanking(), false, 100);
							}
							
						}
						catch(Exception e)
						{
							String name = Manager.getInstance().getCurrentPlayer().getPlayerName();
							String playerColor = Manager.getInstance().getPlayerColor();
							boolean isCurrentPlayer = Manager.getInstance().isCurrentPlayer(currentIp);
							if(Manager.getInstance().isGameActive()){
								sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, 0, playerColor, isCurrentPlayer), false, REFRESH);
							}
							
							else {
								sendResponse(200, HTMLSites.getInstance().endRanking(), false, 100);
							}
						}
					}
				}
			}*/
			
			if(httpMethod.equals("GET"))
			{
				if(httpQueryString.contains("refresh"))
				{
					String name = Manager.getInstance().getCurrentPlayer().getPlayerName();
					
					String playerColor = Manager.getInstance().getPlayerColor();
					boolean isCurrentPlayer = Manager.getInstance().isCurrentPlayer(currentIp);
					HTMLSites.getInstance().setTaBo();
					
					if(Manager.getInstance().isGameActive())
					{
						if(isCurrentPlayer)
						{
							int diceValue = Manager.getInstance().getCurrentPlayer().getLastDiceValue();
							
							if(Manager.getInstance().diceRolled())
							{
								String addon = NOADDON;
								
								
								if(diceValue == 6 && Manager.getInstance().isFirstRound() == false)
								{
									addon = "Du darfst nochmal w&uuml;rfeln!";
								}
								sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, diceValue, playerColor, isCurrentPlayer, addon), false, 100, NOADDON);
							}
							else
							{
								int throwCnt = 1;
										
								if(false == Manager.getInstance().playerCanMakeAction())
								{
									if(false == Manager.getInstance().gotActiveChips(Manager.getInstance().getCurrentPlayer(), 0))
									{
										throwCnt = Manager.getInstance().getThrowCount();
									}
								}
								
								String addon = "verbleibende Anzahl Würfe: " + throwCnt;
								
								if(throwCnt == 1)
								{
									if(false == Manager.getInstance().playerCanMakeAction())
									{
										sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, diceValue, playerColor, isCurrentPlayer, addon), false, 100, NOADDON);
									}
									else
									{
										sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, 0, playerColor, isCurrentPlayer, addon), false, 100, NOADDON);
									}
								}
								else
								{
									if(throwCnt == 3)
									{
										sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, 0, playerColor, isCurrentPlayer, addon), false, 100, NOADDON);
									}
									else
									{
										sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, diceValue, playerColor, isCurrentPlayer, addon), false, 100, NOADDON);
									}
								}
							}
						}
						else
						{
							sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, 0, playerColor, isCurrentPlayer, NOADDON), false, REFRESH, NOADDON);
						}
					}
					else
					{
						sendResponse(200, HTMLSites.getInstance().endRanking(), false, 100, NOADDON);
						
						if(allClientsGotEndPage(currentIp))
						{
							gameStarted= false;
						}
					}
				}
				
				if(httpQueryString.contains("moveChip"))
				{
					//moveChip?Color=red&ChipID=1&divId=f1001&x=10&y=17
					
					if(Manager.getInstance().diceRolled())
					{
						String[] qmSub = httpQueryString.split("\\?");
						beforeQm = qmSub[0];
						afterQm = qmSub[1];
						String[] inputForm = afterQm.split("\\&");
						String sub0 = inputForm[0];
						String sub1 = inputForm[1];
						String sub2 = inputForm[2];
						String[] eqSub_1 = sub0.split("\\=");
						eqSub0 = eqSub_1[0]; // Color
						eqSub1 = eqSub_1[1]; // bsp: red
						String[] eqSub_2 = sub1.split("\\=");
						eqSub2 = eqSub_2[0]; // ChipId
						eqSub3 = eqSub_2[1]; // bsp: 1
						String[] eqSub_3 = sub1.split("\\=");
						eqSub4 = eqSub_3[0]; // divId
						eqSub5 = eqSub_3[1]; // bsp: f1001
						
						String position = eqSub5.substring(1, eqSub5.length());
						String name = Manager.getInstance().getCurrentPlayer().getPlayerName();
						int diceValue = Manager.getInstance().getDiceValue();
						String playerColor = Manager.getInstance().getPlayerColor();
						boolean isCurrentPlayer = Manager.getInstance().isCurrentPlayer(currentIp);
						
						try
						{
							Manager.getInstance().manageGame(currentIp, Integer.parseInt(position));
							
							sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, diceValue, playerColor, isCurrentPlayer, NOADDON), false, REFRESH, REDIRECT);
							
						}
						catch(Exception e)
						{
							sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, diceValue, playerColor, isCurrentPlayer, NOADDON), false, REFRESH, REDIRECT);
						}
					}
					else
					{
						String name = Manager.getInstance().getCurrentPlayer().getPlayerName();
						String playerColor = Manager.getInstance().getPlayerColor();
						boolean isCurrentPlayer = Manager.getInstance().isCurrentPlayer(currentIp);
						
						sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, 0, playerColor, isCurrentPlayer, NOADDON), false, REFRESH, REDIRECT);
					}
				}
				else
				{
					if(httpQueryString.contains("?"))
					{
						String[] qmSub = httpQueryString.split("\\?");
						beforeQm = qmSub[0];
						afterQm = qmSub[1];
						
						String[] inputForm = afterQm.split("\\=");
						beforeEq = inputForm[0];
						
						try
						{
							afterEq1 = inputForm[1];
						}
						catch(Exception e)
						{
							String addon = "Bitte einen Spielernamen eingeben!";
							
							sendResponse(200, HTMLSites.getInstance().getIndexHtml(addon), false, 100, NOADDON);
						}
						
						if(httpQueryString.contains("&"))
						{
							String[] usernameArray = inputForm[1].split("\\&");
							username = usernameArray[1];
						}
					}
					
					if(httpQueryString.equals("/") || httpQueryString.toLowerCase().contains("restart_game"))
					{
						if(gameStarted)
						{
							Iterator<Player> itP = Manager.getInstance().getPlayers().iterator();
							
							while(itP.hasNext())
							{
								Player actualP = itP.next();
								
								if(currentIp.equals(actualP.getIP()))
								{
									String name = Manager.getInstance().getCurrentPlayer().getPlayerName();
									int diceValue = Manager.getInstance().getDiceValue();
									String playerColor = Manager.getInstance().getPlayerColor();
									boolean isCurrentPlayer = Manager.getInstance().isCurrentPlayer(currentIp);
									sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, diceValue, playerColor, isCurrentPlayer, NOADDON), false, REFRESH, NOADDON);
								}
								else
								{
									sendResponse(200, HTMLSites.getInstance().gameRunning(), false, REFRESH, NOADDON);
								}
							}
						}
						else
						{
							sendResponse(200, HTMLSites.getInstance().getIndexHtml(null), false, 100, NOADDON);
						}
					}
					
					else if(httpQueryString.toLowerCase().contains("form_action"))
					{
						if(afterEq1 != null)
						{
							if(Manager.getInstance().isGameActive())
							{
								sendStartGameSzenario(currentIp);
							}
							
							int size = Manager.getInstance().getPlayers().size();
							
							if(size<=4)
							{
								String [] p;
		
								if(size>=2 && Manager.getInstance().isHost(currentIp))
								{
									p = getPlayerArray(Manager.getInstance().getPlayers());
									
									sendResponse(200, HTMLSites.getInstance().createLandingPage(p[0], p[1], p[2], p[3], true, NOADDON), false, 8, NOADDON);
								}
								else
								{
									try
									{
										Manager.getInstance().insertPlayer(currentIp, afterEq1, true);
										playerIPList.add(currentIp);
										p = getPlayerArray(Manager.getInstance().getPlayers());
										
										sendResponse(200, HTMLSites.getInstance().createLandingPage(p[0], p[1], p[2], p[3], false, NOADDON), false, 8, NOADDON);
									}
									catch(Exception e)
									{
										// Index HTML modifizieren -> Flag 
										p = getPlayerArray(Manager.getInstance().getPlayers());
										
										sendResponse(200, HTMLSites.getInstance().createLandingPage(p[0], p[1], p[2], p[3], false, NOADDON), false, 8, NOADDON);
									}
								}
							}
							else
							{
								sendResponse(200, HTMLSites.getInstance().gameFull(), false, REFRESH, NOADDON);
							}
						}
						else
						{
							
						}
					}
	
					else if(httpQueryString.toLowerCase().contains("random"))
					{
						// Player has rolled dice already
						if(Manager.getInstance().diceRolled())
						{
							int diceValue = Manager.getInstance().getDiceValue();
							String name = Manager.getInstance().getCurrentPlayer().getPlayerName();
							String playerColor = Manager.getInstance().getPlayerColor();
							boolean isCurrentPlayer = Manager.getInstance().isCurrentPlayer(currentIp);
							
							String addon = NOADDON;
							
							if(diceValue == 6 && Manager.getInstance().isFirstRound() == false)
							{
								addon = "Du darfst nochmal w&uuml;rfeln!";
							}
							
							sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, diceValue, playerColor, isCurrentPlayer, addon), false, REFRESH, REDIRECT);
						}
						// Player is allowed to roll dice
						else
						{
							try
							{
								String name = Manager.getInstance().getCurrentPlayer().getPlayerName();
								int diceValue = Manager.getInstance().rollDice(currentIp);
								String playerColor = Manager.getInstance().getPlayerColor();
								boolean isCurrentPlayer = Manager.getInstance().isCurrentPlayer(currentIp);
									
								if(Manager.getInstance().isGameActive())
								{
									sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, diceValue, playerColor, isCurrentPlayer, NOADDON), false, REFRESH, REDIRECT);
								}
								else
								{
									sendResponse(200, HTMLSites.getInstance().endRanking(), false, 100, NOADDON);
								}
							}
							catch(Exception e)
							{
								String name = Manager.getInstance().getCurrentPlayer().getPlayerName();
								String playerColor = Manager.getInstance().getPlayerColor();
								boolean isCurrentPlayer = Manager.getInstance().isCurrentPlayer(currentIp);
								
								if(Manager.getInstance().isGameActive())
								{
									sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, 0, playerColor, isCurrentPlayer, NOADDON), false, REFRESH, REDIRECT);
								}
								else
								{
									sendResponse(200, HTMLSites.getInstance().endRanking(), false, 100, NOADDON);
								}
							}
						}
					}
					else if(httpQueryString.toLowerCase().contains("start_game"))
					{
						int botIp = 0;
						
						if(afterQm.contains("&"))
						{
							String inputForm[] = afterQm.split("&");
							
							for(int i = 0; i<inputForm.length; i++)
							{
								if(inputForm[i].contains("="))
								{
									String attributes[] = inputForm[i].split("=");
									
									if(attributes[0].contains("bot"))
									{
										String[] nameArray = new String[7];
										nameArray[0] = "Alf_BOT";
										nameArray[1] = "Luc_BOT";
										nameArray[2] = "Indy_BOT";
										nameArray[3] = "Frodo_BOT";
										nameArray[4] = "He-Man_BOT";
										nameArray[5] = "Batman_BOT";
										nameArray[6] = "Lara_BOT";
										int randomName = 1 + (int)(Math.random() * 6);
										Manager.getInstance().insertPlayer("botIp"+Integer.toString(botIp), nameArray[randomName], false);
										botIp++;
									}
								}
							}
						}
						
						if(Manager.getInstance().isHost(currentIp))
						{
							int size = Manager.getInstance().getPlayers().size();
							
							if(size == 1)
							{
								String error = "Bitte auf weiteren Spieler warten oder einen Computerspieler hinzufügen!";
								
								String [] p = getPlayerArray(Manager.getInstance().getPlayers());
								
								sendResponse(200, HTMLSites.getInstance().createLandingPage(p[0], p[1], p[2], p[3], false, error), false, 8, NOADDON);
							}
							
							Manager.getInstance().startGame();
						}
						
						sendStartGameSzenario(currentIp);
					}
					else
					{
						String fileName = "bin/files" + httpQueryString;
						fileName = URLDecoder.decode(fileName);
						
						if(new File(fileName).isFile())
						{
							sendResponse(200, fileName, true, REFRESH, NOADDON);
						}
						else
						{
							sendResponse(404, "<b>The Requested resource not found .... Usage: http://127.0.0.1:5000 or http://127.0.0.1:5000/</b>", false, REFRESH, NOADDON);
						}
					}
				}
			}
			else
			{
				sendResponse(404, "<b>The Requested resource not found .... Usage is: http://127.0.0.1:5000 or http://127.0.0.1:5000/</b>", false, REFRESH, NOADDON);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	/**
	 * send the html site to request ip, when game is started into first round
	 * @throws Exception 
	 */
	private void sendStartGameSzenario(String currentIp) throws Exception
	{
		String name = Manager.getInstance().getCurrentPlayer().getPlayerName();
		String playerColor = Manager.getInstance().getPlayerColor();
		boolean isCurrentPlayer = Manager.getInstance().isCurrentPlayer(currentIp);
		
		int diceValue = 0;
		
		if(Manager.getInstance().diceRolled())
		{
			diceValue = Manager.getInstance().getDiceValue(); 
		}
		
		sendResponse(200, HTMLSites.getInstance().createLudoBoard(name, diceValue, playerColor, isCurrentPlayer, "Bitte W&uuml;rfeln um Startreihenfolge </br>zu ermitteln"), false, REFRESH, NOADDON);
		gameStarted = true;
	}
	

	public String[] getPlayerArray(ArrayList<Player> players) {
		String[] p = new String[4];
        
        Iterator<Player> players_itr = players.iterator();
        
        for(int i=0; i<4; i++)
        {
            if(players_itr.hasNext())
            {
                p[i] = players_itr.next().getPlayerName();
            }
            else
            {
                p[i] = "";
            }
        }
		return p;
	}

	public void sendResponse(int statusCode, String responseString, boolean isFile, int refresh, String addon) throws Exception
	{
		String statusLine = null;
		String contentLengthLine = null;
		String contentTypeLine = "Content-Type: text/html \r\n";
		
		String fileName = null;
		FileInputStream fin = null;

		// prepare statusLine
		if(statusCode == 200)
		{
			statusLine = "HTTP/1.1 200 OK \r\n";
		}
		else
		{
			statusLine = "HTTP/1.1 404 Not Found \r\n";
		}
		
		// prepare answer
		if(isFile)
		{
			fileName = responseString;
			fin = new FileInputStream(fileName);
			contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
            
            if(!fileName.endsWith(".htm") && !fileName.endsWith(".html"))
            {
            	contentTypeLine = "Content-Type: \r\n";
            }
        }
        else
        {
            responseString = HTMLSites.getInstance().getHtmlStart(refresh, addon) + responseString + HTMLSites.getInstance().getHtmlEnd();
            contentLengthLine = "Content-Length: " + responseString.length() + "\r\n";
        } 

		outToClient.writeBytes(statusLine);
		outToClient.writeBytes(contentTypeLine);
		outToClient.writeBytes(contentLengthLine);
		outToClient.writeBytes("Connection: close\r\n");
		outToClient.writeBytes("\r\n");
		
		if(isFile)
		{
			sendFile(fin, outToClient);
		}
		else
		{
			outToClient.writeBytes(responseString);
		}
		
		outToClient.close();
	}
	
	
	/**
	 * @param fin
	 * @param out
	 * @throws Exception
	 */
	public void sendFile(FileInputStream fin, DataOutputStream out) throws Exception
	{
		byte[] buffer = new byte[1024];
		int bytesRead;

		while((bytesRead = fin.read(buffer)) != -1)
		{
			out.write(buffer, 0, bytesRead);
		}
		
		fin.close();
    }

	
	/**
	 * @param args
	 */
	public static void main(String args[]) throws Exception
	{
		final JFrame startFrame=new JFrame("Ludo");
		JPanel panel1=new JPanel();
		JLabel portLabel=new JLabel("Bitte einen Port zwischen 8001 und 8099 eingeben:");
		final JTextField portTextField=new JTextField(6);
	
		
		panel1.add(portLabel);
		panel1.add(portTextField);
		
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Dimension d = startFrame.getToolkit().getScreenSize();
		startFrame.setLocation((int) ((d.getWidth() - startFrame.getWidth()) / 2), (int) ((d.getHeight() - startFrame.getHeight()) / 2));

		JPanel panel2 = new JPanel();

		final InetAddress ip = InetAddress.getLocalHost();

		JButton okButton = new JButton("ok");
		
		okButton.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				startFrame.setVisible(false);

				try
				{
					if(portTextField.getText().toString().isEmpty() == false)
					{
						if(Integer.parseInt(portTextField.getText()) >= 8001 && Integer.parseInt(portTextField.getText()) <= 8099)
						{
							PORT = Integer.parseInt(portTextField.getText());
							ipAddress = ip.getHostAddress().toString();
							System.out.println("Eigene IP: " + ipAddress);
							ServerSocket Server = null;
							
							try
							{
								Server = new ServerSocket(PORT, 10, InetAddress.getByName(ipAddress));
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							
							JOptionPane.showMessageDialog(null, "Bitte einen Browser starten und folgende Adresse eingeben: " + ipAddress + ":" + PORT, "Das Spiel kann beginnen!", JOptionPane.INFORMATION_MESSAGE);
							
							System.out.println("TCPServer Waiting for client on port " + PORT);
							
							while(true)
							{
								Socket connected = null;
								
								try
								{
									connected = Server.accept();
								}
								catch(Exception e)
								{
									e.printStackTrace();
								}
								
								(new HTTPServer(connected)).start();
							}
						}
						else
						{
							//JOptionPane.showMessageDialog(null, "falscher Port, Port wurde auf 8001 gesetzt", "Error", JOptionPane.ERROR_MESSAGE);
							PORT = 8001;
							ipAddress = ip.getHostAddress().toString();
							ServerSocket Server = null;
							JOptionPane.showMessageDialog(null, "Bitte einen Browser starten und folgende Adresse eingeben: " + ipAddress + ":" + PORT, "Das Spiel kann beginnen!", JOptionPane.INFORMATION_MESSAGE);
							
							try
							{
								Server = new ServerSocket(PORT, 10, InetAddress.getByName(ipAddress));
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							
							System.out	.println("TCPServer Waiting for client on port " + PORT);

							while (true)
							{
								Socket connected = null;
								
								try
								{
									connected = Server.accept();
								}
								catch (IOException e)
								{
									e.printStackTrace();
								}
								
								(new HTTPServer(connected)).start();
							}
						}
					}
					else
					{
						//JOptionPane.showMessageDialog(null, "falscher Port, Port wurde auf 8001 gesetzt", "Error", JOptionPane.ERROR_MESSAGE);
						PORT = 8001;
						ipAddress = ip.getHostAddress().toString();
						ServerSocket Server = null;
						JOptionPane.showMessageDialog(null, "Bitte einen Browser starten und folgende Adresse eingeben: " + ipAddress + ":" + PORT, "Das Spiel kann beginnen!", JOptionPane.INFORMATION_MESSAGE);
						
						try
						{
							Server = new ServerSocket(PORT, 10, InetAddress.getByName(ipAddress));
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						System.out.println("TCPServer Waiting for client on port " + PORT);

						while (true)
						{
							Socket connected = null;
							
							try
							{
								connected = Server.accept();
							}
							catch(Exception e)
							{	
								e.printStackTrace();
							}
							
							(new HTTPServer(connected)).start();
						}
					}
				}
				catch(Exception ex)
				{
					//JOptionPane.showMessageDialog(null, "Port kann nur aus Zahlen bestehen,Port wurde auf 8001 gesetzt", "Error", JOptionPane.ERROR_MESSAGE);
					PORT = 8001;
					ipAddress = ip.getHostAddress().toString();
					ServerSocket Server = null;
					JOptionPane.showMessageDialog(null, "Bitte einen Browser starten und folgende Adresse eingeben: " + ipAddress + ":" + PORT, "Das Spiel kann beginnen!", JOptionPane.INFORMATION_MESSAGE);
					
					try
					{
						Server = new ServerSocket(PORT, 10, InetAddress.getByName(ipAddress));
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					
					System.out.println("TCPServer Waiting for client on port " + PORT);

					while (true)
					{
						Socket connected = null;
						
						try
						{
							connected = Server.accept();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						
						(new HTTPServer(connected)).start();
					}
				}

			}
		});

		panel2.add(okButton);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(panel1);
		mainPanel.add(panel2);
		startFrame.add(mainPanel);
		startFrame.setSize(500, 700);
		startFrame.pack();
		startFrame.setVisible(true);

		divManager.getInstance().createEmptyLudoBoard();
	}
	
	private boolean allClientsGotEndPage(String ip)
	{
		if(playerIPList.contains(ip))
		{
			playerIPList.remove(ip);
		}
		
		if(playerIPList.isEmpty())
		{
			Manager.getInstance().resetStation();
			
			return true;
		}
		
		return false;
	}
	
}
