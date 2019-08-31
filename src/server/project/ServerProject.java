/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.project;

import java.awt.AWTException;
import java.awt.List;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Ankit
 */


public class ServerProject {

    /**
     * @param args the command line arguments
     */
    
    
    
    private static ServerSocket server = null;
    private static Socket client = null;
    private static BufferedReader in = null;
    private static String line;
    private static boolean isConnected = true;
    private static Robot robot;
    ClientHandler clientHandler;
    private static final int SERVER_PORT = 8998;
     ArrayList<ClientHandler> listHandler  = new ArrayList<ClientHandler>();
    
    public  ServerProject() {
     DataInputStream din = null;
         try {
            robot = new Robot();
            server = new ServerSocket(SERVER_PORT);
            
            
            //in = new BufferedReader(new InputStreamReader(client.getInputStream()));
           // din = new DataInputStream(client.getInputStream());
        } catch(IOException e) {
            System.out.print("Error in openning socket" + e.toString());
            System.exit(-1);
        }
        catch(AWTException e) {
            System.out.println("Error in create robot");
            System.exit(-1);
        }
         
    }
    
    void setupServerSocket() {
                
   
    }
    
   void setupServer(){
   // System.out.println("we are in setupServer");
  
        
            while (true) {
               
            try {
                
            client = server.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            
          ClientHandler cl = new ClientHandler(client, robot);
          listHandler.add(cl);
          cl.start();
            
        }
         
         
        
       /* while (isConnected) {
            
            InputStreamReader input = null;
            BufferedReader buff = null;
            String inputFromClient = null;
            
            try {
             
                input = new InputStreamReader(client.getInputStream());
                buff = new BufferedReader(input);
                inputFromClient  = buff.readLine();
                
               
            } catch (IOException ex) {
                //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (inputFromClient.equalsIgnoreCase("play")) {
                 robot.keyPress(KeyEvent.VK_SPACE);
                 robot.keyRelease(KeyEvent.VK_SPACE);
            }
            else if(inputFromClient.equalsIgnoreCase("previous")) {
                robot.keyPress(KeyEvent.VK_UP);
                 robot.keyRelease(KeyEvent.VK_UP);
            }
            
            else if(inputFromClient.contains(",")) {
                float pointX = Float.parseFloat(inputFromClient.split(",")[0]);
                float pointY = Float.parseFloat(inputFromClient.split(",")[1]);
                
                Point point = MouseInfo.getPointerInfo().getLocation();
                float nowX = point.x;
                float nowY = point.y;
                robot.mouseMove((int)(pointX + nowX),(int) (nowY + pointY));
                        
            }
            
             else if(inputFromClient.equalsIgnoreCase("Left_click")) {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                 robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }
            
            else if(inputFromClient.equalsIgnoreCase("next")) {
                robot.keyPress(KeyEvent.VK_DOWN);
                 robot.keyRelease(KeyEvent.VK_DOWN);
            }
            
            
        }
        */
        
       
        
   }
 
   
  void stop() {
      
    for (ClientHandler c : listHandler) {
        c.onObserve();
    }
     
  }
   
    
}
