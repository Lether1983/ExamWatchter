import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.ListView;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Vincent on 05.12.2016.
 */
public class ServerController implements Initializable
{
    private Server server;
    @FXML
    private ListView<String> UserListView;
    @FXML
    private TilePane UserTileView;
    @FXML
    private Circle OnlineStatus;

    public ServerController() throws IOException
    {
        ObservableList<String> items = FXCollections.observableArrayList();
        server = new Server();
        server.clientConnect = socket ->
        {

            Client client = new Client(socket, App.cancellationToken);
            Platform.runLater(() ->
            {
                try
                {
                    UserTileView.getChildren().add(FXMLLoader.load(getClass().getResource("ClientView.fxml"), null, new JavaFXBuilderFactory(), param ->
                    {
                        return new ClientViewController(client,items,UserListView);
                    }));
                    client.start();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            });
        };
    }

    public void StartServer(ActionEvent actionEvent)
    {
        server.start();
        OnlineStatus.setFill(Paint.valueOf("#358632"));
    }

    public void ShowAll(ActionEvent actionEvent)
    {
    }

    public void DisplaySingleUser(ActionEvent actionEvent)
    {
    }

    public void InvokeExamFinish(ActionEvent actionEvent)
    {

    }

    public void ClientConnected()
    {
        /*        Main.CurrentStage.setScene(new Scene(FXMLLoader.<Parent>load(getClass().getResource("sample.fxml"), null, new JavaFXBuilderFactory(), new Callback<Class<?>, Object>()
        {
            @Override
            public Object call(Class<?> controller)
            {
                return new ServerController(Username.getText(),IpAddress.getText());
            }
        })));
    }*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        UserListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            /* Wenn Single View, dann update image! */
        });
    }
}
