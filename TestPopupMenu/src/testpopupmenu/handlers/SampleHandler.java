package testpopupmenu.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jface.viewers.IStructuredSelection;
import testpopupmenu.MyDialog;

public class SampleHandler extends AbstractHandler {

	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		
		// Get work path
		String workPath = getProjectWorkPath();
		System.out.println("WORK PATH: " + workPath);
		
		// Get file path
		String credPath = getCredsFilePath();
		System.out.println("GOT FROM DIALOG:: " + credPath);
		
		// Run maven command
		runMavenCommandAndPrintInput(workPath, credPath);
		System.out.println("Process endend!");

		return null;
	}

	private void runMavenCommandAndPrintInput(String workPath, String credPath) {
		
		ProcessBuilder procBuilder = new ProcessBuilder("mvn","clean","install","-DskipTests",
														"-Dprinter=print", "-DcredentialsFile="+credPath);
		Process process = null;
		BufferedReader br = null;
		try {
			
			procBuilder.directory(new Path(workPath).toFile());
			process = procBuilder.start();
			
			// Read output from maven command
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			process.waitFor();

			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getCredsFilePath() {
		String credPath;
		MyDialog mdl= new MyDialog(Display.getCurrent().getActiveShell());
		mdl.open();
		credPath = mdl.getInput();
		return credPath;
	}

	private String getProjectWorkPath() {
		
		String workPath = null;
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	    if (window != null)
	    {
	        IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
	        Object firstElement = selection.getFirstElement();
	        if (firstElement instanceof IAdaptable)
	        {
	            IProject project = (IProject)((IAdaptable)firstElement).getAdapter(IProject.class);
	            IPath path = project.getLocation();
	            System.out.println(path);
	            
	            workPath = path.toOSString();
	        }
	    }
		return workPath;
	}
}
