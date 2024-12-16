package com.epam.atm.airport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.epam.atm.airport.planes.ExperimentalPlane;
import com.epam.atm.airport.planes.enums.types.MilitaryType;
import com.epam.atm.airport.planes.MilitaryPlane;
import com.epam.atm.airport.planes.PassengerPlane;
import com.epam.atm.airport.planes.AbstractPlane;

import java.util.*;

import static java.util.Comparator.*;

public class Airport {
    private final List<? extends AbstractPlane> planes;

    private static final Logger logger = LogManager.getRootLogger();


    // rewrite getPassengerPlanes and getMilitaryPlanes to one method getPlanesByType (or use common method inside both methods)
    public List<PassengerPlane> getPassengerPlanes() {
        List<PassengerPlane> passengerPlanes = new ArrayList<>();//wild cards can be used here
        for (AbstractPlane plane : planes) {
            if (plane instanceof PassengerPlane) {
                passengerPlanes.add((PassengerPlane) plane);
                logger.info("Passenger plane was found and added to the list");
            }
        }
        return passengerPlanes;
    }

    public List<MilitaryPlane> getMilitaryPlanes() {
        List<MilitaryPlane> militaryPlanes = new ArrayList<>();
        for (AbstractPlane plane : planes) {
            if (plane instanceof MilitaryPlane) {
                militaryPlanes.add((MilitaryPlane) plane);
                logger.info("Military plane was found and added to the list"); // add plane name +
            }
        }
        return militaryPlanes;
    }

    public List<ExperimentalPlane> getExperimentalPlanes() {
        List<ExperimentalPlane> experimentalPlanes = new ArrayList<>();
        for (AbstractPlane plane : planes) {
            if (plane instanceof ExperimentalPlane) {
                experimentalPlanes.add((ExperimentalPlane) plane);
                //add logger
            }
        }
        return experimentalPlanes;
    }

    public PassengerPlane findMaxPassengersCapacityPlane() {
        List<PassengerPlane> passengerPlanes = getPassengerPlanes();
        PassengerPlane planeWithMaxCapacity = passengerPlanes.get(0);
        for (int i = 0; i < passengerPlanes.size(); i++) { //update with list iteration
            if (passengerPlanes.get(i).getPassengersCapacity() > planeWithMaxCapacity.getPassengersCapacity()) {
                planeWithMaxCapacity = passengerPlanes.get(i);
            }
        }
        return planeWithMaxCapacity;
    }

    public List<MilitaryPlane> getMilitaryPlanesByType(MilitaryType type) {
        List<MilitaryPlane> militaryPlanes = getMilitaryPlanes();
        List<MilitaryPlane> filteredPlanes = new ArrayList<>(); // can be replaced by streams
        for (int i = 0; i < militaryPlanes.size(); i++) {
            MilitaryPlane plane = militaryPlanes.get(i);
            if (plane.getType() == type) {
                filteredPlanes.add(plane);
                //debug add logger
            }
        }
        // info logger: return final list of planes
        // add error message if no planes found
        return filteredPlanes;
    }


    public Airport sortByMaxDistance() {
        planes.sort(comparingInt(AbstractPlane::getMaxFlightDistance));
        return this;
    }

    /**
     * Sorts by max speed
     *
     * @return airport.Airport
     */
    public Airport sortByMaxSpeed() {
        planes.sort(comparingInt(AbstractPlane::getMaxSpeed));
        return this;
    }

    public void sortByMaxLoadCapacity() {
        planes.sort(comparingInt(AbstractPlane::getMaxLoadCapacity));
    }

    public List<? extends AbstractPlane> getPlanes() {
        return planes;
    }

    @Override
    public String toString() {
        return "airport.Airport{" +
                "Planes=" + planes.toString() +
                '}';
    }

    public Airport(List<? extends AbstractPlane> planes) {
        this.planes = planes;
    }

}
