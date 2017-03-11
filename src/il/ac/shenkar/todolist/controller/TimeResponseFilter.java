/**
 * Filter calculate response time and print to client 
 */

package il.ac.shenkar.todolist.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * Servlet Filter implementation class TimeResponseFilter
 */
@WebFilter("/todolist/*")
public class TimeResponseFilter implements Filter {
	private FilterConfig filterConfig;
    /**
     * Default constructor. 
     */
    public TimeResponseFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	
		long start = System.currentTimeMillis();  
		chain.doFilter(request, response);
	 	long elapsedTime = System.currentTimeMillis() - start;	
	 	response.getWriter().println("<p class='time'>response time: " + elapsedTime + " ms</p>");
		
		}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		 this.filterConfig = filterConfig;
	}

}
