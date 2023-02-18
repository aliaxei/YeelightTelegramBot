package com.kislytgbot.solution.YeelightBot.api.yapi.services.command;

import com.kislytgbot.solution.YeelightBot.api.yapi.dob.YeelightCommand;
import com.kislytgbot.solution.YeelightBot.api.yapi.enumeration.YeelightAdjustAction;
import com.kislytgbot.solution.YeelightBot.api.yapi.enumeration.YeelightAdjustProperty;
import com.kislytgbot.solution.YeelightBot.api.yapi.flow.YeelightFlow;

public interface YeelightCommandService {

    YeelightCommand getColorTemperatureChangingCmd(int colorTemp);

    YeelightCommand getRGBChangingCmd(int r, int g, int b);

    YeelightCommand getBacklightRGBChangingCmd(int r, int g, int b);

    YeelightCommand getHSVChangingCmd(int hue, int sat);

    YeelightCommand getBrightnessChangingCmd(int brightness);

    YeelightCommand getPowerChangingCmd(boolean power);

    YeelightCommand getBacklightPowerChangingCmd(boolean power);

    YeelightCommand getToggleChangingCmd();

    YeelightCommand getDefaultSettingCmd();

    YeelightCommand getFlowStartingCmd(YeelightFlow flow);

    YeelightCommand getFlowStoppingCmd();

    YeelightCommand getCronAddingCmd(int delay);

    YeelightCommand getCronDeletionCmd();

    YeelightCommand getAdjustSettingCmd(YeelightAdjustProperty property, YeelightAdjustAction action);

    YeelightCommand getNameSettingCmd(String name);
}
