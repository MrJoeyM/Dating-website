package LinkedIn.Login.Action;

import java.io.IOException;
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
 * ��ѯ���ѵ�Servlet
 * @author Seavan_CC
 *
 */
public class SearchAction extends HttpServlet {

	public SearchAction() {
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
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		//��ת�������б���ҳ
		response.sendRedirect(path+"/FriendList.jsp");
		String stu = request.getParameter("_search");
		HttpSession session= request.getSession();
		Dao dao = new Dao();
		List<User> search_list=null;
		List<Map<String,Object>> params = new ArrayList<Map<String,Object>>();
    	Map<String,Object> map = new HashMap<String, Object>();
	    if(stu.equals(""))
	    {
	    	//�����ѯ����Ϊ�գ����ò�ѯ���session����Ϊ��
	    	search_list = new ArrayList<User>();
	    	request.getSession().setAttribute("search_list",search_list);
	    	List<User> clearList = (List<User>) request.getSession().getAttribute("search_list");
	    	for(int i = 0;i < clearList.size();++i){
	    		clearList.remove(i);
	    	}
	    }
	    if(!stu.equals("") )
	    {
	    	//�ȸ���ѧ�Ž��в�ѯ�û�
	    	map.put("name", "stuNumber");
	    	map.put("rela", "=");
	    	map.put("value", "'"+stu+"'");//ע�����˫��������ĵ����ţ�����sql��������ӵ����Ų���
	    	params.add(map);			
	    	try {
				search_list = dao.search(params);
				if(search_list.size() == 0)
				{
					//�������ֲ�ѯ�û�
					map.put("name", "stuName");
			    	map.put("rela", "=");
			    	map.put("value", "'"+stu+"'");//ע�����˫��������ĵ����ţ�����sql��������ӵ����Ų���
			    	params.add(map);			
			    	try {
						search_list = dao.search(params);
						if(search_list==null)
						{
						}else{
						    request.getSession().setAttribute("search_list",search_list);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					//��ѯ����º����б�
					request.getSession().setAttribute("search_list",search_list);
				}		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	    	
	    }
	}

	public void init() throws ServletException {
		super.init();
	}

}
