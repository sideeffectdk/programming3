
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
	
public class somethingsomething
{

		private static int n = 1;
		static String last;
		
		/*public static void main(String[] args) throws IOException 
		{
			
		}*/
		public void run ()
		{
			
		}
		
		
		public static void ReadTest () throws IOException
		{
			
			//for (int n = 1; n<123; n++)
			//{
				BufferedReader input = new BufferedReader(new FileReader("D:\\Desktop\\Medialogy\\P3\\DataFiles\\Test"+n+".txt"));
				String line;
				
				while ((line = input.readLine()) != null) 
			    {
			        last = line;
			        String[] PageData = last.split(";");
			      
			        //fra 0 til 6
			        double FirstCheck = Double.parseDouble (PageData[4]);
			        if (FirstCheck != 0)
			        {
			        	String PageLocation = PageData[2];
			        	if (PageLocation.equals("http://testserver3.weebly.com/"))
			        	{
			        		
			        		  
			        	
			        		
			        	}
			        }
			    }
			//}
		}
}
