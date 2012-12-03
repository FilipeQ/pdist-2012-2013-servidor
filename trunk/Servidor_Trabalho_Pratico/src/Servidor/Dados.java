package Servidor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Dados implements Serializable
{
	List<String>usersActivos;
	List<String>paresActivos;
	String login;
	
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

	@Override
	public String toString() {
		return "Dados [usersActivos=" + usersActivos + "]";
	}


	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}


	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	
}
