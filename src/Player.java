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

	public Player(String name, int num)
	{
		hand = new ArrayList<Card>();
		score = 0;
		this.name = name;
		playernumber = num;

		try
		{
			sock = new Socket("localhost", Dealer.SOCKET_NUMBER);
			output = new DataOutputStream(sock.getOutputStream());
			input = new ObjectInputStream(sock.getInputStream());

		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public int getPlayerNumber()
	{
		return playernumber;
	}

	public String getPlayerName()
	{
		return name;
	}

	public boolean requestCardsFromOther(CardRank rank, Player other)
	{
		boolean retval = true;
		Card[] cardsFromOther = other.giveCards(rank);

		// other player did not have requested card, draw a
		// card from the deck
		if (cardsFromOther.length == 0)
		{
			// TODO put if statement checking if the deck is empty
			
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
		}

		Collections.sort(hand);
		return retval;
	}

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
	}

	public int getScore()
	{
		return score;
	}

	public ArrayList<Card> getPlayerHand()
	{
		return hand;
	}

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

	public void draw()
	{
		for (int i = 0; i < 5; i++)
		{
			hand.add(getCardFromDealer());
		}
		Collections.sort(hand);
	}
	
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
}
