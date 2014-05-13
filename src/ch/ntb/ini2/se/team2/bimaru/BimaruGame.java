package ch.ntb.ini2.se.team2.bimaru;

import java.awt.Container;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	private LevelSelectView lsv;
	//private String gamename="game.xml";
	
	

	/**
	 * Erstellt ein neues Spiel.
	 */
	public BimaruGame() {
		loadGame("game.xml");		
		setTitle("Bimaru");
		Container contentPane = getContentPane();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		lsv=new LevelSelectView(this);
		contentPane.add(lsv);
		contentPane.add(new ToolBarView(this));
		contentPane.add(view);
		contentPane.add(new AvailableShipsView(ggm));

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public BimaruGame(String game) {
		loadGame(game);		
		setTitle("Bimaru");
		Container contentPane = getContentPane();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		lsv=new LevelSelectView(this);
		contentPane.add(lsv);
		contentPane.add(new ToolBarView(this));
		contentPane.add(view);
		contentPane.add(new AvailableShipsView(ggm));

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}


	/**
	 * Startet das Spiel.
	 * @param args Kommandozeilenparameter
	 */
	public static void main(String[] args) {
		new BimaruGame();
		
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
	

}
