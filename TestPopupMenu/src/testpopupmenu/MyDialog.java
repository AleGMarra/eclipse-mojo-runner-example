package testpopupmenu;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;


public class MyDialog extends TitleAreaDialog {

	private Text txtFileName;

	private String fileName;

	public MyDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Credentials file selection");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		GridLayout layout = new GridLayout(3, false);
		GridData gdata= new GridData();
		gdata.horizontalAlignment = GridData.FILL;
		gdata.grabExcessHorizontalSpace = true;
		
		container.setLayoutData(gdata);
		container.setLayout(layout);

		createFileInput(container);
		createBrowser(container);

		return area;
	}

	private void createFileInput(Composite container) {
		Label lbtFirstName = new Label(container, SWT.NONE);
		
		GridData dataLabel = new GridData();
		dataLabel.grabExcessHorizontalSpace = false;
		dataLabel.horizontalAlignment = SWT.BEGINNING;
		dataLabel.horizontalSpan = 1;
		
		lbtFirstName.setLayoutData(dataLabel);
		lbtFirstName.setText("Credentials");

		GridData dataFileName = new GridData();
		dataFileName.grabExcessHorizontalSpace = true;
		dataFileName.horizontalAlignment = SWT.FILL;
		dataFileName.horizontalSpan = 1;

		txtFileName = new Text(container, SWT.BORDER);
		txtFileName.setLayoutData(dataFileName);
	}

	private void createBrowser(Composite container){
		Button button = new Button(container, SWT.PUSH);
		GridData dataButton = new GridData();
		dataButton.grabExcessHorizontalSpace = false;
		dataButton.horizontalAlignment = SWT.RIGHT;
		dataButton.horizontalSpan = 1;

		button.setLayoutData(dataButton);
		button.setText("Browse");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDialog = new FileDialog(Display.getCurrent().getActiveShell());
				fileDialog.setText("Select File");
				
				fileDialog.setFilterExtensions(new String[] { "*.xml" });
				fileDialog.setFilterNames(new String[] { "Textfiles(*.xml)" });
				
				String selected = fileDialog.open();
				System.out.println("INPUTED FILE "+selected);
				
				txtFileName.setText(selected);
			}
		});
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	// save content of the Text fields because they get disposed
	// as soon as the Dialog closes
	private void saveInput() {
		fileName = txtFileName.getText();

	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public String getInput() {
		return fileName;
	}

} 