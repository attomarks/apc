package created;
import java.util.*;
import java.io.*;

public class DivideIntoBlocks{
	static List<String> blocks = new ArrayList<>();
	public static int Dividing(FileReader source) throws DivideException{
	List<Boolean> usedBlockNum = new ArrayList<>();

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
	return blocks.size();
    }
    
   public static void Generate(){
	int size = blocks.size() - 1;
	System.out.println("size="+size);
	for(int i=0;i<size+1;i++){
	    System.out.println("block"+i);
	    System.out.println(blocks.get(i));
	    System.out.println("");
	}
    }

   public static void Generate(int num){
	System.out.println(blocks.get(num));
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
}
