package de.vogella.rcp.example.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class ViewPart1 extends ViewPart {

	public ViewPart1() {
	}

	@Override
	public void createPartControl(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setText("Hello");
	}

	@Override
	public void setFocus() {

	}

}
