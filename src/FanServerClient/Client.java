
package FanServerClient;

import java.io.*;
import java.net.*;;
import static java.lang.Math.round;
import java.util.ArrayList;
import javax.swing.*;

public class Client extends JFrame {
    int numberOfFans = 3;
    Fan[] fan = new Fan[numberOfFans];
    Fan[] fan2 = new Fan[numberOfFans];
    FanPane[] fanPanes = new FanPane[numberOfFans];
    ArrayList<Fan> fans = new ArrayList();
    
    public static void main(String[] args) throws IOException
    {
        new Client();
    }
    
    Client() throws IOException
    {
        Socket echoSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

    //Connect to Server        
        try {
            echoSocket = new Socket("127.0.0.1", 10007);
               
            
            out = new ObjectOutputStream(echoSocket.getOutputStream());
            in = new ObjectInputStream(echoSocket.getInputStream());
        }catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: taranis.");
            System.exit(1);
        }
    // initialize fan arrays
            for (int i = 0; i < numberOfFans; i++)
            {
                fan[i] = new Fan();
                fan2[i] = new Fan();
            }
    //write then read object from server as two different objects in infinite loop
        while(true)
        {
            //in = new ObjectInputStream(echoSocket.getInputStream());
            for (int i = 0; i < numberOfFans; i++)
            {
                fan[i].setSpeed(Math.round(Math.random()*100));
            }
    //write fan1           
            System.out.println ("fan speed: " + fan[0].getSpeed() + " to Server");
            out.writeObject(fan);
            out.flush();
    // set fan to equal fan2
            this.setFan(fan, fan2);
    // read fan2
            try {
                 fan2 = (Fan[]) in.readObject();
                }
            catch (Exception ex)
                {
                 System.out.println (ex.getMessage());
                }

            System.out.println("fan2[0] speed: " + fan2[0].getSpeed() + " from Server");
            out.reset();
        }
	//out.close();
	//in.close();
	//echoSocket.close();
    }
    
    
// public methods
    
    
    public  void setFanPanes()
    {
        for(int i = 0; i<numberOfFans; i++)
        {
            fans.add(fan[i]);
        }
    }
    
    
    public FanPane[] getFanPanes()
    {
        return fanPanes;
    }
    
    public ArrayList<Fan> getFans()
    {
        return fans;
    }
    /// Didn't realize Felix used an ArrayList instead of an array... 
    /// will change later
    
    public  void setFan(Fan[] fan, Fan[] fan2)
    {
       for(int i = 0; i < numberOfFans; i++)
                {
                    //fan[i].setSpeed(fan2[i].getSpeed());
                    fan[i].setSpeed(round(Math.random()*100));
                    fan[i].setTemperature(fan2[i].getTemperature());
                    fan[i].setDuty(fan2[i].getDuty());
                }
    }
    
}
