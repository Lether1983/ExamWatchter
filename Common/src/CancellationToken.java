/**
 * Created by Vincent on 07.12.2016.
 */
public class CancellationToken
{
    private boolean cancelled;

    public CancellationToken()
    {
        cancelled = false;
    }

    public void cancel()
    {
        cancelled = true;
    }

    public boolean isCancelled()
    {
        return cancelled;
    }
}
