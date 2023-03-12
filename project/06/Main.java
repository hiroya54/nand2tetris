package assembler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		try {
			//入力ファイル準備
			System.out.println("入力ファイル名：");
			String inFile=sc.next();
			//ファイルのパスを指定する
			File file = new File(inFile);
			//ファイル存在確認
			if(!file.exists()) {
				System.out.println("ファイルが存在しません");
				return;
			}
			
			//出力ファイル準備
			System.out.println("出力ファイル名：");
			String outFile=sc.next();
			File output = new File(outFile);
			PrintWriter pw = new PrintWriter(outFile);
			
			//first step
			Parse p = new Parse(file);
			SymbolTable symbols = new SymbolTable();
			//Lコマンドを対象にシンボルテーブルを作成
			while(p.hasMoreCommands()) {
				//命令文を指定
				String command = p.getInstructions().get(p.getCurrentCommandNum());
				//Lコマンドをシンボルテーブルに格納
				if(p.commandType().equals("L_COMMAND")) {
					String symbol = p.symbol();
					//現在の命令文のシンボルがシンボルテーブルに含まれない場合のみ追加
					if(!symbols.contains(symbol)) {
						//追加
						symbols.addEntry(symbol, p.getCurrentCommandNum()-symbols.getNumOfL());
						//追加したシンボルの数を管理
						symbols.setNumOfL(symbols.getNumOfL()+1);
						p.advance();
					}
				}else  {
					p.advance();
				}
			}
			//currentCommandNumを0に戻す
			p.reset();
			
			//second step
			while(p.hasMoreCommands()) {
				//命令文を指定
				String command = p.getInstructions().get(p.getCurrentCommandNum());
				
				//命令文のタイプに合わせて機械語に変換
				if(p.commandType().equals("A_COMMAND")) {
					//シンボルを取得
					String symbol = p.symbol();
					String binary="";
					if(symbol.matches("^[0-9]+$")) {
						//2進数に変換
						binary =Integer.toBinaryString(Integer.parseInt(symbol));
					}else {
						if(!symbols.contains(symbol)) {
							//シンボルテーブルに追加
							symbols.addEntry(symbol, symbols.getCurrentValMemory());
							symbols.setCurrentValMemory(symbols.getCurrentValMemory()+1);
						}
						//2進数に変換
						binary = Integer.toBinaryString(symbols.getAddress(symbol));
					}
					//16桁で出力
					String zr="";
					for(int i=0;i<16-binary.length();i++) {
						zr+="0";
					}
					pw.println(zr+binary);	
				}else if(p.commandType().equals("C_COMMAND")) {
					//命令文を各領域に分割し、機械語に変換
					Code code = new Code();
					String destBinary=code.dest(p.dest());
					String compBinary=code.comp(p.comp());
					String jumpBinary=code.jump(p.jump());
					//出力
					if(compBinary.equals("NG")|destBinary.equals("NG")|jumpBinary.equals("NG")) {
						System.out.println("NGがあります");
						pw.close();
						return;
					}else {
						pw.println("111"+compBinary+destBinary+jumpBinary);
					}
					
				}else if(p.commandType().equals("L_COMMAND")) {
					p.advance();
					continue;
				}else {
					System.out.println("不正な命令文です");
					pw.close();
					return;
				}
				p.advance();
			}
			pw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}		
	}
}
