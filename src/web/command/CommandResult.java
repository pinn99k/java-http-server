package web.command;

public class CommandResult {
    public final boolean redirect;
    public final String redirectUrl;
    public final String view;

    private CommandResult(boolean redirect, String redirectUrl, String view){
        this.redirect = redirect;
        this.redirectUrl = redirectUrl;
        this.view = view;
    }

    public static CommandResult redirect(String url){
        return new CommandResult(true, url, null);
    }

    public static CommandResult render(String view) {
        return new CommandResult(false, null, view);
    }
}
