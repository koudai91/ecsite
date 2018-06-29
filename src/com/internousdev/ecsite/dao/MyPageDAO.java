package com.internousdev.ecsite.dao;
import java.sql.Connection;/*JDBCによるDBへの接続と検索の実行*/
import java.sql.PreparedStatement;/*プリコンパイルされた SQL 文を表すオブジェクト。sqlからデータを引っ張っている！*/
import java.sql.ResultSet;/*sqlの実行結果をJAVAベースの戻り値で返す*/
import java.sql.SQLException;/*sqlのえらーを出すために必要*/
/*DAOファイルを使うとき2－5行は必要！！！*/
import java.util.ArrayList;

import com.internousdev.ecsite.dto.MyPageDTO;
import com.internousdev.ecsite.util.DBConnector;

public class MyPageDAO {
	private DBConnector dbConnector = new DBConnector();
	private Connection connection = dbConnector.getConnection();

	public ArrayList<MyPageDTO> getMyPageUserInfo (String item_transaction_id, String user_master_id) throws SQLException {  ///cathでの例外処理
	ArrayList<MyPageDTO> myPageDTO = new ArrayList<MyPageDTO>();
	String sql ="SELECT ubit.id, iit.item_name, ubit.total_price, ubit.total_count,ubit.pay, ubit.insert_date "
			+"FROM user_buy_item_transaction ubit "/*名前の変更*/
			+ "LEFT JOIN item_info_transaction iit ON ubit.item_transaction_id = iit.id "/*！！左外部結合！！*/
			+"WHERE ubit.item_transaction_id = ? AND ubit.user_master_id = ? "
			+"ORDER BY insert_date DESC" ;

			try {     ///通常実行される文
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, item_transaction_id);/*sqlのデータをjavaで使えるよう変換！！！*/
				preparedStatement.setString(2, user_master_id);/*sqlのデータをjavaで使えるよう変換！！！*/
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next()) {
					MyPageDTO dto = new MyPageDTO();
					dto.setId(resultSet.getString("id"));
					dto.setItemName(resultSet.getString("item_name"));
					dto.setTotalPrice(resultSet.getString("total_price"));
					dto.setTotalCount(resultSet.getString("total_count"));
					dto.setPayment(resultSet.getString("pay"));
					dto.setInsert_date(resultSet.getString("insert_date"));/*30-35ここでデータいれている！！！*/
					myPageDTO.add(dto);   // add*mypagedtoのAllayListに格納
				}
			} catch(Exception e) {   ///例外発生時に実行される文
				e.printStackTrace();
			} finally {   ///例外があってもなくても実行する処理
				connection.close(); // DBconecctionはクローズ処理が必要
			}
			return myPageDTO;
	}

	public int buyItemHistoryDelete(String item_transaction_id, String user_master_id) throws SQLException {


		String sql ="DELETE FROM user_buy_item_transaction WHERE item_transaction_id = ? AND user_master_id = ?";
		PreparedStatement preparedStatement;
		int result =0;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, item_transaction_id);
			preparedStatement.setString(2, user_master_id);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return result;
	}
}


