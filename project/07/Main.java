package vmTranslator;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			//入力ファイル準備
			System.out.print("入力ファイル名:");
			String inFile = sc.next();
			File input = new File(inFile);
			
			if(!input.exists()) {
				System.out.println("ファイルが存在しません");
				return;
			}
	
			//ファイル整形
			Parser p = new Parser(input);
		
			//出力ファイル準備
			System.out.print("出力ファイル名:");
			String outFile = sc.next();
			CodeWriter cw = new CodeWriter(outFile);
			
			while(p.hasMoreCommands()) {
				if(p.commandType().equals("C_ARITHMETIC")) {
					cw.writeArithmetc(p);	
				}else if(p.commandType().equals("C_PUSH")) {
					cw.writePushPop(p);
				}
				p.advance();
			}
			cw.close();	
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
