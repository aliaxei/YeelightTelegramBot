package com.kislytgbot.solution.YeelightBot.command;

import com.kislytgbot.solution.YeelightBot.api.yapi.YeelightDevice;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightSocketException;
import com.kislytgbot.solution.YeelightBot.config.BotConfig;
import org.springframework.stereotype.Component;

@Component
public abstract class BotCommand {

    final BotConfig config;
    protected YeelightDevice device;

    protected BotCommand(BotConfig config) throws YeelightSocketException {
        this.config = config;
        try {
            this.device = new YeelightDevice(config.getYeelightDeviceIp());
        } catch (Exception e) {
            config.getBotName();
        }
    }
}
