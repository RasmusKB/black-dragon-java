package dk.acto.blackdragon.service;

import dk.acto.blackdragon.model.AuthorData;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import java.net.URL;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class AuthorDataFactoryTest {

    @Test(groups = "transform")
    public void testCreate(ITestContext context) {

        AuthorDataFactory subject = new AuthorDataFactory() {
            @Override
            public AuthorData create() {
				try {
					AuthorData author = AuthorData.builder()
						.name("Rasmus Bak")
						.linkedInProfile(new URL("https://www.linkedin.com/in/rasmuskb/"))
						.solutionRepository(new URL("https://github.com/RasmusKB/black-dragon-java"))
						.build();
					return author;
				} catch (Exception excep) {
					return null;
				}
            }
        };

        AuthorData result = subject.create();
        assertNotNull(result);
        assertNotNull(result.getLinkedInProfile());
        assertNotNull(result.getSolutionRepository());
        assertTrue(result.getName().length() > 0);
        context.setAttribute("author", result);
    }
}
