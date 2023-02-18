package com.kislytgbot.solution.YeelightBot.command;

import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightResultErrorException;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightSocketException;
import com.kislytgbot.solution.YeelightBot.api.yapi.services.YeelightDeviceService;
import com.kislytgbot.solution.YeelightBot.config.BotConfig;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public abstract class BotCommand {
    @Autowired
    protected YeelightDeviceService deviceService;

    protected BotConfig config;

    protected BotCommand(BotConfig config) {
        this.config = config;
    }

    public String execute(Update update) throws YeelightResultErrorException, YeelightSocketException {
        String executionResult;
        try {
            performAction(update);
            executionResult = "Action performed";
        } catch (Exception e) {
            executionResult = "Error occurred while action execution " + ExceptionUtils.getStackTrace(e);
        }
        return executionResult;
    }

    protected abstract void performAction(Update update) throws YeelightResultErrorException, YeelightSocketException;
}
