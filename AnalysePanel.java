import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.*;

/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */

/*
 * for startpanel, uncomment frame, return panel, and the frame below;
 */
public class AnalysePanel {
	private JFrame frame1;
	private JPanel basepanel;
	private JPanel panel0;
	private JPanel panelthreatname;
	private JPanel paneltop;
	private JPanel panelok;
	private JPanel threatpanel1;
	private JPanel categories;
	private int numquery;
	private int threatcount;
	private JButton buttn;
	private JButton[] viewbtn;
	private int tech;
	private JPanel bottom;
	ArrayList<PrefResults> orderedprefs;
	private int[] orgmax;
	private Map<VM, ArrayList<Integer>> mapofints;
	private ArrayList<Threat> threatorder;
	private int col1, col2, col3, col4;
	private int left,middle,right;
	private String propnamescon, propnamesint, propnamesava, propnamescmp;
	private int count;
	private int rowheight;
	private int respanel;
	private int orgcol;
	private JPanel tempPanel;
	private JButton getresults;
	private boolean startedorg;
	private int lastorgval;
	
	private Map<Integer, Integer> buttonmap;
	
	private JButton details[];
	
	private static String url = "jdbc:mysql://localhost/a1"; 
    private static String user = "root"; 
    private static String password = "j1mmyp1g";
    private static int buttonnumber;
	
	public AnalysePanel() throws SQLException, IOException{
//		System.out.println("Analysis 1"); 
		
        mapofints = new HashMap<VM, ArrayList<Integer>>();			//the map to store everything 
		orderedprefs = new ArrayList<PrefResults>();
		orderedprefs.clear();
		threatorder = new ArrayList<Threat>();
		threatorder.clear();
		
		basepanel = new JPanel();
		basepanel.setLayout(new BoxLayout(basepanel,BoxLayout.Y_AXIS));

		panel0 = new JPanel();
		panel0.setLayout(new BoxLayout(panel0,BoxLayout.Y_AXIS));
		buttn= new JButton("OK");
		frame1 = new JFrame();
		OKHandler ok = new OKHandler();
//		JButton buttn = new JButton("OK");
		bottom = pnlBse(ok, buttn);

		rowheight = 33;
		col1 = 300;
		col2 = 50;
		col3 = 150;
		col4 = 150;
		startedorg = false;
//		frame1.setLayout(new BoxLayout(frame1,BoxLayout.Y_AXIS));
//		panel0.setLayout(new BoxLayout(panel0,BoxLayout.Y_AXIS));

		resetanalysis();
	}
//	public JPanel newpanel() throws SQLException{
//		resetpanel();
//		return panel0;
//	}
	public JPanel resetok(){
		
		panelok = new JPanel();
		formatpanel(panelok,500,50,false,1);

		OKHandler okbutton = new OKHandler();
		panelok.setLayout(new FlowLayout(FlowLayout.CENTER));

//		buttn = new JButton("OK");
		buttn.addActionListener(okbutton);
		panelok.add(buttn);
//		frame.add(panelok, BorderLayout.SOUTH);
		return panelok;
	}
	/*
	 * when performing the walk on the instances, we need to know whether we need to check any of the values 
	 */
	public int[] conprefmatch(Map map, Data datainmap){
		int[] prefvals = new int[4];
		ArrayList<Preference> vmprefs = new ArrayList<Preference>();
		Set<Integer> keys = map.keySet();			//get the keys for the map
//		Integer [] array = keys.toArray(new Integer[keys.size()]);
//		System.out.println(); 
//		Arrays.sort(array);
//		System.out.println("Sorted array:: "); 
//		System.out.println("Check Data name: " + datainmap.getname()); 
		for (int key: keys){
			ArrayList<Preference> listkeyprefs = (ArrayList<Preference>) map.get(key);
// 			Preference currentpref = (Preference) map.get(key); 
			for (int j = 0; j < listkeyprefs.size(); j++){ 
				if (listkeyprefs.get(j).getowner().equals(datainmap.getname())){					//ie if the preference belongs to the data
	//				vmprefs.add(currentpref);
	//				currentpref.getprop();
//					System.out.println(listkeyprefs.get(j).getowner() + ":: " + listkeyprefs.get(j).getprop() + ",  kEY: " + key) ; 	
					prefvals[listkeyprefs.get(j).getprop()]=key;
	//				System.out.println(vmprefs.get(vmprefs.size()).getowner());
				}
			}
      	}				
		return prefvals;
//		return vmprefs;
	}
	/*
	 * This method goes through the map to get the highest confidentiality value
	 */
	public int[] hi (){
		int[] orgthreats = new int[4];
		Map map = null;
		if(ProgramOne.getmap()!=null){
			map = ProgramOne.getmap();		
			Set<Integer> keys = map.keySet();			//get the keys for the map
//			Integer [] array = keys.toArray(new Integer[keys.size()]);
			Integer [] orderedkeys = keys.toArray(new Integer[keys.size()]);
			for (int key: orderedkeys){
//				System.out.println("In intkey forloop"); 
				ArrayList<Preference> prefslist = (ArrayList<Preference>) map.get(key);	//prefslist should be 4 (cia + compliance)
				for (int i = 0; i < prefslist.size(); i++){ 
					int prefproperty = prefslist.get(i).getprop();
					if (orgthreats[prefproperty]==0){
						orgthreats[prefproperty]=key;
					}
				}
			}
		}
		
		return orgthreats;
	}
	
	
	public int[] orderorgthreats(Map map){
		int[] orgthreats = new int[4];
		@SuppressWarnings("unchecked")
		Set<Integer> keys = map.keySet();			//get the keys for the map
		Integer [] orderedkeys = keys.toArray(new Integer[keys.size()]);
		for (int key: orderedkeys){
			ArrayList<Preference> prefslist = (ArrayList<Preference>) map.get(key);
			for (int i = 0; i < prefslist.size(); i++){ 
				int prefproperty = prefslist.get(i).getprop();
				if (orgthreats[prefproperty]==0){
					orgthreats[prefproperty]=key;
				}
			}
		}
		
		return orgthreats;
	}
	
	public ArrayList<ThreatList> readorgthreats() throws IOException{						//here we load the threats
		ReadFile rf = new ReadFile();
		String[] loadthreats = rf.readLines(true);

		
        for (String line : loadthreats)               { 		//Go through the array: for each line in loadthreats
            String[] threatbreaks = line.split(", ");		//Qsinline is the split line
//            qs.add(qsinline);							//Store qsinline in qs (the passed ArrayList of String[]s)
            boolean[] ciaquals = new boolean[5];

            if (threatbreaks[3].equals("prop")){
				ciaquals[4]=true;
			}
            for (int i = 4; i < threatbreaks.length; i++){ 
//    			System.out.print(qsinline[i]+ "|");	
    			if (threatbreaks[i].equals("conf")){
    				ciaquals[0]=true;
    			}
    			if (threatbreaks[i].equals("integ")){
//    				System.out.println("PRINT");
    				ciaquals[1]=true;
    			}
    			if (threatbreaks[i].equals("avail")){
    				ciaquals[2]=true;
    			}
    			if (threatbreaks[i].equals("comp")){
    				ciaquals[3]=true;
    			}
    		}
//			System.out.println("THT " + threatbreaks[0] + ": " + ciaquals[1]);	            
			ThreatList newthreat = new ThreatList(threatbreaks[0],Integer.parseInt(threatbreaks[1]),Integer.parseInt(threatbreaks[2]),ciaquals[0], ciaquals[1],ciaquals[2],ciaquals[3],ciaquals[4], threatbreaks[4]);
			ThreatList.addallorgthreats(newthreat);
			if (ciaquals[0]==true){	ThreatList.addconfiorgthreats(newthreat);}
			if (ciaquals[1]==true){	ThreatList.addintegorgthreats(newthreat);}
			if (ciaquals[2]==true){	ThreatList.addavailorgthreats(newthreat);}
			if (ciaquals[3]==true){	ThreatList.addcomplorgthreats(newthreat);}

        }  		
//		System.out.print(ThreatList.getconfithreats().size() + ", " + ThreatList.getintegthreats().size() + ", " + ThreatList.getavailthreats()
//				.size()+ ", " + ThreatList.getcomplthreats().size());
        ArrayList<ThreatList> deletestuff1 = ThreatList.getallorgthreats();		
//		ArrayList<ThreatList> deletestuff = ThreatList.deletethreats(ThreatList.getallthreats(), 1);
		for (int i = 0; i < deletestuff1.size(); i++){
//			System.out.println(deletestuff1.get(i).getname()); 
		}
//		System.out.println("///////////////////////////////////"); 
		return deletestuff1;
	}

	
	public void readthreats() throws IOException{						//here we load the threats
		ReadFile rf = new ReadFile();
		String[] loadthreats = rf.readLines();
		
        for (String line : loadthreats)               { 		//Go through the array: for each line in loadthreats
            String[] threatbreaks = line.split(", ");		//Qsinline is the split line
//            qs.add(qsinline);							//Store qsinline in qs (the passed ArrayList of String[]s)
            boolean[] ciaquals = new boolean[5];
//            String threatexclude = threatbreaks[4];
            if (threatbreaks[3].equals("prop")){
				ciaquals[4]=true;
			}
            for (int i = 4; i < threatbreaks.length; i++){ 
//    			System.out.print(qsinline[i]+ "|");	
    			if (threatbreaks[i].equals("conf")){
    				ciaquals[0]=true;
    			}
    			if (threatbreaks[i].equals("integ")){
//    				System.out.println("PRINT");
    				ciaquals[1]=true;
    			}
    			if (threatbreaks[i].equals("avail")){
    				ciaquals[2]=true;
    			}
    			if (threatbreaks[i].equals("comp")){
    				ciaquals[3]=true;
    			}
    		}
//			System.out.println("THT " + threatbreaks[0] + ": " + ciaquals[1]);	            
			ThreatList newthreat = new ThreatList(threatbreaks[0],Integer.parseInt(threatbreaks[1]),Integer.parseInt(threatbreaks[2]),ciaquals[0], ciaquals[1],ciaquals[2],ciaquals[3],ciaquals[4], threatbreaks[4]);
			ThreatList.addallthreats(newthreat);
			if (ciaquals[0]==true){	ThreatList.addconfithreats(newthreat);}
			if (ciaquals[1]==true){	ThreatList.addintegthreats(newthreat);}
			if (ciaquals[2]==true){	ThreatList.addavailthreats(newthreat);}
			if (ciaquals[3]==true){	ThreatList.addcomplthreats(newthreat);}

        }  		
//		System.out.print(ThreatList.getconfithreats().size() + ", " + ThreatList.getintegthreats().size() + ", " + ThreatList.getavailthreats()
//				.size()+ ", " + ThreatList.getcomplthreats().size());
		
		ArrayList<ThreatList> deletestuff = ThreatList.deletethreats(ThreatList.getallthreats(), 1);
		for (int i = 0; i < deletestuff.size(); i++){
//			System.out.println(deletestuff.get(i).getname()); 
		}
//		System.out.println("///////////////////////////////////"); 
	}
	
	public void resetanalysis() throws SQLException, IOException{
		readthreats();
		details = new JButton[(VM.allvms.size()*4)*(ThreatList.getallthreats().size())];
		count = 0;
		
		int[] orgresults = new int[4];
		int orgnum = 0;
		int[] altorgresults = new int [4];
		
		Map prefsmap = null;

		if(ProgramOne.getmap()!=null){
			prefsmap = ProgramOne.getmap();		
			Set<Integer> keys = prefsmap.keySet();			//get the keys for the map
			Integer [] array = keys.toArray(new Integer[keys.size()]);
			System.out.println();
			Arrays.sort(array);
			System.out.println("Sorted array:: "); 
			
			boolean oconfi = false;
			boolean ointeg = false;
			boolean oavail = false;
			boolean ocompl = false;
			int orgvalue = 0;
			
			for (int i = 0; i < array.length; i++){
//				System.out.print(array[i]+","); 
				ArrayList<Preference> thiskeysprefs = (ArrayList<Preference>) prefsmap.get(array[i]);
//	    		Preference newprefobject = (Preference) prefsmap.get(array[i]);
				for (int j = 0; j < thiskeysprefs.size(); j++){ 
					System.out.println(array[i]+", " + thiskeysprefs.get(j).getowner() + ", " + thiskeysprefs.get(j).getprop()); 
					
					if (thiskeysprefs.get(j).getprop()==0 && oconfi==false){       
						oconfi = true;
						orgresults[0]=orgnum;
						orgnum++;
						altorgresults[0] = array[i];
					}
					else if (thiskeysprefs.get(j).getprop()==1  && ointeg==false) {
						ointeg = true;
						orgresults[1]=orgnum;
						orgnum++;
						altorgresults[1] = array[i];
					}
					else if (thiskeysprefs.get(j).getprop()==2  && oavail==false){       
						oavail = true;
						orgresults[2]=orgnum;
						orgnum++;
						altorgresults[2] = array[i];
					}
					else if (thiskeysprefs.get(j).getprop()==3  && ocompl==false){ 
						ocompl = true;
						orgresults[3]=orgnum;
						orgnum++;
						altorgresults[3] = array[i];
					}

				}
	      	}		
			System.out.println(); 
		}						//order the preferences
//		hi();
//		orderorgthreats(prefsmap);
		
		
		
		/*
		 * now we get the lowst values (of preferences) for each instance (by going through all the data on that instance)
		 */
		ArrayList <VM> allinstances = VM.returnallvms();
		for (int i = 0; i < allinstances.size(); i++){
			VM vmcheck = allinstances.get(i);
			System.out.println("VM" + vmcheck.name);
			Map vmthreatmap = vmcheck.getvmmap();
			Set <String >keys = vmthreatmap.keySet();
			for (String key: keys){
				System.out.println("Key " + key + " is " + vmthreatmap.get(key));
			}
			
			if (vmcheck.getcom()==true) System.out.println(vmcheck.name + " has bad comms");
			
			ArrayList<Data> vmchecksdata = vmcheck.getvmdata();
			vmcheck.emptyinitvals();												//this makes everything zero on currentvals
			for (int j = 0; j < vmchecksdata.size(); j++){ 

				Data dataitem = vmchecksdata.get(j);
				boolean[] databools = dataitem.getbooleans();
//				System.out.println(dataitem.getname()+":::");
//				if (databools[0]==true) System.out.println("SQL is true");
//				if (databools[1]==true) System.out.println("MSA is true");
//				if (databools[2]==true) System.out.println("ENC is true");

				
				int[] currentvals = vmcheck.getinitvals();							
				int[] dataprefvals = conprefmatch(prefsmap, vmchecksdata.get(j));	//this gets the values of prefs for each data item
/*
 * 		here we check the highest value of data on each instance
 * 		if the value of the data being checked is lower than the 
 * 		data checked already, we include the lower value
 */								
				
				if (currentvals[0]==0){
					vmcheck.setinitvals(0, dataprefvals[0]);					
				}					
				else{																//we compare the scores, we keep the highest vals 
					if (dataprefvals[0]<currentvals[0]){							//for each instance
						vmcheck.setinitvals(0, dataprefvals[0]);
					}
				}
				if (currentvals[1]==0){
					vmcheck.setinitvals(1, dataprefvals[1]);					
				}					
				else{																//we compare the scores, we keep the highest vals 
					if (dataprefvals[1]<currentvals[1]){							//for each instance
						vmcheck.setinitvals(1, dataprefvals[1]);
					}
				}
				if (currentvals[2]==0){
					vmcheck.setinitvals(2, dataprefvals[2]);					
					vmcheck.addavahere(vmchecksdata.get(j));
				}					
				else{																//we compare the scores, we keep the highest vals 
					if (dataprefvals[2]<currentvals[2]){							//for each instance
						vmcheck.setinitvals(2, dataprefvals[2]);
						vmcheck.emptyconhere();
						vmcheck.addavahere(vmchecksdata.get(j));
					}
					else if (dataprefvals[2]==currentvals[2]){
						vmcheck.addavahere(vmchecksdata.get(j));						
					}
				}
				if (currentvals[3]==0){
					vmcheck.setinitvals(3, dataprefvals[3]);					
				}					
				else{																//we compare the scores, we keep the highest vals 
					if (dataprefvals[3]<currentvals[3]){							//for each instance
						vmcheck.setinitvals(3, dataprefvals[3]);
					}
				}
			}
		}
/*
 * 		here we check whether to propagate values:
 * 		go through each outgoing connection, check to see whether to prop val back on the con		
 */
//		System.out.println("b4CONS::"); 
		for (int i = 0; i < allinstances.size(); i++){
			
			VM vmcheck = allinstances.get(i);
			ArrayList<Data> checkavls = vmcheck.getavahere();
			for (int avl = 0; avl < checkavls.size(); avl++){
//				System.out.println(vmcheck.name + " &AVL: " + checkavls.get(avl).getname()); 
			}
			
			vmcheck.propsfromEmpty();
			ArrayList<VM> connectionout = vmcheck.vmsout;
			ArrayList<Connexion> allconnections = Connexion.returncomms();
//			System.out.println("CONS::"); 

			for (int j = 0; j < connectionout.size(); j++){
//				System.out.println(vmcheck.name + " tot " + connectionout.get(j).name); 
								
				VM receivingvm = connectionout.get(j);

				for(int k =0; k < allconnections.size(); k++){
					Connexion thiscon = allconnections.get(k);
					if (thiscon.vm1 == vmcheck && thiscon.vm2 == receivingvm){						

/*
 * 				This bit is for checking the confidentiality						
 */
						
						if (thiscon.getcreate()==true || thiscon.getforward()==true || thiscon.getsend()==true){
							
							int[] receivervals = receivingvm.getinitvals();
							int[] propvals = vmcheck.getpropvals();

							if (propvals[0]==0){
								vmcheck.setpropvals(0, receivervals[0]);
//								System.out.println("vmcheck"+vmcheck.name + receivervals[0]);
//								vmcheck.propsfromAdd(receivingvm);
								vmcheck.addconprop(receivingvm);
							}					
							else{
								if (receivervals[0]<propvals[0]){
									vmcheck.setpropvals(0, receivervals[0]);
//									vmcheck.propsfromEmpty();
//									vmcheck.propsfromAdd(receivingvm);
									vmcheck.emptycon();
									vmcheck.addconprop(receivingvm);
								}
								else if (receivervals[0]==propvals[0]){
//									vmcheck.setpropvals(0, receivervals[0]);
//									vmcheck.propsfromAdd(receivingvm);
									vmcheck.addconprop(receivingvm);
								}
							}
						}
/*
 * 				This bit is for checking the integrity
 */												
						if (thiscon.getcreate()==true || thiscon.getdestroy()==true){
							
							int[] receivervals = receivingvm.getinitvals();
							int[] propvals = vmcheck.getpropvals();

							if (propvals[1]==0){
								vmcheck.setpropvals(1, receivervals[1]);
//								vmcheck.propsfromAdd(receivingvm);
								vmcheck.addintprop(receivingvm);
							}					
							else{
								if (receivervals[1]<propvals[1]){
									vmcheck.setpropvals(1, receivervals[1]);
//									vmcheck.propsfromEmpty();
//									vmcheck.propsfromAdd(receivingvm);
									vmcheck.emptyint();
									vmcheck.addintprop(receivingvm);
								}
								else if (receivervals[1]==propvals[1]){
//										vmcheck.setpropvals(0, receivervals[0]);
//									vmcheck.propsfromAdd(receivingvm);
									vmcheck.addintprop(receivingvm);
								}
							}
						}
						
/*
 * 				This bit is for checking the availability
 */												
						
						if (thiscon.getdestroy()==true){
							
							int[] receivervals = receivingvm.getinitvals();
							int[] propvals = vmcheck.getpropvals();

							if (propvals[2]==0){
								vmcheck.setpropvals(2, receivervals[2]);
//														vmcheck.propsfromAdd(receivingvm);
								vmcheck.addavaprop(receivingvm);
							}					
							else{
								if (receivervals[2]<propvals[2]){
									vmcheck.setpropvals(2, receivervals[2]);
//															vmcheck.propsfromEmpty();
//															vmcheck.propsfromAdd(receivingvm);
									vmcheck.emptyava();
									vmcheck.addavaprop(receivingvm);
								}
								else if (receivervals[2]==propvals[2]){
//																vmcheck.setpropvals(0, receivervals[0]);
//															vmcheck.propsfromAdd(receivingvm);
									vmcheck.addavaprop(receivingvm);
								}
							}
						}
						

						
/*
 * 				This bit is for checking the compliance
 */												
						if (thiscon.getcreate()==true || thiscon.getdestroy()==true || thiscon.getforward()==true || thiscon.getsend()==true){
							
							int[] receivervals = receivingvm.getinitvals();
							int[] propvals = vmcheck.getpropvals();

							if (propvals[3]==0){
								vmcheck.setpropvals(3, receivervals[3]);
//																				vmcheck.propsfromAdd(receivingvm);
								vmcheck.addcmpprop(receivingvm);
							}					
							else{
								if (receivervals[3]<propvals[3]){
									vmcheck.setpropvals(3, receivervals[3]);
//																					vmcheck.propsfromEmpty();
//																					vmcheck.propsfromAdd(receivingvm);
									vmcheck.emptycmp();
									vmcheck.addcmpprop(receivingvm);
								}
								else if (receivervals[3]==propvals[3]){
//																						vmcheck.setpropvals(0, receivervals[0]);
//																					vmcheck.propsfromAdd(receivingvm);
									vmcheck.addcmpprop(receivingvm);
								}
							}
						}						
						
						
					}
				}
			}
		}

		/*THIS IS STAGE 3
		 * The final loop is just to gather the results and print them out
		 */
//		System.out.println("This has to be printed surely"); 
//		Connection mycon = getCon();		    	
		int uid = 1;
		int aid = 1;
		int lastid = 0;
		int presid = 0;
		for (int i = 0; i < allinstances.size(); i++){
//			JPanel paneltoadd = new JPanel();
//			JLabel panelname = new JLabel("Name"+i);
//			paneltoadd.add(panelname);
//			panel0.add(paneltoadd);
			VM vmcheck = allinstances.get(i);
			int [] vmvals = vmcheck.getinitvals();
			int [] prvals = vmcheck.getpropvals();
			ArrayList<VM> propsnames = vmcheck.propsfromGet();
			ArrayList<Data> vmchecksdata = vmcheck.getvmdata();
			for (int datai = 0; datai < vmchecksdata.size();datai++){							
				
				for (int k = 0; k < 4; k++){										//we are checking to see if the vmvalues values are lower than pr vals
					if (vmvals[k]<=prvals[k]){
						vmcheck.setallvals(k, vmvals[k]);
					}
					else if (prvals[k]<vmvals[k]){									//if pr vals lower, we add the pr vals in the setallvals method
						vmcheck.setallvals(k, prvals[k]);
					}
	//				else vmcheck.setallvals(k, vmvals[k]);
				}
			}
			
			for (int deleteprop = 0; deleteprop < 4; deleteprop++){
				if (prvals[0]==0) vmcheck.emptycon();
				if (prvals[1]==0) vmcheck.emptyint();
				if (prvals[2]==0) vmcheck.emptyava();
				if (prvals[3]==0) vmcheck.emptycmp();

			}
	
			int[] allvals = vmcheck.getallvals();
			System.out.println(vmcheck.name + " HINITVALS:: [" + 
			vmvals[0]+"]["+vmvals[1]+"]["+vmvals[2]+"]["+vmvals[3]+"]"+ "    PROPVALS:: [" + 
			prvals[0]+"]["+prvals[1]+"]["+prvals[2]+"]["+prvals[3]+"]"+ "    ALLVALS:: [" + 		                                                                  			
			allvals[0]+"]["+allvals[1]+"]["+allvals[2]+"]["+allvals[3]+"]");
					
			

/*			**********************************************************************************************************
 * 					THIS IS A BIT WHERE WE ADD THE NAMES OF THE VMS VALS ARE PROPAGATING FROM, NO REAL ANALYSIS
 * 			**********************************************************************************************************
 */			
			propnamescon = null;
			ArrayList<VM>convms = vmcheck.getconprops();			
			boolean addcomma = false;
			for (int zz = 0; zz < convms.size(); zz++){
				if (addcomma==true){
					propnamescon = propnamescon + ", ";
				}
//				System.out.println("CONFVM:" + convms.get(zz).name); 
				String toadd = convms.get(zz).name;
				if (propnamescon==null){
					propnamescon=toadd;
					addcomma = true;
				}
				else {
					propnamescon= propnamescon+toadd;
					addcomma = true;
				}
			}
			propnamesint = null;
			ArrayList<VM>intvms = vmcheck.getintprops();
			boolean addcomma2 = false;
			for (int zz = 0; zz < intvms.size(); zz++){
				if (addcomma2==true){
					propnamesint = propnamesint + ", ";
				}
//				System.out.println("INTGVM:" + convms.get(zz).name); 
				String toadd = intvms.get(zz).name;
				if (propnamesint==null){
					propnamesint=toadd;
					addcomma2 = true;
				}
				else {
					propnamesint= propnamesint+toadd;
					addcomma2 = true;
				}
			}			
			
			propnamesava = null;
			ArrayList<VM>avavms = vmcheck.getavaprops();
			boolean addcomma3 = false;
			for (int zz = 0; zz < avavms.size(); zz++){
				if (addcomma3==true){
					propnamesava = propnamesava + ", ";
				}
//				System.out.println("INTGVM:" + convms.get(zz).name); 
				String toadd = avavms.get(zz).name;
				if (propnamesava==null){
					propnamesava=toadd;
					addcomma3 = true;
				}
				else {
					propnamesava= propnamesava+toadd;
					addcomma3 = true;
				}
			}
			
			propnamescmp = null;
			ArrayList<VM>cmpvms = vmcheck.getcmpprops();
			boolean addcomma4 = false;
			for (int zz = 0; zz < cmpvms.size(); zz++){
				if (addcomma4==true){
					propnamescmp = propnamescmp + ", ";
				}
//				System.out.println("INTGVM:" + convms.get(zz).name); 
				String toadd = cmpvms.get(zz).name;
				if (propnamescmp==null){
					propnamescmp=toadd;
					addcomma4 = true;
				}
				else {
					propnamescmp= propnamescmp+toadd;
					addcomma4 = true;
				}
			}
			
				int[] ivals = vmcheck.getinitvals();
				int[] pvals = vmcheck.getpropvals();
				int[] avals = vmcheck.getallvals();

				for(int z1=0; z1<4; z1++){
					
					String propnames = null;
					if (z1==0){propnames = propnamescon;}
					else if (z1==1){propnames = propnamesint;}
					else if (z1==2){propnames = propnamesava;}
					else if (z1==3){propnames = propnamescmp;}
					
					if (ivals[z1]==0) ivals[z1]=10000000; 
					if (pvals[z1]==0) pvals[z1]=10000000; 
					
					if (ivals[z1]!=10000000){
						PrefResults newpref1 = new PrefResults(vmcheck, ivals[z1], z1, false, null, propnames);
						orderedprefs.add(newpref1);						
						}
//						System.out.println(); 
					if (pvals[z1]!=10000000){
						PrefResults newpref2 = new PrefResults(vmcheck, pvals[z1], z1, true, null, propnames);					
						orderedprefs.add(newpref2);												
						}					
				}						
		}		
		
		System.out.println("ORGVALS:: [" + orgresults[0]+"]["+orgresults[1]+"]["+orgresults[2]+"]["+orgresults[3]+"]");

		Collections.sort(orderedprefs,new ListSorter());
		boolean sameaid2 = true;
    		
/*		//THIS IS WHERE WE LIST THE THREATS         STAGE 4
 * 		//*************************************************************************************************************
 */
				
		for(int z2=0; z2<orderedprefs.size(); z2++){							//ordered prefs are the sorted list of all the threats
			boolean sameaid1 = true;
			PrefResults thisresult = orderedprefs.get(z2);
			PrefResults nextresult = null;
			int z3 = z2+1;														//we are checking the nextresult
			if (z3!=orderedprefs.size()){
				nextresult = orderedprefs.get(z3);								//adding one if it exists
			}
					
			if (thisresult.value!=0 && thisresult.value!=10000000){
				
				int attribute = thisresult.attribute;
				addtomap(thisresult.host,attribute);
				ArrayList<VM> vmto = null;				
				ArrayList<ThreatList> thisvmpropthreats = null;										
				ArrayList<ThreatList> thisvmthreats = null;											//we get the right threats for the att
				if (thisresult.attribute==0){	
					thisvmthreats=ThreatList.getconfithreats();
					vmto = thisresult.host.getconprops();
					}
				else if (thisresult.attribute==1){ 
					thisvmthreats=ThreatList.getintegthreats()	;
					vmto = thisresult.host.getintprops();
					}
				else if (thisresult.attribute==2){ 
					thisvmthreats=ThreatList.getavailthreats()	;
					vmto = thisresult.host.getavaprops();
					}
				else if (thisresult.attribute==3){ 
					thisvmthreats=ThreatList.getcomplthreats()	;
					vmto = thisresult.host.getcmpprops();
				}
				
				
				if (thisresult.propval==true){
					
					thisvmthreats = ThreatList.deletenonprops(thisvmthreats);
					
					if (thisresult.host.getused1()==true){
						thisvmthreats = ThreatList.deletethreats(thisvmthreats, 0);
					}
					if (thisresult.host.getused2()==true){
						thisvmthreats = ThreatList.deletethreats(thisvmthreats, 1);						
					}
					if (thisresult.host.getused3()==true){
						thisvmthreats = ThreatList.deletethreats(thisvmthreats, 2);
					}
					if (thisresult.host.getused4()==true){
						thisvmthreats = ThreatList.deletethreats(thisvmthreats, 3);
					}
				}
				else if (thisresult.propval==false){
				//we delete the threats if already used
					if (thisresult.host.getucon()==true){ 							
	//					System.out.println("Used confi already ");
						thisvmthreats = ThreatList.deletethreats(thisvmthreats, 0);
					}
					if (thisresult.host.getuint()==true){ 
	//					System.out.println("Used integ already "); 
					thisvmthreats = ThreatList.deletethreats(thisvmthreats, 1);
					}
					if (thisresult.host.getuava()==true){ 
	//					System.out.println("Used avail already "); 
					thisvmthreats = ThreatList.deletethreats(thisvmthreats, 2);
					}
					if (thisresult.host.getucmp()==true){ 
	//					System.out.println("Used compl already "); 
					thisvmthreats = ThreatList.deletethreats(thisvmthreats, 3);				
					}
				}				
				
				if (nextresult!=null && nextresult.value!=10000000){
					if ((nextresult.value - thisresult.value) > 50){
						sameaid1 = false;										//ie if not sameid we make the val higher
					}
				}

		       	presid = thisresult.value;
		       	if (lastid==0) lastid=1;
		       	if (presid-lastid>1){
		       		aid+=3;
		       	}
		       	else if (presid-lastid==1){
		       		aid+=1;
		       	}
		       	else if (presid-lastid==0){
		       	}
		       	
		       	
				for (int thri =0; thri < thisvmthreats.size(); thri++){
					
					uid++;
					int threatid = aid;
					int probnum = thisvmthreats.get(thri).getprob();
////					if (probnum==3)threatid+=1;{System.out.println("Add0");}
					
					if (probnum==2){
						threatid+=1;
//						System.out.println("Add1");
					}
					if (probnum==1){
						threatid+=2; 
//						System.out.println("Add2");
					}
					
					int threatprob = thisvmthreats.get(thri).getprob();
					int thisval = thisresult.value;
					if (threatprob==1) thisval+=2;
					else if (threatprob==2) thisval+=1;
					else if (threatprob==3) ;
					
					ThreatList threatentry = thisvmthreats.get(thri);
					String includethis = threatentry.getexclude();
		    		String fromnames = null;
					boolean includethreat = false;
		    		Set <String> thisvmkeys;
		    		Map thisvmmap;
		    		
					if (thisresult.propval==true){									//if its a propagated value
						boolean includethreat2 = false;
						for (int vmcons = 0; vmcons < vmto.size(); vmcons++){		//go through all cons
							
							VM vmconto = vmto.get(vmcons);
				    		thisvmkeys = vmconto.getvmmap().keySet();			//we get the keys of the host vm (of the threat
				    		thisvmmap = vmconto.getvmmap();						// we get the map
				    		includethreat  = (boolean) thisvmmap.get(includethis);		//we say whether the exclude value is in the vm. 
				    		}
		    			fromnames = thisresult.vmfrom;
		    			fromnames = fromnames + " & any other cons";

					}
					else {
			    		thisvmkeys = thisresult.host.getvmmap().keySet();			//we get the keys of the host vm (of the threat
			    		thisvmmap = thisresult.host.getvmmap();						// we get the map
	//		    		System.out.println("ThreatID:" + threatid); 
			    		includethreat  = (boolean) thisvmmap.get(includethis);		//we say whether the exclude value is in the vm. 
			    		fromnames = "";												
					}
		    		boolean doesthisprp = thisresult.propval;
		    		
		    		if (includethreat==true){
		    			System.out.println("FINAL:"+ thisresult.host.name + ", " + thisvmthreats.get(thri).getname() + " & " + doesthisprp); 
			    		Threat newthreat = new Threat(thisresult.host, thisvmthreats.get(thri).getname(), thisval, thisresult.attribute, thisvmthreats.get(thri).getprob(),thisvmthreats.get(thri).getid(), fromnames);						
//			    		Threat newthreat = new Threat(thisresult.host, thisvmthreats.get(thri).getname(), threatid, thisresult.attribute, thisvmthreats.get(thri).getprob(),thisvmthreats.get(thri).getid(), thisresult.vmfrom);
			    		threatorder.add(newthreat);

			    		
						if (thisresult.propval==true){
					       	if (thisresult.attribute == 0) { 
	//				       		System.out.println("0setting"); 
					       		thisresult.host.setused1(); 
					       		}							
					       	if (thisresult.attribute == 1) { 
	//				       		System.out.println("0setting"); 
					       		thisresult.host.setused2(); 
					       		}							
					       	if (thisresult.attribute == 2) { 
	//				       		System.out.println("0setting"); 
					       		thisresult.host.setused3(); 
					       		}							
					       	if (thisresult.attribute == 3) { 
	//				       		System.out.println("0setting"); 
					       		thisresult.host.setused4(); 
					       		}							
						}
			    		
			    		if (thisresult.propval==false){
					       	if (thisresult.attribute == 0) { 
	//				       		System.out.println("0setting"); 
					       		thisresult.host.setucon(); 
					       		}
					       	else if (thisresult.attribute == 1) { 
	//				       		System.out.println("1setting"); 			       		
					       		thisresult.host.setuint();
					       	}
					       	else if (thisresult.attribute == 2) { 
	//				       		System.out.println("2setting"); 
					       		thisresult.host.setuava(); 
					       	}
					       	else if (thisresult.attribute == 3) { 
	//				       		System.out.println("3setting"); 
					       		thisresult.host.setucmp(); 
					       	}
			    		}

		    		}
		    		
		    		lastid=thisresult.value;
				}
			}
		}	
		
		buttonmap = new HashMap<Integer,Integer>();
		
		Collections.sort(threatorder,new ThreatSorter());
//        System.out.println("Final Threat");
        int thisid =0;
        int previousid = 1;
        for(Threat t:threatorder){
        	if (thisid==0){
        		createheader();
        		JPanel pnlheader = new JPanel();
        		formatpanel(pnlheader, (col1+col2+col3+col4+col4),rowheight,true,2);
        		pnlheader.setLayout(new FlowLayout(FlowLayout.LEFT));
        		JLabel panelname = new JLabel("Most important");
        		panelname.setForeground(Color.RED);
        		pnlheader.add(panelname);
        		panel0.add(pnlheader);

//        		System.out.println("Most important");
        		thisid = t.getorder();
        		previousid = t.getorder();
        	}
        	respanel = col1+col2+col3+col4+col4;
        	if (thisid>0){
        		thisid = t.getorder();
        		if (thisid>previousid){
//        			System.out.println("Next Most important");
            		JPanel pnlheader = new JPanel();
            		formatpanel(pnlheader, (col1+col2+col3+col4+col4),rowheight,true,2);
            		pnlheader.setLayout(new FlowLayout(FlowLayout.LEFT));
            		JLabel panelname = new JLabel("Next most important");
            		panelname.setForeground(Color.RED);
            		pnlheader.add(panelname);
            		panel0.add(pnlheader);
        		}
        		previousid = t.getorder();
        	}
    		JPanel paneltoadd = new JPanel();
    		paneltoadd.setLayout(new BoxLayout(paneltoadd,BoxLayout.X_AXIS));

    		JPanel pnl1 = new JPanel();
    		formatpanel(pnl1, col1,rowheight,true,2);
    		pnl1.setBackground(Color.white);
    		pnl1.setLayout(new FlowLayout(FlowLayout.LEFT));
    		JLabel panelname = new JLabel(t.getthreat());
    		pnl1.add(panelname);
    		paneltoadd.add(pnl1);
    		
    		JPanel pnl2 = new JPanel();
    		formatpanel(pnl2, col2,rowheight,true,2);
    		pnl2.setBackground(Color.white);
    		pnl2.setLayout(new FlowLayout(FlowLayout.LEFT));
    		JLabel panelvm = new JLabel(t.gethost().name);
    		pnl2.add(panelvm);
    		paneltoadd.add(pnl2);
    		
    		JPanel pnl3 = new JPanel();
    		formatpanel(pnl3, col3,rowheight,true,2);
    		pnl3.setBackground(Color.white);
    		pnl3.setLayout(new FlowLayout(FlowLayout.LEFT));
    		JLabel panelatt = new JLabel(dbquery(t.getattr()));
    		pnl3.add(panelatt);
    		paneltoadd.add(pnl3);

    		JPanel pnl4 = new JPanel();
    		formatpanel(pnl4, col4,rowheight,true,2);
    		pnl4.setBackground(Color.white);
    		pnl4.setLayout(new FlowLayout(FlowLayout.LEFT));
    		JLabel panelprop = new JLabel(t.getpropnames());
    		pnl4.add(panelprop);
    		paneltoadd.add(pnl4);
    		
    		JPanel pnl5 = new JPanel();
    		formatpanel(pnl5, col4,rowheight,true,2);
    		pnl5.setBackground(Color.white);
//    		pnl5.setLayout(new FlowLayout(FlowLayout.LEFT));
    		details[count] = new JButton("Details");
    		addbuttonmap(count, t.getid());
    		CekListener ceklisten = new CekListener();
    		details[count].addActionListener(ceklisten);
    		pnl5.add(details[count]);
    		count++;
    		paneltoadd.add(pnl5);
    		
    		panel0.add(paneltoadd);        	
//        	newpnl(t.getthreat(), t.gethost().name, Integer.toString(t.getorder()));        	
            System.out.println(t.getthreat() + " VM:" + t.gethost().name + " :::" + t.getorder());
        }
        
        createorgheader();             
        ArrayList<ThreatList> startorgthreats = readorgthreats();
        ArrayList<ThreatList> listorg = orgorder1(altorgresults);
        for (int i = 0; i < listorg.size();i++){
        	fillorgthreats(listorg.get(i));
        }        

        basepanel.add(panel0);
        JScrollPane scrPane = new JScrollPane(basepanel);
        frame1.add(scrPane);
        panel0.add(bottom, BorderLayout.SOUTH);

		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(1000,700);
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
		
//		System.out.println(orgresults[0] + "][" + orgresults[1]);
//		System.out.println("[" + altorgresults[0] + "][" + altorgresults[1] + "]");		
	}
	public ArrayList<ThreatList>  orgorder1(int [] order ){
		
//		int iArr[] = {2, 1, 102, 2};
		int iArr[] = order;
		boolean gotocon = false;
		boolean gotoint = false;
		boolean gotoava = false;
		boolean gotocmp = false;
		
		HashMap<Integer, ArrayList<Integer>> buttonmap = new HashMap<Integer,ArrayList<Integer>>();
		
		for (int i = 0; i < iArr.length; i++) {
			if (buttonmap.containsKey(iArr[i])==true){
				ArrayList<Integer> maplist = buttonmap.get(iArr[i]);
				maplist.add(i);
				buttonmap.put(iArr[i], maplist);
			}
			else {
				ArrayList<Integer> newmaplist = new ArrayList<Integer>();
				newmaplist.add(i);
				buttonmap.put(iArr[i], newmaplist);				
			}		
		}
		
		Set<Integer> keys = buttonmap.keySet();
        TreeSet sortedSet = new TreeSet<Integer>(keys);
        
        int[] orderoforg = new int [5];
        int count = 1;
        int lastval = 1;
        Iterator<Integer> itr=sortedSet.iterator();
        String attribute = null;
        
    	ArrayList<ThreatList> listoforg = new ArrayList<ThreatList>();

        while(itr.hasNext()){
        	
            Integer key=itr.next();
        	
        	if ((key-lastval)>10){
        		count+=100;
//        		System.out.println("Ls " + lastval + " key " + key); 
        	}
            ArrayList<Integer> getlist = buttonmap.get(key);
            for (int j = 0; j < getlist.size(); j++){
            	int thisval = getlist.get(j);
            	if (getlist.get(j)==0) attribute = "Confi";
            	else if (getlist.get(j)==1) attribute = "Integ";
            	else if (getlist.get(j)==2) attribute = "Avail";
            	else if (getlist.get(j)==3) attribute = "Compl";

//    			System.out.println(attribute + " &::" + count);
    			orderoforg[getlist.get(j)]=count;
    			
    			ArrayList<ThreatList> listthese = ThreatList.getthisorgthreats(thisval);
    			
    			if (gotocon == true){ listthese = ThreatList.deleteorgthreats(listthese, 0); 
    			}
    			if (gotoint == true){ listthese = ThreatList.deleteorgthreats(listthese, 1); 
    			}
    			if (gotoava == true){ listthese = ThreatList.deleteorgthreats(listthese, 2); 
    			}
    			if (gotocmp == true){ listthese = ThreatList.deleteorgthreats(listthese, 3); 
    			}
    			    			
    			if (thisval==0){ gotocon = true;
    			}
    			else if (thisval==1){ gotoint = true;
    			}
    			else if (thisval==2){ gotoava = true;
    			}
    			else if (thisval==3){ gotocmp = true;
    			}
//				System.out.println("SIZE IS " + listthese.size());
				listoforg.addAll(listthese);
				for (int k = 0; k < listthese.size(); k++){
					listthese.get(k).setval(count);
//					System.out.println(listthese.get(k).getname() + " and val will be " + count); 
				}
            }
            count++;
            lastval = key;
        }
//		System.out.println("---------------------------THREATS---------------------------");
//		for (int k = 0; k < listoforg.size(); k++){
//			System.out.println(listoforg.get(k).getname()); 
//		}
//		System.out.println("---------------------------END---------------------------");

//        System.out.println("["+orderoforg[0]+"]["+orderoforg[1]+"]["+orderoforg[2]+"]["+orderoforg[3]+"]"); 
		return listoforg;
	
	}
	public void fillorgthreats(ThreatList entry){
		if (startedorg==false){
			JPanel pnlheader = new JPanel();
			formatpanel(pnlheader, (col1+col2+col3+col4+col4),rowheight,true,2);
			pnlheader.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel panelname = new JLabel("Most important");
			panelname.setForeground(Color.RED);
			pnlheader.add(panelname);
			panel0.add(pnlheader);
		}
		else if (startedorg==true && entry.getval()>lastorgval){
    		JPanel pnlheader = new JPanel();
    		formatpanel(pnlheader, (col1+col2+col3+col4+col4),rowheight,true,2);
    		pnlheader.setLayout(new FlowLayout(FlowLayout.LEFT));
    		JLabel panelname = new JLabel("Next most important");
    		panelname.setForeground(Color.RED);
    		pnlheader.add(panelname);
    		panel0.add(pnlheader);			
		}
		
		JPanel paneltoadd = new JPanel();
		paneltoadd.setLayout(new BoxLayout(paneltoadd,BoxLayout.X_AXIS));
		formatpanel(paneltoadd, respanel,rowheight, true, 1);


		JPanel pnl1 = new JPanel();
		formatpanel(pnl1, orgcol,rowheight,true,2);
		pnl1.setBackground(Color.white);
		pnl1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel panelname = new JLabel(entry.getname());
		pnl1.add(panelname);
		paneltoadd.add(pnl1);
		
		JPanel pnl5 = new JPanel();
		formatpanel(pnl5, col4,rowheight,true,2);
		pnl5.setBackground(Color.white);
//		pnl5.setLayout(new FlowLayout(FlowLayout.LEFT));
		details[count] = new JButton("Details");
		addbuttonmap(count, entry.getid());
		CekListener ceklisten = new CekListener();
		details[count].addActionListener(ceklisten);
		pnl5.add(details[count]);
		count++;
		paneltoadd.add(pnl5);

		lastorgval = entry.getval();
		startedorg=true;
		
		panel0.add(paneltoadd);        	

	}
	
	
	
	
	
	public void createheader(){
		JPanel paneltoadd = new JPanel();
		paneltoadd.setLayout(new BoxLayout(paneltoadd,BoxLayout.X_AXIS));

//		formatpanel(JPanel paneltochange, int a, int b, boolean border, int col){
		Font font = new Font("Sans Serif", Font.BOLD, 10);
//		threatlabel1.setFont(font);

		
		JPanel pnl1 = new JPanel();
		formatpanel(pnl1, col1,rowheight,true,1);
		pnl1.setBackground(Color.LIGHT_GRAY);
		pnl1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel panelname = new JLabel("Threat");
//		panelname.setFont(font);
		pnl1.add(panelname);
		paneltoadd.add(pnl1);
		
		JPanel pnl2 = new JPanel();
		formatpanel(pnl2, col2,rowheight,true,1);
		pnl2.setBackground(Color.LIGHT_GRAY);
		pnl2.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel panelvm = new JLabel("VM");
		pnl2.add(panelvm);
		paneltoadd.add(pnl2);
		
		JPanel pnl3 = new JPanel();
		formatpanel(pnl3, col3,rowheight,true,1);
		pnl3.setBackground(Color.LIGHT_GRAY);
		pnl3.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel panelat = new JLabel("Attribute");
		pnl3.add(panelat);
		paneltoadd.add(pnl3);
		
		JPanel pnl4 = new JPanel();
		formatpanel(pnl4, col4,rowheight,true,1);
		pnl4.setBackground(Color.LIGHT_GRAY);
		pnl4.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel panelprop = new JLabel("Prop");
		pnl4.add(panelprop);
		paneltoadd.add(pnl4);

		JPanel pnl5 = new JPanel();
		formatpanel(pnl5, col4,rowheight,true,1);
		pnl5.setBackground(Color.LIGHT_GRAY);
		pnl5.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel p5label = new JLabel("Click for more");
		pnl5.add(p5label);
		paneltoadd.add(pnl5);

		
		panel0.add(paneltoadd);        	

	}
	public void checkdetails(int idofthreat){
		
		JPanel detailspanel = new JPanel();
//		detailspanel.setLayout(new BoxLayout(detailspanel,BoxLayout.X_AXIS));

		String labelthreat = ThreatList.getthreatname(idofthreat);		
		JLabel detaillabel = new JLabel(labelthreat);
		detailspanel.add(detaillabel);		
		
		tempPanel = panel0;
		
		basepanel.removeAll();
		
		getresults = new JButton("Back");
		Backbtn backlistener = new Backbtn();
		getresults.addActionListener(backlistener);
		detailspanel.add(getresults);
		basepanel.add(detailspanel);
		basepanel.revalidate();
		basepanel.repaint();
	}
	
	public void changeback(){	
		
//		tempPanel = panel0;
		
		basepanel.removeAll();
		basepanel.add(tempPanel);
		basepanel.revalidate();
		basepanel.repaint();
	}

	
	public void createorgheader(){
		JPanel panelgap = new JPanel();
		formatpanel(panelgap, 30,30,false,1);
		orgcol = respanel - col4;
//		System.out.println ("ORGCoL" +orgcol);
		JPanel paneltoadd = new JPanel();
		paneltoadd.setLayout(new BoxLayout(paneltoadd,BoxLayout.X_AXIS));

		formatpanel(paneltoadd, respanel,rowheight, true, 1);
		Font font = new Font("Sans Serif", Font.BOLD, 10);
//		threatlabel1.setFont(font);

		
		JPanel pnl1 = new JPanel();
		formatpanel(pnl1, orgcol,rowheight,true,1);
		pnl1.setBackground(Color.LIGHT_GRAY);
		pnl1.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel panelname = new JLabel("Organisational Threats");
//		panelname.setFont(font);
		pnl1.add(panelname);
		paneltoadd.add(pnl1);
		
		JPanel pnl5 = new JPanel();
		formatpanel(pnl5, col4,rowheight,true,1);
		pnl5.setBackground(Color.LIGHT_GRAY);
		pnl5.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel p5label = new JLabel("Click for more");
		pnl5.add(p5label);
		paneltoadd.add(pnl5);

		panel0.add(panelgap);
		panel0.add(paneltoadd);        	

	}

	
	public void newpnl(String a, String b, String c){

		JLabel cat1 = new JLabel(a);
		cat1.setForeground(Color.WHITE);
		JLabel cat2 = new JLabel(b);
//		cat2.setForeground(Color.red);
		cat2.setForeground(Color.WHITE);
		JLabel cat3 = new JLabel(c);
//		cat3.setForeground(Color.red);
		cat3.setForeground(Color.WHITE);

		categories = new JPanel();
		categories.setLayout(new BoxLayout(categories,BoxLayout.X_AXIS));

		JPanel category1 = new JPanel();
		formatpanel(category1,left, rowheight, true,2);
		category1.setBackground(Color.BLACK);
		category1.add(cat1);

		JPanel category2 = new JPanel();
		formatpanel(category2,middle, rowheight, true,2);
		category2.setBackground(Color.BLACK);
		category2.add(cat2);
		
		JPanel category3 = new JPanel();
		formatpanel(category3,right, rowheight, true,2);
		category3.setBackground(Color.BLACK);
		category3.add(cat3);	
		
		categories.add(category1);
		categories.add(category2);
		categories.add(category3);
		
		panel0.add(categories);		

	}
	
	
	public String dbquery(int attr){
		String q = null;
		if (attr ==0) q = "Confidentiality";
		else if (attr==1) q = "Integrity";
		else if (attr==2) q = "Availability";
		else if (attr==3) q = "Compliance";
		return q;
	}
	public ArrayList<Integer> checkmap(VM key){
		ArrayList<Integer> usedints = null; 
    	if (mapofints.containsKey(key)){
			usedints = (ArrayList<Integer>) mapofints.get(key);
//			usedints.add(attribute);
//    	maptoadd.put(key, preftoadd);
    	}
    	return usedints;
	}
	
    public void addtomap(VM key, int attribute){
//    	maptoadd.put(key)
    	if (mapofints.containsKey(key)){
			ArrayList<Integer> attributesatkey = (ArrayList<Integer>) mapofints.get(key);
			attributesatkey.add(attribute);
//    	maptoadd.put(key, preftoadd);
    	}
    	else{
    		ArrayList<Integer> newset = new ArrayList<Integer>();
    		newset.add(attribute);
    		mapofints.put(key, newset);
    	}
        Set<VM> keys = mapofints.keySet();			//get the keys for the map
        for (VM key1 : keys) {
//        	System.out.println(key1.name + "just added " + attribute); 
        	ArrayList<Integer> ints = mapofints.get(key1);						//get the nodes
        	for (int i = 0; i < ints.size(); i++){
//        		System.out.println("For " + key1.name + " Ints: " + ints.get(i));
        	}
        }
//    	System.out.println("::::::"); 


    }

	public Integer checkbtnmap(int key){
		Integer usedints = null; 
    	if (buttonmap.containsKey(key)){
			usedints = buttonmap.get(key);
//			usedints.add(attribute);
//    	maptoadd.put(key, preftoadd);
    	}
    	return usedints;
	}
    public void addbuttonmap(int key, int value){
//    	maptoadd.put(key)
		buttonmap.put(key, value);
    }

    
    
	public JPanel pnlBse(ActionListener nextback, JButton buttn){
//		NextBackHandler nextback = new NextBackHandler();
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//		buttn = new JButton[2];
		buttn.addActionListener(nextback);
		bottomPanel.add(buttn);
		return bottomPanel;
	}

	
	
    public Connection getCon(){
    	Connection con = null;
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
	
	public void formatpanel(JPanel paneltochange, int a, int b, boolean border, int col){

		if (border == true){	// include the border if border = true
			if (col == 1){
				paneltochange.setBorder(BorderFactory.createLineBorder(Color.black));
			}
			else {
				paneltochange.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			}
		}
		Dimension expectedDimension = new Dimension(a, b);
		paneltochange.setPreferredSize(expectedDimension);
		paneltochange.setMaximumSize(expectedDimension);
		paneltochange.setMinimumSize(expectedDimension);
		
	}    
	public void labelpanels(){
		JLabel cat1 = new JLabel("Threats");
		cat1.setForeground(Color.WHITE);
		JLabel cat2 = new JLabel("Responses");
//		cat2.setForeground(Color.red);
		cat2.setForeground(Color.WHITE);
		JLabel cat3 = new JLabel("Priority");
//		cat3.setForeground(Color.red);
		cat3.setForeground(Color.WHITE);
		
		categories = new JPanel();
		categories.setLayout(new BoxLayout(categories,BoxLayout.X_AXIS));

		JPanel category1 = new JPanel();
		formatpanel(category1,left, rowheight, true,2);
		category1.setBackground(Color.BLACK);
		category1.add(cat1);

		JPanel category2 = new JPanel();
		formatpanel(category2,middle, rowheight, true,2);
		category2.setBackground(Color.BLACK);
		category2.add(cat2);
		
		JPanel category3 = new JPanel();
		formatpanel(category3,right, rowheight, true,2);
		category3.setBackground(Color.BLACK);
		category3.add(cat3);	
		
		categories.add(category1);
		categories.add(category2);
		categories.add(category3);
		
		panel0.add(categories);		
}
	
	private class ListSorter implements Comparator<PrefResults>{
		 
	    @Override
	    public int compare(PrefResults e1, PrefResults e2) {
	        if(e1.value > e2.value){
	            return 1;
	        } else {
	            return -1;
	        }
	    }
	}
	private class ThreatSorter implements Comparator<Threat>{
		 
	    @Override
	    public int compare(Threat t1, Threat t2) {
	        if(t1.getorder() > t2.getorder()){
	            return 1;
	        } else {
	            return -1;
	        }
	    }
	}
	public class CekListener implements ActionListener {
	    public void actionPerformed(ActionEvent event){
	        for (int x=0;x<details.length;x++){
//	    		System.out.println(("Checking check:"+ x));        		

	        	if (event.getSource()==details[x]){
//	        		System.out.println("Checkl:" + check.length);
//	        		System.out.println("Clicked threat" +checkbtnmap(x));   
	        		checkdetails(checkbtnmap(x));
	        	}
	        }
	    }
	}

	private class OKHandler implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
//			int vm1pos = -1, vm2pos =-1;
	    	if (event.getSource()==buttn){				//ie if the button is 'next'     
//	    		System.out.println("clicked on framepanel ok button"); 
	    		frame1.setVisible(false);
	    		ProgramOne.vis();
	    	}
	    }
	}

	private class Backbtn implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
//			int vm1pos = -1, vm2pos =-1;
	    	if (event.getSource()==getresults){				//ie if the button is 'next'     
//	    		System.out.println("We want to go back"); 
	    		changeback();
	    	}
	    }
	}

}
