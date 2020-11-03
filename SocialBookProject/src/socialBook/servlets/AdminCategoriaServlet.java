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
import socialBook.model.CategoriaDAO;

/**
 * @author Mattia De Rosa
 *
 */
@WebServlet("/AdminCategoria")
public class AdminCategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final CategoriaDAO categoriaDAO = new CategoriaDAO();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MyServletException.checkAdmin(request);

		String idstr = request.getParameter("id");
		if (idstr != null) {
			@SuppressWarnings("unchecked")
			List<Categoria> categorie = ((List<Categoria>) getServletContext().getAttribute("categorie"));

			Categoria categoria = null;
			if (!idstr.isEmpty()) {
				int id = Integer.parseInt(idstr);
				categoria = categorie.stream().filter(c -> c.getId() == id).findAny().get();
			}

			if (request.getParameter("rimuovi") != null) {
				categoriaDAO.doDelete(categoria.getId());
				categorie.remove(categoria);
				request.setAttribute("notifica", "Categoria rimossa con successo.");
			} else {
				String nome = request.getParameter("nome");
				String descrizione = request.getParameter("descrizione");
				if (nome != null && descrizione != null) { // modifica/aggiunta categoria
					if (categoria == null) { // aggiunta nuova categoria
						categoria = new Categoria();
						categoria.setNome(nome);
						categoria.setDescrizione(descrizione);
						categoriaDAO.doSave(categoria);
						categorie.add(categoria);
						request.setAttribute("notifica", "Categoria aggiunta con successo.");
					} else { // modifica categoria esistente
						categoria.setNome(nome);
						categoria.setDescrizione(descrizione);
						categoriaDAO.doUpdate(categoria);
						request.setAttribute("notifica", "Categoria modificata con successo.");
					}
				}
				request.setAttribute("categoria", categoria);
			}
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/admincategoria.jsp");
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
