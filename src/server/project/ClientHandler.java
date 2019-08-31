/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.project;



import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Ankit
 */
public class ClientHandler extends Thread implements ObserverInterface {
    Socket socket = null;
    private static Robot robot;
    private boolean connect = true;
    ServerProject server;
 
   private  JniFunctionCall jni = new JniFunctionCall();
    
    private int virtualKey[] = {38,37,39,40,KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S};
    
    public ClientHandler() {}
    
    public  ClientHandler (Socket socket, Robot robot) {
     this.socket = socket;
     this.robot = robot;
   
    }
    
  
    
 
    @Override
    public void run() {
       
         InputStreamReader input = null;
            BufferedReader buff = null;
            String line = null;
            
            try {
             
                input = new InputStreamReader(socket.getInputStream());
                buff = new BufferedReader(input); 
               
            } catch (IOException ex) {
                //Logger.dddddgedtLoggeddr(Sedrver.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            while (connect) {
                
                if (!connect) {
                 input = null;
                 buff = null;
                  line = null;
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
                
                try {
                    line  = buff.readLine();
                     if ((line == null || line.equalsIgnoreCase("exit"))) {
                    socket.close();
                    return;
                }
                     System.out.println(line);
                     if(line.equalsIgnoreCase("next")){
				//Simulate press and release of key 'n'
				robot.keyPress(KeyEvent.VK_N);
				robot.keyRelease(KeyEvent.VK_N);
			}
			//if user clicks on previous
			else if(line.equalsIgnoreCase("previous")){
				//Simulate press and release of key 'p'
				robot.keyPress(KeyEvent.VK_P);
				robot.keyRelease(KeyEvent.VK_P);		        	
			}
			//if user clicks on play/pause
			else if(line.equalsIgnoreCase("play")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_SPACE);
				robot.keyRelease(KeyEvent.VK_SPACE);
			}
			//input will come in x,y format if user moves mouse on mousepad
			else if(line.contains(",")){
				float movex=Float.parseFloat(line.split(",")[0]);//extract movement in x direction
				float movey=Float.parseFloat(line.split(",")[1]);//extract movement in y direction
				Point point = MouseInfo.getPointerInfo().getLocation(); //Get current mouse position
				float nowx=point.x;
				float nowy=point.y;
				robot.mouseMove((int)(nowx+movex),(int)(nowy+movey));//Move mouse pointer to new location
			}
			//if user taps on mousepad to simulate a left click
			else if(line.contains("left_click")){
				//Simulate press and release of mouse button 1(makes sure correct button is pressed based on user's dexterity)
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			}
                } catch(IOException e) {
                    
                }
                
                
               // jni.move(true);
                 
              /*  for (int i = 0; i < inputFromClient.length()/2; i++) {
                   int index =  Integer.parseInt(inputFromClient.split(",")[i]);
                   
                  switch(index) {
                      
                      case 0 :
                   jni.moveLeft(true);
                          break;
                      case 1 :
                             jni.moveRight(true);
                         
                          break;
                      case 2 :
                            jni.move(true); 
                          break;
                      case 3 : 
                         
                            jni.moveBottom(true);
                          break;
                      default :
                          robot.keyPress(virtualKey[index]);
                          break;
                  }
                }
                
             try {
                 Thread.sleep(100);
                   } catch (InterruptedException ex) {
                 Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
             }
            
              for (int i = 0; i < inputFromClient.length()/2; i++) {
                   int index =  Integer.parseInt(inputFromClient.split(",")[i]);
                   
                  switch(index) {
                      case 0 :
                     jni.moveLeft(false);
                        
                          break;
                      case 1 :
                         jni.moveRight(false); 
                          break;
                      case 2 :
                          
                           jni.move(false);
                          break;
                      case 3 : 
                           jni.moveBottom(false);
                          break;
                      default :
                          robot.keyRelease(virtualKey[index]);
                          break;
                  }
                }
                 
                 // jni.pressButtons(virtualKey);
                 
                 
                 /*  if (inputFromClient.equalsIgnoreCase("play")) {
                 robot.keyPress(KeyEvent.VK_SPACE);
                 robot.keyRelease(KeyEvent.VK_SPACE);
                 }
                 else if (inputFromClient.equalsIgnoreCase("left")) {
                 
                 jni.moveLeft();
                 
                 } else if (inputFromClient.equalsIgnoreCase("right")) {
                 jni.moveRight();
                 
                 } else if (inputFromClient.equalsIgnoreCase("top")) {
                 
                 jni.move();
                 
                 } else if (inputFromClient.equalsIgnoreCase("bottom")) {
                 
                 jni.moveBottom();
                 
                 } else if (inputFromClient.equalsIgnoreCase("oButton")) {
                 robot.keyPress(KeyEvent.VK_W);
                 try {
                 Thread.sleep(100);
                 robot.keyRelease(KeyEvent.VK_W);
                 } catch (InterruptedException ex) {
                 Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
                 }
                 else if (inputFromClient.equalsIgnoreCase("sqButton")) {
                 robot.keyPress(KeyEvent.VK_S);
                 try {
                 Thread.sleep(30);
                 robot.keyRelease(KeyEvent.VK_S);
                 } catch (InterruptedException ex) {
                 Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
                 } else if (inputFromClient.equalsIgnoreCase("triButton")) {
                 
                 robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                 try {
                 Thread.sleep(100);
                 robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                 } catch (InterruptedException ex) {
                 Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
                 
                 }
                 else if (inputFromClient.equalsIgnoreCase("xButton")) {
                 robot.keyPress(KeyEvent.VK_D);
                 try {
                 Thread.sleep(30);
                 robot.keyRelease(KeyEvent.VK_D);
                 } catch (InterruptedException ex) {
                 Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
                 }
                 else if(inputFromClient.equalsIgnoreCase("previous")) {
                 robot.keyPress(KeyEvent.VK_W);
                 
                 try {
                 Thread.sleep(30);
                 robot.keyRelease(KeyEvent.VK_W);
                 } catch (InterruptedException ex) {
                 Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
                 
                 }
                 
                 else if(inputFromClient.contains(",")) {
                 float pointX = Float.parseFloat(inputFromClient.split(",")[0]);
                 float pointY = Float.parseFloat(inputFromClient.split(",")[1]);
                 
                 Point point = MouseInfo.getPointerInfo().getLocation();
                 float nowX = point.x;
                 float nowY = point.y;
                 robot.mouseMove((int)(pointX + nowX),(int) (nowY +pointY));
                 System.out.println("THE Mouse x and y position = "+pointX + " "+pointY);
                 
                 }
                 
                 else if(inputFromClient.equalsIgnoreCase("Left_click")) {
                 
                 robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                 try {
                 Thread.sleep(300);
                 robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                 } catch (InterruptedException ex) {
                 Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
                 
                 
                 }
                 
                 else if(inputFromClient.equalsIgnoreCase("next")) {
                 robot.keyPress(KeyEvent.VK_DOWN);
                 robot.keyRelease(KeyEvent.VK_DOWN);
                 } */
           
            
        }
            
       
        
       
        
    }
    
      public void makeStop() {
          
        
         }   

    @Override
    public void onObserve() {
       
        connect = false;
        
         //To change body of generated methods, choose Tools | Templates.
    }
    
}
