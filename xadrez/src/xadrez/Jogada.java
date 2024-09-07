/*
    A jogada: 
    - conhece o tabuleiro 
    - cria um caminho (composição) -> não sei como 
    - tem um jogador (agregação)

*/
package xadrez;

public class Jogada {
  private Jogador jogador; // jogador que está realizando a jogada
  private Caminho caminho; // caminho que a peça percorre da posição inicial até a posição final
  private Casa posicaoInicial; // posição inicial da jogada
  private Casa posicaoFinal; // posição final da jogada

  // uma vez que a jogada é criada, ela não pode ser alterada
  public Jogada(Jogador jogador, int linhaO, char colunaO, int linhaD, char colunaD) {
      this.jogador = jogador;
      this.caminho = new Caminho(new Casa(linhaO, colunaO), new Casa(linhaD, colunaD));
      this.posicaoInicial = caminho.casaInicial();
      this.posicaoFinal = caminho.casaFinal();
  }

  // verifica se a jogada é válida
  // 
  public boolean ehValida(Tabuleiro tabuleiro) {
    // Verifica se a posição inicial e a posição final estão dentro dos limites do tabuleiro
    if (!tabuleiro.noLimite(posicaoInicial) || !tabuleiro.noLimite(posicaoFinal)) {
      return false;
    }
    
    // Verifica se a peça na posição inicial pertence ao jogador que está realizando a jogada
    if (!tabuleiro.getPeca(posicaoInicial).getCor().equals(jogador.getCor())) {
      return false;
    }
    
    // Verifica se a posição final está livre ou ocupada por uma peça do oponente
    if (tabuleiro.getPeca(posicaoFinal) != null && tabuleiro.getPeca(posicaoFinal).getCor().equals(jogador.getCor())) {
      return false;
    }
    
    // Verifica se o caminho está livre, caso a peça não possa pular sobre outras peças
    if (!caminho.estaLivre()) {
      return false;
    }
    
    // Verifica se o movimento é válido para a peça na posição inicial
    if (!tabuleiro.getPeca(posicaoInicial).movimentoValido(posicaoInicial.getLinha(), posicaoInicial.getColuna(), posicaoFinal.getLinha(), posicaoFinal.getColuna())) {
      return false;
    }
    
    return true;
  }

  // verifica se a jogada resulta em xeque
  public boolean ehXeque(Tabuleiro tabuleiro) {
    // obtém a cor do jogador atual
    String corJogador = jogador.getCor();
    
    // obtém a posição do rei do jogador atual
    Casa posicaoRei = tabuleiro.getPosicaoRei(jogador.getCor());
    
    // verifica se o rei está em xeque
    boolean emXeque = tabuleiro.estaEmCheque(corJogador, posicaoRei);
    
    return emXeque;
  }
  
  public boolean ehXequeMate(Tabuleiro tabuleiro) {
    // Verifica se o jogador está em xeque
    if (!ehXeque(tabuleiro)) {
        return false; // Se não está em xeque, não é xeque-mate
    }

    // Para cada peça do jogador
    for (Peca peca : jogador.pecasAtivas()) {
        Casa posicaoAtual = tabuleiro.getPosicaoPeca(peca);

        // Tenta todas as posições possíveis no tabuleiro
        for (int linha = 1; linha <= 8; linha++) {
            for (char coluna = 'A'; coluna <= 'H'; coluna++) {
                Casa posicaoDestino = new Casa(linha, coluna);

                // Cria uma jogada hipotética
                Jogada jogadaTeste = new Jogada(jogador, posicaoAtual.getLinha(), posicaoAtual.getColuna(), posicaoDestino.getLinha(), posicaoDestino.getColuna());

                // Verifica se a jogada é válida, incluindo se o caminho está livre
                if (jogadaTeste.ehValida(tabuleiro) && jogadaTeste.getCaminho().estaLivre()) {
                    // Realiza a jogada (modificando diretamente o tabuleiro)
                    tabuleiro.removerPeca(posicaoAtual);
                    tabuleiro.colocarPeca(peca, posicaoDestino);

                    // Verifica se o rei ainda está em xeque após a jogada
                    if (!tabuleiro.estaEmCheque(jogador.getCor(), tabuleiro.getPosicaoRei(jogador.getCor()))) {
                        // Se alguma jogada tira o rei do xeque, não é xeque-mate
                        // Desfaz a jogada manualmente
                        tabuleiro.removerPeca(posicaoDestino);
                        tabuleiro.colocarPeca(peca, posicaoAtual);
                        return false;
                    }

                    // Desfaz a jogada (coloca as peças de volta às posições originais)
                    tabuleiro.removerPeca(posicaoDestino);
                    tabuleiro.colocarPeca(peca, posicaoAtual);
                }
            }
        }
    }

    // Se nenhuma jogada possível tira o rei do xeque, é xeque-mate
    return true;
}


  // Getters para as propriedades da jogada
  public Jogador getJogador() {
    return jogador;
  }

  public Caminho getCaminho() {
    return caminho;
  }
}
