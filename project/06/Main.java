package assembler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args){
		
		try {
			String inFile="/Applications/Eclipse_2022-09.app/Contents/workspace/nand2tetris/file/06/pong/PongL.asm";
			//ファイルのパスを指定する
			File file = new File(inFile);
			//ファイル存在確認
			if(!file.exists()) {
				System.out.println("ファイルが存在しません");
				return;
			}
			String outFile="/Applications/Eclipse_2022-09.app/Contents/workspace/nand2tetris/file/06/pong/PongL.hack";
			Path path = Paths.get(outFile);
			if(Files.exists(path)) {
				System.out.println("既存ファイルが存在するので処理をスキップしました。");
			} else {
				Files.createFile(path);
				System.out.println("ファイルを作成しました。");
			}
			PrintWriter pw = new PrintWriter(outFile);

			Parse p = new Parse(file);
			
			while(p.hasMoreCommands()) {
				//命令文を指定
				String command = p.instructions.get(p.currentCommandNum);
				
				//命令文のタイプに合わせて機械語に変換
				if(p.commandType().equals("A_COMMAND")) {
					//文頭の@を削除
					String numStr = command.substring(1,command.length());
					//2進数に変換
					String binary =Integer.toBinaryString(Integer.parseInt(numStr));
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
