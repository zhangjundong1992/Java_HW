import utilities.JDBCUtils;
import utilities.ResCode;

import java.sql.*;

class AccountModel {
    ResCode transferMoney(String user_out, String user_in, int count) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet res = null;

        try {

            con = JDBCUtils.getConnection();
            String sql;

            /*检查账户是否存在*/
            sql = "select * from account where username = ? or username = ?";
            assert con != null;
            statement = con.prepareStatement(sql);
            statement.setString(1, user_out);
            statement.setString(2, user_in);
            res = statement.executeQuery();
            res.last();
            int rowCount = res.getRow();
            if (rowCount < 2) {
                return ResCode.NULL_ACCOUNT;
            }

            /*检查转出账户金额*/
            sql = "select balance from account where username = ?";
            statement = con.prepareStatement(sql);
            statement.setString(1, user_out);
            res = statement.executeQuery();
            if (res.next()) {
                int balance = res.getInt("balance");
                if (balance <= count) {
                    return ResCode.NO_MONEY;
                }
            }

            ResCode code = ResCode.OTHER;
            /*开启事务,转账*/
            con.setAutoCommit(false);

            /*转出*/
            sql = "update account set balance = balance - ? where username = ?";
            statement = con.prepareStatement(sql);
            statement.setInt(1, count);
            statement.setString(2, user_out);
            statement.executeUpdate();

            /*转入*/
            sql = "update account set balance = balance + ? where username = ?";
            statement = con.prepareStatement(sql);
            statement.setInt(1, count);
            statement.setString(2, user_in);
            statement.executeUpdate();
            code = ResCode.SUCCESS;

            /*关闭事务*/
            con.commit();

            return code;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.close(con, statement, null);
        }

        return ResCode.OTHER;
    }
}
