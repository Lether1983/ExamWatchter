/**
 * Created by Vincent on 05.12.2016.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application
{
    public static final CancellationToken cancellationToken = new CancellationToken();

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
        primaryStage.setTitle("Exam Viewer");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.isResizable();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception
    {
        cancellationToken.cancel();
        super.stop();
    }
}
