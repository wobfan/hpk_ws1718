package klausur2017_java;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public abstract class Aufgabe4_OperationTest<A, R, E extends Exception> {

    protected Operation<A, R, E> undertest;
    protected A[] validArgs;
    protected A[] invalidArgs;

    protected abstract boolean validateReturn(R r);
    protected abstract boolean validateError(Exception e);
    protected abstract Operation<A, R, E> createOperation();
    protected abstract A[] createArgs();
    protected abstract A[] createInvalidArgs();

    @Before
    public final void setUp() throws Exception {
            undertest = createOperation();
            invalidArgs = createInvalidArgs();
            validArgs = createArgs();
    }

    @Test
    public final void testValidArgs() throws Exception {
            R retValue = undertest.operate(validArgs);
            assertTrue("wrong result " + retValue, validateReturn(retValue));
    }

    @Test
    public void testInvalidArgs() {
            try {
                    @SuppressWarnings("unused")
                    R retValue = undertest.operate(invalidArgs);
                    fail("invalid argument not detected");
            } catch (Exception e) {
                    assertTrue("wrong error:" + e, validateError(e));
            }
    }

    @Test(expected = NullPointerException.class)
    public final void testNullArgs() throws Exception {
            A[] nullArgs = null;
            undertest.operate(nullArgs);
    }
}