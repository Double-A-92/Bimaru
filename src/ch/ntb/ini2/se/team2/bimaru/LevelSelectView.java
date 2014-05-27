package ch.ntb.ini2.se.team2.bimaru;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Stellt eine Dropdown-Liste mit den auswählbaren Spielen dar
 *
 * @author Christoph Solis , Amedeo Amato
 */
public class LevelSelectView extends JPanel implements ActionListener{
	private static final long serialVersionUID = -6070731239912690911L;
	@SuppressWarnings("rawtypes")
	private JComboBox gameList;
	private BimaruGame bg;
	private HashMap<String, String> gameListMap;
	
	/**
	 * Erstellt einen neues Panel zur Anzeige einer Dropdown-Liste
	 * mit den auswählbaren Spielen
	 * 
	 * @param bimaruGame aktuelles Spiel-Objekt
	 */
	public LevelSelectView(BimaruGame bimaruGame) {
		bg = bimaruGame;
		this.add(new JLabel("Level-Auswahl:"));
		
		//Liste aus XML-Datei laden
		gameListMap = new HashMap<String, String>();	     
		try {
			JAXBContext context = JAXBContext.newInstance(GameList.class);
			Unmarshaller u = context.createUnmarshaller();

			InputStream is = getClass().getResourceAsStream("/games/gameList.xml");
			gameListMap = ((GameList) u.unmarshal(is)).getGameList();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		//ComboBox generieren
		gameList = new JComboBox<String>(gameListMap.keySet().toArray(new String[0]));
		gameList.setEditable(false);
		gameList.addActionListener(this);
		this.add(gameList);
	}

	@Override
	/**
	 * Erweitert das Dropdown-Menue mit den dazugehörigen Funktionen 
	 * 
	 */
	public void actionPerformed(ActionEvent e) {	
		Object[] options = { "Ja", "Nein" };
		int eingabe = JOptionPane.showOptionDialog(null, "Möchten Sie wirklich ein neues Spiel laden?", "Spiel Laden",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		if (eingabe == 0) {
			String selectedGame =  gameListMap.get(gameList.getSelectedItem().toString());   
			bg.loadNewGame(selectedGame);
		}
	}
}

/**
 * Verwatet die Liste mit den verfügbaren Spielen
 * 
 * @author Amedeo Amato
 */
@XmlRootElement(name = "bimaru")
class GameList {
	private HashMap<String, String> gameList = new HashMap<String, String>();
	
	/**
	 * Gibt die Liste mit den Spielen zurück
	 * 
	 */
	public HashMap<String, String> getGameList() {
        return gameList;
    }
	
	/**
	 * Setzt die Liste mit den Spielen
	 * 
	 */
    public void setGameList(HashMap<String, String> gameList) {
        this.gameList = gameList;
    }
}
