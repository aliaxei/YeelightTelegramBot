package com.kislytgbot.solution.YeelightBot.command;

import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightResultErrorException;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightSocketException;
import com.kislytgbot.solution.YeelightBot.command.annotation.Command;
import com.kislytgbot.solution.YeelightBot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Command("/lighton")
@Component
public class LightOn extends BotCommand {
    protected LightOn(BotConfig config) {
        super(config);
    }

    @Override
    protected void performAction(Update update) throws YeelightResultErrorException, YeelightSocketException {
        device.setPower(true);
    }
}
