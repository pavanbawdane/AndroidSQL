package androidsql.pavan.com.androidsql;

/**
 * Created by Pavan on 01/11/2015.
 */
public class Events {
    String emailId, eventName, genre, address;
    int phoneNumber;

    public Events(String emailId, String eventName, String genre, String address, int phoneNumber) {
        this.emailId = emailId;
        this.eventName = eventName;
        this.genre = genre;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
