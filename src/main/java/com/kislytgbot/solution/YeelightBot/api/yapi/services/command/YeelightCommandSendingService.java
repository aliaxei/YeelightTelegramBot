package com.kislytgbot.solution.YeelightBot.api.yapi.services.command;

import com.kislytgbot.solution.YeelightBot.api.yapi.dob.YeelightCommand;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightResultErrorException;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightSocketException;

import java.util.List;

public interface YeelightCommandSendingService {

    /**
     * Send commands on socket
     *
     * @param commands Command to send
     * @return Raw result array
     * @throws YeelightSocketException      when socket error occurs
     * @throws YeelightResultErrorException when commands result is an error
     */
    List<List<String>> sendCommand(YeelightCommand... commands) throws YeelightSocketException, YeelightResultErrorException;
}
