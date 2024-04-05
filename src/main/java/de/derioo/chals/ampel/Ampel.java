package de.derioo.chals.timer;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static net.kyori.adventure.text.Component.text;

@DefaultQualifier(NonNull.class)
public final class Ampel extends JavaPlugin implements Listener {

  private AmpelObject ampel;


  @Override
  public void onEnable() {
    this.getServer().getPluginManager().registerEvents(this, this);

    ampel = new AmpelObject();
  }

  class AmpelObject {
    private Color color;
    private final Random random = new Random();
    private long nextYellowSwitch;

    private long lastSwitch = 0;
    public AmpelObject() {
      color = Color.GREEN;
      nextYellowSwitch = TimeUnit.SECONDS.toMillis(random.nextInt(3) + 2);


      Bukkit.getScheduler().runTaskTimer(getPlugin(Ampel.class), () -> {
        if (color == Color.YELLOW || color == Color.RED) return;
        if (lastSwitch - nextYellowSwitch < System.currentTimeMillis()) {
          color = Color.YELLOW;
          Bukkit.getScheduler().runTaskLater(getPlugin(Ampel.class), () -> {
            color = Color.RED;
            Bukkit.getScheduler().runTaskLater(getPlugin(Ampel.class), () -> {
              color = Color.GREEN;
              nextYellowSwitch = TimeUnit.SECONDS.toMillis(random.nextInt(3) + 2);
              lastSwitch = System.currentTimeMillis();
            }, random.nextInt(20) + 30);
          }, random.nextInt(20) + 30);
        }

      }, 0, 5);
    }

    enum Color {
      GREEN,
      YELLOW,
      RED
    }
  }

}
