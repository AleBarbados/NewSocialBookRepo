/*
BSD 3-Clause License

Copyright (c) 2019, Mattia De Rosa
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the copyright holder nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package socialBook.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import socialBook.model.Categoria;
import socialBook.model.Libro;
import socialBook.model.LibroDAO;

/**
 * @author Mattia De Rosa
 *
 */
@WebServlet("/Categoria")
public class CategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final LibroDAO libroDAO = new LibroDAO();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		List<Categoria> categorie = (List<Categoria>) getServletContext().getAttribute("categorie");
		int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("categoria", categorie.stream().filter(c -> c.getId() == id).findAny().get());

		String pagstr = request.getParameter("pag");
		int pag = pagstr == null ? 1 : Integer.parseInt(pagstr);
		request.setAttribute("pag", pag);

		String perpagstr = request.getParameter("perpag");
		int perpag = perpagstr == null ? 10 : Integer.parseInt(perpagstr);
		request.setAttribute("perpag", perpag);

		int totaleprodotti = libroDAO.countByCategoria(id);
		int npag = (totaleprodotti + perpag - 1) / perpag;
		request.setAttribute("npag", npag);

		String ordstr = request.getParameter("ord");
		LibroDAO.OrderBy ord = ordstr == null ? LibroDAO.OrderBy.DEFAULT : LibroDAO.OrderBy.valueOf(ordstr);
		request.setAttribute("ord", ord);

		List<Libro> prodotti = libroDAO.doRetrieveByCategoria(id, ord, (pag - 1) * perpag, perpag);
		request.setAttribute("prodotti", prodotti);

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/categoria.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
