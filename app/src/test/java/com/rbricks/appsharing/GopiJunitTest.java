package com.rbricks.appsharing;

import com.rbricks.appsharing.architecture.mockingData.MockData;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by gopikrishna on 05/12/16.
 */


public class GopiJunitTest {

    @Test
    public void aFirstTest() {
        MockData item = Mockito.mock(MockData.class);
//        when(item.addConvertable("r")).thenReturn("r");
        when(item.addConvertable(any())).thenReturn("FOR EVERYTHING");
//        when(item.addConvertable("")).thenReturn("NULL");
//        when(item.addConvertable(null)).thenReturn("NULL");
        assertEquals(item.addConvertable("r"), "r");
        assertEquals(item.addConvertable("abc"), "abcCONVERTED");
    }


}
