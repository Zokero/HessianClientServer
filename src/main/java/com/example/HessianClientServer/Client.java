package com.example.HessianClientServer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class Client extends Application {

    private GridPane root;
    public static IGuessTheNumberService service;
    private Parent parent;
    public String playerName = UUID.randomUUID().toString().substring(0,8);

    @Bean
    public HessianProxyFactoryBean hessianInvoker() {
        HessianProxyFactoryBean invoker = new HessianProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8080/booking123");
        invoker.setServiceInterface(IGuessTheNumberService.class);
        return invoker;
    }

    private Parent createContent() {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        TextField textField = new TextField();
        Button button = new Button("CHECK");
        button.setPrefWidth(200);
        button.setPrefHeight(50);
        button.setDisable(true);
        button.setOnAction(event -> {
            String shootNumber = textField.getText();
            String response="";
            try{
                response = service.sendNumber(Integer.parseInt(shootNumber));
                textField.clear();
                textArea.appendText("You bet on " + shootNumber + "\n");
                textArea.appendText(response);
                List<Integer> list2 = service.getNumber();
                String g = list2.stream().map(Object::toString).collect(Collectors.joining(","));
                textArea.appendText(g + "\n");
                //button.setDisable(true);
            }catch (NumberFormatException e){
                textArea.appendText("This is not a number\n");
                textField.clear();
            }
        });
        Button button1 = new Button("Start/Restart game");
        button1.setPrefWidth(200);
        button1.setPrefHeight(30);
        button1.setOnAction(event -> {
            if(service.startGame()){
                textArea.appendText("Welcome to the game " + playerName + "\n");
                List<Integer> list = service.getNumber();
                String s = list.stream().map(Object::toString).collect(Collectors.joining(","));
                textArea.appendText(s + "\n");
                button.setDisable(false);
            }
        });
        Button button2 = new Button("ResetServerFlag");
        button2.setPrefWidth(200);
        button2.setPrefHeight(30);
        button2.setOnAction(event -> {
            service.restart();
        });


        root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));
        root.setPrefSize(300, 300);
        root.add(button, 0, 3);
        root.add(button1, 0, 4);
        root.add(button2, 0, 5);
        root.add(textField, 0, 2);
        root.add(textArea, 0, 1);
        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        parent = createContent();
        primaryStage.setTitle("Game");
        primaryStage.setScene(new Scene(parent, 400, 300));
        primaryStage.show();
        Runnable task = () -> {
            while (true) {
                try {
                    Thread.sleep(3000);
                    Platform.runLater(() -> {

                    });
                } catch (InterruptedException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(task).start();
    }
    public void initRoot(){

    }

    public static void main(String[] args) {
        service = SpringApplication
                .run(Client.class)
                .getBean(IGuessTheNumberService.class);
        Application.launch(args);
    }
}



