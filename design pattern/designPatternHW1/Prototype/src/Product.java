public interface Product extends Cloneable{
    public Product createClone() throws CloneNotSupportedException;
    public Product createDeepClone() throws CloneNotSupportedException;
}