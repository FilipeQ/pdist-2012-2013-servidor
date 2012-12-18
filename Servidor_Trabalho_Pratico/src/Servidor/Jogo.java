package Servidor;

public class Jogo {
	private static final int COLUNAS = 3;//numero de colunas da matriz do galo
    private static final int LINHAS = 3;//numero de linhas da matriz
	private int m[][];//matriz de jogo
	
	
	public Jogo() {
		colocaMatrizInicializada();
	}


	
	public void colocaMatrizInicializada(){
        for(int i=0;i<COLUNAS;i++)
        {
            for(int j=0;j<LINHAS;j++)
            {
                m[i][j]=-1;
            }
        }
    }
	
	public int verificaFimJogo(){
        int valor;
        
        for(int i=0;i<3;i++) //verifica se alguem ganhou nas linhas e devolve o numero do jogador que ganhou
        {
            if(m[i][0]!=-1)
                valor=m[i][0];//recebe o valor da primeira posi��o caso estaja preenchida se estiver vazia(-1) d� ao valor um numero diferente de 1 ou 2 ou -1
            else
                valor=55;
                      
                if(m[i][0]==valor && m[i][1]==valor && m[i][2]==valor)//caso todos coincidam devolve o valor
                {
                    return valor;
                }
            
        }
        
        for(int i=0;i<3;i++) //verifica se alguem ganhou nas colunas e devolve o numero do jogador que ganhou
        {
            if(m[0][i]!=-1)
                valor=m[0][i];//recebe o valor da primeira posi��o caso estaja preenchida se estiver vazia(-1) d� ao valor um numero diferente de 1 ou 2 ou -1
            else
                valor=55;
             
                if(m[0][i]==valor && m[1][i]==valor && m[2][i]==valor)
                {
                    return valor;
                }
            
        }
        
        //vai ser verificado as diagonais
        if(m[0][0]!=-1)//recebe o valor da primeira posi��o caso estaja preenchida se estiver vazia(-1) d� ao valor um numero diferente de 1 ou 2 ou -1
            valor=m[0][0];
        else
            valor=55;
       
        if(m[0][0]==valor && m[1][1]==valor && m[2][2]==valor)//verifica se ganha nas diagonais
        {
            return valor;
        }
        
        if(m[2][0]!=-1)//recebe o valor da primeira posi��o caso estaja preenchida se estiver vazia(-1) d� ao valor um numero diferente de 1 ou 2 ou -1
            valor=m[2][0];
        else
            valor=55;
        
        if(m[2][0]==valor && m[1][1]==valor && m[0][2]==valor)
        {
           
            return valor;
        }
        
        //verifica se o tabuleiro esta todo completo ou nao, se estiver vai devolver o 3 senao devolve -1 e segue o jogo
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(m[i][j]==-1)
                    return -1;// devolve -1 se nimguem ganhou e ainda se pode continuar a jogar
        
        return 3;// devolve 3 se empatarem e o jogo acabou
    }
}