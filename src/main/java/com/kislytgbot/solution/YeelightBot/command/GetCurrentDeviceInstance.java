package com.kislytgbot.solution.YeelightBot.command;

import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightResultErrorException;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightSocketException;
import com.kislytgbot.solution.YeelightBot.command.annotation.Command;
import com.kislytgbot.solution.YeelightBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Command("/getcurrentinstance")
@Component
public class GetCurrentDeviceInstance extends BotCommand {

    protected GetCurrentDeviceInstance(BotConfig config) {
        super(config);
    }

    @Override
    public String execute(Update update) throws YeelightResultErrorException, YeelightSocketException {
        return device.getProperties().toString();
    }

    @Override
    protected void performAction(Update update) throws YeelightResultErrorException, YeelightSocketException {}
}
