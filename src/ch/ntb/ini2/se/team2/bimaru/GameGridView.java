package ch.ntb.ini2.se.team2.bimaru;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class GameGridView extends JPanel {
	private static final long serialVersionUID = -4348366816890703134L;
	int xSize, ySize;
	FieldButton[][] fields;
	PartsCounterLabel[][] partsCounter;

	public GameGridView(BimaruGame bimaruGame, GameGridModel model) {
		xSize = model.getXSize();
		ySize = model.getYSize();
		fields = new FieldButton[xSize][ySize];	
		partsCounter = new PartsCounterLabel[2][(xSize > ySize)?xSize:ySize];
		setLayout(new GridLayout(model.getYSize()+1, model.getXSize()+1, 0, 0));
		
		
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
	
	public void updateGameGrid() {
		
	}

}
