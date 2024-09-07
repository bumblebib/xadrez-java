package xadrez;

public class Tabuleiro {
    
    private Casa[][] casas = new Casa[8][8]; //uma matriz que é o tabuleiro
    
    public Tabuleiro() {
        
        for (int i = 0; i < 8; i++) { //linha
            for (int j = 0; j < 8; j++) { //coluna
                
                char jChar = (char)(j + 97);
                casas[i][j] = new Casa(i + 1, jChar); //cria uma casa nova
                
                switch(i) {
                
                case 0 -> {
                    //se a linha for 1, vamos colocar as peças brancas
                    switch (jChar) {
                        case 'a', 'h' -> casas[i][j].ocupar(new Torre("branco", true));
                        case 'b', 'g' -> casas[i][j].ocupar(new Cavalo("branco", true));
                        case 'c', 'f' -> casas[i][j].ocupar(new Bispo("branco", true));
                        case 'd' -> casas[i][j].ocupar(new Dama("branco", true));
                        case 'e' -> casas[i][j].ocupar(new Rei("branco", true));
                        default -> {
                        }
                    }
                    }


                    
                case 7 -> {
                    //se a linha for 8, vamos botar as peças pretas
                    if(jChar == 'a' || jChar == 'h') casas[i][j].ocupar(new Torre("preto", true));
                    if(jChar == 'b' || jChar == 'g') casas[i][j].ocupar(new Cavalo("preto", true));
                    if(jChar == 'c' || jChar == 'f') casas[i][j].ocupar(new Bispo("preto", true));
                    if(jChar == 'd') casas[i][j].ocupar(new Dama("preto", true));
                    if(jChar == 'e') casas[i][j].ocupar(new Rei("preto", true));
                    }
                    
                case 1 -> //se a linha for 2, vamos botar os peões brancos
                    casas[i][j].ocupar(new Peao("branco", true));
                    
                case 6 -> //se for 7, os peões pretos
                    casas[i][j].ocupar(new Peao("preto", true));
                }
                
            }
            
        }
    } //disposição inicial do tabuleiro
    
    public void desenho() {
        for (int i = 7; i >= 0; i--) { 
            for (int j = 0; j < 8; j++) {
                System.out.print("" + casas[i][j].desenha());
            }
            System.out.println("");
        }
    }
    
    public boolean noLimite(Casa casa) {
        if(casa.getLinha() < 1 || casa.getLinha() > 8) return false; //linha é válido de 1 a 8
        if(casa.getColuna() < 'a' || casa.getColuna() > 'h') return false; //coluna de 'a' a 'h'
        return true;
    }

    public void colocarPeca(Peca peca, Casa casa){
        casa.ocupar(peca);
    }

    public void removerPeca(Casa casa){
        casa.desocupar();
    }
    
    public Casa getPosicaoRei(String cor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casa casa = casas[i][j];
                Peca peca = casa.getPecaNaCasa();
                if (peca instanceof Rei && peca.getCor().equals(cor)) {
                    return casa;
                }
            }
        }
        return null; // Return null if the king is not found
    }

    public Casa getPosicaoPeca(Peca peca){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casa casa = casas[i][j];
                if (casa.getPecaNaCasa() == peca) {
                    return casa;
                }
            }
        }
        return null; // Return null if the piece is not found
    }


    public boolean estaEmCheque(String cor, Casa posicaoRei) {
        
        // Verificar se alguma peça adversária pode atacar o rei
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casa casa = casas[i][j];
                Peca peca = casa.getPecaNaCasa();
                
                // Verificar se a peça é do adversário
                if (peca != null && !peca.getCor().equals(cor)) {
                    // Verificar se a peça pode atacar a posição do rei
                    if (peca.movimentoValido(casa.getLinha(), casa.getColuna(), posicaoRei.getLinha(), posicaoRei.getColuna())) {
                        return true; // O rei está em cheque
                    }
                }
            }
        }
        
        return false; // O rei não está em cheque
    }

    public Peca getPeca(Casa posicao){
        return posicao.getPecaNaCasa();
    }

    public Casa getCasa(Casa posicao){
        return casas[posicao.getLinha() - 1][posicao.getColuna() - 97];
    }

    public String getCaminhoPeca(Casa posicaoInicial, Casa posicaoFinal){
        Peca peca = posicaoInicial.getPecaNaCasa();
        if (peca != null) {
            int linhaO = posicaoInicial.getLinha();
            char colunaO = posicaoInicial.getColuna();
            int linhaD = posicaoFinal.getLinha();
            char colunaD = posicaoFinal.getColuna();
            return peca.caminho(linhaO, colunaO, linhaD, colunaD);
        }
        return null;
    }
    
}
