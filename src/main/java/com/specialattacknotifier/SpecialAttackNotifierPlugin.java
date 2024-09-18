package com.specialattacknotifier;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@PluginDescriptor(
	name = "Special Attack Notifier"
)
public class SpecialAttackNotifierPlugin extends Plugin
{
	private boolean OverlayClearedByTimeout = false;
	private static final int SPEC_REGEN_TICKS = 50;
	private int LastSpecialAttackEnergy = -1;
	private boolean wearingLightbearer;
	private int ticksSinceSpecRegen;
	private int SpecSoonOverlayDisplayedForTicks = 0;
	private boolean SpecialAttackSoonOverlayShowing = false;
	private int SpecOverlayDisplayedForTicks = 0;
	private boolean SpecialAttackOverlayShowing = false;

	@Inject
	private SpecialAttackNotifierOverlay specialAttackOverlay;

	@Inject
	private SpecialAttackNotifierSoonOverlay specialAttackSoonOverlay;

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private SpecialAttackNotifierConfig config;

	@Override
	protected void shutDown()
	{
		clearSpecOverlay();
		clearSpecSoonOverlay();
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		if (event.getContainerId() != InventoryID.EQUIPMENT.getId())
		{
			return;
		}

		ItemContainer equipment = event.getItemContainer();
		final boolean hasLightbearer = equipment.contains(ItemID.LIGHTBEARER);
		if (hasLightbearer == wearingLightbearer)
		{
			return;
		}

		ticksSinceSpecRegen = Math.max(0, ticksSinceSpecRegen - 25);
		wearingLightbearer = hasLightbearer;
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged varbitChanged){
		if(config.specialAttackAlert()){
			boolean specialAttackValueChanged = false;
			int specialAttackEnergy = client.getVarpValue(VarPlayer.SPECIAL_ATTACK_PERCENT);
			if(LastSpecialAttackEnergy != specialAttackEnergy){
				LastSpecialAttackEnergy = specialAttackEnergy;
				specialAttackValueChanged = true;
			}
			if(specialAttackEnergy >= (config.specialAttackThreshold() * 10) && !SpecialAttackOverlayShowing && specialAttackValueChanged && specialAttackEnergy < (config.specialAttackThreshold() * 10 + 50)){
				notifySpecialAttack();
			}
		}

	}
	@Subscribe
	public void onGameTick(GameTick event)
	{
		int specialAttackEnergy = client.getVarpValue(VarPlayer.SPECIAL_ATTACK_PERCENT);

		if(config.specialAttackSoonAlert()){
			final int ticksPerSpecRegen = wearingLightbearer ? SPEC_REGEN_TICKS / 2 : SPEC_REGEN_TICKS;

			if(SpecialAttackSoonOverlayShowing){
				if(SpecSoonOverlayDisplayedForTicks == config.specialAttackSoonExpireTime()){
					clearSpecSoonOverlay();
				}
				else{
					SpecSoonOverlayDisplayedForTicks++;
				}
			}

			if(specialAttackEnergy == 1000){
				ticksSinceSpecRegen = 0;
			}
			else
			{
				ticksSinceSpecRegen = (ticksSinceSpecRegen + 1) % ticksPerSpecRegen;
			}
			if((specialAttackEnergy + 100) >= config.specialAttackThreshold() * 10 && (specialAttackEnergy + 50) < config.specialAttackThreshold() * 10){
				if((ticksPerSpecRegen - ticksSinceSpecRegen) == config.specialAttackSoonDelay()){
					notifySpecialAttackSoon();
				}
			}
		}

		if(config.specialAttackAlert()){
			if(SpecialAttackOverlayShowing){
				if(SpecOverlayDisplayedForTicks == config.expireTime()){
					clearSpecOverlay();
				}
				else{
					SpecOverlayDisplayedForTicks++;
				}
			}
			if(specialAttackEnergy < (config.specialAttackThreshold() * 10) && SpecialAttackOverlayShowing){
				clearSpecOverlay();
				OverlayClearedByTimeout = false;
			}
		}


	}

	private void notifySpecialAttackSoon()
	{
		SpecialAttackSoonOverlayShowing = true;
		overlayManager.add(specialAttackSoonOverlay);
	}
	private void notifySpecialAttack()
	{
		SpecialAttackOverlayShowing = true;
		overlayManager.add(specialAttackOverlay);
	}

	private void clearSpecOverlay(){
		overlayManager.remove(specialAttackOverlay);
		SpecialAttackOverlayShowing = false;
		SpecOverlayDisplayedForTicks = 0;

	}
	private void clearSpecSoonOverlay(){
		overlayManager.remove(specialAttackSoonOverlay);
		SpecialAttackSoonOverlayShowing = false;
		SpecSoonOverlayDisplayedForTicks = 0;
	}

	@Provides
	SpecialAttackNotifierConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(SpecialAttackNotifierConfig.class);
	}
}
