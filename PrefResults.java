
/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


public class PrefResults <PrefResults>{
//	String name;
	VM host;
	int value;
	int attribute; 
	boolean propval;
	VM threatfrom;
	String vmfrom;
	/**
	 * @param args
	 */
	public PrefResults(VM vmhost, int intvalue, int intattribute, boolean propvaltf, VM threatfromvm, String from){
		host = vmhost;
		value = intvalue;
		propval = propvaltf;
		threatfrom = threatfromvm;
		attribute = intattribute;
		vmfrom = from;
	}
//	@Override
//	public int compareTo(PrefResults anotherresult) {
//		// TODO Auto-generated method stub
//		int compval = ((PrefResults) anotherresult).value; 
//		return this.value - compval;
//
////		return this.value - anotherresult.value;
//	}
}
