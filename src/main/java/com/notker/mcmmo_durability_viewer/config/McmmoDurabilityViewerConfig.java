package com.notker.mcmmo_durability_viewer.config;

import com.notker.mcmmo_durability_viewer.McmmoDurabilityViewer;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "mcmmo_durability_viewer")
public class McmmoDurabilityViewerConfig implements ConfigData {


    @Comment("Durability Bar")
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public DurabilityBar durabilityBar = new DurabilityBar();

    /**
     * Durability Bar configuration schema.
     */
    public static class DurabilityBar {
        @Comment("Max Durability Nbt Tag")
        public String MAX_DURABILITY_TAG = McmmoDurabilityViewer.DEFAULT_MAX_DURABILITY_TAG;

        @Comment("Durability Nbt Tag")
        public String DURABILITY_TAG = McmmoDurabilityViewer.DEFAULT_DURABILITY_TAG;
    }

    @Comment("Consume Value")
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public ConsumeValue consumeValue = new ConsumeValue();

    /**
     * Durability Bar configuration schema.
     */
    public static class ConsumeValue {

        @Comment("Max Consume Nbt Tag")
        public String MAX_CONSUME_TAG = McmmoDurabilityViewer.DEFAULT_MAX_CONSUME_TAG;

        @Comment("At this value the Color is Green \n Below it fades to Red")
        @ConfigEntry.BoundedDiscrete(min = 1, max = 64)
        public Integer FULL_CONSUME_VALUE = McmmoDurabilityViewer.DEFAULT_FULL_CONSUME_VALUE;

        @Comment("Single color for the Consume number")
        public boolean SINGLE_COLOR = McmmoDurabilityViewer.DEFAULT_SINGLE_COLOR;

        @Comment("The color for the Consume number")
        @ConfigEntry.ColorPicker
        public Integer CONSUME_COLOR = McmmoDurabilityViewer.DEFAULT_CONSUME_COLOR;

    }
}
