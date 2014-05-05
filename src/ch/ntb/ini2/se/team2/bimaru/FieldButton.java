package ch.ntb.ini2.se.team2.bimaru;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;

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
	public static HashMap<String, ImageIcon> tileIcons = new HashMap<>();
	int buttonState;
	int x, y;
	private GameGridModel model;
	private boolean canToggel=true;
	
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
				if (canToggel){
					model.toggleFieldState(x, y);
				}
				//updateButton();
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
							//updateButton();
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
		
		//Konflikt?
		boolean isConflicting = false;
		if (model.getFieldState(x, y) == 2) {
			//-Andere Schiffsteile in Diagonale
			if (model.getFieldState(x-1, y-1) == 2 || model.getFieldState(x-1, y+1) == 2
		     || model.getFieldState(x+1, y-1) == 2 || model.getFieldState(x+1, y+1) == 2) {
				isConflicting = true;
			}
			
			//-Angrenzende Schiffsteile aus mehreren Richtungen
			if ((model.getFieldState(x, y-1) == 2 || model.getFieldState(x, y+1) == 2 ) 
			 && (model.getFieldState(x-1, y) == 2 || model.getFieldState(x+1, y) == 2)) {
				isConflicting = true;
			}
			
			//-Passt nicht zu angrenzendem Hinweis
			if (model.isHint(x, y-1) && (model.getRealShipPartType(x, y-1) == 3 || model.getRealShipPartType(x, y-1) == 0)) {
				isConflicting = true;
			} else if (model.isHint(x, y+1) && (model.getRealShipPartType(x, y+1) == 1 || model.getRealShipPartType(x, y+1) == 0)) {
				isConflicting = true;
			} else if (model.isHint(x+1, y) && (model.getRealShipPartType(x+1, y) == 4 || model.getRealShipPartType(x+1, y) == 0)) {
				isConflicting = true;
			} else if (model.isHint(x-1, y) && (model.getRealShipPartType(x-1, y) == 2 || model.getRealShipPartType(x-1, y) == 0)) {
				isConflicting = true;
			}
		}
		
		//Passendes Icon laden und darstellen
		String iconPath = "/images/tile/";		
		if (isConflicting) {
			iconPath = "/images/error_tile/conflict/";
		} else if (buttonState == 3 || buttonState == 4) {
			iconPath = "/images/error_tile/wrong/";
		}
		
		switch (buttonState) {
		case 0:
			setIcon(getScaledImageIcon("/images/tile/empty.png"));
			break;	
		case 1:
			setIcon(getScaledImageIcon("/images/tile/water.png"));
			break;
		case 2:
		case 4:
			int shipPartType;
			if (model.isHint(x, y)) {
				shipPartType = model.getRealShipPartType(x, y);
			} else {
				shipPartType = model.getShipPartType(x, y);
			}
			
			switch (shipPartType) {
			case 0:
				setIcon(getScaledImageIcon(iconPath+"single_ship.png"));
				break;
			case 1:
				setIcon(getScaledImageIcon(iconPath+"ship_end_up.png"));
				break;
			case 2:
				setIcon(getScaledImageIcon(iconPath+"ship_end_right.png"));	
				break;
			case 3:
				setIcon(getScaledImageIcon(iconPath+"ship_end_down.png"));
				break;
			case 4:
				setIcon(getScaledImageIcon(iconPath+"ship_end_left.png"));
				break;
			case 5:
				setIcon(getScaledImageIcon(iconPath+"ship_part.png"));
				break;
			default:
				if (isConflicting) {
					setIcon(getScaledImageIcon(iconPath+"ship_part.png"));
				} else {
					setIcon(getScaledImageIcon(iconPath+"ship_part_grey.png"));
				}
				break;
			}
			break;
		case 3:
			setIcon(getScaledImageIcon("/images/error_tile/wrong/water.png"));
			break;
		}
	}
	
	private ImageIcon getScaledImageIcon(String path) {
		if (tileIcons.containsKey(path)) {
			return tileIcons.get(path);
		} else {
			try {
				tileIcons.put(path, new ImageIcon(ImageIO.read(getClass().getResource(path)).getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH)));
				return tileIcons.get(path);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} 
		}
	}
	public void stopToggel(){
		this.canToggel=false;
	}
	public void startToggel(){
		this.canToggel=true;
	}
}
