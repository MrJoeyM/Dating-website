package LinkedIn.Login.connection;
/**
 * �������ݿ�
 * @author Seavan_CC
 *
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnection {
	private Connection con;
	private String user = "Vincent";
	private String password = "752340690";
	private String className = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1522:orcl";
	//�û��������ݿ�����
	public OracleConnection(){
		try {
			Class.forName(className);
			System.out.println("�������ݿ������ɹ���");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("�������ݿ�����ʧ�ܣ�");
			e.printStackTrace();
		}
	}
	//���ڻ�ȡoracle���ݿ������
	public Connection getCon()
	{
		try {
			con = DriverManager.getConnection(url,user,password);
			System.out.println("���ݿ����ӳɹ�");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(con);
			System.out.println("���ݿ�����ʧ��");
			con = null;
			e.printStackTrace();
		}
		return con;
	}
	//�û��ر����ݿ�
	public void closed(){
		try {
			if(con != null)
			{
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("�ر�con����ʧ�ܣ�");
			e.printStackTrace();
		}
	}
}
