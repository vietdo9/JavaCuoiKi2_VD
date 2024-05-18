package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Client extends javax.swing.JFrame {

	private Thread thread;
	private BufferedWriter os;
	private BufferedReader is;
	private Socket socketOfClient;
	private List<String> onlineList;
	private int id;
	private String userName;

	public Client() {
		initComponents();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		jTextArea1.setEditable(false);
		jTextArea2.setEditable(false);
		onlineList = new ArrayList<>();
		userName = JOptionPane.showInputDialog(this, "Nhập tên của bạn: ");
		setUpSocket();
		id = -1;
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {

		jPanel3 = new javax.swing.JPanel();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jPanel1 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTextArea2 = new javax.swing.JTextArea();
		jPanel2 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jTextField1 = new javax.swing.JTextField();
		jButton1 = new javax.swing.JButton();
		jComboBox1 = new javax.swing.JComboBox<>();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 100, Short.MAX_VALUE));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 100, Short.MAX_VALUE));

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jTextArea2.setColumns(20);
		jTextArea2.setRows(5);
		jScrollPane2.setViewportView(jTextArea2);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE));

		jTabbedPane1.addTab("Danh sách online", jPanel1);

		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane1.setViewportView(jTextArea1);

		jButton1.setText("Gửi");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jComboBox1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jComboBox1ActionPerformed(evt);
			}
		});

		jLabel1.setText("Chọn người nhận");

		jLabel2.setText("Nhập tin nhắn");

		jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel3.setText("{Người nhận}");

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
						.addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jTextField1)
						.addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel2Layout.createSequentialGroup()
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 112,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 127,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addGap(10, 10, 10).addComponent(jLabel3)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jLabel1)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jLabel2)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jButton1).addContainerGap(27, Short.MAX_VALUE)));

		jTabbedPane1.addTab("Nhắn tin", jPanel2);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jTabbedPane1));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jTabbedPane1));

		pack();
	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		String messageContent = jTextField1.getText();
		if (messageContent.isEmpty()) {
			JOptionPane.showMessageDialog(rootPane, "Bạn chưa nhập tin nhắn");
			return;
		}
		if (jComboBox1.getSelectedIndex() == 0) {
			try {
				write("send-to-global" + "," + messageContent + "," + this.id);
				jTextArea1.setText(jTextArea1.getText() + "Bạn: " + messageContent + "\n");
				jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
			}
		} else {
			try {

				String selectedItem = (String) jComboBox1.getSelectedItem();
				System.out.println(selectedItem);

				write("send-to-person" + "," + messageContent + "," + this.id + "," + userName + "," + selectedItem);
				jTextArea1.setText(jTextArea1.getText() + "Bạn (tới " + selectedItem + "): " + messageContent + "\n");
				jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(rootPane, "Có lỗi xảy ra");
			}
		}
		jTextField1.setText("");
	}

	private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
		if (jComboBox1.getSelectedIndex() == 0) {
			jLabel3.setText("Global");
		} else {
			jLabel3.setText("Đang nhắn với " + jComboBox1.getSelectedItem());
		}
	}

	private void setUpSocket() {
		try {
			thread = new Thread() {
				@Override
				public void run() {
					try {
						socketOfClient = new Socket("localhost", 7777);
						System.out.println("Kết nối thành công!");
						os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
						is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

						// Gửi tên người dùng tới máy chủ
						write("set-name," + userName);

						String message;
						while (true) {
							message = is.readLine();
							if (message == null) {
								break;
							}
							String[] messageSplit = message.split(",");
							if (messageSplit[0].equals("get-id")) {
								setID(Integer.parseInt(messageSplit[1]));
							}
							if (messageSplit[0].equals("get-username")) {
								setIDTitle(messageSplit[1]);
							}
							if (messageSplit[0].equals("update-online-list")) {
								onlineList = new ArrayList<>();
								String online = "";
								String[] onlineSplit = messageSplit[1].split("-");
								for (String onlineUser : onlineSplit) {
									if (!onlineUser.equals(userName)) {
										onlineList.add(onlineUser);
										online += onlineUser + " đang online\n";
									}
								}
								jTextArea2.setText(online);
								updateCombobox();
							}
							if (messageSplit[0].equals("global-message")) {
								jTextArea1.setText(jTextArea1.getText() + messageSplit[1] + "\n");
								jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
							}
						}
					} catch (UnknownHostException e) {
						return;
					} catch (IOException e) {
						return;
					}
				}
			};
			thread.start();
		} catch (Exception e) {
		}
	}

	private void write(String message) throws IOException {
		os.write(message);
		os.newLine();
		os.flush();
	}

	private void setID(int id) {
		this.id = id;
	}

	private void setIDTitle(String username) {
		this.setTitle(username + " client");
	}

	private void updateCombobox() {
		jComboBox1.removeAllItems();
		jComboBox1.addItem("Global");
		for (String user : onlineList) {
			jComboBox1.addItem(user);
		}
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Client().setVisible(true);
			}
		});
	}

	private javax.swing.JButton jButton1;
	private javax.swing.JComboBox<String> jComboBox1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextArea jTextArea2;
	private javax.swing.JTextField jTextField1;
}
//