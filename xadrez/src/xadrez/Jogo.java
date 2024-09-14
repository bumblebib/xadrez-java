package xadrez;

import java.util.ArrayList;
import java.util.Scanner;

public class Jogo {

    private Tabuleiro tabuleiro;
    private Jogador jogadores[] = new Jogador[2];
    private ArrayList<Jogada> jogada;
    private Peca pecas[] = new Peca[32];
    private int estado; // inicio 1, xeque 2, xeque-mate 3
    private int turnoJogador; // qual jogador é a vez branco(0) e preto (1)
    private Scanner leitor = new Scanner(System.in);
    
    public Jogo() {
        
        this.jogada = new ArrayList<Jogada>(); 
        setEstado(1);
        setTurnoJogador(0); //peças brancas começam
        
        try {
            this.jogadores[0] = new Jogador("Branco");
            this.jogadores[1] = new Jogador("Preto");
            
        }catch(IllegalArgumentException exc) {
            System.out.println("Erro ao criar Jogador: " + exc.getMessage());
        }
        
        inicializarJogo();
    }
    
    public Jogo(String jogador1, String jogador2) { //um construtor diferente pro caso de carregarJogo
        this.jogada = new ArrayList<Jogada>();
        setEstado(1);
        setTurnoJogador(0);
        
        try {
            this.jogadores[0] = new Jogador("Branco", jogador1); //tbm criando jogador diferente com nome ja
            this.jogadores[1] = new Jogador("Preto", jogador2);
            
        } catch(IllegalArgumentException exc) {
            System.out.println("Erro ao criar Jogador: " + exc.getMessage());
        }
        
        inicializarJogo(); //vai inicializar mas n vai rodar
    }
    
    private void inicializarJogo() {
        
        try {
            this.tabuleiro = new Tabuleiro();
        
            pecas[0] = new Torre("Branco"); 
            pecas[1] = new Cavalo("Branco");
            pecas[2] = new Bispo("Branco");
            pecas[3] = new Dama("Branco");
            pecas[4] = new Rei("Branco");
            pecas[5] = new Bispo("Branco");
            pecas[6] = new Cavalo("Branco");
            pecas[7] = new Torre("Branco");
        
            for(int i = 8; i < 16; i++) { 
                pecas[i] = new Peao("Branco");
            } //criando todas as peças brancas
        
            pecas[16] = new Torre("Preto"); 
            pecas[17] = new Cavalo("Preto");
            pecas[18] = new Bispo("Preto");
            pecas[19] = new Dama("Preto");
            pecas[20] = new Rei("Preto");
            pecas[21] = new Bispo("Preto");
            pecas[22] = new Cavalo("Preto");
            pecas[23] = new Torre("Preto");
        
            for(int i = 24; i < 32; i++) { 
                pecas[i] = new Peao("Preto");
            } //criando todas as peças pretas
        
            //agr vamos entregar as peças brancas pro jogador 1
            for(int i = 0; i < 16; i++) {
                jogadores[0].receberPecas(pecas[i]);
            } 
        
            //pretas pro jogador 2
            for(int i = 16; i < 32; i++) {
                jogadores[1].receberPecas(pecas[i]);
            }
        
            //faltar colocar elas no tabuleiro
            for(int i = 0; i < 8; i++) {
                tabuleiro.ocuparCasa(1, i, pecas[i]);
                tabuleiro.ocuparCasa(2, i, pecas[i + 8]);
                tabuleiro.ocuparCasa(8, i, pecas[i + 16]);
                tabuleiro.ocuparCasa(7, i, pecas[i + 24]);
            }
        
            tabuleiro.desenho();
            
        }catch(IllegalArgumentException exc) {
            System.out.println("Erro ao incializar o Jogo: " + exc.getMessage());
        }
            
    }
    
    public void rodarJogo() {
        
        boolean continuar = true;
        
        while(continuar) {
            
            String jogadaAtual = jogadores[turnoJogador].informaJogada();
            
            if(jogadaAtual.equals("Parar") || jogadaAtual.equals("parar")) {
                continuar = false;
                break;
            }
            
            int linhaO = (int)(jogadaAtual.charAt(0) - 49 + 1);
            char colunaO = jogadaAtual.charAt(1);
            int linhaD = (int)(jogadaAtual.charAt(2) - 49 + 1);
            char colunaD = jogadaAtual.charAt(3);
            
            realizarJogada(linhaO, colunaO, linhaD, colunaD);
            
            if(estado == 3) continuar = false;
        }
        
        System.out.println('\n' + "Bom jogo! Muito obrigado por jogar com a gente! >-<" + '\n');
    }
    
    private void setEstado(int estado){
        this.estado = estado;
    }
    
    private void setTurnoJogador(int jogador){
        this.turnoJogador = jogador;
    }
    
    private void realizarJogada(int linhaO, char colunaO, int linhaD, char colunaD) {
        System.out.println(jogadores[turnoJogador].getNome() + " realizou a jogada de: " + linhaO+ colunaO + " para " + linhaD + colunaD);
        
        try {
        
            Jogada novaJogada = new Jogada(linhaO, colunaO, linhaD, colunaD, jogadores[turnoJogador], tabuleiro);
            Peca pecaMovendo = tabuleiro.getCasa(linhaO, colunaO).getPeca(); //pegando a peça

            if(jogadaValida(novaJogada)) {
                jogada.add(novaJogada); //adiciona no histórico
            

                tabuleiro.getCasa(linhaO, colunaO).desocupar(); //liberando a casa
           
                if(tabuleiro.getCasa(linhaD, colunaD).estaOcupada()) { //se for um moviemnto de captura
                    if(turnoJogador == 0) {
                        jogadores[1].capturarPeca(tabuleiro.getCasa(linhaD, colunaD).getPeca());  //captura a peça do advr
                    } else {
                        jogadores[0].capturarPeca(tabuleiro.getCasa(linhaD, colunaD).getPeca()); 
                    }
                }
           
                tabuleiro.getCasa(linhaD, colunaD).ocupar(pecaMovendo); //ocupa a nova casa
                System.out.println("Movendo peça: " + pecaMovendo.desenha() + " " + pecaMovendo.getCor() + " de " + linhaO + colunaO + " para " + linhaD + colunaD);
           
                //agr que a jogada foi feita, precisamos atualizar as info do jogo e mostrar as informaçoes na tela
        
                atualizandoStatus(novaJogada); //testa xeque e xeque mate e imprime uma mensagem dependendo
                
                jogadores[1].mostrarCapturadas();
                tabuleiro.desenho();
                jogadores[0].mostrarCapturadas(); //printando o tabuleiro e as peças capturadas no lado de cada um 
        
                if(turnoJogador == 0) {
                    setTurnoJogador(1);
                } else {
                    setTurnoJogador(0);
                } //troca o turno
            
            } else { //pro caso da jogada nao ser válida
            System.out.println("Jogada inválida. Tente novamente");
            
            }
        } catch(IllegalArgumentException exc) {
            System.out.println("Erro ao realizar Jogada: " + exc.getMessage());
        }
        
    } 
    
    public boolean jogadaValida(Jogada novaJogada) {  
        
        if(novaJogada == null) throw new IllegalArgumentException("Essa Jogada nao existe");
        
        //se o jogador tentar fzr um movimento de capturar o rei, nao vou permitir
        if(tabuleiro.getCasa(novaJogada.getLinhaD(), novaJogada.getColunaD()).getPeca() instanceof Rei) {
            return false;
        }
        
        if(novaJogada.ehValida()) return true;
        return false;
    }
    
    private void atualizandoStatus(Jogada novaJogada) {
        
        if(novaJogada == null) throw new IllegalArgumentException("Essa Jogada nao existe");
        
        try {
        
            if(turnoJogador == 0) { //se era a vez das brancas
                if(novaJogada.ehXeque(jogadores[1])) { //vamos ver se as pretas estao em xeque
                
                    if(novaJogada.ehXequeMate(jogadores[1])) { //se estiver em xeque, testamos xeque mate
                        setEstado(3);
                        System.out.println("Xeque-Mate! " + jogadores[0].getNome() + " venceu!");
                    
                    } else {
                        setEstado(2); //se nao é só xeque mesmo
                        System.out.println("O rei Preto esta em Xeque!");
                    }
                }
            
            } else {
            
                if(novaJogada.ehXeque(jogadores[0])) { //mesma coisa para as pretas
                
                    if(novaJogada.ehXequeMate(jogadores[0])) {
                        setEstado(3);
                        System.out.println("Xeque-Mate! " + jogadores[1].getNome() + " venceu!");
                    
                    } else {
                        setEstado(2);
                        System.out.println("O rei Branco esta em Xeque!");
                    }
                }
            }
          
        }catch(IllegalArgumentException exc) {
            System.out.println("Erro ao atualizar status do Jogo: " + exc.getMessage());
        }
    }
    
    public ArrayList getJogadas() {
        return jogada;
    }
    
    public Jogada getJogada(int i) {
        
        if(i < 0 || i > jogada.size()) throw new IllegalArgumentException("Essa Jogada nao existe");
        
        return jogada.get(i);
    }
    
    public String escreverJogador(int i) {
        
        if(i != 0 && i != 1) throw new IllegalArgumentException("Esse jogador nao existe");
        
        return jogadores[i].escrever();
    }
    
    public void receberJogada(String jogada) {
        
        if(jogada == null) throw new IllegalArgumentException("Essa Jogada nao existe");
        
        int linhaO = (int)(jogada.charAt(0) - 49 + 1);
        char colunaO = jogada.charAt(1);
        int linhaD = (int)(jogada.charAt(2) - 49 + 1);
        char colunaD = jogada.charAt(3);
        
        realizarJogada(linhaO, colunaO, linhaD, colunaD);
        
    }

    public int getEstado() {
        return estado;
    }
    
    
}