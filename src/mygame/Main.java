
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

    Spatial player;
    Spatial map;
    List<Enemy> oponents;
    Character character;
    
    @Override
    public void simpleInitApp() {
        scene = new SceneManager(assetManager, rootNode);
        oponents = new ArrayList<>(mobs_number);
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        character = new Character(scene);
        character.name = "Stefek";
        player = assetManager.loadModel(character.modelPath);
        player.setMaterial(mat);
        player.setLocalTranslation(new Vector3f(0, 3, 0));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Attack", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addListener(new InputListener(character), "Left", "Right", "Up", "Down");

        
        map = assetManager.loadModel("Models/Mapka.mesh.xml");
        map.rotate((float)Math.PI, 0, 0);
        Material m = mat.clone();
        map.setMaterial(m);
        
        rootNode.attachChild(player);
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
        //rootNode.addLight(sun);
        DirectionalLight sun2 = new DirectionalLight();
        sun2.setColor(ColorRGBA.White);
        sun2.setDirection(new Vector3f(.5f,.5f,.5f).normalizeLocal());
        //rootNode.addLight(sun2);
        com.jme3.light.Light light = new DirectionalLight();
        rootNode.addLight(light);
        player.setLocalScale(0.2f);
        player.setLocalTranslation(0, 0.5f, 0);
        
        stateManager.detach( stateManager.getState(FlyCamAppState.class) );
        
    }

    
    @Override
    public void simpleUpdate(float tpf) {
        
        Vector3f inputVector = character.movementState.getInputVector();
        System.out.printf("LookingVector: %s %s %s\n", inputVector.x, inputVector.y, inputVector.z);
        player.move(inputVector.mult(character.walkSpeed*tpf));
        player.lookAt(player.getWorldTranslation().add(character.getLookingVector()), new Vector3f(0, 1, 0));
        getCamera().setLocation(player.getWorldTranslation().add(new Vector3f(5, 2.5f, 0)));
        getCamera().lookAt(player.getWorldTranslation(), new Vector3f(0, 1, 0));
        
        
        for (Enemy o : oponents) {
            CollisionResults collision = new CollisionResults();
            player.collideWith(o.model.getWorldBound(), collision);
            if (collision.size() > 0) {
                o.gotHit();
            }
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
