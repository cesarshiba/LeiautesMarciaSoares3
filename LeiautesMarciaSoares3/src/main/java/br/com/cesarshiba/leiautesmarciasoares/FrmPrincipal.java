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
		Label label = new Label("Cliente Escolhido");
		label.setFont(new Font("Arial Black", 45));
		label.setTextFill(Color.WHITE);
		label.setAlignment(Pos.CENTER);
		//label.setPrefWidth(1000);
		/*
		 * Painel para hospedar o label com nome do cliente
		 */
		Pane pane = new Pane();
		//pane.setPrefSize(1000, 100);
		pane.setStyle("-fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20; -fx-background-color:#ffa000");
		pane.getChildren().add(label);
		/*
		 * Botão de config
		 */
		Image imagem = new Image(MainClass.caminho() +"/config.png");
		ImageView imageView = new ImageView(imagem);
		imageView.setFitHeight(80);
		imageView.setFitWidth(80);
		Button botao = new Button();
		botao.setGraphic(imageView);
		botao.setStyle("-fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20; -fx-background-color:#fddbaf" );
		/*
		 * Ajuste da foto no painel reduzido
		 */
		Image foto = new Image(MainClass.caminho() +"/Paisagem.jpg");
		ImageView fotoView = new ImageView(foto);
		fotoView.setFitHeight(628);
		fotoView.setFitWidth(1000);
		fotoView.fitHeightProperty();
		fotoView.fitWidthProperty();
		/*
		 * Montagem do grid de painéis
		 */
		GridPane gridPane = new GridPane();
		gridPane.add(pane, 8, 2, 8, 2);
		gridPane.add(botao, 16, 2, 2, 2);
		gridPane.add(fotoView, 8, 4, 8, 8);
		gridPane.setHgap(15);
		//gridPane.setVgap(15);
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
	    tblClientes.setPrefWidth(582.0);
	    tblClientes.setStyle("-fx-background-radius: 20 20 20 20; -fx-border-radius: 20 20 20 20;");
	    //tblClientes.setLayoutX(9.0);
	    //tblClientes.setLayoutY(36.0);
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

		Image foto = new Image(MainClass.caminho() +"/Paisagem.jpg"); //Ajuste da foto no painel reduzido
		ImageView fotoView = new ImageView(foto);
		fotoView.setFitHeight(628);
		fotoView.setFitWidth(1000);
		fotoView.fitHeightProperty();
		fotoView.fitWidthProperty();

		GridPane gridPane = new GridPane();
		gridPane.add(fotoView, 8, 4, 8, 8);
		gridPane.add(tblClientes, 10, 9, 3, 2);
		gridPane.setHgap(15);
		gridPane.setVgap(15);
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
	 * Painel vazio
	 */
	private void montaPainelVazio() {
		GridPane grid = new GridPane();
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
				montaPainelVazio();
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
