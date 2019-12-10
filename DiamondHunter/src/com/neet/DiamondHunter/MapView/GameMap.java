package com.neet.DiamondHunter.MapView;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * Main class of MapViewer. It loads the root layout by using the FXML file.
 * It loads the two files used to build the map. Testmap.map is input in to a multidimensional array. 
 * It then uses the values from the array to draw the map based on the corresponding
 * tile in testtileset.gif. 
 * */   

public class GameMap extends Application {
    public static Stage primaryStage;
    public static TileMap tileMap;
    
    public BorderPane Layout1;
    public TilePane Layout2;
    
    /*Building Window*/
	@Override
	public void start(Stage primaryStage) {
		GameMap.primaryStage = primaryStage;
        GameMap.primaryStage.setTitle("Game Map");
        
        Platform.setImplicitExit(false);

        initializeLayout();
        showMap();
		primaryStage.setOnCloseRequest(event -> { Platform.setImplicitExit(true); });
	}
	
    public void initializeLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GameMap.class.getResource("/com/neet/DiamondHunter/MapView/Layout1.fxml"));
            Layout1 = (BorderPane) loader.load();

            Scene scene = new Scene(Layout1);
            
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showMap() {
    	tileMap = new TileMap();
    	tileMap.loadMap("/Maps/testmap.map");
    	tileMap.loadTile("/Tilesets/testtileset.gif");
	    
		Layout2 = new TilePane();
		
		Layout2.setPrefColumns(tileMap.cols);
		Layout2.setPrefRows(tileMap.rows);
		tileMap.canvas();
		Layout2.getChildren().add(tileMap.mainCanvas);

		Layout1.setCenter(Layout2);
    }

     
	public static void main(String[] args) {
		launch(args);
	}
}
