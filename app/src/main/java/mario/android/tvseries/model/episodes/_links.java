package mario.android.tvseries.model.episodes;

import mario.android.tvseries.model.show.Self;


public class _links
{
    private Self self;

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
        return "ClassPojo [self = "+self+"]";
    }
}
