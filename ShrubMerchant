package warehouse;
import java.io.IOException;

/**
 * ShrubMerchant interface. The no-argument constructor of a class 
 * implementing this interface should initialise the ShrubMerchant 
 * containing no shrubs
 * 
 * @author Jonathan Fieldsend 
 * @version 1.1
 */

public interface ShrubMerchant
{

    /**
     * Method adds num shrubs, with the arguments as shrub attributes.
     *
     * @param num               number of shrubs added
     * @param name            shrub name
     * @param foliage             foliage type
     * @param ID                unique ID of shrub 
     * @throws NegativeNumberOfShrubsAddedException
     * @throws ShrubMismatchException
     * @throws IllegalIDException
     */
    public void addShrubs(int num, String name, Foliage foliage, String ID) 
    throws NegativeNumberOfShrubsAddedException, ShrubMismatchException, IllegalIDException;

    /**
     * Method adds num shrubs, with the arguments as shrub attributes.
     *
     * @param num               number of shrubs added
     * @param name            shrub name
     * @param foliage             foliage type
     * @param information       free text detailing specific shrub information
     * @param ID                unique ID of shrub 
     * @throws NegativeNumberOfShrubsAddedException
     * @throws ShrubMismatchException
     * @throws IllegalIDException
     */
    public void addShrubs(int num, String name, Foliage foliage, String information, String ID) 
    throws NegativeNumberOfShrubsAddedException, ShrubMismatchException, IllegalIDException;

    /**
     * Method to set the price of a shrub with matching ID in stock.
     *
     * @param ID                ID of shrub
     * @param priceInPence      shrub price in pence
     * @throws NegativePriceException
     * @throws ShrubNotInStockException
     * @throws IllegalIDException
     */
    public void setShrubPrice(String ID, int priceInPence) throws NegativePriceException, 
    ShrubNotInStockException, IllegalIDException;


    /**
     * Method sells shrubs with the corresponding ID from the store and removes 
     * the sold shrubs from the stock.
     *
     * @param num           number of shrubs to be sold
     * @param ID            ID of shrubs to be sold
     * @throws ShrubNotInStockException
     * @throws InsufficientStockException
     * @throws NegativeNumberOfShrubsSoldException
     * @throws PriceNotSetException
     * @throws IllegalIDException
     */
    public void sellShrubs(int num, String ID) throws ShrubNotInStockException, 
    InsufficientStockException, NegativeNumberOfShrubsSoldException, 
    PriceNotSetException, IllegalIDException; 

    /**
     * Method reserves shrubs with the corresponding ID from the store 
     *
     * @param num           number of shrubs to be reserved
     * @param ID            ID of shrubs to be reserved
     * @return                  unique reservation number
     * @throws ShrubNotInStockException
     * @throws InsufficientStockException
     * @throws NegativeNumberOfShrubsReservedException
     * @throws PriceNotSetException
     * @throws IllegalIDException
     */
    public int reserveShrubs(int num, String ID) throws ShrubNotInStockException, 
    InsufficientStockException, NegativeNumberOfShrubsReservedException, 
    PriceNotSetException, IllegalIDException;
    
    /**
     * Method removes an existing reservation from the system due to a reservation cancellation
     * (rather than sale). The stock should therefore remain unchanged.
     *
     * @param reservationNumber           reservation number
     * @throws ReservationNumberNotRecognisedException
     */
    public void unreserveShrubs(int reservationNumber) throws ReservationNumberNotRecognisedException;
    
    /**
     * Method sells shrubs with the corresponding reservation number from the store and removes 
     * these sold shrubs from the stock.
     *
     * @param reservationNumber           reservation number
     * @throws ReservationNumberNotRecognisedException
     */
    public void sellShrubs(int reservationNumber) throws ReservationNumberNotRecognisedException;

    /**
     * Access method for the number of shrubs stocked by this ShrubMerchant.
     *
     * @return                  number of shrubs in this store
     */
    public int shrubsInStock();

    /**
     * Access method for the number of reserved shrubs stocked by this ShrubMerchant.
     *
     * @return                  number of reserved shrubs in this store
     */
    public int reservedShrubsInStock();

    /**
     * Method returns number of shrubs with matching ID in stock.
     *
     * @param ID            ID of shrub
     * @return              number of shrubs matching ID in stock
     * @throws IllegalIDException
     */
    public int shrubsInStock(String ID) throws IllegalIDException; 

    /**
     * Method saves this ShrubMerchant's contents into a serialised file, with the filename
     * given in the argument.
     *
     * @param filename      location of the file to be saved
     * @throws IOException
     */

    public void saveMerchantContents(String filename) throws IOException;
    
    /**
     * Method should load and replace this ShrubMerchant's contents with the 
     * serialised contents stored in the file given in the argument.
     *
     * @param filename      location of the file to be loaded
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public void loadMerchantContents(String filename) throws IOException, 
    ClassNotFoundException;
    
    /**
     * Access method for the number of different shrubs currently stocked by this 
     * ShrubMerchant.
     *
     * @return                  number of different specific shrubs currently in this store 
     *                          (i.e. how many different IDs currently with at least one stock item)
     */
    public int getNumberOfDifferentShrubsInStock();

    /**
     * Method to return number of shrubs sold by this ShrubMerchant.
     *
     * @return                  number of shrubs sold by the store
     */
    public int getNumberOfSoldShrubs();
    
    /**
     * Method to return number of shrubs sold by this ShrubMerchant with matching ID.
     *
     * @param ID                 ID of shrubs
     * @return                   number shrubs sold by the store with matching ID
     * @throws IllegalIDException
     */
    public int getNumberOfSoldShrubs(String ID) throws IllegalIDException;

    /**
     * Method to return total cost of shrubs sold by this ShrubMerchant (in pence).
     *
     * @return                  total cost of shrubs sold (in pence)
     */
   public int getCostOfSoldShrubs();

    /**
     * Method to return total cost of shrubs sold by this ShrubMerchant (in pence) 
     * with matching ID.
     *
     * @param ID                ID of shrubs
     * @return                  total cost of shrubs sold (in pence) with matching ID
     * @throws IllegalIDException
     */
    public int getCostOfSoldShrubs(String ID) throws IllegalIDException;

    /**
     * Method to total cost of reserved shrubs in this ShrubMerchant.
     *
     * @return                  total cost of reserved shrubs
     */ 
    public int getCostOfReservedShrubs();

    /**
     * Method to return textual details of a shrub  in stock. If there are no String
     * details for a shrub, there will be an empty String instance returned.
     *
     * @param ID                ID of shrub
     * @return                  any textual details relating to the shrub
     * @throws IllegalIDException
     */
    public String getShrubDetails(String ID) throws IllegalIDException;

    /**
     * Method empties this ShrubMerchant of its contents and resets 
     * all internal counters.
     */
    public void empty();

    /**
     * Method resets the tracking of number and costs of all shrubs sold. The stock 
     * levels of this ShrubMerchant and reservations should be unaffected.
     */
    public void resetSaleAndCostTracking();
}

