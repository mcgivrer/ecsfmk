### The game object.

```Java
class Car {
    Vector2D position;
    Vector2D size;
    Vector2D velocity;
    Vector2D acceleration;

    float resistance;
    float elasticity;

    public void update(float dt) { ... }
    public void render(Graphics2D g){ ... }
}
```

![illustrations/car-class-diragram.png](illustrations/car-class-diragram.png 
'Car UML class diagram')

### The Refactored Car entity/component oriented.

#### The car entity

This is the basic entity to be inherited by business entity.
```Java
class Entity {
    String name;

    public Entity(String name){...}
    public String getName(){...}

}
```

The implemented Car entity

```Java
class Car extends Entity{
    PositionComponent pos;
    PhysicComponent physic;
    RenderComponent render;
}
```

#### The Components

Position component containing position and size information.

```Java
class PositionComponent implement Component{
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
class PositionComponent implement Component{
    Vector2D position;
    Vector2D size;
    Vector2D velocity;
    Vector2D acceleration;

    float resistance;
    float elasticity;
}
```

![illustrations/car-entity-components-class-diragram.png](illustrations/car-entity-components-class-diragram.png 
'Car UML class diagram')

### The Systems

And the the needed Systems :

What is a System ?

```Java
interface System {
    public void update(Application app, float dt); 
}
```

And what are the implemented system.

```Java
class CarSystem implements System {
  public void update(Application app, float dt){
    // see details in code :) ...
  }
}
```

The system to manage input:

```Java
class InputSystem implements System{
  public void update(Application app, float dt){
    // see details in code :) ...
  }
}
```

The System to render things on screen:

```Java
class RenderSystem implements System{
  public void update(Application app, float dt){
    // see details in code :) ...
  }
}
```
