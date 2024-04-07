/* ***************************************************************
* Autor............: Hugo Botelho Santana
* Matricula........: 202210485
* Inicio...........: 22/03/2023
* Ultima alteracao.: 07/04/2023
* Nome.............: Camada Fisica
* Funcao...........: Simular a camada fisica de uma rede
*************************************************************** */

//Importacao das bibliotecas do JavaFx

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Transmissor {
  private int qtdCaracters;
  private int tipoDeCodificacao;
  MeioDeComunicao meioDeComunicao = new MeioDeComunicao();

  public void tipoDeCodificacao(int n){
    this.tipoDeCodificacao = n;
  }

  /* ***************************************************************
  * Metodo: AplicacaoTransmissora.
  * Funcao: fazer aparacer o campo para digitar a mensagem e chamar a CamadaDeAplicacaoTransmissora.
  * Parametros: sem parametros.
  * Retorno: sem retorno.
  *************************************************************** */
  public void AplicacaoTransmissora(){
    TextArea textArea = new TextArea();
    Principal.root.getChildren().add(textArea);
    textArea.setLayoutX(63);
    textArea.setLayoutY(252);
    textArea.setStyle(
      "-fx-font-size: 14px;" + // Tamanho da fonte
      "-fx-border-radius: 10px;" + // Bordas arredondadas
      "-fx-padding: 10px;" + // Padding
      "-fx-background-color: #FFEEE5;" + // Cor de fundo
      "-fx-focus-color: transparent;" + // Cor de foco
      "-fx-faint-focus-color: transparent;" // Cor de foco fraco
    );

    // Definindo o tamanho do TextArea
    textArea.setPrefSize(110, 55);
    Button enviarButton = new Button("Enviar");
    enviarButton.setStyle(
      "-fx-font-size: 16px; " + // Tamanho da fonte
      "-fx-background-color: white; " + // Cor de fundo
      "-fx-text-fill: #435D7A; " + // Cor do texto
      "-fx-padding: 10px; " + // Padding
      "-fx-background-radius: 10px; " + // Bordas arredondadas
      "-fx-border-radius: 10px; " + // Bordas arredondadas
      "-fx-border-color: transparent;" // Cor da borda
    );
    // Mudando a cor do texto quando o mouse passa por cima
    enviarButton.setOnMouseEntered(e -> enviarButton.setStyle("-fx-font-size: 16px; -fx-background-color: #435D7A; -fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 10px; fx-border-radius: 10px; -fx-border-color: transparent;"));
    // Voltando à cor original do texto quando o mouse sai de cima
    enviarButton.setOnMouseExited(e -> enviarButton.setStyle("-fx-font-size: 16px; -fx-background-color: white; -fx-text-fill: #435D7A; -fx-padding: 10px; -fx-background-radius: 10px; fx-border-radius: 10px; -fx-border-color: transparent;"));

    enviarButton.setLayoutX(90);
    enviarButton.setLayoutY(400);

    enviarButton.setOnAction(e -> {
      String mensagem = textArea.getText();
      CamadaDeAplicacaoTransmissora(mensagem);
    });

    Principal.root.getChildren().add(enviarButton);

  }
  
  /* ***************************************************************
  * Metodo: CamadaDeAplicacaoTransmissora.
  * Funcao: metodo para inserir os bits de cada caracter da mensagem em um array de inteiros e chama a CamadaFisicaTransmissora.
  * Parametros: recebe uma mensagem do tipo String.
  * Retorno: sem retorno.
  *************************************************************** */

  public void CamadaDeAplicacaoTransmissora(String mensagem){
    char[] arrayDeCaracteres = mensagem.toCharArray();
    qtdCaracters =  arrayDeCaracteres.length;
    int[] quadro = new int[(qtdCaracters+3)/4];
    int index = 0;
    int desloca = 31;
    for (int i = 0; i < qtdCaracters; i++){
      char caractere = mensagem.charAt(i);	
      String caractere8Bits = String.format("%8s", Integer.toBinaryString(caractere)).replace(' ', '0');
      //System.out.println(caractere8Bits);
      for (int j = 0; j < 8; j++){
        if (caractere8Bits.charAt(j) == '1'){
          quadro[index] = quadro[index] | (1 << desloca);
        }
        desloca--;
        if (desloca<0){
          desloca = 31;
          index++;
        }
      }
    }
    //System.out.println(String.format("%32s", Integer.toBinaryString(quadro[1])).replace(' ', '0'));
    CamadaFisicaTransmissora(quadro); 
  }
  
  /* ***************************************************************
  * Metodo: CamadaFisicaTransmissora.
  * Funcao: transforma o array de inteiros em outro array codificado com base na tipo de codificacao e chama o MeioDeComunicacao.
  * Parametros: recebe o array de inteiros.
  * Retorno: sem retorno.
  *************************************************************** */

  public void CamadaFisicaTransmissora(int quadro[]){
    int [] fluxoBrutoDeBits = new int[0]; //ATENÇÃO: trabalhar com BITS!!!
    switch (tipoDeCodificacao) {
    case 0 : //codificao binaria
    fluxoBrutoDeBits = CamadaFisicaTransmissoraCodificacaoBinaria(quadro);
    break;
    case 1 : //codificacao manchester
    fluxoBrutoDeBits = CamadaFisicaTransmissoraCodificacaoManchester(quadro);
    break;
    case 2 : //codificacao manchester diferencial
    fluxoBrutoDeBits = CamadaFisicaTransmissoraCodificacaoManchesterDiferencial(quadro);
    break;
    }//fim do switch/case
    meioDeComunicao.setQtdCaracters(qtdCaracters);
    meioDeComunicao.setTipoDeCodificacao(tipoDeCodificacao);
    meioDeComunicao.meioDeComunicacao(fluxoBrutoDeBits);
  }
  
  /* ***************************************************************
  * Metodo: CamadaFisicaTransmissoraCodificacaoBinaria.
  * Funcao: metodo para codificar a mensagem em binario.
  * Parametros: recebe o array de inteiros.
  * Retorno: retorna o mesmo array de interios porque a codificacao eh binaria.
  *************************************************************** */

  public int[] CamadaFisicaTransmissoraCodificacaoBinaria (int quadro []) {
    //implementacao do algoritmo
    /*
    for (int i = 0; i < quadro.length; i++){
      System.out.println(String.format("%32s", Integer.toBinaryString(quadro[i])).replace(' ', '0'));
    }*/
    return quadro;
  }//fim do metodo CamadaFisicaTransmissoraCodificacaoBinaria

  /* ***************************************************************
  * Metodo: CamadaFisicaTransmissoraCodificacaoMancherster.
  * Funcao: metodo para codificar a mensagem em Mancherster (o O eh representado por O1 e o 1 eh representado por 10).
  * Parametros: recebe o array de inteiros.
  * Retorno: retornar o array codificado.
  *************************************************************** */

  public int[] CamadaFisicaTransmissoraCodificacaoManchester (int quadro []) {
    int [] fluxoCodificacaoMancherster = new int [(qtdCaracters+1)/2];
    int indexFLuxo = 0;
    int posBit = 31;
    int fim = 0;
    for (int i = 0; i < quadro.length; i++){
      for (int j = 31; j >= fim; j--) {
        int bit = (quadro[i] >> j) & 1;

        if (bit == 1){
          fluxoCodificacaoMancherster[indexFLuxo] = fluxoCodificacaoMancherster[indexFLuxo] | (1 << posBit);
          posBit--;
          fluxoCodificacaoMancherster[indexFLuxo] = fluxoCodificacaoMancherster[indexFLuxo] | (0 << posBit);
        }
        else{
          fluxoCodificacaoMancherster[indexFLuxo] = fluxoCodificacaoMancherster[indexFLuxo] | (0 << posBit);
          posBit--;
          fluxoCodificacaoMancherster[indexFLuxo] = fluxoCodificacaoMancherster[indexFLuxo] | (1 << posBit);
        }
        posBit--;
        if (posBit <= 0){
          posBit = 31;
          if (indexFLuxo < fluxoCodificacaoMancherster.length-1){
            indexFLuxo++;
          }
        }
        
        if (qtdCaracters == (4*(i+1))-1){
          fim = 8;
        }
        if (qtdCaracters == (4*(i+1))-2){
          fim = 16;
        }
        if (qtdCaracters == (4*(i+1))-3){
          fim = 24;
        }
      }
    }
    /*
    for (int i = 0; i < fluxoCodificacaoMancherster.length; i++){
      System.out.println(String.format("%32s", Integer.toBinaryString(fluxoCodificacaoMancherster[i])).replace(' ', '0'));
    }
    */
    return fluxoCodificacaoMancherster;
  }//fim do metodo CamadaFisicaTransmissoraCodificacaoManchester
  
  /* ***************************************************************
  * Metodo: CamadaFisicaTransmissoraCodificacaoManchersterDiferencial.
  * Funcao: metodo para codificar a mensagem em Mancherster (o O eh representado por uma inversao de sinal e o 1 eh representado por uma falta de inversao de sinal).
  * Parametros: recebe o array de inteiros.
  * Retorno: retornar o array codificado.
  *************************************************************** */

  public int[] CamadaFisicaTransmissoraCodificacaoManchesterDiferencial(int quadro []){
    int [] fluxoCodificacaoManchersterDiferencial = new int [(qtdCaracters+1)/2];
    int indexFLuxo = 0;
    int posBit = 31;
    int fim = 0;
    int sinalAnterior = 0;
    for (int i = 0; i < quadro.length; i++){
      for (int j = 31; j >= fim; j--) {
        int bit = (quadro[i] >> j) & 1;
        if (bit == 1){
          if (j==31 && i == 0){
          fluxoCodificacaoManchersterDiferencial[indexFLuxo] = fluxoCodificacaoManchersterDiferencial[indexFLuxo] | (1 << posBit);
          posBit--;
          fluxoCodificacaoManchersterDiferencial[indexFLuxo] = fluxoCodificacaoManchersterDiferencial[indexFLuxo] | (0 << posBit);
          }
          else{
            //int num = posBit + 1;
            //int sinalAnterior = (fluxoCodificacaoManchersterDiferencial[indexFLuxo]>>(num))&1;
            if (sinalAnterior == 1){
              fluxoCodificacaoManchersterDiferencial[indexFLuxo] = fluxoCodificacaoManchersterDiferencial[indexFLuxo] | (1 << posBit);
              posBit--;
              fluxoCodificacaoManchersterDiferencial[indexFLuxo] = fluxoCodificacaoManchersterDiferencial[indexFLuxo] | (0 << posBit);                  
            }
            else{
              fluxoCodificacaoManchersterDiferencial[indexFLuxo] = fluxoCodificacaoManchersterDiferencial[indexFLuxo] | (0 << posBit);
              posBit--;
              fluxoCodificacaoManchersterDiferencial[indexFLuxo] = fluxoCodificacaoManchersterDiferencial[indexFLuxo] | (1 << posBit);  
            }
          }
        }
        else{
          if (j==31 && i == 0){
            fluxoCodificacaoManchersterDiferencial[indexFLuxo] = fluxoCodificacaoManchersterDiferencial[indexFLuxo] | (0 << posBit);
            posBit--;
            fluxoCodificacaoManchersterDiferencial[indexFLuxo] = fluxoCodificacaoManchersterDiferencial[indexFLuxo] | (1 << posBit);
          }
          else{
            //int num = posBit + 1;
            //int sinalAnterior = (fluxoCodificacaoManchersterDiferencial[indexFLuxo]>>(num))&1;
            if (sinalAnterior == 1){
              fluxoCodificacaoManchersterDiferencial[indexFLuxo] = fluxoCodificacaoManchersterDiferencial[indexFLuxo] | (0 << posBit);
              posBit--;
              fluxoCodificacaoManchersterDiferencial[indexFLuxo] = fluxoCodificacaoManchersterDiferencial[indexFLuxo] | (1 << posBit);                
            }
            else{
              fluxoCodificacaoManchersterDiferencial[indexFLuxo] = fluxoCodificacaoManchersterDiferencial[indexFLuxo] | (1 << posBit);
              posBit--;
              fluxoCodificacaoManchersterDiferencial[indexFLuxo] = fluxoCodificacaoManchersterDiferencial[indexFLuxo] | (0 << posBit);    
            }
          }
        }
        posBit--;
        if (posBit <= 0){
          posBit = 31;
          sinalAnterior = (fluxoCodificacaoManchersterDiferencial[indexFLuxo]<<(0))&1;
          if (indexFLuxo < fluxoCodificacaoManchersterDiferencial.length-1){
            indexFLuxo++;
          }
        }
        else{
          int num = posBit + 1;
          sinalAnterior = (fluxoCodificacaoManchersterDiferencial[indexFLuxo]>>num)&1;
        }
        
        if (qtdCaracters == (4*(i+1))-1){
          fim = 8;
        }
        if (qtdCaracters == (4*(i+1))-2){
          fim = 16;
        }
        if (qtdCaracters == (4*(i+1))-3){
          fim = 24;
        }
      }
    }
    /*
    for (int i = 0; i < fluxoCodificacaoManchersterDiferencial.length; i++){
      System.out.println(String.format("%32s", Integer.toBinaryString(fluxoCodificacaoManchersterDiferencial[i])).replace(' ', '0'));
    }
    */
    return fluxoCodificacaoManchersterDiferencial;
  }//fim do CamadaFisicaTransmissoraCodificacaoManchesterDiferencial


}