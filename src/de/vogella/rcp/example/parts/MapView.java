package de.vogella.rcp.example.parts;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;

public class MapView extends ViewPart {

	public MapView() {
	}

	public void createPartControl(Composite parent) {
		Bundle bundle = Platform.getBundle("de.vogella.rcp.example");
		URL fileURL = bundle.getEntry("html/map.html");
		URI uri;
		File file = null;
		try {
			
			fileURL  = fileURL.toURI().toURL();
			file = new File(FileLocator.resolve(fileURL).toURI());

		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		final Composite mapComposite = new Composite((Composite) parent,
				SWT.NONE);
		mapComposite.setLayout(new GridLayout(1, false));
		Composite buttonBar = new Composite(mapComposite, SWT.NONE);
		buttonBar.setLayout(new GridLayout(2, true));

		final Browser browser = new Browser(mapComposite, SWT.NONE);

		browser.addControlListener(new ControlListener() {
			public void controlResized(ControlEvent e) {
				// Use Javascript to set the browser width and height
				browser.execute("document.getElementById('map_canvas').style.width= "
						+ (browser.getSize().x - 20) + ";");
				browser.execute("document.getElementById('map_canvas').style.height= "
						+ (browser.getSize().y - 20) + ";");
			}

			public void controlMoved(ControlEvent e) {
			}
		});

		new CustomFunction(browser, "theJavaFunction");

		Button b = new Button(buttonBar, SWT.PUSH);
		b.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		b.setText("Add a Marker ?");
		b.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				browser.evaluate("OnClickMarkerListenerFunction();");
			}
		});

		Button addMarker = new Button(buttonBar, SWT.PUSH);
		addMarker
				.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		addMarker.setText("Add Marker");
		addMarker.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {

				browser.evaluate("createMarker();");

			}
		});

		browser.setUrl(file.toURI().toString());

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		browser.setLayoutData(data);
		mapComposite.setLayoutData(data);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {

	}

	// Called by JavaScript
	class CustomFunction extends BrowserFunction {

		Browser browser = null;

		CustomFunction(Browser browser, String name) {
			super(browser, name);
			this.browser = browser;

			// this.data.browser = browser;
		}

		public Object function(Object[] arguments) {
			double lat = ((Double) arguments[0]).doubleValue();
			double lng = ((Double) arguments[1]).doubleValue();
			browser.execute("document.getElementById('map_canvas').style.width= "
					+ (browser.getSize().x - 20) + ";");
			browser.execute("document.getElementById('map_canvas').style.height= "
					+ (browser.getSize().y - 20) + ";");

			return null;
		}
	}

}
