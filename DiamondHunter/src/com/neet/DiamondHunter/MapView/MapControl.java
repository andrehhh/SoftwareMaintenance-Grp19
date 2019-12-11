package com.neet.DiamondHunter.MapView;

import com.neet.DiamondHunter.Main.Game;
import com.neet.DiamondHunter.MapView.GameMap;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;



public class MapControl {
	
	@FXML
	private void handleKeyAction(KeyEvent event) {
	    if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
	    	GameMap.tileMapViewer.moveCursor("W");
	    }
	    else if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
	    	GameMap.tileMapViewer.moveCursor("S");
	    }
	    else if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
	    	GameMap.tileMapViewer.moveCursor("A");
	    }
	    else if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
	    	GameMap.tileMapViewer.moveCursor("D");
	    }
	    else if (event.getCode() == KeyCode.P) {
	    	startGame();
	    }
	    else if (event.getCode() == KeyCode.E) {
			GameMap.tileMapViewer.SetBoat();
		}
	    else if (event.getCode() == KeyCode.Q) {
			GameMap.tileMapViewer.SetAxe();
		}
	}
	
	@FXML
	private void startGame() {
		GameMap.primaryStage.hide();
    	Game.main(null);
	}
}