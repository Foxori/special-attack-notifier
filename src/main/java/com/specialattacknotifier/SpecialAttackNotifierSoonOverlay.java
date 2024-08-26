package com.specialattacknotifier;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.awt.*;
import java.io.ObjectInputFilter;

public class SpecialAttackNotifierSoonOverlay extends OverlayPanel {
    private final Client client;
    private final SpecialAttackNotifierConfig config;

    @Inject
    private SpecialAttackNotifierSoonOverlay(Client client, SpecialAttackNotifierConfig config)
    {
        this.client = client;
        this.config = config;
    }
    @Override
    public Dimension render(Graphics2D graphics){
        panelComponent.getChildren().clear();

        panelComponent.getChildren().add((LineComponent.builder())
                .left("Special attack ready in " + config.specialAttackSoonDelay() + " ticks!")
                .build());

        if (config.shouldFlash()) {
            if (client.getGameCycle() % 40 >= 20)
            {
                panelComponent.setBackgroundColor(config.specialAttackSoonNotificationBoxColor1());
            } else
            {
                panelComponent.setBackgroundColor(config.specialAttackSooNotificationBoxColor2());
            }
        } else {
            panelComponent.setBackgroundColor(config.specialAttackSoonNotificationBoxColor1());
        }

        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);

        return panelComponent.render(graphics);
    }

}
