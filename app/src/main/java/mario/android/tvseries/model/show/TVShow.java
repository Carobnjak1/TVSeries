package mario.android.tvseries.model.show;


public class TVShow
{
    private String score;

    private Show show;

    public String getScore ()
    {
        return score;
    }

    public void setScore (String score)
    {
        this.score = score;
    }

    public Show getShow ()
    {
        return show;
    }

    public void setShow (Show show)
    {
        this.show = show;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [score = "+score+", show = "+show+"]";
    }
}