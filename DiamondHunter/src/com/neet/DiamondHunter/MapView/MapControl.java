package com.neet.DiamondHunter.MapView;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MapControl {

	@FXML
	private void handleKeyAction(KeyEvent event) {
	    if (event.getCode() == KeyCode.W) {
	    	GameMap.tileMapViewer.moveCursor("W");
	    }
	    else if (event.getCode() == KeyCode.S) {
	    	GameMap.tileMapViewer.moveCursor("S");
	    }
	    else if (event.getCode() == KeyCode.A) {
	    	GameMap.tileMapViewer.moveCursor("A");
	    }
	    else if (event.getCode() == KeyCode.D) {
	    	GameMap.tileMapViewer.moveCursor("D");
	
	    }
	}
}
