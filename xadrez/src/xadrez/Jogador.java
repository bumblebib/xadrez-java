package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogador {
    private String nome; // nome do jogador
    private String cor; // cor das peças: "Branco" ou "Preto"
    private List<Peca> pecas;
    private Scanner leitor = new Scanner(System.in);

    // construtor
    public Jogador(String cor) { 
        
        if(!cor.equals("Branco") && !cor.equals("Preto")) {
            throw new IllegalArgumentException("Essa cor nao e valida");
        }
        
        System.out.print("Jogador, por favor informe seu nome: ");
        nome = leitor.next();
        
        this.cor = cor;
        
        if(cor.equals("Branco")) System.out.println("" + nome + ", voce eh o jogador 1. Sua cor eh Branco" + '\n');
        else System.out.println("" + nome + ", voce eh o jogador 2. Sua cor eh Preto" + '\n');
        
        this.pecas = new ArrayList<>();
    }
    
    public Jogador(String cor, String nome) {
        
        if(!cor.equals("Branco") && !cor.equals("Preto")) {
            throw new IllegalArgumentException("Essa cor nao e valida");
        }
        
        this.cor = cor;
        this.nome = nome;
        this.pecas = new ArrayList<>();
    }
    
    public void receberPecas(Peca p) {
        
        if(p == null) throw new IllegalArgumentException("Essa peca nao existe");
        
        pecas.add(p);
    }
    
    public boolean ehDoJogador(Peca p) {
        
        if(p == null) throw new IllegalArgumentException("Essa peca nao existe");
        
        if(pecas.contains(p)) return true;
        return false;
    }

    // solicita a jogada do jogador
    public String informaJogada() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(nome + ", digite sua jogada (ou 'Parar' para interromper): ");
        return scanner.nextLine();
    }

    // atualiza o estado da peça pra capturada
    public void capturarPeca(Peca peca) {
        
        if(peca == null) throw new IllegalArgumentException("Essa peca nao existe");
        
        peca.capturar();
    }

    public String getNome() {
        return nome;
    }
    
    public String getCor() {
        return cor;
    }
    
    public void mostrarCapturadas() {
        for (Peca peca : pecas) {
            if(!peca.estaEmJogo()) {
                System.out.print(peca.desenha() + " "); 
            }
        }
            System.out.println("");
    }

    public String escrever() {
        
        StringBuilder descricao = new StringBuilder();
        
        descricao.append("<" + nome + " - ");
        
        if(cor.equals("Branco")) descricao.append("pecas brancas>");
        else descricao.append("pecas pretas>");
        
        return descricao.toString();
    }
}
