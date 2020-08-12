package test;

import exceptions.InputFileException;
import io.FileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {

    @Test
    void WrongTableName() {
        FileReader fr = new FileReader("test/configTest1.txt");
        Assertions.assertThrows(InputFileException.class, fr::readFromFile);
    }


    @Test
    void IdUnknown() {
        FileReader fr = new FileReader("test/configTest2.txt");
        Assertions.assertThrows(InputFileException.class, fr::readFromFile);
    }

    @Test
    void NoColumn() throws IOException {
        FileReader fr = new FileReader("test/configTest3.txt");
        Assertions.assertThrows(InputFileException.class, fr::readFromFile);
    }

    @Test
    void wrongFormat() {
        FileReader fr = new FileReader("test/configTest4.txt");
        Assertions.assertThrows(InputFileException.class, fr::readFromFile);
    }

    @Test
    void unknownPlaceInPlacesFile() throws IOException {
        FileReader fr = new FileReader("test/configTest5.txt", "test/placesTest.txt");
        Assertions.assertThrows(InputFileException.class, fr::readFromFile);
    }

    @Test
    void correctConfig() throws IOException {
        FileReader fr = new FileReader("test/configTest5.txt");
        fr.readFromFile();
        assertEquals(fr.getGraph().getGraph()[0][1].getTime(), 120);
        assertEquals(fr.getGraph().getObligatoryPlaces().size(), 0);
        assertEquals(fr.getGraph().getAllPlaces().size(), 3);
        assertEquals(fr.getGraph().getPathsWithCost().size(), 2);
    }

    @Test
    void correctConfigWithPlaces() throws IOException {
        FileReader fr = new FileReader("test/configTest5.txt", "test/placesTest2.txt");
        fr.readFromFile();

        assertEquals(fr.getGraph().getObligatoryPlaces().size(), 2);
    }
}