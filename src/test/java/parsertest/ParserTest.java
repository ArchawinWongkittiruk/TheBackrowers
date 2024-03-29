package parsertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import parser.Parser;
import datastructures.Node;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ParserTest {

    private Parser parser;
    private List<String> filteredData;
    private List<String> rawData;

    @BeforeEach
    public void setup() throws IOException{
        parser = new Parser(Paths.get("src/test/resources/testData.txt"));
        filteredData = Arrays.asList("Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 );",
                "Room HenRaph_04_476_264 = new Room( 47.614590f , 26.463207f , GuysHeights.HenRaph_04 , \"HR 4.2\" );",
                "Toilet HenRaph_04_374_347 = new Toilet( 37.426018f , 34.716671f , GuysHeights.HenRaph_04 , ToiletType.Female );",
                "FloorChanger HenRaph_04_418_357 = new FloorChanger( 41.841064f , 35.724461f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );",
                "FloorChanger HenRaph_04_419_365 = new FloorChanger( 41.976913f , 36.541119f , GuysHeights.HenRaph_04 , FloorChangerType.Lift ); // LAJ2");
        rawData = Arrays.asList("// Data of Mappin Technologies LTD 2019",
                "// Thu Aug  1 11:48:39 2019", "", "",
                "Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 );",
                "Room HenRaph_04_476_264 = new Room( 47.614590f , 26.463207f , GuysHeights.HenRaph_04 , \"HR 4.2\" );",
                "Toilet HenRaph_04_374_347 = new Toilet( 37.426018f , 34.716671f , GuysHeights.HenRaph_04 , ToiletType.Female );",
                "FloorChanger HenRaph_04_418_357 = new FloorChanger( 41.841064f , 35.724461f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs );",
                "FloorChanger HenRaph_04_419_365 = new FloorChanger( 41.976913f , 36.541119f , GuysHeights.HenRaph_04 , FloorChangerType.Lift ); // LAJ2",
                "", "HenRaph_04_493_264.addAllNeighbours( new List<Node>{ HenRaph_04_476_264 , HenRaph_04_493_276 , HenRaph_04_491_243 } );",
                "HenRaph_04_439_365.addAllNeighbours( new List<Node>{ HenRaph_04_439_357 , HenRaph_04_491_365 , HenRaph_04_419_365 , HenRaph_04_442_369 } );",
                "HenRaph_04_621_365.addAllNeighbours( new List<Node>{ HenRaph_04_581_365 , HenRaph_04_621_354 } );", "",
                "====== NODE LISTS =======", "", "// Nodes:",
                " , HenRaph_04_493_264 , HenRaph_04_493_276 , HenRaph_04_493_346 , HenRaph_04_438_346 , HenRaph_04_439_357 , HenRaph_04_439_365");
    }

    @Test
    public void emptyPathTest() throws IOException {
        assertThrows(IOException.class, () ->{
            Parser parser = new Parser(Paths.get("/"));
            parser.getLines(); 
        });
    }

    @Test
    public void getLinesTest() throws IOException {
        List<String> data = parser.getAllLines();
        assertEquals(rawData, data);
    }

    @Test
    public void getFilteredLinesTest() throws IOException {
        List<String> data = parser.getLines();
        assertEquals(filteredData, data);
    }

    @Test
    public void getNeighboursLinesTest() throws IOException {
        List<String> data = parser.getNeighboursLines();
        List<String> neighboursData = Arrays.asList(
            "HenRaph_04_493_264.addAllNeighbours( new List<Node>{ HenRaph_04_476_264 , HenRaph_04_493_276 , HenRaph_04_491_243 } );",
            "HenRaph_04_439_365.addAllNeighbours( new List<Node>{ HenRaph_04_439_357 , HenRaph_04_491_365 , HenRaph_04_419_365 , HenRaph_04_442_369 } );",
            "HenRaph_04_621_365.addAllNeighbours( new List<Node>{ HenRaph_04_581_365 , HenRaph_04_621_354 } );"
            );
        assertEquals(neighboursData, data);
    }

    @Test
    public void beginDataLinesTest() throws IOException {
        Parser fullInputParser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        assertEquals(fullInputParser.beginOfDataLines(),4);
    }

    @Test
    public void endDataLinesTest() throws IOException {
        Parser fullInputParser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        assertEquals(fullInputParser.endOfDataLines(),25);
    }

    @Test
    public void beginNeighbourLinesTest() throws IOException {
        Parser fullInputParser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        assertEquals(fullInputParser.beginOfNeighbourLines(),27);
    }

    @Test
    public void endNeighbourLinesTest() throws IOException {
        Parser fullInputParser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        assertEquals(fullInputParser.endOfNeighbourLines(),48);
    }
    
    @Test
    public void beginNodeListLinesTest() throws IOException {
        Parser fullInputParser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        assertEquals(fullInputParser.beginOfNodeListLines(),51);
    }


}
