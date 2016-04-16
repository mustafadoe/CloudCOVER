import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


public class EnterPanel {
	private AddUser adduser;
	private VMFrame addinstance;
	private DataAdd adddata;
	private ConnectionAdd addcon;
	private AnalysePanel analysis;
	JFrame frame;
	private JPanel paneltop;
	private JPanel panel0;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JPanel panel6;
	private JPanel panel7;
	private JPanel panel8;
	private JPanel panelmain;


	private JButton buttons[];
	private boolean analysisexist;		//maybe we can delete this

	
	public void hello (){
		System.out.println("in hi::::::"); 
	}
	
	public EnterPanel() throws SQLException{
		frame = new JFrame();
		adduser = new AddUser();
		addinstance = new VMFrame();
		adddata = new DataAdd();
		addcon = new ConnectionAdd();
//		analysis = new AnalysePanel();
		reset();

	}
//	public JPanel newcomms(){
//		reset();
//		return panel0;
//	}
	public void invisible(){
		frame.setVisible(false);
	}

	public void visible(){
//		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void reset1(){
		
		panelmain.removeAll();	        			
//		panadddrv = addalldrvs(number);	  
//		JPanel paneltoadd = panelmain;
		panelmain.add(panel0);	        			
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
		frame.setLocationRelativeTo(null);

	}
	
	public void reset()	{
		
		panelmain = new JPanel();
		panel0 = new JPanel();
		paneltop = new JPanel();
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		panel6 = new JPanel();
		panel7 = new JPanel();
		panel8 = new JPanel();

		
		formatpanel(paneltop,500,50);
		formatpanel(panel1,200,20);
		formatpanel(panel2,400,60);
		formatpanel(panel3,400,60);
		formatpanel(panel4,400,60);
		formatpanel(panel5,400,60);
		formatpanel(panel6,400,60);
		formatpanel(panel7,400,60);
		formatpanel(panel8,400,60);

		panel0.setLayout(new BoxLayout(panel0,BoxLayout.Y_AXIS));
		panelmain.setLayout(new BoxLayout(panelmain,BoxLayout.Y_AXIS));

		Font font = new Font("Sans Serif", Font.BOLD, 16);
		JLabel labeltop = new JLabel("Cloud Security Evaluation Tool V1.5");	
		labeltop.setFont(font);
		paneltop.add(labeltop);	
		
		JLabel label1 = new JLabel("What do you want to do?");		
		panel1.add(label1);		

		Listener listen=new Listener();
		buttons = new JButton[7];
		buttons[0] = new JButton("Cloud Instance");		
		buttons[1] = new JButton("Connections");
		buttons[2] = new JButton("View / Remove");	
		buttons[3] = new JButton("Save / Load Input");	
		buttons[4] = new JButton("Done");	
		buttons[5] = new JButton("Add Data");	
		buttons[6] = new JButton("Add User");	

		
		buttons[0].setPreferredSize(new Dimension(150, 30));
		buttons[1].setPreferredSize(new Dimension(150, 30));
		buttons[2].setPreferredSize(new Dimension(150, 30));
		buttons[3].setPreferredSize(new Dimension(150, 30));
		buttons[4].setPreferredSize(new Dimension(150, 30));
		buttons[5].setPreferredSize(new Dimension(150, 30));
		buttons[6].setPreferredSize(new Dimension(150, 30));

		
		buttons[0].addActionListener(listen);	//adds a listener for those
		buttons[1].addActionListener(listen);	//adds a listener for those
		buttons[2].addActionListener(listen);	//adds a listener for those
		buttons[3].addActionListener(listen);	//adds a listener for those
		buttons[4].addActionListener(listen);	//adds a listener for those
		buttons[5].addActionListener(listen);	//adds a listener for those
		buttons[6].addActionListener(listen);	//adds a listener for those

		panel2.add(buttons[0]);					
		panel3.add(buttons[1]);						
		panel4.add(buttons[2]);				
		panel5.add(buttons[3]);				
		panel6.add(buttons[4]);				
		panel7.add(buttons[5]);				
		panel8.add(buttons[6]);				//add user

		JLabel label2 = new JLabel("Input details of VMs & cloud instances");		
		panel2.add(label2);		
		label2.setForeground(Color.BLUE);
		JLabel label3 = new JLabel("Add communications between cloud instances");		
		panel3.add(label3);		
		label3.setForeground(Color.BLUE);
		JLabel label4 = new JLabel("View input / remove instances or connections");		
		panel4.add(label4);	
		label4.setForeground(Color.BLUE);
		JLabel label5 = new JLabel("Save the details of your input so far");	
		panel5.add(label5);		
		label5.setForeground(Color.BLUE);
		JLabel label6 = new JLabel("Go through the threats to your deployment");		
		panel6.add(label6);		
		label6.setForeground(Color.BLUE);
		JLabel label7 = new JLabel("Add important data to the instances");		
		panel7.add(label7);		
		label7.setForeground(Color.BLUE);
		JLabel label8 = new JLabel("Add information about users on deployment");		
		panel8.add(label8);		
		label8.setForeground(Color.BLUE);	
		panel0.add(paneltop);
		panel0.add(panel1);		
		panel0.add(panel2);
		panel0.add(panel3);
		panel0.add(panel7);		
		panel0.add(panel8);				//user data
		panel0.add(panel4);	
		panel0.add(panel5);				
		panel0.add(panel6);	
		panelmain.add(panel0);
		frame.add(panelmain);
		
//		JScrollPane pane = new JScrollPane(panelmain);  // panel0 is the panel that will be scrolled
//		frame.getContentPane().add(pane); 			// frame adds the scrollpane pane

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,700);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
//		frame.setResizable(false);
	}	
	public void formatpanel(JPanel paneltochange, int a, int b){
//		paneltochange.setBorder(BorderFactory.createLineBorder(Color.black));
		Dimension expectedDimension = new Dimension(a, b);
		paneltochange.setPreferredSize(expectedDimension);
		paneltochange.setMaximumSize(expectedDimension);
		paneltochange.setMinimumSize(expectedDimension);
	}
	public class ListenerAlt implements ActionListener {
	    public void actionPerformed(ActionEvent event){
//        	if (event.getSource()==okbutton){
        		System.out.println("Clicked on OK"); 	
 //       		EnterPanel.reset1();
//        		}

	    }
	}
	private class Listener implements ActionListener {
	    public void actionPerformed(ActionEvent event){
	        for (int x=0;x<buttons.length;x++){
	        	if (event.getSource()==buttons[x]){
	        		if (x==0){
	        			panelmain.removeAll();	        			
	        			JPanel paneltoadd = addinstance.resetpanel();
	        			panelmain.add(paneltoadd);	        				        			
	        		}
	        		if (x==1){
	        			if (VM.vmnames.size()<2){
		        			JOptionPane.showMessageDialog(frame,"You need to enter at least two instances first","Error",JOptionPane.PLAIN_MESSAGE);			
	        			}	        			
	        			else{
		        			panelmain.removeAll();	        			
		        			panelmain.add(addcon.resetpanel());
	        			}
	        		}
	        		if (x==2){						//want to change this to better see info
//	        			invisible();
//	        			ProgramOne.viewinput();

	        			
//	        			ViewDisplay viewdisplay = new ViewDisplay();
//	        			viewdisplay.createmap();
	        			ViewInput.printall();
	        		}
	        		if (x==3){
	        			invisible();
	        			try {
							ProgramOne.loadOrSave();
						} catch (SQLException e) {
							e.printStackTrace();
						}
	        		}
	        		if (x==4){
	        			try {
	        				analysis = new AnalysePanel();

//							analysis.resetanalysis();
	        				frame.setVisible(false);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	
	        			
	        			
//						analysis.resetanalysis();

	        			
	        		}
	        		if (x==5){
	        			panelmain.removeAll();	        			
	        			panelmain.add(adddata.resetpanel());	        			
	        		}
	        		if (x==6){	        			
	        			panelmain.removeAll();	        			
	        			JPanel paneltoadd = adduser.resetpanel();
	        			panelmain.add(paneltoadd);	        			
	        		}
        			panelmain.revalidate();
        			panelmain.repaint();
	        		}
	        	}
	        }
	    }
}
