package vmTranslator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			//入力ファイル準備
			System.out.print("入力ファイル名:");
			String inFile = sc.next();
			File input = new File(inFile);
			ArrayList<File> vmFile = new ArrayList<>();
			String outFile = "";
			if(input.isFile()) {
				if(input.getName().endsWith(".vm")) {
					vmFile.add(input);
					outFile = inFile.substring(0,inFile.indexOf(".vm"));
				}else {
					System.out.println("vmファイルではありません");
					return;
				}
			}else if(input.isDirectory()) {
				File[] list = input.listFiles();
				if(list != null) {
					for(File file:list) {
						if(file.getName().endsWith(".vm")) {
							vmFile.add(file);
						}
					}
				}else {
					System.out.println("ファイルが存在しません");
					return;
				}
				if(inFile.endsWith("/")) {
					outFile = inFile;
					outFile = outFile.substring(0,outFile.length()-1);
				}else {
					outFile = inFile;
				}
				String file = outFile.substring(outFile.lastIndexOf("/")+1);
				outFile = outFile + "/" + file;
			}
			if(vmFile.size()==0) {
				System.out.println("vmファイルが存在しません");
				return;
			}
			//出力ファイル準備
			outFile = outFile+".asm";
			CodeWriter cw = new CodeWriter(outFile);
			System.out.println("出力ファイル名:"+ outFile);
			
			cw.writeInit();
			
			for(File fileName:vmFile) {
				String currentFileName=fileName.getName();
				currentFileName=currentFileName.substring(currentFileName.lastIndexOf("/")+1,currentFileName.lastIndexOf("."));
				cw.setFileName(currentFileName);
				System.out.println("現在のファイル名:"+currentFileName);
				
				//ファイル整形
				Parser p = new Parser(fileName);
				
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
					}else if(p.commandType().equals("C_CALL")){
						cw.writeCall(p.arg1(), p.arg2());
					}else if(p.commandType().equals("C_RETURN")) {
						cw.writeReturn();
					}
					p.advance();
				}
			}
			cw.close();	
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
