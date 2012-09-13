package org.hikst.Simulator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SimulationRequest 
{
	public static final String Request_Pending = "Pending";
	public static final String Request_Finished = "Finished";
	public static final String Request_Processing = "Processing";
	
	private int ID;
	private int simulationDescriptionsID;
	
	public int getID() {
		return ID;
	}
	
	public int getSimulationDescriptionsID() {
		return simulationDescriptionsID;
	}
	
	/**
	 * @param id
	 * @throws ObjectNotFoundException
	 * 
	 * Creates a new simulation request from data given in the database at specified row id.
	 * Throws exception if the row is null.
	 */
	public SimulationRequest(int id) throws ObjectNotFoundException
	{
		Connection connection = Settings.getDBC();
		
		try
		{
			String query = "SELECT ID,Simulation_Descriptions_ID FROM " +
					"Simulator_Queue_Objects WHERE ID=?; ";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet set = statement.executeQuery();
			
			if(set.next())
			{
				this.ID = set.getInt(1);
				this.simulationDescriptionsID = set.getInt(2);
			}
			else
			{
				throw new ObjectNotFoundException();
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void setStatusToProcessing()
	{
		try
		{
			Connection connection = Settings.getDBC();
			
			int statusID = Status.getInstance().getStatusID(Request_Processing);
			
			String query = "UPDATE Simulator_Queue_Objects SET Status_ID=? WHERE ID=?";
			
			PreparedStatement statement =  connection.prepareStatement(query);
			statement.setInt(1, statusID);
			statement.setInt(2, ID);
			statement.executeUpdate();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		catch(StatusIdNotFoundException ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	 * TODO:
	 */
	public void setStatusToFinished()
	{
		try
		{
			Connection connection = Settings.getDBC();
			
			int statusID = Status.getInstance().getStatusID(Request_Finished);
			
			String query = "UPDATE Simulator_Queue_Objects SET Status_ID=? WHERE ID=?";
			
			PreparedStatement statement =  connection.prepareStatement(query);
			statement.setInt(1, statusID);
			statement.setInt(2, ID);
			statement.executeUpdate();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		catch(StatusIdNotFoundException ex)
		{
			ex.printStackTrace();
		}
	}
	
}
