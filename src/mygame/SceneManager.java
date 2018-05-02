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
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author krzysiek
 */
public class SceneManager {
    private AssetManager assetManager;
    private Node rootNode;
    private AbstractMap<String, Spatial> elements;
    public SceneManager(AssetManager am, Node rootNode) {
        this.assetManager = am;
        this.rootNode = rootNode;
        elements = new HashMap<>();
    }
    public void addNode(String key, Spatial element) {
        this.elements.put(key, element);
    }
    public String addNodeWithAutoKey(Spatial element, String keyStart) {
        String key;
        do {
            key = randomKey(14);
        } while (this.elements.containsKey(key));
        addNode(key, element);
        return key;
    }
    public Spatial getNodeByKey(String key) {
        return this.elements.get(key);
    }
    public static String randomKey(int length) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        char random_char ;
        for(int i=0; i<length;i++)
        { 
            random_char = (char) (48 + r.nextInt(74));
            sb.append(random_char);
        }
        return sb.toString();
    }

}
