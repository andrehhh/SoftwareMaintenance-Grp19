package com.neet.DiamondHunter.MapView;

import com.neet.DiamondHunter.Main.Game;
import com.neet.DiamondHunter.MapView.GameMap;

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
	    else if (event.getCode() == KeyCode.U) {
	    	GameMap.primaryStage.hide();
	    	Game.main(null);
	    }
	    else if (event.getCode() == KeyCode.E) {
			GameMap.tileMapViewer.SetBoat();
		}
	    else if (event.getCode() == KeyCode.Q) {
			GameMap.tileMapViewer.SetAxe();
		}
	}
}