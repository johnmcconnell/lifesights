package com.mcconnell.blog;

import java.io.IOException;

import javax.servlet.http.*;


@SuppressWarnings("serial")
public class PrimitiveObservationsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String entryId = req.getParameter("e");
		resp.setContentType("text/plain");
		if (entryId != null) {
			resp.sendRedirect(entryId);
		}
	}
}
