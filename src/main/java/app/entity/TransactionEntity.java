package app.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "transactions")
public class TransactionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "data")
    private LocalDate date;

    @Column(name = "completed")
    private String completed;

    @Column(name = "car_id")
    private long carId;

    @Column(name="client_id")
    private long clientId;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "TransactionEntity{" +
                "id=" + id +
                ", date=" + date +
                ", completed='" + completed + '\'' +
                ", carId=" + carId +
                ", clientId=" + clientId +
                '}';
    }
}

