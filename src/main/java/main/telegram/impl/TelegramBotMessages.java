package main.telegram.impl;

import main.telegram.MessagesBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotMessages {

  private MessagesBot bot;

  @Value("${bot.chatId}")
  private long chatId;

  @Autowired
  public TelegramBotMessages(MessagesBot bot) {
    this.bot = bot;
  }

  public void sendMessage(String text) {
    bot.sendMessage(chatId, text);
  }

}
