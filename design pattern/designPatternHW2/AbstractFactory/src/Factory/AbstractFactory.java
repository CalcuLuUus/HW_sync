package Factory;
public abstract class AbstractFactory {
    public static AbstractFactory getFactory(String kind){
        AbstractFactory abstractFactory = null;
        try {
            abstractFactory = (AbstractFactory)Class.forName(kind).newInstance();
        } catch (ClassNotFoundException e){
            System.out.println(kind + " Not Found");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return abstractFactory;
    }
    public abstract Connection createConnection(String str);
    public abstract Statememt createStatememt(String str);
}
