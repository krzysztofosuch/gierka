
package mygame;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    Geometry geom;
    Spatial player;
    Spatial map;
    
    Character character;
    
    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        geom = new Geometry("Box", b);
        
        
        
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");

        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        
        character = new Character();
        character.name = "Stefek";
        player = assetManager.loadModel(character.modelPath);
        player.setMaterial(mat);
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(new InputListener(character), "Left", "Right", "Up", "Down");
//        TangentBinormalGenerator.generate(map);           // for lighting effect

        map = assetManager.loadModel("Models/Mapka.mesh.xml");
        map.rotate((float)Math.PI, 0, 0);
        Material m = mat.clone();
     //   m.setColor("Color", ColorRGBA.Red);
        map.setMaterial(m);
        
        rootNode.attachChild(player);
        rootNode.attachChild(map);
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-.5f,-.5f,-.5f).normalizeLocal());
        rootNode.addLight(sun);
        DirectionalLight sun2 = new DirectionalLight();
        sun2.setColor(ColorRGBA.White);
        sun2.setDirection(new Vector3f(.5f,.5f,.5f).normalizeLocal());
        rootNode.addLight(sun2);
        com.jme3.light.Light light = new DirectionalLight();
        rootNode.addLight(light);
        player.setLocalTranslation(0, 10, 0);
        
        stateManager.detach( stateManager.getState(FlyCamAppState.class) );
        
    }

    
    @Override
    public void simpleUpdate(float tpf) {
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.randomColor());
        geom.setMaterial(mat);
        
        geom.move(0, tpf, 0);
        Vector3f inputVector = character.movementState.getInputVector();
        System.out.printf("LookingVector: %s %s %s\n", inputVector.x, inputVector.y, inputVector.z);
        player.move(inputVector.mult(character.walkSpeed*tpf));
        player.lookAt(player.getWorldTranslation().add(inputVector), new Vector3f(0, 1, 0));
        getCamera().setLocation(player.getWorldTranslation().add(new Vector3f(10, 5, 0)));
        getCamera().lookAt(player.getWorldTranslation(), new Vector3f(0, 1, 0));
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
