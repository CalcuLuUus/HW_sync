public class Filter {
    private Img img;
    public Filter(Img img){
        this.img = img;
    }
    public void open(){
        img.imgOpen();
    }
    public void blur(){
        System.out.println("bluring......");
    }
    public void close(){
        img.imgClose();
    }
}
