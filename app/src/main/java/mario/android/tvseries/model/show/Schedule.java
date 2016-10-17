package mario.android.tvseries.model.show;

public class Schedule
{
    private String time;

    private String[] days;

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String[] getDays ()
    {
        return days;
    }

    public void setDays (String[] days)
    {
        this.days = days;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [time = "+time+", days = "+days+"]";
    }
}