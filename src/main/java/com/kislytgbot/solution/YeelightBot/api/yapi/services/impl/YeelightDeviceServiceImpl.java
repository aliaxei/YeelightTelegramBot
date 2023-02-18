package com.kislytgbot.solution.YeelightBot.api.yapi.services.impl;

import com.kislytgbot.solution.YeelightBot.api.yapi.constants.YeelightConstants;
import com.kislytgbot.solution.YeelightBot.api.yapi.dob.YeelightCommand;
import com.kislytgbot.solution.YeelightBot.api.yapi.enumeration.YeelightAdjustAction;
import com.kislytgbot.solution.YeelightBot.api.yapi.enumeration.YeelightAdjustProperty;
import com.kislytgbot.solution.YeelightBot.api.yapi.enumeration.YeelightEffect;
import com.kislytgbot.solution.YeelightBot.api.yapi.enumeration.YeelightProperty;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightResultErrorException;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightSocketException;
import com.kislytgbot.solution.YeelightBot.api.yapi.flow.YeelightFlow;
import com.kislytgbot.solution.YeelightBot.api.yapi.services.YeelightDeviceService;
import com.kislytgbot.solution.YeelightBot.api.yapi.services.command.YeelightCommandSendingService;
import com.kislytgbot.solution.YeelightBot.api.yapi.services.command.YeelightCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class YeelightDeviceServiceImpl implements YeelightDeviceService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private YeelightCommandService commandService;

    @Autowired
    private YeelightCommandSendingService commandSendingService;

    private YeelightEffect effect;
    private int duration;

    /**
     * Constructor for Yeelight device. Initial effect set to 'sudden'
     */
    public YeelightDeviceServiceImpl() {
        this.setEffect(YeelightEffect.SUDDEN);
        this.setDuration(0);
    }

    @Override
    public void setEffect(YeelightEffect effect) {
        this.effect = effect == null ? YeelightEffect.SUDDEN : effect;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = Math.max(0, duration);
    }

    @Override
    public Map<String, String> getProperties(YeelightProperty... properties) throws YeelightResultErrorException, YeelightSocketException {
        YeelightProperty[] expectedProperties = properties.length == 0 ? YeelightProperty.values() : properties;
        Object[] expectedPropertiesValues = Stream.of(expectedProperties).map(YeelightProperty::getValue).toArray();
        YeelightCommand command = new YeelightCommand("get_prop", expectedPropertiesValues);
        List<String> result = commandSendingService.sendCommand(command).iterator().next();
        Map<String, String> propertyToValueMap = new HashMap<>();
        if (result.contains(YeelightConstants.NEGATIVE_RESPONSE)) {
            propertyToValueMap.put("result", result.iterator().next());
        } else {
            for (int i = 0; i < expectedProperties.length; i++) {
                propertyToValueMap.put(expectedProperties[i].toString(), result.get(i));
            }
        }
        return propertyToValueMap;
    }

    @Override
    public void setColorTemperature(int colorTemp) throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand colorTemperatureChangingCmd = commandService.getColorTemperatureChangingCmd(colorTemp);
        commandSendingService.sendCommand(colorTemperatureChangingCmd);
    }

    @Override
    public void setRGB(int r, int g, int b) throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand rgbChangingCmd = commandService.getRGBChangingCmd(r, g, b);
        commandSendingService.sendCommand(rgbChangingCmd);
    }

    @Override
    public void setBacklightRGB(int r, int g, int b) throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand backlightRGBChangingCmd = commandService.getBacklightRGBChangingCmd(r, g, b);
        commandSendingService.sendCommand(backlightRGBChangingCmd);
    }

    @Override
    public void setHSV(int hue, int sat) throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand hsvChangingCmd = commandService.getHSVChangingCmd(hue, sat);
        commandSendingService.sendCommand(hsvChangingCmd);
    }

    @Override
    public void setBrightness(int brightness) throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand brightnessChangingCmd = commandService.getBrightnessChangingCmd(brightness);
        commandSendingService.sendCommand(brightnessChangingCmd);
    }

    @Override
    public void setPower(boolean power) throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand powerChangingCmd = commandService.getPowerChangingCmd(power);
        commandSendingService.sendCommand(powerChangingCmd);
    }

    @Override
    public void setBacklightPower(boolean power) throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand command = commandService.getBacklightPowerChangingCmd(power);
        commandSendingService.sendCommand(command);
    }

    @Override
    public void toggle() throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand toggleChangingCmd = commandService.getToggleChangingCmd();
        commandSendingService.sendCommand(toggleChangingCmd);
    }

    @Override
    public void setDefault() throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand defaultSettingCmd = commandService.getDefaultSettingCmd();
        commandSendingService.sendCommand(defaultSettingCmd);
    }

    @Override
    public void startFlow(YeelightFlow flow) throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand flowStartingCmd = commandService.getFlowStartingCmd(flow);
        commandSendingService.sendCommand(flowStartingCmd);
    }

    @Override
    public void stopFlow() throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand flowStoppingCmd = commandService.getFlowStoppingCmd();
        commandSendingService.sendCommand(flowStoppingCmd);
    }

    @Override
    public void addCron(int delay) throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand command = commandService.getCronAddingCmd(delay);
        commandSendingService.sendCommand(command);
    }

    @Override
    public int getCronDelay() throws YeelightResultErrorException, YeelightSocketException {
        Map<String, String> propertyToString = this.getProperties(YeelightProperty.DELAY_OFF);
        try {
            return Integer.parseInt(propertyToString.get(YeelightProperty.DELAY_OFF.toString()));
        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public void deleteCron() throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand cronDeletionCmd = commandService.getCronDeletionCmd();
        commandSendingService.sendCommand(cronDeletionCmd);
    }

    @Override
    public void setAdjust(YeelightAdjustProperty property, YeelightAdjustAction action) throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand adjustSettingCmd = commandService.getAdjustSettingCmd(property, action);
        commandSendingService.sendCommand(adjustSettingCmd);
    }

    @Override
    public void setName(String name) throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand nameSettingCmd = commandService.getNameSettingCmd(name);
        commandSendingService.sendCommand(nameSettingCmd);
    }

    @Override
    public void setRedBacklight() throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand rgbChangingCmd = commandService.getRGBChangingCmd(255, 0, 0);
        YeelightCommand powerChangingCmd = commandService.getPowerChangingCmd(false);
        YeelightCommand backlightPowerChangingCmd = commandService.getBacklightPowerChangingCmd(true);
        commandSendingService.sendCommand(rgbChangingCmd, powerChangingCmd, backlightPowerChangingCmd);
    }

    @Override
    public void turnOffLight() throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand powerChangingCmd = commandService.getPowerChangingCmd(false);
        YeelightCommand backlightPowerChangingCmd = commandService.getBacklightPowerChangingCmd(false);
        commandSendingService.sendCommand(powerChangingCmd, backlightPowerChangingCmd);
    }
}
