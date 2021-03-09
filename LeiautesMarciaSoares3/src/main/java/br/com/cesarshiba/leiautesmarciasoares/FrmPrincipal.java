package br.com.cesarshiba.leiautesmarciasoares;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
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
    
	//public String caminho = "";
	public String caminho = "C:\\Users\\cesar\\git\\LeiautesMarciaSoares3\\LeiautesMarciaSoares3\\src\\main\\java\\br\\com\\cesarshiba\\leiautesmarciasoares\\";

	public String clienteSelecionado = "Selecione o paciente";

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
				montaPainelBackup();  //mostra fundo com paisagem
				root.setLeft(Menu());
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Tela de navegação principal do sistema
	 * Painel com dados básicos do paciente e consultas realizadas
	 */
	private void montaPainelPrincipal() {
		/*
		 * Nome do cliente escolhido
		 */
		Label label = new Label(clienteSelecionado);
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
		botao.setOnAction(value -> {
			System.out.println("Apertou botão CONFIG");
		});
		botao.setOnMouseEntered(value -> {
			botao.setStyle("-fx-background-color:#ffa000");
		});
		botao.setOnMouseExited(value -> {
			botao.setStyle("-fx-background-color:#fddbaf");
		});
		/*
		 * Montagem do painel de datas
		 * 
		 * Painel vertical para abrir nova consulta
		 */
		VBox vBoxNovaConsulta = new VBox();
		vBoxNovaConsulta.setAlignment(Pos.CENTER);
		vBoxNovaConsulta.setPadding(new Insets(5));
		vBoxNovaConsulta.setSpacing(5);
		//LocalDate diaHoje = LocalDate.now();
		//String localDate = diaHoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();
		Button btnNovaConsulta = new Button("Selecione Data");
		btnNovaConsulta.setFont(new Font("Arial Black", 20));
		btnNovaConsulta.setPrefSize(200, 100);
		btnNovaConsulta.setStyle("-fx-background-radius: 10 10 10 10; -fx-border-radius: 10 10 10 10; -fx-background-color:#87dff0");
		btnNovaConsulta.setAlignment(Pos.CENTER);
		btnNovaConsulta.setOnAction(value -> {
			String data = btnNovaConsulta.getText();
			System.out.println("apertou botão data=" + data);
		});
		DatePicker dataCalendario = new DatePicker();
		dataCalendario.setShowWeekNumbers(true);
		dataCalendario.setOnAction(value -> {
			LocalDate i = dataCalendario.getValue();
			btnNovaConsulta.setText(i.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		});
		vBoxNovaConsulta.getChildren().add(dataCalendario);
		vBoxNovaConsulta.getChildren().add(btnNovaConsulta);
		/*
		 * Painel horizontal com consultas realizadas
		 */
		HBox hBoxConsultas = new HBox();
		hBoxConsultas.setAlignment(Pos.CENTER);
		hBoxConsultas.setPadding(new Insets(5));
		hBoxConsultas.setSpacing(5);
		List<ClienteConsultas> consultasCliente = new ArrayList<>();
		consultasCliente = leConsultasClientes(clienteSelecionado);
		if (consultasCliente != null) {
			for (int i = 0; i < consultasCliente.size(); i++) {
				if (i < 5) {
					ClienteConsultas consulta = consultasCliente.get(i);
					hBoxConsultas.getChildren().add(Botao(consulta.getDataConsulta()));
				}
			}
		}
		/*
		 * Montagem do grid de painéis
		 */
		GridPane gridPane = new GridPane();
		gridPane.add(pane, 8, 2, 6, 2);
		gridPane.add(botao, 15, 2, 1, 1);
		gridPane.add(vBoxNovaConsulta, 8, 5, 1, 1);
		gridPane.add(hBoxConsultas, 10, 5, 1, 1);
		gridPane.setHgap(15);
		gridPane.setVgap(15);
		gridPane.setAlignment(Pos.TOP_CENTER);
		root.setCenter(gridPane);
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

    	tblClientes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ClienteNomeSexoEmail>() {
			@Override
			public void changed(ObservableValue<? extends ClienteNomeSexoEmail> observable,
					ClienteNomeSexoEmail oldValue, ClienteNomeSexoEmail newValue) {
		    	clienteSelecionado = newValue.getNomeCliente();
			}
		});

		GridPane gridPane = new GridPane();
		gridPane.add(tblClientes, 0, 0, 1, 1);
		gridPane.setAlignment(Pos.CENTER);
		root.setCenter(gridPane);
	}

	/*
	 * Monta painel com opções de Backup do sistema
	 */
	private void montaPainelBackup() {
		int nRandom = (int) (Math.random() * 9);
		String n =  String.format("%1d", nRandom);
		Image imagem = new Image(MainClass.caminho() +"/Paisagem" + n + ".jpg");
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
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		root.setCenter(grid);
		int n = 0;
		int linha = 2;
		int coluna = 0;
		for (n = 1; n < 36; n++) {
			grid.add(Imagem(n), linha, coluna);
			if (linha == 8) {
				linha = 2;
				coluna++;
			} else {
				linha++;
			}
		}
	}
	/*
	 * Gera imagem sequencial
	 */
	private ImageView Imagem(int n) {
		String seq = String.format("%02d", n);
		Image food = new Image(MainClass.caminho() + "/food" + seq + ".png");
		ImageView ivFood = new ImageView(food);
		return ivFood;
	}

	/*
	 * Gera botões sequenciais com datas das consultas
	 */
	private Button Botao(String dataConsulta) {
		Button btnDate = new Button(dataConsulta);
		btnDate.setFont(new Font("Arial Black", 20));
		btnDate.setStyle("-fx-background-radius: 10 10 10 10; -fx-border-radius: 10 10 10 10; -fx-background-color:#ffa000");
		btnDate.setPrefSize(160, 100);
		btnDate.setAlignment(Pos.CENTER);
		btnDate.setOnAction(value -> {
			String data = btnDate.getText();
			System.out.println("apertou botão data=" + data);
		});
		return btnDate;
	}
	/*
	 * Menu de botões lateral à esquerda da tela
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

	/*
	 * Rotina de leitura da base de dados de clientes e datas de consultas
	 */
    private List<ClienteConsultas> leConsultasClientes(String nomeCliente) {
    	Scanner scanner;
    	int contador = 0;
		List<ClienteConsultas> lista = new ArrayList<>();
    	try {
			scanner = new Scanner(new FileInputStream(caminho + "clientesconsultas.txt"));
	    	while(scanner.hasNextLine()) {
	    		String[] textoSeparado;
	    		String token = scanner.nextLine();
	    		textoSeparado = token.split(";");
	    		ClienteConsultas clienteConsulta = new ClienteConsultas(textoSeparado[0], textoSeparado[1]);
	    		if (nomeCliente.equals(clienteConsulta.getNomeCliente())) {
		    		lista.add(clienteConsulta);
		    		contador++;
	    		}
	    	}
	    	if (contador > 0) {
		    	return lista;
	    	} else {
	    		return null;
	    	}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
    }
}
