package dansplugins.mailboxes.services;

import dansplugins.mailboxes.objects.Mailbox;
import dansplugins.mailboxes.objects.Message;
import dansplugins.mailboxes.objects.PlayerMessage;
import dansplugins.mailboxes.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MailService implements IMailService {

    private static MailService instance;

    private MailService() {

    }

    public static MailService getInstance() {
        if (instance == null) {
            instance = new MailService();
        }
        return instance;
    }

    public boolean sendMessage(Message message) {
        Logger.getInstance().log("Attempting to send message with ID " + message.getID());
        Logger.getInstance().log("Message Sender: " + message.getSender());
        Logger.getInstance().log("Message Recipient: " + message.getRecipient());

        if (message instanceof PlayerMessage) {
            PlayerMessage playerMessage = (PlayerMessage) message;

            Logger.getInstance().log("Sender UUID: " + playerMessage.getSenderUUID());
            Logger.getInstance().log("Recipient UUID: " + playerMessage.getRecipientUUID());

            Mailbox mailbox = MailboxLookupService.getInstance().lookup(playerMessage.getRecipientUUID());

            if (mailbox == null) {
                Logger.getInstance().log("Mailbox was null.");
                return false;
            }

            playerMessage.setMailboxID(mailbox.getID());

            mailbox.addActiveMessage(playerMessage);
            Player player = Bukkit.getPlayer(playerMessage.getRecipientUUID());
            try {
                player.sendMessage(ChatColor.AQUA + "You've received a message. Type /m list to view your messages.");
            } catch(Exception e) {

            }
            return true;
        }
        else {
            return false;
        }
    }

}
