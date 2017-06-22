package ua.nure.kramarenko.SummaryTask4.web.tags;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.IOException;

public class HelloTag extends SimpleTagSupport {

  public void doTag() throws JspException, IOException {
		// <%
		// String site = new String("controller?command=productList");
		// response.setStatus(response.SC_MOVED_TEMPORARILY);
		// response.setHeader("Location", site);
		// %>
    JspWriter out = getJspContext().getOut();
    out.println("Hello Custom Tag!");
    
    
  }
}