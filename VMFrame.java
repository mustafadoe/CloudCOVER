import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


public class VMFrame {
	private String[] questions;// = {"Infrequently backed up", "SQL", "MS Access", "Has more than 1 user", "Has a keyserver", "Uses an IDM", "Is connected to using an API", "Javascript"};
	private String[] questionsinfo;// = {"Click if the data from this instance is not frequently backed up", "Click if this instance uses an SQL", 
//			"Click if this instance uses MS Access", "Click if this instance has more than one user", "Click if this instance manages a keyserver",
//			"Click if this instance manages access using an Identity Management tool", "Click if this instance uses an API to manage conncections to it",
//			"Click if this instance uses Javascript"};
	private boolean[] questionanswersvm;
	private String[] strformap;

	private boolean start;
	private int state;
	JFrame frame;
//	private PanButtons mainpanel;
    JTextField vmachinename;

	private JComboBox[] userdropdown;
	private JComboBox numberofusers;

    private String input;
    private JCheckBox[] button;
    private JButton[] buttn;
	private JPanel panel0;
	private JPanel panel1;					//
	private JPanel panelupdate;
	private JPanel panelupdatebase;

	private int usercount;
	private int[] usertrack;
	private int intymax;
	private JComboBox choosePubPriv;
	private int xmax;
	private boolean statehere[];
	String vmname;
	private String importance;
	private int priority;
	private int instance;
	private int intpubpriv;	
	private String userpriority;
	private int userintpriority;
	private int panelheight;
	private int panelwidth;	
	
	
	private ArrayList<String> hostedusers;// = new ArrayList<String>();

//	private int numberofusers;
	
	public VMFrame(){
//		newframe();
	}
	public JPanel resetpanel(){
		hostedusers = new ArrayList<String> ();
		input = null;
//		vmname = name;
		start = false;
		state =0;
		usercount = 0;
		intpubpriv = 0;
		
		panelheight = 30;
		panelwidth = 590;	

//		frame = new JFrame();
		JPanel panelx = new JPanel();		
		panelx.setLayout(new BoxLayout(panelx,BoxLayout.Y_AXIS));
		vmachinename = new JTextField(10);		
		panelx.add(PanelMaker.pnlTop(vmachinename));					
		panel0 = new JPanel();
//		panel0.setLayout(new BoxLayout(panel0,BoxLayout.Y_AXIS));
		panel0.add(addquestionspanel());
		panel0.add(createdropdown()); //private or public		
		JPanel blankpanel = new JPanel();
		formatPanel(blankpanel,100,30);
		panel0.add(blankpanel);		
		panelx.add(panel0);
		panelx.add(createbottom(), BorderLayout.SOUTH);

		return panelx;
	}
	public JPanel createdropdown(){
		JLabel dropbabel = new JLabel("Instance in public or private cloud?");
		ListenPubPriv listen = new ListenPubPriv();
		String[] pubpriv = new String[] { "Public cloud", "Private cloud"};
		choosePubPriv = new JComboBox(pubpriv);
		JPanel dropdown = PanelMaker.pnlBox(dropbabel, listen, panelwidth, panelheight, choosePubPriv);
		return dropdown;
	}
	public JPanel createbottom(){
		NextBackHandler nextback = new NextBackHandler();
		buttn = new JButton[2];
		JPanel bottomPanel = PanelMaker.pnlBse(nextback, buttn);		
		return bottomPanel;
	}
	public JPanel addquestionspanel(){
//		System.out.println("Listen"); 
		Listenervm listen=new Listenervm();	
		questions = loadquestions();
		questionanswersvm = new boolean [questions.length];
		intymax = questions.length;
		button = new JCheckBox[questions.length];		
		panel1 = PanelMaker.pnlQst(listen, button, questions);
		return panel1;
	}
//	public JPanel addquestionspanel(){
//		Listener listen=new Listener();	
//		questions = loadquestions();
//		questionsanswers = new boolean [questions.length];
//		xmax = questions.length;
////		System.out.println("Q length " + questionsanswers.length); 
//		button = new JCheckBox[questions.length];		
//		panel1 = PanelMaker.pnlQst(listen, button, questions);
//		return panel1;
//	}


	private void addusers (String username){
		this.hostedusers.add(username);
	}
	private void printusers (){
		for (int i = 0; i < this.hostedusers.size(); i++){
			System.out.println(this.hostedusers.get(i)); 
		}
	}
	
	public String[] loadquestions(){
    	ReadFile rf = new ReadFile();  
    	String filename = "/home/mustafa/Dropbox/work/saves/instance.txt";
//    	String filename = "/home/mustafa/Dropbox/data.txt";

//		String filename = "/home/mustafa/Desktop/Qs.txt";
    	String[] lines = null;
//        try  {  
//            lines = rf.readLines(filename);  
//          
////            for (String line : lines)               {  
////                System.out.println(line);  
////            }  
//        }  
        try  {  
            lines = rf.readLines(filename);     
            strformap = new String [lines.length];
            for (int i = 0; i < lines.length; i++)               {  
            	String[] partone = lines[i].split(", ");
            	
//                System.out.println(partone[0]);  
                strformap[i] = partone[1];
//                System.out.println(strformap[i]);  

            }  
        }  

        catch(IOException e)          {  
            // Print out the exception that occurred  
            System.out.println("Unable to find "+filename+": "+e.getMessage());                
        }  
        return lines;
	}
	
		
	public JComboBox addnumusers(){
//		nmvms;numberofvms
		ComboListennumusers comboListen = new ComboListennumusers();
		String[] number = new String[] { "1", "2", "3", "4"};
//		String[] number = new String[] { "1", "2", "3", "4", "5", "6+"};
		numberofusers = new JComboBox(number);
		numberofusers.setPreferredSize(new Dimension(160,20));
		numberofusers.setSelectedIndex(-1);
		numberofusers.addActionListener(comboListen);
	    return numberofusers;
	}
	public JPanel addallusers(int num){
		JPanel addthese = new JPanel();
		addthese.setLayout(new BoxLayout(addthese,BoxLayout.Y_AXIS));

		userdropdown = new JComboBox[num];
		for (int i = 0; i < num; i++){
			JPanel newpanel = new JPanel();
//			formatPanel(newpanel,panelwidth,panelheight);
//			newpanel.add(newlabel);
//			addingvms[i]=selectvms();
						
			JPanel panelleft = new JPanel();
			formatPanel(panelleft,370,25);
			JPanel panelright = new JPanel();
			formatPanel(panelright,180,25);
			
			JLabel newlabel = new JLabel("Pick a user: ");
			panelleft.setLayout(new FlowLayout(FlowLayout.LEFT));
			panelleft.add(newlabel);
			panelright.setLayout(new FlowLayout(FlowLayout.LEFT));
			panelright.add(getusers(i));
			newpanel.add(panelleft);
			newpanel.add(panelright);		
			addthese.add(newpanel);
		}	
		return addthese;
	}
	
	public JComboBox getusers(int number){
		
		ArrayList<String> vmnames1 = AddUser.returnusers();
//		String[] vmnames;
//		System.out.println("size of vmnames is " + vmnames1.size()); 

		String[] allusernames = vmnames1.toArray(new String[vmnames1.size()]);				

		NameListener namelisten = new NameListener();

		userdropdown[number] = new JComboBox(allusernames);
		userdropdown[number].setPreferredSize(new Dimension(160,20));
		userdropdown[number].setSelectedIndex(-1);
		userdropdown[number].addActionListener(namelisten);
	    return userdropdown[number];
	}

//	private JComboBox addpanelusers(){
//		
//	}
	public void formatPanel(JPanel paneltochange, int a, int b){
//		paneltochange.setBorder(BorderFactory.createLineBorder(Color.black));
		Dimension expectedDimension = new Dimension(a, b);
		paneltochange.setPreferredSize(expectedDimension);
		paneltochange.setMaximumSize(expectedDimension);
		paneltochange.setMinimumSize(expectedDimension);
		}

	public JComboBox addpublicprivate(){

		ListenPubPriv comboListen = new ListenPubPriv();
		String[] pubpriv = new String[] { "Public cloud", "Private cloud"};
		choosePubPriv = new JComboBox(pubpriv);
		choosePubPriv.setPreferredSize(new Dimension(160,20));
		choosePubPriv.setSelectedIndex(-1);
		choosePubPriv.addActionListener(comboListen);
	    return choosePubPriv;
	}
	public void emptyFrame (){
		frame.getContentPane().removeAll();
		resetpanel();
	}
	public void emptyState(){
		for (int i = 0; i < statehere.length; i++){
			statehere[i]=false;
		}
	}
	public int importance (){
		return priority;
	}
	public int getintpriority (){
		return userintpriority;
	}
	public int getinstance (){
		return instance;
	}
	public int getintpubpriv (){
		return intpubpriv;
	}
	public void nextStage(int number){
		panelupdatebase = new JPanel();
		usertrack = new int[number];
		panelupdate.removeAll();
		
		panelupdatebase = addallusers(number);
						
		panelupdate.add(panelupdatebase);
		panelupdate.revalidate();
		panelupdate.repaint();
	}//moredrv

	
	public boolean norepeat(){
		boolean namerepeat = false;		
		if (usertrack==null) return false;
		for (int i = 0; i < usertrack.length; i++){
//			System.out.println("Pos in check1 is " + vms[i]); 
			for (int j = 0; j < usertrack.length; j++){
//				System.out.println("Pos in check2 is " + vms[j]); 
				if (i == j){
					continue;
				}
				if (usertrack[i]==(usertrack[j])){
//					System.out.println("We have a match"); 
    				JOptionPane.showMessageDialog(frame,"Repeating information","Empty values",JOptionPane.WARNING_MESSAGE);			
					namerepeat = true;
				}
			}				
		}
		return namerepeat;
	}
	public boolean notnull(){
		boolean vmsnotnull = false;
//		if (vms==null){
//			vmsnotnull=true;
////			System.out.println("its null!"); 
//		}
		return vmsnotnull;
	}
	public boolean checkpubpriv(){
		boolean pubprivempty = false;
//		System.out.println("Val is " + intpubpriv); 
		if (intpubpriv==0){						//we don't want a default value for pub or private cloud        			
			pubprivempty=true;
			JOptionPane.showMessageDialog(frame,"Empty values","Empty values",JOptionPane.WARNING_MESSAGE);			
		}
		return pubprivempty;
	}
	public boolean notempty(){
		boolean vmnotempty = false;
		if (usertrack==null) return false;
		
		for (int i = 0; i < usertrack.length; i++){
			if (usertrack[i]==0){
				vmnotempty=true;
				JOptionPane.showMessageDialog(frame,"Empty values","Empty values",JOptionPane.WARNING_MESSAGE);			

			}
		}
		return vmnotempty;
	}	
	
private class NameListener implements ActionListener {		
    public void actionPerformed(ActionEvent event){
//	    	if (event.getSource()==numberofvms){//    	if (event.getSource()==numberofvms){
    	int place;
        for (int x=0;x<userdropdown.length;x++){
	        if (event.getSource()==userdropdown[x]){
	        	System.out.println("Number of selection is " + (userdropdown[x].getSelectedIndex()+1));
	        	place = (userdropdown[x].getSelectedIndex()+1);
	        	usertrack[x] = place;
	            }
	        }
    }
}

private class NextBackHandler implements ActionListener {		
    public void actionPerformed(ActionEvent event){
        	if (event.getSource()==buttn[0]){				//ie if the button is 'next'        		
        		
        		String input = vmachinename.getText();

        		if (input.isEmpty()){
//        			System.out.println("its emptaaay");
        			ProgramOne.emptyinfo();				//calls the info frame, which tell user field is empty
        		}
        		else{
	//        		System.out.println(input);
        			
        			if (norepeat()==false && notempty()==false && checkpubpriv()==false){
        			
//		        			if (){						//we don't want a default value for pub or private cloud     
		        				
			        			if (VM.checknames(input)==false){
			//        				System.out.println("back up is " + statehere[0]); 
			//		        		VM one = new VM(input, statehere[0],statehere[1],statehere[2],statehere[3],statehere[4],statehere[5],statehere[6],statehere[7],avlbpr,intgpr, cmplpr, cnfdnt, getintpubpriv());                
		//	        				System.out.println("pubpriv is " + getintpubpriv()); //avlbpr intgpr cmplpr
					        		VM one = new VM(input, questionanswersvm, getintpubpriv(), strformap);                
						        	
//					        		frame.setVisible(false);
									JOptionPane.showMessageDialog(frame,input + " has been added as a cloud instance","Message",JOptionPane.PLAIN_MESSAGE);			
					        		ProgramOne.changeframe();
			
			        			}
			        			else{
			        				ProgramOne.namerepeat();
			        			}
//		        			}
//	        			else{
////	        				JOptionPane.showMessageDialog(frame,"You have left some values empty","Empty values",JOptionPane.WARNING_MESSAGE);			
//	        			}
        			}
        		}
        	}
        	else {
        		ProgramOne.changeframe();
        	}
    }}

private class ListenPubPriv implements ActionListener {				//listener for choosing instance type
    public void actionPerformed(ActionEvent event){
//    	String choice  = (String) choosePubPriv.getSelectedItem();
    	intpubpriv = (choosePubPriv.getSelectedIndex()+1);
//    	System.out.println("size of the thing is: " + bookList.getSelectedIndex());
//		System.out.println("You selected the cloud type : " + choice + " : " + intpubpriv);
		}
    }

private class ComboListennumusers implements ActionListener {		
    public void actionPerformed(ActionEvent event){
//    	if (event.getSource()==numberofvms){
    	usercount = (int) numberofusers.getSelectedIndex()+1;
//    	System.out.println("number of vms to enter is " + nmvms); 
    	nextStage(usercount);
//    	}    	
    	
//    	System.out.println("chosen the property " + userintpriority); 
//    	System.out.println("size of the thing is: " + bookList.getSelectedIndex());
//		System.out.println("You selected the following book: " + userpriority);
		}
    }//ComboListendrvnum


private class Listenervm implements ActionListener {
    public void actionPerformed(ActionEvent event){
        for (int x=0;x<intymax;x++){
//        	System.out.println("Im now true");
        if (event.getSource()==button[x]){
            if(questionanswersvm[x]==false){
            	questionanswersvm[x]=true;
//            	System.out.println(x + " is now true");
            }
            else {
            	questionanswersvm[x]=false;
//            	System.out.println(x + " is now false");
            }
        }
    }}
}
}
