/* ***************************************************************
* Autor............: Hugo Botelho Santana
* Matricula........: 202210485
* Inicio...........: 22/03/2023
* Ultima alteracao.: 22/03/2023
* Nome.............: Camada Fisica
* Funcao...........: Simular a camada fisica de uma rede
*************************************************************** */

//Importacao das bibliotecas do JavaFx

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.application.Platform;

// Classe principal que herda da classe Application
public class Principal extends Application {
  public static Pane root = new Pane();
  public void start(Stage primaryStage){
    Image backgroundImage = new Image("/img/background.png");
    ImageView backgroundImageView = new ImageView(backgroundImage);
    root.getChildren().add(backgroundImageView);
    //Criação dos ToggleButtons para as opções de trajetória
    ToggleButton opcao1 = new ToggleButton("Codificacao Binaria");
    ToggleButton opcao2 = new ToggleButton("Codificacao Mancherster");
    ToggleButton opcao3 = new ToggleButton("Codificacao Mancherster Diferencial");

    //Criação de um ToggleGroup para garantir que apenas um botão seja selecionado
    ToggleGroup toggleGroup = new ToggleGroup();
    opcao1.setToggleGroup(toggleGroup);
    opcao2.setToggleGroup(toggleGroup);
    opcao3.setToggleGroup(toggleGroup);

    //Listener para a mudança de seleção dos ToggleButtons
    toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        //Reseta as cores de todos os botões
        opcao1.setStyle("#FFFFFF; -fx-font-size: 15px; -fx-padding: 10; -fx-background-radius: 10; -fx-border-color: #435D7A; -fx-border-radius: 10; -fx-text-fill: #435D7A");
        opcao2.setStyle("#FFFFFF; -fx-font-size: 15px; -fx-padding: 10; -fx-background-radius: 10; -fx-border-color: #435D7A; -fx-border-radius: 10; -fx-text-fill: #435D7A");
        opcao3.setStyle("#FFFFFF; -fx-font-size: 15px; -fx-padding: 10; -fx-background-radius: 10; -fx-border-color: #435D7A; -fx-border-radius: 10; -fx-text-fill: #435D7A");
        //Define a cor do botão selecionado
        ((ToggleButton) newValue).setStyle("-fx-font-size: 15px; -fx-background-color: #435D7A; -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 10; -fx-border-radius: 10;");
        }
    });

    // Define um botão como selecionado inicialmente
    toggleGroup.selectToggle(opcao1);
    VBox vboxOpoces = new VBox(10, opcao1, opcao2, opcao3);
    vboxOpoces.setAlignment(Pos.CENTER);
    vboxOpoces.setLayoutX(430);
    vboxOpoces.setLayoutY(540);    

    root.getChildren().add(vboxOpoces);

    Transmissor transmissor = new Transmissor();
    transmissor.AplicacaoTransmissora();

  	Scene scene = new Scene(root, 1100, 700);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Camada Fisica");
    //Bloqueio da maximizacao da tela
    primaryStage.setResizable(false);
    //Exibicao da janela
    primaryStage.show();

    primaryStage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    });
  }

  /* ***************************************************************
  * Metodo: main.
  * Funcao: metodo para iniciar a aplicacao.
  * Parametros: padrao java.
  * Retorno: sem retorno.
  *************************************************************** */
  public static void main(String[] args){
    launch(args);
  }
}