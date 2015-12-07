//Med 3-2

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;


public class ServerSide 
{
	ServerSocket serverSocket;
	public ServerSide() throws IOException, InterruptedException{
		
		try {
			ServerSocket serverSocket = new ServerSocket(6666);
			System.out.println("Listening");
            
			//listen for client forever, until client sends something.
            while (true) 
            {
                Socket socket;

                socket = serverSocket.accept();
                Send send = new Send(socket);
            }
        }
		catch (IOException e) 
		{
			e.printStackTrace();}
        }
		
		public static void main(String[] args) throws IOException, InterruptedException
		{
	        // initiates the server
	        new ServerSide();
	    }
		
		public class Send extends Thread implements Runnable 
		{
	        Socket socket;
	        
	        public Send(Socket socket) 
	        {
	            this.socket = socket;
	            this.start();
	        }
	        
	        
	        public void run ()
	        {
	        	try 
	        	{
	        		OutputStream outputStream = socket.getOutputStream();
	        		InputStream inputStream = socket.getInputStream();
	        		System.out.println("Connected");
	        		byte[] sizeAr = new byte[4];
	        		inputStream.read(sizeAr);
	        		int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
	                System.out.println("Waiting to get image from client");
	        		
	                byte[] imageAr = new byte[size];
	                int sizerecv = 0;
	                int sizerecv2 = 0;


	                byte[] imageAr2 = new byte[size];
	                int placement = 0;
	                while(sizerecv < size){
	                    sizerecv2 = inputStream.read(imageAr);
	                    for(int i = 0; i < sizerecv2; i++)
	                    {
	                        imageAr2[i + placement] = imageAr[i];

	                    }
	                    placement = placement + sizerecv2;

	                    sizerecv = sizerecv + sizerecv2;
	                }
	                System.out.println("Read image data, converting to image.");
	                
	                BufferedImage buffer = null;
	                try {
	                    buffer = ImageIO.read(new ByteArrayInputStream(imageAr2));
	                    ImageIO.write(buffer, "jpg", new File("D:\\Desktop\\test2.jpg"));
	                    EdgeDetect edgeDetect = new EdgeDetect();
	                    System.out.println("Sending image back.");
	                    
	                    File fp = new File ("D:\\Desktop\\output.png");
	                    BufferedImage image = ImageIO.read(fp);
	                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	                    ImageIO.write(image, "jpg", byteArrayOutputStream);

	                    byte[] sendBack = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();

	                    //Sends the image
	                    outputStream.write(sendBack);
	                    outputStream.write(byteArrayOutputStream.toByteArray());
	                    outputStream.flush();
	                    System.out.println("Flushed back");
	                    
	                    //closing the streams and socket.
	                    inputStream.close();
	                    outputStream.close();
	                    socket.close();
	                }
	                catch(EOFException e) {
	                    
	                }
	                
	                
	        	}catch(IOException e)
	        	{
	        		e.printStackTrace();
	        	}
	        } 
	}
	
}

