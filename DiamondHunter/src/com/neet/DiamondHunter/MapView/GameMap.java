package com.neet.DiamondHunter.MapView;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class GameMap extends Application {
    public static Stage primaryStage;
    public static TileMap tileMapViewer;
    
    public BorderPane Layout1;
    public TilePane Layout2;
    
	@Override
	public void start(Stage primaryStage) {
		GameMap.primaryStage = primaryStage;
        GameMap.primaryStage.setTitle("MapViewer");

        initializeLayout();
        
		showMap();
	}
	
    public void initializeLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GameMap.class.getResource("/com/neet/DiamondHunter/MapView/Layout2.fxml"));
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
    	tileMapViewer = new TileMap();
    	tileMapViewer.loadMap("/Maps/testmap.map");
    	tileMapViewer.loadImages("/Tilesets/testtileset.gif", "/Sprites/items.gif");
	    
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GameMap.class.getResource("/com/neet/DiamondHunter/MapView/Layout1.fxml"));
		
			Layout2 = (TilePane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Layout2.setPrefColumns(tileMapViewer.cols);
		Layout2.setPrefRows(tileMapViewer.rows);
		tileMapViewer.canvas();
		Layout2.getChildren().add(tileMapViewer.currCanvas);
		Layout1.setCenter(Layout2);
    }

	public static void main(String[] args) {
		launch(args);
	}
}
