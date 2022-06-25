package encryptdecrypt;

abstract class CrackingMessage {

    abstract void crackMessage(Message message);
}

public class EncryptMessage extends CrackingMessage {
    final StringBuilder newMessage = new StringBuilder();
    private String realMessage;
    private int key;

    @Override
    void crackMessage(Message message) {
        switch (message.getAlg()) {
            case SHIFT -> shifting(message);
            case UNICODE -> unicode(message);
        }
    }

    void unicode(Message message) {
        realMessage = message.getMessage();
        key = message.getKey();

        for (char character : realMessage.toCharArray()) {
            newMessage.append((char) (character + key));
        }
        message.setOutput(newMessage.toString());
    }

    void shifting(Message message) {
        realMessage = message.getMessage();
        key = message.getKey();

        for (char ichar : realMessage.toCharArray()) {
            if ('a' <= ichar && ichar <= 'z') {
                ichar = (char) (ichar + key > 'z' ? ichar + key - 1 - 'z' + 'a' : ichar + key);
            }
            if ('A' <= ichar && ichar <= 'Z') {
                ichar = (char) (ichar + key > 'Z' ? ichar + key - 1 - 'Z' + 'A' : ichar + key);
            }
            newMessage.append(ichar);
        }
        message.setOutput(newMessage.toString());
    }
}

class DecodeMessage extends CrackingMessage {

    private String realMessage;
    private int key;
    private final StringBuilder newMessage = new StringBuilder();

    @Override
    void crackMessage(Message message) {
        switch (message.getAlg()) {
            case SHIFT -> shiftDecode(message);
            case UNICODE -> unicodeDecode(message);
        }
    }

    protected void shiftDecode(Message message) {
        realMessage = message.getMessage();
        key = message.getKey();

        for (char ichar : realMessage.toCharArray()) {
            if ('a' <= ichar && ichar <= 'z') {
                ichar = (char) (ichar - key < 'a' ? ichar - key + 1 + 'z' - 'a' : ichar - key);
            }
            if ('A' <= ichar && ichar <= 'Z') {
                ichar = (char) (ichar + key < 'Z' ? ichar - key + 1 + 'Z' - 'A' : ichar - key);
            }
            newMessage.append(ichar);
        }
        message.setOutput(newMessage.toString());
    }

    protected void unicodeDecode(Message message) {
        realMessage = message.getMessage();
        key = message.getKey();

        for (char character : realMessage.toCharArray()) {
            newMessage.append((char) (character - key));
        }

        message.setOutput(newMessage.toString());
    }


}

class MessageContext {
    private CrackingMessage cracking;

    public void setMethod(CrackingMessage cracking) {
        this.cracking = cracking;
    }

    public void crackMessage(Message message) {
        this.cracking.crackMessage(message);
    }
}