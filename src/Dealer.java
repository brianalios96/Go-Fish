import java.io.*;
import java.net.*;

public class Dealer
{
	public static final int SOCKET_NUMBER = 10531;
	public static final int GET_A_CARD = 1;
	public static final int CLOSE_CONNECTION = 0;

	public static void main(String[] args)
	{
		try
		{
			Deck deck = new Deck();
			ServerSocket server = new ServerSocket(SOCKET_NUMBER);
			
			DealerThread threads[] = new DealerThread[2];
			
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
			System.out.println("closing");
			server.close();
			System.out.println("closed");
		} catch (IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}

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
		}

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
				}
				System.out.println("break");
			} catch (IOException e)
			{
				e.printStackTrace();
				System.exit(1);
			}

		}
	}
}
