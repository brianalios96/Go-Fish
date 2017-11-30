import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

//Dantran93/openburn

public class GoFishGUI extends Application {

	private static final String WINDOW_TITLE = "Go Fish";
	// private static final String ICON_FILE_PATH = "/images/TODO";

	private static final int WINDOW_WIDTH = 1200;
	private static final int WINDOW_HEIGHT = 600;
	
	private PointView playerpoints;
	
	private Deck deck;
	private Player players[];

	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		//Initialize the deck and the 3 players
		deck= new Deck();
		players= new Player[3];
		for(int i=0; i<3; i++)
		{
			players[i]= new Player("Player"+(i+1), (i+1));
		}
		//players ready to play the game
		
		
		// Initialize primary window
		Pane root = new Pane();
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		stage.setScene(scene);
		stage.setTitle(WINDOW_TITLE);
		// stage.getIcons().add(new
		// Image(this.getClass().getResourceAsStream(ICON_FILE_PATH)));
		stage.setResizable(false);
		Pane frame = new Pane();
		scene.setRoot(frame);

		showPlayerCards(frame);
		showPlayerPoints(frame);
		showOtherPlayerCards(frame);
		showOtherPlayerPoints(frame);
		// Display window
		stage.show();
		
		startgame();
	}

	private void startgame() {
		// TODO Auto-generated method stub
		
	}

	private void showOtherPlayerPoints(Pane frame) {
		// TODO Auto-generated method stub
		
	}

	private void showOtherPlayerCards(Pane frame) {
		// TODO Auto-generated method stub
		
	}

	private void showPlayerPoints(Pane frame) {
		// TODO Auto-generated method stub
		playerpoints = new PointView();
		playerpoints.setTranslateX(800);
		playerpoints.setTranslateY(375);
		playerpoints.setPrefWidth(350);
		playerpoints.setPrefHeight(350);
		frame.getChildren().add(playerpoints);
		
	}

	private void showPlayerCards(Pane frame) {
		// TODO Auto-generated method stub
		
	}
}
