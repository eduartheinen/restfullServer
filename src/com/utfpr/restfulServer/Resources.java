package com.utfpr.restfulServer;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.utfpr.restfulServer.User.User;

@Path("/")
public class Resources {
	@GET
	@Path("hello")
	@Produces(MediaType.TEXT_HTML)
	// http://localhost:8080/restfulServer/hello #html
	public String sayHtmlHello() {
		return "<html><body><h1>Hello Jersey</h1></body></html>";
	}

	@GET
	@Path("users")
	@Produces(MediaType.APPLICATION_JSON)
	// http://localhost:8080/restfulServer/users #json
	public String usersIndex() {
		return Utility.constructJSON("users index - vai ter?", true);
	}

	@POST
	@Path("users")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// http://localhost:8080/restfulServer/users #POST
	public void createUser(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("email") String email,
			@Context HttpServletResponse servletResponse) throws IOException,
			ClassNotFoundException, SQLException {

		User user = new User(null, username, password, email);

		user.create();
		servletResponse
				.sendRedirect("http://localhost:8080/restfulServer/users");
	}

	@GET
	@Path("setup")
	@Produces(MediaType.TEXT_HTML)
	// http://localhost:8080/restfulServer/setup #html
	public String setup() {
		try {
			ProjectSetup.setupDatabase();
			return "<html><body><p> success! </p></body></html>";
		} catch (Exception e) {
			return "<html><body><p> error: " + e.getMessage()
					+ "</p></body></html>";
		}
	}
}
