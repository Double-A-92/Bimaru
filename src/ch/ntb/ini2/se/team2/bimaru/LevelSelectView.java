package ch.ntb.ini2.se.team2.bimaru;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LevelSelectView extends JPanel implements ActionListener{
	private static final long serialVersionUID = -6070731239912690911L;
	
	//Globale Variable, welches den Namen des Ausgewählten Spiels enthält
	//und an die anderen Klassen weitergeleitet wird
	private File selectedGame;
	private File[] gameNames;
	private JComboBox gameList;
	
	

	public String getGamename() {
		
		return selectedGame.getName();
	}

	public LevelSelectView(BimaruGame bimaruGame) {
		
		this.add(new JLabel("Level Selector"));
		GameNames();
		DropdownMenu();
		this.add(gameList);
		
	}

	public void GameNames(){
		gameNames = new File("//D:/Studium NTB/Semester 4/Softwareengineering/Bimaru/bin/games").listFiles();
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
		
	}

}
