import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.io.IOException;

/**
 * Created by Vincent on 07.12.2016.
 */
public class ScreenCaptureThread extends Thread
{
    private final Client client;
    private final CancellationToken cancellationToken;

    public ScreenCaptureThread(Client client, CancellationToken cancellationToken)
    {
        this.client = client;
        this.cancellationToken = cancellationToken;
    }

    @Override
    public boolean isInterrupted()
    {
        return cancellationToken.isCancelled() || super.isInterrupted();
    }

    @Override
    public void run()
    {
        Robot r = null;
        try
        {
            r = new Robot();
        }
        catch (AWTException e)
        {
            e.printStackTrace();
        }
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rect = new Rectangle(0, 0, size.width, size.height);

        while (client.isAlive() && !isInterrupted())
        {
            BufferedImage screenCapture = r.createScreenCapture(rect);
            BufferedImage resized = new BufferedImage(screenCapture.getWidth() / 4, screenCapture.getHeight() / 4, screenCapture.getType());
            Graphics graphics = resized.getGraphics();
            graphics.drawImage(screenCapture, 0, 0, screenCapture.getWidth() / 4, screenCapture.getHeight() / 4, Color.BLACK, null);
            graphics.dispose();

            try
            {
                ScreenMessage message = new ScreenMessage();
                message.setImage(new ImageIcon(resized));
                client.WriteMessage(message);
                sleep(1000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
