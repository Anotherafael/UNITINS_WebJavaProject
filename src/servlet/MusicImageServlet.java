package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lip.util.Util;

@WebServlet("/views/img-music")
public class MusicImageServlet extends HttpServlet {

	private static final long serialVersionUID = 179071710294408180L;

	public MusicImageServlet() {
		super();
	}

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

        String nome = request.getParameter("id");

        String diretorio = System.getProperty("user.home") + File.separator + Util.PATH_IMAGES_USUARIO + File.separator;
        
        File image = new File(diretorio + nome);

        if ((nome == null) || (!image.exists())) {
            File img = new File(diretorio + "sem_foto.png");

            response.reset();
            response.setContentType("image");
            response.setHeader("Content-Length", String.valueOf(img.length()));

            Files.copy(img.toPath(), response.getOutputStream());
            return;
        }

        String contentType = getServletContext().getMimeType(image.getName());
        if ((contentType == null) || (!contentType.startsWith("image/"))) {
            response.sendError(404);
            return;
        }
        response.reset();
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(image.length()));

        Files.copy(image.toPath(), response.getOutputStream());
    }
}
