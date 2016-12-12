import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Vincent on 07.12.2016.
 */
public class ClientViewController
{
    @FXML
    private TitledPane title;
    @FXML
    private Canvas screen;
    private final Client client;

    public ClientViewController(final Client client)
    {
        this.client = client;
        this.client.updateUIHandler = () ->
        {
            title.setText(client.name);
        };
        this.client.screenMessageHandler = message ->
        {
            BufferedImage bi = new BufferedImage(
                    message.getImage().getIconWidth(),
                    message.getImage().getIconHeight(),
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.createGraphics();
            message.getImage().paintIcon(null, g, 0, 0);
            g.dispose();
            screen.getGraphicsContext2D().drawImage(SwingFXUtils.toFXImage(bi, null), 0, 0, screen.getWidth(), screen.getHeight());
        };
        client.connectionClosedHandler = () -> Platform.runLater(() -> ((Pane) title.getParent()).getChildren().remove(title));
    }
}
