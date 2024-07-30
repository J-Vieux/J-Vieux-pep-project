package Service;
import DAO.MessageDAO;
import Model.Message;

import java.util.List;


public class MessageService {
    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();

    }

    public Message addMessage(Message message){
        if(messageDAO.isUser(message.posted_by) != null && message.message_text.length() < 255 && message.message_text.length() > 0){
            
            System.out.println(message);
            messageDAO.insertMessage(message);
            System.out.println(messageDAO.getMessage(message));
            return messageDAO.getMessage(message);
        }
        return null;
    }

    public List<Message> getAllMessage(){
        return messageDAO.retriveAllMessage();
    }

    public Message getMessageByID(int id){
        if(messageDAO.getMessageByID(id) != null){
            return messageDAO.getMessageByID(id);
        }
        else
            return null;
        
    }

    public Message deleteMessage(Message message){
        return messageDAO.deleteMessage(message.message_id);
    }

    public Message updateMessage(int id, String newmessage){
        
        if(newmessage.trim().isEmpty() || newmessage.trim().length() > 255){
            return null;
    }
    return this.messageDAO.updateMessage(newmessage, id);
}

    public List<Message> getMessageByAuthor(int id){
        System.out.println(messageDAO.getAllMessageByAuthor(id));
        return messageDAO.getAllMessageByAuthor(id);
    }

    
}
