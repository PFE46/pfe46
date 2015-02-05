package fr.unice.polytech.si5.pfe46.templating.components;

import org.junit.Test;

import fr.unice.polytech.si5.pfe46.templating.exceptions.DuplicateMethodSignatureException;
import junit.framework.TestCase;

/**
 * Since the output will be built, we need to ensure that what is
 * generated contains no error.
 * 
 * @author victorsalle
 */
public class OutputCompilationTest extends TestCase {

	private UpnpMethod getX_NoParam_v1;     // getX()
	private UpnpMethod getX_NoParam_v2;     // getX()
	private UpnpMethod getY;                // getY()
	private UpnpMethod getX_WithParams_AB1; // getX(a, b)
	private UpnpMethod getX_WithParams_AB2; // getX(a, b)
	private UpnpMethod getX_WithParams_BA;  // getX(b, a)
	private UpnpMethod getY_WithParams_AB;  // getY(a, b)
	private UpnpService service;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUp()
	{
		try
		{
			// Same name, no params
			getX_NoParam_v1 = new UpnpMethod("getX", "", null, null, null);
			getX_NoParam_v2 = new UpnpMethod("getX", "", null, null, null);
	
			// Same name, same params but with:
			//   - getX_AB1 & getX_AB2: same order
			//   - getX_AB1 & getX_BA: different order
			//   - getX_AB1 & getY_AB: different name
			getX_WithParams_AB1 = new UpnpMethod("getX", "", null, null, null);
			getX_WithParams_AB1.addInput(new UpnpStateVariable("a", UpnpStateVariableType.INT));
			getX_WithParams_AB1.addInput(new UpnpStateVariable("b", UpnpStateVariableType.STRING));
			
			getX_WithParams_AB2 = new UpnpMethod("getX", "", null, null, null);
			getX_WithParams_AB2.addInput(new UpnpStateVariable("a", UpnpStateVariableType.INT));
			getX_WithParams_AB2.addInput(new UpnpStateVariable("b", UpnpStateVariableType.STRING));
			
			getX_WithParams_BA = new UpnpMethod("getX", "", null, null, null);
			getX_WithParams_BA.addInput(new UpnpStateVariable("b", UpnpStateVariableType.STRING));
			getX_WithParams_BA.addInput(new UpnpStateVariable("a", UpnpStateVariableType.INT));
			
			getY_WithParams_AB = new UpnpMethod("getY", "", null, null, null);
			getY_WithParams_AB.addInput(new UpnpStateVariable("a", UpnpStateVariableType.INT));
			getY_WithParams_AB.addInput(new UpnpStateVariable("b", UpnpStateVariableType.STRING));
			
			// Different name
			getY = new UpnpMethod("getY", "", null, null, null);
			
			// Service
			service = new UpnpService();
		}
		catch (Exception e)
		{
			fail();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void tearDown()
	{
		getX_NoParam_v1 = null;
		getX_NoParam_v2 = null;
		getX_WithParams_AB1 = null;
		getX_WithParams_AB2 = null;
		getX_WithParams_BA = null;
		getY = null;
		
		service = null;
	}

	/**
	 * Each method of a class (a UpnpService in the present case) must have
	 * a unique signature. No duplicate allowed.
	 * Test without parameter.
	 */
	@Test
	public void testNoDuplicateMethodSignatureWithoutParam()
	{
		try
		{
			// Let's add a new method: no problem
			service.addMethod(getX_NoParam_v1);
			assertTrue(service.getMethods().contains(getX_NoParam_v1));
			
			// Same name, no param too: should throw DuplicateMethodSignatureException
			try
			{
				service.addMethod(getX_NoParam_v2);
				fail();
			}
			catch (DuplicateMethodSignatureException e)
			{
				// Should go there (nothing to do)
			}
			
			// Different name: should pass without any problem
			service.addMethod(getY);
			assertTrue(service.getMethods().contains(getY));
		}
		catch (Exception e)
		{
			fail();
		}
	}
	
	/**
	 * Each method of a class (a UpnpService in the present case) must have
	 * a unique signature. No duplicate allowed.
	 * Test with parameters.
	 */
	@Test
	public void testNoDuplicateMethodSignatureWithParams()
	{
		try
		{
			// Let's add a method with parameters
			service.addMethod(getX_WithParams_AB1);
			assertTrue(service.getMethods().contains(getX_WithParams_AB1));

			// Same name, same params, same order: should throw DuplicateMethodSignatureException
			try
			{
				service.addMethod(getX_WithParams_AB2);
				fail();
			}
			catch (DuplicateMethodSignatureException e)
			{
				// Should go there (nothing to do)
			}
			
			// Same name, same params, but different order: should pass
			service.addMethod(getX_WithParams_BA);
			assertTrue(service.getMethods().contains(getX_WithParams_BA));
			
			// Different name: should pass without any problem
			service.addMethod(getY_WithParams_AB);
			assertTrue(service.getMethods().contains(getY_WithParams_AB));
		}
		catch (Exception e)
		{
			fail();
		}
	}
	
}
