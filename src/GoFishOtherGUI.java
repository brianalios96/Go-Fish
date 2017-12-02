import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

public class GoFishOtherGUI extends JFrame {
	private static final long serialVersionUID = -3381491740976366400L;
	
	private final String RANK_FROM_USER= "Select Rank of the Card you want to ask for (Integer of 1-10)";
	private final String INVALID_RANK_FROM_USER= "Invalid rank selection. Select Rank of the Card you want to ask for (Integer of 1-10)";
	
	private final String PLAYER_SELECTION_FROM_USER= 
			"Select the number of the player you wish to get cards from that is not yourself. "
			+ "For example, For Player 1 as the selection, input 1 into the box";
	
	private final String INVALID_PLAYER_SELECTION_FROM_USER= 
			"Invalid Player number selection. Select the number of the player you wish to get cards from that is not yourself. "
			+ "For example, For Player 1 as the selection, input 1 into the box";
	
	
	private Image cardback;
	private Image blueone;
	private Image bluetwo;
	private Image bluethree;
	private Image bluefour;
	private Image bluefive;
	private Image bluesix;
	private Image blueseven;
	private Image blueeight;
	private Image bluenine;
	private Image blueten;
	
	private Image purpleone;
	private Image purpletwo;
	private Image purplethree;
	private Image purplefour;
	private Image purplefive;
	private Image purplesix;
	private Image purpleseven;
	private Image purpleeight;
	private Image purplenine;
	private Image purpleten;
	
	private Image greenone;
	private Image greentwo;
	private Image greenthree;
	private Image greenfour;
	private Image greenfive;
	private Image greensix;
	private Image greenseven;
	private Image greeneight;
	private Image greennine;
	private Image greenten;
	
	private Image orangeone;
	private Image orangetwo;
	private Image orangethree;
	private Image orangefour;
	private Image orangefive;
	private Image orangesix;
	private Image orangeseven;
	private Image orangeeight;
	private Image orangenine;
	private Image orangeten;
	
	private JTextArea info;
	private JTextArea player2;
	private JTextArea player3;
	private JTextArea player4;

	
	private Graphics2D g2;
	
	public static void main(String args[])
	{
		new GoFishOtherGUI();
	}
	
	GoFishOtherGUI()
	{
		setTitle("Go Fish");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(1300, 700)); //1000 700
		setLocation(30, 30);
		this.setLayout(new BorderLayout());
		
		loadImages();
		
		DrawingPanel boardPanel = new DrawingPanel();
		
		player2=new JTextArea();
		player2.setEditable(false);
		player2.setLineWrap(true);
		player2.setWrapStyleWord(true);
		player2.setText("Player 2");
		boardPanel.add(player2);
		
		player3=new JTextArea();
		player3.setEditable(false);
		player3.setLineWrap(true);
		player3.setWrapStyleWord(true);
		player3.setText("Player 3");
		boardPanel.add(player3);
		
		player4=new JTextArea();
		player4.setEditable(false);
		player4.setLineWrap(true);
		player4.setWrapStyleWord(true);
		player4.setText("Player 4");
		boardPanel.add(player4);
		
		info= new JTextArea();
		info.setEditable(false);
		info.setFont(new Font("Arial", Font.BOLD, 24));
		info.setText("Info JTextArea will show the game play	");
		info.setMaximumSize(new Dimension(300, 100));
		
		this.add(boardPanel, BorderLayout.CENTER);
		this.add(info, BorderLayout.PAGE_END);
		this.setVisible(true);
		
		getUserInputPopup();
		
	}
	
	private void getUserInputPopup() {
		String userSelectedRank;
		userSelectedRank = JOptionPane.showInputDialog(RANK_FROM_USER);
		while(isParsable(userSelectedRank) == false || Integer.parseInt(userSelectedRank)<1 
				|| Integer.parseInt(userSelectedRank)>10)
		{
			userSelectedRank= JOptionPane.showInputDialog(INVALID_RANK_FROM_USER);
		}
		
		String userSelectedPlayer= JOptionPane.showInputDialog(PLAYER_SELECTION_FROM_USER);
		while(isParsable(userSelectedPlayer) == false || Integer.parseInt(userSelectedPlayer)<1 
				|| Integer.parseInt(userSelectedPlayer)>4)//TODO add the check for the player selecting himself
		{
			userSelectedPlayer = JOptionPane.showInputDialog(INVALID_PLAYER_SELECTION_FROM_USER);
		}
		
		System.out.println("rank selected: "+ userSelectedRank +" player selected: "+ userSelectedPlayer);
	}

	private boolean isParsable(String input) {
		boolean parsable = true;
	    try{
	        Integer.parseInt(input);
	    }catch(NumberFormatException e){
	        parsable = false;
	    }
	    return parsable;
	}

	private class DrawingPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g2 = (Graphics2D) g;
			
			//player 2 card back
			g2.drawImage(cardback, 0,200, 100, 200, null);
			
			//player 3 card back
			g2.drawImage(cardback, 500, 40, 100, 200, null);
			
			//player 4 card back
			g2.drawImage(cardback, 1180, 200, 100, 200, null);
			
			player2.setLocation(0, 400);
			player2.setSize(260, 40);
			
			player3.setLocation(500, 0);
			player3.setSize(260, 40);
			
			player4.setLocation(1020, 400);
			player4.setSize(260, 40);
		}
	}
	
	private void loadImages() {
		try {
			cardback=ImageIO.read(new File("Images/cardback.jpg"));
			
			blueone=ImageIO.read(new File("Images/1blue.jpeg"));
			bluetwo=ImageIO.read(new File("Images/2blue.jpeg"));
			bluethree=ImageIO.read(new File("Images/3blue.jpeg"));
			bluefour=ImageIO.read(new File("Images/4blue.jpeg"));
			bluefive=ImageIO.read(new File("Images/5blue.jpeg"));
			bluesix=ImageIO.read(new File("Images/6blue.jpeg"));
			blueseven=ImageIO.read(new File("Images/7blue.jpeg"));
			blueeight=ImageIO.read(new File("Images/8blue.jpeg"));
			bluenine=ImageIO.read(new File("Images/9blue.jpeg"));
			blueten=ImageIO.read(new File("Images/10blue.jpeg"));
			
			purpleone=ImageIO.read(new File("Images/1purple.jpeg"));
			purpletwo=ImageIO.read(new File("Images/2purple.jpeg"));
			purplethree=ImageIO.read(new File("Images/3purple.jpeg"));
			purplefour=ImageIO.read(new File("Images/4purple.jpeg"));
			purplefive=ImageIO.read(new File("Images/5purple.jpeg"));
			purplesix=ImageIO.read(new File("Images/6purple.jpeg"));
			purpleseven=ImageIO.read(new File("Images/7purple.jpeg"));
			purpleeight=ImageIO.read(new File("Images/8purple.jpeg"));
			purplenine=ImageIO.read(new File("Images/9purple.jpeg"));
			purpleten=ImageIO.read(new File("Images/10purple.jpeg"));
			
			greenone=ImageIO.read(new File("Images/1green.jpeg"));
			greentwo=ImageIO.read(new File("Images/2green.jpeg"));
			greenthree=ImageIO.read(new File("Images/3green.jpeg"));
			greenfour=ImageIO.read(new File("Images/4green.jpeg"));
			greenfive=ImageIO.read(new File("Images/5green.jpeg"));
			greensix=ImageIO.read(new File("Images/6green.jpeg"));
			greenseven=ImageIO.read(new File("Images/7green.jpeg"));
			greeneight=ImageIO.read(new File("Images/8green.jpeg"));
			greennine=ImageIO.read(new File("Images/9green.jpeg"));
			greenten=ImageIO.read(new File("Images/10green.jpeg"));
			
			orangeone=ImageIO.read(new File("Images/1orange.jpeg"));
			orangetwo=ImageIO.read(new File("Images/2orange.jpeg"));
			orangethree=ImageIO.read(new File("Images/3orange.jpeg"));
			orangefour=ImageIO.read(new File("Images/4orange.jpeg"));
			orangefive=ImageIO.read(new File("Images/5orange.jpeg"));
			orangesix=ImageIO.read(new File("Images/6orange.jpeg"));
			orangeseven=ImageIO.read(new File("Images/7orange.jpeg"));
			orangeeight=ImageIO.read(new File("Images/8orange.jpeg"));
			orangenine=ImageIO.read(new File("Images/9orange.jpeg"));
			orangeten=ImageIO.read(new File("Images/10orange.jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

