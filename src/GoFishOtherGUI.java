import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

public class GoFishOtherGUI extends JFrame {
	private static final long serialVersionUID = -3381491740976366400L;
	
	private Image cardback;
	private Image blueone;
	//TODO do the rest of the images
	
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
		// TODO finish loading all images
		try {
			cardback=ImageIO.read(new File("Images/cardback.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

