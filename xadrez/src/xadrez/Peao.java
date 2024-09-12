package xadrez;

//add o método pra analisar a validade do ataque do peao

public class Peao extends Peca {
    
    public Peao(String cor) {
        super(cor);
    }
    
    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (emJogo && cor.equals("Branco")) {
            
            int dLinha = linhaD - linhaO;
            int dColuna = colunaD - colunaO;
            
            if (dLinha == 1 && dColuna == 0) return true; //peão só pode se mover um casa para frente 
            
            if (dColuna == 0 && dLinha == 2 && linhaO == 2) return true; //salvo na primeira jogada, ele pode mover duas casas
            //eu deveria fazer o movimento de captura aqui também? 
            
        } else {
            
            int dLinha = linhaD - linhaO;
            int dColuna = colunaD - colunaO;
            
            if(dLinha == -1 && dColuna == 0) return true;
            
            if(dColuna == 0 && dLinha == -2 && linhaO == 7) return true;
        }
        
        return false;
    }
    
    public boolean peaoAtaque(int linhaO, char colunaO, int linhaD, char colunaD) {
        if(linhaD - linhaO == 1 && Math.abs(colunaD - colunaO) == 1) return true; //ele precisa estar indo na diagonal pra frente, n importa dir ou esq
        return false;
    }
    
    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        if(movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
            
            StringBuilder percurso = new StringBuilder();
            percurso.append(linhaO).append(colunaO);
            
            if(linhaD - linhaO == 2) {
                percurso.append(++linhaO).append(colunaO);
                percurso.append(++linhaO).append(colunaO);
            } else {
                percurso.append(++linhaO).append(colunaO);
            }
            
            return percurso.toString();
        }
        
         return "";
    }
    
    @Override
    public String desenha() {
        if (cor.equals("Branco")) return "P";
        return "p";
    }
    
    /*    @Override
    public String desenho() {
        return this.cor.equals("branco") ? "♙" : "♟";
    }*/
    
}
   