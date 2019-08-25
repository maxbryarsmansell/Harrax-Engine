package com.max.harrax.layer;

import static org.lwjgl.glfw.GLFW.*;

import com.max.harrax.Application;
import com.max.harrax.events.Event;
import com.max.harrax.events.EventDispatcher;
import com.max.harrax.events.KeyPressedEvent;
import com.max.harrax.events.MouseButtonPressedEvent;
import com.max.harrax.events.MouseButtonReleasedEvent;
import com.max.harrax.events.MouseMovedEvent;
import com.max.harrax.graphics.Camera;
import com.max.harrax.graphics.Colour;
import com.max.harrax.graphics.Light;
import com.max.harrax.graphics.Material;
import com.max.harrax.graphics.Mesh;
import com.max.harrax.graphics.PerspectiveCamera;
import com.max.harrax.graphics.Renderer;
import com.max.harrax.graphics.Shader;
import com.max.harrax.input.Input;
import com.max.harrax.maths.Helper;
import com.max.harrax.maths.Mat4;
import com.max.harrax.maths.Vec2;
import com.max.harrax.maths.Vec3;

import java.util.ArrayList;
import java.util.Random;


public class DebugLayer extends Layer {
	
	private Mesh testMesh;
	
	private Shader lightingShader, basicShader;

	private Camera camera;
	private Vec3 cameraPosition = new Vec3();
	private float yaw = 0, pitch = 0;
	private boolean firstMouse = true;
	private float lastX = 0, lastY = 0;

	private ArrayList<Mat4> transforms;
	private ArrayList<Material> transformMaterials;

	private Vec3 lightPosition = new Vec3();
	
	public DebugLayer() {
		super("Debug");
	}

	@Override
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.EventType.KeyPressed, (Event e) -> (onKeyboardPress((KeyPressedEvent) e)));
		dispatcher.dispatch(Event.EventType.MouseButtonPressed, (Event e) -> (onMousePressed((MouseButtonPressedEvent) e)));
		dispatcher.dispatch(Event.EventType.MouseButtonReleased, (Event e) -> (onMouseReleased((MouseButtonReleasedEvent) e)));
		dispatcher.dispatch(Event.EventType.MouseMoved, (Event e) -> (onMouseMoved((MouseMovedEvent) e)));
	}

	private boolean onKeyboardPress(KeyPressedEvent e) {
		//System.out.println((char)e.getKeyCode() + " pressed");
		
		switch (e.getKeyCode()) {
		case GLFW_KEY_W:
			//cameraPosition.z -= 0.5f;
			break;
		case GLFW_KEY_S:
			//cameraPosition.z += 0.5f;
			break;
		}
		
		//System.out.println(cameraPosition.toString());
		
		return true;
	}
	
	private boolean onMousePressed(MouseButtonPressedEvent e) {
		//System.out.println(e.getMouseButton() == 0 ? "Mouse 1 pressed" : (e.getMouseButton() == 1 ? "Mouse 2 pressed" : "Other Mouse pressed"));
		return true;
	}
	
	private boolean onMouseReleased(MouseButtonReleasedEvent e) {
		//System.out.println(e.getMouseButton() == 0 ? "Mouse 1 released" : (e.getMouseButton() == 1 ? "Mouse 2 released" : "Other Mouse released"));
		
		return true;
	}
	
	private boolean onMouseMoved(MouseMovedEvent e) {
		//System.out.println(e.getPos().toString());
		
		return true;
	}
	
	private void input(float delta) {
		Vec2 mousePos = Input.mousePosition();
		
		if(firstMouse)
	    {
	        lastX = mousePos.x;
	        lastY = mousePos.y;
	        firstMouse = false;
	    }
	  
	    float xoffset = mousePos.x - lastX;
	    float yoffset = lastY - mousePos.y; 
	    lastX = mousePos.x;
	    lastY = mousePos.y;

	    float sensitivity = 0.01f;
	    xoffset *= sensitivity;
	    yoffset *= sensitivity;

	    yaw   += xoffset;
	    pitch += yoffset;

	    if(pitch > Math.PI / 2f - 0.01f)
	        pitch = (float) (Math.PI / 2f - 0.01f);
	    if(pitch < -Math.PI / 2f - 0.01f)
	        pitch = (float) (-Math.PI / 2f - 0.01f);
		
		float cameraSpeed = 100f * delta; // adjust accordingly
		Vec3 cameraFront = new Vec3(0, 0, -1);
		Vec3 cameraUp = new Vec3(0, 1, 0);
		
		cameraFront.x = (float) (Math.cos(pitch) * Math.cos(yaw));
		cameraFront.y = (float) Math.sin(pitch);
		cameraFront.z = (float) (Math.cos(pitch) * Math.sin(yaw));
		cameraFront = cameraFront.norm();
		
	    if (Input.isKeyDown(GLFW_KEY_W))
	    	cameraPosition.addEquals(cameraFront.mult(cameraSpeed));
	    if (Input.isKeyDown(GLFW_KEY_S))
	    	cameraPosition.subEquals(cameraFront.mult(cameraSpeed));
	    if (Input.isKeyDown(GLFW_KEY_A))
	    	cameraPosition.subEquals((cameraFront.cross(cameraUp)).norm().mult(cameraSpeed));
	    if (Input.isKeyDown(GLFW_KEY_D))
	    	cameraPosition.addEquals((cameraFront.cross(cameraUp)).norm().mult(cameraSpeed));
		
		
		camera.setPosition(cameraPosition);
		((PerspectiveCamera)camera).lookAt(cameraPosition.add(cameraFront));
	}

	@Override
	public void onUpdate(float delta) {
		
		input(delta);
		
		double lightRadius = 40.0;
		double time = Application.get().totalTime();
		
		lightPosition.x = (float) (Math.sin(time) * lightRadius);
		lightPosition.y = (float) (Math.sin(time) * lightRadius * 0.5f);
		lightPosition.z = (float) (Math.cos(time) * lightRadius);
		
		Light light = new Light(
				lightPosition ,
				new Vec3(1, 1, 1),
				new Vec3(1, 1, 1),
				new Vec3(1, 1, 1));
		
		Renderer.beginScene(camera, light);
		
		for (int i = 0; i < transforms.size(); i++) {
			Renderer.submit(lightingShader, transformMaterials.get(i), testMesh, transforms.get(i).mult(Mat4.scale(2f)));
		}
		
		Renderer.submit(lightingShader, Material.MATERIAL_CHROME, testMesh, Mat4.scale(10f));
		
		Renderer.submit(basicShader, testMesh, Mat4.translation(lightPosition).mult(Mat4.scale(2f)));
		
		Renderer.endScene(); 
	}

	@Override
	public void onAttach() {
		System.out.println(name + " layer added to the layer stack.");
	
		testMesh = Mesh.cubeMesh2();
		
		transforms = new ArrayList<Mat4>();
		transformMaterials = new ArrayList<Material>();
		
		float radius = 100;
		Random rand = new Random();
		for (int i = 0; i < 1000; i++) {
			float x = (rand.nextFloat() * 2 - 1) * radius;
			float y = (rand.nextFloat() * 2 - 1) * radius;
			float z = (rand.nextFloat() * 2 - 1) * radius;
			Vec3 tra = new Vec3(x, y, z);
			transforms.add(Mat4.translation(tra));
			
			transformMaterials.add(Material.MATERIAL_EMERALD);
		}
		
		lightingShader = new Shader("/shaders/lighting.vert", "/shaders/lighting.frag");
		basicShader = new Shader("/shaders/basic.vert", "/shaders/basic.frag");
		
		camera = new PerspectiveCamera(Helper.toRadians(70f), 16f / 9f);
		
		//camera = new OrthographicCamera(-4, 4, -3, 3);
	}
	
	@Override
	public void dispose() {
		testMesh.dispose();
		lightingShader.dispose();
		basicShader.dispose();
	}

	@Override
	public void onDetach() {
		System.out.println(name + " layer removed from the layer stack.");
	}
}
