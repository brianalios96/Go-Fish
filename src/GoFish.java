import java.util.*;

public class GoFish
{
	private static final String SELECT_RANK = " Please select the rank of the card you wish to ask for (integer 1-10)";
	private static final String SELECT_PLAYER = " Please input the number of the player you wish to ask the card from. Ex. Player 1 would be 1";
	
	public static void main(String args[])
	{
		Scanner scan = new Scanner(System.in);
		
		Deck deck = new Deck();
		
		Player players[] = new Player[2];

		for (int i = 0; i < players.length; i++)
		{
			players[i] = new Player("Player " + (i + 1), (i + 1));
			players[i].draw();
		}

		playgame(players, scan, deck);

		scan.close();
	}

	private static void playgame(Player[] players, Scanner scan, Deck deck)
	{
		int totalscore = checkscore(players.length, players);

		while (totalscore != 10)
		{
			// each player gets a turn
			for (int i = 0; i < players.length; i++)
			{
				if (players[i].getPlayerHand().size() == 0 && deck.getNumofDeckLeft() > 0)
				{
					System.out.println(
							players[i].getPlayerName() + " ran out of cards and there are still cards in the deck");
					players[i].draw();
				}

				// if player does not have any cards they do not get to go
				else if (players[i].getPlayerHand().size() == 0)
				{
					System.out.println(players[i].getPlayerName() + " does not have any cards, their turn is skipped");
					continue;
				}

				printCurrentPlayersCards(players[i]);

				// otherwise, player gets to go until turn is over (no more
				// matches)
				
				int userrank = checkUserRankinput(scan, players[i]);
				CardRank rank = checkRank(players[i], getCardRank(userrank), scan);

				System.out.println(players[i].getPlayerName() + SELECT_PLAYER);
				int otherplace = scan.nextInt();
				while (otherplace > players.length || players[i].getPlayerNumber() == otherplace)
				{
					System.out.println("Select a player between 1 and " + players.length + " that is not yourself");
					System.out.println(players[i].getPlayerName() + SELECT_PLAYER);
					otherplace = scan.nextInt();
				}

				Player other = players[otherplace - 1];

				while (players[i].requestCardsFromOther(rank, other))
				{
					if (players[i].getPlayerHand().size() == 0 && deck.getNumofDeckLeft() > 0)
					{
						System.out.println(
								players[i].getPlayerName() + " ran out of cards and there are still cards in the deck");
						players[i].draw();
					}
					if (players[i].getPlayerHand().size() == 0)
					{
						break;
					}

					printCurrentPlayersCards(players[i]);

					// if returned true then they got the cards
					userrank = checkUserRankinput(scan, players[i]);
					rank = checkRank(players[i], getCardRank(userrank), scan);

					System.out.println(players[i].getPlayerName() + SELECT_PLAYER);
					otherplace = scan.nextInt();
					while (otherplace > players.length || players[i].getPlayerNumber() == otherplace)
					{
						System.out.println("Select a player between 1 and " + players.length + " that is not yourself");
						System.out.println(players[i].getPlayerName() + SELECT_PLAYER);
						otherplace = scan.nextInt();
					}
					other = players[otherplace - 1];
				}
				System.out.println(players[i].getPlayerName() + " GO FISH");
				System.out.println();
			}

			// see if anyone has won
			totalscore = checkscore(players.length, players);

			if (totalscore != 10)
			{
				printplayerhands(players.length, players);
			}
		}
	}

	private static int checkUserRankinput(Scanner scan, Player player)
	{
		int newrank = 0;
		while (newrank < 1 || newrank > 10)
		{
			System.out.println(player.getPlayerName() + SELECT_RANK);
			newrank = scan.nextInt();
		}
		return newrank;
	}

	private static CardRank checkRank(Player player, CardRank rank, Scanner scan)
	{
		while (!player.checkIfInHand(rank))
		{
			System.out.println("Selected rank is not in " + player.getPlayerName() + "'s hand");
			System.out.println(player.getPlayerName() + SELECT_RANK);
			int userrank = scan.nextInt();
			rank = getCardRank(userrank);
		}
		return rank;
	}

	private static CardRank getCardRank(int userrank)
	{
		for(CardRank rank : CardRank.values())
		{
			if(userrank == rank.getValue())
			{
				return rank;
			}
		}
		return null; // only happends if invalid number is passed
	}
	
	private static void printCurrentPlayersCards(Player player)
	{
		System.out.println(player.getPlayerName() + "'s cards");
		ArrayList<Card> temp = player.getPlayerHand();
		for (int j = 0; j < temp.size(); j++)
		{
			System.out.print(" " + temp.get(j).getRank());
		}
		System.out.println();
	}

	private static int checkscore(int numofplayers, Player[] players)
	{
		int totalscore = 0;
		for (int i = 0; i < numofplayers; i++)
		{
			totalscore = totalscore + players[i].getScore();
			System.out.println(players[i].getPlayerName() + " has " + players[i].getScore() + " points");

		}
		System.out.println();
		System.out.println();
		return totalscore;
	}

	private static void printplayerhands(int numofplayers, Player[] players)
	{
		for (int i = 0; i < numofplayers; i++)
		{
			System.out.println("name " + players[i].getPlayerName());
			ArrayList<Card> temp = players[i].getPlayerHand();
			for (int j = 0; j < temp.size(); j++)
			{
				System.out.print(" " + temp.get(j).getRank());
			}
			System.out.println();
			System.out.println();
		}
	}
}
