package ch.ntb.ini2.se.team2.bimaru;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

/**
 * Zeigt am Rand des Spielfeldes an, wieviele Schiffsteile pro Zeile bzw. Spalte ben�tigt werden.
 * 
 * @author Amedeo Amato
 */
public class PartsCounterLabel extends JLabel {
	private static final long serialVersionUID = -5155361543082783632L;
	private boolean forRow;
	private int position;
	private GameGridModel model;

	/**
	 * Erstellt einen neuen Z�hler.
	 * @param forRow Gibt an ob Schiffsteile in Zeile gez�hlt werden (sonst in Spalte).
	 * @param position Koordinate der zu z�hlenden Zeile bzw. Spalte
	 * @param model aktuelles GameGridModel
	 */
	public PartsCounterLabel(boolean forRow, int position, GameGridModel model) {
		this.forRow = forRow;
		this.position = position;
		this.model = model;
		setHorizontalAlignment(JLabel.CENTER);
		setFont(new Font(getFont().getName(), Font.BOLD, 20));
		setForeground(Color.black);
	}

	/**
	 * �ndert abh�ngig von den aktuellen Feldzust�nden in der Zeile bzw. Spalte die Farbe des Z�hlers.
	 * Schwarz = Unvollst�ndig; Grau = Passend; Rot = Zuviele Schiffe
	 */
	public void updateLabel() {
		int numOfRealShipParts = model.countRealShipParts(forRow, position);
		setText(String.valueOf(numOfRealShipParts));
		
		int numOfShipParts = model.countShipParts(forRow, position);
		int numOfWaterFields = model.countWaterFields(forRow, position);
		int numOfRealWaterFields = (forRow ? model.getXSize(): model.getYSize())-numOfRealShipParts;
		
		if (numOfShipParts > numOfRealShipParts) {
			setForeground(Color.red);
		} else if(numOfShipParts == numOfRealShipParts && numOfWaterFields == numOfRealWaterFields) {
			setForeground(Color.gray);
		} else {
			setForeground(Color.black);
		}
	}
	
}
