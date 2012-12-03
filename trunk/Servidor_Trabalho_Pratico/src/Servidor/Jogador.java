package Servidor;

import java.net.Socket;

public class Jogador 
{
	Socket s;
	String nome;
	int id;
	int id_jogo;
	
	public Jogador(Socket s,String nome,int id)
	{
		this.s=s;
		this.nome=nome;
		this.id=id;
	}

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
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
	
	
	
}
