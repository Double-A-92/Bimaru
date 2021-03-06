package ch.ntb.ini2.se.team2.bimaru;

import java.util.ArrayList;
import java.util.Observable;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

/**
 * Speichert alle Informationen �ber das Spielfeld.
 * Disee sind: Gr�sse, Position und L�nge der Schiffe, 
 * und den aktuellen Zustand der einzelnen Felder.
 * 
 * @author Amedeo Amato
 * @author Egeman Yesil
 */
@XmlRootElement
public class GameGridModel extends Observable{
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private ArrayList<Hint> hints = new ArrayList<Hint>();
	private int xSize;
	private int ySize;	
	
	private int[][] fieldStates;
	private int lastStateChanged;
	private long startTime = 0, endTime = 0;
	private boolean isGameRunning = true;
	private boolean isSolved = false;
	
	
	

	/**
	 * Konstruktor der ein Test-Spielfeld erstellt.
	 * @param xSize Horizontale Spielfeld-Gr�sse
	 * @param ySize Vertikale Spielfeld-Gr�sse
	 */
	public GameGridModel(int xSize, int ySize){
		this.xSize = xSize;
		this.ySize = ySize;
		fieldStates = new int[xSize][ySize];
		
		//Einige Test-Schiffe hinzuf�gen
		ArrayList<Ship> ships = new ArrayList<Ship>();
		ships.add(new Ship(2, 2, 4, false));
		ships.add(new Ship(5, 3, 2, true));
		ships.add(new Ship(6, 1, 3, true));
		this.setShips(ships);
		
		//Einige Test-Hinweise hinzuf�gen
		ArrayList<Hint> hints = new ArrayList<Hint>();
		hints.add(new Hint(2, 3));
		hints.add(new Hint(5, 6));
		hints.add(new Hint(2, 4));
		this.setHints(hints);
		
		fixHintFieldState();
	}
	
	/**
	 * Konstruktor ohne Parameter f�r JAXB
	 */
	public GameGridModel() {}
	
	void afterUnmarshal(Unmarshaller u, Object parent) {
		fieldStates = new int[xSize][ySize];
		fixHintFieldState();
	}
	
	/**
	 * Wechselt den Zustand eines Feldes in den n�chsten Zustand.
	 * @param x x-Koorditate (0 ist links)
	 * @param y y-Koorditate (0 ist oben)
	 */
	public void toggleFieldState(int x, int y) {
		if (!isHint(x, y) && isGameRunning){
			fieldStates[x][y] = (fieldStates[x][y] + 1) % 3; // 0:Leer, 1:Wasser, 2:Schiffsteil
			lastStateChanged = fieldStates[x][y];

			if (startTime == 0) {
				startTime = System.nanoTime();
			}
			
			isSolved = checkIfGameIsSolved();
			if (isSolved) {
				endTime = System.nanoTime();
				isGameRunning = false;
			}
			
			setChanged();
			notifyObservers(new int[] {x, y});
		}
	}

	/**
	 * Setzt den Zustand eines Feldes.
	 * @param x x-Koorditate (0 ist links)
	 * @param y y-Koorditate (0 ist oben)
	 * @param state Zustand des Feldes 
	 */
	public void setFieldState(int x, int y, int state) {
		if (!isHint(x, y)){
			fieldStates[x][y] = state;
			lastStateChanged = fieldStates[x][y];
			
			setChanged();
			notifyObservers(new int[] {x, y});
		}
	}
	
	/**
	 * Hiermit kann iGameRunning z.B. auf false gesetzt werden wenn das Spiel beendet wird.
	 * @param isGameRunning
	 */
	public void setGameRunning(boolean isGameRunning) {
		this.isGameRunning = isGameRunning;
		if (!isGameRunning) startTime = 0;
	}
	
	public boolean isGameRunning() {
		return isGameRunning;
	}

	/**
	 * Gibt den aktuellen Zustand eines Feldes zur�ck.
	 * @param x x-Koorditate (0 ist links)
	 * @param y y-Koorditate (0 ist oben)
	 * @return Zustand des Feldes (0=Leer, 1=Wasser, 2=Schiffsteil)
	 */
	public int getFieldState(int x, int y) {
		if (x < 0 || x > xSize-1 || y < 0 || y > ySize-1) return 1; //Wasser wenn Koordinaten ausserhalb des Spielfelds.
		return fieldStates[x][y];
	}
	
	/**
	 * Gibt zur�ck, in welchen Zustand das letzte Feld gewechselt ist.
	 * @return Letzte Zustands�nderung (0=Leer, 1=Wasser, 2=Schiffsteil)
	 */
	public int getLastStateChanged() {
		return lastStateChanged;
	}
		
	/**
	 * Gibt die horizontale Spielfeld-Gr�sse zur�ck.
	 * @return horizontale Spielfeld-Gr�sse
	 */
	public int getXSize() {
		return xSize;
	}

	/**
	 * Setzt die horizontale Spielfeld-Gr�sse.
	 * @param xSize horizontale Spielfeld-Gr�sse
	 */
	public void setXSize(int xSize) {
		this.xSize = xSize;
	}

	/**
	 * Gibt die vertikale Spielfeld-Gr�sse zur�ck.
	 * @return vertikale Spielfeld-Gr�sse
	 */
	public int getYSize() {
		return ySize;
	}

	/**
	 * Setzt die vertikale Spielfeld-Gr�sse.
	 * @param ySize vertikale Spielfeld-Gr�sse
	 */
	public void setYSize(int ySize) {
		this.ySize = ySize;
	}

	/**
	 * Gibt den tats�chlichen Zustand eines Feldes zur�ck.
	 * @param x x-Koorditate (0 ist links)
	 * @param y y-Koorditate (0 ist oben)
	 * @return Zustand des Feldes (1=Wasser, 2=Schiffsteil)
	 */
	public int getRealFieldState(int x, int y) {
		for (Ship ship : ships) {
			if (ship.isHorizontal()) {
				if (y == ship.getY() && x >= ship.getX() && x <= ship.getX() + ship.getSize() -1)
					return 2;
			} else {
				if (x == ship.getX() && y >= ship.getY() && y <= ship.getY() + ship.getSize() -1)
					return 2;
			}
		}
		return 1;		
	}
	
	/**
	 * Gibt zur�ck ob es sich bei dem Feld um ein Hinweis-Feld handelt.
	 * @param x x-Koorditate (0 ist links)
	 * @param y y-Koorditate (0 ist oben)
	 * @return true, wenn das Feld ein Hinweis ist.
	 */
	public boolean isHint(int x, int y) {
		if (x < 0 || x > xSize-1 || y < 0 || y > ySize-1) return false; //Kein Hinweis wenn Koordinaten ausserhalb des Spielfelds.
		return hints.contains(new Hint(x, y));
	}
	
	/**
	 * Setzt die Schiffe.
	 * @param ships Schiffe
	 */
	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}
	
	/**
	 * Gibt die Schiffe zur�ck.
	 * @return Schiffe
	 */
	@XmlElement(name = "ship")
	public ArrayList<Ship> getShips() {
		return this.ships;
	}
	
	/**
	 * Setzt die Hinweise.
	 * @param hints Hinweise
	 */
	public void setHints(ArrayList<Hint> hints) {
		this.hints = hints;
	}
	
	/**
	 * Gibt die Hinweise zur�ck.
	 * @return Hinweise
	 */
	@XmlElement(name = "hint")
	public ArrayList<Hint> getHints() {
		return this.hints;
	}
	
	private void fixHintFieldState() {
		for (Hint hint : hints) {
			fieldStates[hint.getX()][hint.getY()] = getRealFieldState(hint.getX(), hint.getY());
		}
	}
	
	/**
	 * Gibt die Anzahl gesetzter Schiffsteile in einer Zeile bzw. Spalte zur�ck.
	 * @param forRow true = Zeile, false = Spalte
	 * @param position Koordinate der Zeile bzw. Spalte
	 * @return
	 */
	public int countShipParts(boolean forRow, int position) {
		return countParts(forRow, position, 2);
	}
	
	/**
	 * Gibt die Anzahl gesetzter Wasserfelder in einer Zeile bzw. Spalte zur�ck.
	 * @param forRow true = Zeile, false = Spalte
	 * @param position Koordinate der Zeile bzw. Spalte
	 * @return
	 */
	public int countWaterFields(boolean forRow, int position) {
		return countParts(forRow, position, 1);
	}
	
	private int countParts(boolean forRow, int position, int part) {
		int numOfParts = 0;
		
		if (forRow) {
			for (int x = 0; x < xSize; x++) {
				if (fieldStates[x][position] == part)
				numOfParts++;
			}
		} else {
			for (int y = 0; y < ySize; y++) {
				if (fieldStates[position][y] == part)
					numOfParts++;
			}
		}
		return numOfParts;
	}
	
	/**
	 * Gibt die Anzahl tats�chlicher Schiffsteile in einer Zeile bzw. Spalte zur�ck.
	 * @param forRow true = Zeile, false = Spalte
	 * @param position Koordinate der Zeile bzw. Spalte
	 * @return
	 */
	public int countRealShipParts(boolean forRow, int position) {
		int numOfShipParts = 0;
		
		if (forRow) {
			for (int x = 0; x < xSize; x++) {
				if (getRealFieldState(x, position) == 2)
				numOfShipParts++;
			}
		} else {
			for (int y = 0; y < ySize; y++) {
				if (getRealFieldState(position, y) == 2)
					numOfShipParts++;
			}
		}
		return numOfShipParts;
	}	
	
	/**
	 * Gibt an welches Schiffsteil sich an diesen Koordinaten befindet.
	 * @param x x-Koorditate (0 ist links)
	 * @param y y-Koorditate (0 ist oben)
	 * @return Typ des Schiffsteil. -1: Kein, 0: Einzelschiff, 1: Oben, 2: Rechts, 3: Unten, 4: Links, 5: Mitte
	 */
	public int getShipPartType(int x, int y) {
		if (x < 0 || x > xSize-1 || y < 0 || y > ySize-1) return -1; //Kein Typ wenn Koordinaten ausserhalb des Spielfelds.
		
		int shipType = -1;
		if (getFieldState(x, y) == 2) {
			//-Angrenzende Wasserfelder z�hlen
			int waterCounter = 0;
			if (getFieldState(x, y-1) == 1) waterCounter++;
			if (getFieldState(x, y+1) == 1) waterCounter++;
			if (getFieldState(x-1, y) == 1) waterCounter++;
			if (getFieldState(x+1, y) == 1) waterCounter++;
			
			//-Angrenzende Schiffsteile z�hlen
			int shipPartCounter = 0;
			if (getFieldState(x, y-1) == 2) { shipPartCounter++; shipType = 3;}
			if (getFieldState(x, y+1) == 2) { shipPartCounter++; shipType = 1;}
			if (getFieldState(x-1, y) == 2) { shipPartCounter++; shipType = 2;}
			if (getFieldState(x+1, y) == 2) { shipPartCounter++; shipType = 4;}
			
			//Auswertung
			if (waterCounter == 4) { // Einzelschiff
				shipType = 0;
			} else if (waterCounter == 3 && shipPartCounter == 1) { //Schiffsende
				//shipType = shipType;
			} else if (waterCounter == 2 && shipPartCounter == 2){ //Schiffsmitte
				shipType = 5;
			} else { //Undefiniert
				shipType = -1;
			}
		}

		return shipType;
	}
	
	/**
	 * Gibt an welches Schiffsteil sich tats�chlich an diesen Koordinaten befindet.
	 * @param x x-Koorditate (0 ist links)
	 * @param y y-Koorditate (0 ist oben)
	 * @return Typ des Schiffsteil. -1: Kein, 0: Einzelschiff, 1: Oben, 2: Rechts, 3: Unten, 4: Links, 5: Mitte
	 */
	public int getRealShipPartType(int x, int y) {
		if (x < 0 || x > xSize-1 || y < 0 || y > ySize-1) return -1; //Kein Typ wenn Koordinaten ausserhalb des Spielfelds.
		
		int shipType = -1;
		if (getRealFieldState(x, y) == 2) {
			//-Angrenzende Wasserfelder z�hlen
			int waterCounter = 0;
			if (getRealFieldState(x, y-1) == 1) waterCounter++;
			if (getRealFieldState(x, y+1) == 1) waterCounter++;
			if (getRealFieldState(x-1, y) == 1) waterCounter++;
			if (getRealFieldState(x+1, y) == 1) waterCounter++;
			
			//-Angrenzende Schiffsteile z�hlen
			int shipPartCounter = 0;
			if (getRealFieldState(x, y-1) == 2) { shipPartCounter++; shipType = 3;}
			if (getRealFieldState(x, y+1) == 2) { shipPartCounter++; shipType = 1;}
			if (getRealFieldState(x-1, y) == 2) { shipPartCounter++; shipType = 2;}
			if (getRealFieldState(x+1, y) == 2) { shipPartCounter++; shipType = 4;}
			
			//Auswertung
			if (waterCounter == 4) { // Einzelschiff
				shipType = 0;
			} else if (waterCounter == 3 && shipPartCounter == 1) { //Schiffsende
				//shipType = shipType;
			} else if (waterCounter == 2 && shipPartCounter == 2){ //Schiffsmitte
				shipType = 5;
			} else { //Undefiniert
				shipType = -1;
			}
		}

		return shipType;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public long getEndTime() {
		return endTime;
	}

	public boolean isSolved() {
		return isSolved;
	}

	public void setSolved(boolean isSolved) {
		this.isSolved = isSolved;
	}
	
	/**
	 * �berpr�ft ob das Spiel korrekt gel�st wurde
	 */
	private boolean checkIfGameIsSolved() {
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				int fieldState = this.getFieldState(i, j);
				int realFieldState = this.getRealFieldState(i, j);
				if ((fieldState == 2 && realFieldState !=2) || (realFieldState == 2 && fieldState != 2)){
					return false;
				}
			}
		}
		return true;
	}
}

/**
 * Stellt ein Schiff dar, mit Position, L�nge und Ausrichtung.
 */
@XmlRootElement(name = "ships")
class Ship {
	private int size;
	private int x, y;
	private boolean isHorizontal;
	
	/**
	 * Konstruktor ohne Parameter f�r JAXB
	 */
	public Ship() {}
	
	/**
	 * Erstellt ein neues Schiff.
	 * @param x x-Koorditate (0 ist links)
	 * @param y y-Koorditate (0 ist oben)
	 * @param size Schiffsl�nge
	 * @param isHorizontal Ausrichtung, true = horizontal
	 */
	public Ship(int x, int y, int size, boolean isHorizontal) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.isHorizontal = isHorizontal;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isHorizontal() {
		return isHorizontal;
	}

	public void setHorizontal(boolean isHorizontal) {
		this.isHorizontal = isHorizontal;
	}
}

/**
 * Stellt einen Hinweis dar, also ein Feld das konstant die L�sung anzeigt.
 */
@XmlRootElement(name = "hints")
class Hint {
	private int x, y;
	
	/**
	 * Konstruktor ohne Parameter f�r JAXB
	 */
	public Hint() {}
	
	/**
	 * Erstellt einen neuen Hinweis.
	 * @param x x-Koorditate (0 ist links)
	 * @param y y-Koorditate (0 ist oben)
	 */
	public Hint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean equals(Object hint) {
		if (this.x == ((Hint) hint).x && this.y == ((Hint) hint).y) 
			return true;
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}

