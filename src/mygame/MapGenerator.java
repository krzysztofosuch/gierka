/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author krzysiek
 */
public class MapGenerator {
    private final int sizeX;
    private final int sizeY;
    private final float squareDimension = 1f;
    private final float maxDistanceFromCenter;
    private final float chanceModifier = 0.5f;
    private final Vector3f offsetVector;
    public MapGenerator(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        maxDistanceFromCenter = distanceFromCenter(0, 0);
        this.offsetVector = new Vector3f(-(sizeX/2)*squareDimension, 0, -(sizeY/2)*squareDimension);
    }
    public List<Wall> generateMap() {
        List<Wall> walls = new ArrayList<>();
        for (int x = 0; x<sizeX; x++) {
            for (int y = 0; y<sizeY; y++) {
                boolean destructable = true;
                Mesh m;
                if (isBorderField(x, y)) {
                    m = wallSpatial();
                    destructable = false;
                } else {
                    float dist = distanceFromCenter(x, y);
                    float rand = (float)Math.random();
                    System.out.println(String.format("rand: %s, dist: %s, maxDist: %s, distFraction: %s", rand, dist, maxDistanceFromCenter, (dist/maxDistanceFromCenter)));
                    if (rand < (dist/maxDistanceFromCenter)*chanceModifier) {
                        m = wallSpatial();
                    } else {
                        //m = null;//blankSpatial();
                        continue;
                    }
                }
                Geometry g = new Geometry("Map"+x+y, m);
                g.setLocalTranslation(new Vector3f(x*squareDimension, 0, y*squareDimension).add(offsetVector));
                walls.add(new Wall(g, destructable));
            }
        }
        return walls;
    }
    private float distanceFromCenter(int x, int y) {
        int centerX = (int)(sizeX/2);
        int centerY = (int)(sizeY/2);
        int distanceX = Math.abs(centerX-x);
        int distanceY = Math.abs(centerY-y);
        return (float)Math.sqrt(distanceX*distanceX + distanceY*distanceY);
    }
    private Mesh wallSpatial() {
        return new Box(squareDimension, .5f, squareDimension);
    }
    private Mesh blankSpatial() {
        return new Box(squareDimension, 0.1f, squareDimension);
    }
    private int XYtoflat(int x, int y) {
        return x*sizeX+y;
    }
    private boolean isBorderField(int x, int y) {
        return x == 0 || y == 0 || x == sizeX-1 || y == sizeY-1;
    }
}
