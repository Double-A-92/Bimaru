package ch.ntb.ini2.se.team2.bimaru;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 * Baut die View des Spielfeldes bestehend aus FieldButtons auf.
 * 
 * @author Amedeo Amato
 */
public class GameGridView extends JPanel implements Observer {
	private static final long serialVersionUID = -4348366816890703134L;
	private int xSize, ySize;
	private FieldButton [][] fields;
	private PartsCounterLabel[][] partsCounter;

	/**
	 * Erstellt ein neues Spielfeld in der passenden Grösse.
	 * @param model aktuelles GameGridModel
	 */
	public GameGridView(GameGridModel model) {
		// Wird bei Zustandsänderungen im Model benachrichtigt
		model.addObserver(this);
		
		xSize = model.getXSize();
		ySize = model.getYSize();
		fields = new FieldButton[xSize][ySize];	
		partsCounter = new PartsCounterLabel[2][(xSize > ySize)?xSize:ySize];
		setLayout(new GridLayout(model.getYSize()+1, model.getXSize()+1, 0, 0));
		
		// Erstellt die einzelnen Felder und Zähler des Spielfeldes
		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {
				fields[x][y] = new FieldButton(x, y, model);
				fields[x][y].updateButton();
				this.add(fields[x][y]);
			}	
			
			partsCounter[1][y] = new PartsCounterLabel(true, y, model);
			partsCounter[1][y].updateLabel();
			this.add(partsCounter[1][y]);
		}
		
		for (int x = 0; x < xSize; x++) {
			partsCounter[0][x] = new PartsCounterLabel(false, x, model);
			partsCounter[0][x].updateLabel();
			this.add(partsCounter[0][x]);
		}
	}
	
	@Override
	public void update(Observable model, Object changedField) {
		if (model instanceof GameGridModel) {
			// Aktualisiert die Feldzähler am Rand
			int x = ((int[]) changedField)[0];
			int y = ((int[]) changedField)[1];
			partsCounter[0][x].updateLabel();
			partsCounter[1][y].updateLabel();
			
			//Aktualisiert alle umgebenden Felder, des gerade geänderten Felds.
			int leftBorder = 0;
			if (x-1 > 0) leftBorder = x-1;
			
			int upperBorder = 0;
			if (y-1 > 0) upperBorder = y-1;
			
			int rightBorder = xSize-1;
			if (x+1 < xSize-1) rightBorder = x+1;
			
			int lowerBorder = ySize-1;
			if (y+1 < ySize-1) lowerBorder = y+1;
			
			for (int i = leftBorder; i <= rightBorder; i++) {
				for (int j = upperBorder; j <= lowerBorder; j++) {
					fields[i][j].updateButton();					
				}
			}
		}	
	}
	
	public void updateButton(int x,int y){
		fields[x][y].updateButton();
	}
}
