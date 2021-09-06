package features;

import fr.mcgivrer.prototype.ecsfmk.components.PhysicComponent;
import fr.mcgivrer.prototype.ecsfmk.entities.Entity;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertTrue;

public class ComponentStepdefs {

    Entity car;

    @Given("a new Entity named {string}")
    public void aNewCarEntity(String entityName) {
        car = new Entity(entityName);
    }

    @When("I add a {string} to the {string} entity")
    public void iAddAToTheEntity(String componentName, String entityName) {
        switch (componentName) {
            case "PhysicComponent":
                car.add(new PhysicComponent());
        }
    }

    @Then("a new component named {string} exists in the Car entity")
    public void aNewComponentNamedExistsInTheCarEntity(String componentName) {
        assertTrue("the Component " + componentName + " does not exists", car.getComponent(componentName).isPresent());
    }
}
