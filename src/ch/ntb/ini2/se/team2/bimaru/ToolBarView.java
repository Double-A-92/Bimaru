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
	
	JButton undo;
	JButton redo;
	JButton eye;
	JButton refresh;
	JButton check;
	JButton clock;
	JButton help;
	JButton save;
	JButton print;
	
	public ToolBarView(BimaruGame bimaruGame) {
		ggm=bimaruGame.getGGM();
		this.view=bimaruGame.getView();
		
		FlowLayout fl = new FlowLayout();
		setLayout(fl);
		addButtons();
	}
	
	public void addButtons(){
		undo = new JButton(createIcon("/images/icon/undo.png"));
		undo.addActionListener(this);
		//add(undo);
		redo = new JButton(createIcon("/images/icon/redo.png"));
		redo.addActionListener(this);
		//add(redo);
		eye = new JButton(createIcon("/images/icon/eye.jpg"));
		eye.addActionListener(this);
		add(eye);
		refresh = new JButton(createIcon("/images/icon/refresh.png"));
		refresh.addActionListener(this);
		//add(refresh);
		check = new JButton(createIcon("/images/icon/check.jpg"));
		check.setHideActionText(true);
		check.addActionListener(this);
		add(check);
		clock = new JButton(createIcon("/images/icon/clock.png"));
		clock.addActionListener(this);
		//add(clock);
		help = new JButton(createIcon("/images/icon/help.png"));
		help.addActionListener(this);
		//add(help);
		save = new JButton(createIcon("/images/icon/save.jpg"));
		save.addActionListener(this);
		//add(save);
		print = new JButton(createIcon("/images/icon/print.png"));
		print.addActionListener(this);
		//add(print);
	}
	
	public void actionPerformed(ActionEvent action){
		if (action.getSource()==this.check) {
			
        	checkFieldState();
        	JOptionPane.showMessageDialog(null,
        		    "You have "+errorcount+" error(s)",
        		    "Check Result",
        		    JOptionPane.ERROR_MESSAGE);
        	errorcount=0;
		}
		else if (action.getSource()==this.clock){
		}
		else if(action.getSource()==this.eye){
			Object[] options = { "OK", "CANCEL" };
			int eingabe = JOptionPane.showOptionDialog(null, 
					 "Showing solution will end this game", "Solution", 
					 JOptionPane.DEFAULT_OPTION, 
					 JOptionPane.WARNING_MESSAGE,null, options, options[0]);
			if(eingabe==0){
				showSolution();
			}
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
	
	public void showSolution(){
		for (int i = 0; i < ggm.getXSize(); i++) {
			for (int j = 0; j < ggm.getYSize(); j++) {
					ggm.setFieldState(i, j, ggm.getRealFieldState(i, j));
					view.updateButton(i, j);
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
