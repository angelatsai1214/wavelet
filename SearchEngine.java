import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> list = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return Arrays.toString(list.toArray());
        } else if (url.getPath().contains("/search")) {
            String desiredLetters = url.getQuery().split("=")[1];
            ArrayList<String> result = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                String currWord = list.get(i);
                if (currWord.contains(desiredLetters)) {
                    result.add(currWord);
                }
            }
            return Arrays.toString(result.toArray());
        } else {
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                list.add(parameters[1]);
                return "Word added!";
            }
            return "404 Not Found!";
        }
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
