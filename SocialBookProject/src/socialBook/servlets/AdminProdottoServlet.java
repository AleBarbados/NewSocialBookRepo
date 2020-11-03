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
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import socialBook.model.Categoria;
import socialBook.model.Libro;
import socialBook.model.LibroDAO;


@WebServlet("/AdminProdotto")
public class AdminProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final LibroDAO libroDAO = new LibroDAO();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MyServletException.checkAdmin(request);

		String idstr = request.getParameter("id");
		if (idstr != null) {
			if (request.getParameter("rimuovi") != null) {
				libroDAO.doDelete(Integer.parseInt(idstr));
				request.setAttribute("notifica", "Libro rimosso con successo.");
			} else {
				Libro libro;
				String nome = request.getParameter("nome");
				String descrizione = request.getParameter("descrizione");
				String prezzoCent = request.getParameter("prezzoCent");
				if (nome != null && descrizione != null && prezzoCent != null) {
					// modifica/aggiunta libro
					libro = new Libro();
					libro.setNome(nome);
					libro.setDescrizione(descrizione);
					libro.setPrezzoCent(Long.parseLong(prezzoCent));

					String[] categorie = request.getParameterValues("categorie");
					libro.setCategorie(categorie != null ? Arrays.stream(categorie).map(id -> {
						Categoria c = new Categoria();
						c.setId(Integer.parseInt(id));
						return c;
					}).collect(Collectors.toList()) : Collections.emptyList());

					if (idstr.isEmpty()) { // aggiunta nuovo libro
						libroDAO.doSave(libro);
						request.setAttribute("notifica", "Libro aggiunto con successo.");
					} else { // modifica libro esistente
						libro.setId(Integer.parseInt(idstr));
						libroDAO.doUpdate(libro);
						request.setAttribute("notifica", "Libro modificato con successo.");
					}
				} else {
					int id = Integer.parseInt(idstr);
					libro = libroDAO.doRetrieveById(id);
				}
				request.setAttribute("prodotto", libro);
			}
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/adminprodotto.jsp");
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
