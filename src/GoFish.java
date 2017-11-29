import java.util.ArrayList;
import java.util.Scanner;

public class GoFish {

	public static void main (String args[])
	{
		Scanner scan= new Scanner(System.in);
		
		int numofplayers=2;
		Deck deck= new Deck();
		deck.shuffle();
		Player players[]= new Player[2];
		
		for(int i=0; i<numofplayers; i++)
		{
			players[i]= new Player(deck, "Player "+(i+1), (i+1));
			players[i].getStatingHand();
		}
		
		playgame(numofplayers, players, scan, deck);
		
		scan.close();
	}

	private static void playgame(int numofplayers, Player[] players, Scanner scan, Deck deck) {
		int totalscore= checkscore(numofplayers, players);
		
		while(totalscore!=10)
		{
			//each player gets a turn
			for(int i=0; i<numofplayers; i++)
			{
				if(players[i].getPlayerHand().size()==0 && deck.getNumofDeckLeft()>0)
				{
					System.out.println(players[i].getPlayerName()+" ran out of cards and there are still cards in the deck");
					players[i].draw();
				}
				
				
				//if player does not have any cards they do not get to go
				else if(players[i].getPlayerHand().size()==0)
				{
					System.out.println(players[i].getPlayerName()+ " does not have any cards, their turn is skipped");
					continue;
				}
				
				printCurrentPlayersCards(players[i]);
				
				//otherwise, player gets to go until turn is over (no more matches)
				System.out.println(players[i].getPlayerName()+" Please select the rank of the card you wish to ask for (integer 1-10)");
				int userrank= scan.nextInt();
				userrank=checkUserRankinput(userrank, scan, players[i]);
				CardRank rank= checkRank(players[i], getCardRank(userrank), scan);
				
				System.out.println(players[i].getPlayerName()+" Please input the number of the player you wish to ask the card from. Ex. Player 1 would be 1");
				int otherplace= scan.nextInt();
				while(otherplace>numofplayers || players[i].getPlayerNumber()==otherplace)
				{
					System.out.println("Select a player between 1 and "+numofplayers+" that is not yourself");
					System.out.println(players[i].getPlayerName()+ " Please input the number of the player you wish to ask the card from. Ex. Player 1 would be 1");
					otherplace= scan.nextInt();
				}
				
				Player other= players[otherplace-1];
				
				while(players[i].requestCardsFromOther(rank, other))
				{
					if(players[i].getPlayerHand().size()==0 && deck.getNumofDeckLeft()>0)
					{
						System.out.println(players[i].getPlayerName()+" ran out of cards and there are still cards in the deck");
						players[i].draw();
					}
					if(players[i].getPlayerHand().size()==0)
					{
						break;
					}
					
					printCurrentPlayersCards(players[i]);
					
					//if returned true then they got the cards
					System.out.println(players[i].getPlayerName()+" Please select the rank of the card you wish to ask for (integer 1-10)");
					//userrank= scan.nextInt();
					userrank=checkUserRankinput(scan.nextInt(), scan, players[i]);
					rank=checkRank(players[i], getCardRank(userrank), scan);
					
					System.out.println(players[i].getPlayerName()+ " Please input the number of the player you wish to ask the card from. Ex. Player 1 would be 1");
					otherplace= scan.nextInt();
					while(otherplace>numofplayers || players[i].getPlayerNumber()==otherplace)
					{
						System.out.println("Select a player between 1 and "+numofplayers+" that is not yourself");
						System.out.println(players[i].getPlayerName()+ " Please input the number of the player you wish to ask the card from. Ex. Player 1 would be 1");
						otherplace= scan.nextInt();
					}
					other= players[otherplace-1];
				}
				System.out.println(players[i].getPlayerName()+" GO FISH");
				System.out.println();
			}
			
			//see if anyone has won
			totalscore=checkscore(numofplayers, players);
			
			if(totalscore !=10)
			{
				printplayerhands(numofplayers, players);
			}	
		}
	}

	private static int checkUserRankinput(int userrank, Scanner scan, Player player) {
		if(userrank>0 && userrank<11)
		{
			return userrank;
		}
		System.out.println(player.getPlayerName()+" Please select the rank of the card you wish to ask for (integer 1-10)");
		int newrank= scan.nextInt();
		while(newrank<1 || newrank>10)
		{
			System.out.println(player.getPlayerName()+" Please select the rank of the card you wish to ask for (integer 1-10)");
			newrank= scan.nextInt();
		}
		return newrank;
	}

	private static void printCurrentPlayersCards(Player player) {
		System.out.println(player.getPlayerName()+"'s cards");
		ArrayList<Card> temp= player.getPlayerHand();
		for(int j=0; j<temp.size(); j++)
		{
			System.out.print(" "+temp.get(j).getRank());
		}
		System.out.println();
	}

	private static int checkscore(int numofplayers, Player[] players) {
		int totalscore=0;
		for(int i=0; i<numofplayers; i++)
		{
				totalscore= totalscore+ players[i].getScore();
				System.out.println(players[i].getPlayerName()+" has "+ players[i].getScore()+" points");
				
		}
		System.out.println();
		System.out.println();
		return totalscore;
	}

	private static void printplayerhands(int numofplayers, Player[] players) {
		for(int i=0; i<numofplayers;i++)
		{
			System.out.println("name "+players[i].getPlayerName());
			ArrayList<Card> temp= players[i].getPlayerHand();
			for(int j=0; j<temp.size(); j++)
			{
				System.out.print(" "+temp.get(j).getRank());
			}
			System.out.println();
			System.out.println();
		}
	}

	private static CardRank checkRank(Player player, CardRank rank, Scanner scan) {
		while(!player.checkIfInHand(rank))
		{
			System.out.println("Selected rank is not in "+ player.getPlayerName()+"'s hand");
			System.out.println(player.getPlayerName()+" Please select the rank of the card you wish to ask for (integer 1-10)");
			int userrank= scan.nextInt();
			rank = getCardRank(userrank);
		}
		return rank;
	}

	private static CardRank getCardRank(int userrank) {
		if(userrank==CardRank.ONE.getValue())
		{
			return CardRank.ONE;
		}
		if(userrank==CardRank.TWO.getValue())
		{
			return CardRank.TWO;
		}
		if(userrank==CardRank.THREE.getValue())
		{
			return CardRank.THREE;
		}
		if(userrank==CardRank.FOUR.getValue())
		{
			return CardRank.FOUR;
		}
		if(userrank==CardRank.FIVE.getValue())
		{
			return CardRank.FIVE;
		}
		if(userrank==CardRank.SIX.getValue())
		{
			return CardRank.SIX;
		}
		if(userrank==CardRank.SEVEN.getValue())
		{
			return CardRank.SEVEN;
		}
		if(userrank==CardRank.EIGHT.getValue())
		{
			return CardRank.EIGHT;
		}
		if(userrank==CardRank.NINE.getValue())
		{
			return CardRank.NINE;
		}
		return CardRank.TEN;
	}
}
