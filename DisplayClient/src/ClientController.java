import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Vincent on 06.12.2016.
 */
public class ClientController
{
    @FXML
    private Button ConnectButton;
    @FXML
    private TextField serverAddress;
    @FXML
    private TextField Username;
    private Client client;
    private ScreenCaptureThread screenCaptureThread;

    public ClientController()
    {
    }

    public void ConnectToServer()
    {
        try
        {
            Socket socket = new Socket(serverAddress.getText(), 24404);
            client = new Client(socket, App.cancellationToken,Username.getText());
            screenCaptureThread = new ScreenCaptureThread(client, App.cancellationToken);
            client.start();
            screenCaptureThread.start();
            //ConnectButton.setDisable(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void CloseApplication(ActionEvent actionEvent)
    {
        Platform.exit();
    }
}
