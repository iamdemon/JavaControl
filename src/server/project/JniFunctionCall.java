/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.project;

/**
 *
 * @author Ankit
 */
public class JniFunctionCall {
    
   static {
       System.loadLibrary("GamePadConsole");
   }
    
    public native void move(boolean isUp);
    public native void moveBottom(boolean isUp);
    public native void moveLeft(boolean isUp);
    public native void moveRight(boolean isUp);
    public native void pressButtons(int keys[]);
    
    
}
