# Implementando um Jogo de Xadrez em Java !♟️

Para este trabalho de avaliação de Programação Orientada a objetos, implementamos um jogo de Xadrez utilizando a linguagem Java.

## Classes e Suas Responsabilidades
> ⚠️ **Atenção**
> 
>  **O detalhamento de métodos estão listados dentro dos arquivos de cada classe em forma de comentários, por favor removê-los após a finalização da classe** 💛
>


- **Gerenciador** 👩🏽‍🔧: vai criar e disparar o jogo, permitindo que o usuário a escolha de iniciar um jogo do zero, carregar um jogo a partir de um arquivo de texto, cujo o nome será fornecido pelo usuário e salvar um jogo após o encerramento ou interrupção de uma partida. <span style="color: BlueViolet">**Nenhum controle do jogo em si deve ser feito nesta classe.** </span>(Contém a main)

---

- **Jogo** ♟️: responsável pelo gerenciamento do jogo, controlando tudo o que acontece. Essa classe contém um <span style="color: BlueViolet">**tabuleiro, 2 jogadores e o conjunto de 32 peças.**</span> O jogo sabe o estado em que se encontra a cada momento (início do jogo, xeque, xeque-mate). Sabe também de que jogador é a vez, controlando as jogadas. as checagens, etc. Sendo a classe responsável por manter as informações na tela e solicitar ao jogador da vez as informações necessárias para a jogada ou interrupção do jogo. No início do jogo, também solicita a cada jogador o seu nome. Na tela, além do tabuleiro, o Jogo deve manter visível as peças de cada jogador que já foram capturadas, desenhadas do lado do tabuleiro correspondente ao jogador

---

- **Jogador** 🧙🏽: cada jogador tem um <span style="color: BlueViolet">**nome, um conjunto de peças de uma das cores disponíveis**</span> e <span style="color: BlueViolet">**sabe quais peças suas continuam ativas no jogo.**</span> Essa classe é onde é feita a <span style="color: BlueViolet">**entrada de dados**</span> para o jogo em si (dados do jogador e jogadas)

---

- **Jogada** 🎲: uma jogada é criada a partir das <span style="color: BlueViolet">**informações do jogador que a está realizando,**</span> a posição inicial e a posição final da jogada, mas deve manter o caminho com base nessas informações iniciais. Cada peça é quem deve fornecer seu caminho (na forma de string) e identificar se o movimento é valido conforme a sua forma de se movimentar. Uma vez criada, uma jogada não pode ser alterada.

---

- **Caminho** 🛤️: o caminho tem a<span style="color: BlueViolet"> **sequência das casas e informa sobre a posição inicial, a posição final e a situação do caminho.**</span>

---

- **Casa** 🏡**:** Cada casa tem uma cor <span style="color: BlueViolet">**(branco ou preto), uma linha (de 1 a 8) e uma coluna (de “a” a “h”).**</span>  Cada casa pode estar livre ou ocupada por uma peça e deve saber que peça a ocupa.

---

- **Tabuleiro** 🗺️: um tabuleiro<span style="color: BlueViolet"> **contém 64 casas**</span> organizadas em <span style="color: BlueViolet">**8 linhas e 8 colunas.** </span> Essa classe é responsável pela configuração inicial do tabuleiro, manutenção da configuração do tabuleiro a cada jogada, pelas informações dos limites do tabuleiro, bem como pelo desenho do tabuleiro, (com as peças nas posições ocupadas) a ser usado pelo Jogo.

---

- **Peça** 🧩: representa as funcionalidades gerais comuns a todas as peças, e serve de base para todas as outras. A classe Peça deverá ser uma classe abstrata, contendo interface para os métodos <span style="color: BlueViolet">**desenho, movimentoVálido** e **caminho,**</span> cuja implementação já deve constar nas classes específicas. As demais classes devem lidar com as peças polimorficamente a partir de referências para a classe Peça.

### Peças específicas

Cada tipo de peça específica tem uma classe própria: <span style="color: BlueViolet">**Rei, Dama, Cavalo, Bispo, Torre, Peão.**</span> Cada peça tem uma cor e é responsável pela representação que a peça terá na tela, pela checagem da adequação do movimento que o usuário deseja fazer em relação ao tipo específico de peça e pelo fornecimento do caminho que a peça realizaria. Cada peça também é responsável por manter a situação (se está capturada ou em jogo).
