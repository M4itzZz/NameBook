package tthk.opilane.namebook;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class People implements Serializable{

    private static final String filename="papersons";
    private List<Person> personList = new ArrayList<>();
    private transient Context context;

    public People(Context context)throws Exception {
        this.context = context;
        FileInputStream stream = null;
        try {
            stream = context.openFileInput(filename);
        }
        catch (Exception ex) {return;}

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        for (String line = reader.readLine(); line != null; line = reader.readLine())
            try {
                personList.add(new Person(line));
            } catch (Exception ex) {
                reader.close();
            }
    }
    public Person getPerson(int n){return personList.get(n);}
    public List<Person>getPeople(){return personList;}

    public void save(Person person)throws Exception{
        FileOutputStream stream= context.openFileOutput(filename, Context.MODE_APPEND);
        BufferedWriter writer= new BufferedWriter(new OutputStreamWriter(stream));
        writer.write(person.getPerson());
        writer.newLine();
        writer.close();
        personList.add(person);
    }
    public void remove(Context context, Person person)throws Exception{
        if (personList.remove(person)){
            FileOutputStream stream=context.openFileOutput(filename, Context.MODE_PRIVATE);
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(stream));
            for (Person p:personList){
                writer.write(p.getPerson());
                writer.newLine();
            }
            writer.close();
        }
        else throw new Exception("Person could not be removed");
    }
}
