import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.LayoutStyle.ComponentPlacement;


public class Window extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTextPane txtpnElProgramaMostrar = new JTextPane();
		txtpnElProgramaMostrar.setEditable(false);
		txtpnElProgramaMostrar.setText("El programa mostrar\u00E1 la grafica de la complejidad temporal del algoritmo Quicksort, "
				+ "para ello se haran varias pruebas con diferentes arreglos desordenados. Se realizar\u00E1n 50 pruebas por cada "
				+ "arreglo de tamaño n, al final se sacar\u00E1 un promedio de las veces que se realizo la operacion basica A[i] < pivote. "
				+ "NOTA: n crecerá en intervalos de 20 empezando con el 4 [4, 24, 44,..., 10,004]");
		
		JPanel panel = new ProgressBarDemo();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnElProgramaMostrar, GroupLayout.PREFERRED_SIZE, 403, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtpnElProgramaMostrar, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
class ProgressBarDemo extends JPanel
implements ActionListener, 
           PropertyChangeListener {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progressBar;
	private JButton startButton;
	private JTextArea taskOutput;
	private Task task;
	ArrayList<Duplas> tabla = new ArrayList<Duplas>();

	class Task extends SwingWorker<Void, Void> {
/*
* Main task. Executed in background thread.
*/
		@Override
		public Void doInBackground() {
			Random random = new Random();
			int progress = 0;
//Initialize progress property.
			setProgress(0);
			while (progress < 100) {
//Sleep for up to one second.
				try {
					Thread.sleep(random.nextInt(1000));
				} catch (InterruptedException ignore) {}
//Make random progress.
				progress += random.nextInt(10);
				setProgress(Math.min(progress, 100));
			}
			return null;
		}

/*
* Executed in event dispatching thread
*/
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			startButton.setEnabled(true);
			setCursor(null); //turn off the wait cursor
			taskOutput.append("Done!\n");
			
	    	new Tarea5();
			DefaultCategoryDataset data = new DefaultCategoryDataset();
	        for(int i = 0; i < tabla.size(); i++){
	        	data.setValue(tabla.get(i).execut, "Ejecuciones de la Operacion Basica",Double.toString(tabla.get(i).execut));
	        }
	        //Creamos un Chart
	        JFreeChart chart = ChartFactory.createLineChart(
	                           "Ejecuciones vs datos",
	                           "n",
	                           "f(n) - Ejecuciones",
	                           data
	                           );
	        //Creamos una especie de frame y mostramos el JFreeChart en él
	        //Este constructor nos pide el título del Chart y el chart creado
	        ChartFrame tableFrame=new ChartFrame("Primer Chart para javax0711",chart);
	        tableFrame.pack();
	        tableFrame.setVisible(true);
	        tabla = null;
		}
	}
	
	public ProgressBarDemo() {
		super(new BorderLayout());

//Create the demo's UI.
		startButton = new JButton("Start");
		startButton.setActionCommand("start");
		startButton.addActionListener(this);
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(5,5,5,5));
		taskOutput.setEditable(false);

		JPanel panel = new JPanel();
		panel.add(startButton);
		panel.add(progressBar);

		add(panel, BorderLayout.PAGE_START);
		add(new JScrollPane(taskOutput), BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	}

/**
* Invoked when the user presses the start button.
*/
	public void actionPerformed(ActionEvent evt) {
		startButton.setEnabled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//Instances of javax.swing.SwingWorker are not reusuable, so
//we create new instances as needed.
		task = new Task();
		task.addPropertyChangeListener(this);
		task.execute();
		tabla = Tarea5.Executions();

	}

/**
* Invoked when task's progress property changes.
*/
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
		//	taskOutput.append(String.format(
		//			"Dupla: " + tabla.get(progress).n + " guardada.\n"));
		} 
	}
}

/**
* Create the GUI and show it. As with all GUI code, this must run
* on the event-dispatching thread.
*/
