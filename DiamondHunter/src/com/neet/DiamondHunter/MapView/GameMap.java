package com.neet.DiamondHunter.MapView;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * This class is the main class for the application.
 * It produces the window as well as draws the map and the 
 * cursor used to place Boat and Axe.
 * @author daffaster85
 *
 */


public class GameMap extends Application {
    public static Stage primaryStage;
    public static TileMap tileMapViewer;
    
    //Variable used to check if GameMap application was opened or not
    //to decide in editing where Axe and Boat are set in main game
    public static boolean MapOpen = false;
    
    public BorderPane Layout1;
    public TilePane Layout2; 
    
	@Override
	public void start(Stage primaryStage) {
		GameMap.primaryStage = primaryStage;
        GameMap.primaryStage.setTitle("MapViewer");

        initializeLayout();
        
		showMap();
		
		MapOpen = true;
	}
	
	/**
	 * Setting UI of scene using Layout1.fxml
	 */
    public void initializeLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GameMap.class.getResource("/com/neet/DiamondHunter/MapView/Layout1.fxml"));

            Layout1 = (BorderPane) loader.load();

            Scene scene = new Scene(Layout1);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Calls the methods that builds the map
     */
    public void showMap() {
    	tileMapViewer = new TileMap();
    	tileMapViewer.loadMap("/Maps/testmap.map");
    	tileMapViewer.loadImages("/Tilesets/testtileset.gif", "/Sprites/items.gif");
	    
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(GameMap.class.getResource("/com/neet/DiamondHunter/MapView/Layout2.fxml"));
		
			Layout2 = (TilePane) loader.load();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		Layout2.setPrefColumns(tileMapViewer.cols);
		Layout2.setPrefRows(tileMapViewer.rows);
		tileMapViewer.canvas();
		Layout2.getChildren().add(tileMapViewer.currCanvas);
		Layout1.setLeft(Layout2);
    }
    
    //Main
	public static void main(String[] args) {
		launch(args);
	}
}
