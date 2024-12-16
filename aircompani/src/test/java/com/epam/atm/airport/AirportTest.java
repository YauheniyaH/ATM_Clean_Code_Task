package com.epam.atm.airport;

import org.testng.annotations.BeforeMethod;
import com.epam.atm.airport.planes.ExperimentalPlane;
import com.epam.atm.airport.planes.enums.security_level.ClassificationLevel;
import com.epam.atm.airport.planes.enums.types.ExperimentalType;
import com.epam.atm.airport.planes.enums.types.MilitaryType;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.epam.atm.airport.planes.MilitaryPlane;
import com.epam.atm.airport.planes.PassengerPlane;
import com.epam.atm.airport.planes.AbstractPlane;

import java.util.Arrays;
import java.util.List;

import static com.epam.atm.airport.planes.enums.types.MilitaryType.BOMBER;
import static com.epam.atm.airport.planes.enums.types.MilitaryType.TRANSPORT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AirportTest {
    private static final List<AbstractPlane> planes = Arrays.asList(
            //replace list with json file with test data
            new PassengerPlane("Boeing-737", 900, 12000, 60500, 164),
            new PassengerPlane("Boeing-737-800", 940, 12300, 63870, 192),
            new PassengerPlane("Boeing-747", 980, 16100, 70500, 242),
            new PassengerPlane("Airbus A320", 930, 11800, 65500, 188),
            new PassengerPlane("Airbus A330", 990, 14800, 80500, 222),
            new PassengerPlane("Embraer 190", 870, 8100, 30800, 64),
            new PassengerPlane("Sukhoi Superjet 100", 870, 11500, 50500, 140),
            new PassengerPlane("Bombardier CS300", 920, 11000, 60700, 196),
            new MilitaryPlane("B-1B Lancer", 1050, 21000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("B-2 Spirit", 1030, 22000, 70000, MilitaryType.BOMBER),
            new MilitaryPlane("B-52 Stratofortress", 1000, 20000, 80000, MilitaryType.BOMBER),
            new MilitaryPlane("F-15", 1500, 12000, 10000, MilitaryType.FIGHTER),
            new MilitaryPlane("F-22", 1550, 13000, 11000, MilitaryType.FIGHTER),
            new MilitaryPlane("C-130 Hercules", 650, 5000, 110000, TRANSPORT),
            new ExperimentalPlane("Bell X-14", 277, 482, 500, ExperimentalType.HIGH_ALTITUDE, ClassificationLevel.SECRET),
            new ExperimentalPlane("Ryan X-13 Vertijet", 560, 307, 500, ExperimentalType.VTOL, ClassificationLevel.TOP_SECRET)
    );

    private Airport airport;
    @BeforeMethod(alwaysRun = true)
    public void airportSetup()  {
         airport = new Airport(planes);
    }

    @Test (description = "Check if there is Transport type of plane in Military planes list")
    public void testGetTransportMilitaryPlanes() {
        List<MilitaryPlane> transportMilitaryPlanes = airport.getMilitaryPlanesByType(TRANSPORT);
        boolean flag = false;
        for (MilitaryPlane militaryPlane : transportMilitaryPlanes) {
            if ((militaryPlane.getType() == TRANSPORT)) {
                flag = true;
                break;
            }
        }
        assertTrue(flag);
    }

    @Test
    public void testGetPassengerPlaneWithMaxCapacity() {
        PassengerPlane planeMaxPassengerCapacity = new PassengerPlane("Boeing-747", 980, 16100, 70500, 1000000);
        planes.add(planeMaxPassengerCapacity);
        PassengerPlane expectedPlaneMaxPassengersCapacity = airport.findMaxPassengersCapacityPlane();
        assertEquals(planeMaxPassengerCapacity, expectedPlaneMaxPassengersCapacity);
    }

    @Test
    public void testSortingPlanesByMaxLoadCapacity() {
        //вытащить из самотелов все capacity и отсортировать
        // сравнить значения капасити из отсортированных самолетов со списком чисто капасити ( отсорт)
        airport.sortByMaxLoadCapacity();
        List<? extends AbstractPlane> planesSortedByMaxLoadCapacity = airport.getPlanes();

        boolean nextPlaneMaxLoadCapacityIsHigherThanCurrent = true;
        for (int i = 0; i < planesSortedByMaxLoadCapacity.size() - 1; i++) {
            AbstractPlane currentPlane = planesSortedByMaxLoadCapacity.get(i);
            AbstractPlane nextPlane = planesSortedByMaxLoadCapacity.get(i + 1);
            if (currentPlane.getMaxLoadCapacity() > nextPlane.getMaxLoadCapacity()) {
                nextPlaneMaxLoadCapacityIsHigherThanCurrent = false;
                break;
            }
        }
        assertTrue(nextPlaneMaxLoadCapacityIsHigherThanCurrent);
    }

    @Test
    public void testHasAtLeastOneBomberInMilitaryPlanes() {
        List<MilitaryPlane> bomberMilitaryPlanes = airport.getMilitaryPlanesByType(BOMBER);
        boolean militaryListHasBombers = false;
        for (MilitaryPlane militaryPlane : bomberMilitaryPlanes) {
            if ((militaryPlane.getType() == MilitaryType.BOMBER)) {
                militaryListHasBombers = true;
            }
            else {
                Assert.fail("Test failed! List of military planes does not have bombers type of planes.");
            }
        }
    }

    @Test
    public void testClassificationLevelHigherThanUnclassified(){
        List<ExperimentalPlane> experimentalPlanes = airport.getExperimentalPlanes();
        boolean hasUnclassifiedPlanes = false;
        for(ExperimentalPlane experimentalPlane : experimentalPlanes){
            if(experimentalPlane.getClassificationLevel() == ClassificationLevel.UNCLASSIFIED){
                hasUnclassifiedPlanes = true;
                break;
            }
        }
        Assert.assertFalse(hasUnclassifiedPlanes);
    }
}
