package Servidor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Jogador 
{
	ObjectOutputStream out;
	ObjectInputStream in;
	String nome;
	String nomeAdver;
	int id;
	int id_jogo;
	int activo;//0->activo;1->em pedido de convite;2->em jogo
	
	
	
	public Jogador(ObjectOutputStream out,ObjectInputStream in,String nome,int id,int activo)
	{
		this.out=out;
		this.in=in;
		this.nome=nome;
		this.id=id;
		this.activo=activo;
		this.nomeAdver="";
		
	}

	
	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_jogo() {
		return id_jogo;
	}

	public void setId_jogo(int id_jogo) {
		this.id_jogo = id_jogo;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}


	public String getNomeAdver() {
		return nomeAdver;
	}

	public void setNomeAdver(String nomeAdver) {
		this.nomeAdver = nomeAdver;
	}
	
	
}
