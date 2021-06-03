package kambu.rekrutacja;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class RestCall {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Getter private String call;
    @Getter private LocalDateTime dateTime;

    public RestCall() {}

    public RestCall(String call) {
        this.call = call;
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Calls{" +
                "call='" + call + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
