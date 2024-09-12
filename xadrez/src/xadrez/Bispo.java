package xadrez;

public class Bispo extends Peca {
    
    public Bispo(String cor) {
        super(cor);
    }
    
    @Override 
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        
        if(!posicoesValidas(linhaO, colunaO, linhaD, colunaD)) {
            throw new IllegalArgumentException("Linha/Coluna fora do intervalo permitido");
        } 
        
        if (emJogo) {
            
            int dLinha = Math.abs(linhaO - linhaD);
            int dColuna = Math.abs(colunaO - colunaD);
            
            if(dLinha != dColuna) return false; //o bispo só pode se mover na diagonal, então a única maneira de se mover
            //é se a distancia andada na linha e na coluna for igual
            return true;
        }
        
        return false;
    }
    
    @Override 
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (movimentoValido(linhaO, colunaO, linhaD, colunaD)) { //já vai fzr a verificação da exceção em movimentoValido
            
            StringBuilder percurso = new StringBuilder();
            percurso.append(linhaO).append(colunaO);
            
            while(linhaO != linhaD || colunaO != colunaD) {
                if(linhaO < linhaD) {
                    percurso.append(++linhaO);
                } else {
                    percurso.append(--linhaO);
                }
                
                if(colunaO < colunaD) {
                    percurso.append(++colunaO);
                } else {
                    percurso.append(--colunaO);
                }
            }
            
            return percurso.toString();
        }
        
        return "";
    }
    
    @Override
    public String desenha() {
        if (cor.equals("Branco")) return "B";
        return "b";
    }
    
    /*     @Override
    public String desenho() {
       return this.cor.equals("branco") ? "♗" : "♝";
    } */
    
    private boolean posicoesValidas(int linhaO, char colunaO, int linhaD, char colunaD) {
        return (linhaO >= 1 && linhaO <= 8 && linhaD >= 1 && linhaD <= 8) &&
        (colunaO >= 'a' && colunaO <= 'h' && colunaD >= 'a' && colunaD <= 'h');
    } //verifica se as linhas/colunas estao no intervalo valido
   
}

