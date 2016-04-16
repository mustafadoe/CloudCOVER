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


public class DataAdd {
	private String[] questions;// = {"SQL", "MS Access"};
	private String[] questionsinfo;// = {"Click if this instance uses an SQL", "Click if this instance uses MS Access"};
	private boolean[] questionsanswers;
//	String filename = "           /home/mustafa/Dropbox/instance.txt";
	private String questionpath = "/home/mustafa/Dropbox/work/saves/data.txt";
	private String[] strformap;
	
	private JFrame frame;
//	private PanButtons mainpanel;
	private JTextField dataname;

    private String input;
    private JCheckBox[] button;
    private JButton[] buttn;
	private JPanel panel0;
	private JPanel panel1;					//

	private JPanel panaddvms;
	private JPanel panelupdate;
	private JPanel panelupdate2;
	private JPanel panadddrv;

	private boolean drvarrexists;
	
	private JComboBox[] addingvms;
	private JComboBox[] addingdrv;
	private JComboBox numberofvms;
	private JComboBox numberofdrvs;
	
	private int[] vms;
	private int[] drv;

	private int xmax;
	private boolean statehere[];
	String vmname;
	private String importance;
	private int priority;
	private int instance;
	private int intpubpriv;
	
	private int nmvms;
	private int nmdrvs;
	
	private int panelwidth;
	private int panelheight;
	
	ArrayList<String> posdrvs;
	private String userpriority;
	private int userintpriority;
	
	public DataAdd(){
//		newframe();
	}
	public JPanel resetpanel(){
		panelheight = 30;
		panelwidth = 590;		
		nmdrvs = 0;
		drvarrexists = false;
		input = null;
		JLabel labelvmnum = new JLabel("How many instances will this data be on?");
		JLabel labelderive = new JLabel("What other data is this derived from?");
		String methodname = "instances"; String methodname2 = "derivations";
		
		JPanel panelx = new JPanel();		
		panelx.setLayout(new BoxLayout(panelx,BoxLayout.Y_AXIS));
		dataname = new JTextField(10);
		panelx.add(PanelMaker.pnlTop(dataname));
		/*  panelupdate are empty panels, which are added to when dropdown menus are activated  */
		panelupdate = new JPanel();
		panelupdate2 = new JPanel();


		panel0 = new JPanel();
//		panel0.setLayout(new BoxLayout(panel0,BoxLayout.Y_AXIS));
		panel0.add(addquestionspanel());
		nextStage(1);
//		panel0.add(adddropdownpanel(labelvmnum,methodname));	//compliance
		panel0.add(panelupdate);	//for easy removal and insertion
//		panel0.add(adddropdownpanel(labelderive,methodname2));	//derive
		panel0.add(panelupdate2);	//for easy removal and insertion

		panelx.add(panel0);
		panelx.add(createbottom(), BorderLayout.SOUTH);

//		JScrollPane pane = new JScrollPane(panel0);  // panel0 is the panel that will be scrolled
//		panelx.getContentPane().add(pane); 			// frame adds the scrollpane pane
		input = dataname.getText();			//we want to know if the name is empty
		
		return panelx;
	}
	public JPanel adddropdownpanel(JLabel addlabel, String panelname){	//need listener, string, cmbox 2 panel
		JPanel dropdownpanel = new JPanel();
		if (panelname.equals("instances")){					
			ComboListenvmnum comboListen1 = new ComboListenvmnum();
			String[] number = new String[] { "1", "2", "3+"};
			numberofvms = new JComboBox(number);
			dropdownpanel = PanelMaker.pnlBox(addlabel, comboListen1, panelwidth, panelheight, numberofvms);
		}
		else {
			ComboListendrvnum comboListen2 = new ComboListendrvnum();
			String[] numberdrv = new String[] { "None", "1", "2", "3+"};
			numberofdrvs = new JComboBox(numberdrv);
			dropdownpanel = PanelMaker.pnlBox(addlabel, comboListen2, panelwidth, panelheight, numberofdrvs);
		}
		return dropdownpanel;
	}	
	/* This loads the questions from text file, and puts them into the tool */
	public JPanel addquestionspanel(){
		Listener listen=new Listener();	
		questions = loadquestions();
		questionsanswers = new boolean [questions.length];
		xmax = questions.length;
//		System.out.println("Q length " + questionsanswers.length); 
		button = new JCheckBox[questions.length];		
		panel1 = PanelMaker.pnlQst(listen, button, questions);
		return panel1;
	}
	/* We add the OK and Back buttons */
	public JPanel createbottom(){
		NextBackHandler nextback = new NextBackHandler();
		buttn = new JButton[2];
		JPanel bottomPanel = PanelMaker.pnlBse(nextback, buttn);		
		return bottomPanel;
	}
	/* Loads the questions from the text file */
	public String[] loadquestions(){
    	ReadFile rf = new ReadFile();  
    	String[] lines = null;
    	String[] linespartone = null;
        try  {  
            lines = rf.readLines(questionpath);     
            strformap = new String [lines.length];
            for (int i = 0; i < lines.length; i++)               {  
            	String[] partone = lines[i].split(", ");
            	
//                System.out.println(partone[0]);  
                strformap[i] = partone[1];
//                System.out.println(strformap[i]);  

            }  
        }  
        catch(IOException e)          {  
            System.out.println("Unable to find "+questionpath+": "+e.getMessage());                
        }  
        return lines;
	}
	
	public void formatPanel(JPanel paneltochange, int a, int b){
		Dimension expectedDimension = new Dimension(a, b);
		paneltochange.setPreferredSize(expectedDimension);
		paneltochange.setMaximumSize(expectedDimension);
		paneltochange.setMinimumSize(expectedDimension);
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
	public JPanel addallvms(int num){
		JPanel addthese = new JPanel();
		JLabel newlabel1 = new JLabel("Data is on: ");
		addthese.setLayout(new BoxLayout(addthese,BoxLayout.Y_AXIS));
		int add1 = 1;
		addingvms = new JComboBox[num];						//number of vms we want to add
		for (int i = 0; i < num; i++){
			addthese.add(addingpanel(add1,i,newlabel1));
		}	
		return addthese;
	}
	public JPanel addalldrvs(int num){								//num is number of derivations user wants to add
		JPanel addthese = new JPanel();
		JLabel newlabel2 = new JLabel("Data derived from: ");
		addthese.setLayout(new BoxLayout(addthese,BoxLayout.Y_AXIS));
		int add2 = 2;
		addingdrv = new JComboBox[num];
		for (int i = 0; i < num; i++){
			addthese.add(addingpanel(add2,i,newlabel2));
		}	
		return addthese;
	}
	public JPanel addingpanel (int add, int loop, JLabel labeltoadd){
		JPanel newpanel = new JPanel();
		formatPanel(newpanel,panelwidth,panelheight);
					
		JPanel panelleft = new JPanel();
		formatPanel(panelleft,370,25);
		JPanel panelright = new JPanel();
		formatPanel(panelright,180,25);
		
		panelleft.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelright.setLayout(new FlowLayout(FlowLayout.LEFT));
		if (add==1){panelright.add(selectvms(loop)); 		}
		else {panelright.add(selectdrv(loop));		}

		panelleft.add(labeltoadd);
		newpanel.add(panelleft);
		newpanel.add(panelright);		
		return newpanel;
	}

	public JComboBox selectvms(int number){
		
		ArrayList<String> vmnames1 = VM.returnvmnames();
		String[] vmnames = vmnames1.toArray(new String[vmnames1.size()]);				
		NameListener namelisten = new NameListener();

		addingvms[number] = new JComboBox(vmnames);
		addingvms[number].setPreferredSize(new Dimension(160,20));
		addingvms[number].setSelectedIndex(-1);
		addingvms[number].addActionListener(namelisten);
	    return addingvms[number];
	}
	public JComboBox selectdrv(int number2){
		
//		ArrayList<String> drvnames = Data.getdatanames();
//		String[] derivenames = drvnames.toArray(new String[drvnames.size()]);				
		int nextnum = vms[0]-1;
		System.out.println("NUM" + nextnum);
		VM thisvm = VM.allvms.get(nextnum);
		System.out.println("Data on VM" + thisvm.name);

		
		posdrvs = new ArrayList<String>();
		ArrayList<VM> vmsout = thisvm.vmsin;					//need vmsin NOT vmsout / the name is a remainder of a mistake
		for (int i = 0; i < vmsout.size(); i++){
			VM thiscon = vmsout.get(i);
			ArrayList<Data> datahere = thiscon.getvmdata();
			System.out.println("Datasize:"+datahere.size());
			for (int j = 0; j < datahere.size(); j++){
				System.out.println("Dataname " + datahere.get(j).getname());
				posdrvs.add(datahere.get(j).getname());
			}
		}
		String[] derivenames = posdrvs.toArray(new String[posdrvs.size()]);
		
		DataListener datalisten = new DataListener();
		
		
		
//		convertdrvs();
		//we need to fix which data is added cos its the wrong WAN

		addingdrv[number2] = new JComboBox(derivenames);
		addingdrv[number2].setPreferredSize(new Dimension(160,20));
		addingdrv[number2].setSelectedIndex(-1);
		addingdrv[number2].addActionListener(datalisten);
	    return addingdrv[number2];
	}

	
	public void nextStage(int number){
//		frame.getContentPane().remove(2);
		vms = new int[number];
		panelupdate.removeAll();		
		panaddvms = addallvms(number);						
		panelupdate.add(panaddvms);
		panelupdate.revalidate();
		panelupdate.repaint();

//		frame.setVisible(true);	
	}//moredrv
	public void moredrv(int number){
//		frame.getContentPane().remove(2);
		drv = new int[number];
		if (number ==0)		drvarrexists = false;
		else drvarrexists = true;

		panelupdate2.removeAll();		
		panadddrv = addalldrvs(number);						
		panelupdate2.add(panadddrv);
		panelupdate2.revalidate();
		panelupdate2.repaint();
	}//moredrv

	
	public boolean norepeat(){
		boolean namerepeat = false;
		
		for (int i = 0; i < vms.length; i++){
//			System.out.println("Pos in check1 is " + vms[i]); 
			for (int j = 0; j < vms.length; j++){
//				System.out.println("Pos in check2 is " + vms[j]); 
				if (i == j){
					continue;
				}
				if (vms[i]==vms[j]){
//					System.out.println("We have a match"); 
					namerepeat = true;
				}
			}				
		}
		if (drvarrexists == true){
			for (int i = 0; i < drv.length; i++){
//				System.out.println("Pos in check1 is " + vms[i]); 
				for (int j = 0; j < drv.length; j++){
//					System.out.println("Pos in check2 is " + vms[j]); 
					if (i == j){
						continue;
					}
					if (drv[i]==drv[j]){
//						System.out.println("We have a match"); 
						namerepeat = true;
					}
				}				
			}
		}
		if (namerepeat==true){
			JOptionPane.showMessageDialog(frame,"Repeating information","Message",JOptionPane.WARNING_MESSAGE);						
		}		
		return namerepeat;
	}
	public boolean notnull(){
//		System.out.println("VMlength " + vms.length); 
		boolean vmsnotnull = false;
		if (vms==null){
			vmsnotnull=true;
//			System.out.println("its null!"); 
//		}
//		if (vmsnotnull==true){
			JOptionPane.showMessageDialog(ProgramOne.frame,"Add data to an instance","Message",JOptionPane.WARNING_MESSAGE);			
		}						
		return vmsnotnull;
	}
	
	public boolean notempty(){
		boolean vmnotempty = false;
		
		for (int i = 0; i < vms.length; i++){
			if (vms[i]==0){
				vmnotempty=true;
			}
		}
		if (drvarrexists == true){
			for (int i = 0; i < drv.length; i++){
				if (drv[i]==0){
					vmnotempty=true;
				}			
			}
		}
		if (nmdrvs == -1){
			vmnotempty = true;
		}
		if (vmnotempty==true){
			JOptionPane.showMessageDialog(ProgramOne.frame,"Empty information","Message",JOptionPane.WARNING_MESSAGE);			
		}				
		
		return vmnotempty;
	}
	public boolean namefilled(){
		boolean notfilled = false;
		System.out.println("name is " +input); 
		if (input.isEmpty()){
			notfilled = true;
			JOptionPane.showMessageDialog(frame,"Name is empty","Message",JOptionPane.WARNING_MESSAGE);			
		}
		return notfilled;
	}
	
private class NextBackHandler implements ActionListener {		
    public void actionPerformed(ActionEvent event){
        	if (event.getSource()==buttn[0]){				//ie if the button is 'next'        		

        		input = dataname.getText();			//we want to know if the name is empty

	        		if (   
	        			    !namefilled()				//data has been given a name
	        			&& !notnull()					//there is not an empty box
	        			&& !notempty()					//data has been put in at least one instance
//			        	&& !namefilled()	        			
	        			&& !norepeat()
	        			&& !Data.nameexists(input))	        			
	        		{        			
//	        				convertdrvs(drv);
	        				Data data = new Data (questionsanswers, input, vms, drv, strformap);//
							JOptionPane.showMessageDialog(frame,input + " has been added as data","Message",JOptionPane.PLAIN_MESSAGE);			
							ProgramOne.changeframe();					
							}
	        		}
        	else {								//they clicked on cancel
				ProgramOne.changeframe();					
        	}
    }
    }
private class NameListener implements ActionListener {		
    public void actionPerformed(ActionEvent event){
//    	if (event.getSource()==numberofvms){//    	if (event.getSource()==numberofvms){
    	int place;
        for (int x=0;x<addingvms.length;x++){
	        if (event.getSource()==addingvms[x]){
//	        	System.out.println("number of vm is " + (addingvms[x].getSelectedIndex()+1));
	        	place = (addingvms[x].getSelectedIndex()+1);
	        	vms[x] = place;
	            }
	        }
    }
}

private class DataListener implements ActionListener {		
    public void actionPerformed(ActionEvent event){
//    	if (event.getSource()==numberofvms){//    	if (event.getSource()==numberofvms){
    	int place;
        for (int x=0;x<addingdrv.length;x++){
	        if (event.getSource()==addingdrv[x]){
//	        	System.out.println("number of drv" + x + " is " + (addingdrv[x].getSelectedIndex()+1));
	        	place = (addingdrv[x].getSelectedIndex()+1);
	        	drv[x] = place;
	            }
	        }
    }
}

private class ComboListenvmnum implements ActionListener {		
    public void actionPerformed(ActionEvent event){
//    	if (event.getSource()==numberofvms){
    	nmvms = (int) numberofvms.getSelectedIndex()+1;
//    	System.out.println("number of vms to enter is " + nmvms); 
    	nextStage(nmvms);
		}
    }//ComboListendrvnum
private class ComboListendrvnum implements ActionListener {		
    public void actionPerformed(ActionEvent event){
//    	if (event.getSource()==numberofvms){
    	nmdrvs = 0;
    	nmdrvs = (int) numberofdrvs.getSelectedIndex();
//    	System.out.println("number of drvs to enter is " + nmdrvs); 
//    	if(nmdrvs>0) 
    	moredrv(nmdrvs);
		}
    }

private class Listener implements ActionListener {
    public void actionPerformed(ActionEvent event){
        for (int x=0;x<xmax;x++){
//        	System.out.println("Im now true");

        if (event.getSource()==button[x]){
            if(questionsanswers[x]==false){
            	questionsanswers[x]=true;
//            	System.out.println(x + " is now true");
            }
            else {
            	questionsanswers[x]=false;
//            	System.out.println(x + " is now false");
            }
        }
    }}
}
}
