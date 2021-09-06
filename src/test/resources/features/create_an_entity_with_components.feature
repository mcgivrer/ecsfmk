Feature: A developper can create an Entity with Components

  Adding some components to an Entity.

  Scenario: I can create a class from entity with components
    Given a new Entity named "car"
    When I add a "PhysicComponent" to the "car" entity
    Then a new component named "physic" exists in the Car entity

