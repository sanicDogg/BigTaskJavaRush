package Chat.client;

import Chat.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BotClient extends Client {
    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        return "date_bot_" + (int)(Math.random() * 100);
    }

    public class BotSocketThread extends Client.SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("HI I'AM TIMER BOT");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);

            if (!message.contains(":") || message.split(":").length > 1 || message.isEmpty())
                return;

            String senderName = message.substring(0, message.indexOf(":"));
            String textMessage = message.substring(message.indexOf(":") + 2);
            SimpleDateFormat dataRequest = null;

            switch (textMessage)
            {
                case "дата" : dataRequest = new SimpleDateFormat("d.MM.YYYY"); break;
                case "день" : dataRequest = new SimpleDateFormat("d"); break;
                case "месяц" : dataRequest = new SimpleDateFormat("MMMM"); break;
                case "год" : dataRequest = new SimpleDateFormat("YYYY"); break;
                case "время" : dataRequest = new SimpleDateFormat("H:mm:ss"); break;
                case "час" : dataRequest = new SimpleDateFormat("H"); break;
                case "минуты" : dataRequest = new SimpleDateFormat("m"); break;
                case "секунды" : dataRequest = new SimpleDateFormat("s"); break;
                default: dataRequest = new SimpleDateFormat(); break;
            }

            if (dataRequest != null) {
                sendTextMessage(String.format("Информация для %s %s",
                        senderName, dataRequest.format(Calendar.getInstance().getTime())));
            }
        }
    }

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }
}
