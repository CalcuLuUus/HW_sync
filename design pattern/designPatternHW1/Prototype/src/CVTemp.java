public class CVTemp implements Product{
    private String name;
    private Picture picture;

    public CVTemp(String name, Picture picture) {
        this.name = name;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public Product createClone() throws CloneNotSupportedException {
        return (Product) super.clone();
    }

    @Override
    public Product createDeepClone() throws CloneNotSupportedException {
        CVTemp newTemplate = (CVTemp) super.clone();
        Picture newPicture = (Picture) newTemplate.getPicture().createClone();
        newTemplate.setPicture(newPicture);
        return newTemplate;
    }
}
