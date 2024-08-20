/*
A cada jogada solicitada por um jogador, o Jogo é a classe que dispara a jogada checando
se ela é válida, atualizando o tabuleiro, a situação, o histórico de jogadas, a tela, a situação
do jogo, das peças, etc, usando sempre as chamadas de métodos das outras classes que
forem apropriados.
Todas as jogadas efetivamente realizadas devem ser registradas em um histórico de
jogadas que pode ser solicitado pelo Gerenciador para armazenamento em arquivo ou
visualização das jogadas.
A classe Jogo deve ter ao menos os métodos:

boolean jogadaValida(linhaO, colunaO, linhaD, colunaD): verifica se a jogada é
válida ou não

void realizarJogada(linhaO, colunaO, linhaD, colunaD): se a jogada é válida,
atualiza o posicionamento das peças no tabuleiro, a situação do jogo, etc, e mostra
na tela as informações apropriadas.

String registroJogo(): deve retornar uma string com todos os dados relevantes do
jogo para que ele possa ser retomado posteriormente (ver arquivo do Gerenciador).
*/
package xadrez;

/**
 *
 * 
 */
public class Jogo {
    
}
