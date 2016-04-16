import java.util.ArrayList;


/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


public class ThreatList {

	private static ArrayList<ThreatList> allthreats = new ArrayList<ThreatList>();
	private static ArrayList<ThreatList> confithreats = new ArrayList<ThreatList>();
	private static ArrayList<ThreatList> integthreats = new ArrayList<ThreatList>();
	private static ArrayList<ThreatList> availthreats = new ArrayList<ThreatList>();
	private static ArrayList<ThreatList> complthreats = new ArrayList<ThreatList>();

	private static ArrayList<ThreatList> allorgthreats = new ArrayList<ThreatList>();
	private static ArrayList<ThreatList> confiorgthreats = new ArrayList<ThreatList>();
	private static ArrayList<ThreatList> integorgthreats = new ArrayList<ThreatList>();
	private static ArrayList<ThreatList> availorgthreats = new ArrayList<ThreatList>();
	private static ArrayList<ThreatList> complorgthreats = new ArrayList<ThreatList>();

	
	private String name;
	private int prob;
	private int identity;
	private boolean confitf;
	private boolean integtf;
	private boolean availtf;
	private boolean compltf;
	private boolean proptf;
	private String include;
	private int value;
	
	public ThreatList(String threatname, int probability, int uniqueid, boolean confi, boolean integ, boolean avail, boolean compl, boolean prop, String exclude){
		name = threatname;
		prob = probability;
		identity = uniqueid;
		confitf = confi;
		integtf = integ;
		availtf = avail;
		compltf = compl;
		proptf = prop;
		include = exclude;
//		System.out.println("CNSTRCT:: " + name + ", " + proptf);	            

	}
	public void setval(int val){
		value = val;
	}
	public int getval(){
		return value;
	}
	public boolean getproptf(){
		return proptf;
	}
	
	public String getexclude(){
		return this.include;
	}
	public String getname (){
		return this.name;
	}
	public int getprob(){
		return this.prob;
	}
	public int getid(){
		return this.identity;
	}
	public static ArrayList<ThreatList> getallthreats(){
		return allthreats;
	}
	public static ArrayList<ThreatList> getconfithreats(){
		return confithreats;
	}
	public static ArrayList<ThreatList> getintegthreats(){
		return integthreats;
	}
	public static ArrayList<ThreatList> getavailthreats(){
		return availthreats;
	}
	public static ArrayList<ThreatList> getcomplthreats(){
		return complthreats;
	}

	public static void addallthreats(ThreatList threattoadd){
		allthreats.add(threattoadd);
	}
	public static void addconfithreats(ThreatList threattoadd){
		confithreats.add(threattoadd);
	}
	public static void addintegthreats(ThreatList threattoadd){
		integthreats.add(threattoadd);
	}
	public static void addavailthreats(ThreatList threattoadd){
		availthreats.add(threattoadd);
	}
	public static void addcomplthreats(ThreatList threattoadd){
		complthreats.add(threattoadd);
	}

	public static String getthreatname(int threatid){
		String nameofthreat = null;
		for (int i = 0; i < allthreats.size(); i++){
			if (allthreats.get(i).identity==threatid){
//				System.out.println("Name:" + allthreats.get(i).name);
				nameofthreat=allthreats.get(i).name;
			}
		}
		for (int i = 0; i < allorgthreats.size(); i++){
			if (allorgthreats.get(i).identity==threatid){
//				System.out.println("Name:" + allorgthreats.get(i).name);
				nameofthreat=allorgthreats.get(i).name;
			}
		}
		return nameofthreat;
	}
	
	/*
	 * This method takes a list of threats and deletes according to the given instruction
	 */
	public static ArrayList<ThreatList> deletethreats(ArrayList<ThreatList> beforedelete, int attributetodel){
//		System.out.println("We want to delete " + attributetodel); 
		ArrayList<ThreatList> compare = null;
		if (attributetodel ==0){ compare = confithreats;	}
		else if (attributetodel ==1){ compare  = integthreats;	}
		else if (attributetodel ==2){ compare = availthreats;	}
		else if (attributetodel ==3){ compare = complthreats;	}
		
		ArrayList<ThreatList> afterdelete = new ArrayList<ThreatList>();
		
		for (int i = 0; i < beforedelete.size(); i++){
			boolean include = false;
			for (int j = 0; j < compare.size(); j++){ 
				if (beforedelete.get(i).equals(compare.get(j))){
//					System.out.println("Add this:" + beforedelete.get(i).name+ " &" + compare.get(j).name); 		
					include = true; 
				}
			}
			if (include==false){afterdelete.add(beforedelete.get(i));}
		}		
//		for (int i = 0; i < afterdelete.size(); i++){
//			System.out.println("AFTER:" +afterdelete.get(i).name); 
//		}
		return afterdelete;
	}
	
	/*
	 * This method takes a list of threats and deletes the non propagating threats
	 */
	public static ArrayList<ThreatList> deletenonprops(ArrayList<ThreatList> beforedelete){
//		System.out.println("***********************************************************************************");
		ArrayList<ThreatList> afterdelete = new ArrayList<ThreatList>();
		
		for (int i = 0; i < beforedelete.size(); i++){
			ThreatList thisthreat = beforedelete.get(i);
			if (thisthreat.proptf==true){
				afterdelete.add(thisthreat);			
				}		
		}
		for (int i = 0; i < afterdelete.size(); i++){
//			System.out.println("********************************8AFTER:" +afterdelete.get(i).name); 
		}
		return afterdelete;
	}

	
	
	
	/*
	 * This method takes a list of threats and deletes according to the given instruction
	 */
	public static ArrayList<ThreatList> deleteorgthreats(ArrayList<ThreatList> beforedelete, int attributetodel){
		System.out.println("We want to delete " + attributetodel); 
		ArrayList<ThreatList> compare = null;
		if (attributetodel ==0){ compare = confiorgthreats;	}
		else if (attributetodel ==1){ compare  = integorgthreats;	}
		else if (attributetodel ==2){ compare = availorgthreats;	}
		else if (attributetodel ==3){ compare = complorgthreats;	}
		
		ArrayList<ThreatList> afterdelete = new ArrayList<ThreatList>();
		
		for (int i = 0; i < beforedelete.size(); i++){
			boolean include = false;
			for (int j = 0; j < compare.size(); j++){ 
				if (beforedelete.get(i).equals(compare.get(j))){
					System.out.println("A: " + beforedelete.get(i).name+ " B: " + compare.get(j).name); 		
					include = true; 
				}
			}
			if (include==false){afterdelete.add(beforedelete.get(i));}
		}		
//		for (int i = 0; i < afterdelete.size(); i++){
//			System.out.println("AFTER:" +afterdelete.get(i).name); 
//		}
		return afterdelete;
	}
	
	public static ArrayList<ThreatList> getthisorgthreats(int attr){
		ArrayList<ThreatList> returnthis = null;
		if (attr ==0) returnthis = confiorgthreats;
		else if (attr ==1) returnthis = integorgthreats;
		else if (attr ==2) returnthis = availorgthreats;
		else if (attr ==3) returnthis = complorgthreats;
		
		return returnthis;
	}
	
	public static ArrayList<ThreatList> getallorgthreats(){
		return allorgthreats;
	}
	public static ArrayList<ThreatList> getconfiorgthreats(){
		return confiorgthreats;
	}
	public static ArrayList<ThreatList> getintegorgthreats(){
		return integorgthreats;
	}
	public static ArrayList<ThreatList> getavailorgthreats(){
		return availorgthreats;
	}
	public static ArrayList<ThreatList> getcomplorgthreats(){
		return complorgthreats;
	}

	public static void addallorgthreats(ThreatList threattoadd){
		allorgthreats.add(threattoadd);
	}
	public static void addconfiorgthreats(ThreatList threattoadd){
		confiorgthreats.add(threattoadd);
	}
	public static void addintegorgthreats(ThreatList threattoadd){
		integorgthreats.add(threattoadd);
	}
	public static void addavailorgthreats(ThreatList threattoadd){
		availorgthreats.add(threattoadd);
	}
	public static void addcomplorgthreats(ThreatList threattoadd){
		complorgthreats.add(threattoadd);
	}

}
