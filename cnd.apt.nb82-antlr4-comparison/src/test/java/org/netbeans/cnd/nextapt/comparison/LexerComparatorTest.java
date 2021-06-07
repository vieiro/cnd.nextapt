/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.cnd.nextapt.comparison;

import java.io.InputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author antvie
 */
public class LexerComparatorTest {

    public LexerComparatorTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testShouldNotGenerateAnySyntaxError() throws Exception {
        String[] testFiles = {
            "c/char.c",
            "c/define.c",
            "c/include.c",
            "c/strings.c",
            "c/ksort.h",
        };

        for (String testFile : testFiles) {
            System.out.format("TEST: %s%n", testFile);
            try (InputStream inputA = LexerComparatorTest.class.getResourceAsStream(testFile);
                    InputStream inputB = LexerComparatorTest.class.getResourceAsStream(testFile)) {
                LexerComparator.lexInputStream(inputA, inputB);
            }
        }
    }

}
