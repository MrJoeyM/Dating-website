package LinkedIn.Login.Action;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import LinkedIn.Login.Dao.Dao;
import LinkedIn.Login.JavaBean.User;
/**
 * ��Ӻ��ѵ�Servlet
 * @author Seavan_CC
 *
 */
public class AddAction extends HttpServlet {

	public AddAction() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��������Ӻ���
		String path = request.getContextPath();
		response.sendRedirect(path+"/servlet/FriendListAction");
		HttpSession session= request.getSession();
		String stuNumber1=(session.getAttribute("stuNumber")).toString();
		//��ȡ��ӵĺ��ѵ�ѧ��
		String stuNumber2=request.getParameter("addNumber");
		System.out.println(stuNumber1);
		System.out.println(stuNumber2);
		User user = new User();
		List<User> list = null;
		//�ж��������ĺ����б��Ƿ�Ϊ��
		if(session.getAttribute("friendli") == null || ((List<User>)session.getAttribute("friendli")).get(0).getStuName().equals("test"))
		{
			
		}else{
		
			list =(List<User>)session.getAttribute("friendli");
			for(int i=0;i<list.size();i++){
				user=list.get(i); 
				if(user.getStuNumber().equals(stuNumber2))
				{
					response.setContentType("text/html; charset=utf-8");
					PrintWriter writer = response.getWriter();
					writer.write("<script>alert('���û����������ѣ�');</script>");
					writer.close();
					writer.flush();
					return;
				}
			}
		}
		System.out.println("hello");
		Dao dao = new Dao();
		try {
			//��Ӻ��ѵĲ���
			dao.add(stuNumber1,stuNumber2);List<Map<String,Object>> params = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String, Object>();
			
			map.put("name", "stuNumber1");
			map.put("rela", "=");
			map.put("value", stuNumber1);//ע�����˫��������ĵ����ţ�����sql��������ӵ����Ų���
			params.add(map);
			List<User> friend_list;
			try {
				friend_list = dao.query(params);
				if(friend_list.isEmpty())
				{
				}else{
					//���º����б�
					request.getSession().setAttribute("list",friend_list);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void init() throws ServletException {
		super.init();
	}

}
