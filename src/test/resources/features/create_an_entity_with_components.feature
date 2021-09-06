Feature: I can create an Entity with Components

  Adding some components to an Entity.

  Scenario: I can create a class from entity with components
    Given a new Entity named "car"
    When I add a "PhysicComponent" to the "car" Entity
    Then a new component named "physic" exists in the "car" entity

  Scenario: I can remove a Component from an Entity
    Given a new Entity named "car"
    And I add a "PhysicComponent" to the "car" Entity
    And I add a "PositionComponent" to the "car" Entity
    When I remove the "physic" component from the "car" entity
    Then the "car" Entity has only 1 component(s)

  Scenario: Adding 3 components to an Entity
    Given a new Entity named "car"
    And I add a "PhysicComponent" to the "car" Entity
    And I add a "PositionComponent" to the "car" Entity
    And I add a "RenderComponent" to the "car" Entity
    Then the "car" Entity has only 3 component(s)

  Scenario: I can not add multiple times the same component to an Entity
    Given a new Entity named "car"
    And I add a "PhysicComponent" to the "car" Entity
    And I add a "PhysicComponent" to the "car" Entity
    And I add a "PhysicComponent" to the "car" Entity
    And I add a "PhysicComponent" to the "car" Entity
    Then the "car" Entity has only 1 component(s)
