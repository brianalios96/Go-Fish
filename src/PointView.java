import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PointView extends Pane{

	private static final int First_Column_X = 20;
	
	private static final String Player_Points = "Player's Points";
	
	private Text pointText;
	
	public PointView()
	{
		super();
		pointText= new Text(Player_Points);
		pointText.setTranslateZ(First_Column_X);
		pointText.setTranslateY(20);
		this.getChildren().add(pointText);
	}
	
}

