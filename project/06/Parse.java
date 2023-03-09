package assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Parse{
	BufferedReader br;
	String inFile;
	String data;
	
	public Parse(String inFile) throws IOException{
		this.inFile = inFile;
		//ファイルのパスを指定する
		File file = new File(inFile);
		//ファイル存在確認
		if(!file.exists()) {
			System.out.println("ファイルが存在しません");
			return;
		}
		//ファイル読み込み
		FileReader fr = new FileReader(file);
		this.br = new BufferedReader(fr);
		
	}
	
	public boolean hasMoreCommands() throws IOException{
		String data;
		if((data = br.readLine())!=null) {
			this.data=data;
			return true;
		}
		else return false;
	}
	
	public void advance() throws IOException{
		
	}
	
	public String commandType() {
		System.out.println(data.charAt(0));
		if(data.charAt(0)=='@') return "A_COMMAND";
		else if(data.charAt(0)=='(') return "L_COMMAND";
		else return "C_COMMAND";
	}
}