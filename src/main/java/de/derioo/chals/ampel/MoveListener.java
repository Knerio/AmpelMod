package de.derioo.chals.ampel;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

  private final Ampel ampel;


  public MoveListener(Ampel ampel) {
    this.ampel = ampel;
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    if (ampel.getAmpel().getColor() == Ampel.AmpelObject.Color.RED) {
      if (!event.hasChangedPosition()) return;
      event.getPlayer().setHealth(0);
    }
  }


}
