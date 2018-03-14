package klausur2017_java;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.concurrent.ThreadLocalRandom;

interface Operation<A, R, E extends Exception> {
	R operate(A... args) throws E;
}

class SquareRoot implements Operation<Double, Double, Exception> {

	@Override
	public Double operate(Double... args) throws Exception {
		if(args[0] < 0 || args.length > 1) throw new IllegalArgumentException("too much arguments/negative number");
		return Math.sqrt(args[0]);
	}
	
}

public class Aufgabe4_TestImpl extends Aufgabe4_OperationTest<Double, Double, Exception> {

	@Override
	protected boolean validateReturn(Double r) {
		if (Math.sqrt(validArgs[0]) == r) return true;
		return false;
	}

	@Override
	protected boolean validateError(Exception e) {
		if(e instanceof IllegalArgumentException) {
			if (e.getMessage().contains("too much arguments/negative number")) return true;
		}
		return false;
	}

	@Override
	protected Operation<Double, Double, Exception> createOperation() {
		Operation<Double, Double, Exception> op = new SquareRoot();
		return op;
	}

	@Override
	protected Double[] createArgs() {
		Double random[] = new Double[1];
		random[0] = ThreadLocalRandom.current().nextDouble();
		if (random[0] < 0) random[0]*=(-1);
		return random;
	}

	@Override
	protected Double[] createInvalidArgs() {
		Double random[] = new Double[ThreadLocalRandom.current().nextInt(1, 4)];
		for (int i = 0; i < random.length; i++) {
			random[i] = ThreadLocalRandom.current().nextDouble();
		}
		return random;
	}
	
	
	
}

