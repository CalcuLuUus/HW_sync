public class CarBuilder extends Builder {

    @Override
    public void makeBody() {
        System.out.println("Car: makeBody finish!");
    }

    @Override
    public void makeEng() {
        System.out.println("Car: makeEng finish!");
    }

    @Override
    public void makeWheel() {
        System.out.println("Car: makeWheel finish!");
    }

    @Override
    public void makeOther() {
        System.out.println("Car: makeOther finish!");
    }
    
}
