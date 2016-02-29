package testWindowFrame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Component;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import locationinRAID.LocationinRaidfinder;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/*  code for getting text out of clipboard
TextTransfer textTransfer = new TextTransfer();

//display what is currently on the clipboard
System.out.println("Clipboard contains:" + textTransfer.getClipboardContents());
*/


public class AppWindow1 extends LocationinRaidfinder {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindow1 window = new AppWindow1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppWindow1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		final JRadioButton rdDicomBtn = new JRadioButton("Dicom");
		rdDicomBtn.setSelected(true);
		panel.add(rdDicomBtn);
		
		final JRadioButton rdNonDicomBtn = new JRadioButton("Non-Dicom");
		panel.add(rdNonDicomBtn);
		
		//build group around Buttons
		ButtonGroup group = new ButtonGroup();
		group.add(rdNonDicomBtn);
		group.add(rdDicomBtn);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.EAST);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		final JTextPane fsBox = new JTextPane();
		fsBox.setPreferredSize(new Dimension(438, 30));
		panel_2.add(fsBox);
		
		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(130, 135, 144)));
		scrollPane.setPreferredSize(new Dimension(227, 190));
		panel_3.add(scrollPane);
		
		final JTextPane queryBox = new JTextPane();
		queryBox.setBorder(new LineBorder(new Color(0, 0, 0)));
		queryBox.setPreferredSize(new Dimension(10, 10));
		scrollPane.setViewportView(queryBox);
		
		JButton getFSstring = new JButton("Get File Path");
		getFSstring.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean dicom_ind = false;
				//LocationinRaidfinder FSbtn = new LocationinRaidfinder();
				if (rdDicomBtn.isSelected()){
					dicom_ind = true;}
				else if (rdNonDicomBtn.isSelected()){
					dicom_ind = false;
				}
				String box1 = queryBox.getText();
				fsBox.setText(pathFinder(box1, dicom_ind));
			}	
		});
		panel_1.add(getFSstring);
		
		JButton copyString = new JButton("Copy File Path");
		copyString.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Clipboard clipboard = toolkit.getSystemClipboard();
				StringSelection strSel = new StringSelection(fsBox.getText());
				clipboard.setContents(strSel, null);
				
			}
		});
		copyString.setAlignmentX(Component.CENTER_ALIGNMENT);
		copyString.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel_1.add(copyString);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent arg0) {
				
				//stole this as a code example, could not get it to work without try/catch - need
				// to figure out the multiple steps of this process and why they are all necessary. 
				String contents="please copy string to clipboard";
				boolean dicom_ind = false;
				  Clipboard c=Toolkit.getDefaultToolkit().getSystemClipboard();
				  Transferable data=c.getContents(null);
				  if (data != null && data.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				    try {
				      contents=((String)(data.getTransferData(DataFlavor.stringFlavor)));
				    }
				 catch (    Exception e) {
				      //logger.log(Level.WARNING,"Failed getting tranfer data: " + e.getMessage(),e);
				    }
				  }
				 //queryBox.setText(contents);
					if (rdDicomBtn.isSelected()){
						dicom_ind = true;}
					else if (rdNonDicomBtn.isSelected()){
						dicom_ind = false;
					}
					fsBox.setText(pathFinder(contents, dicom_ind));
				  	
			}
		});
		
	}

}


