package ch.ntb.ini2.se.team2.bimaru;

import java.awt.Container;
import java.io.InputStream;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Hauptfenster des Spiels.
 * Erstellt alle nötigen Objekte und stellt die Views in einem Fenster dar.
 *
 */
public class BimaruGame extends JFrame {
	private static final long serialVersionUID = 2503783248730093300L;
	private GameGridModel ggm;
	private GameGridView view;
	private ToolBarView tbv;
	private AvailableShipsView asv;
	private Container contentPane;	

	/**
	 * Erstellt ein neues Spiel.
	 */
	public BimaruGame() {
		loadGame("game_1.xml");		
		setTitle("Bimaru");
		contentPane = getContentPane();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		contentPane.add(new LevelSelectView(this));
		tbv = new ToolBarView(this);
		contentPane.add(tbv);
		contentPane.add(view);
		asv = new AvailableShipsView(ggm);
		contentPane.add(asv);

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void loadNewGame(String game) {	
		contentPane.remove(view);
		contentPane.remove(asv);
		contentPane.invalidate();
		
		loadGame(game);
		tbv.update();
		contentPane.add(view);	
		asv = new AvailableShipsView(ggm);
		contentPane.add(asv);
		contentPane.revalidate();
		contentPane.repaint();
		pack();
		setLocationRelativeTo(null);
	}

	public GameGridView getView() {
		return view;
	}
	
	public GameGridModel getGGM() {
		return ggm;
	}
	
	public void loadGame(String game){	
		try {
			JAXBContext context = JAXBContext.newInstance(GameGridModel.class);
			Unmarshaller u = context.createUnmarshaller();

			InputStream is = getClass().getResourceAsStream("/games/"+game);
			ggm = (GameGridModel) u.unmarshal(is);
			view = new GameGridView(ggm);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
	}	
	
	/**
	 * Startet das Spiel.
	 * @param args Kommandozeilenparameter
	 */
	public static void main(String[] args) {
		new BimaruGame();
		
	}
}
