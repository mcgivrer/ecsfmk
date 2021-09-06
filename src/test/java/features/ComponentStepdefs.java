package features;

import fr.mcgivrer.prototype.ecsfmk.components.PhysicComponent;
import fr.mcgivrer.prototype.ecsfmk.components.PositionComponent;
import fr.mcgivrer.prototype.ecsfmk.components.RenderComponent;
import fr.mcgivrer.prototype.ecsfmk.entities.Entity;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ComponentStepdefs {

    Map<String, Entity> entities = new HashMap<>();

    @Given("a new Entity named {string}")
    public void aNewEntityNamedString(String entityName) {
        entities.put(entityName, new Entity(entityName));
    }

    @When("I add a {string} to the {string} Entity")
    public void iAddAToTheEntity(String componentName, String entityName) {
        Entity e = entities.get(entityName);
        switch (componentName) {
            case "PhysicComponent":
                e.add(new PhysicComponent());
                break;
            case "PositionComponent":
                e.add(new PositionComponent());
                break;
            case "RenderComponent":
                e.add(new RenderComponent());
                break;
        }
    }

    @Then("a new component named {string} exists in the {string} entity")
    public void aNewComponentNamedExistsInTheCarEntity(String componentName, String entityName) {
        Entity e = entities.get(entityName);
        assertTrue("the Component " + componentName + " does not exists", e.getComponent(componentName).isPresent());
    }

    @When("I remove the {string} component from the {string} entity")
    public void iRemoveTheComponentFromTheEntity(String componentName, String entityName) {
        Entity e = entities.get(entityName);
        e.removeComponent(componentName);

    }

    @Then("the {string} Entity has only {int} component\\(s)")
    public void theEntityHasOnlyIntComponent(String entityName, int nbComponents) {
        Entity e = entities.get(entityName);
        assertEquals("The entity " + entityName + " does not have the requested number of components " + nbComponents, nbComponents, e.getComponents().size());

    }

}
