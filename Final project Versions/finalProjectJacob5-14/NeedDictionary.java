 

import java.util.Iterator;
import java.util.ArrayList;
/**
 * This NeedDictionary is designed to take a position in a business as a String, ie, "Busser" or "Host," and link it to an integer that correlates to the total number
 * of employees that are needed on that day.
@author Frank M. Carrano
@version 3.0
 */
public class NeedDictionary
{
    public ArrayList<String> keys;
    public ArrayList<Integer> values;
    
    public NeedDictionary()
    {
        keys = new ArrayList<String>();
        values = new ArrayList<Integer>();
    }
    
    public NeedDictionary(int sortType)
    {
        keys = new ArrayList<String>();
        values = new ArrayList<Integer>(); 
        sortType = sortType;
    }
    
    /** Adds a new entry to this NeedDictionary. If the given search
    key already exists in the NeedDictionary, replaces the
    corresponding value.
    @param key an object search key of the new entry
    @param value an object associated with the search key
    @return either null if the new entry was added to the NeedDictionary
    or the value that was associated with key if that value
    was replaced */
    public void add(String key, Integer value)//Coby Forrester & Jacob Waters
    {
        int location;
        if((location = keys.indexOf(key))!=-1)
        {
            values.remove(location);//make sure to remove obsolete data
            values.add(location,value);// replace item at location
        }
        else
        {
            keys.add(key);//add new key/value pair
            values.add(value);//at the end of keys value list
        }
    }

    /** Removes a specific entry from this NeedDictionary.
    @param key an object search key of the entry to be removed
    @return either the value that was associated with the search key
    or null if no such object exists */
    public Integer remove(String key, int index)//Coby Forrester & Jacob Waters
    {
        if(keys.contains(key))
        { //if key is in there
            int location = keys.indexOf(key);//find the lcoation
            Integer temp = values.remove(index); //remove key and value from NeedDictionary
            return temp;
        }
        return null;            
    }

    /** Retrieves from this NeedDictionary the value associated with a given
    search key.
    @param key an object search key of the entry to be retrieved
    @return either the value that is associated with the search key
    or null if no such object exists */
    public Integer getValue(String key, int index)//Coby Forrester & Jacob Waters
    {
        if(keys.contains(key))
        {
            int location = keys.indexOf(key);
            return values.get(index);
        }
         return null; //returns value at certain key location      
    }
    
    /**
     * @ return This returns the Value associated with the Key
     * @param key This is the key used to find the value.
       */
    public Integer getValue(String key)//Coby Forrester & Jacob Waters
    {
        if(keys.contains(key))
        {
            int location = keys.indexOf(key);
            return values.get(location);
        }
         return null; //returns value at certain key location      
    }
    
    /** Sees whether a specific entry is in this NeedDictionary.
    @param key an object search key of the desired entry
    @return true if key is associated with an entry in the
    NeedDictionary */
    public boolean contains(String key)//Coby Forrester & Jacob Waters
    {
       return keys.contains(key);            
    }

    /** Creates an iterator that traverses all search keys in this
    NeedDictionary.
    @return an iterator that provides sequential access to the search
    keys in the NeedDictionary */
    public Iterator<String> getKeyIterator()
    {
        return keys.iterator();            
    }

    /** Creates an iterator that traverses all values in this NeedDictionary.
    @return an iterator that provides sequential access to the values
    in this NeedDictionary */
    public Iterator<Integer> getValueIterator()//Coby Forrester & Jacob Waters
    {
        return values.iterator();            
    }

    /** Sees whether this NeedDictionary is empty.
    @return true if the NeedDictionary is empty */
    public boolean isEmpty()
    {
        return keys.isEmpty();            
    }

    /** Gets the size of this NeedDictionary.
    @return the number of entries (key-value pairs) currently
    in the NeedDictionary */
    public int getSize()
    {
        return keys.size();            
    }

    /** Removes all entries from this NeedDictionary. */
    public void clear()
    {
        keys.clear();
        values.clear();
    }
    
    public static void print(ArrayList array)
    {
        for(int i = 0; i < array.size(); i++)
        {
            System.out.println(array.get(i));
        }
    }
    
} 