package Controller;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }
    
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::registerUserHandler);
        app.post("login", this::loginUserHandler);
        app.post("messages", this::postMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::delteMessageByIdHandler);
        app.patch("messages/{message_id}", this::updateMessageByIdHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesForUserByIdHandler);

        return app;
    }

    private void registerUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if (addedAccount == null) {
            ctx.status(400);
        } else {
            ctx.status(200);
            ctx.json(om.writeValueAsString(addedAccount));
        }
    }

    private void loginUserHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(ctx.body(), Account.class);
        Account loggedInAccount = accountService.loginAccount(account);
        if (loggedInAccount == null) {
            ctx.status(401);
        } else {
            ctx.status(200);
            ctx.json(om.writeValueAsString(loggedInAccount));
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message m = om.readValue(ctx.body(), Message.class);
        System.out.println(m);
        Message postedMessage = messageService.postMessage(m);
        System.out.println("posted Message" + postedMessage);
        if (postedMessage == null) {
            ctx.status(400);
        } else {
            ctx.status(200);
            ctx.json(om.writeValueAsString(postedMessage));
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        List<Message> messages = new ArrayList<Message>();
        messages = messageService.getAllMessages();
        ctx.json(om.writeValueAsString(messages));
    }

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        int id = Integer.valueOf(ctx.pathParam("message_id"));
        Message message = messageService.getMessageByID(id);
        ctx.status(200);
        if (message == null) {
            System.out.println("I'm null!");
            ctx.json("");
        } else {
            ctx.json(om.writeValueAsString(message));
        }
    }

    private void delteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        int id = Integer.valueOf(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageByID(id);
        ctx.status(200);
        if (message == null) {
            ctx.json("");
        } else {
            ctx.json(om.writeValueAsString(message));
        }
    }

    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.valueOf(ctx.pathParam("message_id"));
        Message message = messageService.getMessageByID(id);

        if (message == null) {
            ctx.status(400);
            ctx.json("");
        } else {
            
            ObjectMapper om = new ObjectMapper();
            Message newMessage = om.readValue(ctx.body(), Message.class);
            Message updatedMessage = messageService.updateMessage(id, newMessage);
            if (updatedMessage != null) {
                ctx.status(200);
                ctx.json(om.writeValueAsString(updatedMessage));    
            } else {
                ctx.status(400);
                ctx.json("");
            }
        }
        
    }

    private void getAllMessagesForUserByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        int id = Integer.valueOf(ctx.pathParam("account_id"));
        ctx.status(200);
        List<Message> messages = messageService.getAllMessagesByUserID(id);
        if (messages != null && messages.size() > 0) {
            ctx.json(om.writeValueAsString(messages));
        } else {
            ctx.json("[]");
        }
        
    }
}