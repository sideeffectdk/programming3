//Med 3-2

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;


public class ClientSide {
	
	private String hostName;
    private int portNumber;
    
    public static void main(String[] args) throws IOException, InterruptedException 
    {
    	//initialize the code.
    	new ClientSide();
    }
	
	public ClientSide() throws UnknownHostException, IOException, InterruptedException{
	    try {
	    	//Choose hostname and portnumber
	    	this.hostName = "localhost";
	        this.portNumber = 6666;
	        //inistializing further code, connecting to server
	        Send(new Socket(hostName, portNumber));
	           
	    }
	    catch (IOException e) {
	        System.out.println("Did not connect!");
	    }
	}
	
	
	public void Send(Socket socket) throws IOException, InterruptedException
	{
		
		OutputStream outputStream;
        InputStream inputStream = null;
        //Choose the picture
		File fp = new File ("D:\\Desktop\\giraffe.jpg");
		
		//Make outputstream and inputstream to send and recieve
		outputStream = socket.getOutputStream();
		inputStream = socket.getInputStream();
		
		//Read the image
		BufferedImage image = ImageIO.read(fp);
		
		//make image to bytes
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        System.out.println("Sending image to server.");
        
        //Send the image to the server
        outputStream.write(size);
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
        System.out.println("Flushed");
        
        //The server sends back the data
        System.out.println("Waiting for converted image.");
        //Reading the bytes from server
        byte[] sizeAr = new byte[4];
        inputStream.read(sizeAr);
        // byte to int
        int newSize = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
        //check size
        byte[]imageAr = new byte[newSize];
        int sizerecv = 0;
        int sizerecv2 = 0;
        
        // loads the image data
        byte[] imageAr2 = new byte[newSize];
        int placement = 0;
        while(sizerecv < newSize){
            sizerecv2 = inputStream.read(imageAr);
            for(int i = 0; i < sizerecv2; i++)
            {
                imageAr2[i + placement] = imageAr[i];

            }
            placement = placement + sizerecv2;
            sizerecv = sizerecv + sizerecv2;
        }
        System.out.println("Got converted image data from server, starting conversion to image.");

        //Converting to image
        BufferedImage Finished = null;
        try {
            Finished = ImageIO.read(new ByteArrayInputStream(imageAr2));
        }
        catch(EOFException e) 
        {
        	
        }
        //Save image on desktop
        ImageIO.write(Finished, "jpg", new File("D:\\Desktop\\Finished.jpg"));
        socket.close();

	}

}

