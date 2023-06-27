public class MovaTable extends Expression{

    private Expression DB;

    public MovaTable(Expression DB){
        this.DB = DB;
    }

    @Override
    public boolean interpret(String context) {
        String[] exp = context.split(" ");
        String srcDB = exp[3];
        String dstDB = exp[5];
        boolean res = DB.interpret(srcDB) && DB.interpret(dstDB);
        return res;
    }
    
}