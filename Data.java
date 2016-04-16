import java.util.ArrayList;

import javax.swing.JOptionPane;


/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


public class Data implements java.io.Serializable{
	private static ArrayList<String> datanames = new ArrayList<String>();
	private static ArrayList<Data> dataall = new ArrayList<Data>();
	
//	private int valueinteg;
//	private int valueavail;
//	private int valueconfi;
//	private int valuecompl;
	private String dataname;
//	private int[] instances;
	private ArrayList<VM> residingon;
	private ArrayList<Data> derivedfrom;
	private boolean[] datainfo;
	private ArrayList<String> users; 
	private String[] mapstrs;
	
	public Data (boolean[] answers, String name, int[] vms){
		datainfo = answers;
//		valueinteg = vinteg;
//		valueavail = vavail;
//		valueconfi = vconfi;
//		valuecompl = vcompl;
		dataname = name;		
		datanames.add(dataname);	//we also want to add the name for easy check
//		instances = vms;
		residingon = new ArrayList<VM>();
		derivedfrom = new ArrayList<Data>();
		users = new ArrayList<String>();
		dataall.add(this);
//		for(int i = 0; i < vms.length; i++){
//			System.out.println("Positions are " + vms[i]); 
//		}
		
		for(int i = 0; i < vms.length; i++){
			int value = (vms[i]-1);
			VM addData = VM.returnallvms().get(value);
//			System.out.println("Name of VM to add to is " + addData.name); 
			addData.insertdata(this);
			addData.printvmdata();
//			System.out.println("Adding " + dataname + "to " + addData.name + ", given pos" + vms[i]); 
			residingon.add(addData);
		}			
	}
	public Data (boolean[] answers, String name, int[] vms, int [] drvs, String[] stringsformap){
		System.out.println("VMIS is " + vms[0]); 

		datainfo = answers;
//		valueinteg = vinteg;
//		valueavail = vavail;
//		valueconfi = vconfi;
//		valuecompl = vcompl;
		dataname = name;		
		datanames.add(dataname);	//we also want to add the name for easy check
//		instances = vms;
		residingon = new ArrayList<VM>();
		derivedfrom = new ArrayList<Data>();
		dataall.add(this);
		users = new ArrayList<String>();
		mapstrs = stringsformap;
		
		
//		System.out.println("Answerlength is : " + answers.length); 
		for(int i = 0; i < answers.length; i++){
//			System.out.println("Answer is: " + answers[i]); 
		}
		
//		for(int i = 0; i < vms.length; i++){
			int value = (vms[0]-1);
			VM addData = VM.returnallvms().get(value);
//			System.out.println("Name of VM to add to is " + addData.name); 
			addData.insertdata(this);
			addData.printvmdata();
//			System.out.println("Adding " + dataname + "to " + addData.name + ", given pos" + vms[i]); 
			residingon.add(addData);
			for(int i = 0; i < mapstrs.length; i++){
//				System.out.println("Positions are " + mapstrs[i]); 
				addData.adddatatomap(mapstrs[i], answers[i]);
			}
			
//		}		
//		for(int i = 0; i < drvs.length; i++){
//			int value = (drvs[i]);
//			Data addData = Data.getdataall().get(value);
//			System.out.println("Adding to derived from:::" + addData.dataname);
//			this.addtoderivedfrom(addData);
////			addData.insertderivation(this);
////			System.out.println("Name of Data to add to is " + addData.getname()); 
////			addData.printvmdata();
////			System.out.println("Adding " + dataname + "to " + addData.name + ", given pos" + vms[i]); 
////			residingon.add(addData);
//		}
		for(int i = 0; i < derivedfrom.size(); i++){
//			System.out.println(derivedfrom.get(i).dataname); 
		}
	}

	public boolean[] getbooleans(){
		return this.datainfo;
	}
	
	/* 
	 * the list consists of the data items THIS data item was derived from.
	 */
	public void insertderivation(Data addthisdata){
		this.derivedfrom.add(addthisdata);
	}
	
	public static boolean nameexists(String name){
		boolean exists = false;
		for (int i = 0; i < datanames.size(); i++){
			if (datanames.get(i).equals(name)){
				exists = true;
    			JOptionPane.showMessageDialog(ProgramOne.frame,"Name already used","Message",JOptionPane.WARNING_MESSAGE);			
			}			
		}
		return exists;
	}
	public void adduser(String user){
		System.out.println("Data "+ this.dataname); 
		for (int i = 0; i < users.size(); i++){ 
			System.out.println("User " + i + " is " + users.get(i)); 
		}		
		this.users.add(user);

	}
	public ArrayList<String> getallusers(){
		return this.users;
	}


	
	public static void printall (){

		System.out.println("DATA---------"); 
		for (int i = 0; i < dataall.size(); i++){
			Data current = dataall.get(i);
			System.out.print("Data "+ current.dataname + " on::: "); 
			ArrayList<VM> myvms = current.getdatareside();			
			for (int j= 0; j < myvms.size(); j++){
				System.out.print(myvms.get(j).name + ", "); 
			}
			System.out.println(); 
			System.out.print("DRV:: "); 
			ArrayList<Data> rsdon = current.getderivedfrom();			
			for (int j= 0; j < rsdon.size(); j++){
				System.out.print(rsdon.get(j).dataname + ", "); 
			}
			System.out.println(); 			
		}			
	}
	
	public static void removederivations(String nameremove){
		for (int i = 0; i < dataall.size(); i++){
			 Data currentdata = dataall.get(i);
			 ArrayList<Data> drvdfrom = currentdata.derivedfrom;
			 for (int j = 0; j < drvdfrom.size(); j++){
				  if (drvdfrom.get(j).dataname.equals(nameremove)){
					  drvdfrom.remove(j);
					  j--;
				  }
			 }
		}
	}	
	
	public static void removedata (int delete){
//		System.out.print("Deleting data item " + dataall.get(delete).dataname + " from <dataall> size " + dataall.size()); 
		Data datadel = dataall.get(delete);
		ArrayList<String> vms4removal = new ArrayList<String>();
		for (int i = 0; i < datadel.residingon.size(); i++){
			vms4removal.add(datadel.residingon.get(i).name);	// we find the vms the data resides on
		}
		removederivations(datadel.dataname);					//we remove the data from any it is said to derive
		VM.removedata(vms4removal, datadel);					// and we call the method to remove the data from them too
		dataall.remove(delete);									// we remove the data
		datanames.remove(delete);								// and from the list of data names
		System.out.print(" ::: size now:" + dataall.size() + datanames.size());
	}
	public static void removevmfromarraylist(String namedvm){
		for (int i = 0; i < dataall.size(); i++){
				
			Data current = dataall.get(i);					//loop through each data item
			System.out.print("Data "+ current.dataname); 
			ArrayList<VM> myvms = current.getdatareside();	//get the vms that data is on
//			ArrayList<String> myvmnames = 
			for (int j= 0; j < myvms.size(); j++){
				System.out.print("::: "+ myvms.get(j).name); 
				if (myvms.get(j).name.equals(namedvm)){		//if the one being deleted (name) is on there
					myvms.remove(j);						//delete it					
//					System.out.println("Removing a VM in loop " + i); 
				}
			}
			System.out.println("  Data + " + current.dataname + "still resides on " + myvms.size() + " more: "); 
			for (int j= 0; j < myvms.size(); j++){
				System.out.print(myvms.get(j).name + ", "); 		
			}
			if (myvms.size()==0){
//				System.out.println("None left"); 			//if its not on vms any more, delete it
				removedata(i);				
			}			
		}
	}
	
	public static void removevm (String namedlt){
//		System.out.println("Size of dataall: " + dataall.size()); 
		int numofdata= dataall.size();
		for (int i = 0; i < numofdata; i++){
			System.out.println("In loop stage"+ i); 
			removevmfromarraylist(namedlt);
		}			
	}
	public void deletevm(VM deletethis){
		for (int i = 0; i < residingon.size(); i++){
			if ( residingon.get(i).name.equals(deletethis.name) ){
				System.out.println("deleting " + residingon.get(i).name + " from the data item"); 
				residingon.remove(i);
			}
		}
	}
	
	public String getname(){
		return this.dataname;
	}
//	public int getinteg(){
//		return this.valueinteg;
//	}
//	public int getavail(){
//		return this.valueavail;
//	}
//	public int getconfi(){
//		return this.valueconfi;
//	}
//	public int getcompl(){
//		return this.valuecompl;
//	}	
	public static ArrayList<String> getdatanames(){
		return datanames;
	}
	public static ArrayList<Data> getdataall(){
		return dataall;
	}
	public ArrayList<Data> getderivedfrom(){
		return this.derivedfrom;
	}
	public void addtoderivedfrom(Data derive){
		this.derivedfrom.add(derive);
	}
	
	public ArrayList<VM> getdatareside(){
		return this.residingon;
	}

	public static void cleardatanames(){
		datanames.clear();
	}
	public static void cleardataall(){
		dataall.clear();
	}

	public static void loaddatanames(ArrayList<String> names){
		datanames = names;
	}
	public static void loaddataall(ArrayList<Data> datalist){
		dataall = datalist;
	}
}
