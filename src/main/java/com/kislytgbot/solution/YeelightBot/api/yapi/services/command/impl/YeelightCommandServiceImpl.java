package com.kislytgbot.solution.YeelightBot.api.yapi.services.command.impl;

import com.kislytgbot.solution.YeelightBot.api.yapi.dob.YeelightCommand;
import com.kislytgbot.solution.YeelightBot.api.yapi.enumeration.YeelightAdjustAction;
import com.kislytgbot.solution.YeelightBot.api.yapi.enumeration.YeelightAdjustProperty;
import com.kislytgbot.solution.YeelightBot.api.yapi.enumeration.YeelightEffect;
import com.kislytgbot.solution.YeelightBot.api.yapi.flow.YeelightFlow;
import com.kislytgbot.solution.YeelightBot.api.yapi.services.command.YeelightCommandService;
import org.springframework.stereotype.Service;

import static com.kislytgbot.solution.YeelightBot.api.yapi.utils.YeelightUtils.clamp;
import static com.kislytgbot.solution.YeelightBot.api.yapi.utils.YeelightUtils.clampAndComputeRGBValue;

@Service
public class YeelightCommandServiceImpl implements YeelightCommandService {

    /**
     * Device effect setting for commands
     */
    private YeelightEffect effect;
    /**
     * Device effect duration setting for commands
     */
    private int duration;

    /**
     * Constructor for Yeelight command service. Initial effect set to 'sudden'
     */
    public YeelightCommandServiceImpl() {
        this.setEffect(YeelightEffect.SUDDEN);
        this.setDuration(0);
    }

    /**
     * Setter for Yeelight device effect
     *
     * @param effect Effect to set (if null, 'sudden' is chosen)
     */
    public void setEffect(YeelightEffect effect) {
        this.effect = effect == null ? YeelightEffect.SUDDEN : effect;
    }

    /**
     * Setter for Yeelight device effect duration
     *
     * @param duration Duration to set (&gt;= 0)
     */
    public void setDuration(int duration) {
        this.duration = Math.max(0, duration);
    }

    @Override
    public YeelightCommand getColorTemperatureChangingCmd(int colorTemp) {
        colorTemp = clamp(colorTemp, 1700, 6500);
        return new YeelightCommand("set_ct_abx", colorTemp, this.effect.getValue(), this.duration);
    }

    @Override
    public YeelightCommand getRGBChangingCmd(int r, int g, int b) {
        int rgbValue = clampAndComputeRGBValue(r, g, b);
        return new YeelightCommand("set_rgb", rgbValue, this.effect.getValue(), this.duration);
    }

    @Override
    public YeelightCommand getBacklightRGBChangingCmd(int r, int g, int b) {
        int rgbValue = clampAndComputeRGBValue(r, g, b);
        return new YeelightCommand("bg_set_rgb", rgbValue, this.effect.getValue(), this.duration);
    }

    @Override
    public YeelightCommand getHSVChangingCmd(int hue, int sat) {
        hue = clamp(hue, 0, 359);
        sat = clamp(sat, 0, 100);
        return new YeelightCommand("set_hsv", hue, sat, this.effect.getValue(), this.duration);
    }

    @Override
    public YeelightCommand getBrightnessChangingCmd(int brightness) {
        brightness = clamp(brightness, 1, 100);
        return new YeelightCommand("set_bright", brightness, this.effect.getValue(), this.duration);
    }

    @Override
    public YeelightCommand getPowerChangingCmd(boolean power) {
        String powerStr = power ? "on" : "off";
        return new YeelightCommand("set_power", powerStr, this.effect.getValue(), this.duration);
    }

    @Override
    public YeelightCommand getBacklightPowerChangingCmd(boolean power) {
        String powerStr = power ? "on" : "off";
        return new YeelightCommand("bg_set_power", powerStr, this.effect.getValue(), this.duration);
    }

    @Override
    public YeelightCommand getToggleChangingCmd() {
        return new YeelightCommand("toggle");
    }

    @Override
    public YeelightCommand getDefaultSettingCmd() {
        return new YeelightCommand("set_default");
    }

    @Override
    public YeelightCommand getFlowStartingCmd(YeelightFlow flow) {
        return new YeelightCommand("start_cf", flow.createCommandParams());
    }

    @Override
    public YeelightCommand getFlowStoppingCmd() {
        return new YeelightCommand("stop_cf");
    }

    @Override
    public YeelightCommand getCronAddingCmd(int delay) {
        delay = Math.max(0, delay);
        return new YeelightCommand("cron_add", 0, delay);
    }

    @Override
    public YeelightCommand getCronDeletionCmd() {
        return new YeelightCommand("cron_del", 0);
    }

    @Override
    public YeelightCommand getAdjustSettingCmd(YeelightAdjustProperty property, YeelightAdjustAction action) {
        String actionValue = action == null ? YeelightAdjustAction.CIRCLE.getValue() : action.getValue();
        String propertyValue = property == null ? YeelightAdjustProperty.COLOR.getValue() : property.getValue();
        return new YeelightCommand("set_adjust", actionValue, propertyValue);
    }

    @Override
    public YeelightCommand getNameSettingCmd(String name) {
        if (name == null) {
            name = "";
        }
        return new YeelightCommand("set_name", name);
    }
}
