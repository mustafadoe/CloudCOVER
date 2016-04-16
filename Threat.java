
/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


public class Threat {
	private VM host;
	private String threat;
	private int order;
	private int attribute;
	private int probability;
	private int uniqueid;
	private String propnames;
//	private Data data;
	
	public Threat(VM hostval, String threatval, int orderval, int attrval, int probval, int id, String prpnames){
		host = hostval;
		threat = threatval;
		order = orderval;
		attribute = attrval;
		probability = probval;
		uniqueid = id;
		propnames = prpnames;
//		data = dataval;
	}
	public String getpropnames(){
		return this.propnames;
	}
	public VM gethost(){
		return this.host;
	}
	public String getthreat(){
		return this.threat;
	} 
	public int getorder(){
		return this.order;
	}
	public int getattr(){
		return this.attribute;
	}
	public int getprob(){
		return this.probability;
	}
	public int getid(){
		return this.uniqueid;
	}
//	public Data getdata(){
//		return this.data;
//	}
}
