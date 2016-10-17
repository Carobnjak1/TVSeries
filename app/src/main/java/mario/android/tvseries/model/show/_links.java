package mario.android.tvseries.model.show;

public class _links
{
    private Previousepisode previousepisode;

    private Self self;

    public Previousepisode getPreviousepisode ()
    {
        return previousepisode;
    }

    public void setPreviousepisode (Previousepisode previousepisode)
    {
        this.previousepisode = previousepisode;
    }

    public Self getSelf ()
    {
        return self;
    }

    public void setSelf (Self self)
    {
        this.self = self;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [previousepisode = "+previousepisode+", self = "+self+"]";
    }
}
