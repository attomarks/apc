package created;
import java.util.*;
import java.io.*;

public class IRGenerator {
    static List<String> var_list = new ArrayList<>();
    static List<Integer> var_address = new ArrayList<>();
    static String func_type = null;
    static int flame_size = 0;
    static List<Integer> func_flame = new ArrayList<>();
    static List<String> printStr = new ArrayList<>();
    public static List<String> IR = new ArrayList<>();
    static List<String> IRfrag = new ArrayList<>();
    static String IRtemp = "";
    static boolean Advance = false;
    
    public static void IRlabel(String label){
	Add("label,,,"+label);
    }

    public static void IRdef_fun(String type, String name){
	Add("func,,"+type+","+name);
	func_type = type;
    }

    static void IRdef_var(String type, String name){
	Add("var,,"+type+","+name);
    }
    
    public static void IRvar_ini(String type, String name, String value){
	switch(type){
	case "int": var_list.add(name);
	    flame_size += 4;
	    var_address.add(-flame_size);
	    break;
	case "double": var_list.add(name); flame_size += 8; break;
	default : System.out.println("Error in IRdef_vars.");
	}
	if(!(value.equals("null"))) Add("=,"+value+",,"+name);
    }

    static void IRparam(String type, String name){
	switch(type){
	case "int": var_list.add(name);
	    flame_size += 4;
	    var_address.add(-flame_size);
	    break;
	case "double": var_list.add(name); flame_size += 8; break;
	default : System.out.println("Error in IRparam.");
	}
	Add("param,,"+type+","+name);
    }

    public static void IRreturn_stmt(String value){
	Add("rtn,,,"+value);
	func_type = null;
	//System.out.println(flame_size);
	func_flame.add(flame_size);
	flame_size = 0;
    }

    public static void IRprintf_stmt(String str){
	int strNum;
	if(printStr.indexOf(str) == -1){
	    printStr.add(str);
	    strNum = printStr.size()-1;
	}else{
	    strNum = printStr.indexOf(str);
	}
	Add("printf,,0,.LC"+strNum);
    }

    public static void IRprintf_stmt(String var, int varNum){
	Add("printf,,"+varNum+","+var);
    }
	
    public static void IRexpr(String op, String lhs, String rhs, String eq){
	Add(op+","+lhs+","+rhs+","+eq);
    }

    public static void IRexpr(String temp, String eq){
	//if(Simple_Assign == true){
	    Add("=,"+eq+",,"+temp);
	    /*}else{
	    Add(temp+","+eq);
	    }*/
    }

    static void IRarg(String name){
	Add("arg,,,"+name);
    }

    public static void IRjnz(String label){
	Add("jnz,,,"+label);
    }

    public static void IRjmp(String label){
	Add("jmp,,,"+label);
    }

    public static void IRjne(String label){
	Add("jne,,,"+label);
    }
    
    public static void IRposf(String opvar) {
    	Add("posf,,"+opvar);
    }
    
    public static void Add(String code) {
    	if(Advance) IRtemp += code + "\n";
    	else IR.add(code);
    }
    
    public void blank(int fragNum) {
    	String str = ">>>"/* + fragNum*/ + "\n";
    	if(Advance) IRtemp += str;
    }
    
   public static void IRfragConfirm() {
	   IRfrag.add(IRtemp); //System.out.println(IRfrag.size()+"\n"+IRtemp);
	   IRtemp = "";
   }
   
   public static void IRConfirm() {
	   int fragNum = 0;
	   int index;
	   Deque<Integer> incompNum = new ArrayDeque<>();
	   List<Boolean> compNum = new ArrayList<>();
	   String line = null;
	   try{
		   
		   int size = IRfrag.size();
		    for(int i=0;i<size;i++){
				compNum.add(false);
			 }
		   
		   while(true) {
			   BufferedReader br = new BufferedReader(new StringReader(IRfrag.get(fragNum)));
			   //System.out.println("fragNum:"+fragNum);
			   while((line = br.readLine()) != null) {
				   //System.out.println("line:"+line);
				   String str = IRfrag.get(fragNum);
				   index = str.indexOf("\n");
				   IRfrag.set(fragNum,str.substring(index+1));
				   //System.out.println("index="+index+"\n"+IRfrag.get(fragNum));
				   if(line.contains(">>>")) {
					   incompNum.push(fragNum);
					   for(int i = fragNum+1; i<compNum.size(); i++) {
						   //System.out.println("i="+i);
						   if(compNum.get(i) == false) {
							   fragNum = i;
							   break;
						   }
						   //System.out.println("fragNum="+fragNum);
					   }
					   //fragNum = Integer.parseInt(line.substring(3));
					   //System.out.println("nextFragNum:"+fragNum);
					   break;
				   }else {
					   IR.add(line);
				   }
			   }
			   //System.out.println("line:"+line+" incompSize:"+incompNum.size()+" num:"+incompNum.peek());
			   //System.out.println("compNum:"+compNum);
			   compNum.set(fragNum,true);
			   if(line == null && incompNum.size() != 0) {
				   fragNum = incompNum.pop();
			   }else if(line == null && incompNum.size() == 0){
				   break;
			   }else {
				   
			   }
		   }
	   }catch(Exception e) {
		   e.printStackTrace();
	   }
   }

   public static void IRGenerate(boolean adv){
	//System.out.println(var_list);
	   if(adv) {
		   System.out.println("-----------------");
		   for(int i=0;i<IRfrag.size();i++){
			   System.out.print(IRfrag.get(i));
			   System.out.println("-----------------");
		   }
	   }else {
		   for(int i=0;i<IR.size();i++){
			   System.out.println(IR.get(i));
		   }
	   }
    }

    static String[] IRRead(int line){
	String[] IR_element = IR.get(line).split(",",0);
	return IR_element;
    }

    static int IRNumOfLines(){
	return IR.size();
    }

    static int getAddress(String str){
	int index = var_list.indexOf(str);
	//System.out.println(str+":"+index);
	if(index == -1) return -1;
	else return var_address.get(index);
    }

    static int getStackSize(){
	return flame_size;
    }

    static int getNumOfString(){
	return printStr.size();
    }

    static String getString(int stringNum){
	return printStr.get(stringNum);
    }

    static int getFlame(int num){
	return func_flame.get(num);
    }
}
