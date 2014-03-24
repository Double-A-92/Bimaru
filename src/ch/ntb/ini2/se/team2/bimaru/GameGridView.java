package ch.ntb.ini2.se.team2.bimaru;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameGridView extends JPanel {
	private static final long serialVersionUID = -4348366816890703134L;
	int xSize, ySize;
	FieldButton[][] fields;

	public GameGridView(BimaruGame bimaruGame, GameGridModel model) {
		xSize = model.getXSize();
		ySize = model.getYSize();
		fields = new FieldButton[xSize][ySize];
		setLayout(new GridLayout(model.getXSize()+1, model.getYSize()+1, 0, 0));
		
		
		for (int y = 0; y < ySize; y++) {
			for (int x = 0; x < xSize; x++) {
				fields[x][y] = new FieldButton(x, y, model);
				this.add(fields[x][y]);
			}	
			
			JLabel l = new JLabel("4");
			l.setHorizontalAlignment(JLabel.CENTER);
			this.add(l);
		}
		for (int x = 0; x < xSize; x++) {
			JLabel l = new JLabel("3");
			l.setHorizontalAlignment(JLabel.CENTER);
			this.add(l);
		}
		
	}
	
	public void updateGameGrid() {
		
	}

}
