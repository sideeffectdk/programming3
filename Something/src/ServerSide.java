import java.io.*;
import java.net.*;

class ServerSide implements Runnable 
{
private Socket s;

ServerSide(Socket s)
{
  this.s=s;
}


public void run () 
{

    try {
    // Get input from the client
        DataInputStream dis = new DataInputStream (s.getInputStream());
        PrintStream out1 = new PrintStream(s.getOutputStream());

        String str,extn="";
        str=dis.readUTF();
        System.out.println("\n"+str);
        int flag=0,i;
        
            for(i=0;i<str.length();i++)
            {
                
                if(str.charAt(i)=='.' || flag==1)
                {
                flag=1;
                extn+=str.charAt(i);
                }
            }
    
    
//**********************reading image*********************************//            
        
                       
                File file = new File("RecievedImage"+str);
                FileOutputStream fout = new FileOutputStream(file);
         
                //receive and save image from client
                byte[] readData = new byte[1024];
                while((i = dis.read(readData)) != -1)
                {
                    fout.write(readData, 0, i);
                    if(flag==1)
                    {
                    System.out.println("Image Has Been Received");
                    flag=0;
                    }
                }
            fout.flush();
            fout.close(); 
              
    } catch (IOException ioe)
    {
    	System.out.println("Something went wrong.");
    }
    
}
}

