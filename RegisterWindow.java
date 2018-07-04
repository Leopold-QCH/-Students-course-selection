// _ooOoo_
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class RegisterWindow extends JFrame implements ActionListener,DocumentListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -249768398488338355L;
	Box boxh,boxv1,boxv2,boxh1,boxh2,boxv;
	JButton buttondl,buttonzc;
	JTextField User;
	JPasswordField PassWord;
	JTextArea JTextA; 
	jwcWin jwcwin;
	jsWin jswin;
	xsWin xswin;
	public RegisterWindow() {
		super();
		init();
	}
	public RegisterWindow(jwcWin jwcwin,jsWin jswin,xsWin xswin) {
		this();
		this.jwcwin=jwcwin;
		this.jswin=jswin;
		this.xswin=xswin;
	}
	Connection ct;
	public void setconnection(Connection ct ) {
		this.ct=ct;
	}
	private void init() {
		initwindow();
		initbox();
		initbutton();
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	private void initwindow() {
		this.setTitle("登录");
		this.setBounds(550,230,300,250);
		this.setLayout(new FlowLayout());
	}
	public void initbox() {
		boxv=Box.createVerticalBox();
		boxh=Box.createHorizontalBox();
		boxv1=Box.createVerticalBox();
		boxv2=Box.createVerticalBox();
		boxh1=Box.createHorizontalBox();
		boxh2=Box.createHorizontalBox();
		User=new JTextField(10);
		PassWord=new JPasswordField(10);
		JTextA=new JTextArea(1,10);
		buttondl=new JButton("登录");
		buttonzc=new JButton("注册");
		boxv1.add(new Label("用户"));
		boxv1.add(Box.createVerticalStrut(10));
		boxv1.add(new Label("密码"));
		boxv2.add(User);
		boxv2.add(Box.createVerticalStrut(10));
		boxv2.add(PassWord);
		boxh1.add(buttondl);
		boxh1.add(Box.createHorizontalStrut(40));
		boxh1.add(buttonzc);
		boxh2.add(new JScrollPane(JTextA));
		boxh.add(boxv1);
		boxh.add(Box.createHorizontalStrut(10));
		boxh.add(boxv2);
		boxv.add(boxh);
		boxv.add(Box.createVerticalStrut(20));
		boxv.add(boxh1);
		boxv.add(Box.createVerticalStrut(10));
		boxv.add(boxh2);
		buttondl.addActionListener(this);
		buttonzc.addActionListener(this);
		(User.getDocument()).addDocumentListener(this);
	}
	JRadioButton buttongl,buttonyh,buttonyk;
	ButtonGroup groupuser;
	Box boxuser;
	public void initbutton() {
		boxuser=Box.createHorizontalBox();
		buttongl=new JRadioButton("教务处");
		buttonyh=new JRadioButton("教师");
		buttonyk=new JRadioButton("学生");
		groupuser=new ButtonGroup();
		groupuser.add(buttongl);
		groupuser.add(buttonyh);
		groupuser.add(buttonyk);
		boxuser.add(buttongl);
		boxuser.add(buttonyh);
		boxuser.add(buttonyk);
		boxv.add(boxuser);
		this.add(boxv);
		buttongl.addActionListener(this);
		buttonyh.addActionListener(this);
		buttonyk.addActionListener(this);
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO 自动生成的方法存根
		changedUpdate(e);
	}
	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO 自动生成的方法存根
		changedUpdate(e);
	}
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO 自动生成的方法存根
		String str=User.getText();
		JTextA.setText(null);
		JTextA.append("用户名是："+str);
	}
	int buttonflag=0;
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		Object src=e.getSource();
		if(buttongl==src) {
			JTextA.setText(null);
			JTextA.append("欢迎教务处管理员登录");
			buttonflag=1;
		}
		else if(buttonyh==src) {
			JTextA.setText(null);
			JTextA.append("欢迎老师登录");
			buttonflag=2;
		}
		else if(buttonyk==src) {
			JTextA.setText(null);
			JTextA.append("欢迎学生登录");
			buttonflag=3;
		}
		else if(buttondl==src) {
			String userdl=User.getText();
			char[] pw=PassWord.getPassword();
			String passworddl=new String(pw);
				if(buttonflag==1) {
					if(userdl.equals("sa")&&passworddl.equals("111")) {
					jwcwin.setVisible(true);
					this.setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(this, "账号或者密码错误","警告", JOptionPane.WARNING_MESSAGE);
					}
				}
				else if(buttonflag==2) {
					Statement findpw;
					String pwget=null;
					jswin.getuser(userdl);
					try {
						findpw = ct.createStatement();
						ResultSet rs=findpw.executeQuery("select 密码\r\n" + 
								" from teacher\r\n" + 
								" where 工号="+userdl);
						while(rs.next()) {
							pwget=rs.getString(1);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if(passworddl.equals(pwget)) {
						jswin.setVisible(true);
						this.setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(this, "账号或者密码错误","警告", JOptionPane.WARNING_MESSAGE);
					}
						
				}
				else if(buttonflag==3) {
					Statement findpw;
					String pwget=null;
					xswin.getuser(userdl);
					xswin.setlabel();
					try {
						findpw = ct.createStatement();
						ResultSet rs=findpw.executeQuery("select 密码\r\n" + 
								" from stu\r\n" + 
								" where 学号="+userdl);
						while(rs.next()) {
							pwget=rs.getString(1);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if(passworddl.equals(pwget)) {
						xswin.setVisible(true);
						this.setVisible(false);
					}
					else {
						JOptionPane.showMessageDialog(this, "账号或者密码错误","警告", JOptionPane.WARNING_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(this, "请先选择用户类别！","提示",JOptionPane.WARNING_MESSAGE);	
				}
		}
		else if(buttonzc==src) {
			if(buttonflag==0) {
				JOptionPane.showMessageDialog(this, 
						"请先选择用户类别！","提示",JOptionPane.WARNING_MESSAGE);	
			}
			if(buttonflag==1||buttonflag==2) {
				JOptionPane.showMessageDialog(this, 
						"管理和教师账号无法注册","警告",JOptionPane.WARNING_MESSAGE);
			}
			else if(buttonflag==3){
				String userzc=JOptionPane.showInputDialog
						(this,"请输入学号/工号","注册", JOptionPane.PLAIN_MESSAGE);
				String password1=JOptionPane.showInputDialog
						(this,"请输入密码","注册", JOptionPane.PLAIN_MESSAGE);
				String password2=JOptionPane.showInputDialog
						(this,"请再次输入密码","注册", JOptionPane.PLAIN_MESSAGE);
				if(!password1.equals(password2)) {
					JOptionPane.showMessageDialog
						(this,"两次密码不一致，请重新注册！","提示",JOptionPane.WARNING_MESSAGE);
					}
				JOptionPane.showMessageDialog(this, 
						"等待教务处审批完善信息","警告",JOptionPane.WARNING_MESSAGE);
				try {
					Statement findcjd=ct.createStatement();
					findcjd.executeQuery("insert into stu  values("+userzc+",'待完善','无','待完善','待完善','待完善',"+password1+",'未审核')");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		}
	}
}
