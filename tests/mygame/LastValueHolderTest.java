/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector2f;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author krzysiek
 */
public class LastValueHolderTest {
    
    public LastValueHolderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getValue method, of class LastValueHolder.
     */
    @Test
    public void testGetValidValue() {
        LastValueHolder<Vector2f> instance = new LastValueHolder<>((value) -> {
            return !value.equals(new Vector2f(0, 0));
        });
        assertTrue(instance.getValue(new Vector2f(0, 1f)).equals(new Vector2f(0,1f)));
    }
    @Test
    public void testGetInValidValue() {
        LastValueHolder<Vector2f> instance = new LastValueHolder<>((value) -> {
            return !value.equals(new Vector2f(0, 0));
        });
        instance.getValue(new Vector2f(0, 1f));
        assertTrue(instance.getValue(new Vector2f(0, 0f)).equals(new Vector2f(0,1f)));
        assertFalse(instance.getValue(new Vector2f(0, 0f)).equals(new Vector2f(0,0)));
    }
    
    
}
