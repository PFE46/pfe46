package fr.unice.polytech.si5.pfe46.templating.components;

import java.util.HashSet;
import java.util.Set;

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

	private UpnpMethod getX_NoParam_v1;
	private UpnpMethod getX_NoParam_v2;
	private UpnpMethod getX_WithParam;
	private UpnpMethod getY;
	private UpnpService service;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUp()
	{
		getX_NoParam_v1 = new UpnpMethod("getX", "", null, null, null);
		getX_NoParam_v2 = new UpnpMethod("getX", "", null, null, null);
		
		Set<UpnpStateVariable> inputsGetX = new HashSet<UpnpStateVariable>();
		inputsGetX.add(new UpnpStateVariable("input", UpnpStateVariableType.INT));
		getX_WithParam = new UpnpMethod("getX", "", inputsGetX, null, null);
		
		getY = new UpnpMethod("getY", "", null, null, null);
		
		service = new UpnpService();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void tearDown()
	{
		getX_NoParam_v1 = null;
		getX_NoParam_v2 = null;
		getX_WithParam = null;
		getY = null;
		
		service = null;
	}
	
	/**
	 * Each method of a class (a UpnpService in the present case) must have
	 * a unique signature. No duplicate allowed.
	 */
	@Test
	public void testNoDuplicateMethodSignature()
	{
		try
		{
			// Let's add a new method: no problem
			service.addMethod(getX_NoParam_v1);
			assertTrue(service.getMethods().contains(getX_NoParam_v1));
			
			// Same name, same parameters: should throw DuplicateMethodSignatureException
			try
			{
				service.addMethod(getX_NoParam_v2);
				fail();
			}
			catch (DuplicateMethodSignatureException e)
			{
				// Should go there (nothing to do)
			}
			
			// No problem with those ones (different parameters and different name)
			service.addMethod(getX_WithParam);
			service.addMethod(getY);
			assertTrue(service.getMethods().contains(getX_WithParam));
			assertTrue(service.getMethods().contains(getY));
		}
		catch (Exception e)
		{
			fail();
		}
	}
	
}
