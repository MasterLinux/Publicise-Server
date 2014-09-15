package mock;

/**
 * Created by Christoph on 15.09.2014.
 */
public class NestedModel {
    private int integerValue;
    private double doubleValue;
    private NestedModel nested;

    public int getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public NestedModel getNested() {
        return nested;
    }

    public void setNested(NestedModel nested) {
        this.nested = nested;
    }
}
