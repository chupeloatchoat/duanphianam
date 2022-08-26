package gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class MyApp {
	static JLabel lbl;
	public static void main(String[] args) throws InvocationTargetException, InterruptedException{
		SwingUtilities.invokeAndWait(() -> {
			new MyGui().setVisible(true);
		});
 	}
	
	static class MyGui extends JFrame{
		
		public MyGui() {
			setTitle("SwingWorker Demo");
			setResizable(false);
			setSize(400,200);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			Box b,b1,b2,b3;
			add(b = Box.createVerticalBox());
			b.add(b1 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10));
			b.add(b2 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10));
			b.add(b3 = Box.createHorizontalBox()); b.add(Box.createVerticalStrut(10));
			
			b.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			
			JProgressBar progressBar;
			b1.add(progressBar = new JProgressBar());
			
			progressBar.setStringPainted(true);
			
			
			b2.add(lbl = new JLabel("Value : 0"));
			
			JButton btn;
			b3.add(btn = new JButton("Start"));
			
			btn.addActionListener((e) -> {
				MyTask myTask = new MyTask();
				myTask.execute();
				myTask.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						// TODO Auto-generated method stub
						if(evt.getPropertyName().equals("progress")) {
							progressBar.setValue((int)evt.getNewValue());
						}
					}
				});
			}); 
			
		}
	}
	static  class MyTask extends SwingWorker<Integer, Integer>{

		@Override
		protected Integer doInBackground() throws Exception {
			
			int progress = 0;
			
			int total = 0;
			for (int i = 0; i < 1000; i+=10) {
				total += i;
				publish(total);
				progress ++;
				setProgress(progress);
				Thread.sleep(10);
			}
			
			return total;
		}

		@Override
		protected void process(List<Integer> chunks) {
			// TODO Auto-generated method stub
			int value = chunks.get(chunks.size() - 1);
			lbl.setText("value: " + value);
		}

		@Override
		protected void done() {
			// TODO Auto-generated method stub
			try {
				int total = get();
				System.out.println("total: " + total);
				System.out.println("finshed");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	 	
	}
}
