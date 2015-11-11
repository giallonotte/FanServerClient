
package FanServerClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Math.round;
import java.net.ServerSocket;
import java.net.Socket;

public class Server  {
    int numberOfFans = 3;
    Fan fan[] = new Fan[numberOfFans];
    Fan[] fan2 = new Fan[numberOfFans];
    FanPane[] fanPanes = new FanPane[numberOfFans];
    
  public static void main(String[] args) throws IOException
  {
    new Server();
  }

  public Server() throws IOException 
  {
    ServerSocket serverSocket = null; 

    try { 
         serverSocket = new ServerSocket(10007); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Could not listen on port: 10007."); 
         System.exit(1); 
        } 

    Socket clientSocket = null; 

    try { 
         System.out.println ("Waiting for Client");
         clientSocket = serverSocket.accept(); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Accept failed."); 
         System.exit(1); 
        } 

    ObjectOutputStream out = new ObjectOutputStream(
                                     clientSocket.getOutputStream()); 
    ObjectInputStream in = new ObjectInputStream( 
                                     clientSocket.getInputStream()); 
    
    for (int i = 0; i < numberOfFans; i++)
        {
            fan[i] = new Fan();
            fan2[i] = new Fan();
            
        }
    
while (true)
{
        for (int i = 0; i < numberOfFans; i++)
        {
                fan2[i].setSpeed(Math.round(Math.random()*100));
        }
    try 
    {
        fan = (Fan[]) in.readObject();
    }
    catch (Exception ex)
        {
         System.out.println (ex.getMessage());
        }


    System.out.println ("Fan[0] speed = " + fan[0].getSpeed() + " from Client");

    
    System.out.println ("Fan2[0] speed " + fan2[0].getSpeed() + " to Client");
    out.writeObject(fan2); 
    out.flush();
    out.reset();
        }

    //out.close(); 
    //in.close(); 
    //clientSocket.close(); 
    //serverSocket.close(); 
     
  }
  public  void setFanPanes(Fan fan)
  {
        for(int i = 0; i < numberOfFans; i++)
        {
        fanPanes[i] = new FanPane();
        fanPanes[i].setFan(fan);
        }
  }
  public FanPane[] getFanPanes()
  {
        return fanPanes;
  }
    public  void setFan(Fan[] fan, Fan[] fan2)
    {
       for(int i = 0; i < numberOfFans; i++)
        {
            //fanServer[i].setSpeed(fanClient[i].getSpeed());
            fan[i].setSpeed(round(Math.random()*100));
            fan[i].setTemperature(fan2[i].getTemperature());
            fan[i].setDuty(fan2[i].getDuty());
        }
    }
    
}

