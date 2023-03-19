package vmTranslator;

import java.io.IOException;
import java.io.PrintWriter;

public class CodeWriter {
	
	private String fileName="";
	private PrintWriter pw;
	private int currentLine;
	
	CodeWriter(String outFile) throws IOException{
		
		this.setFileName(outFile);
		pw = new PrintWriter(fileName);
		currentLine=0;
	}
	public void myWrite(String command) {
		pw.println(command);
		currentLine+=1;
	}
	
	public void setFileName(String fileName) {
		this.fileName=fileName;
	}
	
	public void writeArithmetc(Parser p) {
		if(p.getCurrentCommand().equals("neg")) {
			myWrite("@SP");
			myWrite("A=M-1");
			myWrite("M=-M");
			
		}else if(p.getCurrentCommand().equals("not")) {
			myWrite("@SP");
			myWrite("A=M-1");
			myWrite("M=!M");
			
		}else if(p.getCurrentCommand().matches("eq|gt|lt")){
			myWrite("@SP");
			myWrite("AM=M-1");
			myWrite("D=M");
			myWrite("@SP");
			myWrite("AM=M-1");
			myWrite("D=M-D");
			myWrite("@"+(currentLine+9));
			if(p.getCurrentCommand().equals("eq")) {
				myWrite("D;JEQ");
			}else if(p.getCurrentCommand().equals("gt")) {
				myWrite("D;JGT");
			}else if(p.getCurrentCommand().equals("lt")) {
				myWrite("D;JLT");
			}
			myWrite("@SP");
			myWrite("A=M");
			myWrite("M=0");
			myWrite("@SP");
			myWrite("M=M+1");
			myWrite("@"+(currentLine+7));
			myWrite("0;JMP");
			myWrite("@SP");
			myWrite("A=M");
			myWrite("M=-1");
			myWrite("@SP");
			myWrite("M=M+1");
		}else if(p.getCurrentCommand().matches("add|sub|and|or")){
			myWrite("@SP");
			myWrite("AM=M-1");
			myWrite("D=M");
			myWrite("A=A-1");
			if(p.getCurrentCommand().equals("add")) {
				pw.println("//add");
				myWrite("D=M+D");
			}else if(p.getCurrentCommand().equals("sub")) {
				pw.println("//sub");
				myWrite("D=M-D");
			}else if(p.getCurrentCommand().equals("and")) {
				pw.println("//and");
				myWrite("D=M&D");
			}else if(p.getCurrentCommand().equals("or")) {
				pw.println("//or");
				myWrite("D=M|D");
			}
			myWrite("M=D");
		}
		
	}
	public void writePushPop(Parser p) {
		if(p.commandType().equals("C_PUSH")) {
			if(p.arg1().equals("constant")) {
				//System.out.println("push");
				myWrite("@"+p.arg2());
				myWrite("D=A");
				myWrite("@SP");
				myWrite("A=M");
				myWrite("M=D");
				myWrite("@SP");
				myWrite("M=M+1");
			}
		}
		
	}
	public void close() {
		/*
		myWrite("(END)");
		myWrite("@END");
		myWrite("0;JMP");
		*/
		pw.close();
	}

}
