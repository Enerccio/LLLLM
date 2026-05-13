package io.github.enerccio.llllm.ui.main;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Configurable;

@Push
@PWA(name = "LLLLM", shortName = "llllm")
@Theme(themeClass = Lumo.class, variant = Lumo.LIGHT)
@BodySize(height = "100vh", width = "100vw")
@JsModule("./styles/shared-styles.js")
@JsModule("@vaadin/vaadin-lumo-styles/presets/compact.js")
@JsModule("./js/canvas.js")
@CssImport(value = "./styles/shared-styles.css", include = "lumo-badge")
@Configurable(preConstruction = true)
public class AppShellConfig implements AppShellConfigurator {

    @Override
    public void configurePage(AppShellSettings settings) {
        settings.setPageTitle("LLLLM");
    }

}
