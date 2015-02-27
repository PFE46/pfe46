package fr.unice.polytech.si5.pfe46.modules;

import wiiremotej.*;
import wiiremotej.event.*;

import com.intel.bluetooth.BlueCoveConfigProperties;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements BalanceBoardListener and acts as a general test class. Note that you can ignore the main method pretty much,
 * as it mostly has to do with the graphs and GUIs.
 * At the very end though, there's an example of how to connect to a balance board.
 * @author Michael Diamond
 * @version 2/26/09
 */

public class BBImpl extends BalanceBoardAdapter
{   
	private BalanceBoard board = null;

	// Weight
	private static Double weight;
	// List of measured weight data
	private static List<Double> weights_list = null;

	
	
	public BBImpl(){}
	
	public BBImpl(BalanceBoard board)
	{
		this.board = board;
	}
	
	

	public void getWeight()
	{
		// To handle java.lang.IllegalArgumentException: PCM 11, PCM values restricted by JSR-82 to minimum 4097, see BlueCoveConfigProperties.PROPERTY_JSR_82_PSM_MINIMUM_OFF
		System.setProperty(BlueCoveConfigProperties.PROPERTY_JSR_82_PSM_MINIMUM_OFF, "true");

		// Init weight attributes
		weight = 0.0;
		weights_list = new ArrayList<Double>();

		try
		{            
			findAndConnectBalanceBoard();

			System.out.println("\tRuntime go...");
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
				public void run(){
					board.disconnect();
				}
			}
					));
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void findAndConnectBalanceBoard() throws InterruptedException, IOException
	{

		while (board == null) {
			try {
				board = WiiRemoteJ.findBalanceBoard();
				if ( board != null ) {
					System.out.println("Connected to board!");
					break;
				}
			}
			catch (IllegalStateException ise) {
				ise.printStackTrace();
				System.out.println("/!\\ Failed to connect board. Trying again.");
				System.exit(1);
			}
		}

		board.addBalanceBoardListener(new BBImpl(board));
		board.setLEDIlluminated(true);

	}

	public void disconnected()
	{
		System.exit(0);
	}

	@Override
	public void massInputReceived(BBMassEvent evt)
	{
		Double weight_trans = evt.getTotalMass();
		if ( weight_trans != null )
			weight = evt.getTotalMass();
		if ( weight > 30 )
			weights_list.add(weight);
	}

	@Override
	public void buttonInputReceived(BBButtonEvent evt) {
		if (evt.wasPressed()) {
			System.out.println("Wii BB deconnexion.");
			board.disconnect();
			Double weightSum = 0.0;
			for ( Double d : weights_list ) {
				weightSum += d;
			}
			Double finalWeight = weightSum / weights_list.size();
			System.out.println("\tINFO: measured weight is: " + finalWeight);
			System.exit(0);
		}
	}


}