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
	Dados d;
	String parActivo;
	
	AguardaResposta(ObjectInputStream in,ObjectOutputStream out,List<Jogador>jogadores,Jogador jogPrincipal,Dados d)
	{
		this.oout=null;
		this.inn=null;
		this.in=in;
		this.out=out;
		this.jogadores=jogadores;
		this.jogPrincipal=jogPrincipal;
		vez=0;
		this.d=d;
		jogo=new Jogo(this.d);

	}
		
	

	@Override
	public void run() 
	{
		int salto=0;
		try 
		{
			while (true)
			{
				System.out.println("entra no topo");
				user=(String)in.readObject();//aguarda resposta no proprio socket
				System.out.println("leu tread user:"+user);
				System.out.println("Activo: "+jogPrincipal.getActivo());
				
				
				
				if(jogPrincipal.getActivo()==1)
				{
					
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
									oout.flush();
									oout.reset();

								
								}
								salto=1;
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
					break;
				}
				
			}
			if(salto==0)
			{
				System.out.println("Sai");
				System.out.println("Vai começar o jogo");
				jogPrincipal.setActivo(1);
				jogConvidado.setActivo(1);
				vez=0;
				
				parActivo=jogPrincipal.getNome()+" VS "+jogConvidado.getNome();
				d.getParesActivos().add(parActivo);
				d.getUsersActivos().remove(jogPrincipal.getNome());
				d.getUsersActivos().remove(jogConvidado.getNome());
				

				
				System.out.println("ToString: "+d.toString());
				
				try
				{
					//d.setMensagem(MSG_TIPO_1);
					for(int i=0;i<jogadores.size();i++)
					{
						if(jogadores.get(i).getActivo()==0)
						{
							oout=jogadores.get(i).getOut();
							System.out.println("ToString: "+d.toString());
							oout.writeObject(Main.MSG_TIPO_1);
							oout.writeObject(d);
							oout.flush();
							oout.reset();
						}
					}
				}
				catch(IOException e){System.out.println(e);}
				
				oout=jogConvidado.getOut();
				
				while(true)
				{
					System.out.println("entra while");
					
					if(vez%2==0)//1==X
					{
					
						System.out.println("espera no vez%2");
						jogo=(Jogo)in.readObject();
						jogo.setD(d);
						jogo.setFimJogo(jogo.verificaFimJogo());
						if(jogo.getFimJogo()==-1)//actualiza o adversario caso n tenha sido o fim do jogo
						{
							oout.writeObject(jogo);
							oout.flush();
							oout.reset();
						}
					}
					else//2==O
					{
						jogo=(Jogo)inn.readObject();
						jogo.setD(d);
						jogo.setFimJogo(jogo.verificaFimJogo());//vai verificar se o jogo chegou ao fim
						if(jogo.getFimJogo()==-1)
						{
							out.writeObject(jogo);
							out.flush();
							out.reset();
						}
					}
					vez++;
					//jogo.setFimJogo(jogo.verificaFimJogo());
					//out.writeObject(jogo);
					//oout.writeObject(jogo);
					if(jogo.getFimJogo()!=-1)//caso tenha acabado o jogo
					{
						out.writeObject(jogo);
						out.flush();
						out.reset();
						oout.writeObject(jogo);
						oout.flush();
						oout.reset();
						jogPrincipal.setActivo(0);
						jogConvidado.setActivo(0);
						
						d.getParesActivos().remove(parActivo);
						d.getUsersActivos().add(jogPrincipal.getNome());
						d.getUsersActivos().add(jogConvidado.getNome());
						
						
						try
						{
							//d.setMensagem(MSG_TIPO_1);
							for(int i=0;i<jogadores.size();i++)
							{
								if(jogadores.get(i).getActivo()==0)
								{
									oout=jogadores.get(i).getOut();
									System.out.println("ToString: "+d.toString());
									oout.writeObject(Main.MSG_TIPO_1);
									oout.writeObject(d);
									oout.flush();
									oout.reset();
								}
							}
						}
						catch(IOException e){System.out.println(e);}
						
						
						
						/*out.writeObject(Main.MSG_TIPO_1);
						out.writeObject(d);
						out.flush();
						
						oout.writeObject(Main.MSG_TIPO_1);
						oout.writeObject(d);
						oout.flush();*/
						
						
						break;
					}
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
			System.out.println("1111 saiu a tread jog "+jogPrincipal.getNome());
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
