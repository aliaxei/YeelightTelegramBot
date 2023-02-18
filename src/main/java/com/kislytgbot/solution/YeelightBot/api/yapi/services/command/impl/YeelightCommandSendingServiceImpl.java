package com.kislytgbot.solution.YeelightBot.api.yapi.services.command.impl;

import com.kislytgbot.solution.YeelightBot.api.yapi.constants.YeelightConstants;
import com.kislytgbot.solution.YeelightBot.api.yapi.dob.YeelightCommand;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightResultErrorException;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightSocketException;
import com.kislytgbot.solution.YeelightBot.api.yapi.result.YeelightResultError;
import com.kislytgbot.solution.YeelightBot.api.yapi.result.YeelightResultOk;
import com.kislytgbot.solution.YeelightBot.api.yapi.services.command.YeelightCommandSendingService;
import com.kislytgbot.solution.YeelightBot.api.yapi.socket.YeelightSocketHolder;
import com.kislytgbot.solution.YeelightBot.config.BotConfig;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class YeelightCommandSendingServiceImpl implements YeelightCommandSendingService {

    private BotConfig config;

    YeelightCommandSendingServiceImpl(BotConfig config) {
        this.config = config;
    }

    @Override
    public List<List<String>> sendCommand(YeelightCommand... commands) throws YeelightSocketException, YeelightResultErrorException {
        String jsonCommand = Arrays.stream(commands)
                .map(YeelightCommand::toJson)
                .collect(Collectors.joining("\r\n")) + "\r\n";
        //String jsonCommand = commands.toJson() + "\r\n";
        YeelightSocketHolder socketHolder = new YeelightSocketHolder(config.getYeelightDeviceIp(), config.getYeelightDevicePort());
        socketHolder.send(jsonCommand);
        List<List<String>> responses = new LinkedList<>();
        for (YeelightCommand command : commands) {
            responses.add(this.readUntilResult(command.getId(), socketHolder));
        }
        return responses;
    }

    /**
     * Read socket until result with corresponding ID is found
     *
     * @param id ID of result to find
     * @return Raw result array
     * @throws YeelightSocketException      when socket error occurs
     * @throws YeelightResultErrorException when result founds is an error
     */
    private List<String> readUntilResult(int id, YeelightSocketHolder socketHolder) throws YeelightSocketException, YeelightResultErrorException {
        for (int attempt = 0; attempt <= 6; attempt++) {
            String datas = socketHolder.readLine();
            // parsing datas
            Optional<YeelightResultOk> okResult = YeelightResultOk.from(datas);
            Optional<YeelightResultError> errorResult = YeelightResultError.from(datas);
            // check results
            if (okResult.isPresent() && okResult.get().getId() == id) {
                socketHolder.dropSocketConnection();
                return okResult.get().getResult();
            } else if (errorResult.isPresent() && errorResult.get().getId() == id) {
                throw errorResult.get().getException();
            }
        }
        return Collections.singletonList(YeelightConstants.NEGATIVE_RESPONSE);
    }
}
