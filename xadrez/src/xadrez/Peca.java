package xadrez;

public abstract class Peca {
    
    protected String cor; //Branco => letra maiúscula, Preto => letra minúscula
    protected boolean emJogo; //true => em jogo, false => capturada
    
    public Peca(String cor) {
        this.cor = cor;
        emJogo = true;
    }
    
    
    public abstract boolean movimentoValido(int linhaO, char colunaO, int linhaD, char colunaD);
    public abstract String desenha();
    public abstract String caminho(int linhaO, char colunaO, int linhaD, char colunaD);
    
    public String getCor() {
        return cor;
    }
    
    public boolean estaEmJogo() {
        return emJogo;
    }
    
    public void capturar() {
        emJogo = false;
    } //a cor não vai mudar mas o estado sim, então ta aí
    
    
}
