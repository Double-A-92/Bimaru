package ch.ntb.ini2.se.team2.bimaru;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;

public class LevelSelectView extends JPanel implements ActionListener{
	private static final long serialVersionUID = -6070731239912690911L;
	
	
	private File selectedGame;
	private File[] gameNames;
	private JComboBox gameList;
	private BimaruGame bg;

	public String getGamename() {
		
		return selectedGame.getName();
	}

	public LevelSelectView(BimaruGame bimaruGame) {
		bg=bimaruGame;
		this.add(new JLabel("Level Selector"));
		GameNames();
		DropdownMenu();
		this.add(gameList);
		
		
	}

	public void GameNames(){
		
		try {
			gameNames = new File(getClass().getResource("/games/").toURI()).listFiles();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//gameNames = new File("src/games/").listFiles();
		for( File file : gameNames )
		    System.out.println( file.getName() );
	}
	
	
	public void DropdownMenu(){
		gameList = new JComboBox(gameNames);
		gameList.setEditable(false);
		gameList.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 	
		selectedGame =  (File) gameList.getSelectedItem();
	         
	    System.out.println(selectedGame.getName());
	    
	    bg=new BimaruGame(selectedGame.getName());
		
	}
	

}
