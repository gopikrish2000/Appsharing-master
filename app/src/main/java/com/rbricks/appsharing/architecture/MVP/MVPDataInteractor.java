package com.rbricks.appsharing.architecture.MVP;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by gopi on 18/10/17.
 */

public class MVPDataInteractor {

    private List<String> colorList = Arrays.asList("#FF0000", "#00FF00", "#0000FF");

    public String generateRandomColor() {
        Random random = new Random();
        int val = random.nextInt(3);
        return colorList.get(val);
    }
}
