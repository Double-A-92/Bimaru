package ch.ntb.ini2.se.team2.bimaru;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class BimaruGame extends JFrame{
	private static final long serialVersionUID = 2503783248730093300L;
	
	public BimaruGame() {
		setTitle("Bimaru");
		Container contentPane = getContentPane();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(new LevelSelectView(this));
		contentPane.add(new ToolBarView(this));
		contentPane.add(new GameGridView(this, new GameGridModel(10, 10)));
		contentPane.add(new AvailableShipsView(this));
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new BimaruGame();
	}
}
