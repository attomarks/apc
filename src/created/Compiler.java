package created;
//import java.io.*;
import javacc.Parser;

class Compiler{
    public static void main(String args[]){

	String sourceName = null, option = null;
	boolean advance = false, assembly = false, ASTdebug = false, IRdebug = false, BlockDebug = false;
	if(args.length == 2){
	    sourceName = args[1];
	    option = args[0];
	    switch(option){
	    case "-A":
	    	advance = true;
	    	break;
	    case "-S":
	    	assembly = true;
	    	break;
	    case "-AST":
	    	ASTdebug = true;
	    	break;
	    case "-IR":
	    	IRdebug = true;
	    	break;
	    case "-BLK":
	    	BlockDebug = true;
	    	break;
	    case "-AIR" :
	    	advance = true;
	    	IRdebug = true;
	    	break;
	    default:
		System.out.println("Error : No such option \"" + option + "\".");
		System.exit(1);
	    }
	}else if(args.length == 1){
	    sourceName = args[0];
	}else{
	    System.out.println("Error : Invalid number of arguments.");
	    System.exit(1);
	}
	
	try{
	    //FileReader source;

	    if(advance || BlockDebug){
		//source = new FileReader(new File(sourceName));
		Parser.advancedParsing(sourceName,BlockDebug,IRdebug);
	    }
	    else if(ASTdebug){
		//source = new FileReader(new File(sourceName));
		Parser.ASTdebug(sourceName);
	    }
	    else if(IRdebug){
		//source = new FileReader(new File(sourceName));
		Parser.IRdebug(sourceName);
	    }
	    else {
	    	Parser.generalParsing(sourceName);
	    }
	    //source = new FileReader(new File(sourceName));
   	    //Parser.generalParsing(sourceName);

	    System.out.println("Generating assembly code...");
	    AssemblyGenerator.Generate();
	    
	    if(assembly) {
	    	System.exit(0);
	    }

	    Runtime r = Runtime.getRuntime();
	    Process p = r.exec("as Assembly.s -o Object.o");
	    p.waitFor();
	    p = r.exec("gcc Object.o -o a.out");
	    p.waitFor();
	    System.out.println("done.");
	    System.exit(0);
	    
	}catch(Exception e){
	    e.printStackTrace();
	    System.exit(1);
	}
    }
}
