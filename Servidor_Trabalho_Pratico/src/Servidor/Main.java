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
	static final String MSG_TIPO_1="ActUsersActivos";
	static final String MSG_TIPO_2="ActParesActivos";
	static final String MSG_TIPO_3="Jogar";
	static final String MSG_TIPO_4="Convidar";
	static final String MSG_TIPO_5="JogoAceite";
	static final String MSG_TIPO_6="JogoRegeitado";
	static final String MSG_TIPO_7="JogoGanho";
	static final String MSG_TIPO_8="JogoPerdido";
	static final String MSG_TIPO_9="JogoEmpatado";
	
	List<Jogador>jogadores;
    Socket s;
    Dados d;
	ObjectOutputStream out,oout;
	ObjectInputStream in;
	
	String user;
	String login;
	
	public Main(List<Jogador>jogadores,Socket s,Dados d) throws IOException
	{
		this.s=s;
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
				if(jogadores.get(i).getActivo()==0)
				{
					oout=jogadores.get(i).getOut();
					System.out.println("ToString: "+d.toString());
					oout.writeObject(MSG_TIPO_1);
					oout.writeObject(d);
					oout.flush();
					oout.reset();
				}
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
					jogadores.add(new Jogador(out,in,user,id,0));
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
	
	
	public void esperaPedido()
	{
		
	}
	
	@Override
	public void run() 
	{
		Jogador jog=null;
		int start=0;
		AguardaResposta ag;
		Thread t = null;
		
		efectuaLogin();
		actualizarUsersActivos();
		
		for(int i=0;i<jogadores.size();i++)
		{
			jog=jogadores.get(i);
			if(user==jog.getNome())
				break;
		}
		ag=new AguardaResposta(in,out,jogadores,jog,d);
		while(true)
		{
			//System.out.println(jog.getActivo());
			if(jog.getActivo()==0 & start==0)
			{
				t=new Thread(ag);
				t.start();
				start=1;//Impede que esteja sempre a entrar aqui
				System.out.println("inicio a tread jog"+jog.getNome()+" ligou a tread");
			}
			else
			{
				if(jog.getActivo()==1)
				{
					if(start==1)
					{
						System.out.println("entrouaqui");
						start=0;
						System.out.println("Fechou a tread jog"+jog.getNome());
					}
					
				}
			}
		}
	}
}
