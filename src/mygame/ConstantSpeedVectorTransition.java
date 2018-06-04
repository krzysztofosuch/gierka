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
public class ConstantSpeedVectorTransition {
    private static float speed = 1f;
    public static Vector3f translate(Vector3f startVector, Vector3f endVector, float step) {
        Vector3f diff = endVector.subtract(startVector);
        if (diff.length() < step*speed) {
            return endVector;
        } else {
            return startVector.add(diff.mult(step*speed));
        }
    }
}
