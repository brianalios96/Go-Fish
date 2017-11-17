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

	public void requestCardsFromOther(CardRank rank, Player other)
	{
		Card[] input = other.giveCards(rank);
		if(input.length == 0)
		{
			Card tmp = deck.drawCard();
			hand.add(tmp);
		}
		else
		{
			for(Card card: input)
			{
				hand.add(card);
			}
		}
		
		Collections.sort(hand);
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
	
}
