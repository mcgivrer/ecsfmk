package fr.mcgivrer.prototype.ecsfmk.systems;

import fr.mcgivrer.prototype.ecsfmk.Application;
import fr.mcgivrer.prototype.ecsfmk.systems.api.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SystemManager {

    private static SystemManager instance;

    private final Application application;

    Map<String, System> systems = new HashMap<>();
    Map<Class<? extends Action>, List<System>> allSystems = new HashMap<>();

    public SystemManager(Application app) {
        this.application = app;
    }

    public static SystemManager get(Application app) {
        if (SystemManager.instance == null) {
            SystemManager.instance = new SystemManager(app);
        }
        return SystemManager.instance;
    }

    public void add(System system) {
        assert system == null : "Can not add a null service";
        assert systems.containsKey(system.getName()) : "System " + system.getName() + " already declared";
        systems.put(system.getName(), system);
        addSystemToAction(system);
    }

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

    public System get(String name) {
        assert name == null : "getting a null named service is not possible";
        assert name.equals("") : "the name of a serv ice can not be empty";
        return systems.get(name);
    }

    /**
     * Retrieve all services implementing the action interface.
     *
     * @param action the action to retrieve system for.
     * @return a List of services implementing the `action`.
     */
    public List<? extends System> findAction(Class<? extends Action> action) {
        List<System> list;
        list = allSystems.get(action);
        return list;
    }
}
