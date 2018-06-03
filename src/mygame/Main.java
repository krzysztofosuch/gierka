
package mygame;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    public static final int mobs_number = 10;
    Random randomizer = new Random();
    SceneManager scene;
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    List<Enemy> oponents;
    Character character;
    
    DirectionalLight activeLight;
    private List<Wall> mapElements;
    
    @Override
    public void simpleInitApp() {
        scene = new SceneManager(assetManager, rootNode);
        oponents = new ArrayList<>(mobs_number);
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        character = new Character(scene);
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Attack", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Attack2", new KeyTrigger(KeyInput.KEY_O));
        inputManager.addListener(new MovementInputListener(character), "Left", "Right", "Up", "Down");
        inputManager.addListener(new ActionInputListener(character), "Attack", "Attack2");
        
        Material m = mat.clone();
        mapElements = new MapGenerator(20, 20).generateMap();
        for (Wall wall : mapElements) {
            Spatial field = wall.getSpatial();
            field.setMaterial(mat);
            rootNode.attachChild(field);
        }
        

        for (int i = 0;i<mobs_number;i++) {
            Enemy o = new Enemy(assetManager);
            o.model = assetManager.loadModel(o.modelPath);
            o.model.setMaterial(mat);
            o.model.setLocalScale(0.2f);
            o.model.setLocalTranslation((randomizer.nextInt(10)-5), 0.5f, (randomizer.nextInt(10)-5));
            rootNode.attachChild(o.model);
            oponents.add(o);
        }
        
        
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f,-.5f,-.5f).normalizeLocal());
        rootNode.addLight(sun);
        DirectionalLight sun2 = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(.5f,.5f,.5f).normalizeLocal());
        rootNode.addLight(sun2);
        
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 0.5f));
        rootNode.addLight(ambient);

        
        
        stateManager.detach( stateManager.getState(FlyCamAppState.class) );
        
    }

    
    @Override
    public void simpleUpdate(float tpf) {
        try {
            Vector3f inputVector = character.movementState.getInputVector();
            Spatial player = character.getModel();
            player.move(inputVector.mult(character.walkSpeed*tpf));
            player.lookAt(player.getWorldTranslation().add(character.getLookingVector()), new Vector3f(0, 1, 0));
            getCamera().setLocation(player.getWorldTranslation().add(new Vector3f(5, 4f, 0.2f)));
            getCamera().lookAt(player.getWorldTranslation(), new Vector3f(0, 1, 0));

            scene.updateActiveElements(tpf);
            Iterator<Enemy> i = oponents.iterator();
            for (SceneElement a : character.getAttacks()){
                if (a instanceof HitSceneElement) {
                    HitSceneElement attack = (HitSceneElement)a;
                    if (!attack.isHamless()) {
                        Spatial s = attack.getSpatial();
                        s.move(attack.getVelocity().mult(tpf));
                        collideWithWalls(attack);
                    }
                }
            }

            while(i.hasNext()) {
                Enemy enemy = i.next();
                if(enemy.vulnerable != null){
                    enemy.vulnerable.countTime(tpf);
                    if(enemy.vulnerable.isReady()){
                        enemy.setState(0);
                    }
                }
                

                for (SceneElement a : character.getAttacks()) {
                    if (a instanceof HitSceneElement) {
                        HitSceneElement attack = (HitSceneElement)a;
                        if (!attack.isHamless()) {
                            CollisionResults collision = new CollisionResults();
                            attack.getSpatial().collideWith(enemy.model.getWorldBound(), collision);
                            if (collision.size() > 0) {
                                enemy.gotHit(attack.getHit().power);
                                attack.setHarmless();
                                attack.setTTL(0.1f);
                                if(enemy.isDead()){
                                    if (oponents.contains(enemy)) {
                                        i.remove();
                                        rootNode.detachChild(enemy.model);
                                    }
                                }
                            }

                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("NPE");
            throw e;
        }
        
    }
    private void collideWithWalls(HitSceneElement attack) {
        List<Wall> wallsToRemove = null;
        for (Wall w : mapElements) {
            Spatial s = w.getSpatial();
            CollisionResults collision = new CollisionResults();
            attack.getSpatial().collideWith(s.getWorldBound(), collision);
            if (collision.size() > 0) {
                attack.setHarmless();
                attack.setTTL(.1f);
                if (w.isDestructable()) {
                    if (wallsToRemove == null) {
                        wallsToRemove = new LinkedList<>();
                    }
                    wallsToRemove.add(w);
                    rootNode.detachChild(s);
                }
            }
        }
        if (wallsToRemove != null && wallsToRemove.size() > 0) {
            mapElements.removeAll(wallsToRemove);
        }
    }
    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    @Override
    public void update() {
        super.update(); // makes sure to execute AppTasks
        float tpf = timer.getTimePerFrame() * speed;
        stateManager.update(tpf);
        simpleUpdate(tpf);
    }
}
