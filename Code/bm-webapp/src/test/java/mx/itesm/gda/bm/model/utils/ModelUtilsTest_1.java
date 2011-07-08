/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ModelUtilsTest.java 182 2010-10-08 14:11:48Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-08 09:11:48 -0500 (Fri, 08 Oct 2010) $
 * Last Version      : $Revision: 182 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.utils;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 182 $
 */
public class ModelUtilsTest_1 {

    private static class MockObject {

        private String name;

        private int value;

        @BusinessKey
        private float price;

        private boolean available;

        @BusinessKey
        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

    }

    private static class MockObject2 {

        private Throwable t;

        @BusinessKey
        public float price;

        @BusinessKey
        private String getName() throws Throwable {
            throw t;
        }

        @BusinessKey
        public String getUserName(int x) throws Throwable {
            throw t;
        }

    }

    private MockObject[] mocks;

    @Before
    public void setUp() {
        MockObject[] base_mocks = new MockObject[3];

        base_mocks[0] = new MockObject();
        base_mocks[0].name = "Mock #1";
        base_mocks[0].value = 3;
        base_mocks[0].price = 10.99f;
        base_mocks[0].available = false;

        base_mocks[1] = new MockObject();
        base_mocks[1].name = "Mock #2";
        base_mocks[1].value = 4;
        base_mocks[1].price = 20.99f;
        base_mocks[1].available = true;

        base_mocks[2] = new MockObject();
        base_mocks[2].name = "Mock #3";
        base_mocks[2].value = 5;
        base_mocks[2].price = 30.99f;
        base_mocks[2].available = true;

        List<MockObject> final_mocks = new ArrayList<MockObject>();

        for(int i = 0; i < base_mocks.length; i++) {
            for(int j = 0; j < base_mocks.length; j++) {
                for(int k = 0; k < base_mocks.length; k++) {
                    for(int l = 0; l < base_mocks.length; l++) {
                        MockObject mock = new MockObject();
                        mock.name = base_mocks[i].name;
                        mock.value = base_mocks[j].value;
                        mock.price = base_mocks[k].price;
                        mock.available = base_mocks[l].available;
                        final_mocks.add(mock);
                    }
                }
            }
        }

        mocks = final_mocks.toArray(new MockObject[final_mocks.size()]);

    }

    @Test
    public void classInstanciation() {
        ModelUtils m = new ModelUtils();

    }

    @Test
    public void identityTest() {
        for(int i = 0; i < mocks.length - 1; i++) {
            assertTrue(ModelUtils.equals(mocks[i], mocks[i]));
        }
    }

    @Test
    public void mismatchingClassTest() {
        for(int i = 0; i < mocks.length - 1; i++) {
            MockObject mock = new MockObject() {

                private boolean extra;

            };

            mock.name = mocks[i].name;
            mock.value = mocks[i].value;
            mock.price = mocks[i].price;
            mock.available = mocks[i].available;

            assertFalse(ModelUtils.equals(mocks[i], mock));
        }
    }

    @Test
    public void symetricTest() {
        for(int i = 0; i < mocks.length - 1; i++) {
            for(int j = i + 1; j < mocks.length; j++) {
                assertEquals(ModelUtils.equals(mocks[i], mocks[j]),
                        ModelUtils.equals(mocks[j], mocks[i]));
            }
        }
    }

    @Test
    public void transitiveTest() {
        int counter = 0;
        for(int i = 0; i < mocks.length - 1; i++) {
            for(int j = i + 1; j < mocks.length; j++) {
                for(int k = 0; k < mocks.length; k++) {
                    if(k == i || k == j
                            || !ModelUtils.equals(mocks[i], mocks[j])) {
                        continue;
                    }

                    assertEquals(ModelUtils.equals(mocks[j], mocks[k]),
                            ModelUtils.equals(mocks[i], mocks[k]));
                    counter++;
                }
            }
        }
        assertTrue(counter > 0);
    }

    @Test
    public void nullReferenceTest() {
        for(int i = 0; i < mocks.length; i++) {
            assertFalse(ModelUtils.equals(mocks[i], null));
            assertFalse(ModelUtils.equals(null, mocks[i]));
        }
    }

    @Test
    public void hashCodeEqualityTest() {
        int counter = 0;
        for(int i = 0; i < mocks.length; i++) {
            for(int j = 0; j < mocks.length; j++) {
                if(ModelUtils.equals(mocks[i], mocks[j])) {
                    assertEquals(ModelUtils.hashCode(mocks[i]),
                            ModelUtils.hashCode(mocks[j]));
                    counter++;
                }
            }
        }
        assertTrue(counter > 0);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionHandlingTest1() {
        MockObject2 mock1 = new MockObject2();
        MockObject2 mock2 = new MockObject2();

        ModelUtils.equals(mock1, mock2);
    }

    @Test(expected = RuntimeException.class)
    public void exceptionHandlingTest2() {
        MockObject2 mock1 = new MockObject2();
        mock1.t = new IllegalAccessException();

        ModelUtils.hashCode(mock1);
    }

}
