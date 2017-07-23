package LinkedIn.Login.Dao;
/**
 * �����ݿ�Ĳ���
 * @author Seavan_CC
 *
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sun.corba.se.impl.protocol.giopmsgheaders.ReplyMessage;
import com.sun.crypto.provider.RSACipher;


import sun.security.util.Password;
import LinkedIn.Login.JavaBean.*;
import LinkedIn.Login.connection.OracleConnection;

public class Dao {
	private OracleConnection oc = new OracleConnection();
	public Dao()
	{
	}
	//ʵ���û�ע��
	public boolean register(User user)
	{
		try {
			Connection	conn = oc.getCon();
			String sql = "insert into UserStu " +
					" values(?,?,?,?,?,?,?,?,?) ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getStuNumber());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getStuName());
			ps.setString(4, user.getMajor());
			ps.setString(5, user.getGrade());
			ps.setString(6,user.getSex());
			ps.setInt(7, user.getAge());
			ps.setString(8, user.getHobby());
			ps.setString(9, user.getHeadPostrait());
			ps.execute();
			System.out.println("ע��ɹ���");
			return true;
		} catch (SQLException e) {
			System.out.println("ע��ʧ�ܣ�");
			e.printStackTrace();
			return false;
		}
	}
	//ʵ���û���¼
		public User login(String stuNumber)
		{
			try {
				Connection	conn = oc.getCon();
				String sql = "select * from UserStu " +
						" where stuNumber=? ";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1,stuNumber);
				ResultSet rs = ps.executeQuery();
				User user = null;
				while(rs.next())
				{
					user = new User();
					user.setStuNumber(rs.getString("stuNumber"));
					user.setPassword(rs.getString("password"));
					user.setStuName(rs.getString("stuName"));
					user.setAge(Integer.parseInt(rs.getString("age")));
					user.setSex(rs.getString("sex"));
					user.setMajor(rs.getString("major"));
					user.setGrade(rs.getString("grade"));
					user.setHeadPostrait(rs.getString("headPostrait"));
					user.setHobby(rs.getString("hobby"));
				}
				System.out.println("��¼�ɹ���");
				return user;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("��¼ʧ�ܣ�");
				e.printStackTrace();
				return null;
			}
		}
	
	//ʵ�ֲ�ѯ
		public List<User> query(List<Map<String,Object>> params) throws SQLException{
			//3��ͨ�����ݿ�����Ӳ������ݿ⣬ʵ����ɾ�Ĳ�
			StringBuilder sb = new StringBuilder();
			sb.append("select * from UserStu where stuNumber in (select stuNumber2 from"
					+ " Contacts where 1=1 ");
		
			if(params != null && params.size()>0)
			{
				for (int i = 0; i < params.size(); i++) {
					Map<String,Object> map=params.get(i);
					sb.append(" and "+map.get("name")+" "
							+map.get("rela")+" "+map.get("value")+")");
							
				}
			}
			Connection	conn = oc.getCon();
			java.sql.PreparedStatement ptmt = conn.prepareStatement(sb.toString());

			System.out.println(sb.toString());
			ResultSet rs = ptmt.executeQuery();
			
			List<User> gs = new ArrayList<User>();
			User user = null;
			while(rs.next())
			{
				user = new User();
				/*private String stuNumber;
				private String password;
				private String stuName;
				private String major;
				private String grade;
				private String sex;
				private int age;
				private String hobby;*/
				user.setStuNumber(rs.getString("stuNumber"));
				user.setStuName(rs.getString("stuName"));
				user.setPassword(rs.getString("password"));
				user.setMajor(rs.getString("major"));
				user.setGrade(rs.getString("grade"));
				user.setSex(rs.getString("sex"));
				user.setAge(rs.getInt("age"));
				user.setHobby(rs.getString("hobby"));
				user.setHeadPostrait(rs.getString("headPostrait"));
				gs.add(user);
			}
			return gs;
		}
		//ʵ�ְ�ѧ�Ų�ѯ����
		public List<User> search(String stuNumber)
		{
			try {
				Connection	conn = oc.getCon();
				String sql = "select * from UserStu " +
						" where stuNumber=? ";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1,stuNumber);
				ResultSet rs = ps.executeQuery();
				User user = null;
				while(rs.next())
				{
					user = new User();
					user.setStuNumber(rs.getString("stuNumber"));
					user.setPassword(rs.getString("password"));
				}
				System.out.println("��¼�ɹ���");
				return null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("��¼ʧ�ܣ�");
				e.printStackTrace();
				return null;
			}
		}
		
		//ɾ������
		public boolean deleteFriend(String stuNumber)
		{
			try {
				Connection	conn = oc.getCon();
				String sql = "delete from Contacts " +
						" where stuNumber2 = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, stuNumber);
				ps.execute();
				System.out.println("ɾ�����ѳɹ���");
				return true;
			} catch (SQLException e) {
				System.out.println("ɾ������ʧ�ܣ�");
				e.printStackTrace();
				return false;
			}
		}
		//ʵ�ְ���ͬ������ѯ����
		public List<User> search(List<Map<String,Object>> params) throws SQLException
		{
				Connection	conn = oc.getCon();
				//3��ͨ�����ݿ�����Ӳ������ݿ⣬ʵ����ɾ�Ĳ�
				System.out.println("hello");
				StringBuilder sb = new StringBuilder();
				sb.append("select * from UserStu where 1=1 ");//��ʵ�ʿ��������о���ʹ��ʹ��һ����������1=1��sql��sql����������
				if(params != null && params.size()>0)
				{
					for (int i = 0; i < params.size(); i++) {
						Map<String,Object> map=params.get(i);
						sb.append(" and "+map.get("name")+" "
								+map.get("rela")+" "+map.get("value")+"");
								
					}
				}
				java.sql.PreparedStatement ptmt = conn.prepareStatement(sb.toString());
				ResultSet rs = ptmt.executeQuery();
				
				List<User> gs = new ArrayList<User>();
				User user = null;
				while(rs.next())
				{
					user = new User();
					user.setStuNumber(rs.getString("stuNumber"));
					user.setStuName(rs.getString("stuName"));
					user.setPassword(rs.getString("password"));
					user.setMajor(rs.getString("major"));
					user.setGrade(rs.getString("grade"));
					user.setSex(rs.getString("sex"));
					user.setAge(rs.getInt("age"));
					user.setHeadPostrait(rs.getString("headPostrait"));
					user.setHobby(rs.getString("hobby"));
					gs.add(user);
				}
				return gs;
		}
		//ʵ�������ϵ��
		public boolean add(String number1,String number2) throws SQLException
		{
			try{
				Connection	conn = oc.getCon();
				String string="insert into contacts values('"+number1+"','"+number2+"')";
				System.out.println(string);
				java.sql.PreparedStatement ptmt = conn.prepareStatement(string);

				ptmt.executeQuery();
				return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}
		
		//ʵ�ַ�����
		public boolean publish(Blog blog){
			Connection conn=oc.getCon();
			try {
				String sql="insert into blog "+
						"values(blog_number.nextval,?,?,?,?)";
				PreparedStatement ps=conn.prepareStatement(sql);
				ps.setString(1, blog.getStuNumber());
				ps.setString(2, blog.getBlogText());
				ps.setString(3, blog.getPictureUrl());
				ps.setTimestamp(4, blog.getTime());
				ps.execute();
				System.out.println("�����ɹ���");
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("����ʧ�ܣ�");
				return false;
			}
			
		}


		//��ȡ�Լ����͵Ĳ���
		public  ArrayList<Blog> getBlogs(String sNumber){
			Connection conn=oc.getCon();
			try {
				String sql="select stunumber,text,picture,time from blog where stuNumber=? order by time desc ";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1,sNumber);
				ResultSet rs=ps.executeQuery();
				ArrayList<Blog> allBlogs=new ArrayList<Blog>();
				while(rs.next()){
					String stuNumber=rs.getString("stunumber");
					String blogText=rs.getString("text");
					String pictureUrl=rs.getString("picture");
					java.sql.Timestamp timeStamp=rs.getTimestamp("time");
					Blog blog=new Blog();
					blog.setStuNumber(stuNumber);
					blog.setBlogText(blogText);
					blog.setPictureUrl(pictureUrl);
					blog.setTime(timeStamp);
					allBlogs.add(blog);
				}
				System.out.println("�����б������ɣ�");
				return allBlogs;
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			}
		}
		//��ȡ�Լ��ͺ��ѵĲ���
		public  ArrayList<Blog> getBlog(String sNumber){
			Connection conn=oc.getCon();
			Statement stmt;
			try {
				stmt = conn.createStatement();
				String sql="select stunumber,text,picture,time from blog where stuNumber="
						+" '"+sNumber+"' or stuNumber in(select stuNumber2 from Contacts where stuNumber1 ='"+sNumber+"')"+" order by time desc ";
				ResultSet rs=stmt.executeQuery(sql);
				ArrayList<Blog> allBlogs=new ArrayList<Blog>();
				while(rs.next()){
					String stuNumber=rs.getString("stunumber");
					String blogText=rs.getString("text");
					String pictureUrl=rs.getString("picture");
					java.sql.Timestamp timeStamp=rs.getTimestamp("time");
					Blog blog=new Blog();
					blog.setStuNumber(stuNumber);
					blog.setBlogText(blogText);
					blog.setPictureUrl(pictureUrl);
					blog.setTime(timeStamp);
					allBlogs.add(blog);
				}
				System.out.println("�����б������ɣ�");
				return allBlogs;
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			}
		}
		//�޸ĸ�������
		public boolean modifyPerProfile(User user,String stuNumber)
		{
			try {
				Connection	conn = oc.getCon();
				String sql = "update UserStu " +
						" set sex=?,age=?,major=?,grade=?,hobby=? where stuNumber=?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, user.getSex());
				ps.setInt(2, user.getAge());
				ps.setString(3,user.getMajor());
				ps.setString(4,user.getGrade());
				ps.setString(5,user.getHobby());
				ps.setString(6, stuNumber);
				ps.execute();
				System.out.println("���������޸ĳɹ���");
				ps.close();
				return true;
			} catch (SQLException e) {
				System.out.println("���������޸�ʧ�ܣ�");
				e.printStackTrace();
				return false;
			}
		}
		
		//�޸�����
		public boolean modifyPassword(String password,String stuNumber)
		{
			try {
				Connection	conn = oc.getCon();
				String sql = "update UserStu " +
						" set password=? where stuNumber=?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, password);
				ps.setString(2, stuNumber);
				ps.execute();
				System.out.println("�����޸ĳɹ���");
				return true;
			} catch (SQLException e) {
				System.out.println("�����޸�ʧ�ܣ�");
				e.printStackTrace();
				return false;
			}
		}
		//��ѯԭ������
		public String searchPass(String stuNumber)
		{
			try {
				Connection	conn = oc.getCon();
				String sql = "select password from UserStu " +
						" where stuNumber=? ";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1,stuNumber);
				ResultSet rs = ps.executeQuery();
				String password = "";
				while(rs.next())
				{
					password = rs.getString("password");
				}
				System.out.println("��ѯ����ɹ���");
				return password;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("��ѯ����ʧ�ܣ�");
				e.printStackTrace();
				return null;
			}
		}
		//��ѯԭ��ѧ������
		public String searchName(String stuNumber)
		{
			try {
				Connection	conn = oc.getCon();
				String sql = "select stuName from UserStu " +
						" where stuNumber=? ";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1,stuNumber);
				ResultSet rs = ps.executeQuery();
				String stuName = "";
				while(rs.next())
				{
					stuName = rs.getString("stuName");
				}
				System.out.println("��ѯѧ�������ɹ���");
				return stuName;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("��ѯѧ������ʧ�ܣ�");
				e.printStackTrace();
				return null;
			}
		}
		
		/**
		 * �õ�����   �����»ظ�������������
		 */
		public ArrayList<Frog> getFrogs(String type){

			//���ȵõ����е���������
			ArrayList<Frog> al = new ArrayList<Frog>() ;
			try{
				String sql = "select * from Frog where type=? order by frogDate desc" ;
				Connection	conn = oc.getCon();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, type);
				ResultSet rs=ps.executeQuery();
				System.out.println(sql);
				while(rs.next()){
					String frogNumber = rs.getString("frogNumeber");
					String frogTitle = rs.getString("frogTitle");
					String frogContent = rs.getString("frgContent");
					java.sql.Timestamp frogDate = rs.getTimestamp("frogDate");
					int replyContent = rs.getInt("replyCcount");
					String stuNumber  = rs.getString("stuNumber");
					java.sql.Timestamp newReply = rs.getTimestamp("newReply");
					Frog frog = new Frog();
					frog.setFrogNumber(frogNumber);
					frog.setForgTitle(frogTitle);
					frog.setFrogContent(frogContent);
					frog.setFrogDate(frogDate);
					frog.setReplyCount(replyContent);
					frog.setStuNumber(stuNumber);
					frog.setNewReply(newReply);
					frog.setType(type);
					al.add(frog);
				}
				return al;
			}catch(Exception ex){
			  return null ;
			}	
	
			
		}
			
		/**
		 * �õ�ĳһ��������ϸ��Ϣ
		 */
		public Frog getFrog(String frogNumber){
			
			String sql ="select * from Frog where frogNumeber = ?";
			try{
				System.out.println("bwueihweuibu");
				Connection	conn = oc.getCon();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, frogNumber);
				ResultSet rs=ps.executeQuery();
				Frog frog = new Frog();
				if(rs.next()){
					String frogTitle = rs.getString("frogTitle");
					String frogContent = rs.getString("frgContent");
					java.sql.Timestamp frogDate = rs.getTimestamp("frogDate");
					int replyContent = rs.getInt("replyCcount");
					String stuNumber  = rs.getString("stuNumber");
					java.sql.Timestamp newReply = rs.getTimestamp("newReply");
					String type = rs.getString("type");
					frog.setFrogNumber(frogNumber);
					frog.setForgTitle(frogTitle);
					frog.setFrogContent(frogContent);
					frog.setFrogDate(frogDate);
					frog.setReplyCount(replyContent);
					frog.setStuNumber(stuNumber);
					frog.setNewReply(newReply);
					frog.setType(type);
				}
				return frog;
				
			}catch(Exception ex){
				return null;
			}	
		}
		/**
		 * �õ�ĳ�����ӵĻظ���Ϣ
		 */
		public ArrayList<FrogReply> getReply(String frogNumber){
			ArrayList<FrogReply> al = null ;
			String sql = "select * from FrogReply where frogNumeber=? order by replyDate desc" ;
			try{
				Connection	conn = oc.getCon();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, frogNumber);
				ResultSet rs=ps.executeQuery();
				al = new ArrayList<FrogReply>() ;
				while(rs.next()){
					FrogReply reply = new FrogReply();
					reply.setFrogNumber(frogNumber);
					reply.setStuNumber(rs.getString("stuNumber"));
					reply.setReplyContent(rs.getString("replyContent"));
					reply.setReplyDate(rs.getTimestamp("replyDate"));
					al.add(reply) ;
				}
	 			return al;
			}catch(Exception ex){
				return null;
			}
		}
		/**
		 * ��ӻظ�
		 */
		public boolean addReply(FrogReply reply){
			boolean flag  = false ;
			try{
				//�����µĻظ�
				String sql="insert into frogReply values(?,?,?,?)" ;
				Connection conn=oc.getCon();
				PreparedStatement ps=conn.prepareStatement(sql);
				ps.setString(1, reply.getFrogNumber());
				ps.setString(2,reply.getStuNumber());
				ps.setString(3, reply.getReplyContent());
				ps.setTimestamp(4,reply.getReplyDate());
				ps.execute();
				ps.close() ;
				flag = true;
				return flag;
			}catch(Exception ex){
				return flag ;
			}
		}
		/**
		 * ����µ�����
		 */
		public boolean addFrog(Frog frog){
			String sql = "insert into frog values(frog_number.nextval,?,?,?,0,?,?,?)" ;
			try{
				Connection conn=oc.getCon();
				PreparedStatement ps=conn.prepareStatement(sql);
				ps.setString(1,frog.getForgTitle());
				ps.setString(2, frog.getFrogContent());
				ps.setTimestamp(3, frog.getFrogDate());
				ps.setString(4, frog.getStuNumber());
				ps.setTimestamp(5, frog.getNewReply());
				ps.setString(6, frog.getType());
				ps.execute();
				return true;
			}catch(Exception ex){
				return false ;
			}
		}
		/**
		 * ��ȡ�ظ����ӵ�����
		 */
		public int replyCount(String frogNumber)
		{
			int count = 0;
			String sql = "select count(*) from FrogReply where frogNumeber=?" ;
			try{
				Connection	conn = oc.getCon();
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, frogNumber);
				ResultSet rs=ps.executeQuery();
				rs.next() ;
				count = rs.getInt(1) ;
	 			return count;
			}catch(Exception ex){
				return count;
			}
		}
		
		//�޸ĸ�������
		public boolean modifyHead(String stuNumber,String head)
		{
			try {
				Connection	conn = oc.getCon();
				String sql = "update UserStu " +
								" set headPostrait=? where stuNumber=?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1,head);
				ps.setString(2, stuNumber);
				ps.execute();
				System.out.println(sql);
				System.out.println("ͷ���޸ĳɹ���");
				ps.close();
				return true;
				} catch (SQLException e) {
				System.out.println("ͷ���޸�ʧ�ܣ�");
				e.printStackTrace();
				return false;
			}
		}
}