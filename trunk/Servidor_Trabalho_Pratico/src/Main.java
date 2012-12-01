import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Main implements Runnable
{
	List<Socket>sockets;
	List<String>usersActivos;
	List<String>paresActivos;
    Socket s;
	BufferedReader in;
	PrintWriter out;
	String user;
	
	public Main(List<Socket>sockets,Socket s,List<String>usersActivos,List<String>paresActivos)
	{
		this.sockets=sockets;
		this.usersActivos=usersActivos;
		this.paresActivos=paresActivos;
		this.s=s;
	}
	
	
	public static void main(String args[]) throws IOException
	{
		List<Socket>sockets = new ArrayList<>();
		List<String>usersActivos=new ArrayList<>();
		List<String>paresActivos=new ArrayList<>();
        Socket s;
        Thread t;
        ServerSocket sServer;
		int i=0;
		
		sServer=new ServerSocket(5002);
		while(true)
		{
			s=sServer.accept();
			t=new Thread(new Main(sockets,s,usersActivos,paresActivos));
			//t.setDaemon(true);
			t.start();
			if(i==20)
				sServer.close();
		}
		
	}
	@Override
	public void run() 
	{
		
		try 
		{
			in=new BufferedReader(new InputStreamReader(s.getInputStream()));
			out=new PrintWriter(s.getOutputStream());
			
			
			while(true)
			{
				user=in.readLine();
				System.out.println(user);
				if(usersActivos.size()==0)
				{
					System.out.println("OK");
					out.println("Ok");
					out.flush();
					usersActivos.add(user);
				}
				else
				{
					for(int i=0;i<usersActivos.size();i++)
					{
						if(user.equalsIgnoreCase(usersActivos.get(i)))
						{
							out.println("Nok");
							System.out.println("NOK");
							out.flush();
							user="Nok";
							break;
						}
					}
					
					if(user!="Nok")
					{
						usersActivos.add(user);
						break;
					}
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		while(true);
		
	}
}
