package com.rbricks.appsharing.designpatterns;

/**
 * Created by gopi on 21/03/17.
 */

public class SingleTonConcept implements SingleInterface {

    private static SingleTonConcept singleTonConcept;

    private SingleTonConcept() {

    }

    public static SingleTonConcept getInstance() {
        if (singleTonConcept == null) {
            synchronized (SingleTonConcept.class) {
                if (singleTonConcept == null) {
                    singleTonConcept = new SingleTonConcept();
                }
            }
        }
        return singleTonConcept;
    }

    public void doAdd(int a, int b) {
        System.out.println("(a+b) = " + (a + b));
    }

    @Override
    public void subtract(int a, int b) {
        System.out.println(" val " + (a - b));
    }
}

interface SingleInterface {
    void subtract(int a, int b);
}

// U cannot subclass a singleton bcoz of private constructor. So do
// 1. composition/Aggregation of SingletonInstance to achieve functionality
// 2. Make Singleton implements a interface n use that interface for delegation of objects.
class SingleSubclass implements SingleInterface {

    @Override
    public void subtract(int a, int b) {
        SingleTonConcept.getInstance().subtract(a, b);
    }
}

class SingleSubclass2  {
    SingleInterface singleInterface;

    public void subtract(int a, int b) {
        singleInterface.subtract(a, b);
    }
}
