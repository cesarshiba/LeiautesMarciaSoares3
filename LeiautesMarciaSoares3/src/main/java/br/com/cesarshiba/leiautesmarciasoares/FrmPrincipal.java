package br.com.cesarshiba.leiautesmarciasoares;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.util.Duration;

public class FrmPrincipal implements Initializable{

	@FXML
    public BorderPane root;
    
	public String caminho = "";
	//public String caminho = "C:\\Users\\cesar\\Documents\\Projetos\\JAVA\\LeiautesMarciaSoares\\src\\main\\java\\br\\com\\cesarshiba\\leiautesmarciasoares\\";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (!MainClass.loadSplash) {
			carregaSplash();
		}
	}

	/*
	 * Efeito de abertura do sistema com logo do cliente
	 */
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
			fadeIn.play(); //inicia splash
			fadeIn.setOnFinished((e) -> {
				fadeOut.play(); //finaliza splash
			});
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

	/*
	 * Tela de navegação inicial do sistema
	 */
	private void montaPainelPrincipal() {
		/*
		 * Nome do cliente escolhido
		 */
		Label label = new Label("Cliente Selecionado");
		label.setFont(new Font("Arial Black", 40));
		label.setTextFill(Color.WHITE);
		label.setAlignment(Pos.CENTER);
		label.setPrefWidth(1000);
		/*
		 * Painel para hospedar o label com nome do cliente
		 */
		Pane pane = new Pane();
		pane.setStyle("-fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20; -fx-background-color:#ffa000");
		pane.getChildren().add(label);
		/*
		 * Botão de config
		 */
		Image imagem = new Image(MainClass.caminho() +"/Config.png");
		ImageView imageView = new ImageView(imagem);
		imageView.setFitHeight(80);
		imageView.setFitWidth(80);
		Button botao = new Button();
		botao.setGraphic(imageView);
		double r = 40;
		botao.setShape(new Circle(r));
		botao.setMinSize(2*r, 2*r);
		botao.setMaxSize(2*r, 2*r);
		/*
		 * Montagem do grid de painéis
		 */
		GridPane gridPane = new GridPane();
		gridPane.add(pane, 8, 2, 8, 2);
		gridPane.add(botao, 16, 2, 2, 2);
		gridPane.setHgap(15);
		gridPane.setVgap(15);
		gridPane.setAlignment(Pos.TOP_CENTER);
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

	/*
	 * Painel com lista dos clientes da base de dados para ser selecionado
	 */
	private void montaPainelClientes() {
	    TableView<ClienteNomeSexoEmail> tblClientes = new TableView<>();
	    tblClientes.setPrefWidth(600.0);
	    tblClientes.setPrefHeight(600.0);
	    tblClientes.setStyle("-fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20;");
	    ObservableList<ClienteNomeSexoEmail> obsClientes;
	    TableColumn<ClienteNomeSexoEmail, String> colNomeCliente = new TableColumn<ClienteNomeSexoEmail, String>();
	    colNomeCliente.setPrefWidth(266.0);
	    colNomeCliente.setText("Nome");
	    TableColumn<ClienteNomeSexoEmail, String> colSexoCliente = new TableColumn<ClienteNomeSexoEmail, String>();
	    colSexoCliente.setPrefWidth(85.0);
	    colSexoCliente.setText("Sexo");
	    TableColumn<ClienteNomeSexoEmail, String> colEmailCliente = new TableColumn<ClienteNomeSexoEmail, String>();
	    colEmailCliente.setPrefWidth(235.0);
	    colEmailCliente.setText("E-Mail");
    	obsClientes = FXCollections.observableArrayList(leClientes());
    	colNomeCliente.setCellValueFactory(new PropertyValueFactory<ClienteNomeSexoEmail,String>("nomeCliente"));
    	colSexoCliente.setCellValueFactory(new PropertyValueFactory<ClienteNomeSexoEmail,String>("sexoCliente"));
    	colEmailCliente.setCellValueFactory(new PropertyValueFactory<ClienteNomeSexoEmail,String>("emailCliente"));
    	tblClientes.getColumns().addAll(colNomeCliente, colSexoCliente, colEmailCliente);
    	tblClientes.setItems(obsClientes);

		GridPane gridPane = new GridPane();
		gridPane.add(tblClientes, 0, 0, 1, 1);
		gridPane.setAlignment(Pos.CENTER);
		root.setCenter(gridPane);
	}

	/*
	 * Monta painel com opções de Backup do sistema
	 */
	private void montaPainelBackup() {
		Image imagem = new Image(MainClass.caminho() +"/Paisagem.jpg");
		ImageView imageView = new ImageView(imagem);
		imageView.setFitHeight(628);
		imageView.setFitWidth(1000);
		imageView.fitHeightProperty();
		imageView.fitWidthProperty();
		GridPane grid = new GridPane();
		grid.add(imageView, 8, 4, 8, 8);
		grid.setHgap(15);
		grid.setVgap(15);
		root.setCenter(grid);
	}

	/*
	 * Painel com imagens de nutrição
	 */
	private void montaPainelImagens() {
		Image food01 = new Image(MainClass.caminho() + "/food01.png");
		ImageView ivFood01 = new ImageView(food01);
		Image food02 = new Image(MainClass.caminho() + "/food02.png");
		ImageView ivFood02 = new ImageView(food02);
		Image food03 = new Image(MainClass.caminho() + "/food03.png");
		ImageView ivFood03 = new ImageView(food03);
		Image food04 = new Image(MainClass.caminho() + "/food04.png");
		ImageView ivFood04 = new ImageView(food04);
		Image food05 = new Image(MainClass.caminho() + "/food05.png");
		ImageView ivFood05 = new ImageView(food05);
		Image food06 = new Image(MainClass.caminho() + "/food06.png");
		ImageView ivFood06 = new ImageView(food06);
		Image food07 = new Image(MainClass.caminho() + "/food07.png");
		ImageView ivFood07 = new ImageView(food07);
		Image food08 = new Image(MainClass.caminho() + "/food08.png");
		ImageView ivFood08 = new ImageView(food08);
		Image food09 = new Image(MainClass.caminho() + "/food09.png");
		ImageView ivFood09 = new ImageView(food09);
		Image food10 = new Image(MainClass.caminho() + "/food10.png");
		ImageView ivFood10 = new ImageView(food10);
		Image food11 = new Image(MainClass.caminho() + "/food11.png");
		ImageView ivFood11 = new ImageView(food11);
		Image food12 = new Image(MainClass.caminho() + "/food12.png");
		ImageView ivFood12 = new ImageView(food12);
		Image food13 = new Image(MainClass.caminho() + "/food13.png");
		ImageView ivFood13 = new ImageView(food13);
		Image food14 = new Image(MainClass.caminho() + "/food14.png");
		ImageView ivFood14 = new ImageView(food14);
		Image food15 = new Image(MainClass.caminho() + "/food15.png");
		ImageView ivFood15 = new ImageView(food15);
		Image food16 = new Image(MainClass.caminho() + "/food16.png");
		ImageView ivFood16 = new ImageView(food16);
		Image food17 = new Image(MainClass.caminho() + "/food17.png");
		ImageView ivFood17 = new ImageView(food17);
		Image food18 = new Image(MainClass.caminho() + "/food18.png");
		ImageView ivFood18 = new ImageView(food18);
		Image food19 = new Image(MainClass.caminho() + "/food19.png");
		ImageView ivFood19 = new ImageView(food19);
		Image food20 = new Image(MainClass.caminho() + "/food20.png");
		ImageView ivFood20 = new ImageView(food20);
		Image food21 = new Image(MainClass.caminho() + "/food21.png");
		ImageView ivFood21 = new ImageView(food21);
		Image food22 = new Image(MainClass.caminho() + "/food22.png");
		ImageView ivFood22 = new ImageView(food22);
		Image food23 = new Image(MainClass.caminho() + "/food23.png");
		ImageView ivFood23 = new ImageView(food23);
		Image food24 = new Image(MainClass.caminho() + "/food24.png");
		ImageView ivFood24 = new ImageView(food24);
		Image food25 = new Image(MainClass.caminho() + "/food25.png");
		ImageView ivFood25 = new ImageView(food25);
		Image food26 = new Image(MainClass.caminho() + "/food26.png");
		ImageView ivFood26 = new ImageView(food26);
		Image food27 = new Image(MainClass.caminho() + "/food27.png");
		ImageView ivFood27 = new ImageView(food27);
		Image food28 = new Image(MainClass.caminho() + "/food28.png");
		ImageView ivFood28 = new ImageView(food28);
		Image food29 = new Image(MainClass.caminho() + "/food29.png");
		ImageView ivFood29 = new ImageView(food29);
		Image food30 = new Image(MainClass.caminho() + "/food30.png");
		ImageView ivFood30 = new ImageView(food30);
		Image food31 = new Image(MainClass.caminho() + "/food31.png");
		ImageView ivFood31 = new ImageView(food31);
		Image food32 = new Image(MainClass.caminho() + "/food32.png");
		ImageView ivFood32 = new ImageView(food32);
		Image food33 = new Image(MainClass.caminho() + "/food33.png");
		ImageView ivFood33 = new ImageView(food33);
		Image food34 = new Image(MainClass.caminho() + "/food34.png");
		ImageView ivFood34 = new ImageView(food34);
		Image food35 = new Image(MainClass.caminho() + "/food35.png");
		ImageView ivFood35 = new ImageView(food35);
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.add(ivFood01, 2, 0);
		grid.add(ivFood02, 3, 0);
		grid.add(ivFood03, 4, 0);
		grid.add(ivFood04, 5, 0);
		grid.add(ivFood05, 6, 0);
		grid.add(ivFood06, 7, 0);
		grid.add(ivFood07, 8, 0);
		grid.add(ivFood08, 2, 1);
		grid.add(ivFood09, 3, 1);
		grid.add(ivFood10, 4, 1);
		grid.add(ivFood11, 5, 1);
		grid.add(ivFood12, 6, 1);
		grid.add(ivFood13, 7, 1);
		grid.add(ivFood14, 8, 1);
		grid.add(ivFood15, 2, 2);
		grid.add(ivFood16, 3, 2);
		grid.add(ivFood17, 4, 2);
		grid.add(ivFood18, 5, 2);
		grid.add(ivFood19, 6, 2);
		grid.add(ivFood20, 7, 2);
		grid.add(ivFood21, 8, 2);
		grid.add(ivFood22, 2, 3);
		grid.add(ivFood23, 3, 3);
		grid.add(ivFood24, 4, 3);
		grid.add(ivFood25, 5, 3);
		grid.add(ivFood26, 6, 3);
		grid.add(ivFood27, 7, 3);
		grid.add(ivFood28, 8, 3);
		grid.add(ivFood29, 2, 4);
		grid.add(ivFood30, 3, 4);
		grid.add(ivFood31, 4, 4);
		grid.add(ivFood32, 5, 4);
		grid.add(ivFood33, 6, 4);
		grid.add(ivFood34, 7, 4);
		grid.add(ivFood35, 8, 4);
		root.setCenter(grid);
	}

	/*
	 * Painel lateral à esquerda da tela
	 */
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

	/*
	 * Monta botões no painel lateral
	 */
	private HBox Item(String nome) {
		Image imagem = new Image(MainClass.caminho() +"/"+ nome +".png");
		ImageView imageView = new ImageView(imagem);
		imageView.setFitWidth(130);
		imageView.setFitHeight(130);
		Button btn = new Button();
		btn.setGraphic(imageView);
		btn.setStyle("-fx-background-color:#35145d");
		btn.setOnAction(value -> { //Aciona botões do painel para chamar os procedimentos
			switch (Integer.valueOf(nome)) {
			case 1: {
				montaPainelClientes();
				break;
			}
			case 2: {
				montaPainelPrincipal();
				break;
			}
			case 3:{
				montaPainelBackup();
				break;
			}
			case 4:{
				montaPainelImagens();
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

	/*
	 * Trata eventos do mouse sobre os botões
	 */
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

	/*
	 * Rotina de leitura da base de dados de clientes
	 */
    private List<ClienteNomeSexoEmail> leClientes() {
    	Scanner scanner;
		List<ClienteNomeSexoEmail> lista = new ArrayList<>();
    	try {
			scanner = new Scanner(new FileInputStream(caminho + "clientes.txt"));
	    	while(scanner.hasNextLine()) {
	    		String[] textoSeparado;
	    		String token = scanner.nextLine();
	    		textoSeparado = token.split(";");
	        	ClienteNomeSexoEmail cliente = new ClienteNomeSexoEmail(textoSeparado[0], textoSeparado[1], textoSeparado[2]);
	    		lista.add(cliente);
	    	}
	    	return lista;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
    }
}
