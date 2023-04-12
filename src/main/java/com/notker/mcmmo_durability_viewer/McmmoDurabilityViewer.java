package com.notker.mcmmo_durability_viewer;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class McmmoDurabilityViewer implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	public static final String MAX_DURABILITY_KEY = "MMOITEMS_MAX_DURABILITY";
	public static final String DURABILITY_KEY = "MMOITEMS_DURABILITY";
	public static final String MAX_CONSUME_KEY = "MMOITEMS_MAX_CONSUME";

	public static final Integer FULL_CONSUME_VALUE = 5;


	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}
}
