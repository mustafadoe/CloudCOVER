
import java.io.*;
import java.util.ArrayList;
 
/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


public class FindExtension {
	private static final String FILE_DIR = "/home/mustafa/Dropbox/work/saves/inputs/"; 
//	private static final String FILE_DIR = "/home/mustafa/";
	private static final String FILE_TEXT_EXT = ".ser";
	static ArrayList<String> filenames = new ArrayList<String>();

	
//	public static void main(String args[]) {
//		new FindCertainExtension().listFile();
//	}
 
	public String[] filterList(){
		ArrayList<String> filenames = new FindExtension().listFile();
		String [] savefiles;
		
		for(int i = 0; i < filenames.size();i++){
			String path = filenames.get(i);
			String nameext = path.replace("/home/mustafa/Dropbox/work/saves/inputs//","");
//			String nameext = path.replace("/home/mustafa//","");

			String name = nameext.replace(".ser","");
//			System.out.println(name);
//			System.out.println(filenames.toString());
			filenames.set(i, name);
		}
		savefiles = filenames.toArray(new String[filenames.size()]);
		return savefiles;
	}
	
	public ArrayList listFile() {

		String folder = "/home/mustafa/Dropbox/work/saves/inputs"; 
//		String folder = "/home/mustafa";
		String ext = ".ser";
		
		GenericExtFilter filter = new GenericExtFilter(ext);
 
		File dir = new File(folder);
 
		if(dir.isDirectory()==false){
			System.out.println("Directory does not exists : " + FILE_DIR);
			return null;
		}
 
		// list out all the file name and filter by the extension
		String[] list = dir.list(filter);
 
		if (list.length == 0) {
			System.out.println("no files end with : " + ext);
			return null;
		}
 
		for (String file : list) {
			String temp = new StringBuffer(FILE_DIR).append(File.separator)
					.append(file).toString();
//			System.out.println("file : " + temp);
			filenames.add(temp);
			
		}
		return filenames;
	}
	public class GenericExtFilter implements FilenameFilter {
		 
		private String ext;
 
		public GenericExtFilter(String ext) {
			this.ext = ext;
		}
 
		public boolean accept(File dir, String name) {
			return (name.endsWith(ext));
		}
	}
}
