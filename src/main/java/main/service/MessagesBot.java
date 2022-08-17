package main.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "telegram-api", url = "${bot.url}${bot.token}")
public interface MessagesBot {

  @GetMapping("/sendmessage")
  void sendMessage(@RequestParam("chat_id") Long chatId, @RequestParam("text") String text);

}
