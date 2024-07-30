package Controller;

import Model.Account;
import Model.Message;
import Service.MessageService;
import Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    UserService userService;
    MessageService messageService;

    public SocialMediaController(){
        this.userService = new UserService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::userRegister);
        app.post("/login", this::userLogin);
        app.get("/messages", this::getAllMessage);
        app.post("/messages", this::createMessage);
        app.get("/messages/{message_Id}", this::getMessageByID);
        app.delete("/messages/{message_Id}", this::deleteMessage);
        app.patch("/messages/{message_Id}", this::updateMessage);
        app.get("/accounts/{account_id}/messages", this::getAllMessageFromUser);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void userRegister(Context context) throws JsonProcessingException{
        Account account = context.bodyAsClass(Account.class);
        Account newAccount = userService.register(account);
        
        if(newAccount != null){
            context.status(200).json(newAccount);
        }
        else{
            context.status(400);
        }
    }

    private void userLogin(Context context) throws JsonProcessingException{
        Account account = context.bodyAsClass(Account.class);
        Account newAccount = userService.login(account);
        if(newAccount!=null){
            context.status(200).json(newAccount);
        }
        else{
            context.status(401);
        }
    }

    private void createMessage(Context context) throws JsonProcessingException{
        Message message = context.bodyAsClass(Message.class);
        Message newMessage = messageService.addMessage(message);
        if(newMessage != null){
            context.status(200).json(newMessage);
        }
        else
            context.status(400);
        
    }

    private void getAllMessage(Context context) throws JsonProcessingException{
        List<Message> messages = messageService.getAllMessage();
        context.json(messages);
        context.status(200);
    }

    private void getMessageByID(Context context) throws JsonProcessingException{
        int messageid = Integer.parseInt(context.pathParam("message_Id"));
        System.out.println(messageid);
        Message message = messageService.getMessageByID(messageid);
        if(message != null){
            context.status(200).json(message);
        }
        else{
           context.status(200).json("");
        }
       

    }

    private void deleteMessage(Context context){
        
        int id = Integer.parseInt(context.pathParam("message_Id"));
        Message message = messageService.getMessageByID(id);
        if(message != null){
            messageService.deleteMessage(message);
            context.status(200).json(message); 
        }
            context.status(200);
        
        
    }

    private void updateMessage(Context context) throws JsonProcessingException{
        int messageid = Integer.parseInt(context.pathParam("message_Id"));
        Message message = context.bodyAsClass(Message.class);
        System.out.println(message.message_text);
        Message newmessage = messageService.updateMessage(messageid, message.message_text);
        if(newmessage != null){
            context.status(200).json(newmessage);
        }
        else{
            context.status(400);
        }
    }

    private void getAllMessageFromUser(Context context) throws JsonProcessingException{
        int user = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessageByAuthor(user);
        if(messages != null){
            context.status(200).json(messages);
        }
            context.status(200);
        
        
    }


}