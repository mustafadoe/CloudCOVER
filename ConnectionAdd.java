import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
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


public class ConnectionAdd {
	private JFrame frame;

	private JPanel panel0;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panelvms1;
	private JPanel panelvms2;
	private JPanel panelenc;
	private JPanel paneltwoway;
	private JPanel panelcommands;
	
	private JPanel panelok;
	
	private String commsinput;
	
	private int vm1pos;
	private int vm2pos;
	private int encryptchoice;
	
	private JPanel boxes;			// for the choices about the vms being added

	private JComboBox commsnames1;	// for all the names of instances
	private JComboBox commsnames2;	// for all the names of instances
	private JComboBox encryption;
	private JComboBox twowaycon;
	private JComboBox[] commandbox;
	
	private boolean[] checkdefaults;
	private boolean send;
	private boolean create;
	private boolean destroy;
	private boolean forward;
	
	private JButton buttn[];
	
	private int rpwidth;
	private int panelwidth;
	private int panelheight;
	private int leftwidth;
	private int dropdownwidth;
	
	private String vm1name;
	private String vm2name;
	private String[] encoptions;
	private String encryptionoption;
	private boolean twowaytrue;
	
    private JTextField commsname;
	
	public JPanel newcomms(){
		resetpanel();
		return panel0;
	}
	public void invisible(){
		frame.setVisible(false);
	}
	
	public ConnectionAdd(){
//		frame = new JFrame();
//		reset();
	}		
	public JPanel resetpanel()	{
		
		JPanel panelx = new JPanel();
		panelwidth = 590;
		panelheight = 30;
		rpwidth = 150;
		leftwidth = 390;
		dropdownwidth = 120;
		checkdefaults = new boolean[6];
		
		send = false;
		create = false;
		destroy = false;
		forward = false;
		
//		frame = new JFrame();
		panel0 = new JPanel();
		panel1 = new JPanel();
		formatpanel(panel1,500,60);
		panel2 = new JPanel();
		formatpanel(panel2,500,50);
		panelvms1 = new JPanel();
//		formatpanel(panelvms1,500,50);
		panelvms2 = new JPanel();
//		formatpanel(panelvms2,500,50);
		panelenc = new JPanel();
//		formatpanel(panelenc,500,50);
		paneltwoway = new JPanel();
//		formatpanel(paneltwoway,500,50);
		panelcommands = new JPanel();
		formatpanel(panelcommands,panelwidth,120);
		

		
		panel0.setLayout(new BoxLayout(panel0,BoxLayout.Y_AXIS));

		JLabel label1 = new JLabel("Please add information about the connection");		
		panel1.add(label1);

		JLabel labelentername = new JLabel("Enter a name for this connection:");		
		panel2.add(labelentername);
		commsname = new JTextField(10);
		panel2.add(commsname);
		
		JLabel labelvm1 = new JLabel("1st instance is:");
		formatpanel(panelvms1,panelwidth,panelheight);
		JPanel panelleft = new JPanel();
		formatpanel(panelleft,leftwidth,25);
		JPanel panelright = new JPanel();
		formatpanel(panelright,rpwidth,25);
		panelleft.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelleft.add(labelvm1);
		panelright.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelright.add(addcomms1());
		panelvms1.add(panelleft);
		panelvms1.add(panelright);		
		
		JLabel labelvm2 = new JLabel("2nd instance");
		formatpanel(panelvms2,panelwidth,panelheight);
		JPanel panelleft2 = new JPanel();
		formatpanel(panelleft2,leftwidth,25);
		JPanel panelright2 = new JPanel();
		formatpanel(panelright2,rpwidth,25);
		panelleft2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelleft2.add(labelvm2);
		panelright2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelright2.add(addcomms2());
		panelvms2.add(panelleft2);
		panelvms2.add(panelright2);	
		
		JLabel labelenc = new JLabel("Connection is encrypted using:");		
		formatpanel(panelenc,panelwidth,panelheight);
		JPanel panelleft3 = new JPanel();
		formatpanel(panelleft3,270,25);
		JPanel panelright3 = new JPanel();
		formatpanel(panelright3,270,25);
		panelleft3.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelleft3.add(labelenc);
		panelright3.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelright3.add(addenc());
		panelenc.add(panelleft3);
		panelenc.add(panelright3);	
		
//		JLabel labeltwoway = new JLabel("Commands/data sent both ways?");
//		formatpanel(paneltwoway,panelwidth,panelheight);
//		JPanel panelleft4 = new JPanel();
//		formatpanel(panelleft4,leftwidth,25);
//		JPanel panelright4 = new JPanel();
//		formatpanel(panelright4,rpwidth,25);
//		panelleft4.setLayout(new FlowLayout(FlowLayout.LEFT));
//		panelleft4.add(labeltwoway);
//		panelright4.setLayout(new FlowLayout(FlowLayout.LEFT));
//		panelright4.add(addtwoway());
//		paneltwoway.add(panelleft4);
//		paneltwoway.add(panelright4);	
		checkdefaults[5]=true;
 
		panel0.add(panel1);
		panel0.add(panel2);
		panel0.add(panelvms1);
		panel0.add(panelvms2);
		panel0.add(paneltwoway);		
		panel0.add(addcommands(panelcommands));
		panel0.add(panelenc);
		
		JPanel addok = resetok();
		panelx.add(panel0);
		panelx.add(addok, BorderLayout.SOUTH);
		return panelx;
//		System.out.println("TF1: send: " + send + " create: " + create + " destroy: " + destroy); 

//		frame.add(panel0);
//		frame.add(addok, BorderLayout.SOUTH);
//		
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(700,500);
//		frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
	}	
	public String namecomms(){
		return commsname.getText();
	}
	public void formatpanel(JPanel paneltochange, int a, int b){
//		paneltochange.setBorder(BorderFactory.createLineBorder(Color.black));
		Dimension expectedDimension = new Dimension(a, b);
		paneltochange.setPreferredSize(expectedDimension);
		paneltochange.setMaximumSize(expectedDimension);
		paneltochange.setMinimumSize(expectedDimension);
	}   

	public JPanel resetok(){
		panelok = new JPanel();
		formatpanel(panelok,500,50);

		OKHandler okbutton = new OKHandler();
		panelok.setLayout(new FlowLayout(FlowLayout.CENTER));

		buttn = new JButton[2];
		buttn[0] = new JButton("OK");
		buttn[0].addActionListener(okbutton);
		panelok.add(buttn[0]);
		buttn[1] = new JButton("Cancel");
		buttn[1].addActionListener(okbutton);
		panelok.add(buttn[1]);
		
//		buttn = new JButton("OK");
//		buttn.addActionListener(okbutton);
//		panelok.add(buttn);
//		frame.add(panelok, BorderLayout.SOUTH);
		return panelok;
	}
	public boolean nodefaults(){
		boolean defaultzero = false;
		
		for(int i = 0; i < checkdefaults.length; i++){
//			System.out.println(checkdefaults[i]);
			if(checkdefaults[i]==false){
				defaultzero = true;
			}
		}		
		if (defaultzero ==true){
			JOptionPane.showMessageDialog(frame,"Empty values","Empty values",JOptionPane.WARNING_MESSAGE);			
		}
		return defaultzero;
	}
	public JComboBox addcomms1(){
//		for (int x = 0; x < VM.allvms.size(); x++){
//			
//			System.out.println(VM.allvms.); 
//		}
		ArrayList<String> vmnames1 = VM.returnvmnames();
//		String[] vmnames;
//		System.out.println("size of vmnames is " + vmnames1.size()); 
		String[] vmnames = vmnames1.toArray(new String[vmnames1.size()]);				

//		for (int i = 0; i < vmnames1.size(); i++){
//			System.out.println(vmnames1.get(i)); 
//		}

//		String[] vmnames = new String[VM.allvms.size()];
//		String[] vmnames = new String[] { "VM1", "VM2", "VM3", "VM4"};

		CommsListener1 commslisten = new CommsListener1();

		commsnames1 = new JComboBox(vmnames);
		commsnames1.setSelectedIndex(-1);
		commsnames1.setPreferredSize(new Dimension(dropdownwidth,20));
		commsnames1.addActionListener(commslisten);
	    return commsnames1;
	}
	public JComboBox addcomms2(){
		
		ArrayList<String> vmnames1 = VM.returnvmnames();
//		String[] vmnames;
//		System.out.println("size of vmnames is " + vmnames1.size()); 

		String[] vmnames = vmnames1.toArray(new String[vmnames1.size()]);				

//		String[] vmnames = new String[VM.allvms.size()];
//		String[] vmnames = new String[] { "VM1", "VM2", "VM3", "VM4"};

		CommsListener2 commslisten = new CommsListener2();

		commsnames2 = new JComboBox(vmnames);
		commsnames2.setPreferredSize(new Dimension(dropdownwidth,20));
		commsnames2.setSelectedIndex(-1);
		commsnames2.addActionListener(commslisten);
	    return commsnames2;
	}
	public JComboBox addenc(){
//		String[] vmnames = new String[VM.allvms.size()];
//		String encoptions[] = {"No encryption","Encrypted with SSH","Encrypted using key manager"};
		String encoptions[] = {"No encryption","Single secure channel eg SSH","Multiple channels with key manager"};

		CommsListenerEnc enclisten = new CommsListenerEnc();

		encryption = new JComboBox(encoptions);
		encryption.setPreferredSize(new Dimension(260,20));
		encryption.setSelectedIndex(-1);
		encryption.addActionListener(enclisten);
	    return encryption;
	}

	public JComboBox addtwoway(){
		String twowayoptions[] = {"Yes","No"};
		
		TwoWayListener twowaylisten = new TwoWayListener();
		twowaycon = new JComboBox(twowayoptions);
		twowaycon.setPreferredSize(new Dimension(dropdownwidth,20));
		twowaycon.setSelectedIndex(-1);
		twowaycon.addActionListener(twowaylisten);
		return twowaycon;
	}
	public JPanel addcommands(JPanel commandspanel){
		commandspanel.setLayout(new BoxLayout(commandspanel,BoxLayout.Y_AXIS));		
		String[] labels = {"Connection sends data to other end","Connection commands data creation at other end","Connection commands data destruction at other end", "Connection commands data forwarding at other end"};
		String[] truefalse = {"True", "False"};				
		commandbox = new JComboBox[labels.length];
//		commandbox = new JComboBox[3];
		CommandListener commandlisten = new CommandListener();
		for (int i = 0; i < commandbox.length; i++){
//			System.out.println(i); 
			JPanel panelfull = new JPanel();
			formatpanel(panelfull,panelwidth,panelheight);
			JPanel panelleft = new JPanel();
			formatpanel(panelleft,leftwidth,25);
			JPanel panelright = new JPanel();
//			formatpanel(panelright,rpwidth,25);
			formatpanel(panelright,rpwidth,25);
			JLabel looplabels = new JLabel(labels[i]);
//			System.out.println(labels[i]); 
//			JLabel labelvmnum = new JLabel("How many instances will this data be on?");
			panelleft.setLayout(new FlowLayout(FlowLayout.LEFT));
			panelleft.add(looplabels);
			commandbox[i] = new JComboBox(truefalse);
			commandbox[i].setPreferredSize(new Dimension(dropdownwidth,20));
			commandbox[i].setSelectedIndex(-1);
			commandbox[i].addActionListener(commandlisten);

			panelright.setLayout(new FlowLayout(FlowLayout.LEFT));
			panelright.add(commandbox[i]);
			panelfull.add(panelleft);
			panelfull.add(panelright);		
			
			commandspanel.add(panelfull);
		}
	    return commandspanel;
	}
	
	public boolean namecheckone(Connexion check, String vm1n, String vm2n){
		boolean namepresent = false;

		System.out.println("NEW: " + vm1n + " " + vm2n);
		System.out.println("OLD: " + check.vm1.name + " " + check.vm2.name);
		if (vm1n == check.vm1.name && vm2n == check.vm2.name){
//	    						System.out.println("The name of the first name is the same");
//	    						System.out.println(check.vm1.name);
			JOptionPane.showMessageDialog(frame,"A connection has already been added here","Message",JOptionPane.WARNING_MESSAGE);			
			namepresent=true;
		}
		return namepresent;
	}

	public boolean namechecktwo(Connexion check, String vm1n, String vm2n){
		boolean namepresent = false;
//		System.out.println(check.vm1.name + " " + check.vm2.name);
		if (vm1n == check.vm1.name && vm2n == check.vm2.name){
			namepresent = true;
		}
		if (vm2n == check.vm1.name || vm1n == check.vm2.name){
			namepresent = true;
		}
		if (namepresent == true){
			JOptionPane.showMessageDialog(frame,"A connection has already been added here","Message",JOptionPane.WARNING_MESSAGE);			
		}		
		return namepresent;
	}

	public boolean namefilled(){
		boolean notfilled = false;
		System.out.println("name is " +commsinput); 
		if (commsinput.isEmpty()){
			notfilled = true;
			JOptionPane.showMessageDialog(frame,"Name is empty","Message",JOptionPane.WARNING_MESSAGE);			
		}
		return notfilled;
	}
	public boolean samenames(){
		boolean equalnames = false;
		if (vm1name==vm2name){
			equalnames=true;
			JOptionPane.showMessageDialog(frame,"You cannot add an instance connecting to itself","Message",JOptionPane.WARNING_MESSAGE);			
		}
		return equalnames;
	}

	private class OKHandler implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
//			int vm1pos = -1, vm2pos =-1;
	    	if (event.getSource()==buttn[0]){				//ie if the button is 'next'        		
	        		
	        		commsinput = namecomms();	        		
  		
	        		if (	!commsinput.isEmpty()
	        			&&	!nodefaults()
	        			&&  !samenames())
	        		{
			        			
			    				boolean alreadythere = false;
			    				Connexion check;
			    				ArrayList<Connexion> connex = Connexion.returncomms();
		    					System.out.println("CONZZ::");
			    				for (int i = 0; i < connex.size();i++){			//in this loop we check to make sure the selected vms haven't already been added
			    					check = connex.get(i);
			    					System.out.println(check.vm1.name + " & " + check.vm2.name);
			    				}			    				
		    					System.out.println("Con size = " + connex.size());

			    				for (int i = 0; i < connex.size();i++){			//in this loop we check to make sure the selected vms haven't already been added
			    					check = connex.get(i);
			    					if (twowaytrue ==true){
			    						if (namechecktwo(check, vm1name, vm2name)) {
			    							alreadythere = true;
			    							break;
			    						}
			    					}
			    					else{
			    						if (namecheckone(check, vm1name, vm2name)){
			    							alreadythere = true;
			    							break;
			    						}
			    					}
			    				}
			        			if (Connexion.checkname(commsinput)==false){
				    				if (alreadythere==false){
				    					if (twowaytrue==false){
//											try {
//												System.out.println("TF2: send: " + send + " create: " + create + " destroy: " + destroy); 
	
												Connexion addNew = new Connexion(VM.allvms.get(vm1pos), VM.allvms.get(vm2pos), encryptchoice, commsinput, send, create, destroy, forward);					
						        				JOptionPane.showMessageDialog(frame,"A connection between " + VM.allvms.get(vm1pos).name + " and " + VM.allvms.get(vm2pos).name + " has been added", "Added connection", JOptionPane.PLAIN_MESSAGE);														
						        				ProgramOne.changeframe();					
						        				
				    					}
				    					else {
//											try {
												Connexion addNew1 = new Connexion(VM.allvms.get(vm1pos), VM.allvms.get(vm2pos), encryptchoice, commsinput, send, create, destroy, forward);
												Connexion addNew2 = new Connexion(VM.allvms.get(vm2pos), VM.allvms.get(vm1pos), encryptchoice, commsinput, send, create, destroy, forward);
						        				JOptionPane.showMessageDialog(frame,"Connections between " + VM.allvms.get(vm1pos).name + " and " + VM.allvms.get(vm2pos).name + " have been added both ways", "Added connection", JOptionPane.PLAIN_MESSAGE);			
						        				ProgramOne.changeframe();					
				    					}
				    				}
			        			}
			        			else{
			        				JOptionPane.showMessageDialog(frame,"You have already entered this name for a connection","Repeated Name",JOptionPane.WARNING_MESSAGE);			
			        			}
	        		}
	    		}
	    	else{
				ProgramOne.changeframe();					
	    	}
	    	}
	}

	private class CommandListener implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
//	    	if (event.getSource()==numberofvms){//    	if (event.getSource()==numberofvms){
	    	int place;
	        for (int x=0;x<commandbox.length;x++){
		        if (event.getSource()==commandbox[x]){
		        	if (x==0){
		        		checkdefaults[0]=true;
		        		if ((commandbox[x].getSelectedIndex()+1)==1){send = true;}
		        		else send = false;
		        	}
		        	else if (x==1){
		        		checkdefaults[1]=true;
		        		if ((commandbox[x].getSelectedIndex()+1)==1)create = true;
		        		else create = false;
//		        		System.out.println("create"); 
		        	}
		        	else if (x==2){
		        		checkdefaults[2]=true;
		        		if ((commandbox[x].getSelectedIndex()+1)==1)destroy = true;
		        		else destroy = false;
//		        		System.out.println("destroy is " + destroy); 
		        	}
		        	else {
		        		checkdefaults[3]=true;
		        		if ((commandbox[x].getSelectedIndex()+1)==1)forward = true;
		        		else forward = false;		        		
//		        		System.out.println("Forward is " + forward); 
		        	}
//		        	System.out.println("number of vm is " + (commandbox[x].getSelectedIndex()+1));
//		        	place = (commandbox[x].getSelectedIndex()+1);
//		        	vms[x] = place;
//		        	vms[x]=1;
//		        	System.out.println("hi"+x); 
		            }
		        }
	    }
	}

	private class CommsListener1 implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
//	    System.out.println((String) commsnames1.getSelectedItem());
	    vm1name = (String) commsnames1.getSelectedItem();
	    vm1pos = (Integer) commsnames1.getSelectedIndex();
//	    System.out.println("vm1pos is " + vm1pos); 

//	    	userintpriority = (chooseProperties.getSelectedIndex()+1);
//	    	System.out.println("size of the thing is: " + bookList.getSelectedIndex());
//			System.out.println("You selected the following book: " + userpriority);
			}
	    }
	private class CommsListener2 implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
//	    System.out.println((String) commsnames2.getSelectedItem());
	    vm2name = (String) commsnames2.getSelectedItem();
	    vm2pos = (Integer) commsnames2.getSelectedIndex();
//	    System.out.println("vm2pos is " + vm2pos); 
//	    	userintpriority = (chooseProperties.getSelectedIndex()+1);
//	    	System.out.println("size of the thing is: " + bookList.getSelectedIndex());
//			System.out.println("You selected the following book: " + userpriority);
			}
	    }
	private class CommsListenerEnc implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
    		checkdefaults[4]=true;
//    		System.out.println("hello " + (String) encryption.getSelectedItem());
    		encryptionoption = (String) encryption.getSelectedItem();
    		encryptchoice = (Integer) encryption.getSelectedIndex();
//	    	userintpriority = (chooseProperties.getSelectedIndex()+1);
//	    	System.out.println("size of the thing is: " + bookList.getSelectedIndex());
//			System.out.println("You selected the following book: " + userpriority);
			}
	    }
	private class TwoWayListener implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
    		checkdefaults[5]=true;
//	    	System.out.println("in this place"); 
//	    	System.out.println("two way" + (String) twowaycon.getSelectedItem());
	    	if (twowaycon.getSelectedIndex()==0){
	    		twowaytrue = true;
	    	}
	    	else {
	    		twowaytrue = false;
	    	}
	    	
//	    encryptionoption = (String) encryption.getSelectedItem();
//	    encryptchoice = (Integer) encryption.getSelectedIndex();
//	    	userintpriority = (chooseProperties.getSelectedIndex()+1);
//	    	System.out.println("size of the thing is: " + bookList.getSelectedIndex());
//			System.out.println("You	    	selected the following book: " + userpriority);
			}
	    }

	
}
