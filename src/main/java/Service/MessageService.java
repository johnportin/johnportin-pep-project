package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;
import Model.Account;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO(); 
        this.accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO m) {
        this.messageDAO = m;
    }

    public Message postMessage(Message m) {
        String message_text = m.getMessage_text();
        Account account = accountDAO.getAccountByID(m.getPosted_by());
        System.out.println("account = " + account);
        if (account == null | message_text.length() <= 0 | message_text.length() > 255) {
            return null;
        }
        return messageDAO.postMessage(m);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int id) {
        return messageDAO.getMessageByID(id);
    }

    public Message deleteMessageByID(int id) {
        Message message = getMessageByID(id);
        if (message == null) {
            return null;
        }
        messageDAO.deleteMessageByID(id);
        return message;
    }

    public Message updateMessage(int id, Message newMessage) {
        String newMessageText = newMessage.getMessage_text();
        if (newMessageText.length() <= 0 | newMessageText.length() > 255) {
            return null;
        } else {
            return messageDAO.updateMessageByID(id, newMessage);
        }
    }

    public List<Message> getAllMessagesByUserID(int id) {
        Account a = accountDAO.getAccountByID(id);
        if (a != null) {
            return messageDAO.getAllMessagesByUserID(id);
        }
        return null;
    }
    
}
