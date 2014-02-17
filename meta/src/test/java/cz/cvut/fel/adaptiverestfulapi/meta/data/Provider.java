
package cz.cvut.fel.adaptiverestfulapi.meta.data;

import org.testng.annotations.DataProvider;


public class Provider {

    @DataProvider(name = "packages")
    public static Object[][] packages() {
        return new Object[][] {
                { "cz.cvut.fel.adaptiverestfulapi.meta.data.simple", Object.class },
                { "cz.cvut.fel.adaptiverestfulapi.meta.data.interfaces", Object.class }
        };
    }

}
