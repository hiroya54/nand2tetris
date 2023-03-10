package assembler;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args){
		
		try {
			String inFile="/Applications/Eclipse_2022-09.app/Contents/workspace/nand2tetris/file/06/Add/Add.asm";
			//ファイルのパスを指定する
			File file = new File(inFile);
			//ファイル存在確認
			if(!file.exists()) {
				System.out.println("ファイルが存在しません");
				return;
			}
			Parse p = new Parse(file);
			
			//コマンドを読み込む
			//コマンドがない場合は終了する
			/*
			if(p.hasMoreCommands()) p.advance();
			else return;
			
			System.out.println(p.commandType());
			*/
			
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
