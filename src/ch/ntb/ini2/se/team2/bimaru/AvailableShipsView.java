package ch.ntb.ini2.se.team2.bimaru;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Zeigt alle Schiffe, die platziert werden können, graphisch an.
 * 
 * @author Amedeo Amato
 */
public class AvailableShipsView extends JPanel {
	private static final long serialVersionUID = -5149432909599696162L;
	private JPanel[] shipLayers;
	private GameGridModel model;

	/**
	 * Holt die Schiffe aus dem Model und zeigt sie an. 
	 * @param model aktuelles GameGridModel
	 */
	public AvailableShipsView(GameGridModel model) {
		this.model = model;
		
		// Legt mehrere JPanels an, in denen jeweils Schiffe gleicher Länge platziert werden.
		int xSize = model.getXSize();
		int ySize = model.getYSize();
		shipLayers = new JPanel[((xSize > ySize)?xSize:ySize)]; //Längstes Schiff kann so lang sein wie Feld
		for (int i = 0; i < shipLayers.length; i++) {
			shipLayers[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
		}
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		JLabel descriptionLabel = new JLabel("Verfügbare Schiffe: (Zum Markieren anklicken)");
		descriptionPanel.add(descriptionLabel);
		this.add(descriptionPanel);
		
		updateAvaibleShips();
		showAvailableShips();
	}
	
	private void showAvailableShips() {
		// Macht JPanels die Schiffe enthalten sichtbar
		for (int i = shipLayers.length-1; i >= 0; i--) {
			if (shipLayers[i].getComponentCount() != 0) {
				this.add(shipLayers[i]);
			}
		}
	}
	
	private void addAvailableShip(int length) {
		shipLayers[length-1].add(new AvailableShip(length));
	}
	
	private void updateAvaibleShips(){
		// Holt die Schiffe aus dem Model und platziert sie in die richtigen JPanels
		for (Ship ship : model.getShips()) {
			addAvailableShip(ship.getSize());
		}
	}

	/**
	 * Stellt ein einzelnes Schiff beliebiger Länge in einem JPanel an.
	 * 
	 * @author Amedeo
	 */
	private class AvailableShip extends JPanel {
		private static final long serialVersionUID = 5152041303927051745L;

		/**
		 * Setzt das Schiff aus mehreren Elememten in einem JPanel zusammen.
		 * @param length Schiffslänge
		 */
		public AvailableShip(int length) {
			this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			try {
				if (length == 1) {
					this.add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/images/single_ship_small.png")))));
					
				} else {
					this.add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/images/ship_end_left_small.png")))));
					
					for (int i = 0; i < length-2; i++) {
						this.add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/images/ship_part_black_small.png")))));
					}
					
					this.add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/images/ship_end_right_small.png"))))); 
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {

					
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// Ändert Schiffsfarbe: Grau <-> Schwarz 
					for (Component shipPart : AvailableShip.this.getComponents()) {
						((JLabel) shipPart).setEnabled(!((JLabel) shipPart).isEnabled());
					};				
				}
			});
		}
	}
	
}













