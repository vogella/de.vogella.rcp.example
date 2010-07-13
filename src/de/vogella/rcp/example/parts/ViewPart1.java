package de.vogella.rcp.example.parts;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

import de.vogella.rcp.example.dnd.MyDragSourceListener;
import de.vogella.rcp.example.dnd.MyDropTargetListener;

public class ViewPart1 extends ViewPart {

	public ViewPart1() {
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 4;
		parent.setLayout(gridLayout);

		String[] imgNames = new String[] { "lars.png", "andre.png",
				"matthias.png", "arne.png" };

		for (int i = 0; i < imgNames.length; i++) {
			try {
				URL url = new URL(
						"platform:/plugin/de.vogella.rcp.example/images/"+imgNames[i]);
				Image image = new Image(Display.getDefault(), url.openConnection()
						.getInputStream());
				Label label = new Label(parent, SWT.NONE);
				label.setImage(image);
				// enable each label to be draggable
				DragSource source = new DragSource(label, DND.DROP_NONE);
				source.setTransfer(new Transfer[] { TextTransfer.getInstance() });
				// add a drag listener
				source.addDragListener(new MyDragSourceListener(parent, source));

				// enable each label to be a drop target
				DropTarget target = new DropTarget(label, DND.DROP_NONE);
				target.setTransfer(new Transfer[] { TextTransfer.getInstance() });
				// add a drop listener
				target.addDropListener(new MyDropTargetListener(parent, target));

			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void setFocus() {

	}

}
