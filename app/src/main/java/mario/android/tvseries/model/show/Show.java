package mario.android.tvseries.model.show;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;


public class Show extends RealmObject {
    @Ignore
    private String summary;
    @Ignore
    private String weight;
    @Ignore
    private String[] genres;
    @Ignore
    private String status;
    @Ignore
    private mario.android.tvseries.model.show._links _links;
    @Ignore
    private Image image;
    @Ignore
    private String runtime;
    @Ignore
    private Externals externals;
    @Ignore
    private String type;
    @Ignore
    private Network network;
    @Ignore
    private String url;

    private String id;
    @Ignore
    private Schedule schedule;
    @Ignore
    private String updated;

    private String name;
    @Ignore
    private String premiered;
    @Ignore
    private String language;
    @Ignore
    private Rating rating;
    @Ignore
    private WebChannel webChannel;
    @Ignore
    private boolean liked;

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public _links get_links() {
        return _links;
    }

    public void set_links(_links _links) {
        this._links = _links;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Externals getExternals() {
        return externals;
    }

    public void setExternals(Externals externals) {
        this.externals = externals;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public WebChannel getWebChannel() {
        return webChannel;
    }

    public void setWebChannel(WebChannel webChannel) {
        this.webChannel = webChannel;
    }

    @Override
    public String toString() {
        return "ClassPojo [summary = " + summary + ", weight = " + weight + ", genres = " + genres + ", status = " + status + ", _links = " + _links + ", image = " + image + ", runtime = " + runtime + ", externals = " + externals + ", type = " + type + ", network = " + network + ", url = " + url + ", id = " + id + ", schedule = " + schedule + ", updated = " + updated + ", name = " + name + ", premiered = " + premiered + ", language = " + language + ", rating = " + rating + ", webChannel = " + webChannel + "]";
    }
}
