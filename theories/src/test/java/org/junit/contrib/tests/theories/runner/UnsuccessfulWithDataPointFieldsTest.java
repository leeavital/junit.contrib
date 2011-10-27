package org.junit.contrib.tests.theories.runner;

import org.junit.Test;
import org.junit.contrib.theories.DataPoint;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.runners.model.TestClass;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.experimental.results.PrintableResult.*;
import static org.junit.experimental.results.ResultMatchers.*;
import static org.junit.matchers.JUnitMatchers.*;

public class UnsuccessfulWithDataPointFieldsTest {
    @RunWith(Theories.class)
    public static class HasATheory {
        @DataPoint public static final int ONE = 1;

        @Theory
        public void everythingIsZero(int x) {
            assertThat(x, is(0));
        }
    }

    @Test
    public void theoryClassMethodsShowUp() throws Exception {
        assertThat(new Theories(HasATheory.class).getDescription().getChildren().size(), is(1));
    }

    @Test
    public void theoryAnnotationsAreRetained() throws Exception {
        assertThat(new TestClass(HasATheory.class).getAnnotatedMethods(Theory.class).size(), is(1));
    }

    @Test
    public void canRunTheories() throws Exception {
        assertThat(testResult(HasATheory.class), hasSingleFailureContaining("Expected"));
    }

    @RunWith(Theories.class)
    public static class DoesntUseParams {
        @DataPoint public static int ONE = 1;

        @Theory
        public void everythingIsZero(int x, int y) {
            assertThat(2, is(3));
        }
    }

    @Test
    public void reportBadParams() throws Exception {
        assertThat(testResult(DoesntUseParams.class), hasSingleFailureContaining("everythingIsZero(ONE, ONE)"));
    }

    @RunWith(Theories.class)
    public static class NullsOK {
        @DataPoint public static final String NULL = null;
        @DataPoint public static final String A = "A";

        @Theory
        public void everythingIsA(String a) {
            assertThat(a, is("A"));
        }
    }

    @Test
    public void nullsUsedUnlessProhibited() throws Exception {
        assertThat(testResult(NullsOK.class), hasSingleFailureContaining("null"));
    }

    @RunWith(Theories.class)
    public static class DataPointsMustBeStatic {
        @DataPoint final int THREE = 3;
        @DataPoint final int FOUR = 3;

        @Theory
        public void numbers(int x) {
        }
    }

    @Test
    public void dataPointsMustBeStatic() {
        assertThat(testResult(DataPointsMustBeStatic.class),
            both(failureCountIs(2))
                    .and(hasFailureContaining("DataPoint field THREE must be static"))
                    .and(hasFailureContaining("DataPoint field FOUR must be static")));
    }

    @RunWith(Theories.class)
    public static class TheoriesMustBePublic {
        @DataPoint
        public static final int THREE = 3;

        @Theory
        void numbers(int x) {
        }
    }

    @Test
    public void theoriesMustBePublic() {
        assertThat(testResult(TheoriesMustBePublic.class), hasSingleFailureContaining("public"));
    }
}
