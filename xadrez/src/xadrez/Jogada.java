package xadrez;

public class Jogada {
  private final Jogador jogador; // jogador que está realizando a jogada
  private final String posicaoInicial; 
  private final String posicaoFinal;
  private final String caminho; // caminho que a peça percorre da posição inicial até a posição final

  // uma vez que a jogada é criada, ela não pode ser alterada
  public Jogada(Jogador jogador, String posicaoInicial, String posicaoFinal, String caminho) {
    this.jogador = jogador;
    this.posicaoInicial = posicaoInicial;
    this.posicaoFinal = posicaoFinal;
    this.caminho = caminho;
  }

  // verifica se a jogada é válida
  public boolean ehValida(Tabuleiro tabuleiro) {
    // verifica se as posições estão dentro dos limites do tabuleiro
    if (!tabuleiro.estaDentroDosLimites(posicaoInicial) || !tabuleiro.estaDentroDosLimites(posicaoFinal)) {
      return false;
    }

    // verifica se a peça na posição inicial pertence ao jogador
    Peca pecaInicial = tabuleiro.getPeca(posicaoInicial);
    if (pecaInicial == null || !pecaInicial.getCor().equals(jogador.getCor())) {
      return false;
    }

    // verifica se a posição final está livre ou ocupada por uma peça do oponente
    Peca pecaFinal = tabuleiro.getPeca(posicaoFinal);
    if (pecaFinal != null && pecaFinal.getCor().equals(jogador.getCor())) {
      return false;
    }

    // verifica se o caminho está livre, caso a peça não possa pular
    if (!pecaInicial.podePular() && !tabuleiro.caminhoEstaLivre(caminho)) {
      return false;
    }

    // verifica se o movimento é válido para a peça
    return pecaInicial.movimentoEhValido(posicaoInicial, posicaoFinal, caminho);
  }

  // verifica se a jogada resulta em xeque
  public boolean ehXeque(Tabuleiro tabuleiro) {
    // simula a jogada e verifica se o rei adversário está em xeque
    tabuleiro.simulaJogada(posicaoInicial, posicaoFinal);
    boolean emXeque = tabuleiro.reiEmXeque(jogador.oponente().getCor());
    // depois de verificar, desfaz a jogada e retorna o resultado 'true' se a jogada resulta em xeque ou 'false' caso contrário
    tabuleiro.desfazSimulacao();
    return emXeque;
  }

  // verifica se a jogada resulta em xeque-mate
  public boolean ehXequeMate(Tabuleiro tabuleiro) {
    // simula a jogada e verifica se o rei adversário está em xeque-mate
    // primeiro verifica se a jogada resulta em xeque. Se não for xeque, a jogada não resulta em xeque-mate, então retorna 'false'
    if (ehXeque(tabuleiro)) { 
      return !tabuleiro.temMovimentosValidos(jogador.oponente().getCor());
    }
    return false;
  }

  // Getters para as propriedades da jogada
  public Jogador getJogador() {
    return jogador;
  }

  public String getPosicaoInicial() {
    return posicaoInicial;
  }

  public String getPosicaoFinal() {
    return posicaoFinal;
  }

  public String getCaminho() {
    return caminho;
  }
}