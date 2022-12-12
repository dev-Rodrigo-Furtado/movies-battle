package com.ada.moviesbatlle.domain.models;

import com.ada.moviesbatlle.fixtures.Fixtures;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MovieTest {

    @Test
    public void getScore_shouldCalculatedAndReturnScore() {
        Movie movie = Fixtures.buildMovie("tt0167261", "The Lord of the Rings: The Two Towers");
        Assert.assertEquals(9000000.0, movie.getScore(), 0);
    }

}