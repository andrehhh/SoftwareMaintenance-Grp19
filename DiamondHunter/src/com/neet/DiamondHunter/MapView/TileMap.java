package com.neet.DiamondHunter.MapView;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class TileMap {
	
	private int tileSize = 16;
	private int tiles;
	public int cols, rows;

	private int[][] map;
	private int[][] tileLayout;

	public Canvas mainCanvas;
	
	private Image tileset;

	/**
	 * Method used to load map. Creates variable map to store map to be drawn.
	 * @param mapFile
	 */
	public void loadMap(String mapFile) {
		try {
			InputStream in = getClass().getResourceAsStream(mapFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			cols = Integer.parseInt(br.readLine());
			rows = Integer.parseInt(br.readLine());

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

	public void loadTile(String tilesetImage) {
		try {
			tileset = new Image(TileMap.class.getResourceAsStream(tilesetImage));
			tiles = (int)tileset.getWidth() / tileSize;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*Draws image of map*/
	public void canvas() {
		mainCanvas = new Canvas(640,640);
		tileLayout = new int[rows][cols];

		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				if(map[row][col] == 0) continue;

				int rowcol = map[row][col];
				int r = rowcol / tiles;
				int c = rowcol % tiles;

				if (r == 0) {
					mainCanvas.getGraphicsContext2D().drawImage(tileset, c * tileSize, 0, tileSize, tileSize, col * tileSize, row * tileSize, tileSize, tileSize);
					tileLayout[row][col] = 0;
				}
				else {
					mainCanvas.getGraphicsContext2D().drawImage(tileset, c * tileSize, tileSize, tileSize, tileSize, col * tileSize, row * tileSize, tileSize, tileSize);
					tileLayout[row][col] = 1;
				}
			}
		}
	}
}
