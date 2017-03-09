package server;
	
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Stack;

import application.HistoryContent;
import application.MessengerMethods;
import application.MyGUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextFlow;
 
public class Main extends Application{
	
	Stack<HistoryContent> stack = new Stack<HistoryContent>();
	Server server = new Server();
	final String serverName = "Greeshma: ";	
	
	@Override
	public void start(Stage primaryStage) {
		try {
					
			String title = "Server chat window";
			MyGUI myGUI = new MyGUI();
			BorderPane root = new BorderPane();	
			myGUI.layoutGUI(root,serverName);
			Scene scene = new Scene(root,500,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			ComboBox<String> windowColor = myGUI.getWindowStyle();
			ComboBox<String> fontSize = myGUI.getFont();
			ComboBox<String> fontColor = myGUI.getFontColor();

			Button sendBtn = myGUI.getSendBtn();
			Button clearBtn = myGUI.getClearBtn();
			
			TextFlow historyScrn = myGUI.getHistoryScreen();
			TextArea textarea = myGUI.getChatScreen();
			TextArea chatSrn = myGUI.getChatScreen();
			
			MessengerMethods messengerMethod = new MessengerMethods(historyScrn,stack);
			
			Platform.runLater(new Runnable() {
	            @Override
	            public void run() {
	            	chatSrn.requestFocus();
	            }
	        });
			
			root.getStyleClass().add("bkgColor");
			windowColor.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					String color;
					root.setStyle("-fx-background-color: "+windowColor.getValue()+";");
					if(fontColor.getValue().equalsIgnoreCase("color"))
						color = "black";
					else
						color = fontColor.getValue();
					messengerMethod.changeHistoryPanelColor(windowColor.getValue(),color,serverName);				
					ArrayList<String> list = new ArrayList<String>();
					list.add(windowColor.getValue());
					list.add(color);
					list.add(serverName);
					server.sendMessage(list);
				}				
			});	
			
			fontSize.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub		
					messengerMethod.changeFontSize(chatSrn, Integer.parseInt(fontSize.getValue()));
				}				
			});	
			
			fontColor.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					TextArea chatSrn = myGUI.getChatScreen();
					chatSrn.setStyle("-fx-text-fill:" +fontColor.getValue()+";");
					messengerMethod.changeHistoryPanelColor(windowColor.getValue(),
							fontColor.getValue(),serverName);
					ArrayList<String> list = new ArrayList<String>();
					list.add(windowColor.getValue());
					list.add(fontColor.getValue());
					list.add(serverName);
					server.sendMessage(list);
				}
				
			});	
			
			sendBtn.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub	
					String color;
					TextArea chatSrn = myGUI.getChatScreen();
					LocalTime time = LocalTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
					String sendTime = time.format(formatter);
					String text = chatSrn.getText();
					if(fontColor.getValue().equalsIgnoreCase("color"))
						color = "black";
					else
						color = fontColor.getValue();
					if(text !=null && !(text.equalsIgnoreCase(""))){
						messengerMethod.sendMessage(sendTime,text,
								windowColor.getValue(),color,serverName);
						chatSrn.clear();
						ArrayList<String> list = new ArrayList<String>();
						list.add(sendTime);
						list.add(text);
						list.add(windowColor.getValue());
						list.add(color);
						list.add(serverName);
						server.sendMessage(list);
					}						
				}				
			});	
			
			clearBtn.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub	
					TextArea chatSrn = myGUI.getChatScreen();
					chatSrn.clear();		
				}					
			});
			
			textarea.setOnKeyPressed(new EventHandler<KeyEvent>(){			 
			    @Override
			    public void handle(KeyEvent event) {
			    	String color;
			        if(event.getCode().equals(KeyCode.ENTER)) {
			        	TextArea chatSrn = myGUI.getChatScreen();
						LocalTime time = LocalTime.now();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
						String sendTime = time.format(formatter);
						String text = chatSrn.getText();
						if(fontColor.getValue().equalsIgnoreCase("color"))
							color = "black";
						else
							color = fontColor.getValue();
						if(text !=null && !(text.equalsIgnoreCase(""))){
							messengerMethod.sendMessage(sendTime,text,
									windowColor.getValue(),color,serverName);
							chatSrn.clear();
							ArrayList<String> list = new ArrayList<String>();
							list.add(sendTime);
							list.add(text);
							list.add(windowColor.getValue());
							list.add(color);
							list.add(serverName);
							server.sendMessage(list);
						}
						event.consume();
			        }
			    }
			});
						
			primaryStage.setTitle(title);
			primaryStage.setScene(scene);
			primaryStage.show();
			MyThread myThread = new MyThread(new Server());
			myThread.start();			
						
		} catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static void main(String[] args) {
		launch(args);	
	}	
}

class MyThread extends Thread{
	Server server;
	MyThread(Server server){
		this.server = server;
	}
	public void run (){	   
	   server.start();	   
   }
}