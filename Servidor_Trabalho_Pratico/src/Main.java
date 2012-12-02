import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Main implements Runnable
{
	List<Socket>sockets;
	//List<String>usersActivos;
	//List<String>paresActivos;
	Dados d;
    Socket s;
	BufferedReader in;
	PrintWriter out;
	String user;
	ObjectOutputStream oout;
	ObjectInputStream oin;
	
	public Main(List<Socket>sockets,Socket s,Dados d)
	{
		this.sockets=sockets;
		this.d=d;
		this.s=s;
	}
	
	
	public static void main(String args[]) throws IOException
	{
		List<Socket>sockets = new ArrayList<>();
		//List<String>usersActivos=new ArrayList<>();
		//List<String>paresActivos=new ArrayList<>();
        Dados d;
		Socket s;
        Thread t;
        ServerSocket sServer;
		int i=0;
		
		d=new Dados();
		sServer=new ServerSocket(5002);
		while(true)
		{
			s=sServer.accept();
			t=new Thread(new Main(sockets,s,d));
			//t.setDaemon(true);
			t.start();
			//if(i==20)
				//sServer.close();
		}
		
	}
	@Override
	public void run() 
	{
		
		try 
		{
			in=new BufferedReader(new InputStreamReader(s.getInputStream()));
			out=new PrintWriter(s.getOutputStream());
			//oin=new ObjectInputStream(s.getInputStream());
			//oout=new ObjectOutputStream(s.getOutputStream());
			
			while(true)
			{
				user=in.readLine();
				//user=(String)oin.readObject();
				System.out.println(user);
				if(d.getUsersActivos().size()==0)
				{
					System.out.println("OK");
					out.println("Ok");
					//oout.writeObject("Ok");
					//oout.flush();
					out.flush();
					d.getUsersActivos().add(user);
					sockets.add(s);
					
					for(int i=0;i<sockets.size();i++)
					{
						oout=new ObjectOutputStream(sockets.get(i).getOutputStream());
						oout.writeObject(d);
						oout.flush();
					}
					
					break;
				}
				else
				{
					for(int i=0;i<d.getUsersActivos().size();i++)
					{
						if(user.equalsIgnoreCase(d.getUsersActivos().get(i)))
						{
							out.println("Nok");
							//oout.writeObject("Nok");
							//oout.flush();
							System.out.println("NOK");
							out.flush();
							user="Nok";
							break;
						}
					}
					
					if(user!="Nok")
					{
						System.out.println("Vou adicionar um novo user");
						d.getUsersActivos().add(user);
						out.println("OK");
						//oout.writeObject("Ok");
						//oout.flush();
						out.flush();
						sockets.add(s);
						
						for(int i=0;i<sockets.size();i++)
						{
							oout=new ObjectOutputStream(sockets.get(i).getOutputStream());
							oout.writeObject(d);
							oout.flush();
						}
						break;
					}
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//while(true);
		
	}
}
