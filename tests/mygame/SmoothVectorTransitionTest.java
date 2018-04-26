/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author krzysiek
 */
public class SmoothVectorTransitionTest {
    
    public SmoothVectorTransitionTest() {
    }

    /**
     * Test of nextStep method, of class SmoothVectorTransition.
     */
    @Test
    public void testNextStep() {
        SmoothVectorTransition instance = new SmoothVectorTransition(new Vector3f(1,1,1), new Vector3f(-1,-1,-1), 10f);
        assertEquals(new Vector3f(0,0,0), instance.nextStep(5f));
    }
    @Test 
    public void singleNumberTransitionTest() {
        assertEquals(3,SmoothVectorTransition.singleNumberTransition(0, 10, 10, 3), 0.5f);
    }
    @Test 
    public void singleNumberTransitionasdTest() {
        assertEquals(0,SmoothVectorTransition.singleNumberTransition(1, -1, 10, 5), 0.5f);
    }
    @Test 
    public void singleNumberTransitionbsdTest() {
        assertEquals(-5,SmoothVectorTransition.singleNumberTransition(-10, 0, 10, 5), 0.5f);
    }
    
}
