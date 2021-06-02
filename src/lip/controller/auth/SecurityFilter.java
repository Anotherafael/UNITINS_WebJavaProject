package lip.controller.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import lip.model.User;

@WebFilter(filterName = "SecurityFilter", urlPatterns = { "/views/*" })
public class SecurityFilter implements Filter {

	private List<String> pagesWithPermissions;

	protected List<String> getPagesWithPermissions() {
		if (pagesWithPermissions == null) {
			pagesWithPermissions = new ArrayList<String>();
			pagesWithPermissions.add("/lip/views/user/index.xhtml");
			pagesWithPermissions.add("/lip/views/user/form.xhtml");
			pagesWithPermissions.add("/lip/views/music/index.xhtml");
			pagesWithPermissions.add("/lip/views/music/form.xhtml");
			pagesWithPermissions.add("/lip/views/post/index.xhtml");
			pagesWithPermissions.add("/lip/views/post/form.xhtml");
			pagesWithPermissions.add("/lip/views/auth/login.xhtml");
			pagesWithPermissions.add("/lip/views/home/index.xhtml");
			pagesWithPermissions.add("/lip/views/home/music.xhtml");
			pagesWithPermissions.add("/lip/views/home/music_info.xhtml");
			pagesWithPermissions.add("/lip/views/img-music");
		}
		return pagesWithPermissions;
	}

	protected void setPagesWithPermissions(List<String> pagesWithPermissions) {
		this.pagesWithPermissions = pagesWithPermissions;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest servletRequest = (HttpServletRequest) request;

		String url = servletRequest.getRequestURI();
		System.out.println(url);
		// ||
		if (url.equals("/lip/views/auth/login.xhtml") || url.equals("/lip/views/home/index.xhtml")
				|| url.equals("/lip/views/home/music.xhtml") || url.equals("/lip/views/home/music_info.xhtml")
				|| url.equals("/lip/views/auth/new_password.xhtml")
				|| url.equals("/lip/views/auth/reset_password.xhtml")
				|| url.equals("/lip/views/auth/validation.xhtml")) {
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = servletRequest.getSession(false);

		User user = null;
		if (session != null)
			user = (User) session.getAttribute("loggedInUser");

		if (user == null) {
			((HttpServletResponse) response).sendRedirect("/lip/views/home/index.xhtml");
		} else {
			if (url.equals("/lip/views/auth/login.xhtml"))
				((HttpServletResponse) response).sendRedirect("/lip/views/user/index.xhtml");
			if (getPagesWithPermissions().contains(url)) {
				chain.doFilter(request, response);
				return;
			} else {
				((HttpServletResponse) response).sendRedirect("/lip/views/home/index.xhtml");
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("SecurityFilter Iniciado.");
	}
}
