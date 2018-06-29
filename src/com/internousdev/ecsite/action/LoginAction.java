package com.internousdev.ecsite.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.ecsite.dao.BuyItemDAO;
import com.internousdev.ecsite.dao.LoginDAO;
import com.internousdev.ecsite.dto.BuyItemDTO;
import com.internousdev.ecsite.dto.LoginDTO;
import com.opensymphony.xwork2.ActionSupport;
public class LoginAction extends ActionSupport implements SessionAware {

	/*カプセル化*/
private String loginUserId;  //Login.jspからの得たログイン情報が入ってる
private String loginPassword; //Login.jspからの得たログイン情報が入ってる
public Map<String, Object> session;  //DBへログイン情報を送るために必要(ローカルsession)
private LoginDAO loginDAO = new LoginDAO();  //
private LoginDTO loginDTO = new LoginDTO();
private BuyItemDAO buyItemDAO = new BuyItemDAO();



/*executeメソッド実行*/
public String execute() throws SQLException{
String result = ERROR;
loginDTO = loginDAO.getLoginUserInfo(loginUserId, loginPassword);
session.put("loginUser"
, loginDTO);
if(((LoginDTO) session.get("loginUser")).getLoginFlg()) {
result = SUCCESS;
BuyItemDTO buyItemDTO = buyItemDAO.getBuyItemInfo();
session.put("login_user_id"
,loginDTO.getLoginId());
session.put("id"
, buyItemDTO.getId());
session.put("buyItem_name"
, buyItemDTO.getItemName());
session.put("buyItem_price"
, buyItemDTO.getItemPrice());
return result;
}
return result;
}



public String getLoginUserId() {
return loginUserId;
}
public void setLoginUserId(String loginUserId) {
this.loginUserId = loginUserId;
}
public String getLoginPassword() {
return loginPassword;
}
public void setLoginPassword(String loginPassword) {
this.loginPassword = loginPassword;
}
@Override
public void setSession(Map<String, Object> session) {
this.session = session;
}
}