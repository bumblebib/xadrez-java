package xadrez;

public class Cavalo extends Peca {
    
    public Cavalo(String cor) {
        super(cor);
    }
    
    @Override
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        
        if(!posicoesValidas(linhaO, colunaO, linhaD, colunaD)) {
            throw new IllegalArgumentException("Linha/Coluna fora do intervalo permitido");
        }
        
        if(emJogo) {
            
            int dLinha = Math.abs(linhaO - linhaD);
            int dColuna = Math.abs(colunaO - colunaD);
            
            if (dLinha == 2 && dColuna == 1 || dLinha == 1 && dColuna == 2) return true;
            return false;
        } // cavalo se move em L, então obrigatoriamente ele vai se mover 2 casas em uma direção e mais 1 na outra
        
        return false;
        
    }
    
    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        if(movimentoValido(linhaO, colunaO, linhaD, colunaD)) { 
            
            StringBuilder percurso = new StringBuilder();
            boolean flag = false;
            
            percurso.append(linhaO).append(colunaO);
            
            if (linhaD - linhaO == -1) {
                percurso.append(--linhaO).append(colunaO); 
                flag = true;
            } else if (linhaD - linhaO == 1) {
                percurso.append(++linhaO).append(colunaO);
                flag = true;
            } else if (colunaD - colunaO == -1) {
                percurso.append(linhaO).append(--colunaO);
            } else if (colunaD - colunaO == 1) {
                percurso.append(linhaO).append(++colunaO);
            }
            
            if(flag) {
                if(colunaO < colunaD) {
                    percurso.append(linhaO).append(++colunaO);
                    percurso.append(linhaO).append(++colunaO);
                } else {
                    percurso.append(linhaO).append(--colunaO);
                    percurso.append(linhaO).append(--colunaO);
                }
                
            } else {
                if(linhaO < linhaD) {
                    percurso.append(++linhaO).append(colunaO);
                    percurso.append(++linhaO).append(colunaO);
                } else {
                    percurso.append(--linhaO).append(colunaO);
                    percurso.append(--linhaO).append(colunaO);
                }
            } 
            
            return percurso.toString();
        } 
        
        return "";
    }
    
    @Override 
    public String desenha() {
        if (cor.equals("Branco")) return "C";
        else return "c";
    }
    
    private boolean posicoesValidas(int linhaO, char colunaO, int linhaD, char colunaD) {
        return (linhaO >= 1 && linhaO <= 8 && linhaD >= 1 && linhaD <= 8) &&
        (colunaO >= 'a' && colunaO <= 'h' && colunaD >= 'a' && colunaD <= 'h');
    } // verifica se as linhas/colunas estao no intervalo valido
    
}
