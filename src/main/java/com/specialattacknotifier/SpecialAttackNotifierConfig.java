package com.specialattacknotifier;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup("Config")
public interface SpecialAttackNotifierConfig extends Config
{
	@ConfigSection(
			name = "Special Attack Threshold",
			description = "Special Attack Threshold",
			position = 0
	)
	String specialAttackSettings = "specialAttackThreshold";
	@ConfigItem(
			keyName = "specialAttackAlert",
			name = "Enabled",
			description = "Displays infobox when special attack is at set threshold",
			position = 1,
			section = specialAttackSettings
	)
	default boolean specialAttackAlert(){
		return true;
	}

	@Range(max=100, min=0)
	@ConfigItem(
			keyName = "specialAttackThreshold",
			name = "Threshold %",
			description = "Threshold to display special attack alert",
			position = 2,
			section = specialAttackSettings

	)
	default int specialAttackThreshold(){
		return 100;
	}

	@ConfigItem(
			keyName = "shouldFlash",
			name = "Flash notification box",
			description = "Flashes notification box",
			position = 3,
			section = specialAttackSettings
	)
	default boolean shouldFlash(){
		return true;
	}

	@ConfigItem(
			keyName = "expireTime",
			name = "Expire (ticks)",
			description = "Notification box expire time in ticks",
			position = 4,
			section = specialAttackSettings

	)
	default int expireTime(){
		return 10;
	}

	@Alpha
	@ConfigItem(
			keyName = "notificationBoxColor1",
			name = "Color 1",
			description =  "First color for notification box flash",
			position = 5,
			section = specialAttackSettings
	)
	default Color notificationBoxColor1(){return Color.RED;}

	@Alpha
	@ConfigItem(
			keyName = "notificationBoxColor2",
			name = "Color 2",
			description =  "Second color for notification box flash",
			position = 6,
			section = specialAttackSettings
	)
	default Color notificationBoxColor2(){return Color.GRAY;}
	@ConfigSection(
			name = "Special Attack Soon",
			description = "Special Attack Soon",
			position = 1
	)
	String specialAttackSoon = "specialAttackSoon";
	@ConfigItem(
			keyName = "specialAttackSoonAlert",
			name = "Enabled",
			description = "Display alert before special attack is recharged to specified threshold",
			position = 7,
			section = specialAttackSoon

	)
	default boolean specialAttackSoonAlert(){
		return true;
	}

	@Range(max=30, min=1)
	@ConfigItem(
			keyName = "specialAttackSoonDelay",
			name = "Delay (ticks)",
			description = "Delay to show special attack soon alert in ticks",
			position = 8,
			section = specialAttackSoon

	)
	default int specialAttackSoonDelay(){
		return 5;
	}

	@ConfigItem(
			keyName = "specialAttackSoonShouldFlash",
			name = "Flash notification box",
			description = "Flashes notification box",
			position = 9,
			section = specialAttackSoon
	)
	default boolean specialAttackSoonShouldFlash(){
		return true;
	}

	@Range(max=10, min=1)
	@ConfigItem(
			keyName = "specialAttackSoonExpireTime",
			name = "Expire (ticks)",
			description = "Notification box expire time in ticks",
			position = 10,
			section = specialAttackSoon

	)
	default int specialAttackSoonExpireTime(){
		return 6;
	}
	
	@Alpha
	@ConfigItem(
			keyName = "specialAttackSoonNotificationBoxColor1",
			name = "Color 1",
			description =  "First color for spec soon notification box flash",
			position = 11,
			section = specialAttackSoon
	)
	default Color specialAttackSoonNotificationBoxColor1(){return Color.CYAN;}

	@Alpha
	@ConfigItem(
			keyName = "specialAttackSooNotificationBoxColor2",
			name = "Color 2",
			description =  "Second color for spec soon notification box flash",
			position = 12,
			section = specialAttackSoon
	)
	default Color specialAttackSooNotificationBoxColor2(){return Color.GRAY;}




}
