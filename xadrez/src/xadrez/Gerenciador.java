/*
 Observação: apenas a parte de comunicação com o usuário necessária para o
carregamento/salvamento de um jogo e das opções iniciais deve ser feita nessa classe.
Manipulação de arquivos será vista no roteiro 5.
Os arquivos para registro dos jogos devem ter o seguinte formato:
<Nome do Jogador 1 - peças brancas>
<Nome do Jogador 2 - peças pretas>
<Jogada 1>
<Jogada 2>
<Jogada 3>
…
Cada jogada tem a linha e coluna da casa inicial da jogada e a linha e coluna da casa final,
sem qualquer separação. Por exemplo:
1a3b
4c2h
3g7g

 */
package xadrez;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Gerenciador {
    
    private Scanner leitor = new Scanner(System.in);
    private Jogo jogo;
    private ArrayList<Jogada> historicoJogadas;
    
    public static void main(String args[]){
        
        Gerenciador g = new Gerenciador();
        
        g.menuInicial();
        
        //main o mais enxuto possível   
    }
    
    public void menuInicial() {
        
        System.out.println(" ---- Jogo de Xadrez -----");
        System.out.println("O que deseja fazer? ");
        System.out.println("Opcao 1: Iniciar Novo Jogo");
        System.out.println("Opcao 2: Carregar Jogo");
        
        int operacao = 0;
        boolean continuar = true;
        
        while(continuar) {
            try {
                operacao = leitor.nextInt();
            } catch(IllegalArgumentException exc) {
                System.out.println("Por favor, digite um numero inteiro");
            }
            
            if(operacao == 1 || operacao == 2) continuar = false;
            else System.out.println("Por favor, digite uma opcao valida");
        }
        
        switch(operacao) {
            
            case 1:
                System.out.println("Iniciando uma Nova Partida de Xadrez...");
                jogo = new Jogo();
                jogo.rodarJogo(); //vai rodar sozinho até o jogador digitar parar ou dar xeque-mate
                
                querSalvar();
                
                break;
                
            case 2:
                carregarJogo(); //pior que o bagulho é automatico, vou ter que colocar um método diferente pra carregar um pronto
                break;
                
            default:
                System.out.println("Por favor, selecione uma opcao valida");
                break;
        }
        
    }
    
    private void querSalvar() {
        
        System.out.println("Deseja salvar o seu progresso no Jogo?");
        System.out.println("Opcao 1: Sim");
        System.out.println("Opcao 2: Nao");
        
        int salvamento = 0;
        boolean continuar = true;

        while(continuar) {
            try {
                salvamento = leitor.nextInt();
            } catch(IllegalArgumentException exc) {
                System.out.println("Por favor, digite um numero inteiro");
            }
            
            if(salvamento == 1 || salvamento == 2) continuar = false;
            else System.out.println("Por favor, digite uma opcao valida");
            
        }
        
        if(salvamento == 1) salvarJogo();
        else System.out.println("Tudo bem, adeus :/");
        
    }
    
    private void salvarJogo() {
        
        System.out.println("Digite um nome para o Arquivo em que vamos salvar o jogo: ");
        String nomeArquivo = leitor.next();
       
        try {
            File novoArquivo = new File(nomeArquivo);
        
            FileWriter escritor = new FileWriter(nomeArquivo);
        
            escritor.write(jogo.escreverJogador(0) + '\n');
            escritor.write(jogo.escreverJogador(1) + '\n');
        
            for(int i = 0; i < jogo.getJogadas().size(); i++) {
                escritor.write(jogo.getJogada(i).escrever() + '\n');
            }
            
            System.out.println("Jogo salvo com sucesso!");
            
            escritor.close();
        
        } catch(IOException exc) {
            System.out.println("Erro ao escrever no arquivo");
        } 
    }
    
    private void carregarJogo() {
        
        System.out.println("Digite o nome do Arquivo que deseja carregar: ");
        String nomeArquivo = leitor.next();
        
        
        try(Scanner leitorArquivo = new Scanner(new File(nomeArquivo))) {
            
                String primeiraFrase = leitorArquivo.nextLine();
                String segundaFrase = leitorArquivo.nextLine();
                
                primeiraFrase = primeiraFrase.replace("<", "").replace(">", "").replace("-", "");
                segundaFrase = segundaFrase.replace("<", "").replace(">", "").replace("-", "");
                String[] nomeJogador1 = primeiraFrase.split("\\s+");
                String[] nomeJogador2 = segundaFrase.split("\\s+");
                
                Jogo carregarJogo = new Jogo(nomeJogador1[0], nomeJogador2[0]);
                
                while(leitorArquivo.hasNextLine()) {
                    String jogada = leitorArquivo.nextLine();
                    
                    jogada = jogada.replace("<", "").replace(">", "");
                    
                    carregarJogo.receberJogada(jogada);
                } //depois de passar todas as jogadas feitas vai rodar normalmente
                
                carregarJogo.rodarJogo();
                             
        } catch(FileNotFoundException exc) {
            System.out.println("O arquivo nao foi encontrado");
            
        } 
    }
    
    
}