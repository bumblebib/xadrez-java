package xadrez;
import java.util.ArrayList;

//xeque só pra jogada efetuada agr
//a prof falou pra fzr xeque mate sem passar pelo tabuleiro, pegando as peças do jogador direto
//mas n tem como, a peça n sabe onde esta pra eu usar o movimentoValido

public class Jogada {
    
    private Tabuleiro tabuleiro; //jogada conhece tabuleiro, o jogador que ta fznd a jogada
    private Jogador jogador; //e constroi o caminho
    private Caminho caminho;
    private int linhaO;
    private int linhaD;
    private char colunaO;
    private char colunaD;
    
    public Jogada(int linhaO, char colunaO, int linhaD, char colunaD, Jogador jog, Tabuleiro tab) {
        
        if(tab == null) throw new IllegalArgumentException("Esse tabuleiro nao existe");    
        if(jog == null) throw new IllegalArgumentException("Esse jogador nao existe");
        
        tabuleiro =  tab;
        jogador = jog;
        
        /* if(!tab.noLimite(linhaO, colunaO)) {
            throw new IllegalArgumentException("Linha e/ou Coluna de Origem invalida");
        }
        if(!tab.noLimite(linhaD, colunaD)) {
            throw new IllegalArgumentException("Linha e/ou Coluna de Destino invalida");
        } */ //tinha colocado isso mas vai ficar dando mensagem de erro qnd for criar as jogadas hipoteticas 
        //pra testar o xeque-mate e a verificação já vai acontecer em jogadaValida de qlqr maneira
        
        this.linhaO = linhaO;
        this.linhaD = linhaD;
        this.colunaO = colunaO;
        this.colunaD = colunaD;
        
        try {
            caminho = new Caminho(tabuleiro.getCasa(linhaO, colunaO), tabuleiro.getCasa(linhaD, colunaD));
        }catch(IllegalArgumentException exc) {
            System.out.println("Nao foi possivel criar caminho: " + exc.getMessage());
        }
        
    }
    
    public boolean ehValida() {
        
        try {
            
            //se uma das casas esta fora dos limites do tabuleiro, invalido
            if(!tabuleiro.noLimite(linhaO, colunaO) || !tabuleiro.noLimite(linhaD, colunaD)) return false;
        
            //se a casa inicial nao estiver ocupada, que peça o abençoado vai mexer
            if(!caminho.getCasaInicial().estaOcupada()) return false; 
        
            //se a peça da casa incial nao for do jogador aqui
            if(!jogador.ehDoJogador(caminho.getCasaInicial().getPeca())) return false;
        
            //se a peça na última casa for do próprio jogador (tu quer se capturar eh)
            if(caminho.getCasaFinal().estaOcupada() && jogador.ehDoJogador(caminho.getCasaFinal().getPeca())) return false;
        
            //se a peça em questão for um peão, e a casa que ele quer ir esta ocupada, temos que fzr uma verificação diferente
            if(caminho.getCasaInicial().getPeca() instanceof Peao && caminho.getCasaFinal().estaOcupada()) {
                Peao peao = (Peao) caminho.getCasaInicial().getPeca();
                if(peao.peaoAtaque(linhaO, colunaO, linhaD, colunaD) && !jogador.ehDoJogador(caminho.getCasaFinal().getPeca())) return true;
                return false; 
            } 
        
            //por fim, se a peça nao pode fzr esse movimento, inválido
            if(!caminho.getCasaInicial().getPeca().movimentoValido(linhaO, colunaO, linhaD, colunaD)) return false;
            
            criarCaminho(); //se estiver tudo ok, criamos caminho
            //se o caminho nao esta livre e a peça em questão não é o cavalo, então esse movimento eh invalido
            if(!(caminho.getCasaInicial().getPeca() instanceof Cavalo) && !caminho.estaLivre()) return false;
        
        } catch(IllegalArgumentException exc) {
            System.out.println("Erro ao verificar se a Jogada e valida: " + exc.getMessage());
        }
        
            return true; //se passar por tudo isso, eh valido
        
    }
    
    public boolean ehXeque(Jogador oponente) {
        
        if(oponente == null) throw new IllegalArgumentException("Esse Jogador nao existe");
        
        try {
            
            Casa casaRei = tabuleiro.acharRei(oponente.getCor()); //achando o rei oponente
            
            Jogada simulacao = new Jogada(linhaD, colunaD, casaRei.getLinha(), casaRei.getColuna(), jogador, tabuleiro);
            
            if(simulacao.ehValida()) return true;
            
        } catch(IllegalArgumentException exc) {
            System.out.println("Erro ao verificar xeque: " + exc.getMessage());
        }
        
        return false;
    }
    
  /*  public boolean ehXeque(Jogador oponente) {
        
        if(oponente == null) throw new IllegalArgumentException("Esse jogador nao existe");
        
        try {
        
            Casa casaRei = tabuleiro.acharRei(oponente.getCor()); //achando o rei do oponente
        
            if(caminho.getCasaInicial().getPeca() instanceof Peao) { //se a peça movida é um peao
                Peao peao = (Peao) caminho.getCasaInicial().getPeca();
                if(peao.peaoAtaque(linhaD, colunaD, casaRei.getLinha(), casaRei.getColuna())) return true;
                return false;
            } //se nao for peao, faz a verificação geral
        
            if(caminho.getCasaInicial().getPeca().movimentoValido(linhaD, colunaD, casaRei.getLinha(), casaRei.getColuna())) {
                return true; 
            } //se a peça movida, pode alcançar o rei do oponente da posição pra onde foi movida, então é xeque
        
        } catch(IllegalArgumentException exc) {
            System.out.println("Erro ao verficar xeque: " + exc.getMessage());
        }
        
        return false;
        
    } */
    
    public boolean ehXequeMate(Jogador oponente) {
        
        if(oponente == null) throw new IllegalArgumentException("Esse jogador nao existe");
        
        try {
        
            Casa casaRei = tabuleiro.acharRei(oponente.getCor()); //achando o rei do oponente novamente
        
            int linhaRei = casaRei.getLinha();
            char colunaRei = casaRei.getColuna();
        
            ArrayList<Jogada> testeRei = new ArrayList<>();
            testeRei.add(new Jogada(linhaRei, colunaRei, linhaRei + 1, colunaRei, oponente, tabuleiro));
            testeRei.add(new Jogada(linhaRei, colunaRei, linhaRei, (char)(colunaRei + 1), oponente, tabuleiro));
            testeRei.add(new Jogada(linhaRei, colunaRei, linhaRei - 1, colunaRei, oponente, tabuleiro));
            testeRei.add(new Jogada(linhaRei, colunaRei, linhaRei, (char)(colunaRei - 1), oponente, tabuleiro));
            testeRei.add(new Jogada(linhaRei, colunaRei, linhaRei + 1, (char)(colunaRei + 1), oponente, tabuleiro));
            testeRei.add(new Jogada(linhaRei, colunaRei, linhaRei - 1, (char)(colunaRei - 1), oponente, tabuleiro));
            testeRei.add(new Jogada(linhaRei, colunaRei, linhaRei + 1, (char)(colunaRei - 1), oponente, tabuleiro));
            testeRei.add(new Jogada(linhaRei, colunaRei, linhaRei - 1, (char)(colunaRei + 1), oponente, tabuleiro));
            //criando as 8 jogadas possiveis do rei oponente
        
            for(Jogada j: testeRei) { //para cada jogada possivel do rei oponente
                if(j.ehValida() && !j.procurandoXequeMate()) { //se ela for, ao msm tempo, valida e nao houver nenhuma peça atacando a posição dela
                    return false; //nao é xeque mate
                }
            }
            
        }catch(IllegalArgumentException exc) {
            System.out.println("Erro ao verificar xeque-mate: " + exc.getMessage());
        }
        
        return true; //se passar por todas as jogadas sem achar uma que seja valida e que esteja livre de ataque, xeque mate
        
    }
    
    private void criarCaminho() {
        
        try {
        
            String percurso = tabuleiro.getCasa(linhaO, colunaO).getPeca().caminho(linhaO, colunaO, linhaD, colunaD); //vms usar o método de caminho que existe em peça
            
            if(percurso.length() > 4) { //se o tam de percurso for 4, ent só tem duas casas (final e inicial) nao há caminho pra fzr
                for(int i = 2; i < percurso.length() - 2; i += 2) {
                    char linha = percurso.charAt(i);
                    char coluna = percurso.charAt(i+1);
                    caminho.adicionarCasa(tabuleiro.getCasa((int)(linha - 49 + 1), coluna));
                }
            }
            
        }catch(IllegalArgumentException exc) {
            System.out.println("Erro ao criar caminho: " + exc.getMessage());
        }
    }
    
    
    private boolean procurandoXequeMate() {

        try {
        
            for(int linha = 1; linha <= 8; linha++) { //passando pelo tabuleiro
                for(char coluna = 'a'; coluna <= 'h'; coluna++) {
                    
                    Casa casaAtual = tabuleiro.getCasa(linha, coluna);
                    
                    if(casaAtual.estaOcupada() && jogador.ehDoJogador(casaAtual.getPeca())) { //qnd achamos uma peça do nosso jogador
                        if(casaAtual.getPeca() instanceof Peao) { //se for um peao
                            Peao peao = (Peao) casaAtual.getPeca(); //faz o cast
                            
                            if (peao.peaoAtaque(linha, coluna, linhaD, colunaD)) return true;
                            //se o peao puder atacar a nova casa do rei, esta em xeque
                        } else {
                        
                            if(casaAtual.getPeca().movimentoValido(linha, coluna, linhaD, colunaD)) return true;
                            //se a peça aleatória puder atacar a nova casa do rei, esta em xeque pt2
                        }
                    }
                }
            }
         
        } catch(IllegalArgumentException exc) {
            System.out.println("Erro ao verificar xeque-mate: " + exc.getMessage());
        }
        
        return false;
    }
    
    public String escrever() {
        return "<" + linhaO + colunaO + linhaD + colunaD + ">";
    }
    
}

//meu jesus nem sei mais o que ta acontecendo nesse codigo
//real antigo eu, tbm n sei