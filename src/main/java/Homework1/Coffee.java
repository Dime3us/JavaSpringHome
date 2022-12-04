package Homework1;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity // для класса создается таблица в базе данных
//  @Table(name = "cof") если хотим чтобы таблица называлась не как класс
public class Coffee {
    @Id
    private final String id;
    private String name;

    // для JPA нужен конструктор по-умолчанию
    public Coffee()
    {
        this("");
    }

    public Coffee(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Coffee(String name)
    {
        this(
                UUID.randomUUID().toString(),
                name
        );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
