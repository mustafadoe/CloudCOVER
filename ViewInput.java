import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;

/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */



public class ViewInput {
	private JFrame frame;
	private JPanel panel0;
	private JButton okbutton;
		
	private JPanel paneltop;
	private JPanel panelok;
	private JPanel panelkey;
	private JPanel resetinput;
	private JPanel newvm;
	private JPanel newcon;
	private ArrayList<VM> vms; 
	private ArrayList<Connexion> cons; 
	private ArrayList<Data> datalist;
//	private JPanel threatpanel1;
	private int vmheight;
	private int vmlength;
	private int vmnamelength;
	private int headerheight;
	private JButton remove[];
	private JButton removecon[];
	private JButton removedata[];
	private JButton resetbutton;

	private Color[] customcolours;
	
	public ViewInput(){
		reset();
	}
	public void reset(){
		System.out.println("*****************************"); 
		VM.printinfo();
		System.out.println("*****************************"); 

		frame = new JFrame();
		panel0 = new JPanel();
		panel0.setLayout(new BoxLayout(panel0,BoxLayout.Y_AXIS));
		vmlength = 30;
		vmheight = 30;
		vmnamelength = 100;
		headerheight = 100;
		
		paneltop = new JPanel();
		formatpanel(paneltop,450,50,false);
		JLabel paneltoplabel = new JLabel ("The current deployment is listed below");
		Font font = new Font("Sans Serif", Font.BOLD, 16);
		paneltoplabel.setFont(font);
		paneltop.add(paneltoplabel);
		panel0.add(paneltop);

		customcolours = new Color[5];
		customcolours[0] = new Color(255,0,0);
		customcolours[1] = new Color(255,128,0);
		customcolours[2] = new Color(255,255,0);
		customcolours[3] = new Color(0,255,0);
		customcolours[4] = new Color(0,255,255);
			
		vms = VM.returnallvms();

		if (vms.size()>0){			//we don't need to add anything if there are no vms added

			vmheaders();			
			remove = new JButton[vms.size()];
			
			for (int i = 0; i < vms.size(); i++){
				addvm(i);
			}
		}
/*
 * conheaders and below adds the connections		
 */
		
		cons = Connexion.returncomms();
		if (cons.size()>0){			//we don't need to add anything if there are no connections added
			conheaders();
			removecon = new JButton[cons.size()];

			for (int i = 0; i < cons.size(); i++){
				addcomm(i);
			}
		}
		
		datalist = Data.getdataall();
		if (datalist.size()>0){			//we don't need to add anything if there are no connections added
			dataheaders();
			removedata = new JButton[datalist.size()];

			for (int i = 0; i < datalist.size(); i++){
				adddata(i);
			}
		}
		
		addresetall();
		
		addkey();
		
		frame.add(panel0);
		JPanel addok = resetok();
		frame.add(addok, BorderLayout.SOUTH);

		JScrollPane pane = new JScrollPane(panel0);  // panel0 is the panel that will be scrolled
		frame.getContentPane().add(pane); 			// frame adds the scrollpane pane
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);		
	}
	public void addresetall(){
		JPanel blankpanel = new JPanel();
		formatpanel(blankpanel,vmnamelength,vmheight,false);

		resetinput = new JPanel();
		formatpanel(resetinput,vmnamelength,vmheight,false);
		
		ResetHandler resethandle = new ResetHandler();
		resetbutton = new JButton("Reset all");
		resetbutton.addActionListener(resethandle);
		resetinput.add(resetbutton);
		
		panel0.add(blankpanel);
		panel0.add(resetinput);
		
	}
	
	public static void printall(){
		ArrayList<VM> vmlist = VM.allvms;
		System.out.println(); 
		for (int i = 0; i < vmlist.size(); i++){ 
			VM vmtoprint = vmlist.get(i);
			System.out.print("VM::::::" + vmtoprint.name);
			ArrayList<String> vmusers = vmtoprint.getallusers();
			for (int j = 0; j < vmusers.size(); j++){ 
				System.out.print(" ::User:: " + vmusers.get(j)); 
			}
			System.out.println(); 
			System.out.println("DATA::::"); 			
			ArrayList<Data> instancedata = vmtoprint.getvmdata();
			for (int j = 0; j < instancedata.size(); j++){
				 System.out.println(" ::::::: " + instancedata.get(j).getname()); 
			}
			ArrayList<VM> consto = vmtoprint.vmsout;
			if (consto.size()>0)  System.out.println("CONNECTTO::"); 			
			for (int j = 0; j < consto.size(); j++){
				 System.out.println(" ------> " + consto.get(j).name); 
			}
			System.out.println("**********************"); 
		}
		System.out.println(); 
		ArrayList<Data> datalist = Data.getdataall();
//		for (int i = 0; i < datalist.size(); i++){ 
//			Data datatoprint = datalist.get(i);
//			System.out.print(datatoprint.getname());
//			ArrayList<String> datausers = datatoprint.getallusers();
//			for (int j = 0; j < datausers.size(); j++){ 
//				System.out.println(" ::DtUser:: " + datausers.get(j)); 
//			}			
//			 System.out.println(); 
//		}
		System.out.println("::::::::::::::::::"); 

	}
	
	public void addkey(){

		JPanel blankpanel = new JPanel();
		formatpanel(blankpanel,vmnamelength,vmheight,false);
		
		JPanel keyname = new JPanel();
		panelkey = new JPanel();
		
		Font font0 = new Font ("Arial", Font.BOLD, 16);
		JLabel keylbl = new JLabel("Key");
		keylbl.setFont(font0);
		keyname.add(keylbl);
		
		JPanel prtykey = new JPanel();
		JPanel trflskey = new JPanel();
		prtykey.setLayout(new BoxLayout(prtykey,BoxLayout.Y_AXIS));
		trflskey.setLayout(new BoxLayout(trflskey,BoxLayout.Y_AXIS));
		String[] prioritylbls = { "Highest importance", "Important","Average" ,"Low", "Not Important"};
		
		for (int i = 0; i < customcolours.length; i ++){
			JPanel colorpriority = new JPanel();
			colorpriority.setLayout(new BoxLayout(colorpriority,BoxLayout.X_AXIS));
			JPanel clrlft = new JPanel();
			JPanel clrrgt = new JPanel();
			formatpanel(clrlft,vmlength, vmheight, true);
			clrlft.setBackground(customcolours[i]);
			JLabel prtylabel = new JLabel(prioritylbls[i]);
			clrrgt.add(prtylabel);
			clrrgt.setLayout(new FlowLayout(FlowLayout.LEFT));
			colorpriority.add(clrlft);
			colorpriority.add(clrrgt);
			prtykey.add(colorpriority);
		}		
		for (int i = 0; i < 2; i ++){
			JPanel pnltrfls = new JPanel();
			pnltrfls.setLayout(new BoxLayout(pnltrfls,BoxLayout.X_AXIS));
			JPanel clrlft = new JPanel();
			JPanel clrrgt = new JPanel();
			formatpanel(clrlft,vmlength, vmheight, true);
			if (i==0) clrlft.setBackground(Color.RED);
			else clrlft.setBackground(Color.GREEN);
			JLabel prtylabel = new JLabel();
			if (i==0) prtylabel.setText("False");
			else prtylabel.setText("True");			
			clrrgt.add(prtylabel);
			clrrgt.setLayout(new FlowLayout(FlowLayout.LEFT));
			pnltrfls.add(clrlft);
			pnltrfls.add(clrrgt);
			trflskey.add(pnltrfls);
		}	
		
		panelkey.add(prtykey);
		panelkey.add(trflskey);
		panel0.add(blankpanel);
		panel0.add(keyname);
		panel0.add(panelkey);

	}
	public void vmheaders(){
				
		Font font0 = new Font ("Nimbus Sans L", Font.BOLD, 16);
		Font font = new Font ("Nimbus Sans L", Font.BOLD, 12);
		JPanel vmsbelow = new JPanel();
		formatpanel(vmsbelow,400,vmheight,false);
		JLabel label = new JLabel("The instances are listed below");
		vmsbelow.add(label);
//		<html><br></html>
		String[] alllabels = {"Infrequent backup", "SQL", "MS Access", "Multiple users", "Identity Manager",
				"Key Manager", "API Access", "Javascript"};

		JLabel header0 = new JLabel("Name");
		header0.setFont(font0);

		newvm = new JPanel();
		newvm.setLayout(new BoxLayout(newvm,BoxLayout.X_AXIS));
		
		JPanel category0 = new JPanel();
		formatpanel(category0,vmnamelength, headerheight, true);
		category0.setBackground(Color.WHITE);
		category0.add(header0);
		newvm.add(category0);

		for (int i = 0; i < (alllabels.length+1); i++){
			JPanel categorycurrent = new JPanel();
			
			if(i==(alllabels.length)){
				formatpanel(categorycurrent,(vmlength+36),headerheight, false);				
			}		
			else{
				formatpanel(categorycurrent,vmlength, headerheight, true);	
				JLabel headercurrent = new JLabel(alllabels[i]);
				headercurrent.setUI(new VerticalLabelUI(true));
				headercurrent.setFont(font);			
				categorycurrent.setBackground(Color.WHITE);	
				categorycurrent.add(headercurrent);
			}
			newvm.add(categorycurrent);
		}
//		JPanel category9 = new JPanel();					//padding to fit in the remove button
//		newvm.add(category9);

		panel0.add(vmsbelow);
		panel0.add(newvm);		
}

	public JPanel resetok(){
		
		panelok = new JPanel();
		formatpanel(panelok,500,50,false);

		OKHandler oklisten = new OKHandler();
		panelok.setLayout(new FlowLayout(FlowLayout.CENTER));

		okbutton = new JButton("OK");
		okbutton.addActionListener(oklisten);
		panelok.add(okbutton);
//		frame.add(panelok, BorderLayout.SOUTH);
		return panelok;
	}
	public void visible(){
		frame.setVisible(true);
	}
	
	public void addvmdata(VM vmdataadder){

		ArrayList<Data> instancedata = vmdataadder.getvmdata();
//		System.out.println(vmdataadder.name + " has got " ); 
		String vmname = vmdataadder.name;
		JPanel newadddata = new JPanel();
		newadddata.setLayout(new BoxLayout(newadddata,BoxLayout.X_AXIS));
		int roomleft = 306;	
		int start = 1;
		
		for (int i = 0; i < instancedata.size()+1; i++){
			JPanel panel4data = new JPanel();
			if (i==0 || start==0){
				JLabel onlylabel = new JLabel(vmname + "data--->");
				onlylabel.setForeground(Color.RED);
				panel4data.add(onlylabel);
				panel4data.setBackground(Color.WHITE);
				formatpanel(panel4data,vmnamelength, vmheight, true);
			}
			else{
				Data curdata = instancedata.get(i-1);
//				System.out.println(dataproperty[i-1]); 
				JLabel nlabel = new JLabel(curdata.getname());				
				formatpanel(panel4data,vmnamelength, vmheight, true);
				panel4data.add(nlabel);				
				roomleft -= vmnamelength;
//				if (roomleft<(66+vmnamelength)){
//					start = 0;
//				}
//				else start =1;
			}
			newadddata.add(panel4data);			
		}
		JPanel filler = new JPanel();
		formatpanel(filler, roomleft, vmheight, false);
		newadddata.add(filler);
		
		int buttonwidth = vmlength;
		
		panel0.add(newadddata);		

	}
	
	public void addvm(int pos){
		VM addthis = vms.get(pos);
//		System.out.println(addthis.name); 		
		ImageIcon tick = new ImageIcon("/home/mustafa/tick1.JPG");
				
		newvm = new JPanel();
		newvm.setLayout(new BoxLayout(newvm,BoxLayout.X_AXIS));

		JPanel vmname = new JPanel();
		formatpanel(vmname,vmnamelength, vmheight, true);
		vmname.setBackground(Color.WHITE);
		JLabel vmnamelabel = new JLabel(addthis.name);
		vmname.add(vmnamelabel);
		
		newvm.add(vmname);

		boolean[] vmproperties = new boolean[8];
//		vmproperties[0] = addthis.returnbackedup();
//		vmproperties[1] = addthis.returnsql();
//		vmproperties[2] = addthis.returnmsa();
//		vmproperties[3] = addthis.returnmanyu();
//		vmproperties[4] = addthis.returnidm();
//		vmproperties[5] = addthis.returnkm();
//		vmproperties[6] = addthis.returnapi();
//		vmproperties[7] = addthis.returnjava();
		
		for (int i = 0; i < vmproperties.length; i ++){
			JPanel paneltruefalse = new JPanel();
			formatpanel(paneltruefalse,vmlength, vmheight, true);
//			backedup.setBackground(Color.lightGray);
			if (vmproperties[i]==true) paneltruefalse.setBackground(Color.GREEN);
			else paneltruefalse.setBackground(Color.RED);
			newvm.add(paneltruefalse);
		}
		int buttonwidth = vmlength;
		
		JPanel removevm = new JPanel();
		formatpanel(removevm,(buttonwidth+5),vmheight, false);
		RemoveListener removelisten = new RemoveListener();
		remove[pos] = new JButton("Delete");
//		remove[pos].setPreferredSize(new Dimension((buttonwidth),(vmheight)));
		remove[pos].setBackground(Color.WHITE);
		remove[pos].setFont(new Font("Arial", Font.PLAIN, 10));
		remove[pos].addActionListener(removelisten);
//		removevm.add(remove[pos]);		
		newvm.add(remove[pos]);
//		newvm.add(removevm);

		panel0.add(newvm);		
		if (addthis.getvmdata().size()>0){
//			System.out.println("Size of data on this vm is " + addthis.getvmdata().size()); 
			addvmdata(addthis);
		}

	}
	
	public void dataheaders(){
		//"Not Important", "Low","Average" ,"Important", "Highest importance"
		Font font0 = new Font ("Nimbus Sans L", Font.BOLD, 16);
		Font font = new Font ("Nimbus Sans L", Font.BOLD, 12);
		JPanel blankpanel = new JPanel();
		formatpanel(blankpanel,vmnamelength,vmheight,false);
		JPanel databelow = new JPanel();
		formatpanel(databelow,400,vmheight,false);
		JLabel label = new JLabel("The important data are listed below");
		databelow.add(label);

		String[] dataheaders = {"Name", "Integrity", "Availability", "Confidentiality", "Compliance"};

		JPanel newdata = new JPanel();
		newdata.setLayout(new BoxLayout(newdata,BoxLayout.X_AXIS));
		
		for (int i = 0; i < (dataheaders.length+1); i++){
			JPanel categorycurrent = new JPanel();
			
			if(i==(dataheaders.length)){
				formatpanel(categorycurrent,(vmlength+36),headerheight, false);				
			}		
			else{
				JLabel headercurrent = new JLabel(dataheaders[i]);				
				if (i==0){
					formatpanel(categorycurrent,vmnamelength, headerheight, true);	
					headercurrent.setFont(font0);			
				}
				else {
					formatpanel(categorycurrent,vmlength, headerheight, true);	
					headercurrent.setUI(new VerticalLabelUI(true));
					headercurrent.setFont(font);			
				}
				categorycurrent.setBackground(Color.WHITE);	
				categorycurrent.add(headercurrent);
			}
			newdata.add(categorycurrent);
		}
		panel0.add(blankpanel);
		panel0.add(databelow);
		panel0.add(newdata);		
}
	public void adddata(int pos){
//		String[] dataheaders = {"Name", "Integrity", "Availability", "Confidentiality", "Compliance"};

		Data newdata = Data.getdataall().get(pos);
		JPanel newadddata = new JPanel();
		newadddata.setLayout(new BoxLayout(newadddata,BoxLayout.X_AXIS));

//        Color customColor1 = new Color(255,0,0);
//        Color customColor2 = new Color(255,128,0);
//        Color customColor3 = new Color(255,255,0);
//        Color customColor4 = new Color(0,255,0);
//        Color customColor5 = new Color(0,255,255);
		
		int[] dataproperty = new int[4];
//		dataproperty[0] = newdata.getinteg();
//		dataproperty[1] = newdata.getavail();
//		dataproperty[2] = newdata.getconfi();
//		dataproperty[3] = newdata.getcompl();
		
		for (int i = 0; i < dataproperty.length+1; i++){
//			System.out.println("Num" + i); 
			JPanel panel4data = new JPanel();
			if (i==0){
				JLabel onlylabel = new JLabel(newdata.getname());
				panel4data.setBackground(Color.WHITE);
				panel4data.add(onlylabel);
				formatpanel(panel4data,vmnamelength, vmheight, true);
			}
			else{
//				System.out.println(dataproperty[i-1]); 
				formatpanel(panel4data,vmlength, vmheight, true);
				if (dataproperty[i-1]==5) panel4data.setBackground(customcolours[0]);
				else if (dataproperty[i-1]==4) panel4data.setBackground(customcolours[1]);
				else if (dataproperty[i-1]==3) panel4data.setBackground(customcolours[2]);
				else if (dataproperty[i-1]==2) panel4data.setBackground(customcolours[3]);
				else panel4data.setBackground(customcolours[4]);
			}
			newadddata.add(panel4data);			
		}
		int buttonwidth = vmlength;
		
		RemoveDataListener removelisten = new RemoveDataListener();
		removedata[pos] = new JButton("Delete");
		removedata[pos].setBackground(Color.WHITE);
		removedata[pos].setFont(new Font("Arial", Font.PLAIN, 10));
		removedata[pos].addActionListener(removelisten);
		newadddata.add(removedata[pos]);

		panel0.add(newadddata);		

	}

	public void conheaders(){
		String [] names = {"From", "To", "Encryption", "Send cmd", "Destroy cmd", "Create cmd"};
		
		JPanel blankpanel = new JPanel();
		formatpanel(blankpanel,vmnamelength,vmheight,false);
		JPanel commsbelow = new JPanel();
		formatpanel(commsbelow,400,vmheight,false);
		JLabel label = new JLabel("The connections are below");
		commsbelow.add(label);

		panel0.add(blankpanel);
		panel0.add(commsbelow);
		
		Font font0 = new Font ("Nimbus Sans L", Font.BOLD, 16);
		Font font = new Font ("Nimbus Sans L", Font.BOLD, 12);

		newcon = new JPanel();
		newcon.setLayout(new BoxLayout(newcon,BoxLayout.X_AXIS));

		JPanel vmname = new JPanel();
		formatpanel(vmname,vmnamelength, headerheight, true);
		vmname.setBackground(Color.WHITE);
		JLabel vmnamelabel = new JLabel("Name");
//		vmnamelabel.setForeground(Color.BLUE);
		vmnamelabel.setFont(font0);
		vmname.add(vmnamelabel);
		newcon.add(vmname);
		
		for (int i = 0; i < (names.length+1); i++){
			JPanel add = new JPanel();
			if (i==names.length){
				formatpanel(add,(vmlength+36),headerheight, false);								
			}
			else{
				formatpanel(add,vmlength, headerheight, true);
				add.setBackground(Color.WHITE);
				JLabel addlabel = new JLabel(names[i]);
				addlabel.setUI(new VerticalLabelUI(true));
	//			addlabel.setForeground(Color.BLUE);
				addlabel.setFont(font);
				add.add(addlabel);
			}
			newcon.add(add);
		}				
		panel0.add(newcon);		

	}
	
	public void addcomm(int pos){
		
		Connexion addthis = cons.get(pos);
		String [] names = {addthis.vm1.name, addthis.vm2.name, "INTEGERCONVERT"+Integer.toString(addthis.encryption), Boolean.toString(addthis.getsend()),
				Boolean.toString(addthis.getdestroy()),Boolean.toString(addthis.getcreate())};		
				
		newcon = new JPanel();
		newcon.setLayout(new BoxLayout(newcon,BoxLayout.X_AXIS));

		JPanel vmname = new JPanel();
		formatpanel(vmname,vmnamelength, vmheight, true);
		vmname.setBackground(Color.WHITE);
		JLabel vmnamelabel = new JLabel(addthis.name);
		vmname.add(vmnamelabel);
		newcon.add(vmname);

		for (int i = 0; i < names.length; i++){
			
			JPanel panthis = new JPanel();
			formatpanel(panthis,vmlength, vmheight, true);
			JLabel labelthis;
			if (names[i].equals("INTEGERCONVERT0")){ labelthis = new JLabel ("None"); panthis.add(labelthis);}
			else if (names[i].equals("INTEGERCONVERT1")){ labelthis = new JLabel ("Secure Channel / SSH"); panthis.add(labelthis);}
			else if (names[i].equals("INTEGERCONVERT2")){ labelthis = new JLabel ("Key Manager"); panthis.add(labelthis);}
			else if (names[i].equals("false")) panthis.setBackground(Color.RED);
			else if (names[i].equals("true")) panthis.setBackground(Color.GREEN);
			else {labelthis = new JLabel (names[i]); panthis.add(labelthis);}

			newcon.add(panthis);
		}		
		RemoveComListener removecomlisten = new RemoveComListener();
		removecon[pos] = new JButton("Delete");
//		remove[pos].setPreferredSize(new Dimension((buttonwidth),(vmheight)));
		removecon[pos].setBackground(Color.WHITE);
		removecon[pos].setFont(new Font("Arial", Font.PLAIN, 10));
		removecon[pos].addActionListener(removecomlisten);
		newcon.add(removecon[pos]);
		
		panel0.add(newcon);		
	}

	/*
	 * changes a boolean to a string
	 */
	public String booltostring(boolean check){
		String trueorfalse;
		if (check==true){
			trueorfalse="True";
		}
		else{
			trueorfalse="False";
		}
		return trueorfalse;
	}
	public void formatlabel(JLabel labeltochange){
		Dimension expectedDimension = new Dimension(vmlength, vmheight);
		labeltochange.setPreferredSize(expectedDimension);
		labeltochange.setMaximumSize(expectedDimension);
		labeltochange.setMinimumSize(expectedDimension);

	}
	public void formatpanel(JPanel paneltochange, int a, int b, boolean border){

		if (border == true){	// include the border if border = true
			paneltochange.setBorder(BorderFactory.createLineBorder(Color.black));
		}
		Dimension expectedDimension = new Dimension(a, b);
		paneltochange.setPreferredSize(expectedDimension);
		paneltochange.setMaximumSize(expectedDimension);
		paneltochange.setMinimumSize(expectedDimension);
		
	}    
	public void removecon(String name){
    	ArrayList<Connexion> allcomms = Connexion.returncomms();
		System.out.println("size: " + allcomms.size()); 
    	for(int i = 0; i < allcomms.size(); i++){
    		System.out.println("loop " + i); 
    		if (name == allcomms.get(i).vm1.name || name == allcomms.get(i).vm2.name){
    			System.out.println("name1: " + allcomms.get(i).vm1.name + " name2: " + allcomms.get(i).vm2.name); 
    			System.out.println("need to remove the thing at " + i); 
//    			delete = i;
    			Connexion.removecomms(i);
    		}
    	}

	}
	private class ResetHandler implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
	        	if (event.getSource()==resetbutton){				//ie if the button is 'next'        		
//	        		System.out.println("Reset clicked"); 
	        		Connexion.clearcomms();
	        		VM.allvms.clear();
	        		VM.clearvmnames();
	        		Data.cleardatanames();
	        		Data.cleardataall();

//		    		System.out.println("clicked on framepanel ok button"); 
		    		frame.setVisible(false);
					try {
						ProgramOne.nextStage();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	        	}
	    }
	}
	
	private class OKHandler implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
	        	if (event.getSource()==okbutton){				//ie if the button is 'next'        		
//	        		System.out.println("OK clicked"); 
//		    		System.out.println("clicked on framepanel ok button"); 
		    		frame.setVisible(false);
					try {
						ProgramOne.nextStage();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	        	}
	    }
	}
	private class RemoveListener implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
	    	int x = -1;
	    	for (int i = 0; i < remove.length;i++){
	    		if (event.getSource()==remove[i]){
	    			x = i;
	    		}
	    	}
	    	String name = vms.get(x).name;
	    	VM.removevm(x);
	    	
//	    	int delete = 0;
	    	ArrayList<Connexion> allcomms = Connexion.returncomms();
	    	int sizenow = allcomms.size();
//    		System.out.println("size: " + allcomms.size()); 
	    	for(int i = 0; i < sizenow; i++){
	    		System.out.println("in loop " + i); 
	    		removecon(name);
	    	}

	    	JOptionPane.showMessageDialog(frame,"The instance "+name+", and all its connections, has been removed","Message",JOptionPane.PLAIN_MESSAGE);			

    		frame.setVisible(false);
			try {
				ProgramOne.nextStage();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	private class RemoveComListener implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
	    	int x = -1;
	    	for (int i = 0; i < removecon.length;i++){
	    		if (event.getSource()==removecon[i]){
	    			x = i;
	    		}
	    	}
	    	String name = Connexion.returncomms().get(x).name;
	    	Connexion.removecomms(x);
	    	
			JOptionPane.showMessageDialog(frame,"The connection "+name+" has been removed","Message",JOptionPane.PLAIN_MESSAGE);			

    		frame.setVisible(false);
			try {
				ProgramOne.nextStage();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	private class RemoveDataListener implements ActionListener {		
	    public void actionPerformed(ActionEvent event){
	    	int x = -1;
	    	for (int i = 0; i < removedata.length;i++){
	    		if (event.getSource()==removedata[i]){
	    			x = i;
	    		}
	    	}
	    	String name = Data.getdatanames().get(x);
//	    			Connexion.returncomms().get(x).name;
	    	Data.removedata(x);
	    	
			JOptionPane.showMessageDialog(frame,"The data item "+name+" has been removed","Message",JOptionPane.PLAIN_MESSAGE);			

    		frame.setVisible(false);
			try {
				ProgramOne.nextStage();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

}
