package com.rbricks.appsharing;

import com.rbricks.appsharing.concept.sqllite.NotesItem;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by gopikrishna on 05/12/16.
 */


public class GopiJunitTest {

    @Test
    public void aFirstTest() {
        NotesItem item = Mockito.mock(NotesItem.class);
        System.out.println("item = " + item);
        Assert.assertNotNull(item);
    }
}
