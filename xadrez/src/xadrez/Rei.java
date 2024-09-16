package xadrez;

public class Rei extends Peca {
    
    public Rei(String cor) {
        super(cor);
    }
    
    @Override 
    public boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD) {
        
        if(!posicoesValidas(linhaO, colunaO, linhaD, colunaD)) {
            throw new IllegalArgumentException("Linha/Coluna fora do intervalo permitido");
        }
        
        if (emJogo) { // se a peça ainda está no jogo
            
            int dLinha = Math.abs(linhaO - linhaD);
            int dColuna = Math.abs(colunaO - colunaD); // Math.abs deixa as contas em valor absoluto
            
            if(dLinha > 1 || dColuna > 1) return false;
            // se a diferença entre a linha/coluna de origem e de destino for maior que um , então o movimento do Rei é inválido
            
            return true;
        } 
        return false;
    }
    
    @Override
    public String caminho(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (movimentoValido(linhaO, colunaO, linhaD, colunaD)) {
            return "" + linhaO + colunaO + linhaD + colunaD;
        } 
        
        return "";
    }
    
    @Override 
    public String desenha() {
        if (cor.equals("Branco")) return "R"; // se a cor for branca, retorna maiúsculo
        return "r"; // se não, minúsculo
    }

    
    private boolean posicoesValidas(int linhaO, char colunaO, int linhaD, char colunaD) {
        return (linhaO >= 1 && linhaO <= 8 && linhaD >= 1 && linhaD <= 8) &&
        (colunaO >= 'a' && colunaO <= 'h' && colunaD >= 'a' && colunaD <= 'h');
    } // verifica se as linhas/colunas estao no intervalo valido
    

}
