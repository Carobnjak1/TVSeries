package mario.android.tvseries.model.show;

public class Externals
{
    private String thetvdb;

    private String imdb;

    private String tvrage;

    public String getThetvdb ()
    {
        return thetvdb;
    }

    public void setThetvdb (String thetvdb)
    {
        this.thetvdb = thetvdb;
    }

    public String getImdb ()
    {
        return imdb;
    }

    public void setImdb (String imdb)
    {
        this.imdb = imdb;
    }

    public String getTvrage ()
    {
        return tvrage;
    }

    public void setTvrage (String tvrage)
    {
        this.tvrage = tvrage;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [thetvdb = "+thetvdb+", imdb = "+imdb+", tvrage = "+tvrage+"]";
    }
}
