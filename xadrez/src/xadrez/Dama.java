package xadrez;

public class Dama extends Peca {
    
    public Dama(String cor) {
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
            
            if(dLinha != dColuna && linhaO != linhaD && colunaO != colunaD) return false; // a dama pode se mover quantas casas quiser, pra onde quiser
            // mas é preciso checar a diagonal
            
            return true;
            
        }
        return false;
    }
    
    @Override 
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        if(movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
            
            StringBuilder percurso = new StringBuilder(); // concatenar String
            percurso.append(linhaO).append(colunaO); // colocando a origem
            
            while(linhaO != linhaD || colunaO != colunaD) { // enquanto não chegar na posição final tanto na linha qaunto na coluna
                if (linhaO < linhaD) {
                    percurso.append(++linhaO); // se a origem for menor que o destino, incrementa até se igualarem
                } else if (linhaO > linhaD) {
                    percurso.append(--linhaO); // se a origem for maior, diminui até ficar igual
                } else {
                    percurso.append(linhaO); // se já forem iguais, repete
                }
                
                if (colunaO < colunaD) {
                    percurso.append(++colunaO); // mesma lógica pra coluna
                } else if (colunaO > colunaD) {
                    percurso.append(--colunaO);
                } else {
                    percurso.append(colunaO);
                }
                
                
            }
            
            return percurso.toString(); // transforma de volta pra String normal
        }
        
        return "";
    }
    
    @Override 
    public String desenha() {
        if (cor.equals("Branco")) return "D";
        return "d";
    }
    
    private boolean posicoesValidas(int linhaO, char colunaO, int linhaD, char colunaD) {
        return (linhaO >= 1 && linhaO <= 8 && linhaD >= 1 && linhaD <= 8) &&
        (colunaO >= 'a' && colunaO <= 'h' && colunaD >= 'a' && colunaD <= 'h');
    } // verifica se as linhas/colunas estao no intervalo valido

    
}
