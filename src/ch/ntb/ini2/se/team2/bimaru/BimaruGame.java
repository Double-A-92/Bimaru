package ch.ntb.ini2.se.team2.bimaru;

import java.awt.Container;
import java.io.InputStream;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class BimaruGame extends JFrame {
	private static final long serialVersionUID = 2503783248730093300L;
	private GameGridModel ggm;

	public BimaruGame() {
		try {
			JAXBContext context = JAXBContext.newInstance(GameGridModel.class);
			Unmarshaller u = context.createUnmarshaller();

			InputStream is = getClass().getResourceAsStream("/games/game_1.xml");
			ggm = (GameGridModel) u.unmarshal(is);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}

		setTitle("Bimaru");
		Container contentPane = getContentPane();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(new LevelSelectView(this));
		contentPane.add(new ToolBarView(this));
		contentPane.add(new GameGridView(this, ggm));
		contentPane.add(new AvailableShipsView(this));

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new BimaruGame();
	}
}
