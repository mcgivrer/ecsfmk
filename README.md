# ecpfmk

Entity Component Processor framework


## Introduction

This is a free implementation for an ECS sample.

It's an adaptation of some common framework in GameDev to manage in a more reliable 
way some entities, their living attributes and the System running all those stuff.

This is a Static implementation.

A dynamic implementation (meaning usage of component containers into Entity, will 
come soon).


## Sample code

Checkout the tag 1.0.0.0 will provide a simple implmentation of a common java object 
with some attributes and a main process using it. 

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

#### The Systems

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

the System to render things on screen:

```Java
class RenderSystem implements System{
  public void update(Application app, float dt){
    // see details in code :) ...
  }
}
```




[Frédéric Delorme](frederic.delorme@ge.com "contact the author").
