package hr.fer.zemris.bool;
/**
 * This Enumeration represents legal values for a Boolean function. These are:
 * <ul>
 * <li>logical value zero 
 * <li> logical value one 
 * <li> logical value don't care 
 * </ul>
 * We will treat don't cares as situation in which actual value
 * of function is either zero or one, but we do not care about 
 * its actual value.
 * @author Ivan Pavić
 *
 */
public enum BooleanValue {
	TRUE, FALSE, DONT_CARE;
}
