import java.net.Socket;

/**
 * Created by Vincent on 07.12.2016.
 */
public interface ClientConnectHandler
{
    void ClientConnected(Socket socket);
}
