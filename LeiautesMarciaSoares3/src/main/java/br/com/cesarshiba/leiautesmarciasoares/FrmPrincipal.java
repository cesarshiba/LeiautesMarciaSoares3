package br.com.cesarshiba.leiautesmarciasoares;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FrmPrincipal implements Initializable{

	@FXML
    public BorderPane root;

	public String clienteSelecionado = "Selecione o paciente";
	public String dataSelecionada = "";
	Label lblDataSelecionada = new Label();

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

		Image imagem = new Image("logo marcia.png");
		ImageView imageView = new ImageView(imagem);
		imageView.setFitHeight(imagem.getHeight());
		imageView.setFitWidth(imagem.getWidth());
		imageView.setPreserveRatio(true);
		GridPane grid = new GridPane();
		grid.add(imageView, 0, 0);
		grid.setAlignment(Pos.CENTER);
		root.setCenter(grid);
		FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), imageView);
		fadeIn.setFromValue(0);
		fadeIn.setToValue(1);
		fadeIn.setCycleCount(1);
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), imageView);
		fadeOut.setFromValue(1);
		fadeOut.setToValue(0);
		fadeOut.setCycleCount(1);
		fadeIn.play(); //inicia splash
		fadeIn.setOnFinished((e) -> {
			fadeOut.play(); //finaliza splash
		});
		fadeOut.setOnFinished((e) ->{
			montaPainelPaisagens();  //mostra fundo com paisagem
			root.setLeft(Menu());
		});
	}

	/*
	 * Tela de navega��o principal do sistema
	 * Painel com dados b�sicos do paciente e consultas realizadas
	 */
	private void montaPainelPrincipal() {
		/*
		 * Nome do cliente escolhido
		 */
		Label label = new Label(clienteSelecionado);
		label.setFont(new Font("Leelawadee UI Semilight", 40));
		label.setTextFill(Color.WHITE);
		label.setAlignment(Pos.CENTER);
		label.setPrefWidth(1000);
		label.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#ffa000");
		/*
		 * Bot�o de config
		 */
		double l = 100;
		Image imagem = new Image(MainClass.caminho() +"/Config.png");
		ImageView imageView = new ImageView(imagem);
		imageView.setFitHeight(l);
		imageView.setFitWidth(l);
		Button botao = new Button();
		botao.setGraphic(imageView);
		botao.setShape(new Circle(l));
		botao.setMinSize(l, l);
		botao.setMaxSize(l, l);
		botao.setStyle("-fx-background-color:#fddbaf");
		botao.setOnAction(value -> {
			try {
				TelaCadastroPaciente();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Apertou bot�o CADASTRO");
		});
		botao.setOnMouseEntered(value -> {
			botao.setStyle("-fx-background-color:#87dff0");
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
		Label lblNovaConsulta = new Label("nova consulta");
		lblNovaConsulta.setFont(new Font("Leelawadee UI Semilight", 20));
		lblNovaConsulta.setPrefSize(180, 20);
		lblNovaConsulta.setStyle("-fx-background-radius: 5 5 5 5; -fx-border-radius: 5 5 5 5; -fx-background-color:#ffa000");
		lblNovaConsulta.setAlignment(Pos.CENTER);
		DatePicker dataCalendario = new DatePicker();
		dataCalendario.setShowWeekNumbers(true);
		dataCalendario.setOnAction(value -> {
			LocalDate i = dataCalendario.getValue();
			dataSelecionada = i.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			lblDataSelecionada.setText(dataSelecionada);
		});
		vBoxNovaConsulta.getChildren().add(lblNovaConsulta);
		vBoxNovaConsulta.getChildren().add(dataCalendario);
		/*
		 * Painel horizontal com consultas realizadas
		 */
		HBox hBoxConsultas = new HBox();
		hBoxConsultas.setAlignment(Pos.CENTER);
		hBoxConsultas.setPadding(new Insets(5));
		hBoxConsultas.setSpacing(5);
		List<ClienteConsultas> consultasCliente = new ArrayList<>();
		consultasCliente = leConsultasClientes(clienteSelecionado, "x");
		if (consultasCliente != null) {
			for (int i = 0; i < consultasCliente.size(); i++) {
				if (i < 5) {
					ClienteConsultas consulta = consultasCliente.get(i);
					hBoxConsultas.getChildren().add(Botao(consulta.getDataConsulta()));
				}
			}
		}
		/*
		 * Label com a data selecionada
		 */
		lblDataSelecionada.setFont(new Font("Leelawadee UI Semilight", 25));
		lblDataSelecionada.setTextFill(Color.CADETBLUE);
		lblDataSelecionada.setAlignment(Pos.CENTER);
		lblDataSelecionada.setPrefWidth(160);
		lblDataSelecionada.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fbd9ae");
		/*
		 * Montagem do grid de pain�is
		 */
		GridPane gridPane = new GridPane();
		gridPane.add(label, 6, 2, 6, 2);
		gridPane.add(botao, 13, 2, 1, 1);
		gridPane.add(vBoxNovaConsulta, 6, 5, 1, 1);
		gridPane.add(lblDataSelecionada, 6, 7);
		gridPane.add(hBoxConsultas, 8, 5, 1, 1);
		gridPane.add(montaPainelRegistros(), 8, 7, 1, 1);
		gridPane.setHgap(15);
		gridPane.setVgap(15);
		gridPane.setAlignment(Pos.TOP_CENTER);
		root.setCenter(gridPane);
	}

	/*
	 * Painel com lista dos clientes da base de dados para ser selecionado
	 */
	private void montaPainelClientes() throws SQLException {
	    TableView<ClienteNomeSexoEmail> tblClientes = new TableView<>();
	    tblClientes.setPrefWidth(750.0);
	    tblClientes.setPrefHeight(650.0);
	    tblClientes.getStylesheets().add(MainClass.caminho() + "/tblClientes.css");
	    ObservableList<ClienteNomeSexoEmail> obsClientes;
	    TableColumn<ClienteNomeSexoEmail, Integer> colIdCliente = new TableColumn<ClienteNomeSexoEmail, Integer>();
	    colIdCliente.setPrefWidth(10.0);
	    colIdCliente.setText("ID");
	    TableColumn<ClienteNomeSexoEmail, String> colNomeCliente = new TableColumn<ClienteNomeSexoEmail, String>();
	    colNomeCliente.setPrefWidth(350.0);
	    colNomeCliente.setText("Nome");
	    TableColumn<ClienteNomeSexoEmail, String> colSexoCliente = new TableColumn<ClienteNomeSexoEmail, String>();
	    colSexoCliente.setPrefWidth(100.0);
	    colSexoCliente.setText("Sexo");
	    TableColumn<ClienteNomeSexoEmail, String> colObjetivoCliente = new TableColumn<ClienteNomeSexoEmail, String>();
	    colObjetivoCliente.setPrefWidth(300.0);
	    colObjetivoCliente.setText("Objetivo");
	    clsAcessaDB db = new clsAcessaDB();
	    obsClientes = FXCollections.observableArrayList(db.listaClientes());
    	//obsClientes = FXCollections.observableArrayList(leClientes(""));
    	colIdCliente.setCellValueFactory(new PropertyValueFactory<ClienteNomeSexoEmail,Integer>("idCliente"));
    	colNomeCliente.setCellValueFactory(new PropertyValueFactory<ClienteNomeSexoEmail,String>("nomeCliente"));
    	colSexoCliente.setCellValueFactory(new PropertyValueFactory<ClienteNomeSexoEmail,String>("sexoCliente"));
    	colObjetivoCliente.setCellValueFactory(new PropertyValueFactory<ClienteNomeSexoEmail,String>("objetivoCliente"));
    	tblClientes.getColumns().addAll(colIdCliente,colNomeCliente, colSexoCliente, colObjetivoCliente);
    	tblClientes.setItems(obsClientes);

    	tblClientes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ClienteNomeSexoEmail>() {
			@Override
			public void changed(ObservableValue<? extends ClienteNomeSexoEmail> observable,
					ClienteNomeSexoEmail oldValue, ClienteNomeSexoEmail newValue) {
		    	MainClass.idCliente = newValue.getIdCliente();
				clienteSelecionado = newValue.getNomeCliente();
				dataSelecionada = "";
				lblDataSelecionada.setText("");
			}
		});
    	tblClientes.setOnMouseClicked(event -> {
    		if(event.getClickCount() == 2) {
    			ClienteNomeSexoEmail dblClick = tblClientes.getSelectionModel().getSelectedItem();
    			MainClass.idCliente = dblClick.getIdCliente();
    			clienteSelecionado = dblClick.getNomeCliente();
    			dataSelecionada = "";
    			lblDataSelecionada.setText("");
    			montaPainelPrincipal();
    		}
    	});
		/*
		 * Bot�o de cadastro
		 */
		double l = 100;
    	Image imagem = new Image(MainClass.caminho() +"/cadastro.png");
		ImageView imageView = new ImageView(imagem);
		imageView.setFitHeight(l);
		imageView.setFitWidth(l);
		Button botao = new Button();
		botao.setGraphic(imageView);
		botao.setShape(new Circle(l));
		botao.setMinSize(l, l);
		botao.setMaxSize(l, l);
		botao.setStyle("-fx-background-color:#fddbaf");
		botao.setOnAction(value -> {
			try {
				TelaCadastroPaciente();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Apertou bot�o CADASTRO");
		});
		botao.setOnMouseEntered(value -> {
			botao.setStyle("-fx-background-color:#87dff0");
		});
		botao.setOnMouseExited(value -> {
			botao.setStyle("-fx-background-color:#fddbaf");
		});

		GridPane gridPane = new GridPane();
		gridPane.add(tblClientes, 0, 0, 1, 1);
		gridPane.add(botao, 1, 0);
		gridPane.setHgap(20);
		gridPane.setAlignment(Pos.CENTER);
		root.setCenter(gridPane);
	}

	/*
	 * Monta painel com paisagens
	 */
	private void montaPainelPaisagens() {
		int nRandom = (int) (Math.random() * 9);
		String n =  String.format("%1d", nRandom);
		Image imagem = new Image(MainClass.caminho() +"/Paisagem" + n + ".jpg");
		ImageView imageView = new ImageView(imagem);
		imageView.fitHeightProperty().bind(root.heightProperty());
		imageView.fitWidthProperty().bind(root.widthProperty());
		imageView.setPreserveRatio(true);
		GridPane grid = new GridPane();
		grid.add(imageView, 0, 0);
		grid.setAlignment(Pos.CENTER);
		root.setCenter(grid);
	}

	/*
	 * Painel com registros do paciente
	 */
	private GridPane montaPainelRegistros() {
		double l = 140;
		Image imMedidas = new Image(MainClass.caminho() +"/_medidas.png");
		ImageView ivMedidas = new ImageView(imMedidas);
		ivMedidas.setFitHeight(l);
		ivMedidas.setFitWidth(l);
		Button btnMedidas = new Button();
		btnMedidas.setGraphic(ivMedidas);
		btnMedidas.setShape(new Circle(l));
		btnMedidas.setMinSize(l, l);
		btnMedidas.setMaxSize(l, l);
		btnMedidas.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		btnMedidas.setOnAction(value -> {
			try {
				TelaMedidas();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("apertou bot�o medidas");
		});
		btnMedidas.setOnMouseEntered(value -> {
			btnMedidas.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#87dff0");
		});
		btnMedidas.setOnMouseExited(value -> {
			btnMedidas.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		});

		Image imHabitos = new Image(MainClass.caminho() +"/_habitos.png");
		ImageView ivHabitos = new ImageView(imHabitos);
		ivHabitos.setFitHeight(l);
		ivHabitos.setFitWidth(l);
		Button btnHabitos= new Button();
		btnHabitos.setGraphic(ivHabitos);
		btnHabitos.setShape(new Circle(l));
		btnHabitos.setMinSize(l, l);
		btnHabitos.setMaxSize(l, l);
		btnHabitos.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		btnHabitos.setOnAction(value -> {
			try {
				TelaHabitos();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("apertou bot�o H�bitos");
		});
		btnHabitos.setOnMouseEntered(value -> {
			btnHabitos.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#87dff0");
		});
		btnHabitos.setOnMouseExited(value -> {
			btnHabitos.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		});

		Image imDietaHabitual= new Image(MainClass.caminho() +"/_dietahabitual.png");
		ImageView ivDietaHabitual = new ImageView(imDietaHabitual);
		ivDietaHabitual.setFitHeight(l);
		ivDietaHabitual.setFitWidth(l);
		Button btnDietaHabitual= new Button();
		btnDietaHabitual.setGraphic(ivDietaHabitual);
		btnDietaHabitual.setShape(new Circle(l));
		btnDietaHabitual.setMinSize(l, l);
		btnDietaHabitual.setMaxSize(l, l);
		btnDietaHabitual.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		btnDietaHabitual.setOnAction(value -> {
			System.out.println("apertou bot�o DietaHabitual");
		});
		btnDietaHabitual.setOnMouseEntered(value -> {
			btnDietaHabitual.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#87dff0");
		});
		btnDietaHabitual.setOnMouseExited(value -> {
			btnDietaHabitual.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		});

		Image imExames= new Image(MainClass.caminho() +"/_exames.png");
		ImageView ivExames = new ImageView(imExames);
		ivExames.setFitHeight(l);
		ivExames.setFitWidth(l);
		Button btnExames= new Button();
		btnExames.setGraphic(ivExames);
		btnExames.setShape(new Circle(l));
		btnExames.setMinSize(l, l);
		btnExames.setMaxSize(l, l);
		btnExames.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		btnExames.setOnAction(value -> {
			System.out.println("apertou bot�o Exames");
		});
		btnExames.setOnMouseEntered(value -> {
			btnExames.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#87dff0");
		});
		btnExames.setOnMouseExited(value -> {
			btnExames.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		});

		Image imSinaiseSintomas= new Image(MainClass.caminho() +"/_sinaisesintomas.png");
		ImageView ivSinaiseSintomas = new ImageView(imSinaiseSintomas);
		ivSinaiseSintomas.setFitHeight(l);
		ivSinaiseSintomas.setFitWidth(l);
		Button btnSinaiseSintomas= new Button();
		btnSinaiseSintomas.setGraphic(ivSinaiseSintomas);
		btnSinaiseSintomas.setShape(new Circle(l));
		btnSinaiseSintomas.setMinSize(l, l);
		btnSinaiseSintomas.setMaxSize(l, l);
		btnSinaiseSintomas.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		btnSinaiseSintomas.setOnAction(value -> {
			System.out.println("apertou bot�o SinaiseSintomas");
		});
		btnSinaiseSintomas.setOnMouseEntered(value -> {
			btnSinaiseSintomas.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#87dff0");
		});
		btnSinaiseSintomas.setOnMouseExited(value -> {
			btnSinaiseSintomas.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		});

		Image imAlimentosComplementares = new Image(MainClass.caminho() +"/_alimentoscomplementares.png");
		ImageView ivAlimentosComplementares = new ImageView(imAlimentosComplementares);
		ivAlimentosComplementares.setFitHeight(l);
		ivAlimentosComplementares.setFitWidth(l);
		Button btnAlimentosComplementares= new Button();
		btnAlimentosComplementares.setGraphic(ivAlimentosComplementares);
		btnAlimentosComplementares.setShape(new Circle(l));
		btnAlimentosComplementares.setMinSize(l, l);
		btnAlimentosComplementares.setMaxSize(l, l);
		btnAlimentosComplementares.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		btnAlimentosComplementares.setOnAction(value -> {
			System.out.println("apertou bot�o AlimentosComplementares");
		});
		btnAlimentosComplementares.setOnMouseEntered(value -> {
			btnAlimentosComplementares.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#87dff0");
		});
		btnAlimentosComplementares.setOnMouseExited(value -> {
			btnAlimentosComplementares.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		});

		Image imResumoGeral = new Image(MainClass.caminho() +"/_resumogeral.png");
		ImageView ivResumoGeral = new ImageView(imResumoGeral);
		ivResumoGeral.setFitHeight(l);
		ivResumoGeral.setFitWidth(l);
		Button btnResumoGeral= new Button();
		btnResumoGeral.setGraphic(ivResumoGeral);
		btnResumoGeral.setShape(new Circle(l));
		btnResumoGeral.setMinSize(l, l);
		btnResumoGeral.setMaxSize(l, l);
		btnResumoGeral.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		btnResumoGeral.setOnAction(value -> {
			try {
				ResumoGeral();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JRException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("apertou bot�o ResumoGeral");
		});
		btnResumoGeral.setOnMouseEntered(value -> {
			btnResumoGeral.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#87dff0");
		});
		btnResumoGeral.setOnMouseExited(value -> {
			btnResumoGeral.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		});

		Image imAlimentos = new Image(MainClass.caminho() +"/_alimentos.png");
		ImageView ivAlimentos = new ImageView(imAlimentos);
		ivAlimentos.setFitHeight(l);
		ivAlimentos.setFitWidth(l);
		Button btnAlimentos= new Button();
		btnAlimentos.setGraphic(ivAlimentos);
		btnAlimentos.setShape(new Circle(l));
		btnAlimentos.setMinSize(l, l);
		btnAlimentos.setMaxSize(l, l);
		btnAlimentos.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		btnAlimentos.setOnAction(value -> {
			System.out.println("apertou bot�o Alimentos");
		});
		btnAlimentos.setOnMouseEntered(value -> {
			btnAlimentos.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#87dff0");
		});
		btnAlimentos.setOnMouseExited(value -> {
			btnAlimentos.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#fddbaf");
		});

		GridPane grid = new GridPane();
		grid.add(btnMedidas, 0, 0, 1, 1);
		grid.add(btnHabitos, 1, 0, 1, 1);
		grid.add(btnDietaHabitual, 2, 0, 1, 1);
		grid.add(btnExames, 0, 1, 1, 1);
		grid.add(btnSinaiseSintomas, 1, 1, 1, 1);
		grid.add(btnAlimentosComplementares, 2, 1, 1, 1);
		grid.add(btnResumoGeral, 6, 0, 1, 1);
		grid.add(btnAlimentos, 6, 1, 1, 1);
		grid.setHgap(20);
		grid.setVgap(10);
		return grid;
	}

	/*
	 * Painel com imagens de nutri��o
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
	 * Gera bot�es sequenciais com datas das consultas
	 */
	private Button Botao(String dataConsulta) {
		Button btnDate = new Button(dataConsulta);
		btnDate.setFont(new Font("Leelawadee UI Semilight", 20));
		btnDate.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#ffa000");
		btnDate.setPrefSize(160, 50);
		btnDate.setAlignment(Pos.CENTER);
		btnDate.setOnAction(value -> {
			dataSelecionada = btnDate.getText();
			lblDataSelecionada.setText(dataSelecionada);
			System.out.println("apertou bot�o data=" + dataSelecionada);
		});
		btnDate.setOnMouseEntered(value -> {
			btnDate.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#87dff0");
		});
		btnDate.setOnMouseExited(value -> {
			btnDate.setStyle("-fx-background-radius: 15 15 15 15; -fx-border-radius: 15 15 15 15; -fx-background-color:#ffa000");
		});
		return btnDate;
	}

	/*
	 * Menu de bot�es lateral � esquerda da tela
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
	 * Monta bot�es no painel lateral
	 */
	private HBox Item(String nome) {
		Image imagem = new Image(MainClass.caminho() +"/"+ nome +".png");
		ImageView imageView = new ImageView(imagem);
		imageView.setFitWidth(130);
		imageView.setFitHeight(130);
		Button btn = new Button();
		btn.setGraphic(imageView);
		btn.setStyle("-fx-background-color:#35145d");
		btn.setOnAction(value -> { //Aciona bot�es do painel para chamar os procedimentos
			switch (Integer.valueOf(nome)) {
			case 1: {
				try {
					montaPainelClientes();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			case 2: {
				montaPainelPrincipal();
				break;
			}
			case 3:{
				montaPainelPaisagens();
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
				System.out.println("apertou bot�o:"+ nome);
			}
		});
		Pane paneIndicator = new Pane();
		paneIndicator.setStyle("-fx-background-color:#000000");
		menuDecorator(btn, paneIndicator);
		HBox hbox = new HBox(paneIndicator,btn);
		return hbox;
	}

	/*
	 * Trata eventos do mouse sobre os bot�es
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
//    private List<ClienteNomeSexoEmail> leClientes(String nome) {
//    	Scanner scanner;
//    	List<ClienteNomeSexoEmail> lista = new ArrayList<>();
//    	try {
//			scanner = new Scanner(new FileInputStream("clientes.txt"));
//	    	while(scanner.hasNextLine()) {
//	    		String[] textoSeparado;
//	    		String token = scanner.nextLine();
//	    		textoSeparado = token.split(";");
//	        	ClienteNomeSexoEmail cliente = new ClienteNomeSexoEmail(textoSeparado[0], textoSeparado[1], textoSeparado[2], textoSeparado[3]);
//	        	if (nome != "") {
//	        		if (nome.equals(textoSeparado[0])) {
//	    	        	lista.add(cliente);	        			
//	        		}
//	        	} else {
//		        	lista.add(cliente);
//	        	}
//	    	}
//	    	return lista;
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			return null;
//		}
//    }

	/*
	 * Rotina de leitura da base de dados de clientes e datas de consultas
	 */
    private List<ClienteConsultas> leConsultasClientes(String nomeCliente, String dataSelecionada) {
    	Scanner scanner;
    	int contador = 0;
		List<ClienteConsultas> lista = new ArrayList<>();
    	try {
			scanner = new Scanner(new FileInputStream("clientesconsultas.txt"));
	    	while(scanner.hasNextLine()) {
	    		String[] textoSeparado;
	    		String token = scanner.nextLine();
	    		textoSeparado = token.split(";");
	    		ClienteConsultas clienteConsulta = new ClienteConsultas(textoSeparado[0], textoSeparado[1], textoSeparado[2], textoSeparado[3], textoSeparado[4], textoSeparado[5]);
	    		if (dataSelecionada == "x") {
		    		if (nomeCliente.equals(clienteConsulta.getNomeCliente())) {
			    		lista.add(clienteConsulta);
			    		contador++;
		    		}
	    		} else {
		    		if (nomeCliente.equals(clienteConsulta.getNomeCliente()) && dataSelecionada.equals(clienteConsulta.getDataConsulta())) {
			    		lista.add(clienteConsulta);
			    		contador++;
		    		}
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

    public void TelaCadastroPaciente() throws Exception {
    	FXMLLoader fxmlTela = new FXMLLoader(getClass().getResource(MainClass.caminho() +"/frmTelaCadastroPaciente.fxml"));
    	BorderPane root = (BorderPane) fxmlTela.load();
    	Scene scene = new Scene(root);
    	Stage stage = new Stage();
    	scene.setFill(Color.TRANSPARENT);
    	stage.setScene(scene);
    	stage.setResizable(false);
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.initStyle(StageStyle.TRANSPARENT);
    	stage.show();
    }

    public void TelaMedidas() throws Exception {
    	FXMLLoader fxmlTela = new FXMLLoader(getClass().getResource(MainClass.caminho() +"/frmTelaMedidas.fxml"));
    	ScrollPane root = (ScrollPane) fxmlTela.load();
    	Stage stage = new Stage();
    	Scene scene = new Scene(root);
    	scene.setFill(Color.TRANSPARENT);
    	stage.setScene(scene);
    	stage.setResizable(false);
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.initStyle(StageStyle.TRANSPARENT);
    	stage.show();
    }

    public void TelaHabitos() throws Exception {
    	FXMLLoader fxmlTela = new FXMLLoader(getClass().getResource(MainClass.caminho() +"/frmHabitos.fxml"));
    	ScrollPane root = (ScrollPane) fxmlTela.load();
    	Stage stage = new Stage();
    	Scene scene = new Scene(root);
    	scene.setFill(Color.TRANSPARENT);
    	stage.setScene(scene);
    	//stage.setResizable(false);
    	stage.initModality(Modality.APPLICATION_MODAL);
    	stage.initStyle(StageStyle.TRANSPARENT);
    	stage.show();
    }

    private void ResumoGeral() throws IOException, JRException, SQLException {
//    	List<ClienteNomeSexoEmail> cliente = new ArrayList<ClienteNomeSexoEmail>();
//    	cliente = leClientes(clienteSelecionado);
//    	ClienteNomeSexoEmail cliente1 = cliente.get(0);
//    	System.out.println("cliente= "+ cliente1.toString());

    	clsAcessaDB db = new clsAcessaDB();
    	Cliente cliente = db.leCliente(MainClass.idCliente);
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("nomeCliente", cliente.getTXTNOMECLIENTE());
    	params.put("sexoCliente", cliente.getTXTSEXOCLIENTE());
    	params.put("emailCliente", cliente.getTXTEMAILCLIENTE());
    	params.put("objetivoCliente", cliente.getTXTOBJETIVOCLIENTE());
    	List<ClienteConsultas> consultasCliente = new ArrayList<>();
    	consultasCliente = leConsultasClientes(clienteSelecionado, dataSelecionada);
    	if (consultasCliente != null) {
    		ClienteConsultas consulta = consultasCliente.get(0);
    		params.put("estadoNutricional", consulta.getEstadoNutricional());
    		params.put("planoAlimentar", consulta.getPlanoAlimentar());
    		params.put("prescricaoDietetica", consulta.getPrescricaoDietetica());
    		params.put("recomendacoes", consulta.getRecomendacoes());
    	} else {
    		params.put("estadoNutricional", "");
    		params.put("planoAlimentar", "");
    		params.put("prescricaoDietetica", "");
    		params.put("recomendacoes", "");
    	}
    	params.put("idadeCliente", "33");
    	params.put("pesoCliente", "65");

    	InputStream input = new FileInputStream("PlanoAlimentar.jrxml");

    	Scene scene = root.getScene();
    	scene.setCursor(Cursor.WAIT);

    	JasperDesign jasperDesign = JRXmlLoader.load(input);
    	JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
    	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
    	JasperViewer.viewReport(jasperPrint, false);

    	scene.setCursor(Cursor.DEFAULT);
    }
}
