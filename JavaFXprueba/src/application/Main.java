package application;


import application.modelo.Conexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

//--module-path "src\javafx-sdk-18.0.1\lib" --add-modules javafx.controls,javafx.fxml,javafx.media
public class Main extends Application {
	static
	Object method;
	@Override
	
	public void start(Stage primaryStage) {
		try {
			Conexion conex = new Conexion();
			conex.conectar();
			Parent root = FXMLLoader.load(getClass().getResource("vista/Main.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pokemon Little");
			primaryStage.getIcons().add(new Image(getClass().getResource("res/Picon.png").toExternalForm()));
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		launch(args);	
	}
}
