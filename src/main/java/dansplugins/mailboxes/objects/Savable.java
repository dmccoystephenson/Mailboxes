package dansplugins.mailboxes.objects;

import java.util.Map;

public interface Savable {
    Map<String, String> save();
    void load(Map<String, String> data);
}
