package parsertest;
import datastructures.Node;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import parser.NodeCreator;
import parser.Parser;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeCreatorTest {

    private Parser parser;
    private NodeCreator nodeCreator;


    @Test
    public void extractNameTest() throws IOException {
        parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        nodeCreator = new NodeCreator(parser.getLines(), parser.getNeighboursLines());
        String line = "Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 );";
        String expected = "HenRaph_04_493_264";
        String name = nodeCreator.extractName(line);
        assertEquals(expected, name);
    }


    @Test
    public void extractDataTest() throws IOException {
        parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        nodeCreator = new NodeCreator(parser.getLines(),parser.getNeighboursLines());
        String line = "Node HenRaph_04_493_264 = new Node( 49.312683f , 26.463207f , GuysHeights.HenRaph_04 );";
        Pair<Float, Float> expected = new Pair<>(49.312683f,  26.463207f );
        Pair<Float, Float> data = nodeCreator.extractData(line);
        assertEquals(expected, data);
    }
    @Test
    public void extractSpecialTraitTest() throws IOException {
        parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        nodeCreator = new NodeCreator(parser.getLines(),parser.getNeighboursLines());
        String roomLine = "Room HenRaph_04_476_264 = new Room( 47.614590f , 26.463207f , GuysHeights.HenRaph_04 , HR 4.2 );";
        String toiletLine  = "Toilet HenRaph_04_374_347 = new Toilet( 37.426018f , 34.716671f , GuysHeights.HenRaph_04 , ToiletType.Female );";
        String floorCChangerLine ="FloorChanger HenRaph_04_418_357 = new FloorChanger( 41.841064f , 35.724461f , GuysHeights.HenRaph_04 , FloorChangerType.Stairs ); ";

        String expectedRoomTrait = "HR 4.2";
        String expectedToiletTrait = "ToiletType.Female";
        String expectedFloorChangerTrait ="FloorChangerType.Stairs";

        String roomData = nodeCreator.extractSpecialTrait(roomLine);
        String toiletData = nodeCreator.extractSpecialTrait(toiletLine);
        String floorChangerData = nodeCreator.extractSpecialTrait(floorCChangerLine);

        assertEquals(expectedRoomTrait,roomData);
        assertEquals(expectedToiletTrait,toiletData);
        assertEquals(expectedFloorChangerTrait,floorChangerData);
    }
    @Test
    public void extractTypeTest() throws IOException {
        parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        nodeCreator = new NodeCreator(parser.getLines(),parser.getNeighboursLines());

        String testLine="Room HenRaph_04_476_264 = new Room( 47.614590f , 26.463207f , GuysHeights.HenRaph_04 , HR 4.2 );";
       assertEquals("Room",nodeCreator.extractType(testLine));
    }
    @Test
    public void extractFloorTest() throws IOException{
        parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        nodeCreator = new NodeCreator(parser.getLines(),parser.getNeighboursLines());
        String testLine  = "Room HenRaph_10_476_264 = new Room( 47.614590f , 26.463207f , GuysHeights.HenRaph_04 , HR 4.2 );";
        assertEquals(10,nodeCreator.extractFloor(testLine));
    }

    @Test
    public void nodesAsPerInsertionReturnsOrderedNodes() throws IOException{
        parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        nodeCreator = new NodeCreator(parser.getLines(),parser.getNeighboursLines());

        List<String> order = nodeCreator.getNodesAsPerInsertion();
        TreeMap<String, Node> nodes = nodeCreator.createNodes();
        ArrayList<Integer> ids = new ArrayList<>();
        for(String name: order){
            ids.add(nodes.get(name).getId());
        }
        for(int i =0;i<ids.size();i++){
            assertEquals(ids.get(i),i);
        }
    }
    @Test
    public void extractsNeighbourNodeProperly() throws IOException{
        parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        nodeCreator = new NodeCreator(parser.getLines(),parser.getNeighboursLines());

        String testLine  = "Node2.addAllNeighbours( new List<Node>{ Node1 , Node3 } );";
        List<String> neighbours = Collections.singletonList(nodeCreator.extractNodeFromNeighboursLine(testLine));
        assertEquals("Node2",neighbours.get(0));
    }
    @Test
    public void nodesAreCreatedWithUniqueId() throws IOException {
        parser = new Parser(Paths.get("src/test/resources/fullInputData.txt"));
        nodeCreator = new NodeCreator(parser.getLines(),parser.getNeighboursLines());
        TreeMap<String, Node> nodes = nodeCreator.createNodes();
        List<Integer > ids = new ArrayList<>();
        for(String name: nodes.keySet()){
            ids.add(nodes.get(name).getId());
        }
        for(int i =1; i<ids.size() ;i++){
            assertEquals(true, ids.get(i-1)!=ids.get(i));
        }
    }

}
