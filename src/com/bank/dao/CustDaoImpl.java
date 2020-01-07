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

public class CustDaoImpl implements CustDao {

	@Override
	public List<Card> cardInfoList(Account a) throws BusinessException {
		List<Card> cardInfoList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select cardnumber, cardtype, balance, username from v2_card where username = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, a.getUsername());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Card c = new Card();
				c.setCardNumber(resultSet.getInt("cardnumber"));
				c.setCardType(resultSet.getString("cardtype"));
				c.setBalance(resultSet.getDouble("balance"));
				c.setUsername(resultSet.getString("username"));
				cardInfoList.add(c);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
		return cardInfoList;
	}

	@Override
	public void applyNewCard(Card c) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "insert into v2_pending(pd_cardnumber, pd_cardtype, pd_balance, applicator) values (?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, c.getPd_cardNumber());
			preparedStatement.setString(2, c.getPd_cardType());
			preparedStatement.setDouble(3, c.getPd_balance());
			preparedStatement.setString(4, c.getApplicator());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
	}

	@Override
	public void updateBalance(Card c) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "update v2_card set balance = ? where cardnumber = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, c.getBalance());
			preparedStatement.setInt(2, c.getCardNumber());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
	}

	@Override
	public List<Transaction> pendingMoneyList(Account a) throws BusinessException {
		List<Transaction> pendingMoneyList = new ArrayList<>();
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "select pd_sender, pd_amount from v2_pending where pd_receiver = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, a.getUsername());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Transaction t = new Transaction();
				t.setSender(resultSet.getString("pd_sender"));
				t.setAmount(resultSet.getDouble("pd_amount"));
				pendingMoneyList.add(t);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
		return pendingMoneyList;
	}
	
	@Override
	public void transferProc(Transaction t) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "{call V2_TRANSFER(?, ?, ?)}";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, t.getSender());
			callableStatement.setDouble(2, t.getAmount());
			callableStatement.setString(3, t.getReceiver());
			callableStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
	}

	@Override
	public void insertLogProc(Transaction t) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "{call V2_INSERT_LOG(?, ?, ?, ?)}";
			CallableStatement callableStatement = connection.prepareCall(sql);
			callableStatement.setString(1, t.getPerson_1());
			callableStatement.setString(2, t.getAction());
			callableStatement.setString(3, t.getPerson_2());
			callableStatement.setString(4, t.getTime());
			callableStatement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
	}
	
	@Override
	public void removePendingAmount(Transaction t) throws BusinessException {
		try (Connection connection = OracleConnection.getConnection()) {
			String sql = "delete from v2_pending where pd_amount = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, t.getAmount());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Internal error " + e);
		}
	}

}
