package warehouse;


public class IllegalIDException extends Exception
{
 
    public IllegalIDException()
    {
        super();
    }


    public IllegalIDException(String details)
    {
        super(details);
    }

}
