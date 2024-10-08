package xadrez;

public class Casa {
    
    private String cor;
    private boolean ocupada;
    private int linha;
    private char coluna;
    private Peca pecaNaCasa;
    
    public Casa(int linha, char coluna) { //toda casa tem uma linha e uma coluna
        
        if(!noLimite(linha, coluna)) throw new IllegalArgumentException("Linha/Coluna fora do intervalo permitido");
        
        this.linha = linha;
        this.coluna = coluna;
        ocupada = false; //inicialmente ela está vazia
        pecaNaCasa = null; //não há peças nela
        
        if(linha%2 == 0 && coluna%2 == 0 || linha%2 != 0 && coluna%2 != 0) {
            cor = "Preto"; //casas com linhas e colunas ímpares ou linhas e colunas pares são pretas
        } else {
            cor = "Branco"; //par com ímpar é branco
        }
    }
    
    public int getLinha() {
        return linha;
    }
    
    public char getColuna() {
        return coluna;
    }
    
    public void ocupar(Peca peca) {
        
        if(peca == null) throw new IllegalArgumentException("Essa peca nao existe");
        
        ocupada = true;
        pecaNaCasa = peca; //se a casa for ser ocupada, diga que peça vai pra lá
    }
    
    public void desocupar() {
        ocupada = false;
        pecaNaCasa = null;
    } //se a peça for sair
    
    public boolean estaOcupada() {
        return ocupada;
    }
    
    public Peca getPeca() {
        if(ocupada) {
            return pecaNaCasa;
        }
        
        return null;
    }
    
    public String desenha() {
        if(ocupada) {
           return pecaNaCasa.desenha() + " ";
        } 
        
        return "[]";
    }
    
    public boolean noLimite(int linha, char coluna) {
        if(linha < 1 || linha > 8) return false; //linha é válido de 1 a 8
        if(coluna < 'a' || coluna > 'h') return false; //coluna de 'a' a 'h'
        return true;
    }
       
}
