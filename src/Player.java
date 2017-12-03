import java.io.*;
import java.net.*;
import java.util.*;

public class Player
{
	private ArrayList<Card> hand;
	private int score;
	private String name;
	private int playernumber;
	private ObjectInputStream dealerInput;
	private DataOutputStream dealerOutput;
	private Socket sock;
	private ObjectInputStream upstream;
	private ObjectOutputStream downstream;
	private int numberOfPlayers;

	private static final String GO_FISH = " GO FISH\n";
	private static final String NO_DECK = "Go Fish, Deck is out of cards.";

	private static final int PLAYER_PORT = Dealer.SOCKET_NUMBER + 1;
	private static final int YOUR_TURN = 9000;
	private static final int HAND_SIZE = 9999;
	
	public Player(String IP)
	{
		hand = new ArrayList<Card>();
		score = 0;
		try
		{
			PlayerThread helper = new PlayerThread();
			helper.start();
			sock = new Socket(IP, Dealer.SOCKET_NUMBER);
			dealerOutput = new DataOutputStream(sock.getOutputStream());
			dealerInput = new ObjectInputStream(sock.getInputStream());
			System.out.println("connection");
			
			String[] addresses = (String[]) dealerInput.readObject();
			playernumber = (Integer) dealerInput.readObject();
			this.name = "Player " + playernumber;
			numberOfPlayers = addresses.length;
			int next = (playernumber + 1) % addresses.length;
			
			Socket down = new Socket(addresses[next], PLAYER_PORT);
			downstream = new ObjectOutputStream(down.getOutputStream());
			
			helper.join();
			
			System.out.println(name);

		} catch (IOException | ClassNotFoundException  | InterruptedException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		draw();
	}

	public int getNumberOfPlayers()
	{
		return numberOfPlayers;
	}
	
	public int getPlayerNumber()
	{
		return playernumber;
	}

	public String getPlayerName()
	{
		return name;
	}

	/**
	 * requests all cards of the rank from the other player
	 * goes fish if required
	 * checks for four of a kind and removes from hand / increments score as appropriate
	 */
	public boolean requestCardsFromOther(CardRank rank, int other)
	{
		boolean retval = true;
		Card[] cardsFromOther = sendMessage(rank, other);


		// other player did not have requested card, draw a
		// card from the deck
		if (cardsFromOther.length == 0)
		{
			
			if(getRemainingDeck() > 0)
			{
				System.out.println(name + GO_FISH);
				
				Card tmp = getCardFromDealer();
				hand.add(tmp);
				if (tmp.getRank() != rank)
				{
					retval = false;
				}
			}
			else
			{
				System.out.println(NO_DECK);
				retval = false;
			}
		}

		// other player had card, input requested cards into current player's
		// hand
		else
		{
			for (Card card : cardsFromOther)
			{
				hand.add(card);
			}
		}

		// checks for four of a kind
		for (CardRank aRank : CardRank.values())
		{
			int count = 0;

			for (Card card : hand)
			{
				if (card.getRank() == aRank)
				{
					count++;
				}
			}

			if (count >= 4)
			{
				System.out.println(name + " completed rank: " + aRank);
				score++;
				for (int i = 0; i < hand.size(); i++)
				{
					if (hand.get(i).getRank() == aRank)
					{
						hand.remove(i);
						i--;
					}
				}
			}
		}// outer for loop over all card ranks, checking for four of a kind

		Collections.sort(hand);
		return retval;
	} // request cards from other ()

	private Card[] sendMessage(CardRank rank, int otherPlayer)
	{
		Message output = new Message();
		output.recipientPlayer = otherPlayer;
		output.originPlayer = playernumber;
		output.rank = rank;
		try
		{
			downstream.writeObject(output);
			Message input = (Message) upstream.readObject();
			return input.cards;
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return null; //impossible to reach, compiler is still dumb
	}
	
	/**
	 * give all the cards of the requested rank
	 * returns an empty array if player dosn't have that rank
	 */
	private Card[] giveCards(CardRank rank)
	{
		int count = 0;
		for (Card card : hand)
		{
			if (card.getRank() == rank)
			{
				count++;
			}
		}
		Card cards[] = new Card[count];

		for (int i = 0; i < cards.length; i++)
		{
			for (int j = 0; j < hand.size(); j++)
			{
				if (hand.get(j).getRank() == rank)
				{
					Card tmp = hand.remove(j);
					j--;
					cards[i] = tmp;
					i++;
				}
			} 
		}

		return cards;
	} // give cards ()

	public int getScore()
	{
		return score;
	}

	public ArrayList<Card> getPlayerHand()
	{
		return hand;
	}

	/**
	 * checks if a rank is in the player's hand. necessary because you can't ask for ranks that you don't have
	 */
	public boolean checkIfInHand(CardRank rank)
	{
		for (int i = 0; i < hand.size(); i++)
		{
			if (rank == hand.get(i).getRank())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * draws five cards from the deck and sorts them
	 */
	public void draw()
	{
		for (int i = 0; i < 5; i++)
		{
			if(getRemainingDeck() > 0)
			{
				hand.add(getCardFromDealer());
			}
		}
		Collections.sort(hand);
	}

	/**
	 * gets the number of cards in the deck, handles all socket communication for this
	 */
	public int getRemainingDeck()
	{
		try
		{
			dealerOutput.writeInt(Dealer.GET_REMAINING);
			return (Integer) dealerInput.readObject(); // object stream readInt() is broken, so wrap int with Integer
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return Integer.MIN_VALUE; // impossible to reach but the compiler is dumb
	}
	
	/**
	 * closes the connection to the dealer
	 */
	public void closeConnection()
	{
		try
		{
			dealerOutput.writeInt(Dealer.CLOSE_CONNECTION);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 *  handles all socket communications with the dealer in order to get a card
	 */
	private Card getCardFromDealer()
	{
		try
		{
			dealerOutput.writeInt(Dealer.GET_A_CARD);
			Card tmp = (Card) dealerInput.readObject();
			System.out.println("Recieve: " + tmp.getRank() + "\n");
			return tmp;
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return null; // impossible to reach but the compiler is dumb
	}
	
	
	/**
	 * while loops until it receives a message stating that it is this players turn
	 * handles requests from other players for cards
	 * forwards all other messages
	 */
	public int[] waitForTurn()
	{
		int[] scores = null; // null is required due to compiler, should always be overridden by first if block
		boolean otherTurn = true;
		while(otherTurn)
		{
			try
			{
				Message input = (Message) upstream.readObject();
				if(input.messageType == YOUR_TURN)
				{
					scores = input.scores;
					otherTurn = false;
				}
				else if(input.messageType == HAND_SIZE)
				{
					input.numberOfCards[playernumber] = hand.size();
					downstream.writeObject(input);
				}
				else if(input.recipientPlayer == playernumber)
				{
					Message output = new Message();
					output.recipientPlayer = input.originPlayer;
					output.cards = giveCards(input.rank);
					downstream.writeObject(output);
				}
				else
				{
					downstream.writeObject(input);
				}
			} catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
		return scores;
	}
	
	/**
	 * tells the next player it is their turn, and tells them what everyone's score is
	 */
	public void endTurn(int[] scores)
	{
		try
		{
			scores[playernumber] = score;
			Message output = new Message();
			output.messageType = YOUR_TURN;
			output.scores = scores;
			downstream.writeObject(output);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * asks the other players how many cards are in their hand and return an array containing their answer
	 */
	public int[] getNumberCardsInHands()
	{
		int[] cardsInHands = new int[numberOfPlayers];
		Message message = new Message();
		message.messageType = HAND_SIZE;
		message.numberOfCards = cardsInHands;
		try
		{
			downstream.writeObject(message);
			message = (Message) upstream.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		cardsInHands = message.numberOfCards;
		cardsInHands[playernumber] = hand.size();
		return cardsInHands;
	}
	
	/**
	 * creates a server socket and waits for the connection from peer
	 */
	private class PlayerThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				ServerSocket server = new ServerSocket(PLAYER_PORT);
				Socket up = server.accept();
				upstream = new ObjectInputStream(up.getInputStream());
				server.close();
			} catch (IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
	}// private class player thread
	

	
} // public class player
