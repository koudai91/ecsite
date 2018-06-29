package com.internousdev.ecsite.action;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.ecsite.dao.MyPageDAO;
import com.internousdev.ecsite.dto.MyPageDTO;
import com.opensymphony.xwork2.ActionSupport;

public class MyPageAction extends ActionSupport implements SessionAware {
	public Map<String, Object> session;//map キーと値を入れる
	///String型 (文字列をいれられる）
	///Object型（すべてのデータ型が入ることが許されるから使用）
	private MyPageDAO myPageDAO = new MyPageDAO();
	private ArrayList<MyPageDTO> myPageList = new ArrayList<MyPageDTO>();
	private String deleteFlg;
	private String message;


	public String execute() throws SQLException {
		//LoginAction.javaでidをsession.putしてればtrue→falseとなる。(ログインできてればOK)
		if (!session.containsKey("id")) {//キーになるほうにIDが入っているかチェック
			//上記がfalseなのでERRORはスルーされて、次の処理へ進む。
			return ERROR;///キー(ID)が未入力だった場合エラーが起こる
		}

		if(deleteFlg == null) {//履歴削除してない場合の処理
			String item_transaction_id = session.get("id").toString();//現在セッションに入っているIDを呼び出しているtoStringでストリング型に戻しているセッションを使うとObject型に変わっているため
			//sessionからid情報を取得し、変数へ代入。
			String user_master_id = session.get("login_user_id").toString();
			//sessionからlogin_user_id情報を取得し、変数へ代入。
			myPageList = myPageDAO.getMyPageUserInfo(item_transaction_id, user_master_id);//myPageDAOのMyPageUserInfoメソッドを実行し、変数へ代入。
		} else if(deleteFlg.equals("1")) {//履歴削除した場合、deleteメソッド実行
			delete();//履歴の削除処理
		}
		String result = SUCCESS;
		return result;
	}


public void delete() throws SQLException {//履歴の削除を行うためのメソッド。
String item_transaction_id = session.get("id").toString();
String user_master_id = session.get("login_user_id").toString();
int res = myPageDAO.buyItemHistoryDelete(item_transaction_id, user_master_id);
//↑DBから削除した履歴情報の件数を、「res」に格納している。
if(res > 0) {//削除した件数が1件以上の場合、"商品情報を正しく削除しました。"と表示させる。
myPageList = null;
setMessage("商品情報を正しく削除しました" );
} else if(res == 0) {//削除した件数が0の場合、"商品情報の削除に失敗しました。"と表示される。
setMessage("商品情報の削除に失敗しました。" );
}
}


public void setDeleteFlg(String deleteFlg) {
this.deleteFlg = deleteFlg;
}
@Override //@アノテーション　パソコンに対して
public void setSession(Map<String, Object> session) {//Overrideでセッションで使う型を決めている!セッションを使うときはMAP型(string,object)を使うと宣言
this.session = session;
}


public ArrayList<MyPageDTO> getMyPageList() {
return this.myPageList;
}
public String getMessage() {
return this.message;
}
public void setMessage(String message) {
this.message = message;
}
}