import java.io.*;
import java.net.*;

public class Dealer
{
	public static final int SOCKET_NUMBER = 10626;
	
	public static final int CLOSE_CONNECTION = 0;
	public static final int GET_A_CARD = 1;
	public static final int GET_REMAINING = 2;
	
	public static final int NUMBER_OF_PLAYERS = 2;

	/**
	 * main function for dealer / server
	 */
	public static void main(String[] args)
	{
		try
		{
			Deck deck = new Deck();
			ServerSocket server = new ServerSocket(SOCKET_NUMBER);
			
//			System.out.println("Port: " + SOCKET_NUMBER);
//			System.out.println("IP: " + InetAddress.getLocalHost());
			
			DealerThread threads[] = new DealerThread[NUMBER_OF_PLAYERS];
			
			for (int i = 0; i < threads.length; i++)
			{
				threads[i] = new DealerThread(deck, server.accept());
				System.out.println("socket accepted");
				threads[i].start();
			}

			
			for(DealerThread th : threads)
			{
				th.join();
			}
			
			server.close();
			System.out.println("\nServer is closed");
		} catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}// catch
	}// main()

	/**
	 *  allows the dealer to wait for input from multiple sockets at the same time
	 */
	private static class DealerThread extends Thread
	{
		private Deck deck;
		private DataInputStream input;
		private ObjectOutputStream output;
		private Socket sock;

		DealerThread(Deck deck, Socket sock)
		{
			this.deck = deck;
			try
			{
				this.sock = sock;
				output = new ObjectOutputStream(this.sock.getOutputStream());
				input = new DataInputStream(this.sock.getInputStream());
				
			} catch (IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}// dealer thread constructor

		@Override
		public void run()
		{
			try
			{
				int in = GET_A_CARD;
				while (in != CLOSE_CONNECTION)
				{
					in = input.readInt();
					if (in == GET_A_CARD)
					{
						Card tmp = deck.drawCard();
						output.writeObject(tmp);
					}
					else if (in == GET_REMAINING)
					{
						output.writeObject(deck.getNumofDeckLeft()); // object stream readInt() is broken, so wrap int with Integer
					}
				}// while
				System.out.println("break");
			} catch (IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			} // catch
		}// run()
	}// private class dealer thread
}// public class dealer
