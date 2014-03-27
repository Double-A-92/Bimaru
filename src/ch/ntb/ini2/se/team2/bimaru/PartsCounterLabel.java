package ch.ntb.ini2.se.team2.bimaru;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class PartsCounterLabel extends JLabel {
	private static final long serialVersionUID = -5155361543082783632L;
	private boolean forRow;
	private int position;
	private GameGridModel model;

	public PartsCounterLabel(boolean forRow, int position, GameGridModel model) {
		this.forRow = forRow;
		this.position = position;
		this.model = model;
		setHorizontalAlignment(JLabel.CENTER);
		setFont(new Font(getFont().getName(), Font.BOLD, 20));
		setForeground(Color.black);
	}

	public void updateLabel() {
		int NumOfShipParts = model.countShipParts(forRow, position);
		setText(String.valueOf(NumOfShipParts));
		
	}
	
}
