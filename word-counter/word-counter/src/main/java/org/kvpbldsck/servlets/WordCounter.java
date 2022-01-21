package org.kvpbldsck.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public class WordCounter extends HttpServlet {
    private static final String FIRST_STRING_PARAM = "firstString";
    private static final String SECOND_STRING_PARAM = "secondString";
    private static final String THIRD_STRING_PARAM = "thirdString";

    private static final String FIRST_STRING_WORDS_PARAM = "firstStringWords";
    private static final String SECOND_STRING_WORDS_PARAM = "secondStringWords";
    private static final String THIRD_STRING_WORDS_PARAM = "thirdStringWords";

    private static final Pattern NON_WORD_PATTERN = Pattern.compile("\\W+");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstString = req.getParameter(FIRST_STRING_PARAM);
        String secondString = req.getParameter(SECOND_STRING_PARAM);
        String thirdString = req.getParameter(THIRD_STRING_PARAM);

        int firstStringWordsCount = NON_WORD_PATTERN.split(firstString).length;
        int secondStringWordsCount = NON_WORD_PATTERN.split(secondString).length;
        int thirdStringWordsCount = NON_WORD_PATTERN.split(thirdString).length;

        req.setAttribute(FIRST_STRING_WORDS_PARAM, firstStringWordsCount);
        req.setAttribute(SECOND_STRING_WORDS_PARAM, secondStringWordsCount);
        req.setAttribute(THIRD_STRING_WORDS_PARAM, thirdStringWordsCount);

        req.getRequestDispatcher("/words.jsp").forward(req, resp);
    }

}
