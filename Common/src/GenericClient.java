import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;

/**
 * Created by Vincent on 07.12.2016.
 */
public abstract class GenericClient extends Thread
{
    private final CancellationToken cancellationToken;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private GZIPInputStream compressedInputStream;
    private GZIPOutputStream compressedOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private LocalDateTime nextPing;
    private LocalDateTime lastPong;
    private boolean pingSent;

    public GenericClient(Socket socket, CancellationToken cancellationToken)
    {
        nextPing = LocalDateTime.now();
        lastPong = LocalDateTime.now();
        this.socket = socket;
        try
        {
            outputStream = socket.getOutputStream();
            compressedOutputStream = new GZIPOutputStream(outputStream);
            objectOutputStream = new ObjectOutputStream(outputStream);

            inputStream = socket.getInputStream();
            compressedInputStream = new GZIPInputStream(inputStream);
            objectInputStream = new ObjectInputStream(inputStream);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        this.cancellationToken = cancellationToken;
    }

    public final void WriteMessage(Message message) throws IOException
    {
        new Thread(() ->
        {
            try
            {
                objectOutputStream.writeObject(message);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }).start();
    }

    protected abstract void handleMessage(Message message, Class messageClass);

    protected abstract void disconnect();

    @Override
    public boolean isInterrupted()
    {
        return cancellationToken.isCancelled() || super.isInterrupted();
    }

    @Override
    public final void run()
    {
        while (!socket.isClosed() && !isInterrupted())
        {
            try
            {
                if (inputStream.available() == 0)
                {
                    if (lastPong.plusSeconds(30).isBefore(LocalDateTime.now()))
                    {
                        break;
                    }
                    if (LocalDateTime.now().isAfter(nextPing) && !pingSent)
                    {
                        pingSent = true;
                        nextPing = LocalDateTime.now().plusSeconds(10);
                        objectOutputStream.writeObject(new PingMessage());
                    }
                    continue;
                }
                Object input = objectInputStream.readObject();
                if (input == null)
                {
                    continue;
                }
                Class inputClass = input.getClass();

                if (inputClass == PingMessage.class)
                {
                    objectOutputStream.writeObject(new PongMessage());
                }
                else if (inputClass == PongMessage.class)
                {
                    pingSent = false;
                    lastPong = LocalDateTime.now();
                }
                else
                {
                    handleMessage((Message) input, inputClass);
                }
            }
            catch (SocketException e)
            {
                interrupt();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            disconnect();
        }
    }
}
