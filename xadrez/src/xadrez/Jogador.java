package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Jogador {
    private String nome; // nome do jogador
    private String cor; // cor das peças: "branco" ou "preto"
    private List<String> pecasAtivas; // peças ainda em jogo
    private List<String> pecasCapturadas; // peças que foram capturadas

    // construtor
    public Jogador(String nome, String cor) {
        this.nome = nome;
        this.cor = cor;
        this.pecasAtivas = new ArrayList<>();
        this.pecasCapturadas = new ArrayList<>();
        inicializarPecas();
    }

    // preenche a lista de peças ativas com as peças iniciais do jogador
    // torre posicionada nas extremidades do tabuleiro
    // cavalo ao lado da torre
    // bispo ao lado do cavalo
    // rainha na casa central que corresponde à sua cor, ao lado do bispo
    // rei ao lado da rainha
    // oito peões posicionados na frente de todas essas peças
    private void inicializarPecas() {
        if (cor.equals("branco")) {
            pecasAtivas.add("Torre");
            pecasAtivas.add("Cavalo");
            pecasAtivas.add("Bispo");
            pecasAtivas.add("Rainha");
            pecasAtivas.add("Rei");
            pecasAtivas.add("Bispo");
            pecasAtivas.add("Cavalo");
            pecasAtivas.add("Torre");
            for (int i = 0; i < 8; i++) {
                pecasAtivas.add("Peão");
            }
        } else {
            pecasAtivas.add("Torre");
            pecasAtivas.add("Cavalo");
            pecasAtivas.add("Bispo");
            pecasAtivas.add("Rainha");
            pecasAtivas.add("Rei");
            pecasAtivas.add("Bispo");
            pecasAtivas.add("Cavalo");
            pecasAtivas.add("Torre");
            for (int i = 0; i < 8; i++) {
                pecasAtivas.add("Peão");
            }
        }
    }

    // solicita a jogada do jogador
    public String informaJogada() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(nome + ", digite sua jogada (ou 'parar' para interromper): ");
        return scanner.nextLine();
    }

    // retorna uma string com as peças capturadas
    public String pecasCapturadas() {
        return String.join(" ", pecasCapturadas);
    }

    // remove a peça especificada da lista de peças ativas e adiciona à lista de peças capturadas
    public void capturarPeca(String peca) {
        if (pecasAtivas.remove(peca)) {
            pecasCapturadas.add(peca);
        }
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public List<String> getPecasAtivas() {
        return pecasAtivas;
    }

    public List<String> getPecasCapturadas() {
        return pecasCapturadas;
    }
}