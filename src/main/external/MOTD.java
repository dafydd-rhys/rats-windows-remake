package main.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * MOTD.java
 * Message Of The Day
 *
 * @author Maurice Peterson
 */
public class MOTD {

    /**
     *
     */
    private final URL puzzleURL;
    /**
     *
     */
    private final URL messageURL;

    /**
     * Instantiates a new Motd.
     *
     * @throws MalformedURLException the malformed url exception
     */
    public MOTD() throws MalformedURLException {
        puzzleURL = new URL("http://cswebcat.swansea.ac.uk/puzzle");
        messageURL = new URL("http://cswebcat.swansea.ac.uk/message");
    }

    /**
     * Send a GET request to given URL.
     *
     * @param url url to send request to.
     * @return response
     * @throws IOException
     */
    private String getRequest(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader br = null;
        if (100 <= conn.getResponseCode() && conn.getResponseCode() <= 399) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }

        String inputLine;
        StringBuilder response = new StringBuilder();
        while (true) {
            assert br != null;
            if ((inputLine = br.readLine()) == null) break;
            response.append(inputLine);
        }

        conn.disconnect();
        return response.toString();
    }

    /**
     * Resolves the API puzzle.
     *
     * @return puzzle solution.
     * @throws IOException
     */
    private String getPuzzle() throws IOException {
        String puzzleString = getRequest(puzzleURL).toLowerCase();
        char[] puzzleArray = puzzleString.toCharArray();

        for (int i = 0; i < puzzleArray.length; i++) {
            int shift = i + 1;
            if (shift % 2 != 0) {
                shift = -shift;
            }

            char c = puzzleArray[i];
            c = (char) (c + shift);
            if (c > 'z') {
                c = (char) (c - 26);
            } else if (c < 'a') {
                c = (char) (c + 26);
            }

            puzzleArray[i] = c;
        }

        String solution = new String(puzzleArray).toUpperCase();
        solution = solution + "CS-230";
        solution = solution.length() + solution;
        return solution;
    }

    /**
     * Sends a request to the API using the puzzle solution as parameter.
     *
     * @return message of the day.
     * @throws IOException the io exception
     */
    public String getMessage() throws IOException {
        String secret = getPuzzle();
        URL newURL = new URL(messageURL + "?solution=" + secret);
        String response = getRequest(newURL);
        return response.split("\\(")[0];
    }

}
