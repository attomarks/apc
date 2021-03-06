package created;
import java.util.*;
import java.io.*;

public class AssemblyGenerator {
    static void Generate(){
	AssemblyCode.fileCreate();
	AssemblyCode.headTemp1();
	AssemblyCode.setString();
	AssemblyCode.headTemp2();
	//AssemblyCode.expandStack(IRGenerator.getStackSize());

	int func_num = 0;
	for(int i=0;i<IRGenerator.IRNumOfLines();i++){
	    
	    String[] IR = IRGenerator.IRRead(i);
	    //if((!IR[0].equals("rtn"))) System.out.println(IR[0]+","+IR[1]+","+IR[2]+","+IR[3]);
	    switch(IR[0]){
	    case "func":
		//int flame = IRGenerator.getFlame(func_num);
		func_num++;
		AssemblyCode.function(/*flame,*/IR[3]);
		break;
	    case "param":
		AssemblyCode.parameter(IR[3]);
		break;
	    case "arg":
		AssemblyCode.argument(IR[3]);
		break;
	    case "var":
		AssemblyCode.variable(IR[3]);
		break;
	    case "=": 
		AssemblyCode.assign(IR[3],IR[1]);
		break;
	    case "+":
		AssemblyCode.add(IR[1],IR[2],IR[3]);
		break;
	    case "-":
		AssemblyCode.sub(IR[1],IR[2],IR[3]);
		break;
	    case "*":
		AssemblyCode.mul(IR[1],IR[2],IR[3]);
		break;
	    case "/":
		AssemblyCode.div(IR[1],IR[2],IR[3]);
		break;
	    case "%":
		AssemblyCode.mod(IR[1],IR[2],IR[3]);
		break;
	    case "==":
		AssemblyCode.eq(IR[1],IR[2],IR[3]);
		break;
	    case "!=":
		AssemblyCode.ne(IR[1],IR[2],IR[3]);
		break;
	    case ">":
		AssemblyCode.gt(IR[1],IR[2],IR[3]);
		break;
	    case "<":
		AssemblyCode.lt(IR[1],IR[2],IR[3]);
		break;
	    case ">=":
		AssemblyCode.ge(IR[1],IR[2],IR[3]);
		break;
	    case "<=":
		AssemblyCode.le(IR[1],IR[2],IR[3]);
		break;
	    case "label":
		AssemblyCode.label(IR[3]);
		break;
	    case "jnz":
		AssemblyCode.jnz(IR[3]);
		break;
	    case "jmp":
		AssemblyCode.jmp(IR[3]);
		break;
	    case "jne":
		AssemblyCode.jne(IR[3]);
		break;
	    case "posf":
	    	AssemblyCode.suff(IR[2],IR[3]);
	    	break;
	    case "printf":
		//int address = SymbolTable.searchAddress(IR[3]);
		if(IR[2].equals("0")){
		    AssemblyCode.printf(IR[3]);
		}else{
		    AssemblyCode.printf(Integer.parseInt(IR[2]),IR[3]);
		}break;
	    case "rtnval":
	    	AssemblyCode.rtnval(IR[3]);
	    	break;
	    case "rtn":
	    	AssemblyCode.rtn();
	    	break;
	    }
	}
	AssemblyCode.footTemp();
    }
}
