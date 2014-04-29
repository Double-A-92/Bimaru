package ch.ntb.ini2.se.team2.bimaru;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LevelSelectView extends JPanel {
	private static final long serialVersionUID = -6070731239912690911L;
	
	//Globale Variable, welches den Namen des Ausgewählten Spiels enthält
	//und an die anderen Klassen weitergeleitet wird
	private String gamename;

	public String getGamename() {
		return gamename;
	}

	public LevelSelectView(BimaruGame bimaruGame) {
		this.add(new JLabel("TODO: Level Selector"));
		gamename="";
	}


}
