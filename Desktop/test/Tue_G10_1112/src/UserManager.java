
public class UserManager {
	private DataBaseManager database = new DataBaseManager();
	public UserManager() {
		
	}
	public void register(String ID, String password) throws IDError, PasswordError {
		try {
			int id = Integer.parseInt(ID);
			if (id == 0 || password.length() == 0) throw new IDError("Password can't be empty");
			if(password.length() != 8) throw new PasswordError("Password should be 8 letter");
			if(database.getUser(id) != null) throw new IDError("You have enrolled already!");
			database.createUser(id,password);
		}catch(NumberFormatException e){
			throw new IDError("ID foramt incorrect");
		}
	}
	public User logIn(String ID, String password) throws IDError, PasswordError {
		try {
			int id = Integer.parseInt(ID);
			if (id == 0 || password.length() == 0) throw new IDError("Password can't be empty");
			User user = database.getUser(id);
			if(user == null) throw new IDError("You haven't enrolled yet!");
			if(!user.getPassword().equals(password)) throw new PasswordError("Password wrong");
			return user;
		}catch(NumberFormatException e) {
			throw new IDError("ID foramt incorrect");
		}
	}
}
class IDError extends Exception {
	public IDError(String Error){
		super(Error);
	}
}
class PasswordError extends Exception {
	public PasswordError(String Error){
		super(Error);
	}
}