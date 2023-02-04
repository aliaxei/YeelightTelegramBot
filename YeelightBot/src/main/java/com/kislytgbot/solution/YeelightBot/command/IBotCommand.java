package com.kislytgbot.solution.YeelightBot.command;

import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightResultErrorException;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightSocketException;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface IBotCommand {

    void execute(Update update) throws YeelightResultErrorException, YeelightSocketException;
}
