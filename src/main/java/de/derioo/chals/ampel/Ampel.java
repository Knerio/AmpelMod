package de.derioo.chals.timer;

import de.derioo.chals.ampel.MoveListener;
import lombok.Getter;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.world.level.block.SoundType;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static net.kyori.adventure.text.Component.text;

public class Ampel extends JavaPlugin implements Listener {

  @Getter
  private AmpelObject ampel;


  @Override
  public void onEnable() {
    this.getServer().getPluginManager().registerEvents(new MoveListener(this), this);

    ampel = new AmpelObject();
  }

  public class AmpelObject {
    @Getter
    private Color color;
    private final Random random = new Random();
    private final BossBar bossBar;

    private long nextYellowSwitch;
    private long lastSwitch = 0;

    public AmpelObject() {
      color = Color.GREEN;
      nextYellowSwitch = TimeUnit.SECONDS.toMillis(random.nextInt(3) + 2);
      bossBar = BossBar.bossBar(MiniMessage.miniMessage().deserialize("<green>" + "◼".repeat(20)), 1, BossBar.Color.GREEN, BossBar.Overlay.PROGRESS);

      Bukkit.getScheduler().runTaskTimer(getPlugin(Ampel.class), () -> {
        bossBar.addViewer(Bukkit.getServer());
        bossBar.color(switch (color) {
          case RED -> BossBar.Color.RED;
          case GREEN -> BossBar.Color.GREEN;
          case YELLOW -> BossBar.Color.YELLOW;
        });
        bossBar.name(MiniMessage.miniMessage().deserialize("<"+color.name().toLowerCase()+">" + "◼".repeat(20)));
        if (color == Color.YELLOW || color == Color.RED) return;
        if (lastSwitch - nextYellowSwitch < System.currentTimeMillis()) {
          color = Color.YELLOW;
          Bukkit.getServer().playSound(Sound.sound().type(org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING).build());
          Bukkit.getScheduler().runTaskLater(getPlugin(Ampel.class), () -> {
            Bukkit.getServer().playSound(Sound.sound().type(org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING).build());
            color = Color.RED;
            Bukkit.getScheduler().runTaskLater(getPlugin(Ampel.class), () -> {
              Bukkit.getServer().playSound(Sound.sound().type(org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING).build());
              color = Color.GREEN;
              nextYellowSwitch = TimeUnit.SECONDS.toMillis(random.nextInt(3) + 2);
              lastSwitch = System.currentTimeMillis();
            }, random.nextInt(70) + 5 * 20);
          }, random.nextInt(20) + 30);
        }

      }, 0, 5);
    }

    public enum Color {
      GREEN,
      YELLOW,
      RED
    }
  }

}
