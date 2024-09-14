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
        
        this.linhaO = linhaO;
        this.linhaD = linhaD;
        this.colunaO = colunaO;
        this.colunaD = colunaD;
        
    }
    
    public boolean ehValida() {
        
        try {
            
            //se uma das casas esta fora dos limites do tabuleiro, invalido
            if(!tabuleiro.noLimite(linhaO, colunaO) || !tabuleiro.noLimite(linhaD, colunaD)) return false;
        
            //se a casa inicial nao estiver ocupada, que peça o abençoado vai mexer
            if(!tabuleiro.getCasa(linhaO, colunaO).estaOcupada()) return false; 
        
            //se a peça da casa incial nao for do jogador aqui
            if(!jogador.ehDoJogador(tabuleiro.getCasa(linhaO, colunaO).getPeca())) return false;
        
            //se a peça na última casa for do próprio jogador (tu quer se capturar eh)
            if(tabuleiro.getCasa(linhaD, colunaD).estaOcupada() && jogador.ehDoJogador(tabuleiro.getCasa(linhaD, colunaD).getPeca())) return false;
        
            //se a peça em questão for um peão, e a casa que ele quer ir esta ocupada, temos que fzr uma verificação diferente
            if(tabuleiro.getCasa(linhaO, colunaO).getPeca() instanceof Peao && tabuleiro.getCasa(linhaD, colunaD).estaOcupada()) {
                Peao peao = (Peao) tabuleiro.getCasa(linhaO, colunaO).getPeca();
                if(peao.peaoAtaque(linhaO, colunaO, linhaD, colunaD) && !jogador.ehDoJogador(tabuleiro.getCasa(linhaD, colunaD).getPeca())) return true;
                return false; 
            } 
        
            //por fim, se a peça nao pode fzr esse movimento, inválido
            if(!tabuleiro.getCasa(linhaO, colunaO).getPeca().movimentoValido(linhaO, colunaO, linhaD, colunaD)) return false;
            
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


    public boolean ehXequeMate(Jogador oponente) {
        
         if(oponente == null) throw new IllegalArgumentException("Esse Jogador nao existe");
        
         try {
            
             Casa casaRei = tabuleiro.acharRei(oponente.getCor());
            
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
             for(Jogada j : testeRei) {
                if(j.ehValida()){
                    simularJogada(j);
                    if(!ehXeque(oponente)) {
                        desfazerJogada(j);
                        return false;
                    }else{
                        desfazerJogada(j);
                        return true;
                    }
                }
             }

         } catch(IllegalArgumentException exc) {
             System.out.println("Erro ao verificar xeque-mate: " + exc.getMessage());
         }
        
         return true;
    }


    private void simularJogada(Jogada jogada) {
        
        try {
            
        tabuleiro.getCasa(jogada.getLinhaD(), jogada.getColunaD()).ocupar(tabuleiro.getCasa(jogada.getLinhaO(), jogada.getColunaO()).getPeca());
        tabuleiro.getCasa(jogada.getLinhaO(), jogada.getColunaO()).desocupar();
            
            
        } catch(IllegalArgumentException exc) {
            System.out.println("Erro ao simular jogada: " + exc.getMessage());
        }
    }

    private void desfazerJogada(Jogada jogada) {
            
            try {
                tabuleiro.getCasa(jogada.getLinhaO(), jogada.getColunaO()).ocupar(tabuleiro.getCasa(jogada.getLinhaD(), jogada.getColunaD()).getPeca());
                tabuleiro.getCasa(jogada.getLinhaD(), jogada.getColunaD()).desocupar();
                
                
            } catch(IllegalArgumentException exc) {
                System.out.println("Erro ao desfazer jogada: " + exc.getMessage());
            }
    }
    
    
    private void criarCaminho() {
        
        try {
            
            caminho = new Caminho(tabuleiro.getCasa(linhaO, colunaO), tabuleiro.getCasa(linhaD, colunaD));
        
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

    
    public String escrever() {
        return "<" + linhaO + colunaO + linhaD + colunaD + ">";
    }
    


    public int getLinhaD(){
        return linhaD;
    }
    
    public char getColunaD() {
        return colunaD;
    }

    public int getLinhaO() {
        return linhaO;
    }

    public char getColunaO() {
        return colunaO;
    }

    
}