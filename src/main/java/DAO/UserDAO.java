package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;


public class UserDAO {

    public Account addAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "insert into account(username, password) values(?,?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());

            int rs = preparedStatement.executeUpdate();
            if(rs > 0){
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Account canLogin(Account account){

        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where account_id = ? and username = ? and password = ?;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,account.getAccount_id()+1);
            preparedStatement.setString(2,account.getUsername());
            preparedStatement.setString(3,account.getPassword());
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account accounts = new Account(
                        rs.getString("username"),
                        rs.getString("password"));
                return accounts;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean hasUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select username from account where username = ?;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,username);
            

            preparedStatement.executeQuery();

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Account getUser(Account user){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username = ?;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,user.getUsername());
            

            preparedStatement.executeQuery();

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
