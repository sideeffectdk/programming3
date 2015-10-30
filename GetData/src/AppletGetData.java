import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.swing.JApplet;

public class AppletGetData extends JApplet
{
	public void init ()
	{
		GetData getData = new GetData();
		try {
			toFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void paint(Graphics g)
	{
		String gXString = String.valueOf(GetData.gX);
		String gYString = String.valueOf(GetData.gY);
		super.paint(g);
		g.drawString("x is: "+ gXString +"   and y is:  "+ gYString, 20, 20);
		repaint();
	}
	public void toFile() throws IOException
	{
			
		File file = new File("D:\Program Files (x86)\Eclipse\Workspace\GetData\test1.txt");
			if (!file.exists())
			{
				file.createNewFile();
			}
			if (file.exists())
			{
				System.out.println("Hello?");
				FileWriter printOut = new FileWriter(file);
				printOut.write("x = " + GetData.gX+ " y = "+ GetData.gY);
				printOut.flush();
				printOut.close();
				
			}
			
		
	}
}
