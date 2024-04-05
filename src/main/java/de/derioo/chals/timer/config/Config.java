package de.derioo.chals.timer.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.core.config.json.JsonConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Config {

  private final JsonObject content;
  private final Plugin plugin;

  public Config(Plugin plugin) {
    this.plugin = plugin;
    new File("./plugins/sc/Timer").mkdirs();
    File configFile = new File(new File("./plugins/sc/Timer"), "config.json");
    try {
      if (configFile.createNewFile()) {
        content = getDefault();
      } else {
        try (FileReader fileReader = new FileReader(configFile);) {
          content = JsonParser.parseString(IOUtils.toString(fileReader)).getAsJsonObject();
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    save();
  }

  public JsonObject get() {
    return content;
  }

  private JsonObject getDefault() throws IOException {
    InputStream resource = plugin.getResource("config.json");
    return JsonParser.parseString(IOUtils.toString(resource, StandardCharsets.UTF_8)).getAsJsonObject();
  }

  public void save() {
    File configFile = new File(new File("./plugins/sc/Timer"), "config.json");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
      writer.write(content.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
