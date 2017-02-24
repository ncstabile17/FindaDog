package nickinc.findadog;


import java.io.Serializable;

public class Dog implements Serializable {

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
        name = "";
    }

    Dog() {
        dogBreed = "";
        time = "";
        name = "";
    }

}
