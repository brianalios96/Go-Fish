import javafx.scene.image.Image;

public class Card implements Comparable<Card>
{
	private CardRank rank;
	private Image picture;
	
	public Card(CardRank rank, Image picture)
	{
		this.rank = rank;
		this.picture = picture;
	}
	
	public CardRank getRank()
	{
		return rank;
	}
	
	public Image getPicture()
	{
		return picture;
	}

	@Override
	public int compareTo(Card other)
	{
		return rank.compareTo(other.rank);
	}
}
