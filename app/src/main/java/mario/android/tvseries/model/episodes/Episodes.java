package mario.android.tvseries.model.episodes;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import mario.android.tvseries.model.show.Image;


public class Episodes extends RealmObject
{
    @Ignore
    private String summary;

    private String id;
    @Ignore
    private String airtime;
    @Ignore
    private String airstamp;
    @Ignore
    private String season;

    private String name;
    @Ignore
    private _links _links;
    @Ignore
    private Image image;
    @Ignore
    private String runtime;
    @Ignore
    private String number;
    @Ignore
    private String url;
    @Ignore
    private String airdate;
    @Ignore
    private boolean watched;

    private String tvShowId;

    public String getTvShowId() {
        return tvShowId;
    }

    public void setTvShowId(String tvShowId) {
        this.tvShowId = tvShowId;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    public String getSummary ()
    {
        return summary;
    }

    public void setSummary (String summary)
    {
        this.summary = summary;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAirtime ()
    {
        return airtime;
    }

    public void setAirtime (String airtime)
    {
        this.airtime = airtime;
    }

    public String getAirstamp ()
    {
        return airstamp;
    }

    public void setAirstamp (String airstamp)
    {
        this.airstamp = airstamp;
    }

    public String getSeason ()
    {
        return season;
    }

    public void setSeason (String season)
    {
        this.season = season;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public _links get_links ()
    {
        return _links;
    }

    public void set_links (_links _links)
    {
        this._links = _links;
    }

    public Image getImage ()
    {
        return image;
    }

    public void setImage (Image image)
    {
        this.image = image;
    }

    public String getRuntime ()
    {
        return runtime;
    }

    public void setRuntime (String runtime)
    {
        this.runtime = runtime;
    }

    public String getNumber ()
    {
        return number;
    }

    public void setNumber (String number)
    {
        this.number = number;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getAirdate ()
    {
        return airdate;
    }

    public void setAirdate (String airdate)
    {
        this.airdate = airdate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [summary = "+summary+", id = "+id+", airtime = "+airtime+", airstamp = "+airstamp+", season = "+season+", name = "+name+", _links = "+_links+", image = "+image+", runtime = "+runtime+", number = "+number+", url = "+url+", airdate = "+airdate+"]";
    }
}
			
			