import java.util.ArrayList;
import java.util.Collections;

public class Player
{
	private ArrayList<Card> hand;
	private int score;
	private Deck deck;
	
	public Player(Deck deck)
	{
		hand = new ArrayList<Card>();
		score = 0;
		this.deck = deck;
	}

	public boolean requestCardsFromOther(CardRank rank, Player other)
	{
		boolean retval = true;
		Card[] input = other.giveCards(rank);
		if(input.length == 0)
		{
			Card tmp = deck.drawCard();
			hand.add(tmp);
			if(tmp.getRank() != rank)
			{
				retval = false;
			}
		}
		else
		{
			for(Card card: input)
			{
				hand.add(card);
			}
		}
		
		for(CardRank aRank: CardRank.values())
		{
			int count = 0;
			
			for(Card card: hand)
			{
				if(card.getRank() == aRank)
				{
					count++;
				}
			}
			
			if(count == 4)
			{
				score++;
				for(int i = 0; i < hand.size(); i++)
				{
					if(hand.get(i).getRank() == aRank)
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
		for(Card card: hand)
		{
			if(card.getRank() == rank)
			{
				count++;
			}
		}
		Card cards[] = new Card[count];
		
		for(int i = 0; i < cards.length; i++)
		{
			for(int j = 0; j < hand.size(); j++)
			{
				if(hand.get(j).getRank() == rank)
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
	
}
