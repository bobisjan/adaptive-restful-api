
package cz.cvut.fel.adaptiverestfulapi.meta.data;

import cz.cvut.fel.adaptiverestfulapi.meta.TestInspectionListener;
import cz.cvut.fel.adaptiverestfulapi.meta.data.base.Item;
import org.testng.annotations.DataProvider;


public class Provider {

    @DataProvider(name = "packages")
    public static Object[][] packages() {
        TestInspectionListener listener = new TestInspectionListener();

        return new Object[][] {
                { "cz.cvut.fel.adaptiverestfulapi.meta.data.simple", Object.class, listener, listener},
                { "cz.cvut.fel.adaptiverestfulapi.meta.data.base", Item.class, listener, listener },
                { "cz.cvut.fel.adaptiverestfulapi.meta.data.abstracts", Object.class, listener, listener },
                { "cz.cvut.fel.adaptiverestfulapi.meta.data.interfaces", Object.class, listener, listener }
        };
    }

}
