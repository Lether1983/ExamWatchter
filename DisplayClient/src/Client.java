import java.io.IOException;
import java.net.Socket;

/**
 * Created by Vincent on 07.12.2016.
 */
public class Client extends GenericClient
{
    public Client(Socket socket, CancellationToken cancellationToken,String name) throws IOException
    {
        super(socket, cancellationToken);
        WriteMessage(new UserMessage(name));
    }

    @Override
    protected void handleMessage(Message message, Class messageClass)
    {

    }

    @Override
    protected void disconnect()
    {
    }
}
