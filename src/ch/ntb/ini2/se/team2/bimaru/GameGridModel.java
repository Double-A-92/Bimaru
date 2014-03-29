package ch.ntb.ini2.se.team2.bimaru;

import java.util.ArrayList;
import java.util.Observable;

public class GameGridModel extends Observable{
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private ArrayList<Hint> hints = new ArrayList<Hint>();
	private int[][] fieldStates;
	private int xSize, ySize;
	private int lastStateChanged;
	
	public GameGridModel(int xSize, int ySize){
		this.xSize = xSize;
		this.ySize = ySize;
		fieldStates = new int[xSize][ySize];
		
		//Debug
		ArrayList<Ship> ships = new ArrayList<Ship>();
		ships.add(new Ship(2, 2, 4, false));
		ships.add(new Ship(5, 3, 2, true));
		ships.add(new Ship(6, 1, 3, true));
		this.setShips(ships);
		
		//Debug
		ArrayList<Hint> hints = new ArrayList<Hint>();
		hints.add(new Hint(2, 3));
		hints.add(new Hint(5, 6));
		hints.add(new Hint(2, 4));
		this.setHints(hints);
	}
	
	public void toggleFieldState(int x, int y) {
		if (!isHint(x, y)){
			fieldStates[x][y] = (fieldStates[x][y] + 1) % 3; // 0: Leer, 1:Wasser, 2:Schiffsteil
			lastStateChanged = fieldStates[x][y];
			
			setChanged();
			notifyObservers(new int[] {x, y});
		}
	}
	
	public int getFieldState(int x, int y) {
		return fieldStates[x][y];
	}
	
	public int getLastStateChanged() {
		return lastStateChanged;
	}
	
	public int getXSize() {
		return xSize;
	}
	
	public int getYSize() {
		return ySize;
	}
	
	public int getRealFieldState(int x, int y) {
		for (Ship ship : ships) {
			if (ship.isHorizontal) {
				if (y == ship.y && x >= ship.x && x <= ship.x + ship.size -1)
					return 2;
			} else {
				if (x == ship.x && y >= ship.y && y <= ship.y + ship.size -1)
					return 2;
			}
		}
		return 1;		
	}
	
	public boolean isHint(int x, int y) {
		return hints.contains(new Hint(x, y));
	}
	
	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}
	
	public void setHints(ArrayList<Hint> hints) {
		this.hints = hints;
		
		for (Hint hint : hints) {
			fieldStates[hint.x][hint.y] = getRealFieldState(hint.x, hint.y);
		}
	}
	
	public int countShipParts(boolean forRow, int position) {
		return countParts(forRow, position, 2);
	}
	
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
		
	private class Ship {
		int size;
		int x, y;
		boolean isHorizontal;
		
		public Ship(int x, int y, int size, boolean isHorizontal) {
			this.x = x;
			this.y = y;
			this.size = size;
			this.isHorizontal = isHorizontal;
		}
	}
	
	private class Hint {
		int x, y;
		
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
	}

	
	
}


