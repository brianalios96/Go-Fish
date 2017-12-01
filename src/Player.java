import java.io.*;
import java.net.*;
import java.util.*;

public class Player
{
	private ArrayList<Card> hand;
	private int score;
	private String name;
	private int playernumber;
	private ObjectInputStream input;
	private DataOutputStream output;
	private Socket sock;

	private static final String URL = "localhost";
//	private static final String URL = "cambridge.cs.arizona.edu";
//	private static final String URL = "192.168.1.115"; // experiment
	private static final String GO_FISH = " GO FISH\n";

	public Player(String name, int num)
	{
		hand = new ArrayList<Card>();
		score = 0;
		this.name = name;
		playernumber = num;

		try
		{
//			System.out.println("Port: " + Dealer.SOCKET_NUMBER);
//			System.out.println("IP: " + InetAddress.getByName(URL));
			
			sock = new Socket(URL, Dealer.SOCKET_NUMBER);
			output = new DataOutputStream(sock.getOutputStream());
			input = new ObjectInputStream(sock.getInputStream());

		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		draw();
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
	public boolean requestCardsFromOther(CardRank rank, Player other)
	{
		boolean retval = true;
		Card[] cardsFromOther = other.giveCards(rank);

		// other player did not have requested card, draw a
		// card from the deck
		if (cardsFromOther.length == 0 && getRemainingDeck() > 0)
		{
			System.out.println(name + GO_FISH);
			
			Card tmp = getCardFromDealer();
			hand.add(tmp);
			if (tmp.getRank() != rank)
			{
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

			if (count == 4)
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

	/**
	 * give all the cards of the requested rank
	 * returns an empty array if player dosn't have that rank
	 */
	public Card[] giveCards(CardRank rank)
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
			output.writeInt(Dealer.GET_REMAINING);
			return (Integer) input.readObject(); // object stream readInt() is broken, so wrap int with Integer
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
			output.writeInt(Dealer.CLOSE_CONNECTION);
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
			output.writeInt(Dealer.GET_A_CARD);
			return (Card) input.readObject();
		} catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return null; // impossible to reach but the compiler is dumb
	}
	
} // public class player
