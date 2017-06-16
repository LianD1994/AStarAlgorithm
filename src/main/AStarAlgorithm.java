package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AStarAlgorithm extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/MainPage.fxml"));
        AnchorPane mainPageFXML = loader.load();
        Scene mainPage = new Scene(mainPageFXML, 1250, 900);
        
        primaryStage.setScene(mainPage);
        primaryStage.setTitle("A* Algorithm Assignment");
        primaryStage.setResizable(false);
        primaryStage.show();

	}

	public static void main(String[] args) {
        launch(args);
    }
}
