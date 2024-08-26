package com.specialattacknotifier;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class SpecialAttackNotifierTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(SpecialAttackNotifierPlugin.class);
		RuneLite.main(args);
	}
}