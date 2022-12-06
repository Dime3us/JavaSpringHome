package Homework1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coffees")
public class CoffeeController {
    //private List<Coffee> coffees = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(CoffeeController.class);

    // при старте приложения Spring автоматически инстанциирует CoffeeRepository
    // и добавит ссылку на него в это поле
    @Autowired
    CoffeeRepository coffeeRepository;

    public CoffeeController() {
//        coffees.addAll(
//                List.of(
//                        new Coffee("Espresso"),
//                        new Coffee("Cappuccino"),
//                        new Coffee("Latte"),
//                        new Coffee("Ristretto"),
//                        new Coffee("Macciato")
//                )
//        );

//        coffeeRepository.saveAll(
//                List.of(
//                        new Coffee("Espresso"),
//                        new Coffee("Cappuccino"),
//                        new Coffee("Latte"),
//                        new Coffee("Ristretto"),
//                        new Coffee("Macciato")
//                )
//        );
    }

    //@GetMapping(path = "/coffees") // http://localhost:8080/coffees
    @GetMapping
    public Iterable<Coffee> getCoffees()
    // public List<Coffee> getCoffees()
    {
        // return coffees;
        return coffeeRepository.findAll();
    }

    // напишите метод который вернет конкретное кофе по его идентификатору
    // getCoffeeById
    // GET http://localhost:8080/coffees/aeb62ee3-3111-41ff-b720-a7225b684b75
    //@GetMapping("/coffees/{id}")
    @GetMapping("/{id}")
    public Optional<Coffee> getCoffeeById(
            @PathVariable(name = "id") String id
    ) {
//        return
//                coffees.stream().filter(coffee -> coffee.getId().equals(id))
//                        .findFirst();

//        for(Coffee c : coffees)
//        {
//            if(c.getId().equals(id))
//                return Optional.of(c);
//        }
//        return Optional.empty();

        return coffeeRepository.findById(id);
    }

    // напишите метод который удалит кофе по его идентификатору
    // DELETE http://localhost:8080/coffees/aeb62ee3-3111-41ff-b720-a7225b684b75
    // @DeleteMapping("/coffees/{id}")
    @DeleteMapping("/{id}")
    public void deleteCoffee(
            @PathVariable(name = "id") String id
    ) {
        log.info("delete: " + id);
        // coffees.removeIf(coffee -> coffee.getId().equals(id));
        coffeeRepository.deleteById(id);
    }

    //@PostMapping("/coffees") // создает на сервере новый элемент
    // {"id": "23123", "name"="My coffee" }
    @PostMapping
    public Coffee postCoffee(@RequestBody Coffee coffee) {
        if (coffee != null
                && coffee.getId() != null
                && !coffee.getId().isEmpty()
                && coffee.getName() != null
                && !coffee.getName().isEmpty()
        )
            coffeeRepository.save(coffee);
        //coffees.add(coffee);
        return coffee;
    }


    //@PutMapping("/coffees/{id}") // меняет элемент на сервере
    /*
    @PutMapping("/{id}")
    public Coffee putCoffee(
            @PathVariable(name = "id") String id,
            @RequestBody Coffee coffee
    )
    {
        int coffeeIndex = -1;
        for(Coffee c : coffees)
        {
            if(c.getId().equals(id))
            {
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }
        return (coffeeIndex == -1) ? postCoffee(coffee) : coffee;
    }
     */

    @PutMapping("/{id}")
    public ResponseEntity<Coffee> putCoffee(
            @PathVariable(name = "id") String id,
            @RequestBody Coffee coffee
    ) {
        log.info("PUT " + coffee.getId() + "|" + coffee.getName());
        /*
        int coffeeIndex = -1;
        for (Coffee c : coffees) {
            if (c.getId().equals(id)) {
                coffeeIndex = coffees.indexOf(c);
                coffees.set(coffeeIndex, coffee);
            }
        }
        return (coffeeIndex == -1) ?
                new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED) :
                new ResponseEntity<>(coffee, HttpStatus.OK);

         */

        if (coffeeRepository.existsById(id)) {
            coffeeRepository.save(coffee);
            return new ResponseEntity<>(coffee, HttpStatus.OK);
        } else {
            coffeeRepository.save(coffee);
            return new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED);
        }
    }


    // напишите метод который вернет кофе по его названию
    // если такого нет вернуть null
    // PATCH http://localhost:8080/coffees/Espresso
    @PatchMapping("/{name}")
    List<Coffee> getCoffeeByName(@PathVariable String name) {

        return coffeeRepository.findByNameLike("%" + name + "%");


    }

    @Component
    public class CoffeesInitDB implements CommandLineRunner {

        @Override
        public void run(String... args) throws Exception {
            coffeeRepository.saveAll(List.of(
                    new Coffee("Espresso"),
                    new Coffee("Cappuccino"),
                    new Coffee("Latte"),
                    new Coffee("Ristretto"),
                    new Coffee("Macchiato")
            ));
        }
    }

}
