package created;
import java.util.*;
import java.io.*;

public class BlockManager{
	static List<String> blocks = new ArrayList<>();
	static List<String> preBlocks = new ArrayList<>();
	static List<Integer> funcNumofBlock = new ArrayList<>();
	
	public static int Divide(FileReader source) throws DivideException{
	List<Boolean> usedBlockNum = new ArrayList<>();
	int funcNum = 0;
	funcNumofBlock.add(funcNum);

	try{
	    int ch;
	    int index;
	    String str;
	    String temp;
	    
	    ch = source.read();
	    blocks.add(String.valueOf((char)ch));
	    usedBlockNum.add(false);
	    
	    while((ch = source.read()) != -1){
	    str = String.valueOf((char)ch);
		
		if(str.equals("{")){
		    index = usedBlockNum.lastIndexOf(false);
		    if(index == 0)
		    	funcNum++;
		    funcNumofBlock.add(funcNum);
		    //System.out.println("1st{"+index);
		    temp = blocks.get(index);
		    blocks.set(index,temp+str);
		    usedBlockNum.add(false);
		    //System.out.println("2nd{"+(usedBlockNum.size()-1));
		    blocks.add("");
		}else if(str.equals("}")){
		    index = usedBlockNum.lastIndexOf(false);
		    //System.out.println("true{"+index);
		    temp = blocks.get(index);
		    blocks.set(index,temp);
		    usedBlockNum.set(index,true);
		    index = usedBlockNum.lastIndexOf(false);
		    //System.out.println("false{"+index);
		    temp = blocks.get(index);
		    blocks.set(index,temp+str);
		}else{
		    index = usedBlockNum.lastIndexOf(false);
		    temp = blocks.get(index);
		    blocks.set(index,temp+str);
		}
	    }
	    usedBlockNum.set(0,true);
	}catch(Exception e){
	    e.printStackTrace();
	}
	int unclosedBlock = usedBlockNum.lastIndexOf(false);
	if(unclosedBlock != -1){
	    throw new DivideException(unclosedBlock);
	}
	//System.out.println("funcNumofBlock: "+funcNumofBlock);
	return blocks.size();
    }
    
   public static void Generate(){
	int size = blocks.size() - 1;
	System.out.println("size="+size);
	for(int i=0;i<size+1;i++){
	    System.out.println("block"+i);
	    System.out.println(blocks.get(i));
	    //System.out.println("");
	}
    }

   public static void Generate(int num){
	System.out.println(blocks.get(num));
    }
    
   public static void Output(String curDir, String dirName) {
	   int size = blocks.size() - 1;
	   String filePass = curDir+dirName+"/"+dirName+".blk";
	   
	   try {
		   FileWriter fw = new FileWriter(filePass);
		   PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
		   for(int i=0;i<size+1;i++){
			    pw.print(blocks.get(i));
			    pw.print("/#/");
			}
		   //pw.print("\n/#/");
		   pw.close();
	   }catch(Exception e){
		   e.printStackTrace();
	   }
   }
   
   public static boolean getPreData(String curDir, String dirName) {
	   String filePass = curDir+dirName+"/"+dirName+".blk";

	   if(new File(filePass).exists()) {
		   try {
			   BufferedReader br = new BufferedReader(new InputStreamReader 
					   (new FileInputStream(filePass)));
			   String str = "";
			   String line = null;
		   
			   while ((line = br.readLine()) != null) {
				   //System.out.println(line);
				   if(line.contains("/#/")) {
					   preBlocks.add(str);
					   str = "";
				   }else{
					   str = str + line + "\n";
					   //System.out.println(str);
				   }
			   }
		   }catch(Exception e){
			   e.printStackTrace();
		   }
		   return true;
	   }else {
		   return false;
	   }
   }
   
   public static int compare(int blockNum) {
	   String block = blocks.get(blockNum).replaceAll(" ","").replaceAll("\n","");
	   String preBlock = null;
	   
	   for(int i=0; i<preBlocks.size(); i++) {
		   preBlock = preBlocks.get(i).replaceAll(" ","").replaceAll("\n","");
		   if(block.equals(preBlock))
			   return i;
	   }
	   return -1;
   	}
   
   public static int refFuncNum(int blockNum) {
	   return funcNumofBlock.get(blockNum);
   }
   
   public static String blockRef(int index){
	return blocks.get(index);
    }
   
   public static void replace(int index, String block) {
	   blocks.set(index,block);
   }
   
   public static String sourceRebuild() {
	   StringBuilder source = new StringBuilder(blocks.get(0));
	   int block = 1;
	   int index = 0;
	   
	   while(block < blocks.size()) {
	   index = source.indexOf("{",index)+1;
	   source.insert(index, blocks.get(block));
	   block++;
	   }
	   
		return source.toString();
   }
   
   public static void blockDebug() {
	   int size = blocks.size() - 1;
		System.out.println("size="+size);
		for(int i=0;i<size+1;i++){
		    System.out.println("block"+i);
		    System.out.println(blocks.get(i).replaceAll(" ","").replaceAll("\n",""));
		    //System.out.println("");
		}
		size = preBlocks.size() - 1;
		System.out.println("size="+size);
		for(int i=0;i<size+1;i++){
		    System.out.println("preBlock"+i);
		    System.out.println(preBlocks.get(i).replaceAll(" ","").replaceAll("\n",""));
		    //System.out.println("");
		}
   }
}
