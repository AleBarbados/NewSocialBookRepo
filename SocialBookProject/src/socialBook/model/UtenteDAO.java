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
package socialBook.model;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UtenteDAO {

	public static String DO_RETRIEVE_ALL = "SELECT id, username, passwordhash, nome, email, admin FROM utente LIMIT ?, ?";
	public static String DO_RETRIEVE_BY_ID = "SELECT id, username, passwordhash, nome, email, admin FROM utente WHERE id=?";
	public static String DO_RETRIEVE_BY_USERNAME_PASSWORD = "SELECT id, username, passwordhash, nome, email, admin" +
			"												 FROM utente WHERE username=? AND passwordhash=SHA1(?)";

	public List<Utente> doRetrieveAll(int offset, int limit) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con
					.prepareStatement(DO_RETRIEVE_ALL);
			ps.setInt(1, offset);
			ps.setInt(2, limit);
			ArrayList<Utente> utenti = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Utente u = new Utente();
				u.setId(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setPasswordhash(rs.getString(3));
				u.setNome(rs.getString(4));
				u.setEmail(rs.getString(5));
				u.setAdmin(rs.getBoolean(6));
				utenti.add(u);
			}
			return utenti;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Utente doRetrieveById(int id) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Utente p = new Utente();
				p.setId(rs.getInt(1));
				p.setUsername(rs.getString(2));
				p.setPasswordhash(rs.getString(3));
				p.setNome(rs.getString(4));
				p.setEmail(rs.getString(5));
				p.setAdmin(rs.getBoolean(6));
				return p;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Utente doRetrieveByUsernamePassword(String username, String password) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_USERNAME_PASSWORD);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Utente p = new Utente();
				p.setId(rs.getInt(1));
				p.setUsername(rs.getString(2));
				p.setPasswordhash(rs.getString(3));
				p.setNome(rs.getString(4));
				p.setEmail(rs.getString(5));
				p.setAdmin(rs.getBoolean(6));
				return p;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Utente doRetrieveByUsername(String username) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					"SELECT id, username, passwordhash, nome, email, admin FROM utente WHERE username=?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Utente p = new Utente();
				p.setId(rs.getInt(1));
				p.setUsername(rs.getString(2));
				p.setPasswordhash(rs.getString(3));
				p.setNome(rs.getString(4));
				p.setEmail(rs.getString(5));
				p.setAdmin(rs.getBoolean(6));
				return p;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void doSave(Utente utente) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO utente (username, passwordhash, nome, email, admin) VALUES(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, utente.getUsername());
			ps.setString(2, utente.getPasswordhash());
			ps.setString(3, utente.getNome());
			ps.setString(4, utente.getEmail());
			ps.setBoolean(5, utente.isAdmin());
			if (ps.executeUpdate() != 1) {
				throw new RuntimeException("INSERT error.");
			}
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			utente.setId(rs.getInt(1));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
