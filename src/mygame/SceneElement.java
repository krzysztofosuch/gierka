/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.scene.Spatial;

/**
 *
 * @author krzysiek
 */
public class SceneElement {
    private Spatial spatial;
    private float ttl;
    public SceneElement(Spatial s) {
        spatial = s;
    }
    public SceneElement(Spatial s, float ttl) {
        this(s);
        this.ttl = ttl;
        System.out.printf("New element with ttl %s\n", ttl);
        
    }
    public Spatial getSpatial() {
        return spatial;
    }
    public void decreaseTTL(float tpf) {
        System.out.printf("TTL %s decreased by %s\n",ttl, tpf);
        this.ttl = this.ttl-tpf;
        System.out.printf("TTL: %s\n",this.ttl);
        //System.out.printf("TTL decreased to %s\n",ttl);
    }
    public boolean isStillAlive() {
        return ttl > 0;
    }
}
