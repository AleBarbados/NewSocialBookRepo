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
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import socialBook.model.Login;
import socialBook.model.LoginDAO;
import socialBook.model.Utente;
import socialBook.model.UtenteDAO;

/**
 * @author Mattia De Rosa
 *
 */
@WebFilter("/*")
public class CookieLoginFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;
	private final UtenteDAO utenteDAO = new UtenteDAO();
	private final LoginDAO loginDAO = new LoginDAO();

	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String path = request.getRequestURI();
		if (!path.contains("/Login") && !path.contains("/Logout")) {
			HttpSession session = request.getSession();
			Utente utente = (Utente) session.getAttribute("utente");
			if (utente == null) {
				Cookie cookies[] = request.getCookies();
				// cookie con nome 'login' o null se non esiste
				Cookie loginCookie = cookies == null ? null
						: Arrays.stream(cookies).filter(c -> c.getName().equals("login")).findAny().orElse(null);

				if (loginCookie != null) {
					String[] sp = loginCookie.getValue().split("_");
					String id = sp[0];
					String token = sp.length > 1 ? sp[1] : null;

					Login login = loginDAO.doRetrieveById(id);
					if (login != null && login.getToken().equals(token)) {
						utente = utenteDAO.doRetrieveById(login.getIdutente());
						session.setAttribute("utente", utente);

						// per maggiore sicurezza genera nuovo token
						token = UUID.randomUUID().toString();
						login.setToken(token);
						loginDAO.doUpdate(login);
						loginCookie.setValue(id + "_" + token);
						loginCookie.setMaxAge(30 * 24 * 60 * 60); // 30 giorni
						response.addCookie(loginCookie);
					} else {
						// andrebbe gestita questa eventualità, ad esempio annullando tutte le sessioni
						// dell'utente
						loginCookie.setMaxAge(0);
						response.addCookie(loginCookie);
						if (login != null) {
							loginDAO.doDelete(id);
						}
					}
				}
			}
		}
		chain.doFilter(request, response);
	}

}
