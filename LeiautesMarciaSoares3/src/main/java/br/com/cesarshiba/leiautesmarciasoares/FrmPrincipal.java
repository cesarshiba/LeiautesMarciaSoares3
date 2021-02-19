package br.com.cesarshiba.leiautesmarciasoares;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class FrmPrincipal implements Initializable{

    @FXML
    public BorderPane root;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (!MainClass.loadSplash) {
			carregaSplash();
		}
		//monta quadros do cliente na tela principal
		if(MainClass.idCliente != 0) {
			Pane painel = new Pane();
			painel.setPrefWidth(200);
			painel.setPrefHeight(100);
			painel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			Label label = new Label("Testes");
			label.setPrefSize(100, 80);
			painel.getChildren().add(label);
			root.setTop(painel);
			//root.getChildren().setAll(painel);
		}
	}

	private void carregaSplash() {
		MainClass.loadSplash=true;
		
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource(MainClass.caminho() +"/frmLogo.fxml"));
			root.getChildren().setAll(pane);
			Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
			double x = bounds.getMinX() + (bounds.getWidth() - pane.getWidth()) * 0.28;
			double y = bounds.getMinY() + (bounds.getHeight() - pane.getHeight()) * 0.3;
			pane.setLayoutX(x);
			pane.setLayoutY(y);
			
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(2),pane);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(2),pane);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);
			//inicia splash
			fadeIn.play();
			fadeIn.setOnFinished((e) -> {
				fadeOut.play();
			});
			//finaliza splash
			fadeOut.setOnFinished((e) ->{
				BorderPane parentPane;
				try {
					parentPane = FXMLLoader.load(getClass().getResource(MainClass.caminho() +"/frmPrincipal.fxml"));
					root.getChildren().setAll(parentPane);
					root.setLeft(Menu());
					Image imagem = new Image(MainClass.caminho() +"/Paisagem.jpg");
					ImageView imageView = new ImageView(imagem);
					imageView.setFitHeight(628);
					imageView.setFitWidth(1000);
					imageView.fitHeightProperty();
					imageView.fitWidthProperty();
					GridPane grid = new GridPane();
					grid.add(imageView, 8, 4, 2, 2);
					grid.setHgap(15);
					grid.setVgap(15);
					root.setCenter(grid);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void montaPainelPrincipal() {
		/*
		 * Nome do cliente escolhido
		 */
		Label label = new Label("Cliente Escolhido");
		label.setFont(new Font("Arial Black", 45));
		label.setTextFill(Color.WHITE);
		label.setAlignment(Pos.CENTER);
		label.setPrefWidth(1000);
		Pane pane = new Pane();
		pane.setPrefSize(1000, 100);
		pane.setStyle("-fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20; -fx-background-color:#ffa000");
		pane.getChildren().add(label);
		/*
		 * Botão de config
		 */
		Image imagem = new Image(MainClass.caminho() +"/config.png");
		ImageView imageView = new ImageView(imagem);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		Button botao = new Button();
		botao.setGraphic(imageView);
		botao.setStyle("-fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20; -fx-background-color:#fddbaf");

		Image foto = new Image(MainClass.caminho() +"/Paisagem.jpg");
		ImageView fotoView = new ImageView(foto);
		fotoView.setFitHeight(550);
		fotoView.setFitWidth(1000);
		fotoView.fitHeightProperty();
		fotoView.fitWidthProperty();
		/*
		 * Montagem do grid de painéis
		 */
		GridPane gridPane = new GridPane();
		gridPane.add(pane, 0, 1, 6, 1);
		gridPane.add(botao, 8, 0, 2, 1);
		gridPane.add(fotoView, 2, 2, 8, 8);
		gridPane.setHgap(5);
		root.setCenter(gridPane);
		botao.setOnAction(value -> {
			System.out.println("Apertou botão CONFIG");
		});
		botao.setOnMouseEntered(value -> {
			botao.setStyle("-fx-background-color:#ffa000");
		});
		botao.setOnMouseExited(value -> {
			botao.setStyle("-fx-background-color:#fddbaf");
		});
	}
	
	private VBox Menu() {
		VBox vbox = new VBox();
		//vbox.setPrefWidth(85);
		vbox.setStyle("-fx-background-color:#35145d");
		vbox.setAlignment(Pos.CENTER);
		for(int i = 1; i < 6; i++) {
			vbox.getChildren().add(Item(String.valueOf(i)));
		}
		return vbox;
	}
	
	private HBox Item(String nome) {
		Image imagem = new Image(MainClass.caminho() +"/"+ nome +".png");
		ImageView imageView = new ImageView(imagem);
		imageView.setFitWidth(130);
		imageView.setFitHeight(130);
		Button btn = new Button();
		btn.setGraphic(imageView);
		btn.setStyle("-fx-background-color:#35145d");
		//aciona botões do painel para chamar os procedimentos
		btn.setOnAction(value -> {
			switch (Integer.valueOf(nome)) {
			case 1: {
				try {
					FXMLLoader fxmlClientes = new FXMLLoader(getClass().getResource(MainClass.caminho() +"/frmClientes.fxml"));
					Parent rootClientes = (Parent) fxmlClientes.load();
					Scene sceneClientes = new Scene(rootClientes);
					sceneClientes.setFill(Color.TRANSPARENT);
					Stage stageClientes = new Stage();
					stageClientes.setTitle("Selecionar Cliente");
					stageClientes.setScene(sceneClientes);
					stageClientes.setResizable(false);
					stageClientes.initStyle(StageStyle.TRANSPARENT);
					stageClientes.initModality(Modality.APPLICATION_MODAL);
					stageClientes.show();
				} catch (IOException e1) {
					e1.printStackTrace();
					System.out.println(e1);
				}
				break;
			}
			case 2: {
				montaPainelPrincipal();
				break;
			}
			case 5: {
				System.exit(0);
			}
			default:
				System.out.println("apertou botão:"+ nome);
			}
		});
		Pane paneIndicator = new Pane();
		paneIndicator.setStyle("-fx-background-color:#000000");
		menuDecorator(btn, paneIndicator);
		HBox hbox = new HBox(paneIndicator,btn);
		return hbox;
	}
	
	private void menuDecorator(Button btn, Pane pane) {
		btn.setOnMouseEntered(value -> {
			btn.setStyle("-fx-background-color:#87dff0");
			pane.setStyle("-fx-background-color:#87dff0");
		});
		btn.setOnMouseExited(value -> {
			btn.setStyle("-fx-background-color:#35145d");
			pane.setStyle("-fx-background-color:#000000");
		});
	}
}
