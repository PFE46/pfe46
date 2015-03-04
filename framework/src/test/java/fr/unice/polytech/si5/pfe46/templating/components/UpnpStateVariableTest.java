package fr.unice.polytech.si5.pfe46.templating.components;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * {@link UpnpStateVariable} test case.
 * 
 * @author victorsalle
 */
public class UpnpStateVariableTest extends TestCase {

	private UpnpStateVariable variableFirstFalseBoolean;
	private UpnpStateVariable variableFirstTrueBoolean;
	private UpnpStateVariable variableFirstFalseString;
	private UpnpStateVariable variableSecondFalseBoolean;
	private List<UpnpStateVariable> collection1;
	private List<UpnpStateVariable> collection2;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUp()
	{
		variableFirstFalseBoolean = new UpnpStateVariable("first", Boolean.FALSE, UpnpStateVariableType.BOOLEAN);
		variableFirstTrueBoolean = new UpnpStateVariable("first", Boolean.TRUE, UpnpStateVariableType.BOOLEAN);
		variableFirstFalseString = new UpnpStateVariable("first", Boolean.FALSE, UpnpStateVariableType.STRING);
		variableSecondFalseBoolean = new UpnpStateVariable("second", Boolean.FALSE, UpnpStateVariableType.BOOLEAN);

		collection1 = new ArrayList<UpnpStateVariable>();
		collection2 = new ArrayList<UpnpStateVariable>();

		collection1.add(variableFirstFalseBoolean);
		collection1.add(variableSecondFalseBoolean);

		collection2.add(variableFirstFalseString);
		collection2.add(variableFirstTrueBoolean);
		collection2.add(variableSecondFalseBoolean);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void tearDown()
	{
		variableFirstFalseBoolean = null;
		variableFirstTrueBoolean = null;
		variableFirstFalseString = null;
		variableSecondFalseBoolean = null;

		collection1 = null;
		collection2 = null;
	}

	/**
	 * Test UpnpStateVariable::checkNoConflict(UpnpStateVariable, UpnpStateVariable)
	 */
	@Test
	public void testCheckNoConflictVariableVariable()
	{
		// Different name: no conflict
		assertTrue(UpnpStateVariable.checkNoConflict(variableFirstFalseBoolean, variableSecondFalseBoolean));
		assertTrue(UpnpStateVariable.checkNoConflict(variableSecondFalseBoolean, variableFirstFalseBoolean));

		// Same name but same attributes
		assertTrue(UpnpStateVariable.checkNoConflict(variableFirstFalseBoolean, variableFirstFalseBoolean));

		// Same name with different type: conflict
		assertFalse(UpnpStateVariable.checkNoConflict(variableFirstFalseBoolean, variableFirstFalseString));
		assertFalse(UpnpStateVariable.checkNoConflict(variableFirstFalseString, variableFirstFalseBoolean));

		// Same name with different sendEvents: conflict
		assertFalse(UpnpStateVariable.checkNoConflict(variableFirstFalseBoolean, variableFirstTrueBoolean));
		assertFalse(UpnpStateVariable.checkNoConflict(variableFirstTrueBoolean, variableFirstFalseBoolean));
	}

	/**
	 * Test UpnpStateVariable::checkNoConflict(UpnpStateVariable, Collection<UpnpStateVariable>)
	 */
	@Test
	public void testCheckNoConflictVariableCollection()
	{
		// Collection1 contains:
		//   - a variable with the same name as variableFirstFalseBoolean but with the same attributes
		//   - a variable with an other name
		// --> no conflict
		assertNull(UpnpStateVariable.checkNoConflict(variableFirstFalseBoolean, collection1));

		// Collection2 contains:
		//   - a variable with the same name as variableFirstFalseBoolean but with different attributes
		// --> conflict
		assertNotNull(UpnpStateVariable.checkNoConflict(variableFirstFalseBoolean, collection2));
	}

	/**
	 * Test UpnpStateVariable::checkNoConflict(Collection<UpnpStateVariable>, Collection<UpnpStateVariable>)
	 */
	@Test
	public void testCheckNoConflictCollectionCollection()
	{
		// Collection1 contains: {"first",false,boolean}
		// Collection2 contains: {"first",false,string} and {"first",true,boolean}
		// --> conflict (same name with different attributes)
		assertNotNull(UpnpStateVariable.checkNoConflict(collection1, collection2));
		
		// Let's remove {"first",false,string} and {"first",true,boolean} (the two conflicts)
		// on collection2 and try again
		collection2.remove(variableFirstFalseString);
		collection2.remove(variableFirstTrueBoolean);
		assertNull(UpnpStateVariable.checkNoConflict(collection1, collection2));
	}

}
