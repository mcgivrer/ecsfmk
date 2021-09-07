### The game object.

```Java
class Car {
    Vector2D position;
    Vector2D size;
    Vector2D velocity;
    Vector2D acceleration;

    float resistance;
    float elasticity;

    public void update(float dt) {
    }

    public void render(Graphics2D g) {
    }
}
```

![illustrations/car-class-diagram.png](illustrations/car-class-diagram.png "Car UML class diagram")

### The Refactored Car entity/component oriented.

#### The car entity

This is the basic entity to be inherited by business entity.

```Java
class Entity {
    String name;

    public Entity(String name) {
    }

    public String getName() {
    }

}
```

The implemented Car entity

```Java
class Car extends Entity {
    PositionComponent pos;
    PhysicComponent physic;
    RenderComponent render;
}
```

#### The Components

Position component containing position and size information.

```Java
class PositionComponent implements Component {
    Vector2D position;
    Vector2D size;
    Vector2D velocity;
    Vector2D acceleration;

    float resistance;
    float elasticity;
}
```

The physic components containing all physic computation needed data.

```Java
class PositionComponent implements Component {
    Vector2D position;
    Vector2D size;
    Vector2D velocity;
    Vector2D acceleration;

    float resistance;
    float elasticity;
}
```

![illustrations/car-entity-components-class-diagram.png](illustrations/car-entity-components-class-diagram.png "Car UML class diagram")

### The Systems

And the the needed Systems :

What is a System ?

```Java
interface System {
    void update(Application app, float dt);
}
```

And what are the implemented system.

```Java
class CarSystem implements System {
    public void update(Application app, float dt) {
        // see details in code :) ...
    }
}
```

The system to manage input:

```Java
class InputSystem implements System {
    public void update(Application app, float dt) {
        // see details in code :) ...
    }
}
```

The System to render things on screen:

```Java
class RenderSystem implements System {
    public void update(Application app, float dt) {
        // see details in code :) ...
    }
}
```

## Moving to a more framework oriented implementation

Replace components attribute's from `Car` by a `Component` map in the `Entity` class.

```java
class Car extends Entity {
    public Car(String name) {
        add(new PositionCompoent());
        add(new PhysicCompoent());
        add(new RenderCompoent());
    }
}
```

### Upgrading Entity

And now, the `Entity` is mosdified to propose a map of components, and the capability to add component to this one.

```java
class Entity<T> {
    public String name;
    private Map<String, Component> components;

    public Entity<T> add(Component c) {
    }
}
```

And the implemented `System` must be adapted to use `Entity` objects and no more `Car` class.

The `MoveSystem` is now managing a bunch of `Entity` and not only `Car`. It will parse the `Applicaiton#entities` map,
and detect the ones having the right `Components` to move the matching entities.

### One SystemManager to rules'em all

To get the full hand on the implemented System, and free all resources when stopping your application, the best way to
proceed is to implement a System manager. This class will be the indexer for all System your program will have to work
with.

This `SystemManager` will have to propose a clear API to add, get or remove some services.

```java
class SystemManager {
    private Map<String, System> systems;

    public System get(String name) {
    }

    public void remove(String name) {
    }

    public List<System> getAll() {
    }
}
```

And then you can implement the service you need by implementing the System interface.

```java
class MySuperSystem implements System {
    public void update(long dt) {
        // where all happened in my super service.
    }
}
```

### Using the SSM und System

And then add it to the SystemManager:

```java
import fr.mcgivrer.prototype.ecsfmk.systems.SystemManager;

class Game {
    SystemManager sm;

    //...
    public static void main(Strng[] argc) {
        Game g = new Game();
        g.run();
    }

    //..
    public void initialize() {
        // initialize the System Manager
        sm = SystemManager.get(this);
        // Add the needed System
        sm.add(new MySuperSystem(this));
    }

    //...

    public void udpate(long dt) {
        // here is where I need the System !
        MySuperSystem mss = sm.get("superSystem");
        ssm.update(dt);
    }

}
```

## Upgrading SystemManager

To let the Systems implements dedicated processing, we are goinfg to upgrade System and SystemManager.

In our Application, we need to process 3 kind of steps:

- input
- update
- render.

Those steps corresponds to the 3 operations that must be performed by the main application loop.

let's define those actions :

- Input action : this is the action where all key input must be managed
- Update action : all the entities in the application must be updated according to obey their own behaviors.
- Render action : when all the previous operations are done, we need to display things, it the role of this action.

Each Action must be clearly identified. So we are going to create a Contract between the rules of those action and their
natural operations:

- `InputAction` will provide an input method,
- `UpdateAction` interface will provide an update method,
- `RenderAction`will fix the render method.

### the Actions

All those specific operation are actions. to identify any ofe those actions we will implement an Action interface:

```java
interface Action {
}
```

Action is an empty one because it will be the master of all actions.

#### InputAction

the Input action must implement an input management:

```java
interface InputAction extends Action {
    void input(InputHandler ih, float dt);
}
```

#### UpdateAction

the update action will implement an update :

```java
interface UpdateAction extends Action {
    void update(float dt);
}
```

#### RenderAction

And finally, the Render !

```java
interface RenderAction extends Action {
    void render(float dt);
}
```

### XxxxSystem ?

Now we had defined the Actrion, we will be able to define Systems implementing those actions:

#### InputSystem

this specific System will implements the System AND the InputAction interface :

```java
class InputSystem implements System, InputAction {

    //---- From System interface ----
    @Override
    public String getName() {
        return SERVICE_NAME;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    //---- from InputAction ----
    public void input(InputHandler ih, float dt) {
        //...
    }
}
```

You will have the same thing for the other System.

### Adding new Find capability

the SystemManager must be modified in its add and find methods to add with correct sorting and find the right classes
corresponding to the needed actions.

```java
class SystemManager {
    //...
    Map<Class<? extends Action>, List<System>> allSystems = new HashMap<>();

    // adding a System to the list
    private void addSystem(System system) {
        //...
        addSystemToAction(system);
    }

    // adding the System to the corresponding internal action list.
    private void addSystemToAction(System system) {
        for (Class cls : system.getClass().getInterfaces()) {
            if (cls != System.class) {
                if (!allSystems.containsKey(cls)) {
                    allSystems.put(cls, new ArrayList<System>());
                }
                allSystems.get(cls).add(system);
            }
        }
    }

    //...

    // Retrieve the System corresponding to a specific action.
    public List<? extends System> findSystem(Class<? extends Action> action) {
        List<System> list;
        list = allSystems.get(action);
        return list;
    }
}
```

And now you can easily add a System to the SystemManager and retrieve it (we won't detail the static methods to access
the SystemMnagaer API)

```java
import fr.mcgivrer.prototype.ecsfmk.systems.SystemManager;

class Game {

    void update(float dt) {
        SystemManager.find(UpdateAction.class)
                .forEach(ua -> ((UpdateAction) ua).update(dt));
    }
}
```

That's it !