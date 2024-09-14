package xadrez;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.InputMismatchException;

public class Gerenciador {
    
    private Scanner leitor = new Scanner(System.in);
    private Jogo jogo;
    private ArrayList<Jogada> historicoJogadas;
    
    public static void main(String args[]){ //acho que ta bom de exceção aqui
        
        Gerenciador g = new Gerenciador();
        
        g.menuInicial();
        
        //main o mais enxuto possível   
    }
    
    public void menuInicial() {
        
         System.out.println(" ---- Jogo de Xadrez -----" + '\n');
         System.out.println("O que deseja fazer? ");
         System.out.println("Opcao 1: Iniciar Novo Jogo");
         System.out.println("Opcao 2: Carregar Jogo");
        
         int operacao = 0;
         boolean continuar = true;
        
         while(continuar) {
             try {
                 operacao = leitor.nextInt();
                
             if(operacao == 1 || operacao == 2) continuar = false;
             else System.out.println("Por favor, digite uma opcao valida");
            
             } catch(InputMismatchException exc) {
                 System.out.println("Por favor, digite um numero inteiro");
                 leitor.next(); //apagar o buffer
             }
         }
        
         switch(operacao) { //como já tem uma verificação ali em cima
             //só vai entrar 1 ou 2 aqui
            
             case 1:
                 System.out.println("Iniciando uma Nova Partida de Xadrez..." + '\n');
                 jogo = new Jogo();
                 jogo.rodarJogo(); //vai rodar sozinho até o jogador digitar parar ou dar xeque-mate
                
                 querSalvar(); //vai dar a opção de salvar ou não
                
                 break;
                
             case 2:
                 carregarJogo(); //método pra carregar o jogo
                 break;

         }

        //testeJogo();
        
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
                
            if(salvamento == 1 || salvamento == 2) continuar = false;
            else System.out.println("Por favor, digite uma opcao valida");
            
            } catch(InputMismatchException exc) {
                System.out.println("Por favor, digite um numero inteiro");
                leitor.next(); //limpar o buffer dnv
            }
            
        }
        
        if(salvamento == 1) salvarJogo(); //se o jogador escolher salvar chama outro método
        else System.out.println("Tudo bem, adeus :)" + '\n'); //se n, só uma mensagenzinha
        
    }
    
    private void salvarJogo() {
        
        System.out.println("Digite um nome para o Arquivo em que vamos salvar o jogo: ");
        String nomeArquivo = leitor.next(); //como aqui é uma string, n tem mt como dar InputMismatchException
       
        try {
            File novoArquivo = new File(nomeArquivo);
        
            FileWriter escritor = new FileWriter(nomeArquivo);
        
            escritor.write(jogo.escreverJogador(0) + '\n');
            escritor.write(jogo.escreverJogador(1) + '\n');
        
            for(int i = 0; i < jogo.getJogadas().size(); i++) {
                escritor.write(jogo.getJogada(i).escrever() + '\n');
            }
            
            System.out.println("Jogo salvo com sucesso!" + '\n');
            
            escritor.close();
        
        } catch(IOException exc) {
            System.out.println("Erro ao escrever no arquivo");
        } catch(IllegalArgumentException exc) {
            System.out.println("Erro ao escrever no arquivo: " + exc.getMessage());
        }
    }
    
    private void carregarJogo() {
        
        System.out.println("Digite o nome do Arquivo que deseja carregar: ");
        String nomeArquivo = leitor.next();
        
        try(Scanner leitorArquivo = new Scanner(new File(nomeArquivo))) {
            
                String primeiraFrase = leitorArquivo.nextLine(); //a primeira e a segunda linhas sao
                String segundaFrase = leitorArquivo.nextLine(); //os nomes dos jogadores
                
                primeiraFrase = primeiraFrase.replace("<", "").replace(">", "").replace("-", "");
                segundaFrase = segundaFrase.replace("<", "").replace(">", "").replace("-", ""); //tiramos os simbolos especiais
                String[] nomeJogador1 = primeiraFrase.split("\\s+");
                String[] nomeJogador2 = segundaFrase.split("\\s+"); //separamos as palavras
                
                Jogo carregarJogo = new Jogo(nomeJogador1[0], nomeJogador2[0]); //criamos um jogo diferente
                
                while(leitorArquivo.hasNextLine()) {
                    String jogada = leitorArquivo.nextLine();
                    
                    jogada = jogada.replace("<", "").replace(">", "");
                    
                    carregarJogo.receberJogada(jogada); //refazemos as jogadas do documento
                } 
                
                carregarJogo.rodarJogo(); //depois de passar todas as jogadas feitas vai rodar normalmente
                             
        } catch(FileNotFoundException exc) { //precisa de um IOException?
            System.out.println("O arquivo nao foi encontrado");
            
        }
        
    }
    
    public void testeJogo() {
        System.out.println("Iniciando teste de Jogo de Xadrez com jogadas pré-definidas..." + '\n');
        
        // Criar um novo jogo com jogadores fictícios
        jogo = new Jogo("Jogador 1", "Jogador 2");
        
        // Adicionar jogadas pré-definidas
        String[] jogadasPreDefinidas = {
            "2e4e", "7d5d", "1d5h", "5d4e", "5h7f", "8d2d"
        };
        
        for (String jogada : jogadasPreDefinidas) {
            jogo.receberJogada(jogada);
        }
        
        // Rodar o jogo com as jogadas pré-definidas
        while(jogo.getEstado() == 1) {
            jogo.rodarJogo();
        }


        System.out.println("Fim do teste de Jogo de Xadrez com jogadas pré-definidas." + '\n');

    }
}