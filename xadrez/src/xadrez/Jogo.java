/*
        Não completa!!
        O jogo cria até 2 jogadores
        O jogo cria um tabuleiro
        O jogo cria uma peça
        O jogo cria uma ou mais jogadaas e verifica estas jogadas
*/

package xadrez;

import java.util.ArrayList;
import java.util.Scanner;

public class Jogo {
    private Tabuleiro tabuleiro;
    private Jogador jogadores[] = new Jogador[2];
    private ArrayList<Jogada> jogada;
    private Peca pecas[] = new Peca[32];
    private int estado; // inicio 1, xeque 2, xeque-mate 3
    private int turnoJogador; // qual jogador é a vez branco(1) ou preto(2)
    
    public Jogo(){
        this.jogada = new ArrayList<Jogada>();
        setEstado(1);
        setTurnoJogador(1); // não sei quem que começa primeiro
        
        // pegando informações do jogador 1 
        System.out.println("Jogador 1, por favor informe seu nome e a cor das suas peças");
        Scanner leitor = new Scanner(System.in);
        System.out.print("Nome: ");
        String nome1 = leitor.nextLine();
        System.out.print("Cor: ");
        String cor1 = leitor.nextLine();
        this.jogadores[0] = new Jogador(nome1, cor1);
        
        // pegando informações do jogador 2
        System.out.println("Jogador 1, por favor informe seu nome e a cor das suas peças");
        System.out.print("Nome: ");
        String nome2 = leitor.nextLine();
        System.out.print("Cor: ");
        String cor2 = leitor.nextLine();
        this.jogadores[0] = new Jogador(nome2, cor2);
    }
    

    public boolean jogadaValida(int linhaO, char colunaO, int linhaD, char colunaD){
        Casa origem = tabuleiro.getCasa(new Casa(linhaO, colunaO));
        Casa destino = tabuleiro.getCasa(new Casa(linhaD, colunaD));
        Jogada jogada = new Jogada(jogadores[turnoJogador], origem, destino);
        if (jogada.ehValida(tabuleiro)){
            this.jogada.add(jogada);
            return true;
        }
        return false;
    }

    public void realizarJogada(int linhaO, char colunaO, int linhaD, char colunaD) {
        if (jogadaValida(linhaO, colunaO, linhaD, colunaD)) {
            Casa origem = tabuleiro.getCasa(new Casa(linhaO, colunaO));
            Casa destino = tabuleiro.getCasa(new Casa(linhaD, colunaD));
            Peca pecaMovida = origem.getPecaNaCasa();
            
            // Atualizar o posicionamento das peças no tabuleiro
            tabuleiro.colocarPeca(pecaMovida, destino);
            tabuleiro.removerPeca(origem);
            
            // Verificar se o jogo está em xeque ou xeque-mate
            if (jogada.get(jogada.size() - 1).ehXeque(tabuleiro)) {
                if (jogada.get(jogada.size() - 1).ehXequeMate(tabuleiro)) {
                    setEstado(3); // xeque-mate
                    System.out.println("Xeque-mate! O jogador " + jogadores[turnoJogador].getNome() + " perdeu!");
                } else {
                    setEstado(2); // xeque
                    System.out.println("Xeque! O jogador " + jogadores[turnoJogador].getNome() + " está em xeque!");
                }
            } else {
                setEstado(1); // jogo em andamento
            }

            // Trocar o turno do jogador
            if (turnoJogador == 1) {
                turnoJogador = 2;
            } else {
                turnoJogador = 1;
            }
            // Mostrar as informações apropriadas na tela
            System.out.println("Jogada realizada com sucesso!");
            // ...
        } else {
            System.out.println("Jogada inválida!");
        }
    }

    public String registroJogo() {
        StringBuilder registro = new StringBuilder();
        
        // Informações do tabuleiro
        registro.append("Tabuleiro:\n");
        registro.append(tabuleiro.toString());
        
        // Informações dos jogadores
        registro.append("Jogadores:\n");
        for (Jogador jogador : jogadores) {
            registro.append(jogador.toString());
            registro.append("\n");
        }
        
        // Informações das jogadas
        registro.append("Jogadas:\n");
        for (Jogada jogada : jogada) {
            registro.append(jogada.toString());
            registro.append("\n");
        }
        
        // Informações do estado e turno do jogo
        registro.append("Estado: ");
        switch (estado) {
            case 1:
                registro.append("Jogo em andamento");
                break;
            case 2:
                registro.append("Xeque");
                break;
            case 3:
                registro.append("Xeque-mate");
                break;
        }
        registro.append("\n");
        
        registro.append("Turno do jogador: ");
        switch (turnoJogador) {
            case 1:
                registro.append(jogadores[0].getNome());
                break;
            case 2:
                registro.append(jogadores[1].getNome());
                break;
        }
        registro.append("\n");
        
        return registro.toString();
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setTurnoJogador(int turnoJogador){
        this.turnoJogador = turnoJogador;
    }
    
}
