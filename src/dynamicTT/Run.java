package dynamicTT;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class Run extends JFrame {
	private int min;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Run frame = new Run();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
//C:\\Users\\govin\\eclipse-workspace\\fileRead\\src\\fileRead\\Classroom.csv
	/**
	 * Create the frame.
	 */
	public Run() {
		
		setTitle("Genetic Time Table");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnGenerate = new JButton("GENERATE TIMETABLE");
		btnGenerate.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		btnGenerate.setBounds(108, 117, 230, 42);
		btnGenerate.setForeground(Color.WHITE);
		btnGenerate.setBackground(Color.ORANGE);
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Start.......");
			do {
		        Initialization initialize=new Initialization();       
		        try {
					initialize.readInput();
					System.out.println("HEEEEYYYYY");
					System.out.println(initialize.getMinimum());
					
			
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		       min=initialize.getMinimum();
			}
			while(min!=0);
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnGenerate);
	}

}



