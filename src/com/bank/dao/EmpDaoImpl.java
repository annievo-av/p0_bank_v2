package com.bank.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.dbutil.OracleConnection;
import com.bank.exception.BusinessException;
import com.bank.to.Account;
import com.bank.to.Card;
import com.bank.to.Transaction;

public class EmpDaoImpl implements EmpDao {

	@Override
	public List<Account> accountInfoList() throws BusinessException {
		List<Account> accountInfoList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select a.username, a.usertype, a.fullname, a.email, c.cardnumber, c.cardtype, c.balance "
					+ "from v2_account a join v2_card c " + "on a.username = c.username";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Account a = new Account();
				a.setUsername(resultSet.getString("username"));
				a.setUsertype(resultSet.getString("usertype"));
				a.setFullname(resultSet.getString("fullname"));
				a.setEmail(resultSet.getString("email"));
				a.setCard(new Card(resultSet.getInt("cardnumber"), resultSet.getString("cardtype"),
						resultSet.getDouble("balance"), resultSet.getString("username")));
				accountInfoList.add(a);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
		return accountInfoList;
	}

	@Override
	public List<Account> pendingAccountList() throws BusinessException {
		List<Account> pendingAccountList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select pd_username, pd_password, pd_usertype, pd_fullname, pd_email from v2_pending";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Account a = new Account();
				a.setPd_username(resultSet.getString("pd_username"));
				a.setPd_password(resultSet.getString("pd_password"));
				a.setPd_usertype(resultSet.getString("pd_usertype"));
				a.setPd_fullname(resultSet.getString("pd_fullname"));
				a.setPd_email(resultSet.getString("pd_email"));
				pendingAccountList.add(a);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
		return pendingAccountList;
	}

	@Override
	public List<Card> pendingCardList() throws BusinessException {
		List<Card> pendingCardList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select pd_cardnumber, pd_cardtype, pd_balance, applicator from v2_pending";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Card c = new Card();
				c.setPd_cardNumber(resultSet.getInt("pd_cardnumber"));
				c.setPd_cardType(resultSet.getString("pd_cardtype"));
				c.setPd_balance(resultSet.getDouble("pd_balance"));
				c.setApplicator(resultSet.getString("applicator"));
				pendingCardList.add(c);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
		return pendingCardList;
	}

	public List<Transaction> logList() throws BusinessException {
		List<Transaction> logList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select person_1, action, person_2, time from v2_log order by time";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Transaction t = new Transaction();
				t.setPerson_1(resultSet.getString("person_1"));
				t.setAction(resultSet.getString("action"));
				t.setPerson_2(resultSet.getString("person_2"));
				t.setTime(resultSet.getString("time"));
				logList.add(t);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
		return logList;
	}

	@Override
	public void approveAccountProc(Account a) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "{call V2_APPROVE_ACCOUNT(?, ?, ?, ?, ?)}";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, a.getPd_username());
			callableStatement.setString(2, a.getPd_password());
			callableStatement.setString(3, a.getPd_usertype());
			callableStatement.setString(4, a.getPd_fullname());
			callableStatement.setString(5, a.getPd_email());
			callableStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
	}

	@Override
	public void approveCardProc(Card c) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "{call V2_APPROVE_CARD(?, ?, ?, ?)}";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setInt(1, c.getPd_cardNumber());
			callableStatement.setString(2, c.getPd_cardType());
			callableStatement.setDouble(3, c.getPd_balance());
			callableStatement.setString(4, c.getApplicator());
			callableStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
	}

	@Override
	public void removePendingAccount(Account a) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "delete from v2_pending where pd_username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, a.getPd_username());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
	}
	
	@Override
	public void removePendingCard(Card c) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "delete from v2_pending where applicator = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, c.getApplicator());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
	}

}
