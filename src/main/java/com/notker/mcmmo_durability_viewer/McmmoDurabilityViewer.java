package com.notker.mcmmo_durability_viewer;

import com.notker.mcmmo_durability_viewer.config.McmmoDurabilityViewerConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class McmmoDurabilityViewer implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	public static final String DEFAULT_MAX_DURABILITY_TAG = "MMOITEMS_MAX_DURABILITY";
	public static final String DEFAULT_DURABILITY_TAG = "MMOITEMS_DURABILITY";
	public static final String DEFAULT_MAX_CONSUME_TAG = "MMOITEMS_MAX_CONSUME";
	public static final Integer DEFAULT_FULL_CONSUME_VALUE = 5;
	public static final Boolean DEFAULT_SINGLE_COLOR = false;
	public static final Integer DEFAULT_CONSUME_COLOR = 2149464;


	public static McmmoDurabilityViewerConfig config;
	public static ConfigHolder<McmmoDurabilityViewerConfig> configHolder;


	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world! Load Config!");
		configHolder = AutoConfig.register(McmmoDurabilityViewerConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(McmmoDurabilityViewerConfig.class).getConfig();
		LOGGER.info("Config Loaded!");
	}

}
