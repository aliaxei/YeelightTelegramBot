package com.kislytgbot.solution.YeelightBot.service;

import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightResultErrorException;
import com.kislytgbot.solution.YeelightBot.api.yapi.exception.YeelightSocketException;
import com.kislytgbot.solution.YeelightBot.command.annotation.Command;
import com.kislytgbot.solution.YeelightBot.config.BotConfig;
import com.kislytgbot.solution.YeelightBot.command.IBotCommand;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Set;

@Slf4j
@Component
public class BotService extends TelegramLongPollingBot {

    final BotConfig config;

    @Autowired
    private ApplicationContext context;

    public BotService(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (!update.hasMessage() && !message.hasText()) {
            return;
        }
        String text = message.getText();
        Long chatId = message.getChatId();

        IBotCommand actionForExecute = getBeanForExecute(text);

        try {
            actionForExecute.execute(update);
        } catch (YeelightResultErrorException | YeelightSocketException e) {
            log.error("Error while command executing ", e);
            sendMessage(chatId, "ERROR WHILE ACTION EXECUTING");
        }

        sendMessage(396655048, text + " - your default text from - " + update.getMessage().getFrom().getFirstName());

    }

    private IBotCommand getCommandAction(Class<?> actionForExecute, IBotCommand iBotCommand) {
        Class<IBotCommand> botAction = (Class<IBotCommand>) actionForExecute;
        return botAction.cast(iBotCommand);
    }

    private IBotCommand getBeanForExecute(String messageText) {
        Reflections reflections = new Reflections("com.kislytgbot.solution.YeelightBot.command");

        Set<Class<?>> commandActions = reflections.getTypesAnnotatedWith(Command.class);
        Class<?> actionForExecute = commandActions.stream()
                .filter(action -> messageText.equals(action.getAnnotation(Command.class).value()))
                .findFirst()
                .orElse(null);

        return  (IBotCommand) context.getBean(decapitalize(actionForExecute.getSimpleName()));
    }

    private void sendMessage(long chatId, String answer) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(answer);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }

    }

    public static String decapitalize(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }

        char c[] = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);

        return new String(c);
    }
}
