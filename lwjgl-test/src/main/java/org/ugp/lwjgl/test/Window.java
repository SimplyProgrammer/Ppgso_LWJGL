package org.ugp.lwjgl.test;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

public class Window {

    public static final Map<Long, Window> windows = new HashMap<>();
//    public static GLFWKeyCallback keyCallback;
//    public static GLFWCursorPosCallback cursorPosCallback;
//    public static GLFWMouseButtonCallback mouseButtonCallback;
//    public static GLFWWindowCloseCallback windowCloseCallback;
    
    protected long window;

    public Window(String title, int width, int height) 
    {
		init();
		
		glfwSetWindowRefreshCallback(window, window1 -> onRefresh());
        
        // Set up key callback
//        keyCallback = 
        glfwSetKeyCallback(window, (window1, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(window1, true);
            }
            onKey(key, scancode, action, mods);
        });

        // Set up mouse callback
//        cursorPosCallback = 
        glfwSetCursorPosCallback(window, (window1, xpos, ypos) -> {
            onCursorPos(xpos, ypos);
        });

//        mouseButtonCallback = 
        glfwSetMouseButtonCallback(window, (window1, button, action, mods) -> {
            onMouseButton(button, action, mods);
        });

        // Set up window close callback
//        windowCloseCallback = 
        glfwSetWindowCloseCallback(window, (window1) -> {
            close();
        });

        // Store window instance
        windows.put(window, this);
    }
    
    public void init()
    {
		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		if ((window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL)) == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		
		// Make the window visible
		glfwShowWindow(window);
    }

    public void onKey(int key, int scancode, int action, int mods) {
        // Do nothing by default
    }

    public void onCursorPos(double xpos, double ypos) {
        // Do nothing by default
    }

    public void onMouseButton(int button, int action, int mods) {
        // Do nothing by default
    }

    public void close() {
    	long current = getWindow();
        glfwSetWindowShouldClose(current, true);
        
		glfwFreeCallbacks(current);
		glfwDestroyWindow(current);
    }

    public void onRefresh() {
        // Do nothing by default
    }
    
    public void update() {
        // Do nothing by default
    }

    public void resetViewport() {
    	IntBuffer fbWidth = IntBuffer.allocate(1), fbHeight = IntBuffer.allocate(1);
        glfwGetFramebufferSize(getWindow(), fbWidth, fbHeight);
        glViewport(0, 0, fbWidth.get(), fbHeight.get());
    }

    public void showCursor() {
        glfwSetInputMode(getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    public void hideCursor() {
        glfwSetInputMode(getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }

    public boolean pollEvents() {
    	update();
        glfwSwapBuffers(getWindow());
        glfwPollEvents();
        return !glfwWindowShouldClose(getWindow());
    }

    public void fpsLimit(boolean limit) 
    { 
        glfwSwapInterval(limit ? 1 : 0);
    }

    public void resize(int width, int height) 
    {
        glfwSetWindowSize(getWindow(), width, height);
    }

    public long getWindow() {
        return window;
    }
}