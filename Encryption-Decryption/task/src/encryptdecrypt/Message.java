package encryptdecrypt;

import java.io.*;
import java.util.Arrays;

public class Message {
    private String mode = "enc";
    private int key = 0;
    private String data = "";
    private String in = "";
    private String out = "";
    private Algorithms alg = Algorithms.valueOf("SHIFT");

    public Algorithms getAlg() {
        return alg;
    }

    enum Algorithms {
        SHIFT,
        UNICODE
    }

    public String getMode() {
        return mode;
    }

    public int getKey() {
        return key;
    }

    public Message(String[] args) {

        for (int i = 0; i < args.length; i += 2) { //TODO i+=2
            String value = args[i + 1];
            fillValues(args[i], value);
        }
    }

    private void fillValues(String currentArg, String value) {
        switch (currentArg) {
            case "-mode":
                mode = value;
                break;
            case "-key":
                key = Integer.parseInt(value);
                break;
            case "-data":
                data = value;
                break;
            case "-in":
                in = importFile(value);
                break;
            case "-out":
                out = value;
                break;
            case "-alg":
                alg = Algorithms.valueOf(value.toUpperCase());
            default:
                break;
        }
    }

    protected String importFile(String nameFile) {
        final File file = new File(nameFile);
        final StringBuilder stringBuilder = new StringBuilder();

        if (file.exists()) {
            try (var isr = new InputStreamReader(new FileInputStream(file));
                 var br = new BufferedReader(isr)) {

                String line;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
        } else {
            System.out.println("Error : File not found.");
        }
        return stringBuilder.toString();
    }

    protected String getMessage() {
        return data.isEmpty() ? in.isEmpty() ? data : in : data;
    }

    protected void setOutput(String finalMessage) {
        if (!out.isEmpty()) {
            exportFile(out, finalMessage);
        } else {
            System.out.println(finalMessage);
        }
    }

    protected void exportFile(String nameFile, String content) {
        try (var file = new FileWriter(nameFile)) {
            file.write(content);
        } catch (Exception e) {
            System.out.println("Error : " + Arrays.toString(e.getStackTrace()));
        }
    }
}