package ch.ntb.ini2.se.team2.bimaru;

import java.util.ArrayList;

public class GameGridModel {
	private ArrayList<Ship> ships = new ArrayList<Ship>();
	private ArrayList<Hint> hints = new ArrayList<Hint>();
	private int[][] fieldStates;
	private int xSize, ySize;
	private int lastStateChanged;
	
	public GameGridModel(int xSize, int ySize){
		this.xSize = xSize;
		this.ySize = xSize;
		fieldStates = new int[xSize][ySize];
	}
	
	public void toggleFieldState(int x, int y) {
		if (!hints.contains(new Hint(x, y))){
			fieldStates[x][y] = (fieldStates[x][y] + 1) % 3; // 0: Leer, 1:Wasser, 2:Schiffsteil
			lastStateChanged = fieldStates[x][y];
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
	
	
	@SuppressWarnings("unused")
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
	
	@SuppressWarnings("unused")
	private class Hint {
		int x, y;
		
		public Hint(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
}


