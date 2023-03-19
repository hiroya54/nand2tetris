package vmTranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
	private List<String> commands = new ArrayList<>();
	private int currentCommandNum=0;
	private String currentCommand="";
	
	Parser(File file) throws IOException{
		//ファイル整形
		//コメントアウトと空行を削除
		removeCommentOut(file);
		currentCommand = commands.get(currentCommandNum);
	}
	public void removeCommentOut(File file) throws IOException{
		//ファイル読み込み
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		String command = "";
		while((command = br.readLine()) != null) {
			//コメントアウトと空行を削除
			if(command.length()==0) continue;
			else if(command.substring(0, 2).equals("//")) continue;
			
			//コマンドのコメントアウトと前後の空白を削除
			command = command.trim();
			int comment = command.indexOf("//");
			if(comment!=-1) {
				command = command.substring(comment,command.length());
			}
			commands.add(command);
		}
		br.close();
	}
	public boolean hasMoreCommands() {
		if(currentCommandNum<commands.size()) return true;
		else return false;
	}
	
	public void advance() {
		currentCommandNum += 1;
		if(this.hasMoreCommands()) {
			currentCommand = commands.get(currentCommandNum);
		}
	}
	
	public String commandType() {
		if(currentCommand.matches("add|sub|neg|eq|gt|lt|and|or|not")){
			return "C_ARITHMETIC";
		}else if(currentCommand.startsWith("push")) {
			return "C_PUSH";
		}else if(currentCommand.startsWith("pop")) {
			return "C_POP";
		}else {
			return "NG";
		}
	}
	
	public String arg1() {
		if(this.commandType()=="C_ARITHMETIC") {
			return currentCommand;
		}else if(this.commandType().matches("C_PUSH|C_POP")) {
			return currentCommand.split(" ")[1];
		}else {
			return "NG";
		}
		
	}
	
	public int arg2() {
		if(this.commandType().matches("C_PUSH|C_POP")) {
			return Integer.parseInt(currentCommand.split(" ")[2]);
		}else {
			return -1;
		}
	}
	public List<String> getCommands() {
		return commands;
	}
	public void setCommands(List<String> commands) {
		this.commands = commands;
	}
	public int getCurrentCommandNum() {
		return currentCommandNum;
	}
	public void setCurrentCommandNum(int currentCommandNum) {
		this.currentCommandNum = currentCommandNum;
	}
	public String getCurrentCommand() {
		return currentCommand;
	}
	public void setCurrentCommand(String currentCommand) {
		this.currentCommand = currentCommand;
	}
	
	

}
