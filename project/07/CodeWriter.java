package vmTranslator;

import java.io.IOException;
import java.io.PrintWriter;

public class CodeWriter {
	
	private String fileName="";
	private PrintWriter pw;
	
	CodeWriter(String outFile) throws IOException{
		
		this.setFileName(outFile);
		pw = new PrintWriter(fileName);
	}
	
	public void setFileName(String fileName) {
		this.fileName=fileName;
	}
	
	public void writeArithmetc(Parser p) {
		
		if(p.getCurrentCommand().startsWith("add")) {
			pw.println("@2");
			pw.println("D=A");
			pw.println("@SP");
			pw.println("M=M-D");
			pw.println("@SP");
			pw.println("D=M");
			pw.println("A=D");
			pw.println("D=M");
			pw.println("A=A+1");
			pw.println("D=D+M");
			pw.println("@SP");
			pw.println("A=M");
			pw.println("M=D");
			pw.println("@SP");
			pw.println("M=M+1");
			
		}
	}
	public void writePushPop(Parser p) {
		if(p.commandType().equals("C_PUSH")) {
			if(p.arg1().equals("constant")) {
				pw.println("@"+p.arg2());
				pw.println("D=A");
				pw.println("@SP");
				pw.println("A=M");
				pw.println("M=D");
				pw.println("@SP");
				pw.println("M=M+1");
			}
		}
		
	}
	public void close() {
		/*
		pw.println("(END)");
		pw.println("@END");
		pw.println("0;JMP");
		*/
		pw.close();
	}

}
