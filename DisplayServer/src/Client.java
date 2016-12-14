import java.io.*;
import java.net.*;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by Vincent on 06.12.2016.
 */
public class Client extends GenericClient
{
    public String name;
    public ScreenMessageHandler screenMessageHandler;
    public UpdateUIHandler updateUIHandler;
    public UpdateUIHandler updateUiHandler2;
    public ConnectionClosedHandler connectionClosedHandler;

    public Client(Socket socket, CancellationToken cancellationToken)
    {
        super(socket, cancellationToken);
    }

    @Override
    protected void handleMessage(Message message, Class messageClass)
    {
        if (messageClass == UserMessage.class)
        {
            name = ((UserMessage) message).Name;
            updateUIHandler.Handle();
            updateUiHandler2.Handle();
        }
        else if (messageClass == ScreenMessage.class)
        {
            ScreenMessage screenMessage = (ScreenMessage) message;
            screenMessageHandler.Handle(screenMessage);
        }
    }

    @Override
    protected void disconnect()
    {
        connectionClosedHandler.closed();
    }
}
