package fr.warmadon.dev.settings;

import com.jagrosh.jdautilities.command.GuildSettingsManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import net.dv8tion.jda.core.entities.Guild;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class SettingsManager implements GuildSettingsManager
{
    private final HashMap<Long,Settings> settings;

    public SettingsManager()
    {
        this.settings = new HashMap<>();
        try {
            JSONObject loadedSettings = new JSONObject(new String(Files.readAllBytes(Paths.get("serversettings.json"))));
            loadedSettings.keySet().forEach((id) -> {
                JSONObject o = loadedSettings.getJSONObject(id);
                settings.put(Long.parseLong(id), new Settings(this,
                        o.has("text_channel_id") ? o.getString("text_channel_id") : null,
                        o.has("voice_channel_id")? o.getString("voice_channel_id"): null,
                        o.has("dj_role_id")      ? o.getString("dj_role_id")      : null,
                        o.has("volume")          ? o.getInt("volume")             : 100,
                        o.has("default_playlist")? o.getString("default_playlist"): null,
                        o.has("repeat")          ? o.getBoolean("repeat")         : false));
            });
        } catch(IOException | JSONException e) {
            LoggerFactory.getLogger("Settings").warn("Failed to load server settings (this is normal if no settings have been set yet): "+e);
        }
    }
    
    /**
     * Gets non-null settings for a Guild
     * 
     * @param guild the guild to get settings for
     * @return the existing settings, or new settings for that guild
     */
    @Override
    public Settings getSettings(Guild guild)
    {
        return getSettings(guild.getIdLong());
    }
    
    public Settings getSettings(long guildId)
    {
        return settings.computeIfAbsent(guildId, id -> createDefaultSettings());
    }
    
    private Settings createDefaultSettings()
    {
        return new Settings(this, 0, 0, 0, 100, null, false);
    }
    
    protected void writeSettings()
    {
        JSONObject obj = new JSONObject();
        settings.keySet().stream().forEach(key -> {
            JSONObject o = new JSONObject();
            Settings s = settings.get(key);
            if(s.textId!=0)
                o.put("text_channel_id", Long.toString(s.textId));
            if(s.voiceId!=0)
                o.put("voice_channel_id", Long.toString(s.voiceId));
            if(s.roleId!=0)
                o.put("dj_role_id", Long.toString(s.roleId));
            if(s.getVolume()!=100)
                o.put("volume",s.getVolume());
            if(s.getDefaultPlaylist()!=null)
                o.put("default_playlist", s.getDefaultPlaylist());
            if(s.getRepeatMode())
                o.put("repeat", true);
            obj.put(Long.toString(key), o);
        });
        try {
            Files.write(Paths.get("serversettings.json"), obj.toString(4).getBytes());
        } catch(IOException ex){
            LoggerFactory.getLogger("Settings").warn("Échec d'écriture dans le fichier: "+ex);
        }
    }
}
