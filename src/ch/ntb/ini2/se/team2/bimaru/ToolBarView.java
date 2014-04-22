package ch.ntb.ini2.se.team2.bimaru;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ToolBarView extends JPanel implements ActionListener{
	private static final long serialVersionUID = 6118507465544237331L;
	int errorcount=0;
	private GameGridModel ggm;
	private GameGridView view ;
	JButton b1;
	JButton b2;
	JButton b3;
	JButton b4;
	JButton b5;
	JButton b6;
	JButton b7;
	JButton b8;
	JButton b9;
	
	public ToolBarView(BimaruGame bimaruGame) {
		ggm=bimaruGame.getGGM();
		this.view=bimaruGame.getView();
		
		FlowLayout fl = new FlowLayout();
		setLayout(fl);
		addButtons();
	}
	
	public void addButtons(){
		b1 = new JButton(createIcon("/images/undo.png"));
		b1.setText("undo");
		b1.setHideActionText(true);
		b1.addActionListener(this);
		add(b1);
		b2 = new JButton(createIcon("/images/redo.png"));
		b2.setText("redo");
		b2.setHideActionText(true);
		b2.addActionListener(this);
		add(b2);
		b3 = new JButton(createIcon("/images/eye.jpg"));
		b3.setText("eye");
		b3.setHideActionText(true);
		b3.addActionListener(this);
		add(b3);
		b4 = new JButton(createIcon("/images/refresh.png"));
		b4.setText("refresh");
		b4.setHideActionText(true);
		b4.addActionListener(this);
		add(b4);
		b5 = new JButton(createIcon("/images/check.jpg"));
		b5.setHideActionText(true);
		b5.setText("check");
		b5.setHideActionText(true);
		b5.addActionListener(this);
		add(b5);
		b6 = new JButton(createIcon("/images/clock.png"));
		b6.setText("clock");
		b6.setHideActionText(true);
		b6.addActionListener(this);
		add(b6);
		b7 = new JButton(createIcon("/images/help.png"));
		b7.setText("help");
		b7.setHideActionText(true);
		b7.addActionListener(this);
		add(b7);
		b8 = new JButton(createIcon("/images/save.jpg"));
		b8.setText("save");
		b8.setHideActionText(true);
		b8.addActionListener(this);
		add(b8);
		b9 = new JButton(createIcon("/images/print.png"));
		b9.setText("print");
		b9.setHideActionText(true);
		b9.addActionListener(this);
		add(b9);
	}
	
	public void actionPerformed(ActionEvent action){
		
		System.out.print("klick");
		if (action.getSource()==this.b5) {
			
        	checkFieldState();
        	JOptionPane.showMessageDialog(null,
        		    "You have "+errorcount+" error(s)",
        		    "Check Result",
        		    JOptionPane.ERROR_MESSAGE);
        	errorcount=0;
		}
		else if (action.getSource()==this.b6){
		}
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
	
	protected static ImageIcon createIcon(String path) {
        URL imgURL = ToolBarView.class.getResource(path);	//import java.net.URL; 
        if (imgURL != null) {
        	ImageIcon img = new ImageIcon(imgURL);  
        	Image newimg = img.getImage().getScaledInstance(25,25,java.awt.Image.SCALE_SMOOTH ) ;  
        	return new ImageIcon(newimg);
        } else {
            System.err.println("Couldn't find image in system: " + path);
            return null;
        }
    }
}
