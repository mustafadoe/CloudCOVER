
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

import java.sql.*;

/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */

public class ProgramOne {
    static JFrame frame = new JFrame("Input");
	private static final String url = "jdbc:mysql://localhost/aa2"; 
    private static final String user = "root"; 
    private static final String password = "j1mmyp1g";
    private static Map mapofprefs;
    static int cloudq;

//    static DBFrame choosethreats;
    
//	static Frame oneframe;
	static VMFrame vmframe;
	static EnterPanel enterframe;
	static AnalysePanel analysis;
	static ViewInput view;
//	static CommsPanel commspanel;
	static ConnectionAdd commspanel;
	static boolean commsexists;
	static boolean enterexists;
	static boolean analexists;
	static boolean viewexists;
	
	private static boolean vmframeexists = false;
	/*
	 * save() saves the current VM and Comms lists that are statically in the program. Needs an input name.
	 */
	public static void save(String savename){
		mapofprefs = preferences();
	      try{
	         FileOutputStream fileOut = new FileOutputStream("/home/mustafa/Dropbox/work/saves/inputs/" + savename + ".ser");
//	         FileOutputStream fileOut = new FileOutputStream("/home/mustafa/" + savename + ".ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);

	         out.writeObject(VM.allvms);	         
	         out.writeObject(VM.vmnames);
	         out.writeObject(Connexion.returncomms());
	         out.writeObject(Data.getdatanames());
	         out.writeObject(Data.getdataall());
	         out.writeObject(AddUser.returnusers());
	         out.writeObject(mapofprefs);
	         out.close();
	         fileOut.close();
//	         System.out.printf("Data is saved");
//	         println
//	         JOptionPane.showMessageDialog(frame, "Deployment saved as " + savename,"Message",JOptionPane.PLAIN_MESSAGE);			

	      }
	      catch(IOException i){
	          i.printStackTrace();
	      }
	}
	/*
	 * load() loads the file that it has been given to load. it should be changed to be given a file as input.
	 */	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void load(String savename) throws SQLException{
		Connexion.clearcomms();
		VM.allvms.clear();
		VM.clearvmnames();
		Data.cleardatanames();
		Data.cleardataall();
		AddUser.clearusers();
		if (mapofprefs !=null){ mapofprefs.clear();}
		try{
			FileInputStream fileIn = new FileInputStream("/home/mustafa/Dropbox/work/saves/inputs/" + savename + ".ser");
	    	ObjectInputStream in = new ObjectInputStream(fileIn);
	    		    	
			VM.allvms = (ArrayList<VM>) in.readObject();
			VM.vmnames = (ArrayList <String>) in.readObject();
			Connexion.insertcomms(	(ArrayList<Connexion>) in.readObject()	);
			ArrayList <String> loadednames = (ArrayList <String>) in.readObject();
			Data.loaddatanames(loadednames);
			ArrayList <Data> loadedalldata = (ArrayList <Data>) in.readObject();
			Data.loaddataall(loadedalldata);
			ArrayList <String> loadedusers = (ArrayList <String>) in.readObject();
			AddUser.loadusers(loadedusers);
			mapofprefs = (Map) in.readObject();
			in.close();
			fileIn.close();
	      }catch(IOException i){
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c){
	         System.out.println("Class not found");
	         c.printStackTrace();
	         return;
	      }
		startEntry();
	}

	public static void changeframe (){
		enterframe.reset1();
	}
	
	public static Map getmap(){
		return mapofprefs;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map preferences(){
		Scanner in = new Scanner(System.in);

		/*
		 *  	change the j < 1 to get saves for all of them
		 */
		
		Map prefsmap = SimpleMapofNodes.createmap();
		ArrayList<Data> dataprefs = Data.getdataall();
		for (int i = 0; i < dataprefs.size(); i++){ 
			Data datamakepref = dataprefs.get(i);
			for (int j = 0; j < 4; j++){ 
				System.out.println("Key for " + datamakepref.getname() + propget(j)); 
				int key = in.nextInt();
//				prefsmap.
				SimpleMapofNodes.addtomap(prefsmap, SimpleMapofNodes.newprefs(datamakepref, j), key);
//				prefsmap.put(key, SimpleMapofNodes.newprefs(datamakepref, j));
			}
		}
		in.close();
		
		Set<Integer> keys = prefsmap.keySet();			//get the keys for the map
		Integer [] array = keys.toArray(new Integer[keys.size()]);
		System.out.println(); 
		Arrays.sort(array);
		System.out.println("In POne"); 

		System.out.println("Sorted array:: "); 
		for (int i = 0; i < array.length; i++){
//			System.out.print(array[i]+","); 
			ArrayList<Preference> thiskeysprefs = (ArrayList<Preference>) prefsmap.get(array[i]);
//    		Preference newprefobject = (Preference) prefsmap.get(array[i]);
			for (int j = 0; j < thiskeysprefs.size(); j++){ 
				System.out.println(array[i]+", " + thiskeysprefs.get(j).getowner() + ", " + thiskeysprefs.get(j).getprop()); 
			}
      	}		
		System.out.println(); 
		return prefsmap;
	}
	public static String propget(int ver){
		String stringprop = "";
		if (ver==0){stringprop="Confidentiality";}
		else if (ver==1){stringprop="Integrity";}
		else if (ver==2){stringprop="Availability";}
		else {stringprop="Compliance";}
		return stringprop;
	}
	
/*
 * This method is just for when a deployment needs to be auto loaded for testing purposes
 */
	public static void exampleDeployment(){
//			 System.out.println("We are off");
//			VM VM1 = new VM("VM1",false,false,false,false,false,false,false);
//			VM VM2 = new VM("VM2",false,false,false,false,false,false,false);
//			VM VM3 = new VM("VM3",false,false,false,false,false,false,false);
//			VM VM4 = new VM("VM4",false,false,false,false,false,false,false);
			//			System.out.println(VM1.encrypComs + " and " + VM1.numUsers);
//			System.out.println(VM2.encrypComs + " and " + VM2.numUsers);
//			Comms comms = new Comms (VM1, VM2, false);
//			System.out.println(comms.vm1.name + " " + comms.vm2.name);
	}

    public static Connection getCon(){
    	Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    public static void loadOrSave() throws SQLException{
		int choice;
		Object[] options1 = {"Save","Load", "Cancel"};
		choice = JOptionPane.showOptionDialog(frame,"Do you want to save your entries or load a saved input?", "Cloud system", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options1, options1[0]);
//			System.out.println("saveload " + choice);	
		if (choice ==0){
			saveEntry();
		}
		else if (choice ==1){
			findSaves();
		}
		else {
			startEntry();
		}
    }	    
    public static void saveEntry() throws SQLException {
																						
	    String savename;
	    savename = JOptionPane.showInputDialog("Enter a name for the configuration");
	    if ((savename != null) && (savename.length() > 0)) {
//			    String x = String.parseInt(s);
//			    System.out.println("You entered " + vmname + ".");
	    	save(savename);
	    	JOptionPane.showMessageDialog(frame,"The configuration has been saved as " + savename,"Message",JOptionPane.PLAIN_MESSAGE);						    				
	    	startEntry();
		}
		else {
			startEntry();
		}		
	}
			
	/*
	 * We begin entry of the deployment configuration. 
	 */
	public static void enterv(){			
		vmframe = new VMFrame();	//then we create the vmframe for the first time
	}
	public static void viewinput(){
		if (viewexists==false){		//ie if we have not entered this before
			
			viewexists = true;
			view = new ViewInput();	//then we create the vmframe for the first time
		}
		else {
			view.reset();		//otherwise we empty the frame
//				vmframe.emptyState();
			view.visible();			//and call it to be visible again
			}
	}

	public static void nextStage() throws SQLException{
		startEntry();		
}

	public static void findSaves() throws SQLException{
		
		FindExtension newfile = new FindExtension();
		String[] saves = newfile.filterList();
		
		String saveChoice = (String)JOptionPane.showInputDialog(frame,"Which saved configuration do you want to load?", "Saved configurations",JOptionPane.PLAIN_MESSAGE, null, saves, saves[0]);//If a string was returned, say so.
		if ((saveChoice != null) && (saveChoice.length() > 0)) {
			load(saveChoice);
		}
		else {
			startEntry();
		}
	}

	/*
	 * We offer choice of VMs to link. After first offer, rest of VMs are allowed to be chosen. 
	 */		
//	public static void startanalysis() throws SQLException{
//		
//		if (analexists==false){
//			analysis = new AnalysePanel();		
//			analexists=true;
//		}
//		else {
//			analysis.resetanalysis();
//		}
//	}
	public static void emptyinfo(){
		JOptionPane.showMessageDialog(frame,"You have not entered a name for the instance","Empty Name",JOptionPane.WARNING_MESSAGE);			
	}
	public static void namerepeat(){
		JOptionPane.showMessageDialog(frame,"You have already entered this name for a instance","Repeated Name",JOptionPane.WARNING_MESSAGE);			
	}
	
	private static void startEntry() throws SQLException {
//			if (enterexists==false){		//ie if we have not entered this before
//				
//				enterframe = new EnterPanel();
//				enterexists=true;
//			}
//			else {
//				enterframe.visible();
//				}
		enterframe = new EnterPanel();
	}
	public static void vis(){
		enterframe.visible();
		}
	
	
	public static void main(String[] args) throws SQLException {
//			default for running the program is JOptionPane, firstq, and startentry();
//			DataAdd dataadd= new DataAdd();
//			exampleDeployment();
//			JOptionPane.showMessageDialog(frame,"Welcome to the Cloud Security Inspection Tool.\nYou need to enter at least two instances before adding communications between them.","Message",JOptionPane.PLAIN_MESSAGE);			
//			firstq();
//			System.out.println("WILL THIS WORK?");
			startEntry();				//firstq is now covered in vmframe
//			VMFrame vm = new VMFrame();
//			CommsPanel comms = new CommsPanel();
//			enterComms();
//			findSaves();
//			analyseEntry();
//			UserChoice stage1 = new UserChoice();
//			stage1.create();
//			System.out.println("hello");				
//			DBPanel dbpanel = new DBPanel();

//			DisplayInfo display = new DisplayInfo();
//			SimpleTable simple = new SimpleTable();
//			simple.setVisible( true );
//			runFrame();	
//			load();				
		}	
}
