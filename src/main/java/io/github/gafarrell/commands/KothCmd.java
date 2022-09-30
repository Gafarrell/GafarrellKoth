package io.github.gafarrell.commands;

import org.bukkit.command.CommandSender;

public abstract class KothCmd {
    protected boolean successful = true;
    protected String errorMessage = null;

    protected CommandSender commandSender;
    protected String[] args;

    public KothCmd(CommandSender commandSender, String[] args){
        this.args = args;
        this.commandSender = commandSender;
    }

    public abstract boolean Execute();
    public boolean errorOccurred(){
        return errorMessage == null;
    }
    public boolean isSuccessful() {
        return successful;
    }
    public String getResponseMessage() {
        return errorMessage;
    }
}
