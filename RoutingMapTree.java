import java.util.*;
import java.util.*;


 class Exchange
{
    private int id;
    private boolean isRoot;
    private MobilePhoneSet PhoneSet;
    private ExchangeList childs;
    private Exchange parent;
    // Exchange(Int number): constructor to create an exchange. Unique identifier for an exchange is an integer.
     public Exchange(int id)
    {

        this.id = id;
        childs = new ExchangeList();
        PhoneSet = new MobilePhoneSet();

    }

    public int getId()
    {
        return id;
    }
   // public Exchange numChildren() (for number of children),
    public int numChildren()
    {
        return childs.Size();
    }

    public void MobileRegister(MobilePhone a)
    {
        PhoneSet.Insert(a);
    }

    public void MobileDeregister(MobilePhone a)
    {
        try{
            PhoneSet.Delete(a);
        }catch(Exception e){
            System.out.println(e);
        }
        
    }
  

    public void addChild(Exchange a)
    {
        a.setParent(this);
        childs.Insert(a);
    }
// public Exchange child(int i) (returns the ith child),
    public Exchange child(int i)
    {

        ExchangeList.Node n = childs.Peek();
         if (i>=0) {
            int j=0;
             while(i<childs.Size() && j<i ){

                 n=n.next;
                 j++;
             }
             return (Exchange) n.data;
         }
       
        else
        {
            return null;
        }
    }
    // public Exchange parent()
    public Exchange parent()
    {
        return parent;
    }
    // public Boolean isRoot(),
    public boolean isRoot()
    {
        return isRoot;
    }

    public void setRoot()
    {
        isRoot = true;
    }
    public void removeRoot()
    {
        isRoot = false;
    }
    // – public MobilePhoneSet residentSet(): This returns the resident set of mobile phones of the exchange.
    public MobilePhoneSet residentSet()
    {
        return PhoneSet;
    }
    public void setParent(Exchange a)
    {
        parent = a;
    }
    // public RoutingMapTree subtree(int i) (returns the ith subtree)
    public RoutingMapTree subtree(int i)
    {
        Exchange n = child(i);
        if(n != null)
        {
            RoutingMapTree t=new RoutingMapTree(n);
            return t;
        }
        return null;
    }
}

class RoutingMapTree
{
    Exchange root;
    // RoutingMapTree(): constructor method. This should create a
// RoutingMapTree with one Exchange node, the root node which has an identifier of 0. 
    public RoutingMapTree()
    {
        root = new Exchange(0);
        root.setRoot();
    }
// RoutingMapTree(): constructor method  - other constructors 
    public RoutingMapTree(Exchange root)
    {
        this.root = root;
    }
    // Boolean containsNode(Exchange a)
    public boolean containsNode(Exchange a)
    {
        if(root == a)
        {
            return true;
        }
        for(int i=0; i<root.numChildren();i++)
        {
            if(root.subtree(i+1).containsNode(a))
            {
                return true;
            }
        }
        return false;
    }

    // public Exchange findPhone(MobilePhone m): Given a mobile phone m it returns the level 0 area exchange with which it is registered or
        // throws an exception if the phone is not found or switched of
    public Exchange findPhone(MobilePhone m) throws Exception{
        if(root.residentSet().IsMember(m)){
            if(m.status()==true){
                return m.location();
            }
            else{
                throw new Exception("Phone not turned on");
            }
        }       
        else{
            throw new Exception("Phone not present");
        }
    }

    // public Exchange lowestRouter(Exchange a, Exchange b): Given two level 0 area exchanges a and b this method returns the level i
    // exchange with the smallest possible value of i which contains both a and b in its subtree. If a = b then the answer is a itself

    public Exchange lowestRouter(Exchange a, Exchange b){

        if(a==b){
            return a;
        }
        else{
           return lowestRouter(a.parent(),b.parent());
        }

    }

    // public ExchangeList routeCall(MobilePhone a, MobilePhone b): This method helps initiate a call from phone a to phone b.
     // It returns a list of exchanges.
    public ExchangeList routeCall(MobilePhone a, MobilePhone b){
        try{
                Exchange A = findPhone(a);
                Exchange B = findPhone(b);

                Exchange parentNode=lowestRouter(A,B);
                ExchangeList shortestRoute=new ExchangeList();
                recurAbove(A,B,shortestRoute,parentNode);
                return shortestRoute;

            }catch(Exception e){
                System.out.println(e);
                return null;
            }

    }

    private void recurAbove(Exchange a,Exchange b, ExchangeList list, Exchange parentNode) throws Exception{
        list.Insert(a);
        if(a==parentNode)
            return;
        else{
            recurAbove(a.parent(),b.parent(),list,parentNode);
        }
         list.Insert(b);
    }

    // • public void movePhone(MobilePhone a, Exchange b): This method modifies the routing map by changing the location of
     // mobile phone a from its current location to the base station b
    public void movePhone(MobilePhone a, Exchange b) throws Exception{
            
        if (b.numChildren()>0) {
            throw new Exception("Error - Exchange not base station");
        }

         switchOff(a);   
         Exchange base=a.location();
          while(base != null)
                 {
                base.MobileDeregister(a);
                base = base.parent();
                 }
        switchOn(a,b);

        // System.out.println("succ "+a.location().getId());

    }

    // public void switchOn(MobilePhone a, Exchange b): This method only works on mobile phones that are currently switched off. It
// switches the phone a on and registers it with base station b. The entire routing map tree will be updated accordingly
    public void switchOn (MobilePhone a, Exchange b)
    {
        if(a.status())
        {
            System.out.println("Phone is not switched Off");
            return;
        }
        else
        {
            a.switchOn();
            a.setBase(b);
            b.MobileRegister(a);
            while(!b.isRoot())
            {
                b = b.parent();
                b.MobileRegister(a);
            }
        }
    }

    // public void switchOff(MobilePhone a): This method only works on mobile phones that are currently switched on. It switches the
    // phone a off. The entire routing map tree has to be updated accordingly.
    public void switchOff (MobilePhone a) throws Exception
    {

        if (a==null) {
            throw new Exception("phone not found");
        }
        else{
            if(a.status()==false)
            {
                throw new Exception("Phone is not switched on");
            }
            else
            {   
               a.switchOff();
            }    
            
        }
        
    }

    public MobilePhone SearchPhone(int identifier)
    {
        LinkedList mainList = root.residentSet().set;
        LinkedList.Node n = mainList.Peek();
        
        while(n!=null && (((MobilePhone) n.data).getId() != identifier))
        {
            n = n.next;
        }

        if(n == null)
        {
            return null;
        }
        return (MobilePhone) n.data;
    }

    public Exchange SearchExchange(int identifier)
    {
        
        Exchange temp ;
        if (root.getId() == identifier)
        {
            return root;
        }
        for(int i=0; i<root.numChildren();i++)
        {
            temp = root.subtree(i).SearchExchange(identifier);
            if(temp != null)
            {
                return temp;
            }
        }
        return null;
    }

    public String performAction(String actionMessage)
    {
        Scanner message = new Scanner(actionMessage);
        String action = message.next();
        String result="";
        try
        {
            if(action.equals("addExchange"))
            {
                Exchange exchangeA = SearchExchange(message.nextInt());
                Exchange exchangeB = new Exchange(message.nextInt());
                if(exchangeA == null)
                {
                    throw new Exception();
                }
                exchangeA.addChild(exchangeB);
            }
            else if (action.equals("findPhone")) {
                int mobileIdentifier=message.nextInt();
                MobilePhone m=SearchPhone(mobileIdentifier);
                if (m==null) {
                    result+="queryFindPhone "+mobileIdentifier+": "+"Error - No mobile phone with identifier "+mobileIdentifier+" found in the network";
                }
                else if(m.status()==true){
                    Exchange e=findPhone(m);
                    result+="queryFindPhone "+mobileIdentifier+": "+e.getId();
                }
                else{
                    result+="queryFindPhone "+mobileIdentifier+": "+" Error - Mobile phone with identifier " +mobileIdentifier+ " is currently switched off" ; 
                }
            }
            else if (action.equals("lowestRouter")) {
                int mobileIdentifier1 = message.nextInt();
                int mobileIdentifier2 = message.nextInt();
                Exchange e1 = SearchExchange(mobileIdentifier1);
                Exchange e2 = SearchExchange(mobileIdentifier2);
                Exchange lowestSubTree=lowestRouter(e1,e2);
                result+="queryLowestRouter "+mobileIdentifier1+" "+mobileIdentifier2+": "+lowestSubTree.getId();


            }
             else if(action.equals("findCallPath"))
            {
                String mString="";
                 int mobileIdentifier1 = message.nextInt();
                int mobileIdentifier2 = message.nextInt();
                MobilePhone m1=SearchPhone(mobileIdentifier1);
                MobilePhone m2=SearchPhone(mobileIdentifier2);
              
                if (m1==null) {
                     result+="queryFindCallPath "+mobileIdentifier1+" "+mobileIdentifier2+": "+"Error - No mobile phone with identifier "+mobileIdentifier1+" found in the network";
                }
                else if (m2==null) {
                     result+="queryFindCallPath "+mobileIdentifier1+" "+mobileIdentifier2+": "+"Error - No mobile phone with identifier "+mobileIdentifier2+" found in the network";
                }
                else if (m1.status()==true && m2.status()==true) {
                    
                    ExchangeList shortestList=routeCall(m1,m2);
                    if (shortestList!=null) {
                        ExchangeList.Node n=shortestList.Peek();
                    
                        if(n != null)
                        {
                            mString = ""+((Exchange)n.data).getId();
                            n = n.next; 
                        }
                        while(n != null)
                        {
                            mString += ", " + ((Exchange) n.data).getId();
                            n = n.next;
                        }
                    }
                     result+="queryFindCallPath "+mobileIdentifier1+" "+mobileIdentifier2+": "+mString;
                }
                else{
                    if (m1.status()==false) {
                        result+="queryFindCallPath "+mobileIdentifier1+" "+mobileIdentifier2+": "+"Error - Mobile phone with identifier " +mobileIdentifier1+ " is currently switched off" ;   
                    }
                    if (m2.status()==false) {
                        result+="queryFindCallPath "+mobileIdentifier1+" "+mobileIdentifier2+": "+"Error - Mobile phone with identifier " +mobileIdentifier2+ " is currently switched off";
                    }
                    
                }
                
            }
            else if (action.equals("movePhone")) {
                int mobileIdentifier = message.nextInt();
                MobilePhone a=SearchPhone(mobileIdentifier);

                Exchange b = SearchExchange(message.nextInt());
                if (a.status()==true) {
                    try{
                        movePhone(a,b);
                    }catch(Exception e){
                        System.out.println("Error - Exchange not base station");
                    }
                    
                }
                else{
                    result+="Error - Mobile phone with identifier " +mobileIdentifier+ " is currently switched off";
                }
            }
            else if(action.equals("switchOnMobile"))
            {
                int mobileIdentifier = message.nextInt();

                Exchange exchange = SearchExchange(message.nextInt());
                if(exchange == null)
                {
                    throw new Exception ();
                }
                else
                {
                    MobilePhone newMobile = SearchPhone(mobileIdentifier);
                    if(newMobile != null)
                    {
                        if(newMobile.status())
                        {
                            throw new Exception("Phone is already ON");
                        }
                        else
                        {
                            Exchange e = newMobile.location();
                                    while(e != null)
                                    {
                                        e.MobileDeregister(newMobile);
                                        e = e.parent();
                                    }
                        }
                    }
                    else
                        newMobile = new MobilePhone(mobileIdentifier);
                    switchOn(newMobile,exchange);
                }
            }
            else if(action.equals("switchOffMobile"))
            {
                int mobileIdentifier=message.nextInt();
               
                MobilePhone mobile = SearchPhone(mobileIdentifier);
                
                if(mobile == null)
                {

                    throw new Exception ();
                }
                switchOff(mobile);
            }
            else if(action.equals("queryNthChild"))
            {
                Exchange exchange = SearchExchange(message.nextInt());
                int nthChild = message.nextInt();
                if(exchange == null)
                {
                    throw new Exception();
                }
                
                result+=actionMessage+": "+exchange.child(nthChild).getId();
            }
            else if(action.equals("queryMobilePhoneSet"))
            {
                
                String mString = "";
                Exchange exchange = SearchExchange(message.nextInt());
                if(exchange == null)
                {
                    throw new Exception();
                }
                LinkedList mobileSet = exchange.residentSet().set;
                LinkedList.Node n = mobileSet.Peek();

                if(n != null)
                {
                    mString = ""+((MobilePhone)n.data).getId();
                    n = n.next;
                }
                while(n != null)
                {   
                    if (  ((MobilePhone) n.data).status()==true  ){
                        mString += ", " + ((MobilePhone) n.data).getId();    
                    }
                    
                    n = n.next;
                }
                result+=actionMessage+": "+mString;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        // System.out.println(resul)t;
        return result;
    }
}