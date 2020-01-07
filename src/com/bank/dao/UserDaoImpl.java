package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.dbutil.OracleConnection;
import com.bank.exception.BusinessException;
import com.bank.to.Account;

public class UserDaoImpl implements UserDao {

	@Override
	public void signup(Account a) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "insert into v2_pending(pd_username, pd_password, pd_usertype, pd_fullname, pd_email) values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, a.getPd_username());
			preparedStatement.setString(2, a.getPd_password());
			preparedStatement.setString(3, a.getPd_usertype());
			preparedStatement.setString(4, a.getPd_fullname());
			preparedStatement.setString(5, a.getPd_email());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
	}

	@Override
	public List<Account> accountList() throws BusinessException {
		List<Account> usernameList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select username, password, usertype from v2_account";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Account a = new Account();
				a.setUsername(resultSet.getString("username"));
				a.setPassword(resultSet.getString("password"));
				a.setUsertype(resultSet.getString("usertype"));
				usernameList.add(a);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
		return usernameList;
	}

}
