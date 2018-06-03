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
public class Wall {
    private Spatial spatial;
    private boolean destructable;
    public Wall(Spatial s) {
        this(s, true);
    }
    public Wall(Spatial s, boolean destructable) {
        this.spatial = s;
        this.destructable = destructable;
    }
    public Spatial getSpatial() {
        return spatial;
    }
    public boolean isDestructable() {
        return this.destructable;
    }

}
