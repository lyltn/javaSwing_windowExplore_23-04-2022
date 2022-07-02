package window_Explore;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Explore extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	public DefaultTreeModel treemodel;
	public DefaultMutableTreeNode root;
	private Vector vT, vD;
	private String Current_Folder;
	private JLabel lblNewLabel, lblNewLabel_2;
	private FileInputStream fin;
	private String y;
	public static String uu = "";
	private BufferedInputStream bin;
	private Timer timerUpdate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Explore frame = new Explore();
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
	public Explore() {
		setTitle("Window Explore");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 923, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		NewFile f = new NewFile();
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setBounds(236, 51, 458, 35);
		contentPane.add(textField);
		textField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 106, 186, 294);
		contentPane.add(scrollPane);

		JTree tree = new JTree();
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		scrollPane.setViewportView(tree);
		root = new DefaultMutableTreeNode("This PC");
		File diskC = new File("C:\\");
		DefaultMutableTreeNode DiskC = new DefaultMutableTreeNode("C:\\");
		createChildren(diskC, DiskC);
		root.add(DiskC);
		treemodel = new DefaultTreeModel(root);
		tree.setModel(treemodel);
		// remove Default Icon
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(200, 106, 699, 294);
		contentPane.add(scrollPane_1);

		table = new JTable();
		scrollPane_1.setViewportView(table);
		vD = new Vector();

		vT = new Vector();
		vT.add("Name");
		vT.add("Date modified");
		vT.add("Type");
		vT.add("Size");
		table.setModel(new DefaultTableModel(vD, vT));

		JButton btnNewButton = new JButton("Rename");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newname = JOptionPane.showInputDialog("input younewname");

				if (newname.equals("")) {
					JOptionPane.showConfirmDialog(null, "You need type your new name in this field !!");
				} else {

					String nameofFile = textField.getText() + "\\" + lblNewLabel.getText();

					String newpathname = textField.getText() + "\\" + newname;

					File file = new File(nameofFile);
					boolean renameto = file.renameTo(new File(newpathname));
					if (renameto) {
						JOptionPane.showConfirmDialog(null, "rename success");
						vD = createData(vD, textField.getText());
						table.setModel(new DefaultTableModel(vD, vT));
						table();

					} else
						JOptionPane.showConfirmDialog(null, "rename false");
				}
			}

		});
		btnNewButton.setBackground(new Color(230, 230, 250));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton.setBounds(767, 483, 110, 30);
		contentPane.add(btnNewButton);

		lblNewLabel = new JLabel("Name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(72, 429, 200, 20);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Date modified:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(330, 429, 150, 20);
		contentPane.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Type:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(560, 429, 80, 20);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("Size:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2_1.setBounds(723, 429, 80, 20);
		contentPane.add(lblNewLabel_2_1);

		JButton btnPaste = new JButton("Paste");
		btnPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String r = "";
				try {
					FileOutputStream fos = new FileOutputStream(textField.getText() + "\\" + y);

					int i;
					while ((i = bin.read()) != -1) {
						r += (char) i;
					}
					fos.write(r.getBytes());
					vD = createData(vD, textField.getText());
					table.setModel(new DefaultTableModel(vD, vT));
					table();
					JOptionPane.showMessageDialog(null, "Copy success");
					bin.close();
					fin.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		btnPaste.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnPaste.setBackground(new Color(230, 230, 250));
		btnPaste.setBounds(625, 483, 110, 30);
		contentPane.add(btnPaste);

		JButton btnCopy = new JButton("Copy");
		btnCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String r = "";
				y = lblNewLabel.getText();
//				Path r11 = Paths.get(textField.getText() + "\\" +y);
//				FileStore f = Files.getFileStore(r11);
				try {
					FileInputStream fin = new FileInputStream(textField.getText() + "\\" + y);
					bin = new BufferedInputStream(fin);
				} catch (Exception e1) {
					System.out.println(e1);
				}
			}
		});
		btnCopy.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCopy.setBackground(new Color(230, 230, 250));
		btnCopy.setBounds(475, 483, 110, 30);
		contentPane.add(btnCopy);

		JButton btnCopy_1 = new JButton("Search");
		btnCopy_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File directory = new File(textField.getText());

				// store all names with same name
				// with/without extension
				String[] flist = directory.list();
				int kt = 0;
				if (flist == null) {
					System.out.println("Empty directory.");
					JOptionPane.showMessageDialog(null, "Empty directory.");

				} else {

					String s = JOptionPane.showInputDialog("enter the file you want to find");
					for (int i = 0; i < flist.length; i++) {
						String filename = flist[i];
						if (filename.equalsIgnoreCase(s)) {
							System.out.println(filename + " found");
							JOptionPane.showMessageDialog(null, filename + " found");
							kt = 1;
							JOptionPane.showMessageDialog(null, "C:\\word\\"+ filename );
						}
					}
				}

				if (kt == 0) {
					System.out.println("File Not Found");
					JOptionPane.showMessageDialog(null, "File Not Found");
				}
			}
		});
		btnCopy_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCopy_1.setBackground(new Color(230, 230, 250));
		btnCopy_1.setBounds(174, 483, 110, 30);
		contentPane.add(btnCopy_1);

		JButton btnCopy_2 = new JButton("Open");
		btnCopy_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uu = textField.getText() + "\\" + lblNewLabel.getText();
				ReadFile rf = new ReadFile();
				rf.setVisible(true);
			}
		});
		btnCopy_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnCopy_2.setBackground(new Color(230, 230, 250));
		btnCopy_2.setBounds(25, 483, 110, 30);
		contentPane.add(btnCopy_2);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(230, 230, 250));
		menuBar.setBounds(0, 0, 909, 35);
		contentPane.add(menuBar);

		JMenu mnNewMenu = new JMenu("New");
		mnNewMenu.setBackground(new Color(248, 248, 255));
		mnNewMenu.setFont(new Font("Segoe UI", Font.BOLD, 15));
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Folder");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String m = JOptionPane.showInputDialog("Ten folder: ");
				File dir = new File(textField.getText()+"\\"+m);
				
				if (dir.mkdirs()) {
					System.out.println("Create directory " + dir.getAbsolutePath() + " success.");
					JOptionPane.showMessageDialog(null, "success");
					vD = createData(vD, textField.getText());
					table.setModel(new DefaultTableModel(vD, vT));
					table();
				}
			}
		});
		mntmNewMenuItem.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("File");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.setVisible(true);
				
				timerUpdate.start();
			}
		});
		mntmNewMenuItem_1.setFont(new Font("Segoe UI", Font.BOLD, 12));
		mnNewMenu.add(mntmNewMenuItem_1);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bb = "";
				if (lblNewLabel_2.getText().equals("Folder")) {
					bb = textField.getText();
				} else
					bb = textField.getText() + "\\" + lblNewLabel.getText();
				try {
					File f = new File(bb);
					if (true) {
						deleteDir(f);
						JOptionPane.showMessageDialog(null, "deleted");
						vD = createData(vD, textField.getText());
						table.setModel(new DefaultTableModel(vD, vT));
						table();
					} else
						JOptionPane.showMessageDialog(null, "can not delete");
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDelete.setBackground(new Color(230, 230, 250));
		btnDelete.setBounds(326, 483, 110, 30);
		contentPane.add(btnDelete);
		table();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int row = table.getSelectedRow();
				String name = table.getModel().getValueAt(row, 0).toString();
				String date = table.getModel().getValueAt(row, 1).toString();

				String type = table.getModel().getValueAt(row, 2).toString();
				String size = table.getModel().getValueAt(row, 3).toString();

				lblNewLabel.setText(name);
				lblNewLabel_1.setText(date);
				lblNewLabel_2.setText(type);
				lblNewLabel_2_1.setText(size);

				if (e.getClickCount() == 2) {
					Current_Folder = textField.getText() + "\\" + name;

					File file = new File(Current_Folder);
					if (file.isDirectory()) {
						vD = createData(vD, Current_Folder);
						table.setModel(new DefaultTableModel(vD, vT));
						table();
						textField.setText(Current_Folder);

					}

				}
			}
		});
		setLocationRelativeTo(null);
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				DefaultMutableTreeNode nodeselected = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				String name = nodeselected.getUserObject().toString();
				lblNewLabel.setText(name);
				TreePath treepath = e.getPath();
				System.out.println(treepath.toString());
				textField.setText(getTreepath(treepath));
				vD = createData(vD, textField.getText());
				table.setModel(new DefaultTableModel(vD, vT));
				table();

			}
		});

		setResizable(false);
		
		timerUpdate = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (f.isCheckOk()) {
					f.setCheckOk(false);
					String a = f.content();
					String m = JOptionPane.showInputDialog("Ten file: ");
					try {
						FileWriter f = new FileWriter(textField.getText()+ "\\" +m, true);				
							BufferedWriter bw = new BufferedWriter(f);
							PrintWriter p = new PrintWriter(bw);			
							p.println(a);
							p.close();
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
					f.stay();
					vD = createData(vD, textField.getText());
					table.setModel(new DefaultTableModel(vD, vT));
					table();
					timerUpdate.stop();
				}
			}
		});
			
	}

	public String getTreepath(TreePath path) {
		String value = path.toString();
		value = value.replace("[This PC, ", "");
		value = value.replace(", ", "\\");
		value = value.replace("]", "");

		return value;

	}

	public DefaultTableModel CreateTableData(DefaultTableModel tb, String folder) {
		if (tb.getRowCount() > 0) {
			for (int i = tb.getRowCount() - 1; i > -1; i--) {
				tb.removeRow(i);
			}
		}
		File file = new File(folder);
		File[] list = file.listFiles();
		for (File files : list) {
			String name = files.getName();
			String type = "";
			String size = "";
			if (files.isFile()) {
				type = "." + name.substring(name.lastIndexOf(".") + 1);
				long fileSizeInBytes = files.length();
				long fileSizeInKB = fileSizeInBytes / 1024;
				size = "" + fileSizeInKB + " kb";
			}
			if (files.isDirectory()) {
				type = "Folder";
			}
			SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String lastmodifiedate = date.format(files.lastModified());
			tb.addRow(new Object[] { name, lastmodifiedate, type, size });
		}
		return tb;
	}

	public Vector createData(Vector vD, String f) {
		vD.clear();
		File file = new File(f);
		File[] list = file.listFiles();
		for (File files : list) {
			String name = files.getName();
			String type = "";
			String size = "";
			if (files.isFile()) {
				type = "." + name.substring(name.lastIndexOf(".") + 1);
				long fileSizeInBytes = files.length();
				long fileSizeInKB = fileSizeInBytes / 1024;
				size = "" + fileSizeInKB + " kb";
			}
			if (files.isDirectory()) {
				type = "Folder";
			}
			SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String lastmodifiedate = date.format(files.lastModified());
			Vector t = new Vector();
			t.add(name);
			t.add(lastmodifiedate);
			t.add(type);
			t.add(size);
			vD.add(t);
		}
		return vD;
	}

	public String FileNode(File file) {
		String name = file.getName();
		if (name.equals("")) {
			return file.getAbsolutePath();
		} else {
			return name;
		}
	}

	public void createChildren(File fileRoot, DefaultMutableTreeNode node) {
		File[] files = fileRoot.listFiles();
		if (files == null)
			return;
		for (File file : files) {
			if (file.isDirectory()) {
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(FileNode(file));
				node.add(childNode);
				if (file.isDirectory())
					createChildren(file, childNode);
			}

		}
	}

	public void table() {
		table.setRowHeight(25);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		for (int i = 0; i < vT.size(); i++)
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		if (vT.size() != 0) {
			table.getColumnModel().getColumn(0).setPreferredWidth(110);
			table.getColumnModel().getColumn(1).setPreferredWidth(70);
			table.getColumnModel().getColumn(2).setPreferredWidth(10);
			table.getColumnModel().getColumn(3).setPreferredWidth(10);

		}
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
//		table.setFont(new Font("Gill Sans MT", Font.PLAIN, 15));
	}

	public static String nameFile() {
		return uu;
	}

	public void deleteDir(File file) {
		// neu file la thu muc thi xoa het thu muc con va file cua no
		if (file.isDirectory()) {
			// liet ke tat ca thu muc va file
			String[] files = file.list();
			for (String child : files) {
				File childDir = new File(file, child);
				if (childDir.isDirectory()) {
					// neu childDir la thu muc thi goi lai phuong thuc deleteDir()
					deleteDir(childDir);
				} else {
					// neu childDir la file thi xoa
					childDir.delete();
					System.out.println("File bi da bi xoa " + childDir.getAbsolutePath());
				}
			}
			// Check lai va xoa thu muc cha
			if (file.list().length == 0) {
				file.delete();
				System.out.println("File bi da bi xoa " + file.getAbsolutePath());
			}

		} else {
			// neu file la file thi xoa
			file.delete();
			System.out.println("File bi da bi xoa " + file.getAbsolutePath());

		}

	}
}
