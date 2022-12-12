package com.ada.moviesbatlle.domain.models;

import com.ada.moviesbatlle.domain.enums.Answer;
import com.ada.moviesbatlle.domain.enums.QuizStatus;
import com.ada.moviesbatlle.domain.enums.RoundStatus;
import com.ada.moviesbatlle.domain.exceptions.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static com.ada.moviesbatlle.fixtures.Fixtures.buildRound;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class QuizTest {

    @Test
    public void answerCurrentRound_WithCorrectAnswer() throws RoundAlreadyAnswerdException, QuizStoppedException, NoMoreRoundsException, MaxWrongAnswersException, NotCurrentRoundException {
        Question firstQuestion = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round firstRound = buildRound(firstQuestion);

        Question secondQuestion = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round secondRound = buildRound(secondQuestion);

        Quiz quiz = new Quiz(Arrays.asList(firstRound, secondRound));

        quiz.answerCurrentRound(Answer.FIRST);

        assertEquals(1, quiz.getScore());
        assertEquals(1, quiz.getTotalCorrectAnswers());
        assertEquals(0, quiz.getTotalWrongAnswers());
        assertEquals(QuizStatus.STARTED, quiz.getStatus());
        assertEquals(firstRound, quiz.getCurrentRound());
    }

    @Test
    public void answerCurrentRound_WithWrongAnswer() throws RoundAlreadyAnswerdException, NotCurrentRoundException {
        Question firstQuestion = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round firstRound = buildRound(firstQuestion);

        Question secondQuestion = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round secondRound = buildRound(secondQuestion);

        Quiz quiz = new Quiz(Arrays.asList(firstRound, secondRound));

        quiz.answerCurrentRound(Answer.SECOND);

        assertEquals(0, quiz.getScore());
        assertEquals(0, quiz.getTotalCorrectAnswers());
        assertEquals(1, quiz.getTotalWrongAnswers());
        assertEquals(QuizStatus.STARTED, quiz.getStatus());
        assertEquals(firstRound, quiz.getCurrentRound());
    }

    @Test
    public void answerCurrentRound_WithMaxWrongAnswers() throws RoundAlreadyAnswerdException, NotCurrentRoundException {
        Question firstQuestion = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round firstRound = buildRound(firstQuestion);

        Question secondQuestion = new Question(
                new Movie("tt10872600", "Spider-Man: No Way Home", 9.0, 1_000_000),
                new Movie("tt8579674", "1917", 8.0, 1_000_000));

        Round secondRound = buildRound(secondQuestion);

        Question thirdQuestion = new Question(
                new Movie("tt7286456", "Joker", 9.0, 1_000_000),
                new Movie("tt4154796", "Avengers: Endgame", 8.0, 1_000_000));

        Round thirdRound = buildRound(thirdQuestion);

        Quiz quiz = new Quiz(Arrays.asList(firstRound, secondRound, thirdRound));

        quiz.answerCurrentRound(Answer.SECOND);
        quiz.nextCurrentRound();

        quiz.answerCurrentRound(Answer.SECOND);
        quiz.nextCurrentRound();

        quiz.answerCurrentRound(Answer.SECOND);

        assertEquals(0, quiz.getScore());
        assertEquals(0, quiz.getTotalCorrectAnswers());
        assertEquals(3, quiz.getTotalWrongAnswers());
        assertEquals(QuizStatus.DEFEAT, quiz.getStatus());
        assertEquals(thirdRound, quiz.getCurrentRound());
    }

    @Test
    public void nextCurrentRound_CurrentPendingRound()  {
        Question firstQuestion = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round firstRound = buildRound(firstQuestion);

        Question secondQuestion = new Question(
                new Movie("tt10872600", "Spider-Man: No Way Home", 9.0, 1_000_000),
                new Movie("tt8579674", "1917", 8.0, 1_000_000));

        Round secondRound = buildRound(secondQuestion);

        Question thirdQuestion = new Question(
                new Movie("tt7286456", "Joker", 9.0, 1_000_000),
                new Movie("tt4154796", "Avengers: Endgame", 8.0, 1_000_000));

        Round thirdRound = buildRound(thirdQuestion);

        Quiz quiz = new Quiz(Arrays.asList(firstRound, secondRound, thirdRound));

        quiz.nextCurrentRound();

        assertEquals(firstRound, quiz.getCurrentRound());
        assertEquals(RoundStatus.CURRENT, firstRound.getStatus());
        assertEquals(QuizStatus.STARTED, quiz.getStatus());
    }

    @Test
    public void nextCurrentRound_WithCurrentRoundAnswerd() throws RoundAlreadyAnswerdException, NotCurrentRoundException {
        Question firstQuestion = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round firstRound = buildRound(firstQuestion);

        Question secondQuestion = new Question(
                new Movie("tt10872600", "Spider-Man: No Way Home", 9.0, 1_000_000),
                new Movie("tt8579674", "1917", 8.0, 1_000_000));

        Round secondRound = buildRound(secondQuestion);

        Question thirdQuestion = new Question(
                new Movie("tt7286456", "Joker", 9.0, 1_000_000),
                new Movie("tt4154796", "Avengers: Endgame", 8.0, 1_000_000));

        Round thirdRound = buildRound(thirdQuestion);

        Quiz quiz = new Quiz(Arrays.asList(firstRound, secondRound, thirdRound));
        quiz.answerCurrentRound(Answer.FIRST);

        quiz.nextCurrentRound();

        assertEquals(RoundStatus.DONE, firstRound.getStatus());
        assertEquals(secondRound, quiz.getCurrentRound());
        assertEquals(RoundStatus.CURRENT, secondRound.getStatus());
        assertEquals(QuizStatus.STARTED, quiz.getStatus());
    }

    @Test
    public void nextCurrentRound_WithNoMorePendingsRounds() throws RoundAlreadyAnswerdException, NotCurrentRoundException {
        Question firstQuestion = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round firstRound = buildRound(firstQuestion);

        Question secondQuestion = new Question(
                new Movie("tt10872600", "Spider-Man: No Way Home", 9.0, 1_000_000),
                new Movie("tt8579674", "1917", 8.0, 1_000_000));

        Round secondRound = buildRound(secondQuestion);

        Question thirdQuestion = new Question(
                new Movie("tt7286456", "Joker", 9.0, 1_000_000),
                new Movie("tt4154796", "Avengers: Endgame", 8.0, 1_000_000));

        Round thirdRound = buildRound(thirdQuestion);

        Quiz quiz = new Quiz(Arrays.asList(firstRound, secondRound, thirdRound));

        quiz.answerCurrentRound(Answer.FIRST);
        quiz.nextCurrentRound();

        quiz.answerCurrentRound(Answer.FIRST);
        quiz.nextCurrentRound();

        quiz.answerCurrentRound(Answer.FIRST);
        quiz.nextCurrentRound();

        assertEquals(RoundStatus.DONE, firstRound.getStatus());
        assertEquals(RoundStatus.DONE, secondRound.getStatus());
        assertEquals(RoundStatus.CURRENT, thirdRound.getStatus());
        assertEquals(thirdRound, quiz.getCurrentRound());
        assertEquals(QuizStatus.COMPLETED, quiz.getStatus());
    }

    @Test
    public void stop_WithStartedQuiz() throws QuizNotStartedException {
        Question firstQuestion = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round firstRound = buildRound(firstQuestion);

        Question secondQuestion = new Question(
                new Movie("tt10872600", "Spider-Man: No Way Home", 9.0, 1_000_000),
                new Movie("tt8579674", "1917", 8.0, 1_000_000));

        Round secondRound = buildRound(secondQuestion);

        Question thirdQuestion = new Question(
                new Movie("tt7286456", "Joker", 9.0, 1_000_000),
                new Movie("tt4154796", "Avengers: Endgame", 8.0, 1_000_000));

        Round thirdRound = buildRound(thirdQuestion);

        Quiz quiz = new Quiz(Arrays.asList(firstRound, secondRound, thirdRound));
        quiz.stop();

        assertEquals(QuizStatus.STOPPED, quiz.getStatus());
    }

    @Test
    public void stop_WithStoppedQuiz() {
        Question firstQuestion = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round firstRound = buildRound(firstQuestion);

        Question secondQuestion = new Question(
                new Movie("tt10872600", "Spider-Man: No Way Home", 9.0, 1_000_000),
                new Movie("tt8579674", "1917", 8.0, 1_000_000));

        Round secondRound = buildRound(secondQuestion);

        Question thirdQuestion = new Question(
                new Movie("tt7286456", "Joker", 9.0, 1_000_000),
                new Movie("tt4154796", "Avengers: Endgame", 8.0, 1_000_000));

        Round thirdRound = buildRound(thirdQuestion);

        Quiz quiz = new Quiz(Arrays.asList(firstRound, secondRound, thirdRound));

        assertThrows(QuizNotStartedException.class,
                () -> {
                    quiz.stop();
                    quiz.stop();
                });
    }


}