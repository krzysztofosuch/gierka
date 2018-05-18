/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.AbstractMap;
import java.util.ConcurrentModificationException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author krzysiek
 */
public class SceneManager {
    private final AssetManager assetManager;
    private final Node rootNode;
    
    private final List<SceneElement> activeElements;
    public SceneManager(AssetManager am, Node rootNode) {
        this(am, rootNode, new LinkedList<>());
    }
    public SceneManager(AssetManager am, Node rootNode, List<SceneElement> elements) {
        this.assetManager = am;
        this.rootNode = rootNode;
        activeElements = elements;
    }
    public AssetManager getAssetManager(){
        return this.assetManager;
    }
    
    public SceneManager attachAndGetManager(Spatial node) {
        attach(node);
        return new SceneManager(assetManager, (Node)node, activeElements);
    }
    public int attach(Spatial s) {
        return rootNode.attachChild(s);
    }
    
    public int attach(SceneElement s) {
        activeElements.add(s);
        System.out.printf("add to %s\n", System.identityHashCode(activeElements));
//        SceneElementConfig config = s.getConfig();
        return rootNode.attachChild(s.getSpatial());
    }
    
    public Node getRootNode() {
        return rootNode;
    }
    public void updateActiveElements(float tpf) {
        //System.out.printf("iterate %s\n", System.identityHashCode(activeElements));
        try {

            for (SceneElement se: activeElements) {
                se.decreaseTTL(tpf);
                if (!se.isStillAlive()) {
                    se.die();
                    this.activeElements.remove(se);
                    Spatial s = se.getSpatial();
                    s.getParent().detachChild(s);
                    System.out.println("KILLED SPATIAL");
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException, whatever");
        }
    }
}
