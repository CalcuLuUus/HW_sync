public class Chart extends Display{
    private String content;
    public Chart(String content){
        this.content = content;
    }

    @Override
    public int getRow() {
        return 1;
    }

    @Override
    public String getRowText(int row) {
        return content;
    }
    
}
