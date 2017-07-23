package LinkedIn.Login.Action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import LinkedIn.Login.Dao.Dao;
import LinkedIn.Login.JavaBean.Blog;
/**
 * ��ȡ���͵�Servlet
 *
 */
public class BlogAction extends HttpServlet {

	public BlogAction() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getContextPath();
		Dao dao=new Dao();
		System.out.println("dasdh");
		String stuNumber = request.getSession().getAttribute("stuNumber").toString();
		//��ѯ�û�����
		ArrayList<Blog> myBlogs=dao.getBlog(stuNumber);
		//�趨���ͱ�
		request.getSession().setAttribute("blogLists", myBlogs);
		response.sendRedirect(path+"/BlogPage.jsp");
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
	}

	
	public void init() throws ServletException {
	}

}
