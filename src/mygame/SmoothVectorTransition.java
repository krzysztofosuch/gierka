/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;

/**
 *
 * @author krzysiek
 */
public class SmoothVectorTransition {
    private Vector3f currentVector;
    private final Vector3f endVector;
    private float leftTransitionTime;
    public SmoothVectorTransition(Vector3f startVector, Vector3f endVector, float transitionTime) {
        this.currentVector = startVector;
        this.endVector = endVector;
        this.leftTransitionTime = transitionTime;
    }
    
    public Vector3f nextStep(float timeFromLastStep) {
        Vector3f stepVector = new Vector3f(
            singleNumberTransition(currentVector.x, endVector.x, leftTransitionTime, timeFromLastStep), 
            singleNumberTransition(currentVector.y, endVector.y, leftTransitionTime, timeFromLastStep), 
            singleNumberTransition(currentVector.z, endVector.z, leftTransitionTime, timeFromLastStep)
        );
        leftTransitionTime-=timeFromLastStep;
        currentVector = stepVector;
        return stepVector;
    }
    public static float singleNumberTransition(float from, float to, float transitionTime, float stepTime) {
        float diffToCover = to-from;
        return diffToCover*(stepTime/transitionTime);
    }
    
    
}
