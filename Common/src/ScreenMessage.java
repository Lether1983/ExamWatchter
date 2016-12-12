import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Created by Vincent on 07.12.2016.
 */
public class ScreenMessage implements Message
{
    private ImageIcon image;

    public ScreenMessage()
    {
    }

    public void setImage(ImageIcon image)
    {
        this.image = image;
    }

    public ImageIcon getImage()
    {
        return image;
    }
}
