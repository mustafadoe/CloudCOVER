import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


/**
 * 		We add a hashmap, which takes keys and outputs Preferencess
 */
public class SimpleMapofNodes {	     
    
    public static void addtomap(Map maptoadd, Preference preftoadd, int key){
//    	maptoadd.put(key)
    	if (maptoadd.containsKey(key)){
			ArrayList<Preference> preferencesatkey = (ArrayList<Preference>) maptoadd.get(key);
	    	preferencesatkey.add(preftoadd);
//    	maptoadd.put(key, preftoadd);
    	}
    	else{
    		ArrayList<Preference> newset = new ArrayList<Preference>();
    		newset.add(preftoadd);
    		maptoadd.put(key, newset);
    	}
    }
    public static Preference newprefs(Data data, int i){
    		Preference newprefobject = new Preference(data, (i));    	
    		return newprefobject;
    }
    public static Map createmap(){
        Map<Integer, ArrayList<Preference>> map = new HashMap<Integer, ArrayList<Preference>>();			//the map to store everything 
        return map;
    }        
//    public static class Preferences implements java.io.Serializable{
//    	
//    	private Data owner;
//		int property;
//		
//		public Preferences(Data prefowner, int prop){    		
//			owner = prefowner;
//			property = prop;
//		}
//		public String propverify(int ver){
//			String stringprop = "";
//			if (ver==0){stringprop="Confidentiality";}
//			else if (ver==1){stringprop="Integrity";}
//			else if (ver==2){stringprop="Availability";}
//			else {stringprop="Compliance";}
//			return stringprop;
//		}
//    }
}
