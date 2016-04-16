// ReadFile.java    
import java.io.IOException;  
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException;  
import java.util.ArrayList;  
import java.util.List;  
  
/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


public class ReadFile   
{  
    public String[] readLines(String filename) throws IOException       {  

    	FileReader fileReader = new FileReader(filename);    
        BufferedReader bufferedReader = new BufferedReader(fileReader);  
        List<String> lines = new ArrayList<String>();  
        String line = null;  
        int size = 0;
        while ((line = bufferedReader.readLine()) != null)           {  
            lines.add(line);  
            size++;
        }  
//        System.out.println(size); 
        bufferedReader.close();  
          
        return lines.toArray(new String[lines.size()]);  
    }     
    public String[] readLines() throws IOException       {  

    	FileReader fileReader = new FileReader("/home/mustafa/Dropbox/work/saves/threats.txt");    
        BufferedReader bufferedReader = new BufferedReader(fileReader);  
        List<String> lines = new ArrayList<String>();  
        String line = null;  
        int size = 0;
        while ((line = bufferedReader.readLine()) != null)           {  
            lines.add(line);  
            size++;
        }  
//        System.out.println(size); 
        bufferedReader.close();  
          
        return lines.toArray(new String[lines.size()]);  
    }     
    public String[] readLines(boolean org) throws IOException       {  

    	FileReader fileReader = new FileReader("/home/mustafa/Dropbox/work/saves/orgthreats.txt");    
        BufferedReader bufferedReader = new BufferedReader(fileReader);  
        List<String> lines = new ArrayList<String>();  
        String line = null;  
        int size = 0;
        while ((line = bufferedReader.readLine()) != null)           {  
            lines.add(line);  
            size++;
        }  
//        System.out.println(size); 
        bufferedReader.close();  
          
        return lines.toArray(new String[lines.size()]);  
    }     

//    public static void main(String[] args)       {  
//
//    }  
}  
