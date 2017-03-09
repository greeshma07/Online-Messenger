package application;

import java.util.ArrayList;
import java.util.Stack;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MessengerMethods implements MessengerInterface{
	
	public String message1 = new String();
	static TextFlow hisContent = new TextFlow();
	static Stack<HistoryContent> stack = new Stack<HistoryContent>();
	
	public MessengerMethods() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param t
	 * @param stack
	 */
	public MessengerMethods(TextFlow t, Stack<HistoryContent> stack) {
		super();
		MessengerMethods.hisContent = t;
		MessengerMethods.stack = stack;
	}
	
	@Override
	public void changeHistoryPanelColor(String windowColor,String fontColor,String name) {
		// TODO Auto-generated method stub		
		HistoryContent historyContent = new HistoryContent();
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	hisContent.getChildren().clear();
		    }
		});
		
		for(int i=0;i<stack.size();i++){
			historyContent = stack.get(i);
			Text time = new Text();
			time.setText(historyContent.getLocalTime()+"\n");
			Text textname = new Text();
			Text msg = new Text();
			msg.setText(" "+historyContent.getMessage()+"\n");
			if(name.equalsIgnoreCase(historyContent.getName())){
				textname.setFill(Paint.valueOf(windowColor));
				historyContent.setWindowColor(windowColor);
				textname.setText(historyContent.getName());
				msg.setFill(Paint.valueOf(fontColor));
				historyContent.setColor(fontColor);
			}
			else{
				textname.setFill(Paint.valueOf(historyContent.getWindowColor()));
				textname.setText(historyContent.getName());		
				msg.setFill(Paint.valueOf(historyContent.getColor()));
			}	
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			        hisContent.getChildren().addAll(time,textname,msg);	
			    }
			});			
		}
	}

	@Override
	public void sendMessage(String sendTime, String text, String windowColor, String textColor,String name) {
		// TODO Auto-generated method stub
		HistoryContent historyContent = new HistoryContent();
		Text time = new Text();
		time.setText(sendTime+"\n");
		Text userName = new Text();
		userName.setFill(Paint.valueOf(windowColor));
		userName.setText(name);
		Text msg = new Text();
		msg.setText(" "+text+"\n");
		msg.setFill(Paint.valueOf(textColor));
		historyContent.setLocalTime(sendTime);
		historyContent.setName(name);
		historyContent.setMessage(text);
		historyContent.setWindowColor(windowColor);
		historyContent.setColor(textColor);
		stack.push(historyContent);	
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	hisContent.getChildren().addAll(time,userName,msg);	
		    }
		});
		//changeHistoryPanelColor(windowColor, textColor,name);
	}

	@Override
	public void changeFontSize(TextArea chatSrn, int value) {
		// TODO Auto-generated method stub
		switch(value){
		case 1:	chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 12));
				break;
		case 2: chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 17));
				break;
		case 3: chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 22));
				break;
		case 4:	chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 27));
				break;
		case 5: chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 32));
				break;
		case 6: chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 37));
				break;
		case 7:	chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 42));
				break;
		case 8: chatSrn.setFont(Font.font(null, FontWeight.NORMAL, value + 47));
				break;
		}				
	}
	
	@Override
	public void decodeContent(ArrayList<String> message) {
		// TODO Auto-generated method stub
		String sendTime;
		String text;
		String windowColor;
		String textColor;
		String name;
		if(message.size() == 3){
			windowColor = message.get(0);
			textColor = message.get(1);
			name = message.get(2);
			changeHistoryPanelColor(windowColor, textColor,name);			
		}
		else{
			sendTime = message.get(0);
			text = message.get(1);
			windowColor = message.get(2);
			textColor = message.get(3);
			name = message.get(4);
			sendMessage(sendTime, text, windowColor, textColor,name);
		}				
	}

	@Override
	public void clearHistory() {
		// TODO Auto-generated method stub
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	hisContent.getChildren().clear();
		    	stack.clear();
		    }
		});		
	}	
}
