package edu.byu.cs.familymapserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.byu.cs.familymapserver.util.RandomID;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    RandomID rand;
    int id_length = 10;
    @Before
    public void setUp() throws Exception {
        rand = new RandomID();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLoadDictionaryFromDatabase() throws Exception {
        System.out.println(rand.genRandomID(id_length));
        System.out.println(rand.genRandomID(id_length));
        System.out.println(rand.genRandomID(id_length));
        System.out.println(rand.genRandomID(id_length));
    }
}