package hr.fer.zemris.bool;
import java.util.List;

/**
 * Interface BooleanSource represents any source capable of producing legal BooleanValue. 
 * It declares that it is capable of producing this value (method getValue()) and that 
 * it can provide an information based on which variables is this value produced (method getDomain()).
 * @author Ivan Pavić
 *
 */
public interface BooleanSource {
	/**
	 * @return BooleanValue that source produced.
	 */
	public BooleanValue getValue();	
	/**
	 * @return List of variables on which the value is produced.
	 */
	public List<BooleanVariable> getDomain();
}
