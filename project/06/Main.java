package assembler;

import java.io.IOException;

public class Main {

	public static void main(String[] args){
		
		try {
			//ファイル読み込み
			Parse p = new Parse("/Applications/Eclipse_2022-09.app/Contents/workspace/nand2tetris/file/06/add/Add.asm");
			
			//コマンドを読み込む
			//コマンドがない場合は終了する
			if(p.hasMoreCommands()) p.advance();
			else return;
			
			System.out.println(p.commandType());
			
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
