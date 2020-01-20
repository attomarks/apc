package created;
import jcurses.system.*;
import jcurses.widgets.*;
//import jcurses.util.*;
import jcurses.event.*;
import java.io.*;

public class Curses extends Window implements ItemListener, ActionListener,
    ValueChangedListener, WindowListener, WidgetsConstants {
  private static Curses window = null;
  private static TextArea _block = null;
  //private static TextField textfield = null;
  private static List _source = new List();
  private static List _errorMsg = new List();
  private static Button button = null;
  private static DefaultLayoutManager mgr = null;
  //private boolean windowGenerated = false;
  
  public Curses(int width, int height) {
    super(width, height, true, "Syntax Error Recovery");
  }

  public static void Window() throws Exception{
	window = new Curses(60, 25);
    //window.init(block);
    //return _block.getText();
  }
  
  public static void setSource(FileReader sourceFile) {
	  BufferedReader br = new BufferedReader(sourceFile);
	  
	  try {
		  String str = br.readLine();
		  while(str != null){
			  _source.add(str);
			  str = br.readLine();
		  }
		  br.close();
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
  }
  
  public static void resetSource(String source) {
	  StringReader sr = new StringReader(source);
	  BufferedReader br = new BufferedReader(sr);
	  
	  try {
		  String str = br.readLine();
		  while(str != null){
			  _source.add(str);
			  str = br.readLine();
		  }
		  br.close();
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
  }

  public static String Recover(String block, int blockNum) throws Exception {
	  window.init(block,blockNum);
	    return _block.getText();
  }
  
  public static void setErrorMsg(String msg) {
	  StringReader sr = new StringReader(msg);
	  BufferedReader br = new BufferedReader(sr);
	  
	  try {
		  String str = br.readLine();
		  while(str != null){
			  _errorMsg.add(str);
			  str = br.readLine();
		  }
		  br.close();
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
  }
  
  public static void Close() {
	  window.close();
  }
  
  synchronized public void init(String block, int blockNum) throws Exception{
	  try{
    mgr = new DefaultLayoutManager();
    mgr.bindToContainer(window.getRootPanel());
    
	_block = new TextArea(-1,-1,block);
	
	mgr.addWidget(
	        new Label("block"+blockNum,
	                  new CharColor(CharColor.WHITE, CharColor.GREEN)),
	                  5, -4, 20, 10,
	                  WidgetsConstants.ALIGNMENT_CENTER,
	                  WidgetsConstants.ALIGNMENT_CENTER);
	mgr.addWidget(_block,1,1,28,9,ALIGNMENT_CENTER,ALIGNMENT_CENTER);
	
	mgr.addWidget(
	        new Label("whole program",
	                  new CharColor(CharColor.WHITE, CharColor.GREEN)),
	                  33, -4, 20, 10,
	                  WidgetsConstants.ALIGNMENT_CENTER,
	                  WidgetsConstants.ALIGNMENT_CENTER);
	mgr.addWidget(_source,29,1,28,9,ALIGNMENT_CENTER,ALIGNMENT_CENTER);
	
	mgr.addWidget(
	        new Label("error message from JavaCC",
	                  new CharColor(CharColor.WHITE, CharColor.GREEN)),
	                  15/*15*/, 7, 30, 10,
	                  WidgetsConstants.ALIGNMENT_CENTER,
	                  WidgetsConstants.ALIGNMENT_CENTER);
	mgr.addWidget(_errorMsg,8/*8*/,12,43,8,ALIGNMENT_CENTER,ALIGNMENT_CENTER);

	/*mgr.addWidget(
	        new Label("<TAB> : change the section\n<ESC> : force-quit",
	                  new CharColor(CharColor.WHITE, CharColor.BLACK)),
	                  1, 12, 30, 10,
	                  WidgetsConstants.ALIGNMENT_CENTER,
	                  WidgetsConstants.ALIGNMENT_CENTER);*/

    /*textfield = new TextField(10);
    mgr.addWidget(textfield, 0, 0, 20, 20,
        WidgetsConstants.ALIGNMENT_CENTER,
        WidgetsConstants.ALIGNMENT_CENTER);*/

    button = new Button("OK");
    mgr.addWidget(button, 13, 2, 32, 37,
        WidgetsConstants.ALIGNMENT_CENTER,
        WidgetsConstants.ALIGNMENT_CENTER);

    //button.setShortCut('q');
    button.addListener(this);
    window.addListener((WindowListener) this);
    window.show();
    wait();
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
	  //return _block.getText();
  }

  synchronized public void actionPerformed(ActionEvent event) {
    Widget w = event.getSource();
    if (w == button) {
      //new Message("Message", "You are about to quit", "OK").show();
    	notifyAll();
    	mgr.removeWidget(_block);
    	mgr.removeWidget(_source);
    	mgr.removeWidget(_errorMsg);
    	mgr.removeWidget(button);
    	_source.clear();
    	_errorMsg.clear();
    }
  }

  public void stateChanged(ItemEvent event) {  }

  public void valueChanged(ValueChangedEvent event) {  }

  public void windowChanged(WindowEvent event) {
    if (event.getType() == WindowEvent.CLOSING) {
      event.getSourceWindow().close();
      // Toolkit.clearScreen(new CharColor(CharColor.WHITE, CharColor.BLACK));
    }
  }
}
