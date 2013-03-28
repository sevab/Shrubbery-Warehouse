package warehouse;
import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

/**
 * @author Student ID: 610051863 
 * @version 1.1
 */

public class NiShrubbery implements ShrubMerchant, Serializable
{
	private ShrubItem[] shrubsStockArray;
	private int elementsStocked;
	private int[][] matrix;
	private int[][] tracker;
	private int[][] reservations;
	private int reservationNumber;
	private int elementsReserved;
	private int archieve[];


	public NiShrubbery() {
		this.shrubsStockArray = new ShrubItem[10];
		this.elementsStocked = 0;
		this.matrix = new int[10][6]; 
		// two dimensional array 'matrix' will contain the following values in each row for each ID in database:
		//[ID number][numberInStock][numberReserved][numberSold][costInPence][reservationCost]
		// index of each ID in the 'matrix' array is the same as the index of a shrub with that ID in the shrubsStockArray
		
		this.tracker = new int[1][6];
		// two dimensional array 'tracker' will contain the following stats figures describing database:
		// [shrubsInStock][shrubsReserved][shrubsSold][uniqueShrubs][revenue][reservedCost]

		this.reservations = new int[10][3];
		// two dimensional array 'reservations' will contain the following reservation information in each row:
		//[reservationNumber][numberReserved][index of shrub in matrix and shrubsStockArray array]

		this.reservationNumber = 1000;
		this.elementsReserved = 0;
		this.archieve = new int[3];
	}



    /**
     * {@inheritDoc} 
     */
    @Override
	public void addShrubs(int num, String name, Foliage foliage, String ID) 
	throws NegativeNumberOfShrubsAddedException, ShrubMismatchException, IllegalIDException {
		if (num < 0)
			throw new NegativeNumberOfShrubsAddedException();


		int i = this.getIndex(ID);
		if (i>=0){ 			// if shrub exists and positive index was returned by getIndex(ID),simply increment its stock figures
			// make sure name and foliage match
			if (!this.shrubsStockArray[i].getShrubName().equals(name) || !this.shrubsStockArray[i].getShrubFoliage().equals(foliage))
				throw new ShrubMismatchException();
			else {	
				this.matrix[i][1]+=num;								// increment number of shrubs with this ID in stock
				this.tracker[0][0]+=num;							// increment number of Total shrubs in store
			}
		}
		else { // if shrub was not found in the system by getIndex(ID) and -1 was returned, throw exception
			if (this.elementsStocked == this.shrubsStockArray.length)
				this.resizeStock();
			this.shrubsStockArray[elementsStocked] = new ShrubItem((String) name, (Foliage) foliage, (String) ID);
			this.matrix[elementsStocked][0] = Integer.valueOf((String) ID);
			this.matrix[elementsStocked][1] = num;			// increment number of shrubs with this ID in stock
			this.tracker[0][0]+=num;						// increment number of Total shrubs in store
			this.tracker[0][3]+=1;							// increment number of unique shrubs in store
			this.elementsStocked++;
		}
	}

    /**
     * {@inheritDoc} 
     */
    @Override
	public void addShrubs(int num, String name, Foliage foliage, String information, String ID)
	throws NegativeNumberOfShrubsAddedException, ShrubMismatchException, IllegalIDException {
		this.addShrubs(num, name, foliage, ID);
		this.shrubsStockArray[elementsStocked-1].addShrubInfo(information);
		}

    /**
     * {@inheritDoc} 
     */
    @Override
	public void setShrubPrice(String ID, int priceInPence) throws NegativePriceException, 
	ShrubNotInStockException, IllegalIDException{
		if (priceInPence < 0)
			throw new NegativePriceException();

		int i = this.getIndex(ID);
		if (i>=0)			// if shrub exists and positive index was returned by getIndex(ID), set price
			this.shrubsStockArray[i].setShrubPrice(priceInPence);
		else 	// if shrub was not found in the system by getIndex(ID) and -1 was returned, throw exception
			throw new ShrubNotInStockException();
	}


    /**
     * {@inheritDoc} 
     */
    @Override
	public void sellShrubs(int num, String ID) throws ShrubNotInStockException, 
	InsufficientStockException, NegativeNumberOfShrubsSoldException, 
	PriceNotSetException, IllegalIDException{
		if (num < 0)
			throw new NegativeNumberOfShrubsSoldException();

		int i = this.getIndex(ID);
		if (i>=0){			// if shrub exists and positive index was returned by getIndex(ID), sell shrub
			String message = "Sorry, there are only " + (this.matrix[i][1]-this.matrix[i][2]) + " shrubs of ID " + ID + " available for purchasing. You are trying to sell " + num + " shrubs.";
			this.check3Exceptions(i, num, message);		// since reserveShrubs() method also has 3 overlaping exceptions checks, for the sake of not duplicating, checks have been put put in another method 

			this.matrix[i][1]-=num;			// reduce the stock of these shrubs
			this.matrix[i][3]+=num;			// increment number of these shrubs sold
			this.matrix[i][4]+=num*this.shrubsStockArray[i].getShrubPrice();		// increment revenue from this particular shrub
			this.tracker[0][0]-=num;				// reduce the value for the total number of shrubs in stock
			this.tracker[0][2]+=num;				// increase the value for the total number of shrubs sold
			this.tracker[0][4]+=num*this.shrubsStockArray[i].getShrubPrice();		// increment total revenue figure
			if (this.matrix[i][1] == 0)		// if ran out of stock for this shrub, update value of unique number of shrubs in stock 
				this.tracker[0][3]--;
		}
		else 	// if shrub was not found in the system by getIndex(ID) and -1 was returned, throw exception
			throw new ShrubNotInStockException();
	}


    /**
     * {@inheritDoc} 
     */
    @Override
	public int reserveShrubs(int num, String ID) throws ShrubNotInStockException, 
	InsufficientStockException, NegativeNumberOfShrubsReservedException, 
	PriceNotSetException, IllegalIDException{

		if (num < 0)
			throw new NegativeNumberOfShrubsReservedException();

		int i = this.getIndex(ID);
		if (i>=0){				// if shrub exists and positive index was returned by getIndex(ID), make a reservation

			String message = "Sorry, there are only " + (this.matrix[i][1]-this.matrix[i][2]) + " shrubs of ID " + ID + " available for reservations. You are trying to reserve " + num + " shrubs.";
			this.check3Exceptions(i, num, message);		// since sellShrubs() method also has 3 overlaping exceptions checks, for the sake of not duplicating, checks have been put put in another method

			this.matrix[i][2]+=num;		// increment number of these shrubs reserved
			this.matrix[i][5]+=num*this.shrubsStockArray[i].getShrubPrice();	// increment retail value of shrubs reserved
			this.tracker[0][1]+=num;	// increment total number of shrubs reserved
			this.tracker[0][5]+=num*this.shrubsStockArray[i].getShrubPrice();	// increment total retail value of shrubs reserved

			if (this.elementsReserved == this.reservations.length)
				this.resizeReserve();			// resize reserve array if full

			this.reservations[elementsReserved][0] = this.reservationNumber;
			this.reservations[elementsReserved][1] = num;
			this.reservations[elementsReserved][2] = i;

			this.reservationNumber++; //unite with the previous one?
			this.elementsReserved++;
			return this.reservations[elementsReserved-1][0];
		}
		else 						// if shrub was not found in the system by getIndex(ID) and -1 was returned, throw exception
			throw new ShrubNotInStockException();
	}

    /**
     * {@inheritDoc} 
     */
    @Override
	public void unreserveShrubs(int reservationNumber) throws ReservationNumberNotRecognisedException{
		boolean flag = true;
		for (int i = 0; i < this.elementsReserved; i++) {
			if (this.reservations[i][0] == reservationNumber) {		// search for matching reservationNumber in reservations array

				int index = this.reservations[i][2];
				this.matrix[index][2] -= this.reservations[i][1];		// substract number of shrabs reserved value	
				this.matrix[index][5] -= this.reservations[i][1]*this.shrubsStockArray[index].getShrubPrice();	// substract retail value of sharbs reserved
				this.tracker[0][1] -= this.reservations[i][1];			// substract number of total shrabs reserved value
				this.tracker[0][5] -= this.reservations[i][1]*this.shrubsStockArray[index].getShrubPrice();		// substract total retail value of sharbs reserved

				// remove reservation
				for (int e=i+1; e<this.elementsReserved; e++ )
					for (byte j=0; j<3 ; j++)
						this.reservations[e-1][j] = this.reservations[e][j];

				i = this.elementsReserved;
				this.elementsReserved--;
				flag = false;	// mark that reservation number was found
			}
		}
		if (flag)	// if searched through, and didn't find matching reservation number, throw exception
			throw new ReservationNumberNotRecognisedException();
	}


    /**
     * {@inheritDoc} 
     */
    @Override
	public void sellShrubs(int reservationNumber) throws ReservationNumberNotRecognisedException{
		boolean flag = true;
		for (int i = 0; i < this.elementsReserved; i++) {
			if (this.reservations[i][0] == reservationNumber) {		// search for matching reservationNumber in reservations array

				int num = this.reservations[i][1];		// cache values before unreserving 
				int index = this.reservations[i][2];
				this.unreserveShrubs(reservationNumber); //unreserve corresponding reservation first
				this.matrix[index][1]-=num;				// reduce the stock of these shrubs
				this.matrix[index][3]+=num;				// increment number of these shrubs sold
				this.matrix[index][4]+=num*this.shrubsStockArray[index].getShrubPrice();		// increment revenue from this particular shrub
				this.tracker[0][0]-=num;				// reduce the value for the total number of shrubs in stock
				this.tracker[0][2]+=num;				// increase the value for the total number of shrubs sold
				this.tracker[0][4]+=num*this.shrubsStockArray[index].getShrubPrice();		// increment total revenue figure
				if (this.matrix[index][1] == 0)			// if ran out of stock for this shrub, update value of unique number of shrubs in stock
					this.tracker[0][3]--;
				flag = false;	// mark that reservation number was found
		}
	}
		if (flag)	// if searched through, and didn't find matching reservation number, throw exception
			throw new ReservationNumberNotRecognisedException();
}

    /**
     * {@inheritDoc} 
     */
    @Override
	public int shrubsInStock(){
		return tracker[0][0];
	}



    /**
     * {@inheritDoc} 
     */
    @Override
	public int reservedShrubsInStock(){
		return tracker[0][1];
	}


    /**
     * {@inheritDoc} 
     */
    @Override
	public int shrubsInStock(String ID) throws IllegalIDException{
		int i = this.getIndex(ID);
		return matrix[i][1];
	}

    /**
     * {@inheritDoc} 
     */
    @Override
	public void saveMerchantContents(String filename) throws IOException {

		// pack integers into archieve array for serialization
		this.archieve[0] = elementsStocked;
		this.archieve[1] = reservationNumber;
		this.archieve[2] = elementsReserved;

		FileOutputStream fileOutput = new FileOutputStream(filename);
		ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
		
		objectOutput.writeObject(this.shrubsStockArray);
		objectOutput.writeObject(this.matrix);
		objectOutput.writeObject(this.tracker);
		objectOutput.writeObject(this.reservations);
		objectOutput.writeObject(this.archieve);
		objectOutput.close();
	}

    /**
     * {@inheritDoc} 
     */
    @Override
	public void loadMerchantContents(String filename) throws IOException, 
	ClassNotFoundException{
		FileInputStream fileInput = new FileInputStream(filename);
		ObjectInputStream objectInput = new ObjectInputStream(fileInput); 

		shrubsStockArray = (ShrubItem[]) objectInput.readObject();
		matrix = (int[][]) objectInput.readObject();
		tracker = (int[][]) objectInput.readObject();
		reservations = (int[][]) objectInput.readObject();
		archieve = (int[]) objectInput.readObject();
		objectInput.close();

		// unpack integers from archieve array
		this.elementsStocked = this.archieve[0];
		this.reservationNumber = this.archieve[1];
		this.elementsReserved = this.archieve[2];
	}
	

    /**
     * {@inheritDoc} 
     */
    @Override
	public int getNumberOfDifferentShrubsInStock(){
   		return tracker[0][3];
   }

    /**
     * {@inheritDoc} 
     */
    @Override
	public int getNumberOfSoldShrubs(){
   		return tracker[0][2];
   }

    /**
     * {@inheritDoc} 
     */
    @Override
	public int getNumberOfSoldShrubs(String ID) throws IllegalIDException{
		int i = this.getIndex(ID);
		return matrix[i][3];
	}

    /**
     * {@inheritDoc} 
     */
    @Override
   public int getCostOfSoldShrubs(){
   		return tracker[0][4];
   }

    /**
     * {@inheritDoc} 
     */
    @Override
	public int getCostOfSoldShrubs(String ID) throws IllegalIDException{
		int i = this.getIndex(ID);
		return matrix[i][4];
	}

    /**
     * {@inheritDoc} 
     */
    @Override
	public int getCostOfReservedShrubs(){
		return tracker[0][5];
	}

    /**
     * {@inheritDoc} 
     */
    @Override
	public String getShrubDetails(String ID) throws IllegalIDException{
		int i = this.getIndex(ID);
		return shrubsStockArray[i].getShrubInfo();
	}

    /**
     * {@inheritDoc} 
     */
    @Override
	public void empty(){
		this.shrubsStockArray = new ShrubItem[10];
		this.elementsStocked = 0;
		this.matrix = new int[10][6];
		this.tracker = new int[10][6];
		this.reservations = new int[10][3];
		this.reservationNumber = 1000;
		this.elementsReserved = 0;
	}

    /**
     * {@inheritDoc} 
     */
    @Override
	public void resetSaleAndCostTracking(){
		this.tracker[0][2] = 0;
		this.tracker[0][4] = 0;
		this.tracker[0][5] = 0;

		for (int i = 0; i < elementsStocked; i++) {
			this.matrix[i][4] = 0;
			this.matrix[i][5] = 0;
		}
	}



	/**
	 * Method returns the index of the queried shrub within matrix and shrubsStockArray (same index)
	 *
	 * @param ID                unique ID of shrub 
	 * @throws IllegalIDException
	 */
	private int getIndex(String ID) throws IllegalIDException{
		isIllegalIDException(ID);

		// convert string into integer
		int id = Integer.valueOf((String) ID);

		// if a shrub with specified ID already exists, return it's index within matrix and shrubsStockArray (same index)
		// otherwise return -1 
		for (int i=0; i<elementsStocked; i++)
			if (matrix[i][0] == id)
				return i;
		return -1;
		}


	/**
	 * Method doubles the capacity of the shrubsStockArray and matrix arrays
	 */
	private void resizeStock(){
		ShrubItem[] tempArray = new ShrubItem[this.shrubsStockArray.length*2];
		int[][] tempMatrix = new int[this.matrix.length*2][6];

		for (int i=0; i<this.shrubsStockArray.length; i++){
			tempArray[i] = this.shrubsStockArray[i];
			for (byte e = 0; e<6 ; e++)					// copy 6 values in matrix 2-dimensional array for every row
				tempMatrix[i][e] = this.matrix[i][e];
		}

		this.shrubsStockArray = tempArray;		// redefine shrubsStockArray array with values from tempArray
		this.matrix = tempMatrix;				// redefine matrix array with values from tempMatrix
	}

	
	/**
	 * Method doubles the capacity of the reservations
	 */
	private void resizeReserve(){
		int[][] tempReserve = new int[this.reservations.length*2][3];

		for (int i=0; i<this.reservations.length; i++){
			for (byte e = 0; e<3 ; e++)					// copy 3 values in reserve 2-dimensional array for every row
				tempReserve[i][e] = this.reservations[i][e];
		}
		this.reservations = tempReserve;				// redefine reservations array with values from tempReserve
	}




	/**
	* Method throws exception if the string passed doesn't contain 6 digits
	*
	* @throws IllegalIDException
	* @param ID
	*/
	private void isIllegalIDException(String ID) throws IllegalIDException {
		// check if length is more than 6 characters
		if (ID.length() != 6)
			throw new IllegalIDException("Entered ID is not a 6-digit number");

		// check if contains digits only
		int id;
		try {
			id = Integer.valueOf((String) ID);
		} catch (NumberFormatException e) {
			throw new IllegalIDException("Entered ID should contain digits only");
		}

		// make sure is a 6-digit integer in the following range
		if (id < 99999 || id > 999999)
			throw new IllegalIDException();
	}

	/**
	* Method throws exception if the string passed doesn't contain 6 digits
	*
	* @throws PriceNotSetException
	* @throws ShrubNotInStockException
	* @throws InsufficientStockException
	* @param i 			index
	* @param num
	* @param message
	*/
	private void check3Exceptions(int i, int num, String message) throws
	PriceNotSetException, ShrubNotInStockException, InsufficientStockException{

		if (this.shrubsStockArray[i].getShrubPrice() == 0)
			throw new PriceNotSetException();

		if (this.matrix[i][1] == 0)								// throw ShrubNotInStockException if ran out of stock for these particular shrubs
			throw new ShrubNotInStockException();
		
		if ((this.matrix[i][1]-this.matrix[i][2]) < num)		// throw InsufficientStockException if number of available shrubs, excluding reservations, is less than num
			throw new InsufficientStockException(message);
}


	/**
	* Method throws exception if a negative number of shrubs is entered as an argument
	* @throws NegativeNumberOfShrubsSoldException
	* @param num
	*/
	private void isNegativeNumberOfShrubsSoldException(int num) throws NegativeNumberOfShrubsSoldException {
		if (num < 0)
			throw new NegativeNumberOfShrubsSoldException();
	}

}