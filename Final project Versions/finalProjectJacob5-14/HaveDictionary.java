import java.util.Iterator;
import java.util.ArrayList;
/**
An interface for a HaveDictionary with distinct search keys.
@author Frank M. Carrano
@version 3.0
 */
public class HaveDictionary
{

    private ArrayList<String> keys;
    private ArrayList<ArrayList<Person>> values;

    
    public HaveDictionary()
    {
        keys = new ArrayList<String>();
        values = new ArrayList<ArrayList<Person>>();
    }

    public HaveDictionary(int sortType)
    {
        keys = new ArrayList<String>();
        values = new ArrayList<ArrayList<Person>>();
        sortType = sortType;
    }

    /** Adds a new entry to this HaveDictionary. If the given search
    key already exists in the HaveDictionary, replaces the
    corresponding value.
    @param key an object search key of the new entry
    @param value an object associated with the search key
    @return either null if the new entry was added to the HaveDictionary
    or the value that was associated with key if that value
    was replaced */
    public void add(String key, Person value)
    {
        //         int location;
        //         if((location = keys.indexOf(key))!=-1)
        //         {
        //             values.remove(location);//make sure to remove obsolete data
        //             values.add(location,value);// replace item at location
        //         }
        //         else
        //         {
        //             keys.add(key);//add new key/value pair
        //             values.add(value);//at the end of keys value list
        //         }
        int location;
        if((location = keys.indexOf(key))!=-1)
        {
            values.get(location).add(value);// replace item at location
        }
        else
        {
            keys.add(key);//add new key/value pair
            ArrayList<Person> temp = new ArrayList<Person>();
            values.add(temp);//at the end of keys value list
            temp.add(value);
        }
    }

    /** Removes a specific entry from this HaveDictionary.
    @param key an object search key of the entry to be removed
    @return either the value that was associated with the search key
    or null if no such object exists */
    public Person remove(String key, int index)
    {
        if(keys.contains(key))
        { //if key is in there
            int location = keys.indexOf(key);//find the lcoation
            Person temp = values.get(location).remove(index); //remove key and value from HaveDictionary
            return temp;
        }
        return null;            
    }

    /** Retrieves from this HaveDictionary the value associated with a given
    search key.
    @param key an object search key of the entry to be retrieved
    @return either the value that is associated with the search key
    or null if no such object exists */
    public Person getValue(String key, int index)
    {
        if(keys.contains(key))
        {
            int location = keys.indexOf(key);
            return values.get(location).get(index);
        }
        return null; //returns value at certain key location      
    }

    public ArrayList<Person> getList(String key)
    {
        if(keys.contains(key))
        {
            int location = keys.indexOf(key);
            return values.get(location);
        }
        return null; //returns value at certain key location      
    }

    /** Sees whether a specific entry is in this HaveDictionary.
    @param key an object search key of the desired entry
    @return true if key is associated with an entry in the
    HaveDictionary */
    public boolean contains(String key)
    {
        return keys.contains(key);            
    }

    /** Creates an iterator that traverses all search keys in this
    HaveDictionary.
    @return an iterator that provides sequential access to the search
    keys in the HaveDictionary */
    public Iterator<String> getKeyIterator()
    {
        return keys.iterator();            
    }

    /** Creates an iterator that traverses all values in this HaveDictionary.
    @return an iterator that provides sequential access to the values
    in this HaveDictionary */
    public Iterator<ArrayList<Person>> getValueIterator()
    {
        return values.iterator();            
    }

    /** Sees whether this HaveDictionary is empty.
    @return true if the HaveDictionary is empty */
    public boolean isEmpty()
    {
        return keys.isEmpty();            
    }

    /** Gets the size of this HaveDictionary.
    @return the number of entries (key-value pairs) currently
    in the HaveDictionary */
    public int getSize()
    {
        return keys.size();            
    }

    /** Removes all entries from this HaveDictionary. */
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

} // end HaveDictionaryInterface