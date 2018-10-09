public class Myset{
	 LinkedList set;
	 Myset(){
	 	set=new LinkedList();
	 }

	  public int size()
    {
        return set.Size();
    }

	//public Boolean IsEmpty(): returns true if the set is empty
	public Boolean IsEmpty(){
		return set.IsEmpty();
	}	
	//public Boolean IsMember(Object o) Returns true if o is in the set, false otherwise.
	public Boolean IsMember(Object o){
		if (set.Find( o)==null) {
			return false;
		}
		else
			return true;

	}	
		
	//– public void Insert(Object o): Inserts o into the set.
	public void Insert(Object o){
		
		if(!IsMember(o)){
			set.Insert(o);
		}
	}
	//– public void Delete(Object o): Deletes o from the set, throws exception if o is not in the set.
	public void Delete(Object o) throws Exception{
		// System.out.println("hbje");
			set.Delete(o);
	}

	//– public Myset Union(Myset a): Returns a set which is the union of the current set with the set a.
	public Myset Union(Myset a){
		Myset c=new Myset();
		LinkedList.Node n = set.Peek();
		while(n!=null){
			c.Insert(n.data);
			n=n.next;
		}
		n=a.set.Peek();
		while(n!=null){
			if (!IsMember(n.data)) {
			c.Insert(n.data);	
			}
			n=n.next;
		}

		return c;
	}

	//public Myset Intersection(Myset a): Returns a set which is the intersection of the current set with the set a
	public Myset Intersection(Myset a){
		Myset c=new Myset();
		LinkedList.Node n=set.Peek();
			while(n!=null){
				if( a.IsMember(n.data))
					{
						c.Insert(n.data);
						// System.out.println("inserted an elment in inter set");
					}
				n=n.next;
			}
		return c;
	}

	public String toString()
    {
        return set.toString();
    }

	

	
	
	public static void main(String[] args) {
	Myset a = new Myset();
        System.out.println(a.IsEmpty());
        a.Insert(1);
        a.Insert(2);
        a.Insert(3);
        a.Insert(4);
        a.Insert(5);
        System.out.println(a.IsEmpty());

        Myset b = new Myset();
        b.Insert(4);
        b.Insert(3);
        b.Insert(0);
        b.Insert(9);
        b.Insert(7);
        b.Insert(-1);
        System.out.println(a.IsMember(1));
        System.out.println(a.IsMember(7));
        System.out.println(a);
        try{a.Delete(3);}catch(Exception e){
        	System.out.println(e);
        }
        System.out.println(a);
        System.out.println(b);
        System.out.println(a.Union(b));
        System.out.println(a.Intersection(b));
	
	
	}
}





class LinkedList{
	class Node{
		Object data;
		Node next;
		Node prev;

		Node(Object d){
			data=d;
			next=null;
			prev=null;
		}
	}
	Node head;
	Node tail;

	 public int Size(){
		Node n=head; int count=0;
		while(n!=null){
			++count;
			n=n.next;
		}
		return count;
	}

	public Node Peek()
	{
	    return head;
	}
	 public String toString()
    {
        String str= "";
        Node n = head;
        while(n != null)
        {
            str = str+n.data.toString();
            n = n.next;
        }
        return str;
    }
	public Node Find(Object d)
	{
		// System.out.println("finding "+d)
	    Node n = head;
	    while(n != null && d != n.data)
	    {
	        n = n.next;
	    }
	    return n;
	}

	public Boolean IsEmpty(){
		if(head==null)
			return true;
		else
			return false;
	}

	public boolean Insert(Object d){
		if (head==null) {
			Node new_node=new Node(d);
			head=new_node;
			tail=head;
		}
		else{
			Node new_node=new Node(d);
			tail.next=new_node;
			new_node.prev=tail;
			tail=new_node;
		}
		return true;
	}

	public void Delete(Object o) 
	{
	    Node new_node = Find(o);
	    
	    if(new_node == null)
	    {
	        throw new IllegalStateException();
	    }
	    else
	    {
	        Delete(new_node);
	    }
	}

	public Node Delete(Node node)
    {
       
        if(node == head)
        {
            if(head == tail)
            {
                head = null;
                tail = null;
            }
            else
            {
                head = head.next;
                head.prev = null;
            }
        }
        else
        {
            node.prev.next = node.next;
            if(node.next != null)
            {
                node.next.prev = node.prev;
            }
            else
            {
                tail = node.prev;
            }
        }
        return node.next;
    }

	
}