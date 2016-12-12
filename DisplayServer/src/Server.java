import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Vincent on 07.12.2016.
 */
public class Server extends Thread
{
    public ClientConnectHandler clientConnect;

    private ServerSocket socket;

    public Server() throws IOException
    {
        socket = new ServerSocket(24404);
    }

    @Override
    public void run()
    {
        while (socket.isBound() && !App.cancellationToken.isCancelled())
        {
            try
            {
                socket.setSoTimeout(1000);
                Socket clientSocket = socket.accept();
                clientConnect.ClientConnected(clientSocket);
            }
            catch (SocketTimeoutException e)
            {
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
