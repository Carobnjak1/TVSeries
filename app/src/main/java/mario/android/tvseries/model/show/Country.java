package mario.android.tvseries.model.show;

public class Country
{
    private String timezone;

    private String name;

    private String code;

    public String getTimezone ()
    {
        return timezone;
    }

    public void setTimezone (String timezone)
    {
        this.timezone = timezone;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [timezone = "+timezone+", name = "+name+", code = "+code+"]";
    }
}
