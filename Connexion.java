import java.util.ArrayList;


/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


public class Connexion implements java.io.Serializable{

	private static ArrayList<Connexion> allcomms = new ArrayList<Connexion>();
	VM vm1;
	VM vm2;
	int encryption;
	String name;
	
	private boolean send;
	private boolean create;
	private boolean destroy;
	private boolean forward;
	
	public Connexion (VM vmOne, VM vmTwo, int encrypted, String commsname, boolean snd, boolean crt, boolean dst, boolean frw) {
		// TODO Auto-generated constructor stub	
		
		vm1 = vmOne;
		vm2 = vmTwo;
		allcomms.add(this);
		encryption = encrypted;
		name = commsname;
		send = snd;
		create = crt;
		destroy = dst;
		forward = frw;
				
		vm1.addcommsout(vm2);		//otherwise just add from vm1 to vm2
		vm1.changebool(4);
		vm2.addcommsin(vm1);
//		System.out.println("adding betwen " + vm1.name + " and " + vm2.name); 
//		System.out.println("TF: send: " + send + " create: " + create + " destroy: " + destroy); 
	}
	public boolean getsend(){
		return send;
	}

	public boolean getcreate(){
		return create;		
	}

	public boolean getdestroy(){
		return destroy;		
	}
	public boolean getforward(){
		return forward;
	}

	/*
	 * return the encryption int
	 */
	public int getencrypt(){
		return this.encryption;
	}
	
	/*
	 * we want checkname to return false, so we know its not true
	 */	
	public static boolean checkname(String newname){

		boolean present = false;
//		System.out.println(newname + " is at present " + present); 
		for (int i = 0; i < allcomms.size(); i++){
//			System.out.println("name1: "+ allcomms.get(i).vm1.name); 
//			System.out.println("name2: "+ allcomms.get(i).vm2.name); 
			
			if (allcomms.get(i).name.equals(newname)){				
//				System.out.println("this name is already in the system"); 
				present = true;
			}
//			else System.out.println("names are not true"); 
		}
		return present;		
	}
	public static void insertcomms(ArrayList<Connexion> addcomms){
		allcomms = addcomms;
	}
	public static void clearcomms(){
		allcomms.clear();
	}
	public static ArrayList<Connexion> returncomms(){
		return allcomms;
	}
	public static void removecomms (int delete){
		String namefrom = allcomms.get(delete).vm1.name;
		String nameto = allcomms.get(delete).vm2.name;
//		System.out.println("Deleting the connection with " + allcomms.get(delete).vm1.name + " and " + allcomms.get(delete).vm2.name); 
		VM.removevmcon(namefrom, nameto);
		allcomms.remove(delete);
	}
	
	public static void printcomms(){
		boolean isencrypted = false;
		Connexion current;
		System.out.println("Comms---------------- ");
		for (int i = 0; i < allcomms.size(); i++){
			current = allcomms.get(i);
			System.out.print(current.vm1.name + " >>>>> " + current.vm2.name + " ::::");
			System.out.print("CRT:" + current.getcreate() + " DST:" + current.getdestroy() + " SND:" + current.getsend()+ " FWD:" + current.getforward());
			System.out.println(); 
		}
//		System.out.println("The comms are: " + isencrypted);
		
		Data.printall();
	}
}
