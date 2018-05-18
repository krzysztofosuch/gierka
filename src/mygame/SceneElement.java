/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

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
    public SceneElement(Spatial s, SceneNodeConfig config) {
        spatial = s;
        this.ttl = config.ttl;
        System.out.printf("got ttl: %s\n", ttl);
    }

    public Spatial getSpatial() {
        return spatial;
    }
    public void decreaseTTL(float tpf) {
        this.ttl = this.ttl-tpf;
        System.out.printf("ttl left: %s\n", ttl);
    }
    public boolean isStillAlive() {
        return ttl > 0;
    }   
}
