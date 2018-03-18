package hello.vo;

public class UserInfo {

    private  int id;
    private  String Password;
    private  String Gender;
    private  String DOB;
    private  String Email;
    private  String Risk;
    private  String UserName;
    
    public UserInfo() {
    }
 
    public UserInfo(int id, String userName, String password, String gender, String dOB, String email, String risk) {
		super();
		this.id = id;
		UserName = userName;
		Password = password;
		Gender = gender;
		DOB = dOB;
		Email = email;
		Risk = risk;
	}
	
   
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getRisk() {
		return Risk;
	}
	public void setRisk(String risk) {
		Risk = risk;
	}
	
    

   
}
