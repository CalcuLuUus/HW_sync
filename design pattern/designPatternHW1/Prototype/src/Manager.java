import java.util.HashMap;

public class Manager {
    private HashMap<String, Product> templates = new HashMap<String, Product>();

    public Manager() {}

    public void registerTemplate(String name, Product template) {
        this.templates.put(name, template);
    }

    public CVTemp create(String name, int flag) throws CloneNotSupportedException {
        if(flag == 0) {
            return (CVTemp) this.templates.get(name).createClone();
        } else {
            return (CVTemp) this.templates.get(name).createDeepClone();
        }
    }
}
