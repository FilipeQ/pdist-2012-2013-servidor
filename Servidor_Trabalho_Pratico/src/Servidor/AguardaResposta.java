package Servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class AguardaResposta implements Runnable
{
	ObjectOutputStream out,oout;
	ObjectInputStream in,inn;
	List<Jogador> jogadores;
	String user;
	String escolha;
	Jogador jogConvidado,jogPrincipal;
	Socket s;
	int vez;
	int resultadoJogo;
	Jogo jogo;
	
	
	AguardaResposta(ObjectInputStream in,ObjectOutputStream out,List<Jogador>jogadores,Jogador jogPrincipal)
	{
		this.oout=null;
		this.inn=null;
		this.in=in;
		this.out=out;
		this.jogadores=jogadores;
		this.jogPrincipal=jogPrincipal;
		vez=0;
		jogo=new Jogo();
	}
		
	

	@Override
	public void run() 
	{
		try 
		{
			while (true)
			{
				
				user=(String)in.readObject();//aguarda resposta no proprio socket
				System.out.println("leu tread user:"+user);
				System.out.println("Activo:"+jogPrincipal.getActivo());
						
				if(jogPrincipal.getActivo()==1)
				{
					System.out.println("vou sair da tread:");
					if(!jogPrincipal.getNomeAdver().equalsIgnoreCase(""))
					{
						for(int i=0;i<jogadores.size();i++)
						{
							if(jogPrincipal.getNomeAdver().equalsIgnoreCase(jogadores.get(i).getNome()))
							{
								oout=jogadores.get(i).getOut();
								if(user.equals("1"))
								{
									oout.writeObject(Main.MSG_TIPO_6);//regeita jogo
									jogPrincipal.setActivo(0);
									System.out.println("Activo rejeita jogo:"+jogPrincipal.getActivo());
								}
								else
								{
									oout.writeObject(Main.MSG_TIPO_5);//aceita jogo
									
								}
								break;
							}
							
						}
					}
					break;
				}
				for(int i=0;i<jogadores.size();i++)
				{
					if(user.equalsIgnoreCase(jogadores.get(i).getNome()))
					{
						oout=jogadores.get(i).getOut();
						inn=jogadores.get(i).getIn();
						jogadores.get(i).setActivo(1);
						jogConvidado=jogadores.get(i);
						jogConvidado.setNomeAdver(jogPrincipal.getNome());
						break;
					}
					
				}
				if(oout!=null)
				{
					oout.writeObject(Main.MSG_TIPO_4);
					oout.writeObject(jogPrincipal.getNome());
					oout.flush();
					oout.reset();
					
					
					System.out.println("enviou mensagem po cliente...");
					
				}
				
			}
			System.out.println("Sai");
			System.out.println("Vai começar o jogo");
			
			vez=0;
			while(true)
			{
				if(vez%2==0)//1==X
				{
					jogo=(Jogo)in.readObject();
					
				}
				else//2==O
				{
					jogo=(Jogo)in.readObject();
				}
				
				jogo.setFimJogo(jogo.verificaFimJogo());
				out.writeObject(jogo);
				oout.writeObject(jogo);
				if(jogo.getFimJogo()!=-1)//caso tenha acabado o jogo
					break;
				/*if(resultadoJogo==1)
				{
					out.writeObject(Main.MSG_TIPO_7);
					oout.writeObject(Main.MSG_TIPO_8);
					break;
				}
				else if(resultadoJogo==2)
				{
					out.writeObject(Main.MSG_TIPO_8);
					oout.writeObject(Main.MSG_TIPO_7);
					break;
				}
				else if(resultadoJogo==3)
				{
					out.writeObject(Main.MSG_TIPO_9);
					oout.writeObject(Main.MSG_TIPO_9);
					break;
				}*/
					
			}
				
			
			
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.println("1111 saiu a tread jog"+jogPrincipal.getNome());
			System.out.println("Activo:"+jogPrincipal.getActivo());
		}
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
