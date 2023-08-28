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

public class RestaurtantSharingUpdateFrame extends JFrame {
	
	private JPanel contentPane;
	private JTextField textName;
	private JTextField textPhone;
	private JTextField textMemo;
	private JButton btnCreate;
	private JLabel lblMemo;
	private JLabel lblAddress;
	private JTextField textAddress;
	private JLabel lblPhone;
	private JLabel lblName;
	private JButton btnCancel;
	private JPanel buttonpanel;
	private JPanel panel;
	
	private Component parent; // 부모 JFrame를 저장하기 위한 필드.
	private int cid;
	private RestaurtantSharingMain app; 
	
	private final RestaurtantSharingDaoImpl dao = RestaurtantSharingDaoImpl.getInstance();

	/**
	 * Launch the application.
	 */
	public static void showRestaurtantUpdateFrame(Component parent, int index, RestaurtantSharingMain app){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RestaurtantSharingUpdateFrame frame = new RestaurtantSharingUpdateFrame(parent, index, app);
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
	public RestaurtantSharingUpdateFrame(Component parent, int cid, RestaurtantSharingMain app) {
		this.parent = parent;
		this.cid = cid;
		this.app = app;
		initialize();
		readfood();
	}
		
		private void readfood() {
		RestaurtantSharing restaurtantSharing = dao.read(cid);
		textName.setText(restaurtantSharing.getName());
		textPhone.setText(restaurtantSharing.getPhone());
		textAddress.setText(restaurtantSharing.getAddress());
		textMemo.setText(restaurtantSharing.getMemo());	
	}

		private void initialize() {
		setTitle("맛집 수정");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// JFrame의 좌표.
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
		contentPane.setLayout(new BorderLayout(0,0));
		
		panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblName = new JLabel("식당이름");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("바탕", Font.BOLD, 15));
		lblName.setBounds(23, 30, 179, 54);
		panel.add(lblName);
		
		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(217, 31, 478, 54);
		panel.add(textName);
		
		lblPhone = new JLabel("전화번호");
		lblPhone.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhone.setFont(new Font("바탕", Font.BOLD, 15));
		lblPhone.setBounds(23, 110, 179, 54);
		panel.add(lblPhone);
		
		textPhone = new JTextField();
		textPhone.setColumns(10);
		textPhone.setBounds(217, 111, 478, 54);
		panel.add(textPhone);
		
		lblAddress = new JLabel("주소");
		lblAddress.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddress.setFont(new Font("바탕", Font.BOLD, 15));
		lblAddress.setBounds(23, 199, 179, 54);
		panel.add(lblAddress);
		
		textAddress = new JTextField();
		textAddress.setColumns(10);
		textAddress.setBounds(217, 190, 478, 74);
		panel.add(textAddress);
		
		lblMemo = new JLabel("후기");
		lblMemo.setHorizontalAlignment(SwingConstants.CENTER);
		lblMemo.setFont(new Font("바탕", Font.BOLD, 15));
		lblMemo.setBounds(23, 298, 179, 55);
		panel.add(lblMemo);
		
		textMemo = new JTextField();
		textMemo.setColumns(10);
		textMemo.setBounds(217, 299, 478, 169);
		panel.add(textMemo);
		
		buttonpanel = new JPanel();
		buttonpanel.setBackground(new Color(192, 192, 192));
		contentPane.add(buttonpanel, BorderLayout.SOUTH);
		
		btnCreate = new JButton("수정완료");
		btnCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateNewlist();
			}
		});
		btnCreate.setFont(new Font("바탕", Font.BOLD, 15));
		buttonpanel.add(btnCreate);
		
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); // 현재 창 닫기
			}
		});
		btnCancel.setFont(new Font("바탕", Font.BOLD, 15));
		buttonpanel.add(btnCancel);
	}
		private void updateNewlist() {
			String name = textName.getText();
			if(name.equals("")) {
				JOptionPane.showMessageDialog(this, "반드시 수정할 이름을 입력해주세요!");
				return;
			}
			String phone = textPhone.getText();
			if(phone.equals("")) {
				JOptionPane.showMessageDialog(this, "반드시 수정할 전화번호를 입력해주세요!");
				return;
			}
			String address = textAddress.getText();
			if(address.equals("")) {
				JOptionPane.showMessageDialog(this, "반드시 수정할 주소를 입력해주세요!");
				return;
			}
			String memo = textMemo.getText();
			
			RestaurtantSharing restaurtantSharing = new RestaurtantSharing(cid, name, phone, address, memo);
			
			int confirm = JOptionPane.showConfirmDialog
					(RestaurtantSharingUpdateFrame.this, "수정 하시겠습니까?", "수정 확인", JOptionPane.YES_NO_OPTION);
			if(confirm == JOptionPane.YES_NO_OPTION) {
				dao.upate(restaurtantSharing);
				app.notifyRSUpdate();
				// 현재 창 닫기
				dispose();
			}
			
		}
		
		
}
