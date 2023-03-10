package assembler;

public class Code {
	
	public String dest(String dest) {
		
		if(dest.equals("null")) return "000";
		if(dest.equals("M")) return "001";
		if(dest.equals("D")) return "010";
		if(dest.equals("MD") | dest.equals("DM")) return "011";
		if(dest.equals("A")) return "100";
		if(dest.equals("AM") | dest.equals("MA")) return "101";
		if(dest.equals("AD") | dest.equals("DA")) return "110";
		if(dest.equals("AMD") | dest.equals("ADM") |dest.equals("MAD")
				| dest.equals("MDA") | dest.equals("DAM") |dest.equals("DMA")) return "111";
		else return "NG";
	}
	
	public String comp(String comp) {
		if(comp.equals("0")) return "0101010";
		if(comp.equals("1")) return "0111111";
		if(comp.equals("-1")) return "0111010";
		if(comp.equals("D")) return "0001100";
		if(comp.equals("A")) return "0110000";
		if(comp.equals("!D")) return "0001101";
		if(comp.equals("!A")) return "0110001";
		if(comp.equals("-D")) return "0001111";
		if(comp.equals("-A")) return "0110011";
		if(comp.equals("D+1")| comp.equals("1+D")) return "0011111";
		if(comp.equals("A+1")| comp.equals("1+A")) return "0110111";
		if(comp.equals("D-1")| comp.equals("-1+D")) return "0001110";
		if(comp.equals("A-1")| comp.equals("-1+A")) return "0110010";
		if(comp.equals("A+D") | comp.equals("D+A")) return "0000010";
		if(comp.equals("D-A") | comp.equals("-A+D")) return "0010011";
		if(comp.equals("A-D") | comp.equals("-D+A")) return "0000111";
		if(comp.equals("D&A") | comp.equals("A&D")) return "0000000";
		if(comp.equals("D|A") | comp.equals("A|D")) return "0010101";
		if(comp.equals("M")) return "1110000";
		if(comp.equals("!M")) return "1110001";
		if(comp.equals("-M")) return "1110011";
		if(comp.equals("M+1") | comp.equals("1+M")) return "1110111";
		if(comp.equals("M-1") | comp.equals("-1+M")) return "1110010";
		if(comp.equals("D+M") | comp.equals("M+D")) return "1000010";
		if(comp.equals("D-M") | comp.equals("-M+D")) return "1010011";
		if(comp.equals("M-D") | comp.equals("-D+M")) return "1000111";
		if(comp.equals("D&M") | comp.equals("M&D")) return "1000000";
		if(comp.equals("D|M") | comp.equals("D|M")) return "1010101";
		else return "NG";
	}
	
	public String jump(String jump) {
		if(jump.equals("null")) return "000";
		if(jump.equals("JGT")) return "001";
		if(jump.equals("JEQ")) return "010";
		if(jump.equals("JGE")) return "011";
		if(jump.equals("JLT")) return "100";
		if(jump.equals("JNE")) return "101";
		if(jump.equals("JLE")) return "110";
		if(jump.equals("JMP")) return "111";
		else return "NG";
	}

}
