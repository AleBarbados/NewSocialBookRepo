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
import java.util.stream.Collectors;


public class LibroDAO {

	public final static String DO_RETRIEVE_ALL = "SELECT id, nome, descrizione, prezzo FROM libro LIMIT ?, ?";
	public final static String DO_RETRIEVE_BY_ID = "SELECT id, nome, descrizione, prezzo FROM libro WHERE id=?";
	public final static String COUNT_BY_ID = "SELECT COUNT(*) FROM prodotto LEFT JOIN libro_categoria ON id=idlibro WHERE idcategoria=?";
	public final static String DO_RETRIEVE_BY_CATEGORIA = "SELECT id, nome, descrizione, prezzo FROM prodotto " +
			"											LEFT JOIN prodotto_categoria ON id=idlibro WHERE idcategoria=? ";
	public final static String DO_RETRIEVE_BY_NOME_OR_DESCRIZIONE = "SELECT id, nome, descrizione, prezzo FROM libro " +
			"													WHERE MATCH(nome, descrizione) AGAINST(?) LIMIT ?, ?";
	public final static String DO_RETRIEVE_BY_NOME =  "SELECT id, nome, descrizione, prezzo FROM libro " +
			"									WHERE MATCH(nome) AGAINST(? IN BOOLEAN MODE) LIMIT ?, ?";
	public final static String DO_SAVE = "INSERT INTO libro (nome, descrizione, prezzo) VALUES(?,?,?)";
	public final static String DO_SAVE_CATEGORIA = "INSERT INTO libro_categoria (idlibro, idcategoria) VALUES (?, ?)";
	public final static String DO_UPDATE = "UPDATE libro SET nome=?, descrizione=?, prezzo=? WHERE id=?";
	public final static String DO_DELETE = "DELETE FROM libro WHERE id=?";
	public final static String GET_CATEGORIA = "SELECT id, nome, descrizione FROM categoria LEFT JOIN libro_categoria ON id=idcategoria WHERE idlibro=?";


	public enum OrderBy {
		DEFAULT(""), PREZZO_ASC("ORDER BY prezzoCent ASC"), PREZZO_DESC("ORDER BY prezzoCent DESC");
		private String sql;

		private OrderBy(String sql) {
			this.sql = sql;
		}
	};

	public List<Libro> doRetrieveAll(int offset, int limit) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con
					.prepareStatement(DO_RETRIEVE_ALL);
			ps.setInt(1, offset);
			ps.setInt(2, limit);
			ArrayList<Libro> libri = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Libro p = new Libro();
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setDescrizione(rs.getString(3));
				p.setPrezzo(rs.getLong(4));
				p.setCategorie(getCategorie(con, p.getId()));
				libri.add(p);
			}
			return libri;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Libro doRetrieveById(int id) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con
					.prepareStatement(DO_RETRIEVE_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Libro p = new Libro();
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setDescrizione(rs.getString(3));
				p.setPrezzo(rs.getLong(4));
				p.setCategorie(getCategorie(con, p.getId()));
				return p;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int countByCategoria(int categoria) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con
					.prepareStatement(COUNT_BY_ID);
			ps.setInt(1, categoria);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Libro> doRetrieveByCategoria(int categoria, OrderBy orderBy, int offset, int limit) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_CATEGORIA+ orderBy.sql + " LIMIT ?, ?");
			ps.setInt(1, categoria);
			ps.setInt(2, offset);
			ps.setInt(3, limit);
			ArrayList<Libro> prodotti = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Libro p = new Libro();
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setDescrizione(rs.getString(3));
				p.setPrezzo(rs.getLong(4));
				p.setCategorie(getCategorie(con, p.getId()));
				prodotti.add(p);
			}
			return prodotti;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Libro> doRetrieveByNomeOrDescrizione(String against, int offset, int limit) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_NOME_OR_DESCRIZIONE);
			ps.setString(1, against);
			ps.setInt(2, offset);
			ps.setInt(3, limit);
			ArrayList<Libro> libri = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Libro p = new Libro();
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setDescrizione(rs.getString(3));
				p.setPrezzo(rs.getLong(4));
				p.setCategorie(getCategorie(con, p.getId()));
				libri.add(p);
			}
			return libri;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Libro> doRetrieveByNome(String against, int offset, int limit) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_NOME);
			ps.setString(1, against);
			ps.setInt(2, offset);
			ps.setInt(3, limit);
			ArrayList<Libro> libri = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Libro p = new Libro();
				p.setId(rs.getInt(1));
				p.setNome(rs.getString(2));
				p.setDescrizione(rs.getString(3));
				p.setPrezzo(rs.getLong(4));
				p.setCategorie(getCategorie(con, p.getId()));
				libri.add(p);
			}
			return libri;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void doSave(Libro libro) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(
					DO_SAVE,
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, libro.getNome());
			ps.setString(2, libro.getDescrizione());
			ps.setDouble(3, libro.getPrezzo());
			if (ps.executeUpdate() != 1) {
				throw new RuntimeException("INSERT error.");
			}
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			libro.setId(id);

			PreparedStatement psCa = con
					.prepareStatement(DO_SAVE_CATEGORIA);
			for (Categoria c : libro.getCategorie()) {
				psCa.setInt(1, id);
				psCa.setInt(2, c.getId());
				psCa.addBatch();
			}
			psCa.executeBatch();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void doUpdate(Libro libro) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con
					.prepareStatement(DO_UPDATE);
			ps.setString(1, libro.getNome());
			ps.setString(2, libro.getDescrizione());
			ps.setDouble(3, libro.getPrezzo());
			ps.setInt(4, libro.getId());
			if (ps.executeUpdate() != 1) {
				throw new RuntimeException("UPDATE error.");
			}

			if (libro.getCategorie().isEmpty()) {
				PreparedStatement psCaDel = con.prepareStatement("DELETE FROM libro_categoria WHERE idlibro = ?");
				psCaDel.setInt(1, libro.getId());
				psCaDel.executeUpdate();
			} else {
				PreparedStatement psCaDel = con
						.prepareStatement("DELETE FROM libro_categoria WHERE idlibro = ? AND idcategoria NOT IN ("
								+ libro.getCategorie().stream().map(c -> String.valueOf(c.getId()))
										.collect(Collectors.joining(","))
								+ ")");
				psCaDel.setInt(1, libro.getId());
				psCaDel.executeUpdate();

				PreparedStatement psCa = con.prepareStatement(
						"INSERT IGNORE INTO libro_categoria (idlibro, idcategoria) VALUES (?, ?)");
				for (Categoria c : libro.getCategorie()) {
					psCa.setInt(1, libro.getId());
					psCa.setInt(2, c.getId());
					psCa.addBatch();
				}
				psCa.executeBatch();
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

	private static List<Categoria> getCategorie(Connection con, int idProdotto) throws SQLException {
		PreparedStatement ps = con.prepareStatement(GET_CATEGORIA);
		ps.setInt(1, idProdotto);
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
	}
}
