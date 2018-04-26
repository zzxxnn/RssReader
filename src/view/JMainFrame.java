package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import service.RSSService;
import service.UpdateThread;
import model.Channel;
import model.News;

public class JMainFrame extends JFrame {
	// ��������
	private final static int WIDTH = 800;
	private final static int HEIGHT = 700;
	private final static String TITLE = "RSS�Ķ���";
	// �������
	private JButton button;
	private JTextArea area;
	private JComboBox jComboBox;
	private DefaultTableModel defaultTableModel;
	private JTable jTable;

	//
	RSSService rssService;
	//
	List<News> newlistList;

	/**
	 * ���췽��
	 * 
	 */
	public JMainFrame() {
		rssService = new RSSService();
		this.setTitle(TITLE);
		this.setSize(WIDTH, HEIGHT);
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out
						.println("JMainFrame.JMainFrame().new KeyListener() {...}.keyReleased()");
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		setCenter();
		this.setDefaultCloseOperation(3);
		this.setJMenuBar(getJMenuBa());
		this.setContentPane(getJPMain());
		

	}

	/**
	 * ���þ���
	 */
	private void setCenter() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		this.setLocation((dimension.width - WIDTH) / 2,
				(dimension.height - HEIGHT) / 2);
	}

	/**
	 * ��ȡ�����
	 */
	private JPanel getJPMain() {
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());

		// JLabel jlChannel = new JLabel("վ��");
		// jPanel.add(jlChannel);
		// jPanel.add(getJCBChannel());
		// jPanel.add(getJBRead());
//		jPanel.add(getJPNorth(), BorderLayout.NORTH);
		jPanel.add(getJToolBar(), BorderLayout.NORTH);
		
		jPanel.add(getJSPTable(), BorderLayout.CENTER);
		jPanel.add(getJPanel(), BorderLayout.SOUTH);

		return jPanel;
	}
	/**
	 * ����������
	 * @return
	 */
	private JToolBar getJToolBar(){
		JToolBar bar = new JToolBar("������");
		ImageIcon icon = new ImageIcon("images/��ͼ1441591039.png");
		JButton button = new JButton(icon);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if (rssService.save()) {
					System.out
							.println("JMainFrame.getJToolBar().new ActionListener() {...}.actionPerformed()");
					JOptionPane.showMessageDialog(null, "����ɹ�");
				} else {
					JOptionPane.showMessageDialog(null, "����ʧ��");
				}
			}
		});
		bar.add(button);
		
		
		return bar;
	}
	int o;
	public int getO() {
		return o;
	}

	public void setO(int o) {
		this.o = o;
	}

	private JMenuBar getJMenuBa() { 
		JMenuBar menuBar = new JMenuBar();
		//�ļ��˵�
		JMenu mnNewMenu = new JMenu("�ļ�");
		menuBar.add(mnNewMenu);
		JMenu mnNewMenu1 = new JMenu("����");
		JMenuItem item = new JMenuItem("����");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "������");
			}
		});
		mnNewMenu1.add(item);
		JMenuItem item1 = new JMenuItem("���ø���");
		item1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						new UpdateThread().run();
					}
				}).run();
			}
		});
		mnNewMenu1.add(item1);
		menuBar.add(mnNewMenu1);
		
		final List<Channel> channels = rssService.getChannelList();
//		for (Channel channel : channels) {
//			JMenuItem mntmNewMenuItem = new JMenuItem(channel.getName());
//			mntmNewMenuItem.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					// TODO Auto-generated method stub
//					
//					Channel channel = (Channel) jComboBox.getSelectedItem();
//					String fulepath = channel.getFilePath();
//					List<News> list = rssService.getNewsList(fulepath);
//					// String content = "";
//					// for (News news : list) {
//					// content += rssService.newsToString(news);
//					// }
//					// area.setText(content);
//					showTable(list);
//					newlistList = list;
//				}
//			});
//			mnNewMenu.add(mntmNewMenuItem);
//		}
		final List<String> list = new ArrayList<String>();
		for (Channel string : channels) {
			list.add(string.getName());
		}
		for ( int i = 0; i < channels.size(); i++) {
			setO(i);
			
			JMenuItem mntmNewMenuItem = new JMenuItem(channels.get(i).getName());
			mntmNewMenuItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(e.getActionCommand());
					Channel channel = channels.get(list.indexOf(e.getActionCommand()));
					String fulepath = channel.getFilePath();
					List<News> list = rssService.getNewsList(fulepath);
					showTable(list);
					newlistList = list;
				}
			});
			mnNewMenu.add(mntmNewMenuItem);
		}
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("����");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rssService.save()) {
					JOptionPane.showMessageDialog(null, "����ɹ�");
				} else {
					JOptionPane.showMessageDialog(null, "����ʧ��");
				}
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("�˳�");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		return menuBar;

	}

	/**
	 * 
	 */
	private JPanel getJPNorth() {
		JPanel nor = new JPanel();

		JLabel jlChannel = new JLabel("վ��");
		nor.add(jlChannel);
		nor.add(getJCBChannel());
		nor.add(getJBRead());
		JButton JBSave = new JButton("����");
		JBSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rssService.save()) {
					JOptionPane.showMessageDialog(null, "����ɹ�");
				} else {
					JOptionPane.showMessageDialog(null, "����ʧ��");
				}
			}
		});
		nor.add(JBSave);
		JButton JBUpdate = new JButton("����");
		JBUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Channel channel = (Channel) jComboBox.getSelectedItem();
				String fulepath = channel.getFilePath();
				String url = channel.getUrl();
				try {
					new UpdateThread().upDate(url, fulepath);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		nor.add(JBUpdate);
		JButton szgxButton = new JButton("���ø���");
		szgxButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// String a = JOptionPane.showInputDialog("������")
				new UpdateThread().start();
			}
		});
		nor.add(szgxButton);
		nor.setLayout(new FlowLayout(FlowLayout.LEFT));
		return nor;
	}

	/**
	 * ��ȡ�б������
	 */
	private JComboBox getJCBChannel() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			List<Channel> channels = rssService.getChannelList();
			for (Channel channel : channels) {
				jComboBox.addItem(channel);
			}
			// jComboBox.addItem("��Ѷ-����Ҫ��");
			// jComboBox.addItem("��Ѷ-����Ҫ��");
			// jComboBox.addItem("��Ѷ-����Ҫ��");
			// jComboBox.addItem("��Ѷ-����Ҫ��");
		}
		// jComboBox.addActionListener(new ReadActionListener());
		return jComboBox;
	}

	/**
	 * 
	 */
	private JButton getJBRead() {
		if (button == null) {
			button = new JButton("��ȡ");
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Channel channel = (Channel) jComboBox.getSelectedItem();
					String fulepath = channel.getFilePath();
					List<News> list = rssService.getNewsList(fulepath);
					// String content = "";
					// for (News news : list) {
					// content += rssService.newsToString(news);
					// }
					// area.setText(content);
					showTable(list);
					newlistList = list;
				}
			});

			// button.addActionListener(new ReadActionListener());
		}

		return button;
	}

	/**
	 * 
	 */
	private JPanel getJPanel (){
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());
		jPanel.add(getJSPContent(),BorderLayout.NORTH);
		JLabel jLabel = new JLabel("www.ruanko.com");
		jPanel.add(jLabel,BorderLayout.SOUTH);
		
		return jPanel;
	}
	private JScrollPane getJSPContent() {
		JScrollPane jScrollPane = null;
		if (area == null) {
			area = new JTextArea();
			area.setLineWrap(true);
			jScrollPane = new JScrollPane(area);
//			jScrollPane.add(area);
			jScrollPane.setPreferredSize(new Dimension(780, 260));
		}
//		JLabel jLabel = new JLabel("www.ruanko.com");
//		JPanel jPanel = new JPanel();
//		jPanel.add(jLabel);
//		jScrollPane.add(jPanel);
		return jScrollPane;
	}

	private JScrollPane getJSPTable() {
		JScrollPane jScrollPane = null;
		if (jTable == null) {
			defaultTableModel = new DefaultTableModel();
			defaultTableModel.addColumn("����");
			defaultTableModel.addColumn("����ʱ��");
			defaultTableModel.addColumn("����ʱ��");
			defaultTableModel.addColumn("����");
			jTable = new JTable(defaultTableModel);
			jTable.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					if (e.getClickCount() == 1) {
						int a = jTable.getSelectedRow();
						News news = newlistList.get(a);
						area.setText(rssService.newsToString(news));
					}
				}

			});
			jScrollPane = new JScrollPane(jTable);
		}
		return jScrollPane;
	}

	class ReadActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Channel channel = (Channel) jComboBox.getSelectedItem();
			String filePath = channel.getFilePath();
			if (rssService.load(filePath) == null) {
				area.setText("��ȡʧ��");
			} else {
				area.setText("��ȡ�ɹ�");

			}
		}

	}

	private void showTable(List<News> newsList) {
		// ��ձ������
		int rowcount = defaultTableModel.getRowCount();
		while (rowcount > 0) {
			defaultTableModel.removeRow(0);
			rowcount--;
		}
		// ����������ʾ����
		for (int i = 0; i < newsList.size(); i++) {
			News news = newsList.get(i);
			// ��ȡ��ǰʱ��
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String currentdata = dateFormat.format(date);
			// ��������ʾ�ڱ����
			String[] data = { news.getTitle(), currentdata, news.getPubDate(),
					news.getAuthor() };
			defaultTableModel.addRow(data);

		}

	}
}
