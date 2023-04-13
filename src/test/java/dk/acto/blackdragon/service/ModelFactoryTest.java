package dk.acto.blackdragon.service;

import dk.acto.blackdragon.model.Model;
import io.vavr.collection.List;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import com.opencsv.CSVReader;
import java.io.StringReader;

public class ModelFactoryTest {

    @Test(dependsOnGroups = "fetch", groups = "parse")
    public void testParse(ITestContext context) {

        String data = String.valueOf(context.getAttribute("data"));

        ModelFactory<Model> subject = new ModelFactory<Model>() {
            @Override
            public List<Model> parse(String string) {
				List<Model> modelList = List.of(string.split("\n"))
					.tail()
					.map(row -> {
						String[] values = row.split(", ");
						return Model.builder()
							.id(Long.parseLong(values[0]))
							.weight(Double.parseDouble(values[1]))
							.cost(Integer.parseInt(values[2]))
							.inventory(Long.parseLong(values[3]))
							.build();
					});
				return modelList;
			}
        };

        List<Model> result = subject.parse(data);
        assertNotNull(result);
        assertEquals(result.length(), 13);
        context.setAttribute("models", result);
    }
}
