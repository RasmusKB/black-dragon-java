package dk.acto.blackdragon.service;

import dk.acto.blackdragon.model.Model;
import dk.acto.blackdragon.model.Stats;
import io.vavr.collection.List;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ModelTransformerTest {

    @Test(dependsOnGroups = "parse", groups = "transform")
    public void testTransform(ITestContext context) {

        List<Model> models = (List<Model>) context.getAttribute("models");

        ModelTransformer<Model, Stats> subject = new ModelTransformer<Model, Stats>() {
            @Override
            public Stats transform(List<Model> model) {

				Stats retval =  Stats.builder()
					.evenIds(BigInteger.valueOf(model.count(modelIns ->
									modelIns.getId() % 2 == 0))
					)
					.oddIds(BigInteger.valueOf(model.count(modelIns ->
									modelIns.getId() % 2 == 1))
					)
					.meanCost(BigDecimal.valueOf(model.map(modelIns ->
									modelIns.getCost()).sum().longValue() / model.length() / 100)
					)
					.weightedInventory(BigDecimal.valueOf(model.map(modelIns ->
									(modelIns.getWeight()* 100) * modelIns.getInventory()).sum().longValue(), 2)
					)
					.totalInventory(BigInteger.valueOf(model.map(modelIns ->
									modelIns.getInventory()).sum().intValue())
					)
					.build();
                return retval;
             }
        };

        Stats result = subject.transform(models);
        assertNotNull(result);
        assertEquals(result.getEvenIds(), BigInteger.valueOf(6));
        assertEquals(result.getOddIds(), BigInteger.valueOf(7));
        assertEquals(result.getMeanCost(), BigDecimal.valueOf(11));
        assertEquals(result.getWeightedInventory(), BigDecimal.valueOf(2436800, 2));
        assertEquals(result.getTotalInventory(), BigInteger.valueOf(48197));
        context.setAttribute("result", result);
    }
}
