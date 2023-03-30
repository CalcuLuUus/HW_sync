public class JpgImg extends Img {
    String kind = "JPG";

    public JpgImg(String str) {
        kind = str;
    }

    @Override
    public void imgOpen() {
        System.out.println(kind + " OPEN");
    }

    @Override
    public void imgClose() {
        System.out.println(kind + "Close");
    }
}
