import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* 
 * 
 * This file is part of Cloud-COVER, which is released under the GNU General Public License
 * See the License.txt file for full license details
 */


public class PanelMaker {

	public static JPanel pnlTop(JTextField fielddata){
		JPanel toppanel = new JPanel();
		JLabel details = new JLabel("Enter name:");	//lets add the initial greeting
//		fielddata = new JTextField(10);
		toppanel.add(details);
		toppanel.add(fielddata);
		return toppanel;
	}
	public static JPanel pnlBse(ActionListener nextback, JButton[] buttn){
//		NextBackHandler nextback = new NextBackHandler();
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//		buttn = new JButton[2];
		buttn[0] = new JButton("OK");
		buttn[0].addActionListener(nextback);
		bottomPanel.add(buttn[0]);
		buttn[1] = new JButton("Cancel");
		buttn[1].addActionListener(nextback);
		bottomPanel.add(buttn[1]);
		return bottomPanel;
	}
	public static JPanel pnlQst(ActionListener infolisten, JCheckBox[] buttons, String[] buttoninfo){
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(0,2);
		layout.setHgap(10);		
		layout.setVgap(10);
		panel.setLayout(layout);		
		int xmax = buttoninfo.length;
//		System.out.println("IN PANELMAKER"); 
		for (int x=0;x<xmax;x++){
//			System.out.println("A");  
			buttons[x] = new JCheckBox(buttoninfo[x]);		//adds the buttons based on what is in array a
			buttons[x].addActionListener(infolisten);	//adds a listener for those
//			button[x].setToolTipText(questionsinfo[x]);
			panel.add(buttons[x]);					
		}	
		return panel;
	}

	public static JPanel pnlBox (JLabel label, ActionListener boxlistener, int panelwidth, int panelheight, JComboBox cmBox){
		JPanel panelMain = new JPanel();
		formatPanel(panelMain,panelwidth,panelheight);
		JPanel panelLeft = new JPanel();
		formatPanel(panelLeft,370,25);
		JPanel panelRight = new JPanel();
		formatPanel(panelRight,180,25);
		panelLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelLeft.add(label);
		panelRight.setLayout(new FlowLayout(FlowLayout.LEFT));

		cmBox.setPreferredSize(new Dimension(160,20));
		cmBox.setSelectedIndex(-1);
		cmBox.addActionListener(boxlistener);
		panelRight.add(cmBox);
		panelMain.add(panelLeft);
		panelMain.add(panelRight);		

		return panelMain;
	}
	public static void formatPanel(JPanel paneltochange, int a, int b){
//		paneltochange.setBorder(BorderFactory.createLineBorder(Color.black));
		Dimension expectedDimension = new Dimension(a, b);
		paneltochange.setPreferredSize(expectedDimension);
		paneltochange.setMaximumSize(expectedDimension);
		paneltochange.setMinimumSize(expectedDimension);
		}

}
