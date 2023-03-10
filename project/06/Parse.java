package assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parse{
	int currentCommandNum=0;
	ArrayList<String> instructions = new ArrayList<>();
	
	public Parse(File file) throws IOException{
		//ファイル整形
		//コメントアウトと空白を削除
		removeWhiteSpace(file);
		
	}
	public void removeWhiteSpace(File file) throws IOException{
		//ファイル読み込み
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		//命令文のみ取り出してArrayListに格納
		String command;
		while((command=br.readLine())!=null) {
			
			//コメント行と空白行を削除
			if(command.length()==0) continue;
			else if(command.substring(0,2).equals("//")) continue;
			
			//命令のスペースとコメントを削除
			command=command.replaceAll(" |　", "");
			int comment = command.indexOf("//");
			if(comment!=-1) {
				command=(String)command.subSequence(0, comment);
			}
			instructions.add(command);
		}
		br.close();
	}
	
	public boolean hasMoreCommands() throws IOException{
		if(currentCommandNum < instructions.size()) return true;
		else return false;
	}
	
	public void advance() throws IOException{
		currentCommandNum+=1;
		
	}
	
	public String commandType() {
		String command = instructions.get(currentCommandNum);
		if(command.charAt(0)=='@') return "A_COMMAND";
		else if(command.charAt(0)=='(') return "L_COMMAND";
		else return "C_COMMAND";
	}
	public String dest() {
		String command = this.instructions.get(this.currentCommandNum);
		int indexOfEqual = command.indexOf("=");
		int indexOfSemicolon = command.indexOf(";");
		
		if(indexOfEqual!=-1 && indexOfSemicolon!=-1) {
			return command.substring(0,indexOfEqual);
		}else if(indexOfEqual!=-1) {
			return command.substring(0,indexOfEqual);
		}else if(indexOfSemicolon!=-1){	
			return "null";
		}else {
			return "NG";
		}
		
	}
	public String comp() {
		String command = this.instructions.get(this.currentCommandNum);
		int indexOfEqual = command.indexOf("=");
		int indexOfSemicolon = command.indexOf(";");
		
		if(indexOfEqual!=-1 && indexOfSemicolon!=-1) {
			return command.substring(indexOfEqual+1,indexOfSemicolon);
		}else if(indexOfEqual!=-1) {
			return command.substring(indexOfEqual+1,command.length());
		}else if(indexOfSemicolon!=-1){	
			return command.substring(indexOfEqual+1,indexOfSemicolon);
		}else {
			return "NG";
		}
		
	}
	public String jump() {
		String command = this.instructions.get(this.currentCommandNum);
		int indexOfEqual = command.indexOf("=");
		int indexOfSemicolon = command.indexOf(";");
		
		if(indexOfEqual!=-1 && indexOfSemicolon!=-1) {
			return command.substring(indexOfSemicolon+1,command.length());
		}else if(indexOfEqual!=-1) {
			return "null";
		}else if(indexOfSemicolon!=-1){	
			return command.substring(indexOfSemicolon+1,command.length());
		}else {
			return "NG";
		}
	
	}
}
