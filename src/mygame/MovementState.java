/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

/**
 *
 * @author krzysiek
 */
public class MovementState {
    public float speedX;
    public float speedY;
    public float speedZ;
    
    public float rotX;
    public float rotY;
    public float rotZ;
    
    
    public float inputNorth = 0;
    public float inputSouth = 0;
    public float inputEast = 0;
    public float inputWest = 0;
    public float rotationNS() {
        return inputSouth-inputNorth;
    }
    public float rotationWE() {
        return inputEast-inputWest;
    }
    public Vector3f getInputVector() {
        return new Vector3f(rotationNS(), 0, rotationWE()).normalize();
    }
    public float getLookingAngle(Vector3f referenceVector) {
        return referenceVector.angleBetween(getInputVector());
    }
    public Quaternion getRotationQuaternion() {
        Vector3f referenceVector = new Vector3f(0, 1, 0);
        return new Quaternion().fromAngleAxis(getLookingAngle(referenceVector), referenceVector);
        
    }
    
    public boolean isMoving() {
        return (inputEast + inputNorth + inputSouth + inputWest) > 0;
    }
}
