package mario.android.tvseries.model.show;

public class Image
{
    private String original;

    private String medium;

    public String getOriginal ()
    {
        return original;
    }

    public void setOriginal (String original)
    {
        this.original = original;
    }

    public String getMedium ()
    {
        return medium;
    }

    public void setMedium (String medium)
    {
        this.medium = medium;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [original = "+original+", medium = "+medium+"]";
    }
}
