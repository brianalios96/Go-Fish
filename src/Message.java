import java.io.Serializable;


public class Message implements Serializable
{
	private static final long serialVersionUID = -920539635218577428L;

	public int recipientPlayer;
	public int originPlayer;
	public int messageType;
	public CardRank rank;
	public Card[] cards;
	public int scores[];
}
