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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

	public final static String DO_SAVE = "INSERT INTO genere (nome, descrizione) VALUES(?,?)";
	public final static String DO_RETRIEVE_ALL = "SELECT id, nome, descrizione FROM genere";
	public final static String DO_UPDATE = "UPDATE genere SET nome=?, descrizione=? WHERE id=?";
	public final static String DO_DELETE = "DELETE FROM genere WHERE id=?";

	public List<Categoria> doRetrieveAll() {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_ALL);
			ArrayList<Categoria> categorie = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Categoria c = new Categoria();
				c.setId(rs.getInt(1));
				c.setNome(rs.getString(2));
				c.setDescrizione(rs.getString(3));
				categorie.add(c);
			}
			return categorie;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void doSave(Categoria categoria) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DO_SAVE,
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, categoria.getNome());
			ps.setString(2, categoria.getDescrizione());
			if (ps.executeUpdate() != 1) {
				throw new RuntimeException("INSERT error.");
			}
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			categoria.setId(rs.getInt(1));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void doUpdate(Categoria categoria) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DO_UPDATE);
			ps.setString(1, categoria.getNome());
			ps.setString(2, categoria.getDescrizione());
			ps.setInt(3, categoria.getId());
			if (ps.executeUpdate() != 1) {
				throw new RuntimeException("UPDATE error.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void doDelete(int id) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DO_DELETE);
			ps.setInt(1, id);
			if (ps.executeUpdate() != 1) {
				throw new RuntimeException("DELETE error.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
