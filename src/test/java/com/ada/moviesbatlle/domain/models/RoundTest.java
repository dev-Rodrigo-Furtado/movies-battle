package com.ada.moviesbatlle.domain.models;

import com.ada.moviesbatlle.domain.enums.Answer;
import com.ada.moviesbatlle.domain.enums.Result;
import com.ada.moviesbatlle.domain.enums.RoundStatus;
import com.ada.moviesbatlle.domain.exceptions.NotCurrentRoundException;
import com.ada.moviesbatlle.domain.exceptions.RoundAlreadyAnswerdException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.ada.moviesbatlle.fixtures.Fixtures.buildRound;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class RoundTest {

    @Test
    public void answer_shouldCorrectAnswer() throws RoundAlreadyAnswerdException, NotCurrentRoundException {
        Question question = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round round = buildRound(question);
        round.makeCurrent();

        Assert.assertEquals(Result.CORRECT_ANSWER, round.answer(Answer.FIRST));
        Assert.assertEquals(RoundStatus.CURRENT, round.getStatus());
    }

    @Test
    public void answer_shouldWrongAnswer() throws RoundAlreadyAnswerdException, NotCurrentRoundException {
        Question question = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round round = buildRound(question);
        round.makeCurrent();

        Assert.assertEquals(Result.WRONG_ANSWER, round.answer(Answer.SECOND));
        Assert.assertEquals(RoundStatus.CURRENT, round.getStatus());
    }

    @Test
    public void answer_shouldThrowRoundAlreadyAnswerdException() {
        Question question = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round round = buildRound(question);
        round.makeCurrent();

        assertThrows(RoundAlreadyAnswerdException.class,
                () -> {
                    round.answer(Answer.SECOND);
                    round.answer(Answer.SECOND);
                });
    }

    @Test
    public void answer_shouldThrowNotCurrentRoundException() {
        Question question = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Round round = buildRound(question);

        assertThrows(NotCurrentRoundException.class,
                () -> {
                    round.answer(Answer.SECOND);
                });
    }
}