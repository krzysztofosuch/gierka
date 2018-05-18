
package mygame;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
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
import java.util.List;
import java.util.Random;
/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    public static final int mobs_number = 5;
    Random randomizer = new Random();
    SceneManager scene;
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    Spatial map;
    List<Enemy> oponents;
    Character character;
    
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
        
        map = assetManager.loadModel("Models/Mapka.mesh.xml");
        map.rotate((float)Math.PI, 0, 0);
        Material m = mat.clone();
        map.setMaterial(m);
        
        rootNode.attachChild(map);
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
        DirectionalLight sun2 = new DirectionalLight();
        sun2.setColor(ColorRGBA.White);
        sun2.setDirection(new Vector3f(.5f,.5f,.5f).normalizeLocal());
        com.jme3.light.Light light = new DirectionalLight();
        rootNode.addLight(light);

        
        
        stateManager.detach( stateManager.getState(FlyCamAppState.class) );
        
    }

    
    @Override
    public void simpleUpdate(float tpf) {
        try {
            Vector3f inputVector = character.movementState.getInputVector();
            Spatial player = character.getModel();
            player.move(inputVector.mult(character.walkSpeed*tpf));
            player.lookAt(player.getWorldTranslation().add(character.getLookingVector()), new Vector3f(0, 1, 0));
            getCamera().setLocation(player.getWorldTranslation().add(new Vector3f(5, 2.5f, 0)));
            getCamera().lookAt(player.getWorldTranslation(), new Vector3f(0, 1, 0));

            scene.updateActiveElements(tpf);
            Iterator<Enemy> i = oponents.iterator();
            while(i.hasNext()) {
                Enemy o = i.next();
                if(o.vulnerable != null){
                    o.vulnerable.countTime(tpf);
                    if(o.vulnerable.isReady()){
                        o.setState(0);
                    }
                }
                
                CollisionResults collision = new CollisionResults();

                for (SceneElement a : character.getAttacks()){
                    if (a instanceof HitSceneElement) {
                        HitSceneElement attack = (HitSceneElement)a;
                        if (!attack.isHamless()) {
                            Spatial s = attack.getSpatial();
                            s.move(attack.getVelocity().mult(tpf));
                            attack.getSpatial().collideWith(o.model.getWorldBound(), collision);
                            if (collision.size() > 0) {
                                o.gotHit(attack.getHit().power);
                                attack.setHarmless();
                                attack.setTTL(0.1f);
                                if(o.isDead()){
                                    if (oponents.contains(o)) {
                                        i.remove();
                                        rootNode.detachChild(o.model);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("NPE");
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
