public class Border1 extends Border{
    String border = "++++++++++++++++++++++++++++++";
    protected Border1(Display display) {
        super(display);
    }

    @Override
    public int getRow() {
        return 1 + display.getRow() + 1;
    }

    @Override
    public String getRowText(int row) {
        if(row == 0 || row == display.getRow() + 1){
            return border;
        }else{
            return display.getRowText(row - 1);
        }
    }
    
}
