package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    public SocialMediaController() {
        accountService = new AccountService();
    }
    

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
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

    private void loginUserHandler(Context ctx) {
        ctx.json("Logging in user");
    }

    private void postMessageHandler(Context ctx) {
        ctx.json("Posting message");
    }

    private void getAllMessagesHandler(Context ctx) {
        ctx.json("Retrieving all messages");
    }

    private void getMessageByIdHandler(Context ctx) {
        String id = ctx.pathParam("message_id");
        ctx.json("Retrieving message for id=" + id);
    }

    private void delteMessageByIdHandler(Context ctx) {
        String id = ctx.pathParam("message_id");
        ctx.json("Deleting message for id=" + id);
    }

    private void updateMessageByIdHandler(Context ctx) {
        String id = ctx.pathParam("message_id");
        ctx.json("Updating message for id=" + id);
    }

    private void getAllMessagesForUserByIdHandler(Context ctx) {
        String id = ctx.pathParam("account_id");
        ctx.json("Retrieving all messages for account_id=" + id);
    }



    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}