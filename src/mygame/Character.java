/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.animation.*;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author krzysiek
 */
public class Character extends SceneNode implements AnimEventListener {
    private SceneManager scene;
    private AnimControl control;
    private AnimChannel channel;
    public String name;
    public String modelPath = "Models/Player/Cube.001.mesh.j3o";
    public String hit1Path = "Models/Hit1.mesh.xml";
    public String hit2Path = "Models/Hit2.mesh.xml";
    
    public String distanceHitPath = "Models/Icosphere.mesh.xml";

    public float walkSpeed = 2f;
    public MovementState movementState;
    private final LastValueHolder<Vector3f> lookingVectorHolder;
    private Vector3f lookingVector;
    private final ArrayList<SceneElement> attacks;
    Spatial model;
    public Character(SceneManager sm) {
        AssetManager assetManager;
        Spatial model = sm.getAssetManager().loadModel(modelPath);
        sm.attachAndGetManager(model);
        this.model = model;
        scene = sm;
        model.setLocalScale(0.2f);
        model.setLocalTranslation(0, 0, 0);
        attacks = new ArrayList<SceneElement>();
                
        movementState = new MovementState();
        lookingVectorHolder = new LastValueHolder<>((Vector3f value) -> !value.equals(new Vector3f(0,0,0)), new Vector3f(1, 0, 0));
        control = model.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        channel.setAnim("Idle", 0.50f);
        
    }
    public Spatial getModel() {
        return model;
        //return scene.getRootNode();
    }
    public Vector3f getLookingVector() {
        Vector3f requestedLookingVector = lookingVectorHolder.getValue(movementState.getInputVector());
        if (lookingVector == null) {
            lookingVector = requestedLookingVector;
            return requestedLookingVector;
        } else {
            Vector3f newLookingVector = ConstantSpeedVectorTransition.translate(lookingVector, requestedLookingVector, 0.1f);
            lookingVector = newLookingVector;
            return newLookingVector;
        }
    }
    
    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }
    
    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
    }

    
    
    public void walk() {
        channel.setAnim("Walk", 0.50f);
        channel.setLoopMode(LoopMode.Loop);
    }
    
    public void stop() {
        channel.setAnim("Idle", 0.50f);
        channel.setLoopMode(LoopMode.Loop);
        channel.setSpeed(1f);
    }
    
    public void hit() {
        System.out.println("HICIOR!");
        Spatial hitModel = scene.getAssetManager().loadModel(Math.random() > 0.5 ? hit1Path : hit2Path);
        hitModel.setLocalTransform(model.getLocalTransform());
        hitModel.setLocalTranslation(model.getLocalTranslation().add(new Vector3f(0, 0.2f, 0)));
        SceneElement element = new HitSceneElement(hitModel, getHitConfig(),  new Hit(getHitPower(), this));
        scene.attach(element);
        attacks.add(element);
    }
    
    public void distanceHit() {
        System.out.println("DISTANCE HICIOR!");
        Spatial hitModel = scene.getAssetManager().loadModel(distanceHitPath);
        hitModel.setLocalTransform(model.getLocalTransform());
        hitModel.setLocalTranslation(model.getLocalTranslation().add(new Vector3f(0, 0.2f, 0)));
        SceneElement element = new HitSceneElement(hitModel, getDistanceHitConfig(),  new Hit(getDistanceHitPower(), this));
        element.setVelocity(getLookingVector());
        scene.attach(element);
        attacks.add(element);
    }
    
    
    private int hp = 100;
    public void gotHit(int power) {
        hp -= power;
    }
    public int getHitPower(){
        return (int)Math.round(10+(Math.random()*20));
    }
    public int getDistanceHitPower(){
        return (int)Math.round(5+(Math.random()*5));
    }
    @Override
    public SceneNodeConfig getConfig() {
        return new SceneNodeConfig(true, 0);
    }
    
    public SceneNodeConfig getHitConfig() {
        return new SceneNodeConfig(false, 0.2f);
    }
    
    public SceneNodeConfig getDistanceHitConfig() {
        return new SceneNodeConfig(false, 4f);
    }

    public void removeHit(HitSceneElement aThis) {
        attacks.remove(aThis);
    }
    
    public List<SceneElement> getAttacks() {
        return this.attacks;
    }
}
