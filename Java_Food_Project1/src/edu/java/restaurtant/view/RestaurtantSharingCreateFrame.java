package edu.java.restaurtant.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.java.restaurtant.controller.RestaurtantSharingDaoImpl;
import edu.java.restaurtant.model.RestaurtantSharing;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class RestaurtantSharingCreateFrame extends JFrame {
	
	private final RestaurtantSharingDaoImpl dao = RestaurtantSharingDaoImpl.getInstance();
	private JPanel contentPane;
	private JPanel panel;
	private JPanel buttonpanel;
	private JLabel lblName;
	private JTextField textName;
	private JLabel lblPhone;
	private JTextField textPhone;
	private JLabel lblAddress;
	private JTextField textAddress;
	private JLabel lblMemo;
	private JTextField textMemo;
	private JButton btnCreate;
	private JButton btnDelete;
	
	private Component parent; // 부모 JFrame를 저장하기 위한 필드.
	private RestaurtantSharingMain app; 

	/**
	 * Launch the application.
	 */
	public static void showRestaurtantCreatFrame(Component parent, RestaurtantSharingMain app){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RestaurtantSharingCreateFrame frame = new RestaurtantSharingCreateFrame(parent, app);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public RestaurtantSharingCreateFrame(Component parent, RestaurtantSharingMain app) {
		this.parent = parent;
		this.app = app;
		initialize();
	}
		private void initialize() {
		setTitle("새로운 맛집 저장");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//JFrame 좌표
		int x =100;
		int y = 100;
		if(parent != null) {
			x = parent.getX();
			y = parent.getY();
		
		}
		setBounds(x, y, 731, 587);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblName = new JLabel("식당이름");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("바탕", Font.BOLD, 15));
		lblName.setBounds(23, 10, 179, 54);
		panel.add(lblName);
		
		textName = new JTextField();
		textName.setBounds(217, 11, 478, 54);
		panel.add(textName);
		textName.setColumns(10);
		
		lblPhone = new JLabel("전화번호");
		lblPhone.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhone.setFont(new Font("바탕", Font.BOLD, 15));
		lblPhone.setBounds(23, 82, 179, 54);
		panel.add(lblPhone);
		
		textPhone = new JTextField();
		textPhone.setColumns(10);
		textPhone.setBounds(217, 83, 478, 54);
		panel.add(textPhone);
		
		lblAddress = new JLabel("주소");
		lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddress.setFont(new Font("바탕", Font.BOLD, 15));
		lblAddress.setBounds(23, 158, 179, 54);
		panel.add(lblAddress);
		
		textAddress = new JTextField();
		textAddress.setColumns(10);
		textAddress.setBounds(217, 159, 478, 74);
		panel.add(textAddress);
		
		lblMemo = new JLabel("후기");
		lblMemo.setHorizontalAlignment(SwingConstants.CENTER);
		lblMemo.setFont(new Font("바탕", Font.BOLD, 15));
		lblMemo.setBounds(23, 255, 179, 55);
		panel.add(lblMemo);
		
		textMemo = new JTextField();
		textMemo.setColumns(10);
		textMemo.setBounds(217, 256, 478, 169);
		panel.add(textMemo);
		
		buttonpanel = new JPanel();
		buttonpanel.setBackground(new Color(192, 192, 192));
		contentPane.add(buttonpanel, BorderLayout.SOUTH);
		
		btnCreate = new JButton("추가");
		btnCreate.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				CreatNewlist();
			}
		});
		btnCreate.setFont(new Font("바탕", Font.BOLD, 15));
		buttonpanel.add(btnCreate);
		
		btnDelete = new JButton("취소");
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnDelete.setFont(new Font("바탕", Font.BOLD, 15));
		buttonpanel.add(btnDelete);
	}
		private void CreatNewlist() {
			String name = textName.getText();
			if(name.equals("")) {
				JOptionPane.showMessageDialog(this, "이름을 꼭 입력해주세요!");
				return;
			}
			String phone = textPhone.getText();
			if(phone.equals("")) {
				JOptionPane.showMessageDialog(this, "번호를 꼭 입력해주세요!");
				return;
			}
			String address = textAddress.getText();
			if(address.equals("")) {
				JOptionPane.showMessageDialog(this, "주소를 꼭 입력해주세요!");
				return;
			}
			String memo = textMemo.getText();
			
			RestaurtantSharing restaurtantSharing = new RestaurtantSharing(0, name, phone, address, memo);
				dao.create(restaurtantSharing);
				app.notifyRSCreated();
				// 현재 창 닫기
				dispose();		
		}
			
}
