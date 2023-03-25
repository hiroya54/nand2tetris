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
			
			/*
			for(String command:p.getCommands()) {
				System.out.println(command);
			}
			*/
			//出力ファイル準備
			System.out.print("出力ファイル名:");
			String outFile = sc.next();
			CodeWriter cw = new CodeWriter(outFile);
			
			
			//cw.writeInit();
			while(p.hasMoreCommands()) {
				System.out.println(p.getCurrentCommand());
				if(p.commandType().equals("C_ARITHMETIC")) {
					cw.writeArithmetc(p);	
				}else if(p.commandType().matches("C_PUSH|C_POP")) {
					cw.writePushPop(p);
				}else if(p.commandType().equals("C_LABEL")) {
					cw.writeLabel(p.arg1());
				}else if(p.commandType().equals("C_IF")) {
					cw.writeIf(p.arg1());
				}else if(p.commandType().equals("C_GOTO")) {
					cw.writeGoTo(p.arg1());
				}else if(p.commandType().equals("C_FUNCTION")) {
					cw.writeFunction(p.arg1(), p.arg2());
				}else if(p.commandType().equals("C_RETURN")) {
					cw.writeReturn();
				}
				p.advance();
			}
			cw.close();	
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
