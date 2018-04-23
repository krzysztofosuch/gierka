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
public class Character {
    public String name;
    public String modelPath = "Models/Ludek.mesh.xml";
    public float walkSpeed = 2f;
    public MovementState movementState;
    private LastValueHolder<Vector3f> lookingVectorHolder;
    
    public Character() {
        movementState = new MovementState();
        lookingVectorHolder = new LastValueHolder<>((Vector3f value) -> !value.equals(new Vector3f(0,0,0)), new Vector3f(1, 0, 0));
    }
    public Vector3f getLookingVector() {
        return lookingVectorHolder.getValue(movementState.getInputVector());
    }
    public void walk() {
    }
    
    public void stop() {
    }
    
    
}
