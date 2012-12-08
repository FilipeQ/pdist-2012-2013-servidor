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
	
	List<Jogador>jogadores;
    Socket s;
    Dados d;
	ObjectOutputStream out,oout;
	ObjectInputStream in;
	
	String user;
	String login;
	
	public Main(List<Jogador>jogadores,Socket s,Dados d) throws IOException
	{
		this.jogadores=jogadores;
		out=new ObjectOutputStream(s.getOutputStream());
		in=new ObjectInputStream(s.getInputStream());
		this.d=d;
		login="Ok";
	}
	
	public static void main(String args[])
	{
		List<Jogador>jogadores=new ArrayList<>();
        Dados d;
		Socket s;
        Thread t;
        ServerSocket sServer;
        
        try
        {
	        sServer=new ServerSocket(PORT);
	        d=new Dados();
			while(true)
			{
				s=sServer.accept();
				t=new Thread(new Main(jogadores,s,d));
				t.start();
			}
        }
        catch(IOException e){System.out.println(e);}
	}

	public void actualizarUsersActivos()
	{
		try
		{
			d.setMensagem(MSG_TIPO_1);
			for(int i=0;i<jogadores.size();i++)
			{
				oout=jogadores.get(i).getOut();
				System.out.println("ToString: "+d.toString());
				oout.writeObject(d);
				oout.flush();
				oout.reset();
			}
		}
		catch(IOException e){System.out.println(e);}
	}
	
	public void efectuaLogin()
	{
		try
		{
			while(true)
			{
				user=(String) in.readObject();
	
				if(d.getUsersActivos().size()!=0)
				{
					for(int i=0;i<jogadores.size();i++)
					{
						if(user.equalsIgnoreCase(jogadores.get(i).getNome()))
						{
							login="Nok";
							out.writeObject(login);
							out.flush();
							System.out.println("NOK");
							break;
						}
					}
				}
	
				if(login!="Nok")
				{
					jogadores.add(new Jogador(out,in,user,id));
					id++;
					d.getUsersActivos().add(user);
					out.writeObject(login);
					out.flush();

					System.out.println("OK");	
					break;
				}
			}
		}
		catch(ClassNotFoundException e){System.out.println(e);}
		catch(IOException e){System.out.println(e);}
	}
	
	@Override
	public void run() 
	{
		efectuaLogin();
		actualizarUsersActivos();
		while(true);
	}
}
