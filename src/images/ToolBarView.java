package ch.ntb.ini2.se.team2.bimaru;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ToolBarView extends JPanel {
	private static final long serialVersionUID = 6118507465544237331L;
	private GameGridModel ggm;
	private GameGridView view ;
	private int errorcount=0;
	
	public ToolBarView(BimaruGame bimaruGame) {
		ggm=bimaruGame.getGGM();
		this.view=bimaruGame.getView();
		 JButton buttonCheck = new JButton("Check");
	        //Add action listener to button
	        buttonCheck.addActionListener(new ActionListener() {
	 
	            public void actionPerformed(ActionEvent e)
	            {
	            	checkFieldState();
	            	JOptionPane.showMessageDialog(null,
	            		    "You have "+errorcount+" error(s)",
	            		    "Check Result",
	            		    JOptionPane.ERROR_MESSAGE);
	            	errorcount=0;
	            }
	        });  
	        
	      
	      this.add(buttonCheck);
	}
	
	public void checkFieldState(){
		
		for (int i = 0; i < ggm.getXSize(); i++) {
			for (int j = 0; j < ggm.getYSize(); j++) {
				if(ggm.getFieldState(i, j)!=0 && ggm.getFieldState(i, j)!=ggm.getRealFieldState(i, j))
				{
					errorcount++;
					ggm.setFieldState(i, j, ggm.getFieldState(i, j)+2);
					view.updateButton(i, j);
				
				}			
				
			}
			
		}
		
	
		
	}

}
