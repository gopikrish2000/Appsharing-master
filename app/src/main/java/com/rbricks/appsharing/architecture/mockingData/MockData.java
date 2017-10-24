package com.rbricks.appsharing.architecture.mockingData;

import static com.rbricks.appsharing.utils.CommonUtils.isNullOrEmpty;

/**
 * Created by gopi on 20/10/17.
 */

public class MockData {


    public String addConvertable(String input) {
        if (isNullOrEmpty(input)) {
            return "NULL";
        }
        return input + "CONVERTED";
    }
}
