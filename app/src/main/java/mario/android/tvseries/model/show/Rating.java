package mario.android.tvseries.model.show;

import java.io.Serializable;


public class Rating implements Serializable
{
    private String average;

    public String getAverage ()
    {
        return average;
    }

    public void setAverage (String average)
    {
        this.average = average;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [average = "+average+"]";
    }
}
