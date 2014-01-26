package org.woehlke.jee6.petclinic.web;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.net.URL;
import java.util.logging.Logger;

import static org.jboss.arquillian.graphene.Graphene.goTo;

/**
 * Created with IntelliJ IDEA.
 * User: tw
 * Date: 21.01.14
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Arquillian.class)
public class PetTypeTest {

    private static Logger log = Logger.getLogger(PetTypeTest.class.getName());

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Deployments.createPetTypeDeployment();
    }

    @Drone
    WebDriver driver;

    @ArquillianResource
    URL deploymentUrl;

    @Page
    private HelloPage helloPage;

    @Page
    private PetTypesPage petTypesPage;

    @Page
    private NewPetTypePage newPetTypePage;

    @Page
    private EditPetTypePage editPetTypePage;


    @Test
    @InSequence(1)
    @RunAsClient
    public void testOpeningHomePage() {
        goTo(HelloPage.class);
        helloPage.assertTitle();
    }

    @Test
    @InSequence(2)
    @RunAsClient
    public void testOpeningPetTypesPage() {
        goTo(PetTypesPage.class);
        petTypesPage.assertPageIsLoaded();
    }


    @Test
    @InSequence(3)
    @RunAsClient
    public void testNewPetTypePage() {
        goTo(PetTypesPage.class);
        petTypesPage.assertPageIsLoaded();
        petTypesPage.clickAddNewPetType();
        newPetTypePage.assertPageIsLoaded();
        newPetTypePage.addNewContent("cat");
        petTypesPage.assertPageIsLoaded();
        petTypesPage.assertNewContentFound("cat");
    }


    @Test
    @InSequence(4)
    @RunAsClient
    public void testEditPetTypePage() {
        goTo(PetTypesPage.class);
        petTypesPage.assertPageIsLoaded();
        petTypesPage.clickEditSpecialty();
        editPetTypePage.assertPageIsLoaded();
        editPetTypePage.editContent("dog");
        petTypesPage.assertPageIsLoaded();
        petTypesPage.assertEditedContentFound("dog");
    }

    @Test
    @InSequence(5)
    @RunAsClient
    public void testDeletePetTypePage() {
        goTo(PetTypesPage.class);
        petTypesPage.assertPageIsLoaded();
        petTypesPage.clickDeleteSpecialty();
        petTypesPage.assertPageIsLoaded();
        petTypesPage.assertDeletedContentNotFound();
    }
}
