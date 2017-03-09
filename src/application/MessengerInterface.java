package application;

import java.util.ArrayList;

import javafx.scene.control.TextArea;

public interface MessengerInterface {	
	
	public void changeHistoryPanelColor(String windowColor,String fontColor,String name);
	public void sendMessage(String sendTime,String text,String windowColor, String textColor,String name);
	public void changeFontSize(TextArea chatSrn, int value);	
	public void decodeContent(ArrayList<String> message);
	public void clearHistory();
}
