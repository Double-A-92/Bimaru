package ch.ntb.ini2.se.team2.bimaru;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Stellt ein einzelnes Feld dar, und zeigt den aktuellen Zustand an.
 * Ein Klick oder Drag hierauf löst eine Zustandsänderung aus.
 * 
 * @author Amedeo Amato
 */
public class FieldButton extends JButton {
	private static final long serialVersionUID = 7730763252504355345L;
	int buttonState;
	int x, y;
	private GameGridModel model;
	
	/**
	 * Erstellt einen neuen 45x45 Pixel grossen FieldButton.
	 * @param px x-Koordinate
	 * @param py y-Koordinate
	 * @param pmodel aktuelles GameGridModel
	 */
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
				// Do nothing
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// Normale Zustandsänderung bei Klick
				model.toggleFieldState(x, y);
				updateButton();
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// Do nothing
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// Bei Mouse-Drag über Feld
				if (arg0.getModifiers() == MouseEvent.BUTTON1_MASK) {
					// Wechselt bei mehreren Feldern nur in einen Zustand:
					// Leer -> Wasser oder Schiff; Wasser -> Schiff oder Leer; Schiff toggelt bei Drag nicht.
					int lastStateChanged = model.getLastStateChanged();
					if (model.getFieldState(x, y) == 0 || (model.getFieldState(x, y) == 1 && lastStateChanged != 1)) {
						if (!model.isHint(x, y)) {
							while (model.getFieldState(x, y) != lastStateChanged) {
								model.toggleFieldState(x, y);
							}
							updateButton();
						}	
					}
			    }
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Do nothing
			}
		});
		
		updateButton();
	}
	
	/**
	 * Zeigt je nach Zustand, die entsprechende Graphik auf dem Feld an.
	 */
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
		
		case 3:
			setIcon(getScaledImageIcon("/images/water_error.png"));
			break;
			
		case 4:
			setIcon(getScaledImageIcon("/images/ship_part_error.png"));
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
