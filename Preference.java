
/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


public class Preference implements java.io.Serializable{

	private Data owner;
	private int property;
//	private int origval;
//	private int propval;
//	private ArrayList
	
	public Preference(Data prefowner, int prop){    		
		owner = prefowner;
		property = prop;
	}
	public String propverify(int ver){
		String stringprop = "";
		if (ver==0){stringprop="Confidentiality";}
		else if (ver==1){stringprop="Integrity";}
		else if (ver==2){stringprop="Availability";}
		else {stringprop="Compliance";}
		return stringprop;
	}
	public String getowner (){
		return this.owner.getname();
	}
	public int getprop () {
		return this.property;
	}
}
