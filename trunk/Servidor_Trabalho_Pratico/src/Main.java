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
	//Dados d;
    static Socket s;
    static Dados d;
	//BufferedReader in;
	//PrintWriter out;
	ObjectOutputStream oout;
	ObjectInputStream inn;
	String user;
	
	
	public Main(List<Socket>sockets,Socket s) throws IOException
	{
		this.sockets=sockets;
		//this.d=d;
		this.s=s;
		//in=new BufferedReader(new InputStreamReader(s.getInputStream()));
		//out=new PrintWriter(s.getOutputStream());
		System.out.println("socket servidor: "+s.getInetAddress()+" : "+s.getLocalPort());
		
	}
	
	
	
	public static void main(String args[]) throws IOException
	{
		List<Socket>sockets = new ArrayList<>();
		//List<String>usersActivos=new ArrayList<>();
		//List<String>paresActivos=new ArrayList<>();
       // Dados d;
		Socket ss;
        Thread t;
        ServerSocket sServer;
		//int i=0;
		
		d=new Dados();
		sServer=new ServerSocket(5001);
		while(true)
		{
			ss=sServer.accept();
			System.out.println("socket servidor: "+ss.getInetAddress()+" : "+ss.getLocalPort());
			t=new Thread(new Main(sockets,ss));
			System.out.println("passou1");
			//t.setDaemon(true);
			t.start();
			//if(i==20)
				//sServer.close();
		}
		
	}
	@Override
	public void run() 
	{
		try {
			oout=new ObjectOutputStream(s.getOutputStream());
			inn = new ObjectInputStream(s.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try 
		{

			while(true)
			{
				//user=in.readLine();
				try {
					d.setLogin((String) inn.readObject());
					user=d.getLogin();
				} catch (ClassNotFoundException e) {
					System.out.println("n recebeu dados : "+e);
					s.close();
					break;
				}
				System.out.println(d.getLogin());
				if(d.getUsersActivos().size()==0)
				{
					System.out.println("OK");
					
					//out.println("Ok");
					//out.flush();
					d.getUsersActivos().add(d.getLogin());
					d.setLogin("OK");
					oout.writeObject(d.getLogin());
					oout.flush();
					sockets.add(s);
					
					/*for(int i=0;i<sockets.size();i++)
					{
						oout=new ObjectOutputStream(sockets.get(i).getOutputStream());
						oout.writeObject(d);
						oout.flush();
					}*/
					
					System.out.println("ToString: "+d.toString());	
					
					break;
				}
				else
				{
					for(int i=0;i<d.getUsersActivos().size();i++)
					{
						if(user.equalsIgnoreCase(d.getUsersActivos().get(i)))
						{
							d.setLogin("Nok");
							oout.writeObject(d.getLogin());
							oout.flush();
							System.out.println("NOK");
							user="Nok";
							break;
						}
					}
					
					if(user!="Nok")
					{
						System.out.println("Vou adicionar um novo user");
						d.getUsersActivos().add(d.getLogin());
						//out.println("OK");
						d.setLogin("OK");
						oout.writeObject(d.getLogin());
						oout.flush();
						sockets.add(s);
						System.out.println("OK22");
							
						for(int i=0;i<sockets.size();i++)
						{
							oout=new ObjectOutputStream(sockets.get(i).getOutputStream());
							System.out.println("ToString: "+d.toString());
							oout.writeObject(d.getParesActivos());
							oout.flush();
							oout.writeObject(d.getUsersActivos());
							oout.flush();
						}
						break;
					}
				}
			}
			
			/*for(int i=0;i<sockets.size();i++)
			{
				oout=new ObjectOutputStream(sockets.get(i).getOutputStream());
				System.out.println("ToString: "+d.toString());
				oout.writeObject(d.getParesActivos());
				oout.flush();
				oout.writeObject(d.getUsersActivos());
				oout.flush();
			}*/
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		//while(true);
		
	}
}
