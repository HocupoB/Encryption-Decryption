package encryptdecrypt;

public class Main {
    public static void main(String[] args) {
        final Message message = new Message(args);
        final MessageContext messageContext = new MessageContext();

        switch (message.getMode()) {
            case "enc":
                messageContext.setMethod(new EncryptMessage());
                break;
            case "dec":
                messageContext.setMethod(new DecodeMessage());
                break;
        }

        if (messageContext != null) {
            messageContext.crackMessage(message);
        } else {
            throw new IllegalArgumentException("Error that mode does not exist!");
        }
    }
}