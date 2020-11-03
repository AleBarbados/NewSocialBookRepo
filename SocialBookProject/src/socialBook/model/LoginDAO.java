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
import java.util.UUID;

/**
 * @author Mattia De Rosa
 *
 */
public class LoginDAO {

	public final static String DO_RETRIEVE_BY_ID = "SELECT id, idutente, token, time FROM login WHERE id=?";
	public final static String DO_SAVE = "INSERT INTO login (id, idutente, token, time) VALUES(?, ?,?,?)";
	public final static String DO_UPDATE = "UPDATE login SET idutente=?, token=?, time=? WHERE id=?";
	public final static String DO_DELETE = "DELETE FROM login WHERE id=?";

	public Login doRetrieveById(String id) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DO_RETRIEVE_BY_ID);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Login l = new Login();
				l.setId(rs.getString(1));
				l.setIdutente(rs.getInt(2));
				l.setToken(rs.getString(3));
				l.setTime(rs.getTimestamp(4));
				return l;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void doSave(Login login) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DO_SAVE , Statement.RETURN_GENERATED_KEYS);
			String id = UUID.randomUUID().toString();
			ps.setString(1, id);
			ps.setInt(2, login.getIdutente());
			ps.setString(3, login.getToken());
			ps.setTimestamp(4, login.getTime());
			if (ps.executeUpdate() != 1) {
				throw new RuntimeException("INSERT error.");
			}
			login.setId(id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void doUpdate(Login login) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DO_UPDATE);
			ps.setInt(1, login.getIdutente());
			ps.setString(2, login.getToken());
			ps.setTimestamp(3, login.getTime());
			ps.setString(4, login.getId());
			if (ps.executeUpdate() != 1) {
				throw new RuntimeException("UPDATE error.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void doDelete(String id) {
		try (Connection con = ConPool.getConnection()) {
			PreparedStatement ps = con.prepareStatement(DO_DELETE);
			ps.setString(1, id);
			if (ps.executeUpdate() != 1) {
				throw new RuntimeException("DELETE error.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
