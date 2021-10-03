package dansplugins.mailboxes.objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dansplugins.mailboxes.managers.ConfigManager;
import dansplugins.mailboxes.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Message implements IMessage, Savable {

    protected int ID;
    protected String type;
    protected String sender;
    protected String recipient;
    protected String content;
    protected Date date;
    protected int mailboxID;
    protected boolean archived = false;
    protected boolean unread = true;

    public Message(int ID, String type, String sender, String recipient, String content) {
        this.ID = ID;
        this.type = type;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.date = new Date();
    }

    public Message(int ID, String type, String sender, String recipient, String content, int mailboxID) {
        this.ID = ID;
        this.type = type;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.date = new Date();
        this.mailboxID = mailboxID;
    }

    public Message(Map<String, String> data) {
        this.load(data);
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }

    @Override
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int getMailboxID() {
        return mailboxID;
    }

    @Override
    public void setMailboxID(int ID) {
        this.mailboxID = ID;
    }

    @Override
    public void sendContentToPlayer(Player player) {
        Logger.getInstance().log("Message ID: " + ID);
        Logger.getInstance().log("Mailbox ID: " + mailboxID);
        player.sendMessage(ChatColor.AQUA + "=============================");
        player.sendMessage(ChatColor.AQUA + "Type: " + type);
        player.sendMessage(ChatColor.AQUA + "Date: " + date.toString());
        player.sendMessage(ChatColor.AQUA + "From: " + sender);
        if (ConfigManager.getInstance().getBoolean("quotesEnabled")) {
            player.sendMessage(ChatColor.AQUA + "\"" + content + "\"");
        }
        else {
            player.sendMessage(ChatColor.AQUA + content);
        }

        player.sendMessage(ChatColor.AQUA + "=============================");
    }

    @Override
    public boolean isArchived() {
        return archived;
    }

    @Override
    public void setArchived(boolean b) {
        archived = b;
    }

    @Override
    public boolean isUnread() {
        return unread;
    }

    @Override
    public void setUnread(boolean b) {
        unread = b;
    }

    @Override
    public Map<String, String> save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Map<String, String> saveMap = new HashMap<>();
        saveMap.put("ID", gson.toJson(ID));
        saveMap.put("type", gson.toJson(type));
        saveMap.put("sender", gson.toJson(sender));
        saveMap.put("recipient", gson.toJson(recipient));
        saveMap.put("content", gson.toJson(content));
        saveMap.put("date", gson.toJson(date));
        saveMap.put("mailboxID", gson.toJson(mailboxID));
        saveMap.put("archived", gson.toJson(archived));
        saveMap.put("unread", gson.toJson(unread));

        return saveMap;
    }

    @Override
    public void load(Map<String, String> data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ID = Integer.parseInt(gson.fromJson(data.get("ID"), String.class));
        type = gson.fromJson(data.get("type"), String.class);
        sender = gson.fromJson(data.get("sender"), String.class);
        recipient = gson.fromJson(data.get("recipient"), String.class);
        content = gson.fromJson(data.get("content"), String.class);
        date = gson.fromJson(data.get("date"), Date.class);
        mailboxID = Integer.parseInt(gson.fromJson(data.get("mailboxID"), String.class));
        archived = Boolean.parseBoolean(gson.fromJson(data.get("archived"), String.class));
        unread = Boolean.parseBoolean(gson.fromJson(data.get("unread"), String.class));
    }
}