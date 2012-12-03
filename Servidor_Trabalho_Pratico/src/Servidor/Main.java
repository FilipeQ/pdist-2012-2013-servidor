package Servidor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class Main implements Runnable
{
	static int id=0;
	static final int PORT=5001;
	final String MSG_TIPO_1="ActUsersActivos";
	final String MSG_TIPO_2="ActParesActivos";
	final String MSG_TIPO_3="Jogar";
	
	
	
	List<Socket>sockets;
	List<Jogador>jogadores;
    Socket s;
    Dados d;
	ObjectOutputStream oout;
	ObjectInputStream inn;
	String user;
	
	
	public Main(List<Jogador>jogadores,Socket s,Dados d) throws IOException
	{
		this.jogadores=jogadores;
		this.s=s;
		this.d=d;
		//System.out.println("socket servidor: "+s.getInetAddress()+" : "+s.getLocalPort());
		
	}

	public static void main(String args[]) throws IOException
	{
		//List<Socket>sockets = new ArrayList<>();
		List<Jogador>jogadores=new ArrayList<>();
        Dados d;
		Socket ss;
        Thread t;
        ServerSocket sServer;
		
		d=new Dados();
		sServer=new ServerSocket(PORT);
		while(true)
		{
			ss=sServer.accept();
			//System.out.println("socket servidor: "+ss.getInetAddress()+" : "+ss.getLocalPort());
			t=new Thread(new Main(jogadores,ss,d));
			//System.out.println("passou1");
			//t.setDaemon(true);
			t.start();
		}
		
	}
	
	public void efectuaLogin()
	{
		try {
			oout=new ObjectOutputStream(s.getOutputStream());
			inn = new ObjectInputStream(s.getInputStream());
		
		
			while(true)
			{
				d.setLogin((String) inn.readObject());
				user=d.getLogin();
			

				//System.out.println(d.getLogin());
				if(d.getUsersActivos().size()==0)
				{
					//System.out.println("OK");
					d.getUsersActivos().add(d.getLogin());
					d.setLogin("OK");
					oout.writeObject(d.getLogin());
					oout.flush();
					jogadores.add(new Jogador(s,user,id));
					id++;	
					//System.out.println("ToString: "+d.toString());	
					break;
				}
				else
				{
					//for(int i=0;i<d.getUsersActivos().size();i++)
					for(int i=0;i<jogadores.size();i++)
					{
						if(user.equalsIgnoreCase(jogadores.get(i).getNome()))
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
						//System.out.println("Vou adicionar um novo user");
						d.getUsersActivos().add(d.getLogin());
						d.setLogin("OK");
						oout.writeObject(d.getLogin());
						oout.flush();
						jogadores.add(new Jogador(s,user,id));
						id++;
						//System.out.println("OK22");	
						break;
					}
				}
			}
		} 
		catch (IOException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			/*System.out.println("n recebeu dados : "+e);
			s.close();
			break;*/
			e.printStackTrace();
		}
	}

	public void actualizarUsersActivos()
	{
		try 
		{
			for(int i=0;i<jogadores.size();i++)
			{
				oout=new ObjectOutputStream(jogadores.get(i).getS().getOutputStream());
				System.out.println("ToString: "+d.toString());
				/*oout.writeObject(d.getParesActivos());
				oout.flush();
				oout.writeObject(d.getUsersActivos());
				oout.flush();*/
				//d.setLogin(user);
				d.setMensagem(MSG_TIPO_1);
				oout.writeObject(d);
				oout.flush();
			}
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() 
	{
		efectuaLogin();
		actualizarUsersActivos();
	}
}
