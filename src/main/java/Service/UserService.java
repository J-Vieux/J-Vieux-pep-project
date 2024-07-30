package Service;
import DAO.UserDAO;
import Model.Account;



public class UserService {
    public UserDAO userDAO;

    public UserService(){
        userDAO = new UserDAO();
    }

    public Account register(Account user){
        if(userDAO.hasUsername(user.getUsername()) == false && user.getPassword().length() > 3 && user.username != ""){
            userDAO.addAccount(user);
            System.out.println(userDAO.getUser(user));
            return userDAO.getUser(user);
        }
        else{
            return null;
        }
        

    }

    public Account login(Account user){
        if(userDAO.canLogin(user) != null){
            return userDAO.getUser(user);
        }
        return null;

    }
    
}
