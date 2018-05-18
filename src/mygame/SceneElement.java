/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author krzysiek
 */
public class SceneElement {
    private Spatial spatial;
    private float ttl;
    SceneNodeConfig config;
    private Vector3f velocity;
    
    public SceneElement(Spatial s, SceneNodeConfig config) {
        spatial = s;
        this.ttl = config.ttl;
    }
    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }
    public Vector3f getVelocity() {
        return this.velocity == null ? new Vector3f(0,0,0) : this.velocity;
    }
    public Spatial getSpatial() {
        return spatial;
    }
    public void decreaseTTL(float tpf) {
        this.ttl = this.ttl-tpf;
    }
    public boolean isStillAlive() {
        return ttl > 0;
    }   
    public void die() {
    
    }
    public void setTTL(float ttl) {
        this.ttl = ttl;
    }
}
