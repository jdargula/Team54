package com.example.jayda.team54;

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Unit test for getData methods.
 */
public class ViewReportsActivityTest {

    @Test
    public void getData() throws Exception {
        ViewReportsActivity vra = new ViewReportsActivity();
        int num = vra.getReportNum();
        vra.getData();
        ArrayList<String> reportsArr = vra.getReportsArr();

        assertEquals(num, reportsArr.size());
        assertTrue(!reportsArr.isEmpty());
    }

}