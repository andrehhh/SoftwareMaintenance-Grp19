package com.neet.DiamondHunter.MapView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

/** 
 * This class contains all methods that draw the graphics
 * as well as set the variables that affect the main game.
 * Methods from this class are used in GameMap.java as well
 * as MapControl.java
 * @author daffaster85
 *
 */
public class TileMap {

	private int tileSize = 16;
	public int cols, rows;
	
	//Variables that edit the position of Axe and Boat in the main game
	public int boatCol = 0, boatRow = 0;
	public int axeCol = 0, axeRow = 0;
	
	private int currentcols, currentrows;
	private int numTilesAcross;
	public int moveCol;
	public int moveRow;
	
	//Position of boat and axe in resource testtileset.gif
	public final int boatTile = 0;
	public final int axeTile = 1;
	
	private int[][] map;
	private int[][] tileLayout;
	
	//Variables used in building the map
	private Image tileset;
	private Image mapView;
	private Image originalmapView;
	public Image cursorImage;
	public Image items;
	
	private Canvas mainCanvas;
	public Canvas currCanvas;
	
	public Cursor cursor;
	
	//For checking if boat and axe has been set before
	public boolean boatSet=false;
	public boolean axeSet=false;

	public void loadMap(String mapFile) {
		try {
			InputStream in = getClass().getResourceAsStream(mapFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			cols = Integer.parseInt(br.readLine());
			rows = Integer.parseInt(br.readLine());
			currentcols = cols;
			currentrows = rows;

			map = new int[rows][cols];

			String delims = "\\s+";
			for(int row = 0; row < rows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < cols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the resources as Image object
	 * @param tilesetImage
	 * @param itemsImage
	 */
	public void loadImages(String tilesetImage, String itemsImage) {
		try {
			tileset = new Image(TileMap.class.getResourceAsStream(tilesetImage));
			items = new Image(TileMap.class.getResourceAsStream(itemsImage));
			numTilesAcross = (int)tileset.getWidth() / tileSize;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Building the main window and map
	 */
	public void canvas() {
		mainCanvas = new Canvas(640,640);
		currCanvas = new Canvas(640, 640);
		tileLayout = new int[rows][cols];
		cursor = new Cursor();

		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				if(map[row][col] == 0) continue;

				int rc = map[row][col];

				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;

				if (r == 0) {
					mainCanvas.getGraphicsContext2D().drawImage(tileset, c * tileSize, 0, tileSize, tileSize, col * tileSize, row * tileSize, tileSize, tileSize);
					currCanvas.getGraphicsContext2D().drawImage(tileset, c * tileSize, 0, tileSize, tileSize, col * tileSize, row * tileSize, tileSize, tileSize);
					tileLayout[row][col] = 0;
				}
				else {
					mainCanvas.getGraphicsContext2D().drawImage(tileset, c * tileSize, tileSize, tileSize, tileSize, col * tileSize, row * tileSize, tileSize, tileSize);
					currCanvas.getGraphicsContext2D().drawImage(tileset, c * tileSize, tileSize, tileSize, tileSize, col * tileSize, row * tileSize, tileSize, tileSize);
					tileLayout[row][col] = 1;
				}

			}
		}
		originalmapView = mainCanvas.snapshot(null, null);
		drawCursor();
		cursorImage = new Image(Cursor.class.getResourceAsStream("/Sprites/logo.png"));
		currCanvas.getGraphicsContext2D().drawImage(cursorImage, 0, 0, tileSize, tileSize, cursor.col * tileSize, cursor.row * tileSize, tileSize, tileSize);
		mapView = mainCanvas.snapshot(null, null);
	}
	
	private void updateCanvas() {
		currCanvas.getGraphicsContext2D().drawImage(mapView, moveCol * tileSize, moveRow * tileSize, currentcols * tileSize, currentrows * tileSize,
				0, 0, 640, 640);
	}
	
	/**
	 * Draws cursor for placing boat and Axe
	 */
	private void drawCursor() {
		cursorImage = new Image(Cursor.class.getResourceAsStream("/Sprites/logo.png"));
		mainCanvas.getGraphicsContext2D().drawImage(cursorImage, 0, 0, tileSize, tileSize, cursor.col * tileSize, cursor.row * tileSize, tileSize, tileSize);
	}
	
	/**
	 * Erases cursor from previous position
	 * @param col
	 * @param row
	 */
	private void deleteCursor(int col, int row) {
		if((boatRow == cursor.row && boatCol == cursor.col)) {
			mainCanvas.getGraphicsContext2D().drawImage(originalmapView, col * tileSize, row * tileSize, tileSize, tileSize, col * tileSize, row * tileSize, tileSize, tileSize);
			mainCanvas.getGraphicsContext2D().drawImage(items, boatTile  * tileSize, tileSize, tileSize, tileSize, boatCol * tileSize, boatRow * tileSize, tileSize, tileSize);
		}
		
		else if((axeRow == cursor.row && axeCol == cursor.col)) {
			mainCanvas.getGraphicsContext2D().drawImage(originalmapView, col * tileSize, row * tileSize, tileSize, tileSize, col * tileSize, row * tileSize, tileSize, tileSize);
			mainCanvas.getGraphicsContext2D().drawImage(items, axeTile  * tileSize, tileSize, tileSize, tileSize, axeCol * tileSize, axeRow * tileSize, tileSize, tileSize);
		}
		else {
			mainCanvas.getGraphicsContext2D().drawImage(originalmapView, col * tileSize, row * tileSize, tileSize, tileSize, col * tileSize, row * tileSize, tileSize, tileSize);
		}
	}

	/**
	 * Method to handle moving of cursor
	 * @param c
	 */
	public void moveCursor(String c) {
		if (c == "W") {
			if (cursor.row > 0) {
				deleteCursor(cursor.col, cursor.row);
				cursor.row --;
			}
		}
		else if (c == "A") {
			if (cursor.col > 0) {
				deleteCursor(cursor.col, cursor.row);
				cursor.col --;
			}
		}
		else if (c == "S") {
			if (cursor.row < rows - 1 ) {
				deleteCursor(cursor.col, cursor.row);
				cursor.row ++;
			}
		}
		else if (c == "D") {
			if (cursor.col < cols - 1 ) {
				deleteCursor(cursor.col, cursor.row);
				cursor.col ++;
			}
		}
		drawCursor();
		mapView = mainCanvas.snapshot(null, null);
		updateCanvas();
	}
	
	/**
	 * Method that draws respective items
	 */
	public void drawItems() {
		if(boatSet) {
			mainCanvas.getGraphicsContext2D().drawImage(items, boatTile  * tileSize, tileSize, tileSize, tileSize, boatCol * tileSize, boatRow * tileSize, tileSize, tileSize);
		}
		if(axeSet) {
			mainCanvas.getGraphicsContext2D().drawImage(items, axeTile  * tileSize, tileSize, tileSize, tileSize, axeCol * tileSize, axeRow * tileSize, tileSize, tileSize);
		}
	}
	
	/**
	 * Method for checking if tile is valid for setting of Boat
	 */
	public void SetBoat(){
		
		deleteCursor(cursor.col, cursor.row);
		if (tileLayout[cursor.row][cursor.col] == 1) {
			
		}
		else {
			if(boatSet) {
				deleteCursor(boatCol, boatRow);
				
				tileLayout[boatRow][boatCol] = 0;
				tileLayout[cursor.row][cursor.col] = 1;
			}
			boatSet = true;
			tileLayout[cursor.row][cursor.col] = 1;
			
			boatRow = cursor.row;
			boatCol = cursor.col;
		}
		drawItems();
		drawCursor();
		mapView = mainCanvas.snapshot(null,null);
		updateCanvas();
	}
	
	/**
	 * Method for checking if tile is valid for setting of Axe
	 */
public void SetAxe(){
		
		deleteCursor(cursor.col, cursor.row);
		if (tileLayout[cursor.row][cursor.col] == 1) {
		}
		else {
			if(axeSet) {
				deleteCursor(axeCol, axeRow);
				
				tileLayout[axeRow][axeCol] = 0;
				tileLayout[cursor.row][cursor.col] = 1;
			}
			axeSet = true;
			tileLayout[cursor.row][cursor.col] = 1;
			
			axeRow = cursor.row;
			axeCol = cursor.col;
		}
		drawItems();
		drawCursor();
		mapView = mainCanvas.snapshot(null,null);
		updateCanvas();
	}
}
