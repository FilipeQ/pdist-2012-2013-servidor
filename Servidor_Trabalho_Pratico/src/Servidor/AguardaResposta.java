package Servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class AguardaResposta implements Runnable
{
	ObjectOutputStream oout;
	ObjectInputStream in,inn;
	List<Jogador> jogadores;
	String user;
	String escolha;
	Jogador jogConvidado,jogPrincipal;
	
	AguardaResposta(ObjectInputStream in,List<Jogador>jogadores,Jogador jogPrincipal)
	{
		this.oout=null;
		this.in=null;
		//this.oout=oout;
		this.in=in;
		this.jogadores=jogadores;
		this.jogPrincipal=jogPrincipal;
	}
		
	

	@Override
	public void run() 
	{
		try 
		{
			while(true)
			{
				
				user=(String)in.readObject();
				
				for(int i=0;i<jogadores.size();i++)
				{
					if(user.equalsIgnoreCase(jogadores.get(i).getNome()))
					{
						oout=jogadores.get(i).getOut();
						inn=jogadores.get(i).getIn();
						jogadores.get(i).setActivo(1);
						jogConvidado=jogadores.get(i);
						break;
					}
					
				}
				if(oout!=null)
				{
					oout.writeObject(Main.MSG_TIPO_4);
					oout.writeObject(jogPrincipal.getNome());
					oout.flush();
					oout.reset();
					
					
					System.out.println("Esperar confirmação...");
					escolha=(String)inn.readObject();
					
					if(escolha.equals("1"))
						jogConvidado.setActivo(0);
				}
				
			}
		}
		catch (ClassNotFoundException e) {System.out.println(e);}
		catch (IOException e) {System.out.println(e);}
	}



	
	public ObjectOutputStream getOout() {
		return oout;
	}



	public void setOout(ObjectOutputStream oout) {
		this.oout = oout;
	}



	public ObjectInputStream getIn() {
		return in;
	}



	public void setIn(ObjectInputStream in) {
		this.in = in;
	}



	public List<Jogador> getJogadores() {
		return jogadores;
	}



	public void setJogadores(List<Jogador> jogadores) {
		this.jogadores = jogadores;
	}



	public String getUser() {
		return user;
	}



	public void setUser(String user) {
		this.user = user;
	}


}
