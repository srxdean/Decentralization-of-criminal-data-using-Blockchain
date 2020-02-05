package wit.crime;

public interface SmartContract
{
    public void create(Context context);

    public void update(Context context, String command, Object... params);

    public Object query(Context context, String command, Object... params);
}
