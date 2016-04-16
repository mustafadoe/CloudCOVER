import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */



@SuppressWarnings("serial")
public class VM implements java.io.Serializable{

	static boolean allbackedup = false;
	static boolean hasMaccess = false;
	static boolean hasSql = false;
	static boolean hasApi = false;
	static boolean hasManyusers = false;

	static ArrayList<VM> allvms = new ArrayList<VM>();
	static ArrayList<String> vmnames = new ArrayList<String>();
	
//	boolean encrypComs;		// not sure this is needed
	String name;		//can delete this and the associated constructor
	private ArrayList<Data> vmdata;
	private ArrayList<String> users; 
	private ArrayList<VM> conprop;		//vms that propagate confidentiality values to them ie ones that need protecting
	private ArrayList<VM> intprop;		//"" ""		""
	private ArrayList<VM> avaprop;		//""	""		""
	private ArrayList<VM> cmpprop;		//""	""		""

	private ArrayList<Data> conhere;
	private ArrayList<Data> inthere;
	private ArrayList<Data> avahere;
	private ArrayList<Data> cmphere;

	private Map<String, Boolean> threatbools;

	private boolean usedcon;
	private boolean usedint;
	private boolean usedava;
	private boolean usedcmp;
	
//	private boolean usednonprops; 				// this value means that we have used the propagated threatvalue, and we don't need to lower one
	
	private boolean conused;
	private boolean intused;
	private boolean avaused;
	private boolean cmpused;
	
	
	private boolean sql;
	private boolean msa;
	private boolean enc;
	private boolean com;
	
	private boolean anlsconfi;
	private boolean anlsinteg;
	private boolean anlsavail;
	private boolean anlscompl;
	
	
	private int intpubpriv;
	private int[] initvalues;
	private int[] propvalues;
	private int[] allvalues;
	private ArrayList<VM> propsfrom;
	ArrayList<VM> vmsin;
	ArrayList<VM> vmsout;
	private boolean [] instanceinfo;
//	boolean backedup;
//	boolean sql;
//	boolean msaccess;
//	boolean manyusers;
//	boolean idm;
//	boolean keymanage;
//	boolean api;
//	boolean javascript;
//	boolean allencryp;

//	private int avail;
//	private int integ;
//	private int confi;
//	private int compl;
	
//	public Object vmsout;

	public VM(String name1, boolean[] info, int pubPriv, String[] formap) {
		// TODO Auto-generated constructor stub	
//		encrypComs = coms;
		name = name1;
		instanceinfo = info;
		threatbools = new HashMap<String,Boolean>();

//		System.out.println("VM bool " + info.length + " & " + formap.length);
		for(int i = 0; i < formap.length; i++){
//			System.out.println("Positions are " + mapstrs[i]); 
			this.addtomap(formap[i], info[i]);
		}
		
//		backedup	=a1;
//		sql			=a2;
//		msaccess	=a3;
//		manyusers	=a4;
//		idm			=a5;
//		keymanage	=a6;
//		api			=a7;
//		javascript	=a8;
//		avail 		= avlpr;
//		integ 		= intpr;
//		compl 		= cmppr;
//		confi 		= cnfdn;
		intpubpriv = pubPriv;
		
		initvalues = new int[4];
		propvalues = new int[4];
		allvalues = new int[4];
		propsfrom = new ArrayList<VM>();
		vmsin = new ArrayList<VM>();
		vmsout = new ArrayList<VM>();
		vmdata = new ArrayList<Data>();
		users  = new ArrayList<String>();
		
		conprop = new ArrayList<VM>();
		intprop = new ArrayList<VM>();
		avaprop = new ArrayList<VM>();
		cmpprop = new ArrayList<VM>();

		conhere = new ArrayList<Data>();
		inthere = new ArrayList<Data>();
		avahere = new ArrayList<Data>();
		cmphere = new ArrayList<Data>();

//		System.out.println("adding name of " + this.name);		
//		System.out.println("this name : " + this.name);		

//		System.out.println("avl: " + avail + " integ: " + integ + " compl: " + compl + " intpubpriv: " + intpubpriv + " javascript: " + javascript);		
		
		vmnames.add(name1);
		allvms.add(this);
	
		/*
		 * Below we look at whether certain properties of the system are true, for later purposes of providing known threats about user's configuration
		 */
//		if (backedup==true){
//			allbackedup=true;
//		}
//		if (msaccess==true){
//			hasMaccess = true;
//		}
//		if (sql==true){
//			hasSql = true;
//		}
//		if (api==true){
//			hasApi = true;
//		}
//		if (manyusers==true){
//			hasManyusers = true;
//		}
		
	}
	
	public boolean getused1(){
		return conused;
	}
	public boolean getused2(){
		return intused;
	}
	public boolean getused3(){
		return avaused;
	}
	public boolean getused4(){
		return cmpused;
	}
	public void setused1(){
		this.conused = true;
	}
	public void setused2(){
		this.intused = true;		
	}
	public void setused3(){
		this.avaused = true;		
	}
	public void setused4(){
		this.cmpused = true;
	}

//	public VM getvmfromname (String namestring){
//
//		VM returnvm = null;
//		for (int i = 0; i < allvms.size(); i++){
//			VM thisvm = allvms.get(i);
//			if (thisvm.name.equals(namestring)){
//				returnvm = thisvm;
//			}
//		}
//		return returnvm;
//	}
	
	public Boolean getmapbool(String key){
		Boolean usedints = null; 
    	if (threatbools.containsKey(key)){
			usedints = threatbools.get(key);
//			usedints.add(attribute);
//    	maptoadd.put(key, preftoadd);
    	}
    	return usedints;
	}
	public Map getvmmap (){
		return this.threatbools;
	}
    public void addtomap(String key, boolean value){
//    	System.out.println(key + ":, " + value);
//    	maptoadd.put(key)
		threatbools.put(key, value);
    }
        public void adddatatomap(String key, boolean value){
//    	System.out.println(key + ":, " + value + " *******************");
    	Boolean iskeytrue = null;
    	if (threatbools.containsKey(key)){
    		iskeytrue = threatbools.get(key);    		
    	}
    	else iskeytrue = false;
    	if (iskeytrue == true){
//    		System.out.println(key + " is already is true, in which case we don't need to change anything: " + value);    		
    	}
    	else {
//    		System.out.println(key + " is false, so we can change it if it needs to be changed: " + value);
    		threatbools.put(key, value);
    	}
    }

        public boolean getucon(){
        	return this.usedcon;
        }
        public boolean getuint(){
        	return this.usedint;
        }
        public boolean getuava(){
        	return this.usedava;
        }
        public boolean getucmp(){
        	return this.usedcmp;
        }
        public void setucon(){
        	this.usedcon = true;
        }
        public void setuint(){
        	this.usedint = true;
        }
        public void setuava(){
        	this.usedava = true;
        }
        public void setucmp(){
        	this.usedcmp = true;
        }
        
        
	
	public void changebool(int booltochange){
		if (booltochange==1) this.sql = true;
		else if (booltochange==2) this.msa = true;
		else if (booltochange==3) this.enc = true;
		else if (booltochange==4) this.com = true;		
	}

	public boolean getsql (){
		return this.sql;
	}
	public boolean getmsa (){
		return this.msa;
	}
	public boolean getenc (){
		return this.enc;
	}
	public boolean getcom (){
		return this.com;
	}
	public boolean getanlscon (){
		return this.anlsconfi;
	}
	public boolean getanlsint (){
		return this.anlsinteg;
	}
	public boolean getanlsava (){
		return this.anlsavail;
	}
	public boolean getanlscom (){
		return this.anlscompl;
	}
	public void setanlscon(){
		this.anlsconfi = true;
	}
	public void setanlsint(){
		this.anlsinteg = true;
	}
	public void setanlsava(){
		this.anlsavail = true;
	}
	public void setanlscom(){
		this.anlscompl = true;
	}
	
	
	
	public ArrayList<VM> getconprops(){
		return this.conprop;
	}
	public ArrayList<VM> getintprops(){
		return this.intprop;
	}
	public ArrayList<VM> getavaprops(){
		return this.avaprop;
	}
	public ArrayList<VM> getcmpprops(){
		return this.cmpprop;
	}
	public ArrayList<Data> getconhere(){
		return this.conhere;
	}
	public ArrayList<Data> getinthere(){
		return this.inthere;
	}
	public ArrayList<Data> getavahere(){
		return this.avahere;
	}
	public ArrayList<Data> getcmphere(){
		return this.cmphere;
	}
	
	public void emptycon (){
		this.conprop.clear();
	}
	public void emptyint (){
		this.intprop.clear();		
	}
	public void emptyava (){
		this.avaprop.clear();		
	}
	public void emptycmp (){
		this.cmpprop.clear();		
	}
	public void addconprop(VM vmprop){
		this.conprop.add(vmprop);
	}
	public void addintprop(VM vmprop){
		this.intprop.add(vmprop);		
	}
	public void addavaprop(VM vmprop){
		this.avaprop.add(vmprop);
	}
	public void addcmpprop(VM vmprop){
		this.cmpprop.add(vmprop);		
	}
	public void emptyconhere(){
		this.conhere.clear();
	}
	public void emptyinthere(){
		this.inthere.clear();		
	}
	public void emptyavahere(){
		this.avahere.clear();
	}
	public void emptycmphere(){
		this.cmphere.clear();		
	}
	public void addconhere(Data vmhere){
		this.conhere.add(vmhere);
	}
	public void addinthere(Data vmhere){
		this.inthere.add(vmhere);		
	}
	public void addavahere(Data vmhere){
		this.avahere.add(vmhere);
	}
	public void addcmphere(Data vmhere){
		this.cmphere.add(vmhere);		
	}

	
	public int[] getpropvals(){
		return this.propvalues;
	}
	public int[] getinitvals(){
		return this.initvalues;
	}
	public int[] getallvals(){
		return this.allvalues;
	}
	public void setpropvals(int col, int res){
		this.propvalues[col]=res;
	}
	public void setinitvals(int col, int res){
		this.initvalues[col]=res;
	}
	public void setallvals(int col, int res){
		this.allvalues[col]=res;
	}
	
	public void emptyinitvals(){
		initvalues[0]=0;
		initvalues[1]=0;
		initvalues[2]=0;
		initvalues[3]=0;
	}
	public ArrayList<VM> propsfromGet(){
		return this.propsfrom;
	}
	public void propsfromAdd(VM vmtoadd){
		this.propsfrom.add(vmtoadd);
	}
	public void propsfromEmpty(){
		this.propsfrom.clear();
	}
	
	public ArrayList<Data> getvmdata(){
		return vmdata;
	}
	public ArrayList<String> getallusers(){
		return this.users;
	}
 	public void printvmdata(){
//		System.out.println(this.name + ":"); 
		ArrayList<Data> thisdata = this.getvmdata();
		for (int i = 0; i < thisdata.size(); i++){
//			System.out.println(thisdata.get(i).getname()); 
		}
	}
	public static void printinfo(){
		for (int i = 0; i < allvms.size(); i++){
			VM thisvm = allvms.get(i);
			System.out.print("VM" + thisvm.name + "  :::"); 			
//			System.out.println("BU" + thisvm.backedup +
//				" SQ" + thisvm.sql +
//				" MS" + thisvm.msaccess +
//				" MU" + thisvm.manyusers +
//				" ID" + thisvm.idm +
//				" KM" + thisvm.keymanage +
//				" AP" + thisvm.api +
//				" JS" + thisvm.javascript);
//			System.out.println(); 			
			ArrayList<VM> goout = thisvm.vmsout;
			System.out.print("vmout>>> "); 
			for (int j = 0; j < goout.size(); j++){
				System.out.print(goout.get(j).name + ", "); 				
			}
			System.out.println(); 
			ArrayList<VM> goin = thisvm.vmsin;
			System.out.print("vmin<<<< "); 
			for (int j = 0; j < goin.size(); j++){
				System.out.print(goin.get(j).name + ", "); 								
			}			
			System.out.println(); 
			ArrayList<Data> godata = thisvm.vmdata;
			System.out.print("data<<>> "); 
			for (int j = 0; j < godata.size(); j++){
				System.out.print(godata.get(j).getname() + ", "); 								
			}	
			System.out.println(); 
		}
		Connexion.printcomms();
//		Data.printall();
	}
	
	
	public static void removedata(ArrayList<String> vms4removal, Data todelete){
//		ArrayList<Data> thisdata = getvmdata();
		
		for (int i = 0; i < vms4removal.size(); i++){
//			System.out.println(thisdata.get(i).getname()); 
			String name = vms4removal.get(i);
			
			for (int j = 0; j < allvms.size(); j++){
				if (allvms.get(j).name.equals(name)){
//					System.out.println("B4 Size of data on " + allvms.get(j).name + " is " + allvms.get(j).getvmdata().size()); 
					allvms.get(j).getvmdata().remove(todelete);
//					System.out.println("AFTA Size of data on " + allvms.get(j).name + " is " + allvms.get(j).getvmdata().size()); 

				}
			}
			
		}
		
	}
	
	public void adduser(String user){
		this.users.add(user);
		System.out.println("VM "+ this.name); 
		for (int i = 0; i < users.size(); i++){ 
			System.out.println("User " + i + " is " + users.get(i)); 
		}		
	}	
	
	public void insertdata(Data addthisdata){
		this.vmdata.add(addthisdata);
	}
	
//	public void notallencrypt(){
//		this.allencryp=true;
//	}
	
	public int returnintpubpriv(){
		return intpubpriv;
	}
	
//	public boolean returnbackedup(){
//		return backedup;
//	}
//	public boolean returnsql(){
//		return sql;
//	}
//	public boolean returnmsa(){
//		return msaccess;
//	}	
//	public boolean returnmanyu(){
//		return manyusers;
//	}
//	public boolean returnidm(){
//		return idm;
//	}
//	public boolean returnkm(){
//		return keymanage;
//	}
//	public boolean returnapi(){
//		return api;
//	}
//	public boolean returnjava(){
//		return javascript;
//	}
//	public int returnavlb(){
//		return avail;
//	}
//	public int returnintg(){
//		return integ;
//	}
//	public int returncmpl(){
//		return compl;
//	}
	/*
	 * if a connection is being removed, we also call those vms to remove their knowledge of it.
	 */
	public static void removevmcon(String from, String to){
//		System.out.println("After"); 
		for (int i = 0; i < allvms.size(); i++){
			 VM thisvm = allvms.get(i);
			 if (thisvm.name.equals(from)){
				 ArrayList <VM> thisvmouts = thisvm.vmsout;				 
				 for (int j = 0; j < thisvmouts.size(); j++){
					 if (thisvmouts.get(j).name.equals(to)){
						 thisvmouts.remove(j);
						 j--;
					 }
				 }
			 }
		}
		for (int i = 0; i < allvms.size(); i++){
			 VM thisvm = allvms.get(i);
			 if (thisvm.name.equals(to)){
				 ArrayList <VM> thisvmin = thisvm.vmsin;				 
				 for (int j = 0; j < thisvmin.size(); j++){
					 if (thisvmin.get(j).name.equals(from)){
						 thisvmin.remove(j);
						 j--;
					 }
				 }
			 }
		}	
	}
	/*
	 * a vm which is being removed also needs to be removed from all vms which it is in the vmin or vmout list of
	 */
	public static void removevmcomms(String name){
//		System.out.println("After"); 
		for (int i = 0; i < allvms.size(); i++){
			 VM thisvm = allvms.get(i);
			 for (int j = 0; j < thisvm.vmsout.size(); j++){
				 ArrayList <VM> thisvmouts = thisvm.vmsout;
				 if (thisvmouts.get(j).name.equals(name)){
					 System.out.println("Removing " + thisvmouts.get(j).name); 
					 thisvmouts.remove(j);
					 j--;
				 }
			 }
			 for (int j = 0; j < thisvm.vmsin.size(); j++){
				 ArrayList <VM> thisvmsin = thisvm.vmsin;
				 if (thisvmsin.get(j).name.equals(name)){
					 System.out.println("Removing " + thisvmsin.get(j).name); 
					 thisvmsin.remove(j);
					 j--;
				 }
			 }
		}
	}
	
	
	public static void removevm(int delete){
//		Data.printall();
//		VM del = allvms.get(delete);
		
		String name = allvms.get(delete).name;
		Data.removevm(name);
		
		allvms.remove(delete);
		vmnames.remove(delete);
		
		removevmcomms(name);
//		System.out.println("After"); 

//		Data.printall();

//		System.out.println("size of vms is " + allvms.size()); 
	}

	public static	ArrayList<String> returnvmnames (){
		return vmnames;
	}
	public static ArrayList<VM> returnallvms(){
		return allvms;
	}
//	public boolean returnvmjava (){
//		return javascript;
//	}
	
	public void addcommsin(VM to){
		this.vmsin.add(to);
	}
	public void addcommsout(VM to){
		this.vmsout.add(to);
	}

	public static void clearvms(){
		allvms.clear();
	}
	public static void clearvmnames(){
		vmnames.clear();
	}
	/*
	 * checknames is called @vmframe, checks if passed vmname is used. we want it to be false.
	 */
	public static boolean checknames(String newname){

		boolean present = false;
//		System.out.println(newname + " is at present " + present); 
		for (int i = 0; i < vmnames.size(); i++){
			if (vmnames.get(i).equals(newname)){
				System.out.println("the names are the same"); 
				present = true;
			}
		}
		return present;		
	}
	
	public static void findvms(){
//		System.out.println("VMs: ");		
//		System.out.println("There are " + allvms.size() + " VMs");
		boolean more1 = false;			//tells us if any VM has more than 1 user
		VM current;
		for (int i = 0; i < allvms.size(); i++){
			current = allvms.get(i);
//			System.out.println("Name: " + current.name);
			
			for (int j = 0; j < current.vmsin.size();j++){
//				System.out.println(current.name + " gets data in from " + current.vmsin.get(j).name);
			}
			for (int j = 0; j < current.vmsout.size();j++){
//				System.out.println(current.name + " sends data out from " + current.vmsout.get(j).name);
			}
//			System.out.println("Name: " + current.name);
//			System.out.println("Backed up: " + current.backedup);
//			System.out.println("SQL: " + current.sql);
//			System.out.println("MSAccess: " + current.msaccess);
//			System.out.println("Many users: " + current.manyusers);
//
		}
//		System.out.println(more1);
		
}
}
