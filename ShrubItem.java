package warehouse;


/**
 * Classes that describes a Shrub
 * 
 * @author Student ID: 610051863 
 * @version 1.0
 */
import java.io.Serializable;
public class ShrubItem implements Serializable{
  // declare fields of appropriate type
	private String name;
	private Foliage foliage;
	private String ID;
	private String information;
	private int priceInPence;

   /*
	* Set default no-argument constructor to private; no need for a default broadcast.
	*/
	private ShrubItem(){}

	/**
	 * Initialize object
	 */   
	public ShrubItem(String name, Foliage foliage, String ID) {
		this.name = name;
		this.foliage = foliage;
		this.ID = ID;
		this.information = "";
		priceInPence = 0;
	}


	/**
	 * Method to set number of shrub price
	 *
 	 * @param priceInPence      shrub price in pence
	 */
	public void setShrubPrice(int priceInPence){
		this.priceInPence = priceInPence;
	}


	/**
	 * Method to add text-information to a shrub
	 *
	 * @param num      shrubs textual-information string
	 */
	public void addShrubInfo(String information){
		this.information = information;
	}



	/**
	 * Method to return shrub price
	 *
	 * @return					shrub price
	 */
	public int getShrubPrice(){
		return priceInPence;
	}


	// DELETE!!!
	/**
	 * Method to return unique ID of the shrub
	 *
	 * @return					unique ID of shrub
	 */
	public String getShrubID(){
		return ID;
	}

	/**
	 * Method to return shrub name
	 *
	 * @return					shrub name
	 */
	public String getShrubName(){
		return name;
	}

	/**
	 * Method to return shrub foliage
	 *
	 * @return					foliage type
	 */
	public Foliage getShrubFoliage(){
		return foliage;
	}


	/**
	 * Method to return shrub textual-information
	 *
	 * @return					shrubs textual-information string
	 */
	public String getShrubInfo(){
		return information;
	}


}
