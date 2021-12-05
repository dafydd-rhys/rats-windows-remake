package main.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Message Of The Day.
 *
 * @author Maurice Petersen
 */
public class MOTD {

    /**
     * Minimum value for successful response codes.
     */
    private static final int MIN_SUCCESSFUL_RESCODE = 200;

    /**
     * Maximum value for successful response codes.
     */
    private static final int MAX_SUCCESSFUL_RESCODE = 299;

    /**
     * Number of characters in the alphabet used for the caesar cipher.
     */
    private static final int ALPHABET_COUNT = 26;

    /**
     * The URL to the puzzle
     */
    private final URL puzzleURL;
    /**
     * The URL to the message
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
     * @throws IOException link cannot be found.
     */
    private String getRequest(final URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader br = null;
        if (MIN_SUCCESSFUL_RESCODE <= conn.getResponseCode()
                && conn.getResponseCode() <= MAX_SUCCESSFUL_RESCODE) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }

        String inputLine;
        StringBuilder response = new StringBuilder();
        while (true) {
            assert br != null;
            if ((inputLine = br.readLine()) == null) {
                conn.disconnect();
                return response.toString();
            }
            response.append(inputLine);
        }
    }

    /**
     * Resolves the API puzzle.
     *
     * @return puzzle solution.
     * @throws IOException link cannot be found.
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
                c = (char) (c - ALPHABET_COUNT);
            } else if (c < 'a') {
                c = (char) (c + ALPHABET_COUNT);
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
