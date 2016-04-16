import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


public class AddUser {
	private static ArrayList<String> usernames = new ArrayList<String>();
	@SuppressWarnings("rawtypes")
	private ArrayList<JComboBox> combos;
	private ArrayList<Integer> comboselections;
//	private JFrame frame;
	@SuppressWarnings("rawtypes")
	private ArrayList<JComboBox> datacombos;
	private ArrayList<Integer> dataselections;

	private JFrame frame;
	private JPanel panelx;
	private JPanel panel0;
	private JPanel panelupdate;
	private JPanel panelupdate2;

	private JTextField username;
	private String usernamestring;
	private JButton[] buttn;
	private JButton[] addbtn;
	private int panelwidth;
	private int panelheight;

	public AddUser(){		
	}

	@SuppressWarnings("rawtypes")
	public JPanel resetpanel (){
//		numofinstances = 0;
		panelheight = 30;
		panelwidth = 590;		
		combos = new ArrayList<JComboBox>();
		comboselections = new ArrayList<Integer>();	
		datacombos = new ArrayList<JComboBox>();
		dataselections = new ArrayList<Integer>();
		addbtn = new JButton[2];
		
		panelupdate = new JPanel();
		panelupdate.setLayout(new BoxLayout(panelupdate,BoxLayout.Y_AXIS));
		panelupdate2 = new JPanel();
		panelupdate2.setLayout(new BoxLayout(panelupdate2,BoxLayout.Y_AXIS));		

		panelx = new JPanel();		//base panel returned at end
		username = new JTextField(10);
		panelx.add(PanelMaker.pnlTop(username));

		panel0 = new JPanel();
		panel0.setLayout(new BoxLayout(panel0,BoxLayout.Y_AXIS));
		panel0.add(addtouser());
		panel0.add(panelupdate);
		panel0.add(addtodata());
		panel0.add(panelupdate2);		
		
		panelx.add(panel0);		
		panelx.add(createBottom(), BorderLayout.SOUTH);

		return panelx;
	}
	/* We add the OK and Back buttons */
	public JPanel createBottom(){
		NextBackHandler nextback = new NextBackHandler();
		buttn = new JButton[2];
		JPanel bottomPanel = PanelMaker.pnlBse(nextback, buttn);		
		return bottomPanel;
	}
	public JPanel addtouser(){
		JPanel addusertoinstance = new JPanel();
		ListenerAddInstance listen = new ListenerAddInstance();
		formatpanel(addusertoinstance,panelwidth,panelheight);
//		JButton addusr = new JButton("Add to instance");
		addbtn[0] = new JButton("Click to add to instance");
		addbtn[0].addActionListener(listen);
		addusertoinstance.add(addbtn[0]);
		return addusertoinstance;
	}
	public JPanel addtodata(){
		JPanel addusertoinstance = new JPanel();
		ListenerAddInstance listen = new ListenerAddInstance();
		formatpanel(addusertoinstance,panelwidth,panelheight);
		addbtn[1] = new JButton("Click to add to data");
		addbtn[1].addActionListener(listen);
		addusertoinstance.add(addbtn[1]);
		return addusertoinstance;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JPanel adddropdownpanel(JLabel addlabel){	//need listener, string, cmbox 2 panel
		JPanel dropdownpanel = new JPanel();
		ListenerInstanceName listener = new ListenerInstanceName();
		ArrayList<String> vmnames1 = VM.returnvmnames();
		String[] vmnames = vmnames1.toArray(new String[vmnames1.size()]);	
		JComboBox instances = new JComboBox(vmnames);
		dropdownpanel = PanelMaker.pnlBox(addlabel, listener, panelwidth, panelheight, instances);
		formatpanel(dropdownpanel, panelwidth,panelheight);
		combos.add(instances);
		return dropdownpanel;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JPanel adddropdownpanel2(JLabel addlabel){	//need listener, string, cmbox 2 panel
		JPanel dropdownpanel = new JPanel();
		ListenerDataName listener = new ListenerDataName();
		ArrayList<String> datanames = Data.getdatanames();
//		ArrayList<String> vmnames1 = VM.returnvmnames();
		String[] dnames = datanames.toArray(new String[datanames.size()]);				
		JComboBox datacombobox = new JComboBox(dnames);
		dropdownpanel = PanelMaker.pnlBox(addlabel, listener, panelwidth, panelheight, datacombobox);
		formatpanel(dropdownpanel, panelwidth,panelheight);
		datacombos.add(datacombobox);
		return dropdownpanel;
	}

	public JPanel updateinstancepanel(){
		JLabel a = new JLabel("Add to");
		panelupdate.add(adddropdownpanel(a));
		panelupdate.revalidate();
		panelupdate.repaint();

		return panelupdate;
	}
	
	public JPanel updateinstancepanel2(){
		JLabel a = new JLabel("Add to");
		panelupdate2.add(adddropdownpanel2(a));
		panelupdate2.revalidate();
		panelupdate2.repaint();

		return panelupdate2;
	}

	
	public static ArrayList<String> returnusers(){
		return usernames;
	}
	public static void clearusers(){
		usernames.clear();
	}
	public static void loadusers (ArrayList<String> userstoload){
		usernames = userstoload;
	}
	public static int numusers(){
		return usernames.size();
	}
	
	private void addusername(String uname){
//		System.out.println("To add: " + uname + " length: " + usernames.size()); 
		usernames.add(uname);
//		System.out.println("To add: " + uname + " length: " + usernames.size()); 		
	}

	private void deleteusername(String uname){
		usernames.remove(uname);
	}
	
	private void printusernames(){
		for (int i = 0; i < usernames.size(); i++){
			System.out.println(usernames.get(i)); 
		}
	}

	private void addselections(){
		for (int i = 0; i < comboselections.size(); i++){			//here we add the user to each instance selected
			ArrayList<VM> vms = VM.allvms;			
			vms.get(comboselections.get(i)).adduser(usernamestring);
		}
		for (int i = 0; i < dataselections.size(); i++){			//here we add the user to each data selected
//			System.out.println("Data selection is: " + dataselections.get(i)); 
			ArrayList<Data> alldata = Data.getdataall();			
			alldata.get(dataselections.get(i)).adduser(usernamestring);
		}
	}
	
	public void formatpanel(JPanel paneltochange, int a, int b){
//		paneltochange.setBorder(BorderFactory.createLineBorder(Color.black));
		Dimension expectedDimension = new Dimension(a, b);
		paneltochange.setPreferredSize(expectedDimension);
		paneltochange.setMaximumSize(expectedDimension);
		paneltochange.setMinimumSize(expectedDimension);
	}   
	public boolean isUsernameEmpty(){
		boolean empty = false;
		usernamestring = username.getText();			//we want to know if the name is empty
		if (usernamestring.isEmpty()){
			JOptionPane.showMessageDialog(ProgramOne.frame,"Nothing entered","Message",JOptionPane.WARNING_MESSAGE);			
			empty = true;
		}		
		return empty;
	}
	public boolean doesUsernameExist(){
		boolean alreadyexists = false;
		String usernamestring = username.getText();			
		for (int i = 0; i < usernames.size(); i++){
			String usernametocompare = usernames.get(i);
			System.out.println(usernamestring + " and " + usernametocompare); 
			if (usernamestring.equals(usernametocompare)){
				alreadyexists = true;
				JOptionPane.showMessageDialog(ProgramOne.frame,"This name already exists","Message",JOptionPane.WARNING_MESSAGE);			
//				System.out.println("Exists"); 
			}
		}
		return alreadyexists;
	}
	private class ListenerAddInstance implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
	    	if (event.getSource()==addbtn[0]){
		    	updateinstancepanel();									//we want to add another combobox for another instance to add to
	    	}
	    	else {
	    		updateinstancepanel2();
	    	}
		}
    }//ComboListendrvnum
	private class ListenerInstanceName implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
	    	int place;
	        for (int x=0;x<combos.size();x++){									//get which combobox clicked
	        	if (event.getSource()==combos.get(x)){							
		        	place = (combos.get(x).getSelectedIndex());
	        		System.out.println("Item at ComboBox" + x + " Item "+ place); 
	        		
	        		if (combos.size()> comboselections.size()){					//check if lists are same size (if not, selection not been made before)
	        			comboselections.add(place);
	        		}
	        		else {
	        			System.out.println("We want to replace the one in the " + x + "th combobx with the number " + place); 
	        			comboselections.set(x, place);							//otherwise, replace instead of adding
	        		}
	        	}
			}
	    }
	}
	private class ListenerDataName implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
	    	int place;
	        for (int x=0;x<datacombos.size();x++){									//get which combobox clicked
	        	if (event.getSource()==datacombos.get(x)){							
		        	place = (datacombos.get(x).getSelectedIndex());
	        		System.out.println("Item at DataComboBox" + x + " Item "+ place); 
	        		
	        		if (datacombos.size()> dataselections.size()){					//check if lists are same size (if not, selection not been made before)
	        			dataselections.add(place);
	        		}
	        		else {
	        			System.out.println("We want to replace the one in the " + x + "th combobx with the number " + place); 
	        			dataselections.set(x, place);							//otherwise, replace instead of adding
	        		}
	        	}
			}
	    }
	}

	private class NextBackHandler implements ActionListener {
	    public void actionPerformed(ActionEvent event){
	        	if (event.getSource()==buttn[0]){
//	        		System.out.println("Clicked on OK"); 	
	        		if (isUsernameEmpty()==false		&& 
	        			doesUsernameExist()==false)
	        		{			
	        			addusername(usernamestring);
//	        			printusernames();
	        			addselections();
        				JOptionPane.showMessageDialog(frame,usernamestring + "has been added as a user","Empty values",JOptionPane.PLAIN_MESSAGE);			
	        			ProgramOne.changeframe();
	        		}	        		
	        	}
	        	else ProgramOne.changeframe();			//ie if cancel is clicked
	        
	    }	        
	}
}
