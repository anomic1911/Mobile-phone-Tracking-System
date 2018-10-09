public class MobilePhone
{
    private int number;
    private boolean pstatus;
    private Exchange baseStation;

    public MobilePhone(int number)
    {
        this.number = number;
        pstatus=false;
    }

    public int getId()
    {
        return number;
    }

    // – public Int number(): returns the id of the mobile phone.
    public int number()
    {
        return this.number;
    }

    // – public Boolean status(): returns the status of the phone, i.e. switched on or switched off.
    public boolean status()
    {
        return pstatus;
    }

    // – public void switchOn(): Changes the status to switched on
    public void switchOn()
    {
        pstatus = true;
    }

     // – public void switchOff(): Changes the status to switched off.
    public void switchOff()
    {
        pstatus = false;
    }
    // – public Exchange location(): returns the base station with which the phone is registered if the phone is switched on 
    // and an exception if the phone is off. The class Exchange will be described next.
    public Exchange location() 
    {
        // if(pstatus)
        // {
            return baseStation;
        // }
        // else
        // {
        //     throw new Exception("phone is off");
        // }
    }

    public void setBase(Exchange base) 
    {
        
            baseStation = base;

        

        
       
    }
}

