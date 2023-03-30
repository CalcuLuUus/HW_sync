public abstract class Display {
    public abstract int getRow();
    public abstract String getRowText(int row);
    public void show(){
        int n = getRow();
        for(int i = 0; i < n; i++){
            System.out.println(getRowText(i));
        }
    }
}
