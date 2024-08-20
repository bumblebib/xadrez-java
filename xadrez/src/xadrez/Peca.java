
/*
Para as peças específicas: 

● String desenho(): que vai retornar o elemento que representa a peça específica,
que será desenhado na tela.

● boolean movimentoValido(linhaO, colunaO, linhaD, colunaD): que vai verificar
se o movimento que o usuário deseja fazer é adequado para aquele tipo específico
de peça. Este método se preocupa apenas com a parte do movimento que se refere
à peça, não se preocupando com seu posicionamento do tabuleiro ou se o caminho
está livre para a peça se movimentar

● String caminho(linhaO, colunaO, linhaD, colunaD): se o movimento for válido
para a peça, retorna uma String que representa a sequência de casas pela qual a
peça irá se mover ou “” (string vazia) caso contrário. Por exemplo, se a peça for o
cavalo, a posição inicial for 4b e a final for 5d, o movimento é válido e o método
retorna a string “4b5b5c5d”.
*/
package xadrez;

public abstract class Peca {
    
}
