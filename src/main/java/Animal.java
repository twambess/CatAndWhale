import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name="animals")
@AllArgsConstructor
public class Animal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="animal_one")
    private String animalOne;

    @Column(name="animal_two")
    private String animalTwo;

    @Column(name="judgment")
    private String judgment;

    public Animal(String animalOne, String animalTwo,String judgment){
        this.animalOne=animalOne;
        this.animalTwo=animalTwo;
        this.judgment=judgment;
    }

}
