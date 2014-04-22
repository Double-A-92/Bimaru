package ch.ntb.ini2.se.team2.bimaru;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolBarView2 extends JPanel {
	private static final long serialVersionUID = 6118507465544237331L;

	public ToolBarView2(BimaruGame bimaruGame) {

		FlowLayout fl = new FlowLayout();
		setLayout(fl);
		addButtons();
	}
	
	public void addButtons(){
		JButton b1 = new JButton(createIcon("/images/undo.png"));
		add(b1);
		JButton b2 = new JButton(createIcon("/images/redo.png"));
		add(b2);
		JButton b3 = new JButton(createIcon("/images/eye.jpg"));
		add(b3);
		JButton b4 = new JButton(createIcon("/images/refresh.png"));
		add(b4);
		JButton b5 = new JButton(createIcon("/images/check.jpg"));
		add(b5);
		JButton b6 = new JButton(createIcon("/images/clock.png"));
		add(b6);
		JButton b7 = new JButton(createIcon("/images/help.png"));
		add(b7);
		JButton b8 = new JButton(createIcon("/images/save.jpg"));
		add(b8);
		JButton b9 = new JButton(createIcon("/images/print.png"));
		add(b9);
	}
	
	public void actionPerformed(ActionEvent action){
		int id = action.getID();
		switch (id) {
		case 1:
			break;
		case 2:
			break;
		default:
			break;
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
