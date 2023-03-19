package vmTranslator;

import java.io.IOException;
import java.io.PrintWriter;

public class CodeWriter {
	
	private String fileName;
	private PrintWriter pw;
	private int currentLine;
	
	CodeWriter(String outFile) throws IOException{
		pw = new PrintWriter(outFile);
		this.setFileName(outFile.substring(outFile.lastIndexOf("/")+1, outFile.lastIndexOf(".")));
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
				myWrite("D=M+D");
			}else if(p.getCurrentCommand().equals("sub")) {
				myWrite("D=M-D");
			}else if(p.getCurrentCommand().equals("and")) {
				myWrite("D=M&D");
			}else if(p.getCurrentCommand().equals("or")) {
				myWrite("D=M|D");
			}
			myWrite("M=D");
		}
		
	}
	public void writePushPop(Parser p) {
		if(p.commandType().equals("C_PUSH")) {
			if(p.arg1().equals("constant")) {
				myWrite("@"+p.arg2());
				myWrite("D=A");
				myWrite("@SP");
				myWrite("A=M");
				myWrite("M=D");
				myWrite("@SP");
				myWrite("M=M+1");
			}else if(p.arg1().matches("local|argument|this|that")) {
				if(p.arg1().equals("local")) {
					myWrite("@LCL");
				}else if(p.arg1().equals("argument")) {
					myWrite("@ARG");
				}else if(p.arg1().equals("this")) {
					myWrite("@THIS");
				}else if(p.arg1().equals("that")) {
					myWrite("@THAT");
				}
				myWrite("D=M");
				if(p.arg2()!=0) {
					myWrite("@"+p.arg2());
					myWrite("D=A+D");
				}
				myWrite("A=D");
				myWrite("D=M");
				myWrite("@SP");
				myWrite("A=M");
				myWrite("M=D");
				myWrite("@SP");
				myWrite("M=M+1");
			}else if(p.arg1().matches("pointer|temp")) {
				if(p.arg1().equals("pointer")) {
					myWrite("@"+(p.arg2()+3));
				}else if(p.arg1().equals("temp")) {
					myWrite("@"+(p.arg2()+5));
				}
				myWrite("D=M");
				myWrite("@SP");
				myWrite("A=M");
				myWrite("M=D");
				myWrite("@SP");
				myWrite("M=M+1");
			}else if(p.arg1().equals("static")) {
				myWrite("@16");
				if(p.arg2()!=0) {
					myWrite("D=A");
					myWrite("@"+p.arg2());
					myWrite("A=D+A");
				}
				myWrite("D=M");
				myWrite("@SP");
				myWrite("A=M");
				myWrite("M=D");
				myWrite("@SP");
				myWrite("AM=M+1");
				
			}
		}else if(p.commandType().equals("C_POP")) {
			if(p.arg1().matches("local|argument|this|that")) {
				if(p.arg1().equals("local")) {
					myWrite("@LCL");
				}else if(p.arg1().equals("argument")) {
					myWrite("@ARG");
				}else if(p.arg1().equals("this")) {
					myWrite("@THIS");
				}else if(p.arg1().equals("that")) {
					myWrite("@THAT");
				}
				myWrite("D=M");
				if(p.arg2()!=0) {
					myWrite("@"+p.arg2());
					myWrite("D=A+D");
				}
				myWrite("@R15");
				myWrite("M=D");
				myWrite("@SP");
				myWrite("AM=M-1");
				myWrite("D=M");
				myWrite("@R15");
				myWrite("A=M");
				myWrite("M=D");

			}else if(p.arg1().matches("pointer|temp")) {
				myWrite("@SP");
				myWrite("AM=M-1");
				myWrite("D=M");
				if(p.arg1().equals("pointer")) {
					myWrite("@"+(p.arg2()+3));
				}else if(p.arg1().equals("temp")) {
					myWrite("@"+(p.arg2()+5));
				}
				myWrite("M=D");
			}else if(p.arg1().equals("static")) {
				myWrite("@SP");
				myWrite("AM=M-1");
				myWrite("D=M");
				myWrite("@"+(16+p.arg2()));
				myWrite("M=D");
			}
		}
		
	}
	public void close() {
		pw.close();
	}

}
