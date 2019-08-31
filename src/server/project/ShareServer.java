/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.project;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Ankit
 */
public class ShareServer {
    
    private String filePath;
    private FileInputStream fis;
    private BufferedInputStream bis;
    private OutputStream os;
    private ServerSocket serverSocket;
    private Socket socket;
    
    public ShareServer(String filePath) {
        this.filePath = filePath;
    }
    
    public void startServer() throws IOException {
        System.out.println("SERVER METHOD");
        try {
            serverSocket = new ServerSocket(1553);
            while (true) {
                socket = serverSocket.accept();
                System.out.println("SERVER STARTED");
                new Thread (new Runnable() {
                    @Override
                    public void run() {
                
               try {
                 File myFile = new File(filePath);
                byte[] fileByte = new byte[(int)myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(fileByte, 0, fileByte.length);
                os = socket.getOutputStream();
                os.write(fileByte, 0, fileByte.length);
                os.flush();   
                }catch (IOException exp) {
                            
                 }
                         
                    }
                }).start();
            }
        }
        finally {
            if (socket != null) {
                socket.close();
            }
            if (bis != null) bis.close();
            if (os != null) os.close();
            if (serverSocket != null) serverSocket.close();
        }
    }
    
}
