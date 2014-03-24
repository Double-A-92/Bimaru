package ch.ntb.ini2.se.team2.bimaru;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class FieldButton extends JButton {
	private static final long serialVersionUID = 7730763252504355345L;
	int buttonState;
	int x, y;
	private GameGridModel model;
	
	public FieldButton(int px, int py, GameGridModel pmodel) {
		super();
		setSize(45, 45);
		setPreferredSize(new Dimension(45, 45));
		this.x = px;
		this.y = py;
		this.model = pmodel;
		setContentAreaFilled(false);
		
		addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				model.toggleFieldState(x, y);
				updateButton();
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if (arg0.getModifiers() == MouseEvent.BUTTON1_MASK) {
					if (model.getLastStateChanged() == (model.getFieldState(x, y) + 1) % 3)
					model.toggleFieldState(x, y);
					updateButton();
			    }
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		updateButton();
	}
	
	public void updateButton() {
		buttonState = model.getFieldState(x, y);
		
		switch (buttonState) {
		case 0:
			setIcon(getScaledImageIcon("/images/empty.png"));
			break;
		
		case 1:
			setIcon(getScaledImageIcon("/images/water.png"));
			break;
		
		case 2:
			setIcon(getScaledImageIcon("/images/ship_part_grey.png"));
			break;
		}
		
	}
	
	private ImageIcon getScaledImageIcon(String path) {
		try {
			return new ImageIcon(ImageIO.read(getClass().getResource(path)).getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
}
