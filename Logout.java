import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import file.Storeing;
public class Logout extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String username = request.getHeader("Authorization");
		String tok = request.getHeader("X-CSRF-TOKEN");
		Storeing sto=new Storeing();
		
			sto.clear(username);
		    out.print("success");
		}
	}