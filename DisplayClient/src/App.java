/**
 * Created by Vincent on 06.12.2016.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventListener;

public class App extends Application
{
    public final static CancellationToken cancellationToken = new CancellationToken();

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Platform.setImplicitExit(true);

        Parent root = FXMLLoader.load(getClass().getResource("ClientSample.fxml"));
        primaryStage.setTitle("Exam Watcher Client");
        primaryStage.setScene(new Scene(root));
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
