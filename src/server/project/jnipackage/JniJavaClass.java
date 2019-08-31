/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.project.jnipackage;

/**
 *
 * @author Ankit
 */
public class JniJavaClass {
    
    public native void firstStart(String  s , int i);
    
    static  {
        System.loadLibrary("firstStartImp");
    }
    
    public static void main(String args[]) {
        
        JniJavaClass jni = new JniJavaClass();
        jni.firstStart("This is ak", 4);
    }
    
}
