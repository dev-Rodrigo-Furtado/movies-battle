package com.ada.moviesbatlle.domain.models;

import com.ada.moviesbatlle.domain.enums.Answer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QuestionTest {

    @Test
    public void getAnswer_shouldReturnSECOND() {
        Question question = new Question(new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000),
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000));

        Assert.assertEquals(Answer.SECOND, question.getAnswer());
    }
    @Test
    public void getAnswer_shouldReturnFIRST() {
        Question question = new Question(
                new Movie("tt1745960", "Top Gun: Maverick", 9.0, 1_000_000),
                new Movie("tt0167261", "The Lord of the Rings: The Two Towers", 8.0, 1_000_000));

        Assert.assertEquals(Answer.FIRST, question.getAnswer());
    }

}