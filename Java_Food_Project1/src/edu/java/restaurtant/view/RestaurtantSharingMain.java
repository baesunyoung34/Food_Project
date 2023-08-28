package edu.java.restaurtant.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Font;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.java.restaurtant.controller.RestaurtantSharingDaoImpl;
import edu.java.restaurtant.model.RestaurtantSharing;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RestaurtantSharingMain {

	private static final String[] COLUMN_NAME = { "이름", "전화번호", "주소", "후기" };

	private JFrame frame;
	private JButton btnInsert;
	private JPanel buttonpanel;
	private JButton btnupdate;
	private JButton btnDelete;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel model;
	private List<RestaurtantSharing> restaurtantlist;

	private final RestaurtantSharingDaoImpl dao = RestaurtantSharingDaoImpl.getInstance();
	private JButton btnReturn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RestaurtantSharingMain window = new RestaurtantSharingMain();
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
	public RestaurtantSharingMain() {
		initialize(); // 화면에 보여질 GUI 컴포넌트들을 생성하고 초기화
		loadFoodData(); // 파일에 저장된 데이터를 로딩(테이블 초기화).
	}

	private void loadFoodData() {
		restaurtantlist = dao.read();
//		System.out.println("size=" + restaurtantlist.size());
		for (RestaurtantSharing c : restaurtantlist) {
			Object[] row = { c.getName(), c.getPhone(), c.getAddress(), c.getMemo() };
			model.addRow(row);
		}
	}

	public void notifyRSUpdate() {
		resetTableModel(); // JTable을 처음부터 새로 그림
		JOptionPane.showMessageDialog(frame, "맛집 수정 완료했습니다.");

	}

	// 새로운 맛집 추가시 성공했을때 호출하는 메서드
	public void notifyRSCreated() {
		resetTableModel();
		JOptionPane.showMessageDialog(frame, "새로운 맛집 추가 했습니다.");
	}

	public void resetTableModel() {
		// 데이터가 비워진 모델을 새로 생성.
		model = new DefaultTableModel(null, COLUMN_NAME);
		// 파일에 저장된 데이터를 다시 읽고 테이블 모델 추가.
		loadFoodData();
		// 새롭게 만들어진 테이블 모델을 테이블에 세팅.
		table.setModel(model);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(new Color(255, 255, 255));
		frame.setBounds(100, 100, 773, 505);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("맛집 저장하기");

		buttonpanel = new JPanel();
		buttonpanel.setBackground(new Color(255, 255, 255));
		frame.getContentPane().add(buttonpanel, BorderLayout.NORTH);

		btnInsert = new JButton("추가하기");
		btnInsert.setBackground(new Color(192, 192, 192));
		btnInsert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RestaurtantSharingCreateFrame.showRestaurtantCreatFrame(frame, RestaurtantSharingMain.this); // this -
																												// ACtionListener
																												// 타입의
																												// 익명 객체
			}
		});
		btnInsert.setFont(new Font("바탕", Font.BOLD, 15));
		buttonpanel.add(btnInsert);

		btnupdate = new JButton("수정하기");
		btnupdate.setBackground(new Color(192, 192, 192));
		btnupdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateRestaurtantSharing();
			}
		});
		btnupdate.setFont(new Font("바탕", Font.BOLD, 15));
		buttonpanel.add(btnupdate);

		btnDelete = new JButton("삭제하기");
		btnDelete.setBackground(new Color(192, 192, 192));
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delecteRestaurtantSharing();
			}
		});
		btnDelete.setFont(new Font("바탕", Font.BOLD, 15));
		buttonpanel.add(btnDelete);

		btnSearch = new JButton("검색");
		btnSearch.setBackground(new Color(192, 192, 192));
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectRestaurtantSharingByKeyword();
			}
		});
		btnSearch.setFont(new Font("바탕", Font.BOLD, 15));
		buttonpanel.add(btnSearch);
		
		btnReturn = new JButton("돌아가기");
		btnReturn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 clearAndShowAllRestaurants(); // 모든 식당 목록 보기
			}
		});
		btnReturn.setFont(new Font("바탕", Font.BOLD, 15));
		btnReturn.setBackground(Color.LIGHT_GRAY);
		buttonpanel.add(btnReturn);

		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new JTable(); // JTable 타입 객체 생성
		model = new DefaultTableModel(null, COLUMN_NAME); // TableModel 객체 생성
		table.setModel(model); // TableModel 객체를 테이블에 셋팅
		scrollPane.setViewportView(table); // 테이블을 스크롤페인에 넣음.

		table.setFont(new Font("바탕", Font.BOLD, 13));
		
	}

	private void clearAndShowAllRestaurants() {
		restaurtantlist = dao.read(); // 모든 식당 가져오기
	    model = new DefaultTableModel(null, COLUMN_NAME);
	    for (RestaurtantSharing c : restaurtantlist) {
	        Object[] row = { c.getName(), c.getPhone(), c.getAddress(), c.getMemo()};
	        model.addRow(row);
	    }
	    table.setModel(model);
		
	}

	// 검색은 식당이름과 전화번호만 검색 가능하게 하기
	private void selectRestaurtantSharingByKeyword() {
		String keyword = JOptionPane.showInputDialog(frame, "검색어");
		System.out.println("keyword=" + keyword);
		if (keyword == null) {
			JOptionPane.showMessageDialog(frame, "검색을 입력하세요.");
			return; // 메서드 종료
		} 
		// 검색 결과
		restaurtantlist = dao.read(keyword);
		if(restaurtantlist.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "검색 결과가 없습니다!");
			return;
		}
		model = new DefaultTableModel(null, COLUMN_NAME);
		for (RestaurtantSharing c : restaurtantlist) {
			Object[] row = { c.getName(), c.getPhone(), c.getAddress(), c.getMemo()};
			model.addRow(row);
		}
		table.setModel(model);

	}

	private void updateRestaurtantSharing() {
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(frame, "수정해야하는 행을 선택하세요!", "경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int cid = restaurtantlist.get(row).getCid();
		RestaurtantSharingUpdateFrame.showRestaurtantUpdateFrame(frame, cid, RestaurtantSharingMain.this);

	}

	private void delecteRestaurtantSharing() {
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(frame, "삭제하려는 행을 선택하세요!", "경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int confirm = JOptionPane.showConfirmDialog(frame, "정말 삭제를 할까요?", "삭제확인", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_NO_OPTION) {
			int cid = restaurtantlist.get(row).getCid();

			dao.delete(cid);
			model.removeRow(row);
			JOptionPane.showMessageDialog(frame, "삭제가 되었습니다.");
		}

	}

}
