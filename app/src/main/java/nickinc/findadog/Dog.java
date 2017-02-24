package nickinc.findadog;


public class Dog {

    String dogBreed;
    String time;
    String name;

    Dog(String db, String t, String n) {
        dogBreed = db;
        time = t;
        name = n;
    }

    Dog(String db, String t) {
        dogBreed = db;
        time = t;
    }

}
