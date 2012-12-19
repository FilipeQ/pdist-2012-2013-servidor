package Servidor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Dados implements Serializable
{
	private List<String>usersActivos;
	private List<String>paresActivos;
	private String mensagem;

	
	public Dados()
	{
		usersActivos=new ArrayList<>();
		paresActivos=new ArrayList<>();
	}

	
	public List<String> getUsersActivos() {
		return usersActivos;
	}

	public void setUsersActivos(List<String> usersActivos) {
		this.usersActivos = usersActivos;
	}

	public List<String> getParesActivos() {
		return paresActivos;
	}

	public void setParesActivos(List<String> paresActivos) {
		this.paresActivos = paresActivos;
	}
	
	

	public String getMensagem() {
		return mensagem;
	}


	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}


	@Override
	public String toString() {
		return "Dados [usersActivos=" + usersActivos + ", paresActivos="
				+ paresActivos + "]";
	}



}
