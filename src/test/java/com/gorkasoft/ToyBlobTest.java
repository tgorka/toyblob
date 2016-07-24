package com.gorkasoft;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the structure ToyBlob
 */
public class ToyBlobTest {

    /**
     * Basic test the structure
     */
    @Test
    public void basicTest() {

        ToyBlob<Object> toyBlob = new ToyBlob<>();
        int testSize = 10;

        for (int i = 0; i < testSize; i++) {
            System.out.println(toyBlob.toString().concat(", size:").concat(
                    String.valueOf(toyBlob.size())));
            assertEquals("ToyBlob size must be " + i, i, toyBlob.size());
            toyBlob.add(new Integer(i));
        }

        assertEquals("ToyBlob size must be " + testSize ,
                testSize, toyBlob.size());
        System.out.println(toyBlob.toString().concat(", size:").concat(
                String.valueOf(toyBlob.size())));

        for (int i = 0; i < testSize; i++) {
            Object removed = toyBlob.remove();
            System.out.println(toyBlob.toString().concat(", size:").concat(
                    String.valueOf(toyBlob.size())).concat(", removed:").concat(
                    (removed == null) ? "null" : removed.toString()));
            assertEquals("ToyBlob size must be " + (testSize - i - 1),
                    testSize - i - 1, toyBlob.size());
        }
    }

    /**
     * Random test the structure
     */
    @Test
    public void randomTest() {

        ToyBlob<Object> toyBlob = new ToyBlob<>();
        int testSize = 10;

        for (int i = 0; i < testSize; i++) {
            toyBlob.add("i".concat(String.valueOf(i)));
            toyBlob.add("j".concat(String.valueOf(i)));
            Object removed = toyBlob.remove();
            System.out.println(toyBlob.toString().concat(", size:").concat(
                    String.valueOf(toyBlob.size())).concat(", removed:").concat(
                    (removed == null) ? "null" : removed.toString()));
            assertEquals("ToyBlob size must be " + (i + 1), i + 1, toyBlob.size());
        }
    }

}
