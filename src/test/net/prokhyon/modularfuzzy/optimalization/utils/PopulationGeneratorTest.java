package net.prokhyon.modularfuzzy.optimalization.utils;

import net.prokhyon.modularfuzzy.common.utils.Tuple2;
import net.prokhyon.modularfuzzy.optimalization.ChromosomeElement;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PopulationGeneratorTest {

    static List<Tuple2<TestChromosomeElement, List<Double>>> candidates = new ArrayList<>();
    static TestChromosomeElement testChromosomeElement1;
    static TestChromosomeElement testChromosomeElement2;
    static TestChromosomeElement testChromosomeElement3;
    static TestChromosomeElement testChromosomeElement4;
    static TestChromosomeElement testChromosomeElement5;

    static double simpleCostcalculator(List<Double> cost){

        return cost.stream().mapToDouble(x->x).sum();
    }

    static class TestChromosomeElement implements ChromosomeElement {

        private final String name;
        private final List<Double> costs;

        public TestChromosomeElement(String name, List<Double> costs){

            this.name = name;
            this.costs = costs;
        }

        public String getName() {
            return name;
        }

        public List<Double> getCosts() {
            return costs;
        }
    }


    @BeforeClass
    public static void runOnceBeforeClass(){

        final List<Double> doubles1 = Arrays.asList(new Double(0.1), new Double(0.2), new Double(0.3)); // simpleCostcalculator(...) = 0.6
        final List<Double> doubles2 = Arrays.asList(new Double(1.1), new Double(2.2), new Double(3.3)); // simpleCostcalculator(...) = 6.6
        final List<Double> doubles3 = Arrays.asList(new Double(1.0), new Double(2.3), new Double(3.0)); // simpleCostcalculator(...) = 6.3
        final List<Double> doubles4 = Arrays.asList(new Double(4.1), new Double(2.9), new Double(3.3)); // simpleCostcalculator(...) = 10.3
        final List<Double> doubles5 = Arrays.asList(new Double(5.0), new Double(3.3), new Double(0.1)); // simpleCostcalculator(...) = 8.4

        List<Double> d1list = new ArrayList<>();
        List<Double> d2list = new ArrayList<>();
        List<Double> d3list = new ArrayList<>();
        List<Double> d4list = new ArrayList<>();
        List<Double> d5list = new ArrayList<>();

        d1list.addAll(doubles1);
        d2list.addAll(doubles2);
        d3list.addAll(doubles3);
        d4list.addAll(doubles4);
        d5list.addAll(doubles5);

        testChromosomeElement1 = new TestChromosomeElement("d1", doubles1);
        testChromosomeElement2 = new TestChromosomeElement("d2", doubles2);
        testChromosomeElement3 = new TestChromosomeElement("d3", doubles3);
        testChromosomeElement4 = new TestChromosomeElement("d4", doubles4);
        testChromosomeElement5 = new TestChromosomeElement("d5", doubles5);

        candidates.add(new Tuple2<>(testChromosomeElement1, doubles1));
        candidates.add(new Tuple2<>(testChromosomeElement2, doubles2));
        candidates.add(new Tuple2<>(testChromosomeElement3, doubles3));
        candidates.add(new Tuple2<>(testChromosomeElement4, doubles4));
        candidates.add(new Tuple2<>(testChromosomeElement5, doubles5));
    }

    @Test
    public void testSelectByEvaluatedFitnessPosition_AscendingOrder() throws Exception {

        // ascending order: 1,3,2,5,4

        final Tuple2<TestChromosomeElement, Double> testChromosomeElementDoubleTuple1
                = PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 0, Order.ASCENDING);

        final Tuple2<TestChromosomeElement, Double> testChromosomeElementDoubleTuple2
                = PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 1, Order.ASCENDING);

        final Tuple2<TestChromosomeElement, Double> testChromosomeElementDoubleTuple3
                = PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 2, Order.ASCENDING);

        final Tuple2<TestChromosomeElement, Double> testChromosomeElementDoubleTuple4
                = PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 3, Order.ASCENDING);

        final Tuple2<TestChromosomeElement, Double> testChromosomeElementDoubleTuple5
                = PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 4, Order.ASCENDING);

        final TestChromosomeElement queriedChromosome1 = testChromosomeElementDoubleTuple1._1;
        final TestChromosomeElement queriedChromosome2 = testChromosomeElementDoubleTuple2._1;
        final TestChromosomeElement queriedChromosome3 = testChromosomeElementDoubleTuple3._1;
        final TestChromosomeElement queriedChromosome4 = testChromosomeElementDoubleTuple4._1;
        final TestChromosomeElement queriedChromosome5 = testChromosomeElementDoubleTuple5._1;

        Assert.assertEquals(simpleCostcalculator(testChromosomeElement1.getCosts()), simpleCostcalculator(queriedChromosome1.getCosts()), Double.MIN_VALUE);
        Assert.assertEquals(simpleCostcalculator(testChromosomeElement3.getCosts()), simpleCostcalculator(queriedChromosome2.getCosts()), Double.MIN_VALUE);
        Assert.assertEquals(simpleCostcalculator(testChromosomeElement2.getCosts()), simpleCostcalculator(queriedChromosome3.getCosts()), Double.MIN_VALUE);
        Assert.assertEquals(simpleCostcalculator(testChromosomeElement5.getCosts()), simpleCostcalculator(queriedChromosome4.getCosts()), Double.MIN_VALUE);
        Assert.assertEquals(simpleCostcalculator(testChromosomeElement4.getCosts()), simpleCostcalculator(queriedChromosome5.getCosts()), Double.MIN_VALUE);

        Assert.assertEquals(testChromosomeElement1, PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 0, Order.ASCENDING)._1);
        Assert.assertEquals(testChromosomeElement3, PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 1, Order.ASCENDING)._1);
        Assert.assertEquals(testChromosomeElement2, PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 2, Order.ASCENDING)._1);
        Assert.assertEquals(testChromosomeElement5, PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 3, Order.ASCENDING)._1);
        Assert.assertEquals(testChromosomeElement4, PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 4, Order.ASCENDING)._1);

    }

    @Test
    public void testSelectByEvaluatedFitnessPosition_DescendingOrder() throws Exception {

        // ascending order: 4,5,2,3,1

        final Tuple2<TestChromosomeElement, Double> testChromosomeElementDoubleTuple1
                = PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 0, Order.DESCENDING);

        final Tuple2<TestChromosomeElement, Double> testChromosomeElementDoubleTuple2
                = PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 1, Order.DESCENDING);

        final Tuple2<TestChromosomeElement, Double> testChromosomeElementDoubleTuple3
                = PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 2, Order.DESCENDING);

        final Tuple2<TestChromosomeElement, Double> testChromosomeElementDoubleTuple4
                = PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 3, Order.DESCENDING);

        final Tuple2<TestChromosomeElement, Double> testChromosomeElementDoubleTuple5
                = PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 4, Order.DESCENDING);

        final TestChromosomeElement queriedChromosome1 = testChromosomeElementDoubleTuple1._1;
        final TestChromosomeElement queriedChromosome2 = testChromosomeElementDoubleTuple2._1;
        final TestChromosomeElement queriedChromosome3 = testChromosomeElementDoubleTuple3._1;
        final TestChromosomeElement queriedChromosome4 = testChromosomeElementDoubleTuple4._1;
        final TestChromosomeElement queriedChromosome5 = testChromosomeElementDoubleTuple5._1;

        Assert.assertEquals(simpleCostcalculator(testChromosomeElement4.getCosts()), simpleCostcalculator(queriedChromosome1.getCosts()), Double.MIN_VALUE);
        Assert.assertEquals(simpleCostcalculator(testChromosomeElement5.getCosts()), simpleCostcalculator(queriedChromosome2.getCosts()), Double.MIN_VALUE);
        Assert.assertEquals(simpleCostcalculator(testChromosomeElement2.getCosts()), simpleCostcalculator(queriedChromosome3.getCosts()), Double.MIN_VALUE);
        Assert.assertEquals(simpleCostcalculator(testChromosomeElement3.getCosts()), simpleCostcalculator(queriedChromosome4.getCosts()), Double.MIN_VALUE);
        Assert.assertEquals(simpleCostcalculator(testChromosomeElement1.getCosts()), simpleCostcalculator(queriedChromosome5.getCosts()), Double.MIN_VALUE);

        Assert.assertEquals(testChromosomeElement4, PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 0, Order.DESCENDING)._1);
        Assert.assertEquals(testChromosomeElement5, PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 1, Order.DESCENDING)._1);
        Assert.assertEquals(testChromosomeElement2, PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 2, Order.DESCENDING)._1);
        Assert.assertEquals(testChromosomeElement3, PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 3, Order.DESCENDING)._1);
        Assert.assertEquals(testChromosomeElement1, PopulationGenerator.selectByEvaluatedFitnessPosition(PopulationGeneratorTest::simpleCostcalculator, candidates, 4, Order.DESCENDING)._1);

    }

}